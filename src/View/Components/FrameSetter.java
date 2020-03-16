package View.Components;

import javax.swing.*;
import java.awt.*;

public class FrameSetter {
    private static JFrame jFrame;

    public static JFrame getjFrame() {
        if (jFrame == null) {
            jFrame = new JFrame("Database Manager");
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            jFrame.setLocation(dim.width/5,dim.height/7);
            jFrame.setAlwaysOnTop(true);
            jFrame.setSize((int) (dim.width/1.5), (int) (dim.height/1.3));
            jFrame.setResizable(false);
            jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
        return jFrame;
    }
}
