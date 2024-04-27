package Pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Stock.Produit;

public class pageProduits extends JPanel {
    public pageProduits() {
        add(new JLabel("Produits"));

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
