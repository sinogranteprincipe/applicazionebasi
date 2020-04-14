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
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.*;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;


public class Login{

    private JPanel view;
    private JButton accedi;
    private JTextField hostTextField;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JTextField portaTextField;
    private JTextField databaseTextField;
    private JLabel saluto;
    private JLabel hostLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel portaLabel;
    private JLabel databaseLabel;
    private JPanel hostWrapper;
    private JPanel usernameWrapper;
    private JPanel passwordWrapper;
    private JPanel databaseWrapper;
    private JPanel portaWrapper;

    private JLabel errMessage;
    private JPanel errWrapper;
    private  class LoginController{
        private int checkIfValidData(){
            String host = hostTextField.getText();
            String porta = portaTextField.getText();
            String username = usernameTextField.getText();
            char[] password = passwordTextField.getPassword();
            String database = databaseTextField.getText();
            System.out.println(username);
            System.out.println(password);
            if(host==null || porta==null || username == null || password == null || database == null){
                return -1;
            }

            return 0;
        }

        public void login() {
            if(checkIfValidData()==0){
                String host = hostTextField.getText();
                String porta = portaTextField.getText();
                String username = usernameTextField.getText();
                char[] passwordtmp = passwordTextField.getPassword();
                String password = new String(passwordtmp);
                String database = databaseTextField.getText();
                try {
                    MyOracleConnection orcl = new MyOracleConnection(host, username, password, porta, database);
                    MenuBarBuilder menuBarBuilder = new MenuBarBuilder();
                    JMenuBar mb = MenuBarBuilder.getMenuBar();
                    menuBarBuilder.setHomePageMenuBar();
                    FrameSetter.getjFrame().setJMenuBar(mb);
                    FrameSetter.getjFrame().setContentPane(new VisualizzaPackagesPage().getView());
                    FrameSetter.getjFrame().revalidate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    errMessage.setText("Impossibile connettersi al database.");
                    errMessage.setVisible(true);
                    errWrapper.setVisible(true);
                    FrameSetter.getjFrame().getContentPane().revalidate();
                }
            }else{
                errMessage.setText("Compila tutti i campi!");
                errMessage.setVisible(true);
                errWrapper.setVisible(true);
                FrameSetter.getjFrame().getContentPane().revalidate();
            }
        }
    }
    public Login(){
        LoginController controller = new LoginController();
        view = new JPanel();
        SpringLayout layout = new SpringLayout();
        saluto = new JLabel("<html>Benvenuto!<br/>L'applicazione consente la gestione di un database per la memorizzazione di class diagram.<br/><br/><br/><br/></html>", SwingConstants.CENTER);
        accedi = new JButton("Accedi");
        hostLabel     =new JLabel("             Inserisci host:");
        portaLabel    =new JLabel("            Inserisci porta:");
        databaseLabel =new JLabel("    Inserisci nome database:");
        usernameLabel =new JLabel("      Inserisci nome utente:");
        passwordLabel =new JLabel("  Inserisci password utente:");
        hostTextField =new JTextField( 20);
        usernameTextField = new JTextField( 20);
        passwordTextField = new JPasswordField( 20);
        portaTextField = new JTextField( 20);
        databaseTextField = new JTextField( 20);
        errMessage = new JLabel();
        errWrapper = new JPanel();
        errMessage.setVisible(false);
        errWrapper.setVisible(false);
        errWrapper.add(errMessage);

        errWrapper.setBackground(ColorPicker.getColor("red"));

        accedi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.login();
            }
        });

        accedi.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                if(keyEvent.getKeyCode()==KeyEvent.VK_ENTER){
                    accedi.doClick();
                }
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });

        hostWrapper = new JPanel();
        usernameWrapper = new JPanel();
        passwordWrapper = new JPanel();
        databaseWrapper = new JPanel();
        portaWrapper = new JPanel();

        hostWrapper.add(hostLabel);
        hostWrapper.add(hostTextField);

        portaWrapper.add(portaLabel);
        portaWrapper.add(portaTextField);

        databaseWrapper.add(databaseLabel);
        databaseWrapper.add(databaseTextField);

        usernameWrapper.add(usernameLabel);
        usernameWrapper.add(usernameTextField);

        passwordWrapper.add(passwordLabel);
        passwordWrapper.add(passwordTextField);

        view.add(saluto);
        view.add(databaseWrapper);
        view.add(hostWrapper);
        view.add(portaWrapper);
        view.add(usernameWrapper);
        view.add(passwordWrapper);
        view.add(accedi);
        view.add(errWrapper);

        layout.putConstraint(SpringLayout.NORTH, saluto, FrameSetter.getjFrame().getHeight()/8 , SpringLayout.NORTH, view);
        layout.putConstraint(SpringLayout.WEST, saluto, FrameSetter.getjFrame().getWidth()/5, SpringLayout.WEST, view);

        layout.putConstraint(SpringLayout.NORTH, databaseWrapper, 10, SpringLayout.SOUTH, saluto);
        layout.putConstraint(SpringLayout.WEST, databaseWrapper,FrameSetter.getjFrame().getWidth()/4, SpringLayout.WEST, view);

        layout.putConstraint(SpringLayout.NORTH, hostWrapper, 50, SpringLayout.SOUTH, saluto);
        layout.putConstraint(SpringLayout.WEST, hostWrapper,FrameSetter.getjFrame().getWidth()/4+25, SpringLayout.WEST, view);

        layout.putConstraint(SpringLayout.WEST, portaWrapper,FrameSetter.getjFrame().getWidth()/4+25, SpringLayout.WEST, view);
        layout.putConstraint(SpringLayout.NORTH, portaWrapper, 90, SpringLayout.SOUTH, saluto);

        layout.putConstraint(SpringLayout.WEST, usernameWrapper,FrameSetter.getjFrame().getWidth()/4, SpringLayout.WEST, view);
        layout.putConstraint(SpringLayout.NORTH, usernameWrapper, 130, SpringLayout.SOUTH, saluto);

        layout.putConstraint(SpringLayout.WEST, passwordWrapper,FrameSetter.getjFrame().getWidth()/4, SpringLayout.WEST, view);
        layout.putConstraint(SpringLayout.NORTH, passwordWrapper, 170, SpringLayout.SOUTH, saluto);

        layout.putConstraint(SpringLayout.WEST, accedi,(int)(FrameSetter.getjFrame().getWidth()/2.3), SpringLayout.WEST, view);
        layout.putConstraint(SpringLayout.NORTH, accedi, 220, SpringLayout.SOUTH, saluto);

        layout.putConstraint(SpringLayout.WEST, errWrapper,(int)(FrameSetter.getjFrame().getWidth()/2.3), SpringLayout.WEST, view);
        layout.putConstraint(SpringLayout.NORTH, errWrapper, 250, SpringLayout.SOUTH, saluto);

        view.setLayout(layout);

        view.setVisible(true);
        return;

    }

    public JPanel getView() {
        return view;
    }
}