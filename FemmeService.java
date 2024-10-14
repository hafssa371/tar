/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.service;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import ma.projet.beans.Femme;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author pc
 */
public class FemmeService implements IDao<Femme> {

    @Override
    public boolean create(Femme o) {
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
    public Femme findById(int id) {
        Session session = null;
        Femme h = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            h = (Femme) session.get(Femme.class, id);
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
    public List<Femme> findAll() {
        Session session = null;
        List<Femme> femmes = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            femmes = session.createQuery("from Femme").list();
            session.getTransaction().commit();
            return femmes;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return femmes;
    }

        // Méthode pour renvoyer le nombre d'enfants d'une femme entre deux dates
    public long getNombreEnfantsEntreDates(Femme femme, Date debut, Date fin) {
        long nombreEnfants = 0; // Initialiser le nombre d'enfants
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT SUM(m.nbEnfant) FROM Mariage m WHERE m.femme.id = :femmeId AND m.dateDebut BETWEEN :debut AND :fin";
            Query query = session.createQuery(hql);

            query.setParameter("femmeId", femme.getId());
            query.setParameter("debut", debut);
            query.setParameter("fin", fin);

            Long result = (Long) query.uniqueResult();
            if (result != null) {
                nombreEnfants = result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return nombreEnfants;
    }

    //Méthode pour donner les femmes mariées 2 fois

    public List<Femme> getFemmesMarieesDeuxFoisOuPlus() {
        List<Femme> femmes = null;  // Liste pour stocker les résultats
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            // Appeler la requête nommée "femmesMarieesDeuxFoisOuPlus"
            Query query = session.getNamedQuery("femmesMarieesDeuxFoisOuPlus");

            // Exécuter la requête et obtenir la liste des femmes
            femmes = query.list();
        } catch (Exception e) {
            e.printStackTrace();  // Gérer les exceptions
        }
        return femmes;  // Retourner la liste des femmes mariées deux fois ou plus
    }

    public Long getNombreMariages(Long femmeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Long count = null;
        try {
            Query query = session.createQuery("SELECT COUNT(m) FROM Mariage m WHERE m.femme = :femmeId");
            query.setParameter("femmeId", femmeId);
            count = (Long) query.uniqueResult();
        } catch (Exception e) {
            System.err.println("Erreur lors du comptage des mariages : " + e.getMessage());
        } finally {
            session.close();
        }
        return count;
    }
}
