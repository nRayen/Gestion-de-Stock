package Pages;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Components.pageTitle;
import DataAccessObject.ProduitDAO;
import Stock.Fournisseur;
import Stock.Produit;

public class pageProduits extends JPanel {

    ProduitDAO pDAO = new ProduitDAO();

    JTextField nameInput = new JTextField();
    JTextField priceInput = new JTextField();
    JTextField quantitéInput = new JTextField();
    JComboBox<Fournisseur> fournisseurInput = new JComboBox<>();

    JTable table;
    DefaultTableModel tableModel;

    public pageProduits() {

        pDAO.fetchData();

        // Layout de base
        setLayout(new BorderLayout());
        add(new pageTitle("Produits"), BorderLayout.NORTH);

        Container content = new Container();
        add(content, BorderLayout.CENTER);

        // Layout de la page

        content.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;

        JPanel pInfo = new JPanel();
        pInfo.setLayout(new GridLayout(2, 1, 20, 20));
        content.add(pInfo, gbc);

        /////////////////////////////////////////////////////////////// Liste à droite

        // Création du tableau
        String columns[] = { "ID", "Name", "Price", "Quantité", "Fournisseur" };
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false; // Toujours retourner faux pour savoir si la cellule est modifiable
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setColumnSelectionAllowed(false);

        // Ecouteur d'évènements pour la sélection de ligne
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // Empêche l'écouteur d'éxécuter 2 fois
                    if (!isAnyRowSelected()) {
                        return;
                    }
                    getProduitInfo();
                }
            }
        });

        JScrollPane fList = new JScrollPane(table); // Ajouter la JTable à la JScrollPane
        List<Produit> data = pDAO.getAll();
        for (Produit produit : data) {
            int id = produit.getId();
            String name = produit.getName();
            Float price = produit.getPrice();
            int quantité = produit.getQuantité();
            int idFournisseur = produit.getIDFournisseur();
            Object[] p = { id, name, price, quantité, idFournisseur };
            tableModel.addRow(p);
        }

        gbc.gridx = 1;
        content.add(fList, gbc);
    }

    // Fonctions

    private void getProduitInfo() {
        int row = table.getSelectedRow();
        int id = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
        Produit selected = pDAO.get(id);
        nameInput.setText(selected.getName());
        priceInput.setText(Float.toString(selected.getPrice()));
        quantitéInput.setText(Integer.toString(selected.getQuantité()));
        fournisseurInput.setSelectedItem(null);
    }

    private boolean isAnyRowSelected() {
        return !table.getSelectionModel().isSelectionEmpty();
    }

    private void clearInputs() {
        nameInput.setText("");
        priceInput.setText("");
        quantitéInput.setText("");
        fournisseurInput.setSelectedItem(null);
    }
}
