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
import Entity.TipoDiVisibilita;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NuovoMetodoPanel {
    private JPanel view;
    private JLabel nomeLabel;
    private JTextField nome;
    private JLabel visibilitaLabel;
    private JComboBox<String> visibilita;
    private JLabel hasReturnTypeLabel;
    private JCheckBox hasReturnType;
    private JLabel primitiviTypeLabel;
    private JComboBox<String> selectPrimitiveType;
    private JLabel structuredTypeLabel;
    private JComboBox<String> selectStructuredType;
    private JCheckBox hasParameters;
    private JButton add;
    private JLabel errMessage;
    private JPanel errMessageWrapper;
    private  JLabel hasParametersLabel;

    private Classe c;
    private List<Tipo> primitivi;
    private List<Tipo> strutturati;


    private class NuovoMetodoPanelController{

        public void populateVisibilita() {
            for(TipoDiVisibilita i : TipoDiVisibilita.values()){
                if(i.name().compareTo("MISSING")!=0){
                    visibilita.addItem(i.name());
                }
            }
        }

        public void toggleTypesList(){
            if(selectPrimitiveType.isEnabled() && selectStructuredType.isEnabled()){
                selectStructuredType.setEnabled(false);
                selectPrimitiveType.setEnabled(false);
            }else{
                selectStructuredType.setEnabled(true);
                selectPrimitiveType.setEnabled(true);
            }

        }


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

        public void addMethod(Classe c) {
            Tipo t;
            int pos = 0;
            List<Metodo> metodos;
            Metodo toAdd;
            try {
                t = getSelectedTipo();
                MetodoDAO metodoDAO = new MetodoDAO();
                metodos = metodoDAO.getAllMethodsInClass(c);
                for (Metodo m : metodos) {
                    pos++;
                }
                if(hasReturnType.isSelected()) {
                  toAdd  = new Metodo(-1, nome.getText(), hasParameters.isSelected(), t.getId(), TipoDiVisibilita.getTipoDiVisibilitaByName((String)visibilita.getSelectedItem()), c.getId(), pos+1);
                }else{
                  toAdd  = new Metodo(-1, nome.getText(), hasParameters.isSelected(), 0, TipoDiVisibilita.getTipoDiVisibilitaByName((String)visibilita.getSelectedItem()), c.getId(), pos+1);
                }
                if(metodoDAO.createMethod(toAdd)){
                    JDialog parent = (JDialog) SwingUtilities.getWindowAncestor(view);
                    parent.dispose();
                    if(hasParameters.isSelected()){
                        JDialog addDiagramDialog = new JDialog(FrameSetter.getjFrame(), Dialog.ModalityType.APPLICATION_MODAL);
                        addDiagramDialog.setContentPane(new NuovoAttributoPanel(c).getView());
                        addDiagramDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                        addDiagramDialog.setResizable(false);
                        addDiagramDialog.setAlwaysOnTop(true);
                        addDiagramDialog.setLocation((int) (dim.width/2.5),dim.height/7);
                        addDiagramDialog.setSize(FrameSetter.getjFrame().getWidth()/3, FrameSetter.getjFrame().getHeight());
                        System.out.println(addDiagramDialog.isVisible());
                        addDiagramDialog.setVisible(true);
                        addDiagramDialog.validate();
                    }
                }else{
                    errMessage.setText("Errore nell'inserimento del metodo.");
                    errMessageWrapper.setVisible(true);
                    errMessage.setVisible(true);
                }
            } catch (SQLException e) {
                errMessage.setText("Errore nella connessione al database");
                errMessageWrapper.setVisible(true);
                errMessage.setVisible(true);
                e.printStackTrace();
            }
        }
    }

    public NuovoMetodoPanel(Classe c) {
        this.c = c;
        NuovoMetodoPanelController controller = new NuovoMetodoPanelController();
        primitivi = new ArrayList<>();
        strutturati = new ArrayList<>();
        view = new JPanel();
        nomeLabel = new JLabel("Inserisci il nome:");
        nome = new JTextField("", 20);
        visibilitaLabel = new JLabel("Inserisci la visibilità:                    ");
        visibilita = new JComboBox<>();
        hasReturnTypeLabel = new JLabel("Ha tipo di ritorno?"                         );
        hasReturnType = new JCheckBox();
        primitiviTypeLabel = new JLabel("Scegli un tipo primitivo:                      ");
        selectPrimitiveType = new JComboBox<>();
        structuredTypeLabel = new JLabel("Scegli un tipo strutturato:                   ");
        selectStructuredType = new JComboBox<>();
        hasParameters = new JCheckBox();
        add = new JButton("Aggiungi Metodo");
        errMessage = new JLabel();
        errMessageWrapper = new JPanel();
        hasParametersLabel = new JLabel("Il metodo ha parametri?");
        selectPrimitiveType.setPrototypeDisplayValue ("XXXXXXXXXXXXXXXXXXXXXXXXXXX");
        selectStructuredType.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXX");
        selectStructuredType.setEnabled(false);
        selectPrimitiveType.setEnabled(false);
        errMessageWrapper.setBackground(ColorPicker.getColor("red"));
        errMessageWrapper.add(errMessage);
        errMessageWrapper.setVisible(false);
        errMessage.setVisible(false);
        selectPrimitiveType.addItem("");
        selectStructuredType.addItem("");
        hasReturnType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.toggleTypesList();
                System.out.println("Entro?");
            }
        });

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.addMethod(c);
            }
        });
        controller.populateTipi(c);
        controller.populateVisibilita();
        view.add(nomeLabel);
        view.add(nome);
        view.add(visibilitaLabel);
        view.add(visibilita);
        view.add(hasReturnTypeLabel);
        view.add(hasReturnType);
        view.add(primitiviTypeLabel);
        view.add(selectPrimitiveType);
        view.add(structuredTypeLabel);
        view.add(selectStructuredType);
        view.add(hasParametersLabel);
        view.add(hasParameters);
        view.add(add);
        view.add(errMessageWrapper);
    }

    public JPanel getView() {
        return view;
    }
}
