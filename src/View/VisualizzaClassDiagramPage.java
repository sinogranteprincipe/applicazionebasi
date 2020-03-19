package View;

import Entity.ClassDiagram.ClassDiagram;
import Entity.ClassDiagram.ClassDiagramDAO;
import Entity.ClassDiagramRiferimento.ClassDiagramRiferimento;
import Entity.ClassDiagramRiferimento.ClassDiagramRiferimentoDAO;
import Entity.Package.Package;
import Entity.Package.PackageDAO;
import View.Components.ColorPicker;
import View.Components.FrameSetter;
import View.Components.IconMaker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.event.*;
import java.util.*;
import java.sql.SQLException;

public class VisualizzaClassDiagramPage {

    JPanel  view;
    JComboBox<String> currentPackages;
    JTextField searchBar;
    JButton searchButton;
    JScrollPane tablePane;
    JTable classDiagramTable;
    JPanel errMessageWrapper;
    JLabel errMessage;
    JLabel prompt;
    DefaultTableModel tableModel;

    private class VisualizzaClassDiagramPageController{

        private boolean areTherePackages;
        private List<Package> l;

        public void cleanSearchBarOnFocus(){
            searchBar.setText("");
            return;
        }

        private void populatePackageList(JComboBox j){
            try {
                PackageDAO packageDAO = new PackageDAO();
                l = packageDAO.readAll();
                int i = 0;
                for(Package p: l){
                    if(p.getId()!= 0){
                        j.addItem(p.getNome());
                    }
                }
                if(i-1 != 0){
                    /*se ne trova almeno uno diverso da quello di default setta areTherePackages a true*/
                    areTherePackages = true;
                }

            } catch (SQLException e) {
                System.err.println(e.toString());
            }
        }

