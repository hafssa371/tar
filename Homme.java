package ma.projet.beans;

import java.util.Date;
import javax.persistence.Entity;


@Entity
public class Homme extends Personne {

    public Homme() {
    }

    public Homme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }
    
    
    
}
