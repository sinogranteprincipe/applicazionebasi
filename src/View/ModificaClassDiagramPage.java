package View;

import View.Components.SideMenu;

import javax.swing.*;
import java.awt.*;

public class ModificaClassDiagramPage {

    private class ModificaClassDiagramPageControlerr{

    }

    private SideMenu sideMenu;
    private JPanel view;
    private JPanel currentPage;
    private JPanel sideMenuView;

    public JPanel getView() {
        return view;
    }

    public ModificaClassDiagramPage(){
        view = new JPanel();
        sideMenu = new SideMenu();
        sideMenuView = sideMenu.getView();
        currentPage = new JPanel();
        view.add(sideMenuView);
        view.add(currentPage);
        view.setVisible(true);
    }
}
