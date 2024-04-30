package Stock;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

public class Fournisseur {

    public int id;
    public String name;
    public String country;

    // private static ArrayList<Fournisseur> listeFournisseurs = new
    // ArrayList<Fournisseur>();

    public Fournisseur(String name, String country) {
        this.name = name;
        this.country = country;
    }

    // public void addProduit(Produit p) {
    // listeProduits.add(p);
    // }

    // public static ArrayList<Fournisseur> getFournisseurs() {
    // return listeFournisseurs;
    // }
    // public static String[] getFournisseursNames() {
    // String[] names;
    // for (Fournisseur fournisseur : listeFournisseurs) {
    // names.pus
    // }
    // }

    // Getters
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCountry() {
        return this.country;
    }

    // Setters

    public void setId(int value) {
        this.id = value;
    }

    public void setName(String value) {
        this.name = value;
    }

    public void setCountry(String value) {
        this.country = value;
    }
}
