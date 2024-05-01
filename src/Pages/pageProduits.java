package Pages;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JPanel;

import Components.pageTitle;

public class pageProduits extends JPanel {
    public pageProduits() {

        // Layout de base
        setLayout(new BorderLayout());
        add(new pageTitle("Produits"), BorderLayout.NORTH);

        Container content = new Container();
        add(content, BorderLayout.CENTER);
    }
}
