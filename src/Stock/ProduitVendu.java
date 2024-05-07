package Stock;

import DataAccessObject.ProduitDAO;

public class ProduitVendu {
    int id;
    int id_vente;
    int id_produit;
    int quantité;
    Float price;
    Float total;

    ProduitDAO pDao = new ProduitDAO();

    public ProduitVendu(int id_produit, int quantité) {
        this.id_produit = id_produit;
        this.quantité = quantité;
        this.price = pDao.get(id_produit).getPrice();
        this.total = this.price * this.quantité;
    }

    public ProduitVendu(int id, int id_vente, int id_produit, int quantité) {
        this.id = id;
        this.id_vente = id_vente;
        this.id_produit = id_produit;
        this.quantité = quantité;
        this.price = pDao.get(id_produit).getPrice();
        this.total = this.price * this.quantité;
    }

    // Getters
    public int getId() {
        return this.id;
    }

    public int getId_vente() {
        return this.id_vente;
    }

    public int getId_produit() {
        return this.id_produit;
    }

    public int getQuantité() {
        return this.quantité;
    }

    public Float getPrice() {
        return this.price;
    }

    public Float getTotal() {
        return this.total;
    }

    // Setters
    public void setId(int value) {
        this.id = value;
    }

    public void setId_vente(int value) {
        this.id_vente = value;
    }

    public void setId_produit(int value) {
        this.id_produit = value;
    }

    public void setQuantité(int value) {
        this.quantité = value;
    }

    public void setPrice(Float value) {
        this.price = value;
    }

    public void setTotal(Float value) {
        this.total = value;
    }
}
