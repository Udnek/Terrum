package me.udnekjupiter;

import me.udnekjupiter.file.FileManager;
import me.udnekjupiter.graphic.engine.GraphicEngine;
import me.udnekjupiter.graphic.object.traceable.TraceableObject;
import me.udnekjupiter.graphic.object.traceable.shape.PolygonObject;
import me.udnekjupiter.graphic.scene.GraphicScene3d;
import me.udnekjupiter.graphic.triangle.TraceableTriangle;
import org.jocl.*;
import org.realityforge.vecmath.Vector3d;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Collections;
import java.util.List;

public class GPUGraphicEngine implements GraphicEngine {
    private final String kernelCode = readKernelCodeFile();

    private final String kernelName = "rayTracer";
    private cl_kernel kernel;
    private cl_context context;
    private cl_command_queue commandQueue;
    private cl_program program;

    double[] planeHits;
    int[] pixelToPlane;

    private GraphicScene3d scene;

    private final TraceableObject object =
            new PolygonObject(
                    new Vector3d(),
                    new TraceableTriangle(
                            new Vector3d(-1, -1, 1),
                            new Vector3d(1, -1, 1),
                            new Vector3d(0, 1, 1)
                    )
            );

    public GPUGraphicEngine(GraphicScene3d scene){
        this.scene = scene;
    }
    @Override
    public void initialize() {

        scene.initialize();

        final int platformIndex = 0;
        final long deviceType = CL.CL_DEVICE_TYPE_ALL;
        final int deviceIndex = 0;

        // Enable exceptions and subsequently omit error checks in this sample
        CL.setExceptionsEnabled(true);

        // Obtain the number of platforms
        int numPlatformsArray[] = new int[1];
        CL.clGetPlatformIDs(0, null, numPlatformsArray);
        int numPlatforms = numPlatformsArray[0];

        // Obtain a platform ID
        cl_platform_id platforms[] = new cl_platform_id[numPlatforms];
        CL.clGetPlatformIDs(platforms.length, platforms, null);
        cl_platform_id platform = platforms[platformIndex];

        // Initialize the context properties
        cl_context_properties contextProperties = new cl_context_properties();
        contextProperties.addProperty(CL.CL_CONTEXT_PLATFORM, platform);

        // Obtain the number of devices for the platform
        int numDevicesArray[] = new int[1];
        CL.clGetDeviceIDs(platform, deviceType, 0, null, numDevicesArray);
        int numDevices = numDevicesArray[0];

        // Obtain a device ID
        cl_device_id devices[] = new cl_device_id[numDevices];
        CL.clGetDeviceIDs(platform, deviceType, numDevices, devices, null);
        cl_device_id device = devices[deviceIndex];

        // Create a context for the selected device
        context = CL.clCreateContext(
            contextProperties, 1, new cl_device_id[]{device},
            null, null, null);

        // Create a command-queue for the selected device
        commandQueue = CL.clCreateCommandQueue(context, device, 0, null);

        // Create the program from the source code
        program = CL.clCreateProgramWithSource(context,
            1, new String[]{kernelCode}, null, null);
        // Build the program
        CL.clBuildProgram(program, 0, null, null, null, null);
        // Create the kernel
        kernel = CL.clCreateKernel(program, kernelName, null);

    }
    private String readKernelCodeFile(){
        File file = FileManager.readFile(FileManager.Directory.SHADER, "kernel.cl");

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) { throw new RuntimeException(e);}

