package Pages;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Components.pageTitle;

public class pageVentes extends JPanel {
    public pageVentes() {
        setLayout(new BorderLayout());
        add(new pageTitle("Ventes"), BorderLayout.NORTH);

        Container content = new Container();
        add(content, BorderLayout.CENTER);
    }
}
