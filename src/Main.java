import Entity.ClassDiagram.ClassDiagram;
import Entity.ClassDiagram.ClassDiagramDAO;
import View.Components.FrameSetter;
import View.Components.MenuBarBuilder;
import View.Login;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        LookAndFeel lookAndFeel = new FlatDarculaLaf();
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JFrame f = FrameSetter.getjFrame();
        f.setVisible(true);
        f.setContentPane(new Login().getView());
        f.validate();
    }
}
