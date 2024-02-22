package me.udnek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ScreenAdapter extends WindowAdapter {
    private final JFrame frame;
    private boolean screenIsOpen = true;

    public ScreenAdapter(){
        this.frame = new JFrame("WRLS");
        frame.addWindowListener(this);
        frame.setIgnoreRepaint(true);
        Toolkit.getDefaultToolkit().setDynamicLayout(false);
        frame.add(new Screen());
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    public void nextFrame(){
        System.out.println("NEXTFRAME");

        frame.repaint();
        frame.paintComponents(frame.getGraphics());
        frame.update(frame.getGraphics());
        frame.repaint();
        //frame.paintComponents(frame.getGraphics());
        //frame.validate();
        //frame.setVisible(true);
    }

    public void loop(){
        int fps = 5;

        int nanosecondsBetweenUpdates = (int) (Math.pow(10, 9) / fps);
        long previousStartTime = System.nanoTime();
        while (screenIsOpen) {
            long thisStartTime = System.nanoTime();
            long elapsedTime = thisStartTime - previousStartTime;
            previousStartTime = thisStartTime;

            this.nextFrame();

            float loopSlot = 1f / 50f;
            long endTime = thisStartTime + nanosecondsBetweenUpdates;
            while(System.nanoTime() < endTime) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ignored) {}
            }
        }
    }


    @Override
    public void windowClosing(WindowEvent e) {
        screenIsOpen = false;
        frame.dispose();
    }

}
