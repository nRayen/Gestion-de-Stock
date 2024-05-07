package Stock;

public class Produit {

    public int id;
    public String name;
    public float price;
    public int quantité;
    public int idFournisseur;

    public Produit(String name, float price, int quantité, int idFournisseur) {
        this.name = name;
        this.price = price;
        this.quantité = quantité;
        this.idFournisseur = idFournisseur;
    }

    // Getters
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public float getPrice() {
        return this.price;
    }

    public int getQuantité() {
        return this.quantité;
    }

    public int getIDFournisseur() {
        return this.idFournisseur;
    }

    // Setters
    public void setId(int value) {
        this.id = value;
    }

    public void setName(String value) {
        this.name = value;
    }

    public void setPrice(float value) {
        this.price = value;
    }

    public void setQuantité(int value) {
        this.quantité = value;
    }

    public void setIDFournisseur(int value) {
        this.idFournisseur = value;
    }
}
