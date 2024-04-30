package Pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import Components.StyledButton;
import Components.pageTitle;
import DataAccessObject.FournisseurDAO;
import Stock.Fournisseur;

public class pageFournisseurs extends JPanel {
    FournisseurDAO fDAO = new FournisseurDAO();
    JTextField nameInput = new JTextField();
    JTextField countryInput = new JTextField();

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

        // Panel Info à gauche
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
                String name = nameInput.getText();
                String country = countryInput.getText();
                Fournisseur fournisseur = new Fournisseur(name, country);
                fDAO.save(fournisseur);
            }
        });

        //////////////////////////////////////////////////////////////////////////////////// Liste
        //////////////////////////////////////////////////////////////////////////////////// à
        //////////////////////////////////////////////////////////////////////////////////// droite

        fetchData(); // Récupère les fournisseurs depuis la base de donnée

        String[] data = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6",
                "Item 7", "Item 8", "Item 9",
                "Item 10" };

        // Object[] data = fDAO.getAll().toArray();

        JList<String> liste = new JList<>(data);
        JScrollPane fList = new JScrollPane(liste);

        gbc.gridx = 1;
        content.add(fList, gbc);

    }

    public void fetchData() {
        try {
            // Connexion à la base de données
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", "root", "");

            // Préparation de la requête SQL
            String query = "SELECT * FROM fournisseur";

            Statement stmt = con.prepareStatement(query);

            // Exécution de la requête SQL
            ResultSet rs = stmt.executeQuery(query);

            // Traitement du résultat
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String country = rs.getString("country");

                Fournisseur f = new Fournisseur(name, country);
                f.id = id;
            }

            // Fermeture de la connexion et du Statement
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la récupération des données " + ex.getMessage());
        }
    }
}
