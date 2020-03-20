package View.Components;

import Entity.Associazione.Associazione;
import Entity.Associazione.AssociazioneDAO;
import Entity.ClassDiagram.ClassDiagram;
import Entity.Classe.Classe;
import Entity.Classe.ClasseDAO;
import Entity.RuoloAssociazione;
import Entity.TipoDiClasse;
import Entity.TipoDiVisibilita;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;

public class ModifyClasseOptionPane {

    ClassDiagram ofClassDiagram;
    ModifyClasseOptionPaneController controller;

    JPanel view;
    JLabel nomeLabel;
    JTextField nome;
    JLabel stereotipoLabel;
    JTextField stereotipo;
    JLabel visibilitaLabel;
    JComboBox<String> visibilita;
    JLabel rappresentaLabel;
    JComboBox<String> rappresenta;
    JLabel prompt;
    JComboBox<String> associazioni;
    JLabel tipoDiClasseLabel;
    JComboBox<String> tipoDiClasse;
    JLabel errMessage;
    JCheckBox hasSuperClasses;

    JPanel wrapperForNome;
    JPanel wrapperForStereotipo;
    JPanel wrapperForVisibilita;
    JPanel wrapperForRappresenta;
    JPanel wrapperForTipoDiClasse;
    JPanel wrapperForErrMessage;

    JButton aggiungi;

    public JPanel getView() {
        return view;
    }

    private class ModifyClasseOptionPaneController{

        private void cleanOnFocus(JTextField a){
            a.setText("");
            return;
        }

        private void populateConstantComboBoxes() {
            for(TipoDiClasse i : TipoDiClasse.values()){
                if(i.name().compareTo("MISSING")!=0){
                    tipoDiClasse.addItem(i.name());
                }
            }
            for(RuoloAssociazione i : RuoloAssociazione.values()){
                if(i.name().compareTo("MISSING")!=0){
                    rappresenta.addItem(i.name());
                }
            }
            for(TipoDiVisibilita i : TipoDiVisibilita.values()){
                if(i.name().compareTo("MISSING")!=0){
                    visibilita.addItem(i.name());
                }
            }
            return;
        }

        private void populateAssociationsComboBoxes(ClassDiagram cd){
            List<Associazione> associaziones;
            AssociazioneDAO associazioneDAO;

            try {
                associazioneDAO =new AssociazioneDAO();
                associaziones = associazioneDAO.readAllInClassDiagram(cd);
                for(Associazione a: associaziones){
                    associazioni.addItem(a.toString());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public ModifyClasseOptionPane(ClassDiagram ofClassDiagram){
        this.ofClassDiagram = ofClassDiagram;
        controller = new ModifyClasseOptionPaneController();
        view = new JPanel();
        nomeLabel= new JLabel("Inserisci il nome:");
        nome = new JTextField("Max 300 caratteri", 30);
        stereotipoLabel = new JLabel("Inserisci lo stereotipo:");
        stereotipo = new JTextField("Max 200 caratteri", 30);
        visibilitaLabel = new JLabel("Seleziona la visbilita:");
        visibilita = new JComboBox<>();
        rappresentaLabel = new JLabel("Seleziona un ruolo d'associazione:");
        rappresenta = new JComboBox<>();
        prompt = new JLabel("in associazione:");
        associazioni = new JComboBox<>();
        tipoDiClasseLabel=new JLabel("Seleziona il tipo di classe");
        tipoDiClasse = new JComboBox<>();
        hasSuperClasses = new JCheckBox("Ha superclassi?");

        hasSuperClasses.setHorizontalTextPosition(SwingConstants.LEFT);

        aggiungi = new JButton("Aggiungi");
        errMessage = new JLabel();

          visibilita.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
         rappresenta.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        tipoDiClasse.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        associazioni.setPrototypeDisplayValue("XXXXXXXXXXXXXXXX");

        nome.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                controller.cleanOnFocus(nome);
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {

            }
        });
        stereotipo.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                controller.cleanOnFocus(stereotipo);
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {

            }
        });

        visibilita.addItem("");
        rappresenta.addItem("");
        tipoDiClasse.addItem("");

        controller.populateConstantComboBoxes();
        controller.populateAssociationsComboBoxes(ofClassDiagram);

