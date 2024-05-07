package Stock;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class Rapport {

    int id;
    String dateDébut;
    String dateFin;
    String date;

    public Rapport(String dateDébut, String dateFin) {
        this.dateDébut = dateDébut;
        this.dateFin = dateFin;

        java.util.Date utilDate = new java.util.Date();
        this.date = createStringDate(new java.sql.Date(utilDate.getTime()));

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

    public String getDateFin() {
        return this.dateFin;
    }

    public void setDateDébut(String value) {
        this.dateDébut = value;
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

    public Date getSQLDateFin() {
        return createSQLDate(this.dateFin);
    }

    public Date getSQLDateDébut() {
        return createSQLDate(this.dateDébut);
    }
}
