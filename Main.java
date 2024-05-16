import java.awt.*;
import javax.swing.*;

public class Main {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 900;

    public static void main(String[] args) {
        JFrame f = new JFrame();

        Canvas c = new Canvas(WIDTH, HEIGHT);
        c.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        f.add(c);
        f.pack();

        Toolkit tk = Toolkit.getDefaultToolkit();

        f.setVisible(true);
    }
}