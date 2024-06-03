package me.udnek.test;

import org.realityforge.vecmath.Vector3d;

public class Tests {
    public static void run() {

        Vector3d origin = new Vector3d(-2, 0, 1);
        Vector3d second = new Vector3d(-1, 0, 1);

        double dot = origin.x * -second.z + origin.z * second.x;
        System.out.println(dot < 0);


/*        List<BufferedImage> listBI = new ArrayList<>();
        ImageReader imageReader = new ImageReader();
        imageRead

        File out;
        try {
            out = new File(ImageReader.getImageDirectory() + "output.mp4");
            // for Android use: AndroidSequenceEncoder
            AWTSequenceEncoder encoder = AWTSequenceEncoder.createSequenceEncoder(out, 25);
            for (BufferedImage bufferedImage : listBI) {
                for (int i = 0; i < 25; i++) {
                    encoder.encodeImage(bufferedImage);
                }
            }
            encoder.finish();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/


/*        AWTSequenceEncoder encoder;
        try {
            encoder = AWTSequenceEncoder.createSequenceEncoder(new File(ImageReader.getImageDirectory() + "test.mp4"), 25); // 25 fps
            for (BufferedImage image : listBI) {
                for (int i = 0; i < 25; i++) {
                    encoder.encodeImage(image);
                }

            }
            encoder.finish();
        } catch (Exception e) {
            throw new RuntimeException(e);

        }*/




/*        int size = 512;
        int[] array = new int[size];

        System.out.println(Arrays.toString(array));

        TestRunnable testRunnable0 = new TestRunnable(array, 0);
        TestRunnable testRunnable1 = new TestRunnable(array, 1);
        TestRunnable testRunnable2 = new TestRunnable(array, 2);
        TestRunnable testRunnable3 = new TestRunnable(array, 3);


        testRunnable0.run();
        testRunnable1.run();
        testRunnable2.run();
        testRunnable3.run();

        System.out.println(Arrays.toString(array));*/




    }

}
