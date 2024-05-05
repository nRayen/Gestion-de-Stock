package Stock;

import java.sql.Date;

public class Vente {

    public int id;
    public String clientName;
    public float total;
    public String date;

    public Vente(String client, float total, String date) {
        this.clientName = client;
        this.total = total;
        this.date = date;
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
}
