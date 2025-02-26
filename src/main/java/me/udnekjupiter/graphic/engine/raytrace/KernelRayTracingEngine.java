package me.udnekjupiter.graphic.engine.raytrace;

import me.udnekjupiter.file.FileManager;
import me.udnekjupiter.graphic.engine.GraphicEngine3d;
import me.udnekjupiter.graphic.object.GraphicObject3d;
import me.udnekjupiter.graphic.object.renderable.shape.PolygonObject;
import me.udnekjupiter.graphic.scene.GraphicScene3d;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import me.udnekjupiter.util.vector.Vector3d;
import org.jetbrains.annotations.NotNull;
import org.jocl.*;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class KernelRayTracingEngine extends GraphicEngine3d {
    private final String kernelCode = readKernelCodeFile();

    private static final String kernelName = "rayTracer";
    private cl_kernel kernel;
    private cl_context context;
    private cl_command_queue commandQueue;
    private cl_program program;

    double[] planeHits;
    int[] pixelToPlane;


    private final GraphicObject3d object =
            new PolygonObject(
                    new Vector3d(),
                    new RenderableTriangle(
                            new Vector3d(-1, -1, 1),
                            new Vector3d(1, -1, 1),
                            new Vector3d(0, 1, 1)
                    )
            );

    public KernelRayTracingEngine(GraphicScene3d scene) {
        super(scene);
    }

    @Override
    public void initialize() {
        super.initialize();

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
        BufferedReader reader = FileManager.readText(FileManager.Directory.KERNEL, "kernel.cl");

        String line;
        StringBuilder stringBuilder = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator");

        try {
            while(true) {
                line = reader.readLine();
                if (line == null) break;
                stringBuilder.append(line);
                stringBuilder.append(lineSeparator);
            }
            reader.close();

        } catch (IOException e) {throw new RuntimeException(e);}

        return stringBuilder.toString();
    }
    @Override
    public @NotNull BufferedImage renderFrame(int width, int height) {
        scene.beforeFrameUpdate(width, height);

        int w = width/1;
        int h = height/1;

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        generate(scene.getObjects(), w, h);

        bufferedImage.setRGB(0, 0, w, h, pixelToPlane, 0 , w);

        return bufferedImage;
    }
    public void generate(List<? extends GraphicObject3d> renderableObjects, int width, int height){
        int planesAmount = getPlanesAmount(renderableObjects);

        planeHits = new double[planesAmount];
        pixelToPlane = new int[width * height];
        double[] poses = objectsToVerticesPositions(renderableObjects, planesAmount);
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

    public int getPlanesAmount(List<? extends GraphicObject3d> objects){
        int planesAmount = 0;
        for (GraphicObject3d renderableObject : objects) {
            planesAmount += renderableObject.getRenderTriangles().length;
        }
        return planesAmount;
    }

    public double[] objectsToVerticesPositions(List<? extends GraphicObject3d> renderableObjects, int planesAmount){
        int index = 0;
        double[] result = new double[planesAmount * 3 * 3];
        for (GraphicObject3d object : renderableObjects) {
            RenderableTriangle[] renderTriangles =  object.getRenderTriangles();
            for (int i = 0, renderTrianglesLength = renderTriangles.length; i < renderTrianglesLength; i++) {
                RenderableTriangle renderTriangle = renderTriangles[i];
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

    @Override
    public void terminate() {
        CL.clReleaseKernel(kernel);
        CL.clReleaseProgram(program);
        CL.clReleaseCommandQueue(commandQueue);
        CL.clReleaseContext(context);
    }
}
