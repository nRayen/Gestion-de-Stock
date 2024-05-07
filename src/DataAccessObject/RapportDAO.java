package DataAccessObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
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
    FournisseurDAO fDao = new FournisseurDAO();
    ProduitVenduDAO pvDao = new ProduitVenduDAO();

    List<Produit> stock;
    public List<Vente> ventes;
    public List<ProduitVendu> produitsVendus;

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
            pstmt.setDate(1, rapport.getSQLDateDébut());
            pstmt.setDate(2, rapport.getSQLDateFin());
            // Exécution de la requête SQL
            ResultSet rs = pstmt.executeQuery();

            // Traitement du résultat
            while (rs.next()) {
                int id = rs.getInt("id");
                String client = rs.getString("client");
                float total = rs.getFloat("total");
                Date date = rs.getDate("date");

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
        try {
            File rapportTxt = new File("rapport.txt");
            FileWriter writer = new FileWriter(rapportTxt);

            writer.write("<<==================== Rapport du " + rapport.getDate() + " ====================>>");
            writer.write("\n");

            int CA = 0;
            for (Vente v : ventes) {
                CA += v.getTotal();
            }

            writer.write("\nSur la période du " + rapport.getDateDébut() + " au " + rapport.getDateFin()
                    + ", vous avez réalisez un chiffre d'affaire de " + CA + "€");
            writer.write("\n");
            writer.write(
                    "\n<------------------------------------- Etat du stock ------------------------------------>");
            writer.write("\n");

            for (Produit produit : stock) {
                writer.write("\n ◢ " + produit.getName());
                writer.write("\n ⊢   ------- Quantité : " + produit.getQuantité());
                writer.write("\n ⊢   ----------- Prix : " + produit.getPrice() + "€");
                writer.write("\n ⨽   ---- Fournisseur : " + fDao.get(produit.getIDFournisseur()).getName());
                writer.write("\n");
            }

            writer.write("\n");
            writer.write("\n");
            writer.write("\n");

            writer.write("\n<---------------- Ventes éffectuées entre le " + rapport.getDateDébut() + " et le "
                    + rapport.getDateFin() + " ---------------->");
            writer.write("\n");

            for (Vente v : ventes) {
                writer.write("\n ◢ Vente à " + v.getClientName() + " le " + v.getDate());
                writer.write("\n ⊢   ------- Prix : " + v.getTotal());
                writer.write("\n ⨽   ------- ◢Liste des produits");

                ProduitVendu pV;
                for (Iterator<ProduitVendu> it = v.getProduits().iterator(); it.hasNext();) {
                    pV = it.next();
                    // ... work with movie
                    writer.write("\n               ⊢  ---- " + pV.getQuantité() + "x "
                            + pDao.get(pV.getId_produit()).getName() + " = " + pV.getTotal() + "€");
                    if (!it.hasNext()) { // the last element
                        writer.write("\n               ⨽  ---- " + pV.getQuantité() + "x "
                                + pDao.get(pV.getId_produit()).getName() + " = " + pV.getTotal() + "€");

                    }
                }

                writer.write("\n");
            }

            writer.close();
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
