package View.Components;

import Entity.Attributo.Attributo;
import Entity.Attributo.AttributoDAO;
import Entity.ClassDiagram.ClassDiagram;
import Entity.ClassDiagram.ClassDiagramDAO;
import Entity.ClassDiagramRiferimento.ClassDiagramRiferimento;
import Entity.ClassDiagramRiferimento.ClassDiagramRiferimentoDAO;
import Entity.Classe.Classe;
import Entity.Package.Package;
import Entity.Package.PackageDAO;
import Entity.Tipo.Tipo;
import Entity.Tipo.TipoDAO;
import Entity.TipoDiVisibilita;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class NuovoAttributoPanel {

    private List<Tipo> primitiviList;
    private TreeSet<Tipo> strutturati;
    private final JComboBox<String> tipiPrimitivi;
    private final JLabel primitivo;

    private class NuovoAttributoPanelController{

        public void populateVisibilita() {
            for(TipoDiVisibilita i : TipoDiVisibilita.values()){
                if(i.name().compareTo("MISSING")!=0){
                    visibilita.addItem(i.name());
                }
            }
        }

        public Tipo getSelectedTipo(){
            String primitive = (String) tipiPrimitivi.getSelectedItem();
            String structured = (String) tipiDisponibili.getSelectedItem();
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
                        for(Tipo t: primitiviList){
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
        public void createAttribute(Classe c, Tipo t){
            try {
                AttributoDAO attributoDAO = new AttributoDAO();
                List<Attributo> present = attributoDAO.readAllInClasse(c);
                int pos = 1;
                for(Attributo a: present){
                    pos++;
                }

                Attributo toAdd = new Attributo(-1, nameField.getText(), t.getId(), Entity.TipoDiVisibilita.getTipoDiVisibilitaByName((String) visibilita.getSelectedItem()), minValue.getText(), maxValue.getText(), defaultValue.getText(), c.getId(),pos);
                if(attributoDAO.createAttribute(toAdd)){
                    JDialog parent = (JDialog) SwingUtilities.getWindowAncestor(view);
                    parent.dispose();
                }else{
                    errMessage.setText("Errore nell'inserimento dell'attributo.");
                    errMessageWrapper.setVisible(true);
                    errMessage.setVisible(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
                primitiviList = tipoDAO.readAllPrimitives();
                for(Tipo k: primitiviList){
                    tipiPrimitivi.addItem(k.getNome());
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
                    tipiDisponibili.addItem(r.getNome());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    Classe c;
    JPanel view;
    JLabel nameLabel;
    JTextField nameField;
    JLabel visibilitaLabel;
    JComboBox<String> visibilita;
    JLabel minValueLabel;
    JTextField minValue;
    JLabel maxValueLabel;
    JTextField maxValue;
    JLabel stereotipoLabel;
    JTextField stereotipo;
    JLabel tipoLabel;
    JLabel defaultValueLabel;
    JTextField defaultValue;
    JLabel errMessage;
    JPanel errMessageWrapper;
    JButton add;
    JComboBox<String> tipiDisponibili;

    public NuovoAttributoPanel(Classe c){
        this.c = c;
        NuovoAttributoPanelController controller = new NuovoAttributoPanelController();
        strutturati =  new TreeSet<>(new Comparator<Tipo>() {
            @Override
            public int compare(Tipo tipo, Tipo t1) {
                if(tipo.getNome().compareTo(t1.getNome())<0)
                    return -1;
                else if(tipo.getNome().compareTo(t1.getNome())>0)
                    return 1;
                else
                    return 0;
            }
        });
        primitiviList = new ArrayList<>();
        view = new JPanel();
        nameLabel = new JLabel("Inserisci il nome:");
        nameField = new JTextField("", 20);
        visibilitaLabel = new JLabel("Scegli la visibilità:");
        visibilita = new JComboBox<>();
        visibilita.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXX");
        minValueLabel = new JLabel("Inserisci il valore minimo:");
        minValue = new JTextField("", 20);
        maxValueLabel = new JLabel("Inserisci il valore massimo:");
        maxValue = new JTextField("",20);
        stereotipoLabel = new JLabel("Inserisi uno stereotipo:");
        stereotipo = new JTextField("", 20);
        primitivo = new JLabel("Scegli tra i tipi primitivi disponibili:");
        tipiPrimitivi = new JComboBox<String>();
        tipiPrimitivi.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXX");
        tipoLabel = new JLabel("Scegli tra i tipi strutturati disponibili:");
        tipiDisponibili = new JComboBox<>();
        tipiDisponibili.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXX");
        defaultValueLabel= new JLabel("Inserisci un valore di dafault");
        defaultValue = new JTextField("",20);
        add= new JButton("Aggiungi");
        errMessage = new JLabel();
        errMessageWrapper = new JPanel();
        errMessageWrapper.setVisible(false);
        errMessage.setVisible(false);
        errMessageWrapper.add(errMessage);
        errMessageWrapper.setBackground(ColorPicker.getColor("red"));

        tipiPrimitivi.addItem("");
        tipiDisponibili.addItem("");
        controller.populateVisibilita();
        controller.populateTipi(c);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Tipo t = controller.getSelectedTipo();
                if(t == null){
                    errMessage.setText("Errore nella selezione del tipo");
                    errMessage.setVisible(true);
                    errMessageWrapper.setVisible(true);
                }else{
                    controller.createAttribute(c,t);
                }
            }
        });

        view.add(nameLabel);
        view.add(nameField);
        view.add(visibilitaLabel);
        view.add(visibilita);
        view.add(minValueLabel);
        view.add(minValue);
        view.add(maxValueLabel);
        view.add(maxValue);
        view.add(stereotipoLabel);
        view.add(stereotipo);
        view.add(defaultValueLabel);
        view.add(defaultValue);
        view.add(primitivo);
        view.add(tipiPrimitivi);
        view.add(tipoLabel);
        view.add(tipiDisponibili);
        view.add(errMessageWrapper);
        view.add(add);

    }

    public Classe getC() {
        return c;
    }

    public JPanel getView() {
        return view;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JLabel getVisibilitaLabel() {
        return visibilitaLabel;
    }

    public JComboBox<String> getVisibilita() {
        return visibilita;
    }

    public JLabel getMinValueLabel() {
        return minValueLabel;
    }

    public JTextField getMinValue() {
        return minValue;
    }

    public JLabel getMaxValueLabel() {
        return maxValueLabel;
    }

    public JTextField getMaxValue() {
        return maxValue;
    }

    public JLabel getStereotipoLabel() {
        return stereotipoLabel;
    }

    public JTextField getStereotipo() {
        return stereotipo;
    }

    public JLabel getTipoLabel() {
        return tipoLabel;
    }

    public JLabel getDefaultValueLabel() {
        return defaultValueLabel;
    }

    public JTextField getDefaultValue() {
        return defaultValue;
    }

    public JButton getAdd() {
        return add;
    }

    public JComboBox<String> getTipiDisponibili() {
        return tipiDisponibili;
    }
}
