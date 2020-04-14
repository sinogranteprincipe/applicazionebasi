package View.Components;

import javax.swing.*;
import java.awt.*;

public class IconMaker {

    public static ImageIcon getSearchIcon(){
        ImageIcon search = new ImageIcon("src/View/Resources/search.png");
        search.setDescription("Cerca");
        return new ImageIcon(search.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
    }

    public static ImageIcon getAddIcon(){
        ImageIcon add = new ImageIcon("src/View/Resources/add.png");
        add.setDescription("Aggiungi");
        return new ImageIcon(add.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
    }

    public static ImageIcon getEditIcon(){
        ImageIcon edit = new ImageIcon("src/View/Resources/edit.png");
        edit.setDescription("Modifica");
        return new ImageIcon(edit.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
    }
}
