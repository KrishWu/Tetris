package src;
import java.awt.*;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class Main {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 900;

    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        //Create the JFrame
        JFrame f = new JFrame();

        //And the canvas with the width and height
        Canvas c = new Canvas(WIDTH, HEIGHT);
        c.setPreferredSize(new Dimension(WIDTH, HEIGHT));//Ask to make that the height and width.
        f.add(c);//Add the canvas to the JFrame.
        f.pack();//Squish it so it is the right size.

        // Toolkit tk = Toolkit.getDefaultToolkit();

        f.setVisible(true);//Make it visible.
    }
}