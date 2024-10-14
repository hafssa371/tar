package ma.projet.util;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe utilitaire Hibernate avec une méthode pratique pour obtenir
 * l'objet SessionFactory.
 *
 * @author pc
 */
public class HibernateUtil {

    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure("ma/projet/config/hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            logger.error("La création initiale de la SessionFactory a échoué.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // Méthode pour fermer la SessionFactory
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
