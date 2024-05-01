package Pages;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;

import Components.exitButton;
import DataAccessObject.FournisseurDAO;
import DataAccessObject.ProduitDAO;

public class Interface extends JFrame {

    ProduitDAO pDAO = new ProduitDAO();
    FournisseurDAO fDao = new FournisseurDAO();

    public Interface() {
        fetchAllData(); // Récupérer toute les données de la BDD

        // Paramètres de l'application
        setTitle("Gestion des Stocks");
        setSize(1080, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création du TabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Page Quitter
        JPanel pageExit = new JPanel();
        pageExit.setLayout(new BorderLayout());
        pageExit.add(new exitButton(), BorderLayout.CENTER);

        // Ajout des Pages au TabbedPane
        pageProduits pageProduits = new pageProduits();
        tabbedPane.addTab("Produits", pageProduits);
        tabbedPane.addTab("Fournisseurs", new pageFournisseurs());
        tabbedPane.addTab("Ventes", new pageVentes());
        tabbedPane.addTab("Rapports", new pageRapports());
        tabbedPane.addTab("Quitter", pageExit);

        // Récupérer données au changement de panel
        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                fetchAllData();
                pageProduits.updateFSelector();
            }
        });

        add(tabbedPane);
    }

    private static void fetchAllData() {
        FournisseurDAO.fetchData();
        ProduitDAO.fetchData();
    }

    public static void main(String[] args) {

        // UIManager.setLookAndFeel(new FlatLightLaf());
        try {

            // Parcourir les Look and Feel disponibles
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    // Utiliser le Look and Feel "Nimbus"
                    UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
                    break;
                }
            }
        } catch (Exception e) {
            // En cas d'erreur, afficher la trace
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Interface Interface = new Interface();
                Interface.setVisible(true);
            }
        });
    }
}
