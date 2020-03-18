package View.Components;

import javax.swing.*;
import java.awt.*;

public class IconMaker {

    public static ImageIcon getSearchIcon(){
        ImageIcon search = new ImageIcon("src/View/Resources/search.png");
        return new ImageIcon(search.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
    }

    public static ImageIcon getAddIcon(){
        ImageIcon add = new ImageIcon("src/View/Resources/add.png");
        return new ImageIcon(add.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
    }

    public static ImageIcon getEditIcon(){
        ImageIcon edit = new ImageIcon("src/View/Resources/edit.png");
        return new ImageIcon(edit.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
    }
}
