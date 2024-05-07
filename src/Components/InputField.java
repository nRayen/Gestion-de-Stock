package Components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class InputField extends JTextField {

    @Override
    public String getText() {
        Document doc = getDocument();
        String txt;
        try {
            txt = doc.getText(0, doc.getLength());
        } catch (BadLocationException e) {
            txt = null;
        }
        if (txt.length() == 0) {
            JOptionPane.showMessageDialog(null, "Les champs ne peuvent pas être vides");
            return "";
        }
        if (txt.split("")[0] == "-") {
            JOptionPane.showMessageDialog(null, "Négatif non autorisé");
            return "";
        }
        return txt;
    }

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
