package DataAccessObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import Stock.Fournisseur;

public class FournisseurDAO implements Dao<Fournisseur> {

    public List<Fournisseur> fournisseurs = new ArrayList<>();

    @Override
    public Optional<Fournisseur> get(int id) {
        return Optional.ofNullable(fournisseurs.get((int) id));
    }

    @Override
    public List<Fournisseur> getAll() {
        return fournisseurs;
    }

    @Override
    public void save(Fournisseur user) {
        fournisseurs.add(user);
    }

    @Override
    public void update(Fournisseur fournisseur, String[] params) {
        fournisseur.setName(Objects.requireNonNull(
                params[0], "Name cannot be null"));
        fournisseur.setCountry(Objects.requireNonNull(
                params[1], "Email cannot be null"));

        fournisseurs.add(fournisseur);
    }

    @Override
    public void delete(Fournisseur fournisseur) {
        fournisseurs.remove(fournisseur);
    }
}
