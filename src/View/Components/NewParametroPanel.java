package View.Components;

import Entity.ClassDiagram.ClassDiagram;
import Entity.ClassDiagram.ClassDiagramDAO;
import Entity.ClassDiagramRiferimento.ClassDiagramRiferimento;
import Entity.ClassDiagramRiferimento.ClassDiagramRiferimentoDAO;
import Entity.Classe.Classe;
import Entity.Metodo.Metodo;
import Entity.Metodo.MetodoDAO;
import Entity.Package.Package;
import Entity.Package.PackageDAO;
import Entity.Tipo.Tipo;
import Entity.Tipo.TipoDAO;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewParametroPanel {
    private class NewParametroPanelController{
        public Tipo getSelectedTipo(){
            String primitive = (String) selectPrimitiveType.getSelectedItem();
            String structured = (String) selectStructuredType.getSelectedItem();
            Tipo res = null;
            if(primitive.compareTo("")!=0 && structured.compareTo("")!=0){
                errMessage.setText("Seleziona solo uno tipo.");
                errMessage.setVisible(true);
                errMessageWrapper.setVisible(true);
                return null;
            }else{
                try{
                    TipoDAO tipoDAO = new TipoDAO();
                    if(primitive.compareTo("")!=0){
                        for(Tipo t: primitivi){
                            if(t.getNome().compareTo(primitive)==0){
                                res = t;
                            }
                        }
                        return  res;
                    }else if(structured.compareTo("")!=0){
                        for(Tipo t: strutturati){
                            if(t.getNome().compareTo(structured)==0){
                                res=t;
                            }
                        }
                        return res;
                    }else{
                        errMessage.setText("Seleziona un tipo");
                        errMessage.setVisible(true);
                        errMessageWrapper.setVisible(true);
                    }
                }catch (SQLException e){
                    errMessage.setText("Impossibile connettersi al database.");
                    errMessage.setVisible(true);
                    errMessageWrapper.setVisible(true);
                }
            }
            return res;
        }


        public void populateTipi(Classe c) {
            List<Tipo> t = new ArrayList<>();

            List<ClassDiagramRiferimento> packagesrif;
            try{
                TipoDAO tipoDAO = new TipoDAO();
                ClassDiagram cd;
                List<ClassDiagram> allOfPackage;
                ClassDiagramDAO classDiagramDAO = new ClassDiagramDAO();
                cd = classDiagramDAO.readById(c.getIdClassDiagram());
                packagesrif = new ClassDiagramRiferimentoDAO().readAllPackagesOfAClassDiagram(cd);
                PackageDAO packageDAO = new PackageDAO();
                Package p;
                primitivi = tipoDAO.readAllPrimitives();
                for(Tipo k: primitivi){
                    if(k.getId()!=0){
                        selectPrimitiveType.addItem(k.getNome());
                    }
                }
                for(ClassDiagramRiferimento cdrif: packagesrif){
                    p = packageDAO.readPackageById(cdrif.getIdPackage());
                    allOfPackage = classDiagramDAO.readAllInPackage(p);
                    for(ClassDiagram i: allOfPackage){
                        List<Tipo> alistoftypes = tipoDAO.readAllInClassDiagram(i);
                        for(Tipo tmp: alistoftypes){
                            t.add(tmp);
                            System.out.println(tmp.toString());
                        }
                    }
                }
                for(Tipo a: t){
                    strutturati.add(a);
                }
                for(Tipo r: strutturati){
                    selectStructuredType.addItem(r.getNome());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    private List<Tipo> primitivi;
    private List<Tipo> strutturati;
    private JLabel primitiviTypeLabel;
    private JComboBox<String> selectPrimitiveType;
    private JLabel structuredTypeLabel;
    private JComboBox<String> selectStructuredType;
    private JLabel errMessage;
    private JPanel errMessageWrapper;
    Classe c;
    Metodo m;

    public NewParametroPanel(Classe c){
        c = this.c;
        try {
            m = new MetodoDAO().readLastInserted();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
