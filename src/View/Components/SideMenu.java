package View.Components;

import Entity.ClassDiagram.ClassDiagram;

import javax.swing.*;

public class SideMenu {

    private class SideMenuController{

    }

    private JPanel view;
    private JScrollPane classDiagramContainer;
    private JTree classDiagram;
    private JPanel buttonContainer;
    private JLabel associationLabel;
    private JLabel classLabel;
    private JButton addAssociation;
    private JButton addClasse;
    private JButton modifyClasse;
    private JButton modifyAssociazione;

    public JPanel getView() {
        return view;
    }

    public SideMenu(){

        SpringLayout layout = new SpringLayout();
        view = new JPanel();
        classDiagramContainer = new JScrollPane();
        classDiagram = new JTree(new JTree.DynamicUtilTreeNode("wirvboin ", null));
        buttonContainer = new JPanel();
        addAssociation = new JButton(IconMaker.getAddIcon());
        modifyAssociazione = new JButton(IconMaker.getEditIcon());
        associationLabel = new JLabel("Associazione:");
        classLabel = new JLabel("Classe");
        addClasse = new JButton(IconMaker.getAddIcon());
        modifyClasse = new JButton(IconMaker.getEditIcon());


        layout.putConstraint(SpringLayout.WEST, classLabel, 5, SpringLayout.WEST, buttonContainer);

        layout.putConstraint(SpringLayout.NORTH, classLabel, 5, SpringLayout.SOUTH, buttonContainer);
        layout.putConstraint(SpringLayout.WEST, addClasse, 10, SpringLayout.EAST, classLabel);
        layout.putConstraint(SpringLayout.WEST, modifyClasse, 10, SpringLayout.EAST, addClasse);

        layout.putConstraint(SpringLayout.NORTH, addClasse, 0, SpringLayout.NORTH, classLabel);
        layout.putConstraint(SpringLayout.NORTH, modifyClasse, 0, SpringLayout.NORTH, classLabel);

        layout.putConstraint(SpringLayout.NORTH, associationLabel, 10, SpringLayout.NORTH, classLabel);
        layout.putConstraint(SpringLayout.WEST, addAssociation, 10, SpringLayout.EAST, associationLabel);
        layout.putConstraint(SpringLayout.WEST, modifyAssociazione, 10, SpringLayout.EAST, addAssociation);

        layout.putConstraint(SpringLayout.NORTH, addAssociation, 0, SpringLayout.NORTH, associationLabel);
        layout.putConstraint(SpringLayout.NORTH, modifyAssociazione, 0, SpringLayout.NORTH, associationLabel);

        view.setVisible(true);
        classDiagramContainer.setVisible(true);
        classDiagram.setVisible(true);
        buttonContainer.setVisible(true);
        addAssociation.setVisible(true);
        modifyAssociazione.setVisible(true);
        associationLabel.setVisible(true);
        classLabel.setVisible(true);
        addClasse.setVisible(true);
        modifyClasse.setVisible(true);

        classDiagramContainer.add(classDiagram);

        buttonContainer.add(modifyAssociazione);
        buttonContainer.add(associationLabel);
        buttonContainer.add(addAssociation);
        buttonContainer.add(classLabel);
        buttonContainer.add(addClasse);
        buttonContainer.add(modifyClasse);
        buttonContainer.setLayout(layout);

      //  view.add(classDiagramContainer);
        view.add(buttonContainer);
    }
}
