package ma.projet.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class HommeService implements IDao<Homme> {

    @Override
    public boolean create(Homme o) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(o);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public Homme findById(int id) {
        Session session = null;
        Homme h = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            h = (Homme) session.get(Homme.class, id);
            session.getTransaction().commit();
            return h;
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return h;
    }

    @Override
    public List<Homme> findAll() {
        Session session = null;
        List<Homme> hommes = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            hommes = session.createQuery("from Homme").list();
            session.getTransaction().commit();
            return hommes;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return hommes;
    }

    // Méthode pour afficher les épouses entre deux dates
    public List<Mariage> getEpousesEntreDates(Homme homme, Date debut, Date fin) {
        Session session = null;
        List<Mariage> mariages = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            // Utilisation de Query sans génériques
            Query query = session.createQuery(
                    "from Mariage where homme = :homme and dateDebut between :debut and :fin");

            query.setParameter("homme", homme);
            query.setParameter("debut", debut);
            query.setParameter("fin", fin);

            // Exécuter la requête et obtenir la liste des mariages
            mariages = query.list();  // Utilisation de list() au lieu de getResultList() dans les anciennes versions
        } catch (Exception e) {
            e.printStackTrace();  // Afficher les erreurs
        } finally {
            if (session != null) {
                session.close();  // Fermer la session
            }
        }
        return mariages;  // Retourner la liste des résultats
    }

    //Méthode pour afficher un homme marié 4 femmes
    public long getNombreHommesMarieParQuatreFemmesEntreDates(Date debut, Date fin) {
        long nombreHommes = 0;  // Valeur par défaut
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            // Créer un critère pour l'entité "Mariage"
            Criteria criteria = session.createCriteria(Mariage.class, "mariage");

            // Joindre avec l'entité "Homme"
            criteria.createAlias("mariage.homme", "homme");

            // Ajouter des restrictions pour filtrer les mariages entre deux dates
            criteria.add(Restrictions.between("mariage.dateDebut", debut, fin));

            // Grouper par l'identifiant de l'homme
            criteria.setProjection(Projections.projectionList()
                    .add(Projections.groupProperty("homme.id"))
                    .add(Projections.count("mariage.id").as("nombreMariages")));

            // Filtrer pour garder uniquement les hommes mariés 4 fois ou plus
            criteria.add(Restrictions.ge("nombreMariages", 4));

            // Obtenir la liste des résultats
            List results = criteria.list();

            // Le nombre d'hommes est égal à la taille de la liste des résultats
            nombreHommes = results.size();

        } catch (Exception e) {
            e.printStackTrace();  // Gérer les exceptions
        }

        return nombreHommes;  // Retourner le nombre d'hommes mariés à 4 femmes ou plus
    }

    // Méthode pour afficher les mariages d'un homme donné
    public void afficherMariages(Homme homme) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            // Requête HQL pour obtenir les mariages en cours (pas de date de fin)
            Query queryEnCours = session.createQuery(
                    "from Mariage where homme = :homme and dateFin is null");
            queryEnCours.setParameter("homme", homme);
            List<Mariage> mariagesEnCours = queryEnCours.list();

            // Requête HQL pour obtenir les mariages échoués (avec date de fin)
            Query queryEchoues = session.createQuery(
                    "from Mariage where homme = :homme and dateFin is not null");
            queryEchoues.setParameter("homme", homme);
            List<Mariage> mariagesEchoues = queryEchoues.list();

            // Afficher le nom de l'homme
            System.out.println("Nom : " + homme.getNom() + " " + homme.getPrenom());

            // Afficher les mariages en cours
            System.out.println("Mariages En Cours :");
            int i = 1;
            for (Mariage mariage : mariagesEnCours) {
                System.out.println(i + ". Femme : " + mariage.getFemme().getNom() + " " + mariage.getFemme().getPrenom()
                        + " Date Début : " + formatDate(mariage.getDateDebut())
                        + " Nbr Enfants : " + mariage.getNbEnfant());
                i++;
            }

            // Afficher les mariages échoués
            System.out.println("Mariages Échoués :");
            i = 1;
            for (Mariage mariage : mariagesEchoues) {
                System.out.println(i + ". Femme : " + mariage.getFemme().getNom() + " " + mariage.getFemme().getPrenom()
                        + " Date Début : " + formatDate(mariage.getDateDebut())
                        + " Date Fin : " + formatDate(mariage.getDateFin())
                        + " Nbr Enfants : " + mariage.getNbEnfant());
                i++;
            }

        } catch (Exception e) {
            // Gérer les exceptions ici (log, affichage, etc.)
            System.err.println("Erreur lors de l'affichage des mariages : " + e.getMessage());
        } finally {
            if (session != null) {
                session.close(); // Fermer la session si elle a été ouverte
            }
        }
    }

    // Méthode pour formater une date en chaîne de caractères (dd/MM/yyyy)
    private String formatDate(Date date) {
        if (date == null) {
            return "N/A";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
}
