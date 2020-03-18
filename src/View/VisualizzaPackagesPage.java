package View;

import Entity.Package.Package;
import Entity.Package.PackageDAO;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class VisualizzaPackagesPage {
    JPanel view;
    JTable table;
    JScrollPane tableScrollPane;

    private class VisualizzaPackagesPageController{
        PackageDAO packageDAO;
        List<Package> l;

        public void populateTable(DefaultTableModel dataModel){
            try{
                packageDAO = new PackageDAO();
                l = packageDAO.readAll();
                for(Package p: l){
                    dataModel.addRow(new String[]{p.getNome(), p.getCommento()});

                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public JPanel getView() {
        return view;
    }

    public VisualizzaPackagesPage(){
        VisualizzaPackagesPageController controller = new VisualizzaPackagesPageController();
        DefaultTableModel dataModel = new DefaultTableModel();
        controller.populateTable(dataModel);
        view = new JPanel();
        table = new JTable(dataModel);
        tableScrollPane = new JScrollPane();
        tableScrollPane.add(table);
        view.setVisible(true);



    }
}
