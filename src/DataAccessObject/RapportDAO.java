package DataAccessObject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Stock.Produit;
import Stock.ProduitVendu;
import Stock.Rapport;
import Stock.Vente;

public class RapportDAO {
    String dateDebut;
    String dateFin;

    ProduitDAO pDao = new ProduitDAO();

    List<Produit> stock;
    public List<Vente> ventes;

    public RapportDAO() {
    };

    public void createRapport(Rapport rapport) {
        // Récupérer le stock
        stock = pDao.getAll();
        ventes = new ArrayList<Vente>();

        // Récupérer les ventes
        try {
            // Connexion à la base de données
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", "root", "");

            // Préparation de la requête SQL
            String query = "SELECT * FROM vente WHERE date BETWEEN ? and ?";
            PreparedStatement pstmt = con.prepareStatement(query);

            // pstmt.setDate(1, Date.valueOf(rapport.getDateDébut()));
            // pstmt.setDate(2, Date.valueOf(rapport.getDateFin()));
            // Dates de test
            pstmt.setDate(1, Date.valueOf("2002-01-01"));
            pstmt.setDate(2, Date.valueOf("2005-01-01"));
            // Exécution de la requête SQL
            ResultSet rs = pstmt.executeQuery();

            // Traitement du résultat
            while (rs.next()) {
                int id = rs.getInt("id");
                String client = rs.getString("client");
                float total = rs.getFloat("total");
                String date = rs.getDate("date").toString();

                Vente v = new Vente(id, client, total, date);
                ventes.add(v);
            }

            // Fermeture de la connexion et du Statement
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des données: " + ex.getMessage());
        }

        // Récupérer les produits vendus

        for (Vente v : ventes) {
            try {
                // Connexion à la base de données
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", "root", "");

                // Préparation de la requête SQL
                String query = "SELECT * FROM produitsvendu WHERE id_vente = ?";
                PreparedStatement pstmt = con.prepareStatement(query);

                pstmt.setInt(1, v.getId());
                // Exécution de la requête SQL
                ResultSet rs = pstmt.executeQuery();

                // Traitement du résultat
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int id_vente = rs.getInt("id");
                    int id_produit = rs.getInt("id_produit");
                    int quantité = rs.getInt("quantité");
                    ProduitVendu pV = new ProduitVendu(id, id_vente, id_produit, quantité);

                    v.produits.add(pV);
                }

                // Fermeture de la connexion et du Statement
                pstmt.close();
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des données: " + ex.getMessage());
            }
        }

        ////////////////////////////////////////////// Création fichier texte
        System.out.println("--------------Rapport-----------------");
        System.out.println("Stock :");
        for (Produit produit : stock) {
            System.out.println(produit.getName() + " " + produit.getPrice());
        }
    }
}
