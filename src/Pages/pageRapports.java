package Pages;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Components.pageTitle;

public class pageRapports extends JPanel {
    public pageRapports() {
        setLayout(new BorderLayout());
        add(new pageTitle("Rapports"), BorderLayout.NORTH);

        Container content = new Container();
        add(content, BorderLayout.CENTER);
    }
}