        wrapperForErrMessage = new JPanel();
        wrapperForErrMessage.setBackground(ColorPicker.getColor("red"));

        errMessage.setVisible(false);
        wrapperForErrMessage.setVisible(false);

        wrapperForErrMessage.add(errMessage);

        wrapperForNome= new JPanel();
        wrapperForNome.add(nomeLabel);
        wrapperForNome.add(nome);

        wrapperForStereotipo = new JPanel();
        wrapperForStereotipo.add(stereotipoLabel);
        wrapperForStereotipo.add(stereotipo);

        wrapperForVisibilita = new JPanel();
        wrapperForVisibilita.add(visibilitaLabel);
        wrapperForVisibilita.add(visibilita);

        wrapperForTipoDiClasse=new JPanel();
        wrapperForTipoDiClasse.add(tipoDiClasseLabel);
        wrapperForTipoDiClasse.add(tipoDiClasse);

        wrapperForRappresenta = new JPanel();
        wrapperForRappresenta.add(rappresentaLabel);
        wrapperForRappresenta.add(rappresenta);
        wrapperForRappresenta.add(prompt);
        wrapperForRappresenta.add(associazioni);

        view.setVisible(true);
        nomeLabel.setVisible(true);
        nome.setVisible(true);
        stereotipoLabel.setVisible(true);
        stereotipo.setVisible(true);
        visibilitaLabel.setVisible(true);
        visibilita.setVisible(true);
        rappresentaLabel.setVisible(true);
        rappresenta.setVisible(true);
        prompt.setVisible(true);
        associazioni.setVisible(true);
        tipoDiClasseLabel.setVisible(true);
        tipoDiClasse.setVisible(true);


        wrapperForNome.setVisible(true);
        wrapperForStereotipo.setVisible(true);
        wrapperForVisibilita.setVisible(true);
        wrapperForRappresenta.setVisible(true);
        wrapperForTipoDiClasse.setVisible(true);

        aggiungi.setVisible(true);

        view.add(wrapperForErrMessage);
        view.add(wrapperForNome);
        view.add(wrapperForStereotipo);
        view.add(wrapperForVisibilita);
        view.add(wrapperForTipoDiClasse);
        view.add(wrapperForRappresenta);
        view.add(hasSuperClasses);
        view.add(aggiungi);

        view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));

    }

    public ClassDiagram getOfClassDiagram() {
        return ofClassDiagram;
    }

    public ModifyClasseOptionPaneController getController() {
        return controller;
    }

    public JLabel getNomeLabel() {
        return nomeLabel;
    }

    public JTextField getNome() {
        return nome;
    }

    public JLabel getStereotipoLabel() {
        return stereotipoLabel;
    }

    public JTextField getStereotipo() {
        return stereotipo;
    }

    public JLabel getVisibilitaLabel() {
        return visibilitaLabel;
    }

    public JComboBox<String> getVisibilita() {
        return visibilita;
    }

    public JLabel getRappresentaLabel() {
        return rappresentaLabel;
    }

    public JComboBox<String> getRappresenta() {
        return rappresenta;
    }

    public JLabel getPrompt() {
        return prompt;
    }

    public JComboBox<String> getAssociazioni() {
        return associazioni;
    }

    public JLabel getTipoDiClasseLabel() {
        return tipoDiClasseLabel;
    }

    public JComboBox<String> getTipoDiClasse() {
        return tipoDiClasse;
    }

    public JLabel getErrMessage() {
        return errMessage;
    }

    public JPanel getWrapperForNome() {
        return wrapperForNome;
    }

    public JPanel getWrapperForStereotipo() {
        return wrapperForStereotipo;
    }

    public JPanel getWrapperForVisibilita() {
        return wrapperForVisibilita;
    }

    public JPanel getWrapperForRappresenta() {
        return wrapperForRappresenta;
    }

    public JPanel getWrapperForTipoDiClasse() {
        return wrapperForTipoDiClasse;
    }

    public JPanel getWrapperForErrMessage() {
        return wrapperForErrMessage;
    }

    public JCheckBox getHasSuperClasses() {
        return hasSuperClasses;
    }

    public JButton getAggiungi() {
        return aggiungi;
    }
}
