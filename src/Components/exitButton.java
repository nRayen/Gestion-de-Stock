package Components;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class exitButton extends JButton {
    public exitButton() {
        setIcon((icon));
        setPreferredSize(new Dimension(100, 100));

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Quitter");
                Container frame = getParent();

                do
                    frame = frame.getParent();
                while (!(frame instanceof JFrame));
                ((JFrame) frame).dispose();
            }
        });
    };

    ImageIcon icon = new ImageIcon("images/closeButton.png");
}
