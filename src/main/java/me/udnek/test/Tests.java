package me.udnek.test;

import me.jupiter.image_reader.ImageReader;
import org.jcodec.api.awt.AWTSequenceEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tests {
    public static void run() {

        List<BufferedImage> listBI = new ArrayList<>();
        try {
            listBI.add(ImageIO.read(new File(ImageReader.getImageDirectory() + "1.png")));
            listBI.add(ImageIO.read(new File(ImageReader.getImageDirectory() + "2.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


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
        }


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
