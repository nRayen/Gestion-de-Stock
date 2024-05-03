package Stock;

public class Vente {

    public int id;
    public String clientName;
    public float total;

    public Vente(String client) {
        this.clientName = client;
    }

    // Getters
    public int getId() {
        return this.id;
    }

    public String getClientName() {
        return this.clientName;
    }

    public float getTotal() {
        return this.total;
    }

    // Setters
    public void setId(int value) {
        this.id = value;
    }

    public void setClientName(String value) {
        this.clientName = value;
    }

    public void setTotal(float value) {
        this.total = value;
    }
}
