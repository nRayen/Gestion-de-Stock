package Pages;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Components.InputField;
import Components.InputLabel;
import Components.StyledButton;
import Components.pageTitle;
import DataAccessObject.ProduitDAO;
import DataAccessObject.ProduitVenduDAO;
import DataAccessObject.VenteDAO;
import Stock.Produit;
import Stock.ProduitVendu;
import Stock.Vente;

public class pageVentes extends JPanel {

    JTable table;
    DefaultTableModel tableModel;

    JTextField quantitéInput = new JTextField();
    JComboBox<Produit> produitSelector;
    static Produit selectorData[];

    InputField clientInput;
    InputField dateInput;

    float total;
    JLabel totalPrice;

    ProduitDAO pDao = new ProduitDAO();
    ProduitVenduDAO pvDao = new ProduitVenduDAO();
    VenteDAO vDao = new VenteDAO();

    private List<ProduitVendu> listePanier = new ArrayList<ProduitVendu>();

    public pageVentes() {
        // Layout de base
        setLayout(new BorderLayout());
        add(new pageTitle("Ventes"), BorderLayout.NORTH);

        Container content = new Container();
        add(content, BorderLayout.CENTER);

        // Layout de la page
        content.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.BOTH;

        JPanel vInfo = new JPanel();
        vInfo.setLayout(new GridLayout(2, 1));
        content.add(vInfo, gbc);

        /////////////////////////////////////////////////////////////// Liste à droite
        JPanel panierPanel = new JPanel();
        panierPanel.setLayout(new BorderLayout());

        // Titre
        pageTitle panierTitle = new pageTitle("Panier");
        panierTitle.reverseColors();
        panierPanel.add(panierTitle, BorderLayout.NORTH);

        // Création du tableau

        String columns[] = { "ID", "Nom", "Quantité", "Prix" };
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false; // Toujours retourner faux pour savoir si la cellule est modifiable
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setColumnSelectionAllowed(false);

        JScrollPane vList = new JScrollPane(table); // Ajouter la JTable à la JScrollPane

        panierPanel.add(vList, BorderLayout.CENTER);

        // Infos panier
        JPanel panierInfo = new JPanel();
        panierInfo.setLayout(new GridLayout(2, 2));
        panierPanel.add(panierInfo, BorderLayout.SOUTH);

        // Nom du client
        JPanel clientArea = new JPanel();
        InputLabel clientLabel = new InputLabel("Client :");
        clientInput = new InputField();
        clientArea.add(clientLabel);
        clientArea.add(clientInput);

        panierInfo.add(clientArea);

        // Sélecteur de date
        JPanel dateArea = new JPanel();
        InputLabel dateLabel = new InputLabel("Date :");
        dateInput = new InputField();
        dateArea.add(dateLabel);
        dateArea.add(dateInput);

        panierInfo.add(dateArea);

        // Affichage prix total
        totalPrice = new JLabel("Prix :");
        totalPrice.setFont(new Font("Montserrat", Font.PLAIN, 25));
        totalPrice.setHorizontalAlignment(SwingConstants.CENTER);
        panierInfo.add(totalPrice);

        ///// Bouton ajouter vente
        StyledButton createVenteBtn = new StyledButton("Créer la vente");
        panierInfo.add(createVenteBtn);

        createVenteBtn.addActionListener(new ActionListener() { // Bouton ajouter produit
            public void actionPerformed(ActionEvent e) {
                createVente();
            }
        });

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.weighty = 0.7;
        content.add(panierPanel, gbc);

        /////////////////////////////////////////////////////////////// Panel à gauche
        vInfo.setBorder(new EmptyBorder(50, 10, 10, 10));

        // Input area
        JPanel inputArea = new JPanel();
        vInfo.add(inputArea);
        inputArea.setLayout(new GridLayout(2, 2, 100, 50));

        InputLabel produitLabel = new InputLabel("Produit");
        InputLabel quantitéLabel = new InputLabel("Quantité");

        // Préparation des données pour le selector
        selectorData = pDao.getAll().toArray(new Produit[pDao.getAll().size()]);
        produitSelector = new JComboBox<Produit>(selectorData);
        produitSelector.setRenderer(new DefaultListCellRenderer() { // Afficher fournisseur.name
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {

                if (value instanceof Produit) {
                    value = ((Produit) value).getName() + " - " + Float.toString(((Produit) value).getPrice()) + "€";
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        inputArea.add(produitLabel);
        inputArea.add(produitSelector);
        inputArea.add(quantitéLabel);
        inputArea.add(quantitéInput);

        // Button area
        JPanel buttonArea = new JPanel();
        vInfo.add(buttonArea);
        buttonArea.setBorder(new EmptyBorder(50, 0, 50, 0));

        StyledButton addButton = new StyledButton("Ajouter");
        StyledButton removeButton = new StyledButton("Supprimer");

        buttonArea.add(addButton);
        buttonArea.add(removeButton);

        addButton.addActionListener(new ActionListener() { // Bouton ajouter produit
            public void actionPerformed(ActionEvent e) {
                addProduit();
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
        // Récupérer données
        Produit produit = (Produit) produitSelector.getSelectedItem();
        int id_produit = produit.getId();
        int quantité = Integer.parseInt(quantitéInput.getText());
        // Vérifier que quantité est positif
        if (quantité <= 0) {
            JOptionPane.showMessageDialog(null, "La quantité ne peut pas être nulle ou négative");
            return;
        }
        // Créer l'objet
        ProduitVendu produitVendu = new ProduitVendu(id_produit, quantité);

        // Il faut vérifier si le produit est déja dans le panier
        for (ProduitVendu pV : listePanier) {
            if (pV.getId_produit() == id_produit) {
                pV.setQuantité(pV.getQuantité() + quantité);
                pV.setTotal(pV.getQuantité() * pV.getPrice());
                JOptionPane.showMessageDialog(null,
                        quantité + " " + pDao.get(id_produit).getName() + " ont été ajouté au panier");

                int row = -1;
                for (int i = 0; i < tableModel.getRowCount(); ++i)
                    if (tableModel.getValueAt(i, 0).equals(id_produit)) {
                        row = i;
                        break;
                    }

                if (row != -1) {
                    tableModel.setValueAt(pV.getQuantité(), row, 2);
                    tableModel.setValueAt(pV.getTotal() + "€", row, 3);
                }
                return;
            }
        }

        // Si le panier ne contient pas déja le produit
        listePanier.add(produitVendu);
        Object[] pVendu = {
                produitVendu.getId_produit(),
                pDao.get(id_produit).getName(),
                produitVendu.getQuantité(),
                produitVendu.getTotal() + "€",
        };
        tableModel.addRow(pVendu);
        JOptionPane.showMessageDialog(null,
                quantité + " " + pDao.get(id_produit).getName() + " ont été ajouté au panier");
        updateTotal();

    }

    // Supprimer produit
    private void deleteProduit() {
        int row = table.getSelectedRow();
        int id = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());

        for (ProduitVendu pV : listePanier) {
            if (pV.getId_produit() == id) {
                listePanier.remove(pV);
                break;
            }
        }
        tableModel.removeRow(row);
        updateTotal();
    }

    private void createVente() {
        // Créer la vente
        String name = clientInput.getText();
        String date = dateInput.getText();
        if (!isDateValid(date)) {
            JOptionPane.showMessageDialog(null, "La date n'est pas valide");
            return;
        }

        Vente vente = new Vente(name, total, date);
        vDao.save(vente);

        // Enregistrer les produits
        for (ProduitVendu pV : listePanier) {
            pV.setId_vente(vente.getId());
            pvDao.save(pV);
        }
    }

    private boolean isAnyRowSelected() {
        return !table.getSelectionModel().isSelectionEmpty();
    }

    private void updateTotal() {
        float newTotal = 0;
        for (ProduitVendu pV : listePanier) {
            newTotal += pV.getPrice() * pV.getQuantité();
        }

        total = newTotal;
        totalPrice.setText("Prix : " + total + "€");
    }

    private boolean isDateValid(String date) {
        String[] liste = date.split("/");
        if (Integer.parseInt(liste[1]) < 0 || Integer.parseInt(liste[1]) > 12) {
            return false;
        }
        int maxDay = 31;

        if (Integer.parseInt(liste[1]) == 2) {
            maxDay = 29;
        } else if (Integer.parseInt(liste[1]) % 2 == 0) {
            maxDay = 29;
        }

        if (Integer.parseInt(liste[0]) < 0 || Integer.parseInt(liste[0]) > maxDay) {
            return false;
        }
        if (Integer.parseInt(liste[2]) < 0) {
            return false;
        }

        return true;
    }

    public void updatePSelector() {
        produitSelector
                .setModel(new DefaultComboBoxModel<>(pDao.getAll().toArray(new Produit[pDao.getAll().size()])));
    }

}
