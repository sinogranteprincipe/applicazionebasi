import Entity.Package.Package;
import Entity.Package.PackageDAO;
import Entity.Tipo.Tipo;
import Entity.Tipo.TipoDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws PrinterException {
        List<Package> packages;
        List<Tipo> types;
        JFrame hp = new JFrame("PROVA");
        hp.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        TextArea txt = new TextArea();
        hp.add(txt);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        hp.setSize(screenSize.width, screenSize.height);
        try {
            PackageDAO pDAO = new PackageDAO();
            packages = pDAO.readAll();
            TipoDAO tipoDAO = new TipoDAO();
            types = tipoDAO.readAllPrimitives();
            for(Package p: packages){
                    txt.append(p.toString() +"\n");
            }
            for(Tipo t: types){
                 txt.append(t.toString() +"\n");
            }
        }catch(SQLException e) {
            System.out.println(e.toString());
        }

        hp.setVisible(true);

        return;
    }
}
