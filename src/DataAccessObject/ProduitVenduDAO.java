package DataAccessObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Stock.Produit;
import Stock.ProduitVendu;

public class ProduitVenduDAO implements Dao<ProduitVendu> {

    public ProduitVenduDAO() {
    }

    public static List<ProduitVendu> listeVendus = new ArrayList<>();

    @Override
    public ProduitVendu get(int id) {
        int index = 0;
        for (ProduitVendu pVendu : listeVendus) {
            if (pVendu.getId() == id) {
                index = listeVendus.indexOf(pVendu);
            }
        }
        return listeVendus.get((int) index);
    }

    @Override
    public List<ProduitVendu> getAll() {
        return listeVendus;
    }

    @Override
    public void save(ProduitVendu produitVendu) {

        try {
            // Connexion à la base de données
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", "root", "");

            // Préparation de la requête SQL
            String query = "INSERT INTO produit (name,price,quantité,id_fournisseur) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            // Remplissage des paramètres de la requête SQL
            // pstmt.setString(1, produit.getName());
            // pstmt.setFloat(2, produit.getPrice());
            // pstmt.setInt(3, produit.getQuantité());
            // pstmt.setInt(4, produit.getIDFournisseur());

            // Exécution de la requête SQL
            pstmt.executeUpdate();
            pstmt.close();

            // Récupérer ID attribué
            query = "SELECT id FROM produit WHERE name = ? AND quantité = ? AND id_fournisseur = ?";
            PreparedStatement pstmt2 = con.prepareStatement(query);
            // pstmt2.setString(1, produit.getName());
            // pstmt2.setInt(2, produit.getQuantité());
            // pstmt2.setInt(3, produit.getIDFournisseur());
            // System.err.println(produit.getPrice());
            ResultSet rs2 = pstmt2.executeQuery();

            List<Integer> possibleIDs = new ArrayList<>();
            int id;
            while (rs2.next()) {
                possibleIDs.add(rs2.getInt("id"));
                System.out.println(rs2.getInt("id"));
            }
            id = possibleIDs.reversed().get(0);
            produitVendu.setId(id);

            // Fermeture de la connexion et du PreparedStatement
            pstmt2.close();
            con.close();

            // Affichage d'un message de succès
            JOptionPane.showMessageDialog(null, "Le produit à été ajouté avec succès");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Echec : " + ex.getMessage(), "Erreur", 0);
        }

        listeVendus.add(produitVendu);
    }

}
