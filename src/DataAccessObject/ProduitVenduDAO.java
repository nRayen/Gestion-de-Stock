package DataAccessObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Stock.ProduitVendu;

public class ProduitVenduDAO implements Dao<ProduitVendu> {

    ProduitDAO pDAO = new ProduitDAO();

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
            String query = "INSERT INTO produitsvendu (id_vente,id_produit,quantité,total) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            // Remplissage des paramètres de la requête SQL
            pstmt.setInt(1, produitVendu.getId_vente());
            pstmt.setInt(2, produitVendu.getId_produit());
            pstmt.setInt(3, produitVendu.getQuantité());
            pstmt.setFloat(4, produitVendu.getTotal());

            // Exécution de la requête SQL
            pstmt.executeUpdate();
            pstmt.close();

            // Récupérer ID attribué
            query = "SELECT id FROM produitsvendu WHERE id_vente = ? AND id_produit = ? AND quantité = ? AND total = ?";
            PreparedStatement pstmt2 = con.prepareStatement(query);
            pstmt2.setInt(1, produitVendu.getId_vente());
            pstmt2.setInt(2, produitVendu.getId_produit());
            pstmt2.setInt(3, produitVendu.getQuantité());
            pstmt2.setFloat(4, produitVendu.getTotal());

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

            // Actualiser état du stock

            query = "UPDATE `produit` SET quantité = (?) WHERE id = (?)";
            PreparedStatement pstmt3 = con.prepareStatement(query);

            // Remplissage des paramètres de la requête SQL
            int quantité = pDAO.get(produitVendu.getId_produit()).getQuantité() - produitVendu.getQuantité();
            pstmt3.setInt(1, quantité);
            pstmt3.setInt(2, produitVendu.getId_produit());

            // Exécution de la requête SQL
            pstmt3.executeUpdate();

            // Fermeture de la connexion et du PreparedStatement
            pstmt3.close();
            con.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Echec : " + ex.getMessage(), "Erreur", 0);
        }

        listeVendus.add(produitVendu);
    }

    @Override
    public void update(ProduitVendu produitVendu, String[] params) {
    };

    @Override
    public void delete(ProduitVendu produitVendu) {
    };

}