        String line;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            reader.close();
            return stringBuilder.toString();

        } catch (IOException e) {throw new RuntimeException(e);}
    }
    @Override
    public BufferedImage renderFrame(int width, int height) {
        scene.beforeFrameUpdate(width, height);

        int w = 350;
        int h = 350;

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        generate(Collections.singletonList(scene.getTraceableObjects().get(0)), w, h);

        bufferedImage.setRGB(0, 0, w, h, pixelToPlane, 0 , w);

        return bufferedImage;
    }
    public void generate(List<TraceableObject> traceableObjects, int width, int height){
        int planesAmount = getPlanesAmount(traceableObjects);

        planeHits = new double[planesAmount];
        pixelToPlane = new int[width * height];
        double[] poses = objectsToVerticesPositions(traceableObjects, planesAmount);
        int[] planesAmountArray = new int[]{planesAmount};
        ;
        // Set the arguments for the kernel

        Pointer planeHitsPointer = Pointer.to(planeHits);
        Pointer pixelsPointer = Pointer.to(pixelToPlane);
        Pointer posesPointer = Pointer.to(poses);
        Pointer planesAmountPointer = Pointer.to(planesAmountArray);

        // Allocate the memory objects for the input and output data
        cl_mem[] memory = new cl_mem[4];
        memory[0] = CL.clCreateBuffer(context,
                CL.CL_MEM_READ_WRITE,
                Sizeof.cl_double * planeHits.length, null, null);
        memory[1] = CL.clCreateBuffer(context,
                CL.CL_MEM_READ_WRITE,
                Sizeof.cl_int * pixelToPlane.length, null,null);
        memory[2] = CL.clCreateBuffer(context,
                CL.CL_MEM_READ_ONLY | CL.CL_MEM_COPY_HOST_PTR,
                Sizeof.cl_double * poses.length, posesPointer, null);
        memory[3] = CL.clCreateBuffer(context,
                CL.CL_MEM_READ_ONLY | CL.CL_MEM_COPY_HOST_PTR,
                Sizeof.cl_int * planesAmountArray.length, planesAmountPointer, null);

        CL.clSetKernelArg(kernel, 0, Sizeof.cl_mem, Pointer.to(memory[0]));
        CL.clSetKernelArg(kernel, 1, Sizeof.cl_mem, Pointer.to(memory[1]));
        CL.clSetKernelArg(kernel, 2, Sizeof.cl_mem, Pointer.to(memory[2]));
        CL.clSetKernelArg(kernel, 3, Sizeof.cl_mem, Pointer.to(memory[3]));

        // Set the work-item dimensions
        long[] global_work_size = new long[]{pixelToPlane.length};
        long[] local_work_size = new long[]{1};

        // Execute the kernel
        CL.clEnqueueNDRangeKernel(commandQueue, kernel, 1, null,
                global_work_size, local_work_size, 0, null, null);

        // Read the output data
        CL.clEnqueueReadBuffer(commandQueue, memory[0], CL.CL_TRUE, 0,
                planeHits.length * Sizeof.cl_double, planeHitsPointer, 0, null, null);
        CL.clEnqueueReadBuffer(commandQueue, memory[1], CL.CL_TRUE, 0,
                pixelToPlane.length * Sizeof.cl_int, pixelsPointer, 0, null, null);

        // Release kernel, program, and memory objects
        CL.clReleaseMemObject(memory[0]);
        CL.clReleaseMemObject(memory[1]);
        CL.clReleaseMemObject(memory[2]);
        CL.clReleaseMemObject(memory[3]);

    }

    public int getPlanesAmount(List<TraceableObject> objects){
        int planesAmount = 0;
        for (TraceableObject traceableObject : objects) {
            planesAmount += traceableObject.getRenderTriangles().length;
        }
        return planesAmount;
    }

    public double[] objectsToVerticesPositions(List<TraceableObject> traceableObjects, int planesAmount){
        int index = 0;
        double[] result = new double[planesAmount * 3 * 3];
        for (TraceableObject object : traceableObjects) {
            TraceableTriangle[] renderTriangles =  object.getRenderTriangles();
            for (int i = 0, renderTrianglesLength = renderTriangles.length; i < renderTrianglesLength; i++) {
                TraceableTriangle renderTriangle = renderTriangles[i];
                renderTriangle.addToAllVertexes(object.getPosition().add(0, 0, 0));
                for (Vector3d vertex : renderTriangle.getVertices()) {
                    result[index++] = vertex.x;
                    result[index++] = vertex.y;
                    result[index++] = vertex.z;
                }
            }
        }
        return result;
    }

    public void stop(){
        System.out.println("stopping");
        CL.clReleaseKernel(kernel);
        CL.clReleaseProgram(program);
        CL.clReleaseCommandQueue(commandQueue);
        CL.clReleaseContext(context);
    }
}
