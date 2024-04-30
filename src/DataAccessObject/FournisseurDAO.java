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

import Stock.Fournisseur;

public class FournisseurDAO implements Dao<Fournisseur> {

    public FournisseurDAO() {
    }

    private List<Fournisseur> fournisseurs = new ArrayList<>();

    @Override
    public Fournisseur get(int id) {
        int index = 0;
        for (Fournisseur f : fournisseurs) {
            if (f.getId() == id) {
                index = fournisseurs.indexOf(f);
            }
        }
        return fournisseurs.get((int) index);

    }

    @Override
    public List<Fournisseur> getAll() {
        return fournisseurs;
    }

    @Override
    public void save(Fournisseur fournisseur) {

        try {
            // Connexion à la base de données
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", "root", "");

            // Préparation de la requête SQL
            String query = "INSERT INTO fournisseur (name,country) VALUES (?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            // Remplissage des paramètres de la requête SQL
            pstmt.setString(1, fournisseur.name);
            pstmt.setString(2, fournisseur.country);

            // Exécution de la requête SQL
            pstmt.executeUpdate();
            pstmt.close();

            // Récupérer ID attribué
            query = "SELECT id FROM fournisseur WHERE name = ? AND country = ?";
            PreparedStatement pstmt2 = con.prepareStatement(query);
            pstmt2.setString(1, fournisseur.name);
            pstmt2.setString(2, fournisseur.country);
            ResultSet rs2 = pstmt2.executeQuery();
            while (rs2.next()) {
                int id = rs2.getInt("id");
                fournisseur.id = id;
                break;
            }

            // Fermeture de la connexion et du PreparedStatement
            pstmt2.close();
            con.close();

            // Affichage d'un message de succès
            JOptionPane.showMessageDialog(null, "Le fournisseur à été ajouté avec succès");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Echec : " + ex.getMessage(), "Erreur", 0);
        }

        fournisseurs.add(fournisseur);
        System.err.println(fournisseur.id);
        System.err.println(fournisseurs.size());
    }

    @Override
    public void update(Fournisseur fournisseur, String[] params) {
        try {
            // Connexion à la base de données
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", "root", "");

            // Préparation de la requête SQL
            String query = "UPDATE `fournisseur` SET name = (?), country = (?) WHERE id = (?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            // Remplissage des paramètres de la requête SQL
            pstmt.setString(1, params[0]);
            pstmt.setString(2, params[1]);
            pstmt.setInt(3, fournisseur.getId());

            // Exécution de la requête SQL
            pstmt.executeUpdate();

            // Fermeture de la connexion et du PreparedStatement
            pstmt.close();
            con.close();

            // Affichage d'un message de succès
            JOptionPane.showMessageDialog(null, "Fournisseur mis à jour avec succès!");
        } catch (

        SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la modification des informations: " + ex.getMessage());
        }

        fournisseur.setName(Objects.requireNonNull(params[0], "Name cannot be null"));
        fournisseur.setCountry(Objects.requireNonNull(params[1], "Email cannot be null"));

    }

    @Override
    public void delete(Fournisseur fournisseur) {
        try {
            // Connexion à la base de données
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", "root", "");

            // Préparation de la requête SQL
            String query = "DELETE from `fournisseur` WHERE `id` = (?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            int id = fournisseur.getId();

            // Remplissage des paramètres de la requête SQL
            pstmt.setInt(1, id);

            // Exécution de la requête SQL
            pstmt.executeUpdate();

            // Fermeture de la connexion et du PreparedStatement
            pstmt.close();
            con.close();

            // Affichage d'un message de succès
            JOptionPane.showMessageDialog(null, "Fournisseur avec l'id " + id + " à été supprimé avec succès!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression du fournisseur: " + ex.getMessage());
        }

        fournisseurs.remove(fournisseur);
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
                fournisseurs.add(f);
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
