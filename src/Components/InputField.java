package Components;

import java.awt.Font;

import javax.swing.JTextField;

public class InputField extends JTextField {

    public InputField() {
        setFont(new Font("Montserrat", Font.PLAIN, 20));
        setColumns(10);
    }
}
