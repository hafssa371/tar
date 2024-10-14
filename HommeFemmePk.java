package ma.projet.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Embeddable;


@Embeddable
public class HommeFemmePk implements Serializable {
    private int homme;
    private int femme;
    private Date dateMise;

    public HommeFemmePk() {
    }

    public HommeFemmePk(int homme, int femme, Date dateMise) {
        this.homme = homme;
        this.femme = femme;
        this.dateMise = dateMise;
    }

    public int getHomme() {
        return homme;
    }

    public void setHomme(int homme) {
        this.homme = homme;
    }

    public int getFemme() {
        return femme;
    }

    public void setFemme(int femme) {
        this.femme = femme;
    }

    public Date getDateMise() {
        return dateMise;
    }

    public void setDateMise(Date dateMise) {
        this.dateMise = dateMise;
    }
    
        @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HommeFemmePk)) return false;
        HommeFemmePk that = (HommeFemmePk) o;
        return Objects.equals(homme, that.homme) && Objects.equals(femme, that.femme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homme, femme);
    }

    
    
    
}
