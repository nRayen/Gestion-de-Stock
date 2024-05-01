package Pages;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Components.InputField;
import Components.InputLabel;
import Components.StyledButton;
import Components.pageTitle;
import DataAccessObject.ProduitDAO;
import Stock.Fournisseur;
import Stock.Produit;

public class pageProduits extends JPanel {

    ProduitDAO pDAO = new ProduitDAO();

    InputField nameInput = new InputField();
    InputField priceInput = new InputField();
    InputField quantitéInput = new InputField();
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
        gbc.weightx = 0.2;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.BOTH;

        JPanel pInfo = new JPanel();
        pInfo.setLayout(new GridLayout(2, 1));
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
        gbc.weightx = 0.8;
        gbc.weighty = 0.8;
        content.add(fList, gbc);

        ////////////////////////////////////////////////////////////////////// Panel
        ////////////////////////////////////////////////////////////////////// Info à
        ////////////////////////////////////////////////////////////////////// gauche

        pInfo.setBorder(new EmptyBorder(50, 10, 10, 10));
        JPanel inputArea = new JPanel();
        // inputArea.setBackground(Color.BLACK);
        // inputArea.setPreferredSize(new Dimension(500, 500));
        pInfo.add(inputArea);

        inputArea.setLayout(new GridLayout(4, 1, 100, 50));

        InputLabel nameLabel = new InputLabel("Nom");
        InputLabel priceLabel = new InputLabel("Prix");
        InputLabel quantitéLabel = new InputLabel("Quantité");
        InputLabel fournisseurLabel = new InputLabel("Fournisseur");

        inputArea.add(nameLabel);
        inputArea.add(nameInput);
        inputArea.add(priceLabel);
        inputArea.add(priceInput);
        inputArea.add(quantitéLabel);
        inputArea.add(quantitéInput);
        inputArea.add(fournisseurLabel);
        inputArea.add(fournisseurInput);

        // Button Area

        JPanel buttonArea = new JPanel();
        pInfo.add(buttonArea);
        buttonArea.setBorder(new EmptyBorder(50, 0, 50, 0));

        StyledButton addButton = new StyledButton("Ajouter");
        StyledButton updateButton = new StyledButton("Mettre à jour");
        StyledButton removeButton = new StyledButton("Supprimer");

        buttonArea.add(addButton);
        buttonArea.add(updateButton);
        buttonArea.add(removeButton);

        addButton.addActionListener(new ActionListener() { // Bouton ajouter produit
            public void actionPerformed(ActionEvent e) {
                addProduit();
            }
        });

        updateButton.addActionListener(new ActionListener() { // Bouton mise a jour produit
            public void actionPerformed(ActionEvent e) {
                if (isAnyRowSelected()) {
                    updateProduit();
                } else {
                    JOptionPane.showMessageDialog(null, "Aucun élément sélectionné");
                }
            }
        });

        removeButton.addActionListener(new ActionListener() { // Bouton supprimer produit
            public void actionPerformed(ActionEvent e) {
                if (isAnyRowSelected()) {
                    deleteProduit();
                } else {
                    JOptionPane.showMessageDialog(null, "Aucun élément sélectionné");
                }
            }
        });
    }

    // Fonctions

    private void addProduit() {

    }

    private void updateProduit() {

    }

    private void deleteProduit() {

    }

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
