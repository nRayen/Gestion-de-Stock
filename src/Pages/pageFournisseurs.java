package Pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


import javax.swing.JList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Components.pageTitle;

public class pageFournisseurs extends JPanel {

    public pageFournisseurs() {

        // Layout de base
        setLayout(new BorderLayout());
        add(new pageTitle("Fournisseurs"), BorderLayout.NORTH);

        Container content = new Container();
        add(content, BorderLayout.CENTER);

        // Layout de la page

        content.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel Info à gauche
        JPanel fInfo = new JPanel();
        fInfo.setBackground(Color.BLACK);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        content.add(fInfo, gbc);

        // Liste à droite
        String[] data = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9",
                "Item 10" };
        JList<String> liste = new JList<>(data);
        JScrollPane fList = new JScrollPane(liste);

        gbc.gridx = 1;
        content.add(fList, gbc);

    }
}
