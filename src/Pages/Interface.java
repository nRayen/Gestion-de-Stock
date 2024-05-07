package Pages;

import javax.swing.*;
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
        setTitle("Gestion des Stocks boutique pre^t à porter");
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
        pageVentes pageVentes = new pageVentes();
        tabbedPane.addTab("Fournisseurs", new pageFournisseurs());
        tabbedPane.addTab("Produits", pageProduits);
        tabbedPane.addTab("Ventes", pageVentes);
        tabbedPane.addTab("Rapports", new pageRapports());
        tabbedPane.addTab("Quitter", pageExit);

        // Récupérer données au changement de panel
        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                fetchAllData();
                pageProduits.updateFSelector();
                pageProduits.updatePList();
                pageVentes.updatePSelector();
            }
        });

        add(tabbedPane);
    }

    private static void fetchAllData() {
        FournisseurDAO.fetchData();
        ProduitDAO.fetchData();
    }
}
