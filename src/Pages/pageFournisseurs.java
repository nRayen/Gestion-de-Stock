package Pages;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

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
import DataAccessObject.FournisseurDAO;
import Stock.Fournisseur;

public class pageFournisseurs extends JPanel {
    FournisseurDAO fDAO = new FournisseurDAO();
    InputField nameInput = new InputField();
    InputField countryInput = new InputField();
    JTable table;
    DefaultTableModel tableModel;

    public pageFournisseurs() {

        // Layout de base
        setLayout(new BorderLayout());
        add(new pageTitle("Fournisseurs"), BorderLayout.NORTH);

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

        JPanel fInfo = new JPanel();
        fInfo.setLayout(new GridLayout(2, 1));
        content.add(fInfo, gbc);

        /////////////////////////////////////////////////////////////////////// Liste à
        /////////////////////////////////////////////////////////////////////// droite

        // Création du tableau
        String columns[] = { "ID", "Nom", "Pays" };
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
                    getFournisseurInfo();
                }
            }
        });

        JScrollPane fList = new JScrollPane(table); // Ajouter la JTable à la JScrollPane
        List<Fournisseur> data = fDAO.getAll();
        for (Fournisseur fournisseur : data) {
            Object[] f = { fournisseur.getId(), fournisseur.getName(), fournisseur.getCountry() };
            tableModel.addRow(f);
        }

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        gbc.weighty = 0.8;
        content.add(fList, gbc);

        /////////////////////////////////////////////////////////////////// Panel Info à
        /////////////////////////////////////////////////////////////////// gauche
        fInfo.setBorder(new EmptyBorder(50, 50, 50, 50));
        JPanel inputArea = new JPanel();
        fInfo.add(inputArea);

        inputArea.setLayout(new GridLayout(2, 1, 100, 50));

        InputLabel nameLabel = new InputLabel("Nom");
        InputLabel countryLabel = new InputLabel("Localisation");

        inputArea.add(nameLabel);
        inputArea.add(nameInput);
        inputArea.add(countryLabel);
        inputArea.add(countryInput);

        // Button Area

        JPanel buttonArea = new JPanel();
        buttonArea.setBorder(new EmptyBorder(50, 0, 50, 0));
        fInfo.add(buttonArea);

        StyledButton addButton = new StyledButton("Ajouter");
        StyledButton updateButton = new StyledButton("Mettre à jour");
        StyledButton removeButton = new StyledButton("Supprimer");

        buttonArea.add(addButton);
        buttonArea.add(updateButton);
        buttonArea.add(removeButton);

        addButton.addActionListener(new ActionListener() { // Bouton ajouter fournisseur
            public void actionPerformed(ActionEvent e) {
                addFournisseur();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isAnyRowSelected()) {
                    updateFournisseur();
                } else {
                    JOptionPane.showMessageDialog(null, "Aucun élément sélectionné");
                }
            }
        });

        removeButton.addActionListener(new ActionListener() { // Bouton supprimer fournisseur
            public void actionPerformed(ActionEvent e) {
                if (isAnyRowSelected()) {
                    deleteFournisseur();
                } else {
                    JOptionPane.showMessageDialog(null, "Aucun élément sélectionné");
                }
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////////// Fonctions
    private void addFournisseur() {
        String name = nameInput.getText();
        String country = countryInput.getText();
        if (name == "" || country == "") {
            return;
        }
        Fournisseur fournisseur = new Fournisseur(name, country);
        fDAO.save(fournisseur);
        Object[] f = { fournisseur.getId(), fournisseur.getName(), fournisseur.getCountry() };
        tableModel.addRow(f);
        clearInputs();
    }

    private void deleteFournisseur() {
        int row = table.getSelectedRow();
        int id = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
        tableModel.removeRow(row);
        clearInputs();

        // Modifier dans la BDD
        fDAO.delete(fDAO.get(id));
    }

    private void updateFournisseur() {
        String[] param = { nameInput.getText(), countryInput.getText() };
        int row = table.getSelectedRow();
        int id = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());

        tableModel.setValueAt(param[0], row, 1);
        tableModel.setValueAt(param[1], row, 2);

        // Modifier dans la BDD
        fDAO.update(fDAO.get(id), param);
    }

    private void getFournisseurInfo() {
        int row = table.getSelectedRow();
        int id = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
        Fournisseur selected = fDAO.get(id);
        nameInput.setText(selected.getName());
        countryInput.setText(selected.getCountry());
    }

    private boolean isAnyRowSelected() {
        return !table.getSelectionModel().isSelectionEmpty();
    }

    private void clearInputs() {
        nameInput.setText("");
        countryInput.setText("");
    }
}
