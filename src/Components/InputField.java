package Components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;

public class InputField extends JTextField {

    public InputField() {
        setFont(new Font("Montserrat", Font.PLAIN, 20));
        setColumns(10);
    }

    public void addPlaceHolder() {
        Font font = getFont();
        font.deriveFont(Font.ITALIC);
        setForeground(Color.gray);
    }

    public void removePlaceHolder() {
        Font font = getFont();
        font.deriveFont(Font.PLAIN);
        setForeground(Color.black);
    }
}
