package View.Components;

import Entity.ClassDiagram.ClassDiagram;
import Entity.Classe.Classe;
import Entity.Classe.ClasseDAO;
import Entity.GerarchiaClassi.GerarchiaClassi;
import Entity.GerarchiaClassi.GerarchiaClassiDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.sql.SQLException;

public class GerarchiaClassiOption {
    private class GerarchiaClassiOptionController{

        public void populateList(ClassDiagram cd) {
            try {
                Classe classebase = null;
                ClasseDAO classeDAO = new ClasseDAO();
                GerarchiaClassiDAO gerarchiaClassiDAO = new GerarchiaClassiDAO();
                List<Classe> l = classeDAO.readAllInCLassDIagram(cd);
                for (Classe c : l){
                    if(c.getNome().compareTo(className)==0){
                        classebase = c;
                    }
                }
                if(classebase!= null){
                    List<GerarchiaClassi> k = gerarchiaClassiDAO.readAllForClasse(classebase);
                    for (Classe c : l){
                        boolean check = true;
                        for(GerarchiaClassi g: k) {
                            if(g.getIdSottoClasse()==c.getId() || g.getIdSuperClasse() == c.getId()){
                                check = false;
                            }
                        }
                        if (c.getNome().compareTo(className) != 0 && check) {
                            existingClasses.addItem(c.getNome());
                        }
                    }
                }else{
                    errMessage.setText("Ops... non dovresti poterci arrivare qui.");
                    errMessageWrapper.setVisible(true);
                    errMessage.setVisible(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                errMessage.setText("Impossibile connettersi al database.");
                errMessageWrapper.setVisible(true);
                errMessage.setVisible(true);
            }

        }

        public void addExtendingClass() {
            try {
                Classe classebase = null;
                Classe superclasse = null;
                ClasseDAO classeDAO = new ClasseDAO();
                GerarchiaClassiDAO gerarchiaClassiDAO = new GerarchiaClassiDAO();
                List<Classe> l = classeDAO.readAllInCLassDIagram(cd);
                for (Classe c : l){
                    if(c.getNome().compareTo(className)==0){
                        classebase = c;
                    }else if(c.getNome().compareTo((String) existingClasses.getSelectedItem())==0) {
                        superclasse = c;
                    }
                }
                if(classebase!= null && superclasse!= null){
                    System.out.println("ClasseBase:\n");
                    System.out.println(classebase.toString(1));

                    System.out.println("SuperClasse:\n");
                    System.out.println(superclasse.toString(1));

                    gerarchiaClassiDAO.addGerarchiaClassi(classebase,superclasse, null);
                }else{
                    errMessage.setText("Ops... non dovresti poterci arrivare qui.");
                    errMessageWrapper.setVisible(true);
                    errMessage.setVisible(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                errMessage.setText("Impossibile connettersi al database.");
                errMessageWrapper.setVisible(true);
                errMessage.setVisible(true);
            }
        }
    }

    String className;
    ClassDiagram cd;
    private JLabel prompt;
    private JComboBox<String> existingClasses;
    private JLabel errMessage;
    private JPanel errMessageWrapper;
    private JButton aggiungi;
    private JPanel view;

    public JPanel getView() {
        return view;
    }

    public GerarchiaClassiOption(String className, ClassDiagram cd){
        this.className = className;
        this.cd = cd;
        GerarchiaClassiOptionController controller = new GerarchiaClassiOptionController();
        prompt = new JLabel("Seleziona la superclasse:");
        existingClasses = new JComboBox();
        existingClasses.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXX");
        errMessage = new JLabel();
        errMessageWrapper = new JPanel();
        errMessageWrapper.setBackground(ColorPicker.getColor("red"));
        errMessageWrapper.add(errMessage);
        errMessageWrapper.setVisible(false);
        errMessage.setVisible(false);
        aggiungi = new JButton("Aggiungi");
        view = new JPanel();

        aggiungi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.addExtendingClass();
            }
        });

        view.add(errMessageWrapper);
        view.add(prompt);
        view.add(existingClasses);
        view.add(aggiungi);

        controller.populateList(cd);

    }
}
