package View;

import Entity.MyOracleConnection;
import Entity.Package.Package;
import Entity.Package.PackageDAO;
import View.Components.ColorPicker;
import View.Components.FrameSetter;
import View.Components.MenuBarBuilder;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.*;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.sun.java.swing.*;

public class Login extends JFrame{

    private JPanel view;
    private JButton accedi;
    private JTextField hostTextField;
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JTextField portaTextField;
    private JTextField databaseTextField;
    private JLabel saluto;
    private JLabel hostLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel portaLabel;
    private JLabel databaseLabel;

    /*public void Benvenuto () {
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
        f.getContentPane().add(new JLabel("<html>Benvenuto!<br/>L'applicazione consente la gestione di un database per la memorizzazione di class diagram.</html>", SwingConstants.CENTER));

    }
*/
    public Login(){

        view = new JPanel();

        saluto = new JLabel("<html>Benvenuto!<br/>L'applicazione consente la gestione di un database per la memorizzazione di class diagram.<br/><br/><br/><br/></html>", SwingConstants.CENTER);
        accedi = new JButton("Accedi");
        hostLabel = new JLabel("      Inserisci host:");
        portaLabel = new JLabel("Inserisci porta:");
        databaseLabel = new JLabel("Inserisci nome database:");
        usernameLabel = new JLabel("Inserisci nome utente:");
        passwordLabel = new JLabel("Inserisci password utente:");
        hostTextField = new JTextField( 20);
        usernameTextField = new JTextField( 20);
        passwordTextField = new JTextField( 20);
        //JPasswordField passwordField = new JPasswordField(20);
        portaTextField = new JTextField( 20);
        databaseTextField = new JTextField( 20);


        view.add(saluto);
        view.add(hostLabel);
        view.add(portaLabel);
        view.add(databaseLabel);
        view.add(usernameLabel);
        view.add(passwordLabel);

        view.add(hostTextField);
        view.add(portaTextField);
        view.add(databaseTextField);
        view.add(usernameTextField);
        view.add(passwordTextField);

        // da qui alla linea 120 ho cercato di usare https://mat.unicam.it/piergallini/home/materiale/gc/java/ui/swing/textfield.html
        //la classe login estende JFrame perche altrimenti non compilava a causa di setContentPane
        //non funziona uguale, se tolgo sto codice funzIona ma si vede uno schifo
        JPanel labelPane = new JPanel();
        labelPane.setLayout(new GridLayout(0, 1));
        labelPane.add(hostLabel);
        labelPane.add(portaLabel);
        labelPane.add(databaseLabel);
        labelPane.add(usernameLabel);
        labelPane.add(passwordLabel);


        JPanel fieldPane = new JPanel();
        fieldPane.setLayout(new GridLayout(0, 1));
        fieldPane.add(hostTextField);
        fieldPane.add(portaTextField);
        fieldPane.add(databaseTextField);
        fieldPane.add(usernameTextField);
        fieldPane.add(passwordTextField);


        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.setLayout(new BorderLayout());
        contentPane.add(labelPane);
        contentPane.add(fieldPane, "East");

        setContentPane(contentPane);

        view.setVisible(true);
        return;
        /*
        accedi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MyOracleConnection(hostTextField.getText(), usernameTextField.getText(), passwordTextField.getText(), portaTextField.getText(), databaseTextField.getText());
            }
        });*/
    }

    public JPanel getView() {
        return view;
    }
}