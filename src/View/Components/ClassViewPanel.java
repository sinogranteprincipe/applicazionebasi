package View.Components;

import Entity.Associazione.Associazione;
import Entity.Associazione.AssociazioneDAO;
import Entity.Classe.Classe;
import Entity.Classe.ClasseDAO;
import Entity.Partecipa.Partecipa;
import Entity.Partecipa.PartecipaDAO;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;
public class ClassViewPanel {
    private class ClassViewPanelController{

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
                    associazioni.add(new JLabel(a.getNome()));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

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
    private JList<String> metodi;
    private JList<String> attributi;
    private JList<String> associazioni;
    private JLabel errMessage;

    private JPanel errMessageWrapper;
    private JScrollPane associazioniWrapper;
    private JPanel classdataWrapper;
    private JScrollPane metodiWrapper;
    private JScrollPane attributiWrapper;
    private JPanel buttonWrapper;

    private JButton aggiungiMetodo;
    private JButton aggiungiAttributo;
    private JButton rimuoviMetodo;
    private JButton rimuoviAttributo;

    public ClassViewPanel(Classe c){
        System.out.println("Entro");
        this.c= c;
        view = new JPanel();
        ClassViewPanelController controller = new ClassViewPanelController();
        classeName = new JLabel("Nome Classe:  " + c.getNome());
        visibilita = new JLabel("Visibilità:   " + c.getVisibilita().name().toLowerCase());
        rappresenta = new JLabel("Rappresenta:  " + c.getRappresenta().name().toLowerCase());
        tipoDiClasse = new JLabel("Tipo di Classe:  "+  c.getTipoClasse().name().toLowerCase());
        stereotipo = new JLabel("Stereotipo:  " + c.getStereotipo());
        rappresentaExplainer = new JLabel();
        associazioniLabel= new JLabel("Partecipa alle associazioni:");
        associazioni = new JList<>();
        associazioniWrapper = new JScrollPane();

        SpringLayout layout = new SpringLayout();

        attributiLabel = new JLabel("Attributi:");
        metodiLabel = new JLabel("Metodi:");
        metodi = new JList<>();
        attributi = new JList<>();
        aggiungiMetodo = new JButton("Aggiungi Metodo");
        aggiungiAttributo = new JButton("Aggiungi Attributo");
        rimuoviMetodo = new JButton("Rimuovi Metodo");
        rimuoviAttributo = new JButton("Rimuovi Attributo");

        classdataWrapper = new JPanel();
        metodiWrapper = new JScrollPane();
        attributiWrapper = new JScrollPane();
        buttonWrapper = new JPanel();

        classdataWrapper.add(classeName);
        classdataWrapper.add(visibilita);
        classdataWrapper.add(rappresenta);
        classdataWrapper.add(rappresentaExplainer);
        classdataWrapper.add(stereotipo);
        classdataWrapper.add(tipoDiClasse);

        metodiWrapper.add(metodiLabel);
        metodiWrapper.add(metodi);

        attributiWrapper.add(attributiLabel);
        attributiWrapper.add(attributi);

        associazioniWrapper.add(associazioniLabel);
        associazioniWrapper.add(associazioni);

        buttonWrapper.add(aggiungiMetodo);
        buttonWrapper.add(rimuoviMetodo);
        buttonWrapper.add(aggiungiAttributo);
        buttonWrapper.add(rimuoviAttributo);

        view.add(classdataWrapper);
        view.add(associazioniWrapper);
        view.add(attributiWrapper);
        view.add(metodiWrapper);
        view.add(buttonWrapper);

        if(rappresenta.getText()=="missing" || rappresenta.getText()==null){
            rappresenta.setVisible(false);
        }else{
            controller.populateRappresentaList(rappresenta.getText());
        }

        controller.populateAssociazioniList( c);

        classdataWrapper.setLayout(new BoxLayout(classdataWrapper, BoxLayout.Y_AXIS));
        view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));


        System.out.println("Esco");

    }

    public Classe getC() {
        return c;
    }

    public void setC(Classe c) {
        this.c = c;
    }

    public JPanel getView() {
        return view;
    }

    public void setView(JPanel view) {
        this.view = view;
    }

    public JLabel getClasseName() {
        return classeName;
    }

    public void setClasseName(JLabel classeName) {
        this.classeName = classeName;
    }

    public JLabel getVisibilita() {
        return visibilita;
    }

    public void setVisibilita(JLabel visibilita) {
        this.visibilita = visibilita;
    }

    public JLabel getRappresenta() {
        return rappresenta;
    }

    public void setRappresenta(JLabel rappresenta) {
        this.rappresenta = rappresenta;
    }

    public JLabel getTipoDiClasse() {
        return tipoDiClasse;
    }

    public void setTipoDiClasse(JLabel tipoDiClasse) {
        this.tipoDiClasse = tipoDiClasse;
    }

    public JLabel getStereotipo() {
        return stereotipo;
    }

    public void setStereotipo(JLabel stereotipo) {
        this.stereotipo = stereotipo;
    }

    public JLabel getRappresentaExplainer() {
        return rappresentaExplainer;
    }

    public void setRappresentaExplainer(JLabel rappresentaExplainer) {
        this.rappresentaExplainer = rappresentaExplainer;
    }


    public JLabel getAttributiLabel() {
        return attributiLabel;
    }

    public void setAttributiLabel(JLabel attributiLabel) {
        this.attributiLabel = attributiLabel;
    }

    public JLabel getMetodiLabel() {
        return metodiLabel;
    }

    public void setMetodiLabel(JLabel metodiLabel) {
        this.metodiLabel = metodiLabel;
    }

    public JList<String> getMetodi() {
        return metodi;
    }

    public void setMetodi(JList<String> metodi) {
        this.metodi = metodi;
    }

    public JList<String> getAttributi() {
        return attributi;
    }

    public void setAttributi(JList<String> attributi) {
        this.attributi = attributi;
    }

    public JPanel getClassdataWrapper() {
        return classdataWrapper;
    }

    public void setClassdataWrapper(JPanel classdataWrapper) {
        this.classdataWrapper = classdataWrapper;
    }

    public JScrollPane getMetodiWrapper() {
        return metodiWrapper;
    }

    public void setMetodiWrapper(JScrollPane metodiWrapper) {
        this.metodiWrapper = metodiWrapper;
    }

    public JScrollPane getAttributiWrapper() {
        return attributiWrapper;
    }

    public void setAttributiWrapper(JScrollPane attributiWrapper) {
        this.attributiWrapper = attributiWrapper;
    }

    public JPanel getButtonWrapper() {
        return buttonWrapper;
    }

    public void setButtonWrapper(JPanel buttonWrapper) {
        this.buttonWrapper = buttonWrapper;
    }

    public JButton getAggiungiMetodo() {
        return aggiungiMetodo;
    }

    public void setAggiungiMetodo(JButton aggiungiMetodo) {
        this.aggiungiMetodo = aggiungiMetodo;
    }

    public JButton getAggiungiAttributo() {
        return aggiungiAttributo;
    }

    public void setAggiungiAttributo(JButton aggiungiAttributo) {
        this.aggiungiAttributo = aggiungiAttributo;
    }

    public JButton getRimuoviMetodo() {
        return rimuoviMetodo;
    }

    public void setRimuoviMetodo(JButton rimuoviMetodo) {
        this.rimuoviMetodo = rimuoviMetodo;
    }

    public JButton getRimuoviAttributo() {
        return rimuoviAttributo;
    }

    public void setRimuoviAttributo(JButton rimuoviAttributo) {
        this.rimuoviAttributo = rimuoviAttributo;
    }
}
