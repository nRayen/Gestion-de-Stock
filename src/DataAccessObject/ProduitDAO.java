package DataAccessObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JOptionPane;

import Stock.Produit;

public class ProduitDAO implements Dao<Produit> {
    public ProduitDAO() {
    }

    public static List<Produit> listeProduits = new ArrayList<>();

    @Override
    public Produit get(int id) {
        int index = 0;
        for (Produit p : listeProduits) {
            if (p.getId() == id) {
                index = listeProduits.indexOf(p);
            }
        }
        return listeProduits.get((int) index);
    }

    @Override
    public List<Produit> getAll() {
        return listeProduits;
    }

    @Override
    public void save(Produit produit) {

        try {
            // Connexion à la base de données
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", "root", "");

            // Préparation de la requête SQL
            String query = "INSERT INTO produit (name,price,quantité,id_fournisseur) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            // Remplissage des paramètres de la requête SQL
            pstmt.setString(1, produit.getName());
            pstmt.setFloat(2, produit.getPrice());
            pstmt.setInt(2, produit.getQuantité());
            pstmt.setInt(2, produit.getIDFournisseur());

            // Exécution de la requête SQL
            pstmt.executeUpdate();
            pstmt.close();

            // Récupérer ID attribué
            query = "SELECT id FROM produit WHERE name = ? AND price = ? AND quantité = ? AND id_fournisseur = ?";
            PreparedStatement pstmt2 = con.prepareStatement(query);
            pstmt.setString(1, produit.getName());
            pstmt.setFloat(2, produit.getPrice());
            pstmt.setInt(2, produit.getQuantité());
            pstmt.setInt(2, produit.getIDFournisseur());
            ResultSet rs2 = pstmt2.executeQuery();
            while (rs2.next()) {
                int id = rs2.getInt("id");
                produit.setId(id);
                break;
            }

            // Fermeture de la connexion et du PreparedStatement
            pstmt2.close();
            con.close();

            // Affichage d'un message de succès
            JOptionPane.showMessageDialog(null, "Le produit à été ajouté avec succès");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Echec : " + ex.getMessage(), "Erreur", 0);
        }

        listeProduits.add(produit);
    }

    @Override
    public void update(Produit produit, String[] params) {
        try {
            // Connexion à la base de données
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", "root", "");

            // Préparation de la requête SQL
            String query = "UPDATE `produit` SET name = (?), price = (?), quantité = (?), id_fournisseur = (?) WHERE id = (?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            // Remplissage des paramètres de la requête SQL
            pstmt.setString(1, params[0]); // name
            pstmt.setFloat(2, Float.parseFloat(params[1] + "f")); // price
            pstmt.setInt(2, Integer.parseInt(params[2])); // quantité
            pstmt.setInt(2, Integer.parseInt(params[3])); // idFournisseur
            pstmt.setInt(3, produit.getId());

            // Exécution de la requête SQL
            pstmt.executeUpdate();

            // Fermeture de la connexion et du PreparedStatement
            pstmt.close();
            con.close();

            // Affichage d'un message de succès
            JOptionPane.showMessageDialog(null, "Produit mis à jour avec succès!");
        } catch (

        SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la modification des informations: " + ex.getMessage());
        }

        // Mise à jour des données
        produit.setName(Objects.requireNonNull(params[0], "Name cannot be null"));
        produit.setPrice(Objects.requireNonNull(Float.parseFloat(params[1] + "f"), "Price cannot be null"));
        produit.setQuantité(Objects.requireNonNull(Integer.parseInt(params[2]), "Quantité cannot be null"));
        produit.setIDFournisseur(Objects.requireNonNull(Integer.parseInt(params[3]), "idFournisseur cannot be null"));
    }

    @Override
    public void delete(Produit produit) {
        try {
            // Connexion à la base de données
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", "root", "");

            // Préparation de la requête SQL
            String query = "DELETE from `produit` WHERE `id` = (?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            int id = produit.getId();

            // Remplissage des paramètres de la requête SQL
            pstmt.setInt(1, id);

            // Exécution de la requête SQL
            pstmt.executeUpdate();

            // Fermeture de la connexion et du PreparedStatement
            pstmt.close();
            con.close();

            // Affichage d'un message de succès
            JOptionPane.showMessageDialog(null, "Produit avec l'id " + id + " à été supprimé avec succès!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression du produit: " + ex.getMessage());
        }

        listeProduits.remove(produit);
    }

    public static void fetchData() {
        try {
            // Connexion à la base de données
            listeProduits.clear();
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", "root", "");

            // Préparation de la requête SQL
            String query = "SELECT * FROM produit";

            Statement stmt = con.prepareStatement(query);

            // Exécution de la requête SQL
            ResultSet rs = stmt.executeQuery(query);

            // Traitement du résultat
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                float price = rs.getFloat("price");
                int quantité = rs.getInt("quantité");
                int idFournisseur = rs.getInt("id_fournisseur");

                Produit p = new Produit(name, price, quantité, idFournisseur);
                p.id = id;
                listeProduits.add(p);
            }

            // Fermeture de la connexion et du Statement
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de l'utilisateur: " + ex.getMessage());
        }
    }

}