        private DefaultTableModel populateTable(){
            String packageName = (String) currentPackages.getSelectedItem();
            String toSearch = searchBar.getText();
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Nome");
            tableModel.addColumn("Commento");
            tableModel.addColumn("In Package");
            if(packageName != null && packageName!= ""){
                try{
                    ClassDiagramRiferimentoDAO classDiagramRiferimentoDAO = new ClassDiagramRiferimentoDAO();
                    PackageDAO packageDAO = new PackageDAO();
                    ClassDiagramDAO classDiagramDAO = new ClassDiagramDAO();

                    Package p = packageDAO.readPackageByName(packageName);
                    List<ClassDiagramRiferimento> riferimenti = classDiagramRiferimentoDAO.readAllDiagramsOfAPackage(p);

                    List<ClassDiagram> diagrams = classDiagramDAO.readAllInPackage(p);

                    if(toSearch.compareTo("Inserisci il nome del Class Diagram")!=0){
                        for(ClassDiagram cd : diagrams){
                            if(cd.getNome() != toSearch){
                                diagrams.remove(cd);
                            }
                        }
                        if(diagrams.isEmpty()){
                            errMessage.setText("Il diagramma non è stato trovato");
                        }else{
                            for(ClassDiagram cd : diagrams) {
                                System.out.println(cd.toString());
                                tableModel.addRow(new String[]{cd.getNome(), cd.getCommento(), p.getNome()});
                            }
                        }
                    }else{
                        for(ClassDiagram cd : diagrams) {
                            System.out.println(cd.toString());
                            tableModel.addRow(new String[]{cd.getNome(), cd.getCommento(), p.getNome()});
                        }
                        return tableModel;
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else{
                try{
                    ClassDiagramRiferimentoDAO classDiagramRiferimentoDAO = new ClassDiagramRiferimentoDAO();
                    PackageDAO packageDAO = new PackageDAO();
                    ClassDiagramDAO classDiagramDAO = new ClassDiagramDAO();
                    Package p;
                    List<ClassDiagramRiferimento> riferimentoList;
                    List<ClassDiagram> diagrams;
                    if(toSearch != null || toSearch != "" || toSearch != "Inserisci il nome del Class Diagram"){
                        diagrams = classDiagramDAO.readByName(searchBar.getText());
                        for(ClassDiagram cd: diagrams){
                            riferimentoList = classDiagramRiferimentoDAO.readAllPackagesOfAClassDiagram(cd);
                            for(ClassDiagramRiferimento cdrif : riferimentoList){
                                p = packageDAO.readPackageById(cdrif.getIdPackage());
                                tableModel.addRow(new String[]{cd.getNome(), cd.getCommento(), p.getNome()});
                            }
                        }
                    }else{

                    }
                    return  tableModel;
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        private void goToSearchedDiagramPage() {
            JFrame jFrame = FrameSetter.getjFrame();
            jFrame.setContentPane(new ModificaClassDiagramPage().getView());
            jFrame.validate();
            return;
        }
    }

    public JPanel getView() {
        return view;
    }

    public  VisualizzaClassDiagramPage(){

        VisualizzaClassDiagramPageController controller = new VisualizzaClassDiagramPageController();

        view = new JPanel();
        currentPackages = new JComboBox<>();
        searchBar = new JTextField("Inserisci il nome del Class Diagram");
        searchButton = new JButton(IconMaker.getSearchIcon());
        tablePane = new JScrollPane();
        errMessage = new JLabel();
        errMessageWrapper = new JPanel();
        prompt = new JLabel("in");
        errMessageWrapper.setVisible(false);
        errMessage.setVisible(false);

        errMessageWrapper.add(errMessage);
        errMessageWrapper.setBackground(ColorPicker.getColor("red"));

        currentPackages.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXX");
        currentPackages.addItem("");

        tablePane.setViewportView(classDiagramTable);

        controller.populatePackageList(currentPackages);


        SpringLayout layout = new SpringLayout();

        layout.putConstraint(SpringLayout.WEST, searchBar, FrameSetter.getjFrame().getWidth()/5, SpringLayout.WEST, view);
        layout.putConstraint(SpringLayout.NORTH, searchBar, 10, SpringLayout.NORTH,view);

        layout.putConstraint(SpringLayout.WEST, prompt, 10, SpringLayout.EAST, searchBar);
        layout.putConstraint(SpringLayout.WEST, currentPackages, 25 , SpringLayout.EAST, searchBar );
        layout.putConstraint(SpringLayout.WEST, searchButton, 10, SpringLayout.EAST, currentPackages);

        layout.putConstraint(SpringLayout.NORTH, prompt, 4, SpringLayout.NORTH, searchBar );
        layout.putConstraint(SpringLayout.NORTH, currentPackages, 0, SpringLayout.NORTH, searchBar );
        layout.putConstraint(SpringLayout.NORTH, searchButton, -3, SpringLayout.NORTH, searchBar );


        layout.putConstraint(SpringLayout.NORTH, errMessageWrapper, 5, SpringLayout.SOUTH, searchBar);
        layout.putConstraint(SpringLayout.WEST, errMessageWrapper, FrameSetter.getjFrame().getWidth()/2, SpringLayout.WEST, view);

        layout.putConstraint(SpringLayout.NORTH, tablePane, 10, SpringLayout.SOUTH, errMessageWrapper);
        layout.putConstraint(SpringLayout.WEST, tablePane, FrameSetter.getjFrame().getWidth()/5, SpringLayout.WEST, view);

        tablePane.setViewportView(classDiagramTable);

        view.add(currentPackages);
        view.add(searchBar);
        view.add(searchButton);
        view.add(tablePane);
        view.add(errMessage);
        view.add(errMessageWrapper);
        view.add(prompt);

        view.setLayout(layout);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
              searchButton.setEnabled(false);
              searchBar.setEnabled(false);
              tableModel =  controller.populateTable();
              classDiagramTable = new JTable(tableModel);
              tablePane.setViewportView(classDiagramTable);
              searchButton.setEnabled(true);
              searchBar.setEnabled(true);
              FrameSetter.getjFrame().validate();
            }
        });

        searchBar.addFocusListener(new FocusListener(){

            @Override
            public void focusGained(FocusEvent focusEvent) {
                controller.cleanSearchBarOnFocus();
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {

            }
        });
        searchBar.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if(keyEvent.getKeyCode()==KeyEvent.VK_ENTER){
                    searchButton.setEnabled(false);
                    searchBar.setEnabled(false);
                    tableModel =  controller.populateTable();
                    classDiagramTable = new JTable(tableModel);
                    tablePane.setViewportView(classDiagramTable);
                    searchButton.setEnabled(true);
                    searchBar.setEnabled(true);
                    FrameSetter.getjFrame().validate();
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });

    }

}
