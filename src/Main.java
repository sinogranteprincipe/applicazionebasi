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
        MenuBarBuilder menuBarBuilder = new MenuBarBuilder();
        JMenuBar mb = MenuBarBuilder.getMenuBar();
        menuBarBuilder.setHomePageMenuBar();
        //f.getContentPane().add(new JLabel("<html>Benvenuto!<br/>L'applicazione consente la gestione di un database per la memorizzazione di class diagram.</html>", SwingConstants.CENTER));
        f.setJMenuBar(mb);
        f.setVisible(true);
        f.setContentPane(new Login().getView());
        f.validate();
    }
}
