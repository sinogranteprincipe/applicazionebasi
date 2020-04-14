package View.Components;

import java.awt.*;

public class ColorPicker {
    public static Color getColor(String aColor){
        switch(aColor){
            case "blue":
                return new Color(0x33658A);
            case "yellow":
                return new Color(0xE6F8B2);
            case "grey":
                return new Color(0x504746);
            case "red":
                return new Color(0x931621);
            default:
                return new Color(0xDCE0D9);
        }
    }
}
