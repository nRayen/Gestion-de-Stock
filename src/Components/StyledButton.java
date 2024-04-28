package Components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class StyledButton extends JButton {

    public StyledButton(String t) {
        setText(t);
        setFont(new Font("Montserrat", Font.BOLD, 30));

        setBackground(new Color(61, 131, 197));
        setForeground(new Color(221, 230, 237));
    };

}
