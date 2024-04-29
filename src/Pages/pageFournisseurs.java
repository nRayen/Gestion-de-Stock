package Pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import Components.pageTitle;
import DataAccessObject.FournisseurDAO;
import Stock.Fournisseur;

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

        fetchData(); // Récupère les fournisseurs depuis la base de donnée

        String[] data = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6",
                "Item 7", "Item 8", "Item 9",
                "Item 10" };

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

                new Fournisseur(id, name, country);
            }

            // Fermeture de la connexion et du Statement
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'utilisateur: " + ex.getMessage());
        }
    }
}
