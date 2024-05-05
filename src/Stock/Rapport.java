package Stock;

import java.sql.Date;

public class Rapport {

    int id;
    String dateDébut;
    String dateFin;
    String date;

    public Rapport(String dateDébut, String dateFin) {
        this.dateDébut = dateDébut;
        this.dateFin = dateFin;

        java.util.Date utilDate = new java.util.Date();
        this.date = new java.sql.Date(utilDate.getTime()).toString();

    }

    public int getId() {
        return this.id;
    }

    public void setId(int value) {
        this.id = value;
    }

    public String getDateDébut() {
        return this.dateDébut;
    }

    public void setDateDébut(String value) {
        this.dateDébut = value;
    }

    public String getDateFin() {
        return this.dateFin;
    }

    public void setDateFin(String value) {
        this.dateFin = value;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String value) {
        this.date = value;
    }
}
