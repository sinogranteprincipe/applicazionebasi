package View;

import Entity.Package.Package;
import Entity.Package.PackageDAO;
import View.Components.FrameSetter;
import View.Components.IconMaker;
import com.formdev.flatlaf.ui.FlatTableCellBorder;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.scene.control.TableCell;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.*;
import java.util.List;
import java.util.ListIterator;

public class VisualizzaPackagesPage {

    List<Package> packages = new ArrayList<>();
    JTable table;
    JPanel view;
    JScrollPane tableContainer;
    JPanel buttonContainer;
    JButton addDiagram;
    JButton addPackage;
    DefaultTableModel dataModel;

    private class VisualizzaPackagesPageController{

        PackageDAO packageDAO;


        public void populateTable(DefaultTableModel dataModel){
            dataModel.addColumn("Nome");
            dataModel.addColumn("Commento");

            try{
                packageDAO = new PackageDAO();
                packages = packageDAO.readAll();
                for(Package p: packages){
                    if(p.getId()!=0) {
                        dataModel.addRow(new String[]{p.getNome(), p.getCommento()});
                    }
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        public void modifyTableLook() {
            TableColumn column;
            DefaultTableCellRenderer tableCellRenderer;
            int i = 0, j = 0;
            for(i = 0;i <table.getColumnCount(); i++){
                column = table.getColumnModel().getColumn(i);
                column.setResizable(false);
               if(i==0){
                   column.setPreferredWidth(FrameSetter.getjFrame().getWidth()/4);
               }else if(i == 1){
                   column.setPreferredWidth(FrameSetter.getjFrame().getWidth()-FrameSetter.getjFrame().getWidth()/4);
               }
            }
        }
    }


    public JPanel getView() {

        return view;
    }

    public VisualizzaPackagesPage(){
        VisualizzaPackagesPageController controller = new VisualizzaPackagesPageController();

        view = new JPanel();
        tableContainer = new JScrollPane();
        buttonContainer = new JPanel();
        addDiagram = new JButton("Aggiungi Class Diagram");
        addPackage = new JButton("Aggiungi Package");

        SpringLayout layout = new SpringLayout();

        buttonContainer.add(addDiagram);
        buttonContainer.add(addPackage);

        addDiagram.setEnabled(false);

        buttonContainer.setVisible(true);

        addDiagram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JDialog addDiagramDialog = new JDialog(FrameSetter.getjFrame(), Dialog.ModalityType.APPLICATION_MODAL);
                int row = table.getSelectedRow();
                String p =(String) table.getValueAt(row, 0);
                addDiagramDialog.setContentPane(new NuovoClassDiagramPage(p).getView());
                addDiagramDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                addDiagramDialog.setResizable(false);
                addDiagramDialog.setAlwaysOnTop(true);
                addDiagramDialog.setLocation((int) (dim.width/2.5),dim.height/7);
                addDiagramDialog.setSize(FrameSetter.getjFrame().getWidth()/3, FrameSetter.getjFrame().getHeight());
                addDiagramDialog.setVisible(true);
            }
        });


        dataModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        controller.populateTable(dataModel);

        table = new JTable(dataModel){
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (c instanceof JComponent) {
                        JComponent jc = (JComponent) c;
                        jc.setToolTipText("<html><p width=\"500\">" +getValueAt(row, column).toString()+"</p></html>");
                }
                return c;
            }
        };



        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(addDiagram.isEnabled() == false){
                    addDiagram.setEnabled(true);
                }
            }
        });

        controller.modifyTableLook();
        tableContainer.setViewportView(table);

        addPackage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JDialog addDiagramDialog = new JDialog(FrameSetter.getjFrame(), Dialog.ModalityType.APPLICATION_MODAL);
                int row = table.getSelectedRow();
                addDiagramDialog.setContentPane(new NuovoPackagePage("popup").getView());
                addDiagramDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                addDiagramDialog.setResizable(false);
                addDiagramDialog.setAlwaysOnTop(true);
                addDiagramDialog.setLocation((int) (dim.width/2.5),dim.height/7);
                addDiagramDialog.setSize(FrameSetter.getjFrame().getWidth()/3, FrameSetter.getjFrame().getHeight());
                addDiagramDialog.addWindowListener(new WindowListener() {
                    @Override
                    public void windowOpened(WindowEvent windowEvent) {

                    }

                    @Override
                    public void windowClosing(WindowEvent windowEvent) {

                    }

                    @Override
                    public void windowClosed(WindowEvent windowEvent) {
                        dataModel = new DefaultTableModel(){
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };
                        controller.populateTable(dataModel);
                        table = new JTable(dataModel);
                        controller.modifyTableLook();
                        tableContainer.setViewportView(table);
                        FrameSetter.getjFrame().validate();
                    }

                    @Override
                    public void windowIconified(WindowEvent windowEvent) {

                    }

                    @Override
                    public void windowDeiconified(WindowEvent windowEvent) {

                    }

                    @Override
                    public void windowActivated(WindowEvent windowEvent) {

                    }

                    @Override
                    public void windowDeactivated(WindowEvent windowEvent) {

                    }
                });
                addDiagramDialog.setVisible(true);
            }
        });

        layout.putConstraint(SpringLayout.NORTH, tableContainer, 10, SpringLayout.NORTH, view);
        layout.putConstraint(SpringLayout.NORTH, buttonContainer, 10, SpringLayout.SOUTH, tableContainer);
        layout.putConstraint(SpringLayout.WEST, tableContainer, FrameSetter.getjFrame().getWidth()/4,SpringLayout.WEST ,view);
        layout.putConstraint(SpringLayout.WEST, buttonContainer,FrameSetter.getjFrame().getWidth()/13, SpringLayout.WEST, tableContainer);

        view.add(tableContainer);
        view.add(buttonContainer);

        view.setLayout(layout);

        view.setVisible(true);

    }

}
