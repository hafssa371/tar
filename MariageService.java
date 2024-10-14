package ma.projet.service;

import java.util.List;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;


public class MariageService implements IDao<Mariage> {

    @Override
    public boolean create(Mariage o) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(o);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        }finally{
            session.close();
        }
        return false;
    }

    @Override
    public Mariage findById(int id) {
        Session session = null;
        Mariage h = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            h = (Mariage) session.get(Mariage.class, id);
            session.getTransaction().commit();
            return h;
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
        }finally{
            session.close();
        }
        return h;
    }

    @Override
    public List<Mariage> findAll() {
        Session session = null;
        List<Mariage>  mariages = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            mariages = session.createQuery("from Mariage").list();
            session.getTransaction().commit();
            return mariages;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        }finally{
            session.close();
        }
        return mariages;
    } 
    
}
