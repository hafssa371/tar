package ma.projet.beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
    @NamedQuery(name = "nombreEnfantsEntreDates",
                query = "SELECT SUM(m.nbEnfant) FROM Mariage m WHERE m.femme.id = :femmeId AND m.dateDebut BETWEEN :debut AND :fin")
})
public class Mariage implements Serializable {
    @EmbeddedId
    private HommeFemmePk pk;
    @ManyToOne
    @JoinColumn(name = "homme", insertable = false, updatable = false)
    private Homme homme;
    @ManyToOne
    @JoinColumn(name = "femme", insertable = false, updatable = false)
    private Femme femme;
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    @Column(name = "nbEnfant")
    private int nbEnfant;

    public Mariage() {
    }

    public Mariage(HommeFemmePk pk, Homme homme, Femme femme, Date dateDebut, Date dateFin, int nbEnfant) {
        this.pk = pk;
        this.homme = homme;
        this.femme = femme;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbEnfant = nbEnfant;
    }

    public Mariage(Homme homme, Femme femme, Date dateDebut, Date dateFin, int nbEnfant) {
        this.homme = homme;
        this.femme = femme;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbEnfant = nbEnfant;
    }
    
    

     

    public HommeFemmePk getPk() {
        return pk;
    }

    public void setPk(HommeFemmePk pk) {
        this.pk = pk;
    }

    public Homme getHomme() {
        return homme;
    }

    public void setHomme(Homme homme) {
        this.homme = homme;
    }

    public Femme getFemme() {
        return femme;
    }

    public void setFemme(Femme femme) {
        this.femme = femme;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int getNbEnfant() {
        return nbEnfant;
    }

    public void setNbEnfant(int nbEnfant) {
        this.nbEnfant = nbEnfant;
    }
    
    
    
    
    
}
