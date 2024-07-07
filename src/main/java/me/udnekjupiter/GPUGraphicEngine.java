package me.udnekjupiter;

import me.udnekjupiter.file.FileManager;
import me.udnekjupiter.graphic.engine.GraphicEngine;
import org.jocl.*;

import java.awt.image.BufferedImage;
import java.io.*;

public class GPUGraphicEngine implements GraphicEngine {
    private final String kernelCode = readKernelCodeFile();

    private final String kernelName = "rayCast";
    private cl_kernel kernel;
    private cl_context context;
    private cl_command_queue commandQueue;
    private cl_program program;

    public void initialize() {

        final int platformIndex = 0;
        final long deviceType = CL.CL_DEVICE_TYPE_ALL;
        final int deviceIndex = 0;

        System.out.println(kernelCode);

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
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int[] output = new int[width * height];

        generate(output);
        bufferedImage.setRGB(0, 0, width, height, output, 0 , width);

        return bufferedImage;
    }
    public void generate(int[] output){

        int n = output.length;
        // Set the arguments for the kernel

        Pointer pointer = Pointer.to(output);

        // Allocate the memory objects for the input and output data
        cl_mem memObjects[] = new cl_mem[1];
        memObjects[0] = CL.clCreateBuffer(context,
                CL.CL_MEM_READ_WRITE,
                Sizeof.cl_float * n, null, null);

        CL.clSetKernelArg(kernel, 0,
            Sizeof.cl_mem, Pointer.to(memObjects[0]));

        // Set the work-item dimensions
        long global_work_size[] = new long[]{n};
        long local_work_size[] = new long[]{1};

        // Execute the kernel
        CL.clEnqueueNDRangeKernel(commandQueue, kernel, 1, null,
                global_work_size, local_work_size, 0, null, null);

        // Read the output data
        CL.clEnqueueReadBuffer(commandQueue, memObjects[0], CL.CL_TRUE, 0,
            n * Sizeof.cl_int, pointer, 0, null, null);

        // Release kernel, program, and memory objects
        CL.clReleaseMemObject(memObjects[0]);
    }

    public void stop(){
        System.out.println("stopping");
        CL.clReleaseKernel(kernel);
        CL.clReleaseProgram(program);
        CL.clReleaseCommandQueue(commandQueue);
        CL.clReleaseContext(context);
    }
}
