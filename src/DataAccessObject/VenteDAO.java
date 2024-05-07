package DataAccessObject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import Stock.Vente;

public class VenteDAO implements Dao<Vente> {

    public VenteDAO() {
    }

    public static List<Vente> listeVente = new ArrayList<>();

    @Override
    public Vente get(int id) {
        int index = 0;
        for (Vente v : listeVente) {
            if (v.getId() == id) {
                index = listeVente.indexOf(v);
            }
        }
        return listeVente.get((int) index);
    }

    @Override
    public List<Vente> getAll() {
        return listeVente;
    }

    @Override
    public void save(Vente vente) {
        try {
            // Connexion à la base de données
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", "root", "");

            // Préparation de la requête SQL
            String query = "INSERT INTO vente (client, total, date) VALUES (?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            // Remplissage des paramètres de la requête SQL
            pstmt.setString(1, vente.getClientName());
            pstmt.setFloat(2, vente.getTotal());
            pstmt.setDate(3, createSQLDate(vente.getDate()));

            // Exécution de la requête SQL
            pstmt.executeUpdate();
            pstmt.close();

            // Récupérer ID attribué
            query = "SELECT id FROM vente WHERE client = ? AND total = ? AND date = ?";
            PreparedStatement pstmt2 = con.prepareStatement(query);
            pstmt2.setString(1, vente.getClientName());
            pstmt2.setFloat(2, vente.getTotal());
            pstmt2.setDate(3, createSQLDate(vente.getDate()));
            ResultSet rs2 = pstmt2.executeQuery();

            List<Integer> possibleIDs = new ArrayList<>();
            int id;
            while (rs2.next()) {
                possibleIDs.add(rs2.getInt("id"));
            }
            id = possibleIDs.reversed().get(0);
            vente.setId(id);

            // Fermeture de la connexion et du PreparedStatement
            pstmt2.close();
            con.close();

            // Affichage d'un message de succès
            JOptionPane.showMessageDialog(null, "La vente à été ajouté avec succès");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Echec : " + ex.getMessage(), "Erreur", 0);
        }

        listeVente.add(vente);
    }

    @Override
    public void update(Vente vente, String[] params) {
    }

    @Override
    public void delete(Vente vente) {
    }

    private Date createSQLDate(String date) {
        String[] n = date.split("/");
        List<String> list = Arrays.asList(n).reversed();
        String SQLDate = String.join("-", list);
        return java.sql.Date.valueOf(SQLDate);
    }

}
