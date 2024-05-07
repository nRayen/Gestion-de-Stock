package Stock;

public class Fournisseur {

    private int id;
    private String name;
    private String country;

    public Fournisseur(String name, String country) {
        this.name = name;
        this.country = country;
    }

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
