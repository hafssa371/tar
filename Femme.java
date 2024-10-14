package ma.projet.beans;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(
    name = "femmesMarieesDeuxFoisOuPlus",
    query = "SELECT f FROM Femme f WHERE (SELECT COUNT(m) FROM Mariage m WHERE m.femme = f) >= 2"
)
public class Femme extends Personne {

    public Femme() {
    }

    public Femme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    } 
    
}
