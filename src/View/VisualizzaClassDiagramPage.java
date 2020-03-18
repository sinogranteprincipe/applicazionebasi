package View;

import Entity.Package.Package;
import Entity.Package.PackageDAO;
import View.Components.ColorPicker;
import View.Components.FrameSetter;
import View.Components.IconMaker;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
    private class VisualizzaClassDiagramPageController{

        private boolean areTherePackages;
        private List<Package> l;

        public void cleanSearchBarOnFocus(){
            searchBar.setText("");
            searchButton.setEnabled(true);
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

        public void goToSearchedDiagramPage() {
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
        classDiagramTable = new JTable();
        errMessage = new JLabel();
        errMessageWrapper = new JPanel();
        prompt = new JLabel("in");
        errMessageWrapper.setVisible(false);
        errMessage.setVisible(false);

        errMessageWrapper.add(errMessage);
        errMessageWrapper.setBackground(ColorPicker.getColor("red"));

        currentPackages.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXX");
        currentPackages.addItem("");

        controller.populatePackageList(currentPackages);

        searchButton.setEnabled(false);

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

        view.add(currentPackages);
        view.add(searchBar);
        view.add(searchButton);
        view.add(tablePane);
        view.add(errMessage);
        view.add(classDiagramTable);
        view.add(errMessageWrapper);
        view.add(prompt);

        view.setLayout(layout);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.goToSearchedDiagramPage();
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

    }

}
