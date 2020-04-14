package View.Components;

import View.NuovoClassDiagramPage;
import View.NuovoPackagePage;
import View.VisualizzaClassDiagramPage;
import View.VisualizzaPackagesPage;

import javax.swing.*;
import java.awt.*;

public class MenuBarBuilder {
    private static JMenuBar menuBar;


    private class MenuBarController {
        private void  newPackageClicked(){
            JFrame jFrame = FrameSetter.getjFrame();
            jFrame.setContentPane(new NuovoPackagePage().getView());
            jFrame.validate();
            return;
        }
        private void  newClassDiagramClicked(){
            JFrame jFrame = FrameSetter.getjFrame();
            jFrame.setContentPane(new NuovoClassDiagramPage().getView());
            jFrame.validate();
            return;
        }
        private void  visualizzaPackageClicked(){
            JFrame jFrame = FrameSetter.getjFrame();
            jFrame.setContentPane(new VisualizzaPackagesPage().getView());
            jFrame.validate();
            return;
        }
        private void  visualizzaClassDiagramClicked(){
            JFrame jFrame = FrameSetter.getjFrame();
            jFrame.setContentPane(new VisualizzaClassDiagramPage().getView());
            jFrame.validate();
            return;
        }
    }

    public MenuBarBuilder() {
        menuBar = new JMenuBar();
        return;
    }

    public static JMenuBar getMenuBar(){
        if(menuBar == null){
            menuBar = new JMenuBar();
        }
        return menuBar;
    }

    public void setHomePageMenuBar(){
        JMenu nuovo = new JMenu("Nuovo");
        JMenu visualizza = new JMenu("Visualizza");

        MenuBarController controller = new MenuBarController();
        JMenuItem nuovoPackage = new JMenuItem("Package");
        JMenuItem nuovoClassDiagram = new JMenuItem("Class Diagram");
        JMenuItem visualizzaPackage = new JMenuItem("Package");
        JMenuItem visualizzaClassDiagram = new JMenuItem("Class Diagram");

        nuovoPackage.addActionListener(actionEvent -> controller.newPackageClicked());
        nuovoClassDiagram.addActionListener(actionEvent -> controller.newClassDiagramClicked());
        visualizzaPackage.addActionListener(actionEvent -> controller.visualizzaPackageClicked());
        visualizzaClassDiagram.addActionListener(actionEvent -> controller.visualizzaClassDiagramClicked());

        nuovo.add(nuovoPackage);
        nuovo.add(nuovoClassDiagram);
        visualizza.add(visualizzaPackage);
        visualizza.add(visualizzaClassDiagram);

        menuBar.add(nuovo);
        menuBar.add(visualizza);
        return;
    }
}
