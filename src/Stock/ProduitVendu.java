package Stock;

public class ProduitVendu {
    int id;
    int id_vente;
    int id_produit;
    int quantité;
    Float price;
    Float total;

    public ProduitVendu(int id_produit, int quantité) {
        this.id_produit = id_produit;
        this.quantité = quantité;
    }
}
