package Components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class pageTitle extends JLabel {

    public pageTitle(String t) {
        setFont(new Font("Montserrat", Font.BOLD, 30));
        setHorizontalAlignment(SwingConstants.CENTER);

        setBorder(new EmptyBorder(5, 50, 5, 50));
        setBorder(new MatteBorder(0, 0, 2, 0, new Color(61, 131, 197)));

        setText(t);

        setForeground(new Color(61, 131, 197));
        setBackground(new Color(221, 230, 237));
        setOpaque(true);
    }
}
