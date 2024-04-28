package Pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import Components.pageTitle;
import Stock.Produit;

public class pageProduits extends JPanel {
    public pageProduits() {
        setLayout(new BorderLayout());
        add(new pageTitle("Produits"), BorderLayout.NORTH);
        setBackground(Color.LIGHT_GRAY);

        Container content = new Container();
        add(content, BorderLayout.CENTER);

        JButton addProduit = new JButton("Ajouter");
        addProduit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Produit prod = new Produit("PS5", 17, 4);
                System.out.println("test");
                System.out.println(prod.name);

            }
        });

        add(addProduit);
    }
}
