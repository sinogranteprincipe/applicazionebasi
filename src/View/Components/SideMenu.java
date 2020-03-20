package View.Components;

import Entity.ClassDiagram.ClassDiagram;

import javax.swing.*;
import java.awt.*;

public class SideMenu {

    private JPanel view;
    private JScrollPane classDiagramContainer;
    private JPanel buttonContainerAssociazione;
    private JPanel buttonContainerClasse;
    private JLabel associationLabel;
    private JLabel classLabel;
    private JButton addAssociation;
    private JButton addClasse;
    private JButton modifyClasse;
    private JButton modifyAssociazione;

    public JPanel getButtonContainerAssociazione() {
        return buttonContainerAssociazione;
    }

    public JPanel getButtonContainerClasse() {
        return buttonContainerClasse;
    }

    public JLabel getAssociationLabel() {
        return associationLabel;
    }

    public JLabel getClassLabel() {
        return classLabel;
    }

    public JButton getAddAssociation() {
        return addAssociation;
    }

    public JButton getModifyClasse() {
        return modifyClasse;
    }

    public JButton getModifyAssociazione() {
        return modifyAssociazione;
    }

    public JPanel getView() {
        return view;
    }

    public JButton getAddClasse() {
        return addClasse;
    }

    public JScrollPane getClassDiagramContainer() {
        return classDiagramContainer;
    }

    public SideMenu(){
        view = new JPanel();
        classDiagramContainer = new JScrollPane();
        buttonContainerAssociazione = new JPanel();
        buttonContainerClasse = new JPanel();
        addAssociation = new JButton(IconMaker.getAddIcon());
        modifyAssociazione = new JButton(IconMaker.getEditIcon());
        associationLabel = new JLabel("Associazione:");
        classLabel =       new JLabel("           Classe:");
        addClasse = new JButton(IconMaker.getAddIcon());
        modifyClasse = new JButton(IconMaker.getEditIcon());
        classDiagramContainer.setSize(FrameSetter.getjFrame().getWidth()/4, FrameSetter.getjFrame().getHeight()/3);
        buttonContainerAssociazione.setSize(FrameSetter.getjFrame().getWidth()/4, FrameSetter.getjFrame().getHeight()/3);
        buttonContainerClasse.setSize(FrameSetter.getjFrame().getWidth()/4, FrameSetter.getjFrame().getHeight()/3);

        view.setVisible(true);
        buttonContainerAssociazione.setVisible(true);
        buttonContainerClasse.setVisible(true);
        addAssociation.setVisible(true);
        modifyAssociazione.setVisible(true);
        associationLabel.setVisible(true);
        classLabel.setVisible(true);
        addClasse.setVisible(true);
        modifyClasse.setVisible(true);
        classDiagramContainer.setVisible(true);

        buttonContainerAssociazione.add(associationLabel);
        buttonContainerAssociazione.add(addAssociation);
        buttonContainerAssociazione.add(modifyAssociazione);
        buttonContainerClasse.add(classLabel);
        buttonContainerClasse.add(addClasse);
        buttonContainerClasse.add(modifyClasse);

        view.setLayout(new BoxLayout(view,BoxLayout.Y_AXIS));
        view.add(classDiagramContainer, Component.CENTER_ALIGNMENT);
        view.add(buttonContainerClasse, Component.LEFT_ALIGNMENT);
        view.add(buttonContainerAssociazione, Component.LEFT_ALIGNMENT);
    }
}
