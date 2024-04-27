package Stock;

import java.awt.Image;

public class Produit {

    public String name;
    public float price;
    public int stock;
    public Fournisseur fournisseur = null;
    public Image image = null;

    public Produit(String name, float price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
}
