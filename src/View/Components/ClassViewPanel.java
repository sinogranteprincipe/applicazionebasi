package View.Components;

import Entity.Associazione.Associazione;
import Entity.Associazione.AssociazioneDAO;
import Entity.Attributo.Attributo;
import Entity.Attributo.AttributoDAO;
import Entity.Classe.Classe;
import Entity.Classe.ClasseDAO;
import Entity.Metodo.Metodo;
import Entity.Metodo.MetodoDAO;
import Entity.Parametro.Parametro;
import Entity.Parametro.ParametroDAO;
import Entity.Partecipa.Partecipa;
import Entity.Partecipa.PartecipaDAO;
import Entity.Tipo.Tipo;
import Entity.Tipo.TipoDAO;
import View.NuovoPackagePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
public class ClassViewPanel {
    public class ClassViewPanelController{

        public void populateRappresentaList(String rappresenta) {
            AssociazioneDAO associazioneDAO;
            PartecipaDAO partecipaDAO;
            ClasseDAO classeDAO;
            List<Classe> classeList;
            List<Partecipa> partecipaList;
            List<Associazione> associazioneList;
            switch (rappresenta){
                case "AGGREGATA":
                    try {
                        partecipaDAO = new PartecipaDAO();
                        associazioneDAO = new AssociazioneDAO();
                        partecipaList = partecipaDAO.readAllOfClasse(c);
                        for(Partecipa p: partecipaList){
                             Associazione a = associazioneDAO.readById(p.getIdAssociazione());
                             if(a.getRaffigura().name().compareTo("AGGREGAZIONE")==0){
                                 rappresentaExplainer.setText("Classe aggregata in associazione "+ a.getNome());
                                 break;
                             }else{
                                 errMessage.setText("Impossibile trovare l'associazione per l'aggregazione");
                             }
                        }
                        rappresentaExplainer.setVisible(true);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "COMPOSTA":
                    try {
                        partecipaDAO = new PartecipaDAO();
                        associazioneDAO = new AssociazioneDAO();
                        partecipaList = partecipaDAO.readAllOfClasse(c);
                        for(Partecipa p: partecipaList){
                            Associazione a = associazioneDAO.readById(p.getIdAssociazione());
                            if(a.getRaffigura().name().compareTo("COMPOSTA")==0){
                                rappresentaExplainer.setText("Classe composta in associazione "+ a.getNome());
                                break;
                            }else{
                                errMessage.setText("Impossibile trovare l'associazione per la composizione");
                                errMessage.setVisible(true);
                                errMessageWrapper.setVisible(true);
                            }
                        }
                        rappresentaExplainer.setVisible(true);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

            }
        }

        public void populateAssociazioniList(Classe c) {
            try {
                AssociazioneDAO associazioneDAO= new AssociazioneDAO();
                PartecipaDAO partecipaDAO = new PartecipaDAO();
                List<Partecipa> l = partecipaDAO.readAllOfClasse(c);
                Associazione a;
                for(Partecipa p: l){
                    a = associazioneDAO.readById(p.getIdAssociazione());
                    String toAdd = a.getNome();
                    if(p.getRuoloClasse()!= null){
                        toAdd = toAdd + " con ruolo " + p.getRuoloClasse();
                    }
                    associazioniWrapper.add(new JLabel(toAdd));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        public void populateAttributiList(Classe c) {
            boolean areThereAttributes = false;
            try {
                List<Attributo> l;
                AttributoDAO attributoDAO = new AttributoDAO();
                l=attributoDAO.getAllAttributesInClass(c);
                TipoDAO tipoDAO = new TipoDAO();
                Tipo t;
                if(l.isEmpty()){
                   attributiWrapper.setVisible(false);
                }else {
                    for (Attributo a : l) {
                        t = tipoDAO.readTipoById(a.getIdTipo());
                        System.out.println(t.toString());
                        System.out.println(a.toString());
                        String label = a.getPosizione() + " :  " + a.getVisibilita().name().toLowerCase()  +" " +t.getNome() + " " + a.getNome();
                        attributiWrapper.add(new JLabel(label));
                    }
                    attributiLabel.setVisible(true);
                    FrameSetter.getjFrame().revalidate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                errMessage.setText("Impossibile connettersi al database");
                errMessage.setVisible(true);
                errMessageWrapper.setVisible(true);
            }

        }

        public void addAttributoPressed(Classe c) {
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

        public void addMetodoPressed(Classe c){
            JDialog addDiagramDialog = new JDialog(FrameSetter.getjFrame(), Dialog.ModalityType.APPLICATION_MODAL);
            addDiagramDialog.setContentPane(new NuovoMetodoPanel(c).getView());
            addDiagramDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            addDiagramDialog.setResizable(false);
            addDiagramDialog.setAlwaysOnTop(true);
            addDiagramDialog.setLocation((int) (dim.width/2.5),dim.height/7);
            addDiagramDialog.setSize(FrameSetter.getjFrame().getWidth()/3, FrameSetter.getjFrame().getHeight());
            System.out.println(addDiagramDialog.isVisible());
            addDiagramDialog.setVisible(true);
            System.out.println(addDiagramDialog.isVisible());

            addDiagramDialog.validate();
        }

        public void populateMetodiList(Classe c) {
            MetodoDAO metodoDAO;
            List<Metodo> metodos;
            TipoDAO tipoDAO;
            Tipo t;
            ParametroDAO parametroDAO;
            List<Parametro> parametros;
            try {
                metodoDAO = new MetodoDAO();
                tipoDAO = new TipoDAO();
                parametroDAO = new ParametroDAO();
                metodos = metodoDAO.getAllMethodsInClass(c);
                for(Metodo m: metodos){
                    t = tipoDAO.readTipoById(m.getIdTipoDiRitorno());
                    String toAdd = m.getPosizione() + " : " + m.getVisibilita().name().toLowerCase() + " " + t.getNome() + " " + m.getNome() + " (";
                    if(m.isHaParametri()){
                        parametros = parametroDAO.readAllParametersInMethod(m);
                        parametros.sort(new Comparator<Parametro>() {
                            @Override
                            public int compare(Parametro parametro, Parametro t1) {
                                if(parametro.getPosizione()>t1.getPosizione()){
                                    return 1;
                                }else if(parametro.getPosizione()<t1.getPosizione()){
                                    return -1;
                                }else{
                                    return 0;
                                }
                            }
                        });
                        for(Parametro p : parametros){
                            t = tipoDAO.readTipoById(p.getIdTipo());
                            toAdd = toAdd + t.getNome() + " " + p.getNome() + ", ";
                        }
                    }
                    toAdd = toAdd +");";
                    metodiWrapper.add(new JLabel(toAdd));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                errMessage.setText("Impossibile connettersi al database");
                errMessage.setVisible(true);
                errMessageWrapper.setVisible(true);
            }
        }
    }

    public ClassViewPanelController getController() {
        return controller;
    }

    private ClassViewPanelController controller;
    private Classe c;
    private JPanel view;
    private JLabel classeName;
    private JLabel visibilita;
    private JLabel rappresenta;
    private JLabel tipoDiClasse;
    private JLabel stereotipo;
    private JLabel rappresentaExplainer;
    private JLabel attributiLabel;
    private JLabel metodiLabel;
    private JLabel associazioniLabel;
    private JLabel errMessage;

    private JPanel errMessageWrapper;
    private JPanel associazioniWrapper;
    private JPanel classdataWrapper;
    private JPanel metodiWrapper;
    private JPanel attributiWrapper;
    private JPanel buttonWrapper;

    private JButton aggiungiMetodo;
    private JButton aggiungiAttributo;
    private JButton rimuoviMetodo;
    private JButton rimuoviAttributo;

    public ClassViewPanel(Classe c){
        this.c= c;
        view = new JPanel();

        controller = new ClassViewPanelController();
        classeName = new JLabel("Nome Classe:  " + c.getNome());
        visibilita = new JLabel("Visibilità:   " + c.getVisibilita().name().toLowerCase());
        rappresenta = new JLabel("Rappresenta:  " + c.getRappresenta().name().toLowerCase());
        tipoDiClasse = new JLabel("Tipo di Classe:  "+  c.getTipoClasse().name().toLowerCase());
        stereotipo = new JLabel("Stereotipo:  " + c.getStereotipo());
        rappresentaExplainer = new JLabel();
        associazioniLabel= new JLabel("Partecipa alle associazioni:");
        associazioniWrapper = new JPanel();

        SpringLayout layout = new SpringLayout();

        attributiLabel = new JLabel("Attributi:");
        metodiLabel = new JLabel("Metodi:");

        aggiungiMetodo = new JButton("Aggiungi Metodo");
        aggiungiAttributo = new JButton("Aggiungi Attributo");
        rimuoviMetodo = new JButton("Rimuovi Metodo");
        rimuoviAttributo = new JButton("Rimuovi Attributo");

        classdataWrapper = new JPanel();
        metodiWrapper = new JPanel();
        attributiWrapper = new JPanel();
        buttonWrapper = new JPanel();

        classdataWrapper.add(classeName);
        classdataWrapper.add(visibilita);
        classdataWrapper.add(rappresenta);
        classdataWrapper.add(rappresentaExplainer);
        classdataWrapper.add(stereotipo);
        classdataWrapper.add(tipoDiClasse);

        metodiWrapper.add(metodiLabel);

        attributiWrapper.add(attributiLabel);

        associazioniWrapper.add(associazioniLabel);

        buttonWrapper.add(aggiungiMetodo);
        buttonWrapper.add(rimuoviMetodo);
        buttonWrapper.add(aggiungiAttributo);
        buttonWrapper.add(rimuoviAttributo);

        view.add(classdataWrapper);
        view.add(associazioniWrapper);
        view.add(attributiWrapper);
        view.add(metodiWrapper);
        view.add(buttonWrapper);

        if(rappresenta.getText().compareTo("missing")==0 || rappresenta.getText()==null){
            rappresenta.setVisible(false);
        }else{
            controller.populateRappresentaList(rappresenta.getText());
        }

        controller.populateMetodiList(c);
        controller.populateAssociazioniList( c);
        controller.populateAttributiList(c);
        classdataWrapper.setLayout(new BoxLayout(classdataWrapper, BoxLayout.Y_AXIS));
        view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
    }

    public Classe getC() {
        return c;
    }


    public JPanel getView() {
        return view;
    }


    public JLabel getClasseName() {
        return classeName;
    }


    public JLabel getVisibilita() {
        return visibilita;
    }


    public JLabel getRappresenta() {
        return rappresenta;
    }


    public JLabel getTipoDiClasse() {
        return tipoDiClasse;
    }


    public JLabel getStereotipo() {
        return stereotipo;
    }


    public JLabel getRappresentaExplainer() {
        return rappresentaExplainer;
    }

    public JLabel getAttributiLabel() {
        return attributiLabel;
    }


    public JLabel getMetodiLabel() {
        return metodiLabel;
    }



    public JPanel getClassdataWrapper() {
        return classdataWrapper;
    }


    public JPanel getMetodiWrapper() {
        return metodiWrapper;
    }


    public JPanel getAttributiWrapper() {
        return attributiWrapper;
    }


    public JPanel getButtonWrapper() {
        return buttonWrapper;
    }


    public JButton getAggiungiMetodo() {
        return aggiungiMetodo;
    }


    public JButton getAggiungiAttributo() {
        return aggiungiAttributo;
    }


    public JButton getRimuoviMetodo() {
        return rimuoviMetodo;
    }


    public JButton getRimuoviAttributo() {
        return rimuoviAttributo;
    }

}
