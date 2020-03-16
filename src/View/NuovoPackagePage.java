package View;

import Entity.Package.Package;
import Entity.Package.PackageDAO;
import View.Components.ColorPicker;
import View.Components.FrameSetter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;

public class NuovoPackagePage {
    private JPanel view;
    private JButton add;
    private JTextField packageName;
    private JTextArea packageComment;
    private JLabel nameLabel;
    private JLabel commentLabel;
    private JScrollPane commentContainer;
    private JLabel errMessageForNome;
    private JLabel errMessageForComment;
    private JPanel errMessageWrapperForNome;
    private JPanel errMessageWrapperForComment;

    private class NuovoPackagePageController {

        private static final int NAME_TOO_LONG = -1;
        private static final int COMMENT_TOO_LONG = -2;
        private static final int MISSING_NAME = -3;

        private PackageDAO pDAO;

        private int checkIfValidData(){
            String a = packageName.getText();
            String b = packageComment.getText();
            if (a.length()>=200) {
                return NAME_TOO_LONG;
            }else if(b.length()>=4000){
                return COMMENT_TOO_LONG;
            }else if (a.length()==0){
                return MISSING_NAME;
            }else{
                return 0;
            }
        }
        private void cleanOnFocus(JTextField a){
            a.setText(null);
            add.setEnabled(true);
            if(errMessageWrapperForNome.isVisible()){
                errMessageWrapperForNome.setVisible(false);
            }
            return;
        }
        private void cleanOnFocus(JTextArea a){
            a.setText(null);
            if(errMessageWrapperForComment.isVisible()){
                errMessageWrapperForComment.setVisible(false);
            }
            return;
        }
        private void addPackageButtonPressed(){
            int err = checkIfValidData();
            System.out.println("err vale: " + err);
            if(err<0){
                add.setEnabled(false);
                if(err==NAME_TOO_LONG){
                    errMessageForNome.setText("Il nome non può superare i 200 caratteri.");
                    errMessageForNome.setVisible(true);
                    errMessageWrapperForNome.setVisible(true);
                }
                if(err == COMMENT_TOO_LONG){
                    errMessageForComment.setText("Il commento non può superare i 4000 caratteri.");
                    errMessageForComment.setVisible(true);
                    errMessageWrapperForComment.setVisible(true);
                }
                if(err == MISSING_NAME) {
                    errMessageForNome.setText("Devi inserire un nome.");
                    errMessageForNome.setVisible(true);
                    errMessageWrapperForNome.setVisible(true);
                }
            }else{
                    try{
                        pDAO = new PackageDAO();
                        Package p;
                        if(packageComment.getText()!= "MAX 4000 caratteri" || packageComment.getText() !="") {
                             p = new Package(-1, packageName.getText(), packageComment.getText());
                        }else{
                             p = new Package(-1, packageName.getText(), null);
                        }
                        pDAO.createPackage(p);
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
            }
            return;
        }

    }

    public NuovoPackagePage(){

        NuovoPackagePageController controller = new NuovoPackagePageController();

        view = new JPanel();
        SpringLayout layout = new SpringLayout();


        add = new JButton("Crea Package");
        nameLabel = new JLabel("      Inserisci il nome del Package:");
        commentLabel = new JLabel("Inserisci il commento al Package:");
        packageName = new JTextField("MAX 200 caratteri", 20);
        packageComment = new JTextArea("MAX 4000 caratteri", 10, 20);
        errMessageWrapperForNome = new JPanel();
        errMessageWrapperForComment = new JPanel();
        errMessageForNome = new JLabel("");
        errMessageForComment = new JLabel("");

        errMessageWrapperForNome.setVisible(false);
        errMessageWrapperForNome.setBackground(ColorPicker.getColor("red"));
        errMessageWrapperForComment.setVisible(false);
        errMessageWrapperForComment.setBackground(ColorPicker.getColor("red"));

        errMessageWrapperForComment.add(errMessageForComment);
        errMessageWrapperForNome.add(errMessageForNome);

        add.setEnabled(false);

        errMessageForNome.setVisible(false);
        errMessageForComment.setVisible(false);

        packageComment.setLineWrap(true);
        packageComment.setWrapStyleWord(true);
        packageComment.setAutoscrolls(true);

        commentContainer = new JScrollPane(packageComment);


        packageName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                controller.cleanOnFocus(packageName);
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                return;
            }
        });
        packageComment.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                controller.cleanOnFocus(packageComment);
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                return;
            }
        });

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.addPackageButtonPressed();
            }
        });

        layout.putConstraint(SpringLayout.WEST,nameLabel, (int) (FrameSetter.getjFrame().getWidth()/4), SpringLayout.WEST, view);
        layout.putConstraint(SpringLayout.NORTH,nameLabel, 30, SpringLayout.NORTH, view);

        layout.putConstraint(SpringLayout.WEST, commentLabel, 0, SpringLayout.WEST, nameLabel);
        layout.putConstraint(SpringLayout.NORTH,commentLabel, 30, SpringLayout.NORTH, errMessageWrapperForNome);

        layout.putConstraint(SpringLayout.WEST, packageName, 200, SpringLayout.WEST, nameLabel);
        layout.putConstraint(SpringLayout.NORTH,packageName, 30 , SpringLayout.NORTH, view);

        layout.putConstraint(SpringLayout.WEST, errMessageWrapperForNome, 0, SpringLayout.WEST, packageName);
        layout.putConstraint(SpringLayout.NORTH,errMessageWrapperForNome, 30 , SpringLayout.NORTH, packageName);

        layout.putConstraint(SpringLayout.WEST,commentContainer, 200, SpringLayout.WEST, commentLabel);
        layout.putConstraint(SpringLayout.NORTH, commentContainer, 30, SpringLayout.NORTH, errMessageWrapperForNome);

        layout.putConstraint(SpringLayout.WEST, errMessageWrapperForComment, 0, SpringLayout.WEST, commentContainer);
        layout.putConstraint(SpringLayout.NORTH,errMessageWrapperForComment, 10,SpringLayout.SOUTH, commentContainer);

        layout.putConstraint(SpringLayout.WEST, add, (FrameSetter.getjFrame().getWidth())-(FrameSetter.getjFrame().getWidth()/4), SpringLayout.WEST, view);
        layout.putConstraint(SpringLayout.SOUTH, add, -30, SpringLayout.SOUTH, view);


        view.add(commentLabel);
        view.add(nameLabel);
        view.add(packageName);
        view.add(commentContainer);
        view.add(errMessageWrapperForComment);
        view.add(errMessageWrapperForNome);
        view.add(add);

        view.setLayout(layout);

        view.setVisible(true);
        return;
    }

    public JPanel getView() {
        return view;
    }
}
