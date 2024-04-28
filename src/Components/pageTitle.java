package Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

public class pageTitle extends JLabel {

    public pageTitle(String t) {
        setFont(new Font("Montserrat", Font.BOLD, 30));
        setText(t);

        setForeground(new Color(61, 131, 197));
        setBackground(new Color(221, 230, 237));
        setOpaque(true);
    }
}
