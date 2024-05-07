package Stock;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vente {

    public int id;
    public String clientName;
    public float total;
    public String date;
    public List<ProduitVendu> produits = new ArrayList<ProduitVendu>();

    public Vente(String client, float total, String date) { // Constructeur de base
        this.clientName = client;
        this.total = total;
        this.date = date;
    }

    public Vente(int id, String client, float total, Date date) { // Constructeur pour les rapports
        this.id = id;
        this.clientName = client;
        this.total = total;
        this.date = createStringDate(date);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int value) {
        this.id = value;
    }

    public String getClientName() {
        return this.clientName;
    }

    public void setClientName(String value) {
        this.clientName = value;
    }

    public float getTotal() {
        return this.total;
    }

    public void setTotal(float value) {
        this.total = value;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String value) {
        this.date = value;
    }

    public List<ProduitVendu> getProduits() {
        return this.produits;
    }

    public Date createSQLDate(String date) {
        String[] n = date.split("/");
        List<String> list = Arrays.asList(n).reversed();
        String SQLDate = String.join("-", list);
        return java.sql.Date.valueOf(SQLDate);
    }

    public String createStringDate(Date date) {
        String[] n = date.toString().split("-");
        List<String> list = Arrays.asList(n).reversed();
        return String.join("/", list);
    }
}
