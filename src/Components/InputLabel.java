package Components;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class InputLabel extends JLabel {
    public InputLabel(String t) {
        setText(t);
        setFont(new Font("Montserrat", Font.PLAIN, 25));
        setHorizontalAlignment(SwingConstants.RIGHT);
    }
}
