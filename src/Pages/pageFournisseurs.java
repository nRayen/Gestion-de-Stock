package Pages;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Components.StyledButton;
import Components.pageTitle;
import DataAccessObject.FournisseurDAO;
import Stock.Fournisseur;

public class pageFournisseurs extends JPanel {
    FournisseurDAO fDAO = new FournisseurDAO();
    JTextField nameInput = new JTextField();
    JTextField countryInput = new JTextField();
    JTable table;
    DefaultTableModel tableModel;

    public pageFournisseurs() {

        fDAO.fetchData();

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
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;

        JPanel fInfo = new JPanel();
        fInfo.setLayout(new GridLayout(2, 1, 20, 20));
        content.add(fInfo, gbc);

        /////////////////////////////////////////////////////////////////////// Liste à
        /////////////////////////////////////////////////////////////////////// droite

        // Création du tableau
        String columns[] = { "ID", "Name", "Country" };
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
        content.add(fList, gbc);

        /////////////////////////////////////////////////////////////////// Panel Info à
        /////////////////////////////////////////////////////////////////// gauche
        fInfo.setBorder(new EmptyBorder(50, 50, 50, 50));
        JPanel inputArea = new JPanel();
        fInfo.add(inputArea);

        inputArea.setLayout(new GridLayout(2, 1, 100, 50));

        JLabel nameLabel = new JLabel("Nom");
        nameLabel.setFont(new Font("Montserrat", Font.PLAIN, 25));
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel countryLabel = new JLabel("Localisation");
        countryLabel.setFont(new Font("Montserrat", Font.PLAIN, 25));
        countryLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        nameInput.setFont(new Font("Montserrat", Font.PLAIN, 25));
        nameInput.setColumns(10);
        countryInput.setFont(new Font("Montserrat", Font.PLAIN, 25));
        countryInput.setColumns(10);

        inputArea.add(nameLabel);
        inputArea.add(nameInput);
        inputArea.add(countryLabel);
        inputArea.add(countryInput);

        // Button Area

        JPanel buttonArea = new JPanel();
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
        Fournisseur fournisseur = new Fournisseur(name, country);
        fDAO.save(fournisseur);
        Object[] f = { fournisseur.getId(), fournisseur.getName(), fournisseur.getCountry() };
        tableModel.addRow(f);
        clearInputs();

        // Modfiier dans la BDD
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
