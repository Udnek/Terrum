package me.udnek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ScreenAdapter extends WindowAdapter {
    private JFrame frame;
    private boolean isScreenOpen = true;

    public ScreenAdapter(){
        this.frame = new JFrame();
        frame.addWindowListener(this);
        frame.setIgnoreRepaint(true);
        Toolkit.getDefaultToolkit().setDynamicLayout(false);
        frame.add(new Screen());
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    public void nextFrame(){
        System.out.println("NEXFRAME??????????????????????");
        frame.repaint();
    }

    public void loop(){
        int fps = 1;

        int nanosecondsBetweenUpdates = (int) (Math.pow(10, 9) / fps);
        long previousStartTime = System.nanoTime();
        while (isScreenOpen) {
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
        isScreenOpen = false;
        frame.dispose();
    }

}
