package tar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.HommeFemmePk;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
import ma.projet.util.HibernateUtil;

public class TAR {

    private static Object femmeService;

    // Méthode utilitaire pour formater les dates
    private static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        return sdf.format(date);
    }

    // Méthode pour entrer une date au format dd/MM/yy
    private static Date entrerDate(String prompt) {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        Date date = null;
        boolean dateValide = false;

        while (!dateValide) {
            try {
                System.out.println(prompt);
                String input = scanner.nextLine();
                date = dateFormat.parse(input);
                dateValide = true;
            } catch (ParseException e) {
                System.out.println("Date invalide. Veuillez entrer une date au format dd/MM/yy.");
            }
        }

        return date;
    }

    public static void main(String[] args) throws ParseException {

        HommeService hs = new HommeService();
        FemmeService fs = new FemmeService();
        MariageService ms = new MariageService();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

        //Créer 10 femmes et 5 hommes,
        //fs.create(new Femme("Bamhammed","Fatima","0635269877","Casablanca",sdf.parse("25/07/1969")));
        //fs.create(new Femme("Alami","Fadwa","0625369477","Tanger",sdf.parse("25/11/1999")));
        // fs.create(new Femme("Bamhammed","Hafssa","0735689227","Agadir",sdf.parse("12/10/2003")));
        // fs.create(new Femme("Benani","Leila","0635269877","Rabat",sdf.parse("13/07/1975")));
        //fs.create(new Femme("Lahlou","Manar","0523669127","Sali",sdf.parse("31/12/2005")));
        // fs.create(new Femme("Chadi","Anissa","0632968877","Casablance",sdf.parse("29/02/2000")));
        // fs.create(new Femme("Bamhammed","Sofia","0765982477","Mèknes",sdf.parse("02/06/2005")));
        // fs.create(new Femme("Baraka","Malak","0632659847","Marrakech",sdf.parse("03/08/1980")));
        // fs.create(new Femme("Idressi","Amira","0707374644","El Jadida",sdf.parse("31/07/1990")));
        // fs.create(new Femme("Alaoui","Inasse","0698732564","Fes",sdf.parse("01/01/1969")));
        // hs.create(new Homme("Berrada","Salim","063269251477","Fes",sdf.parse("30/11/1960")));
        //hs.create(new Homme("Bamhammed","Youssef","0635269877","Tanger",sdf.parse("20/02/1997")));
        // hs.create(new Homme("Alaoui","Sami","0623698777","Casablanca",sdf.parse("03/03/1993")));
        //hs.create(new Homme("Chaabi","Anass","0765983247","Oujda",sdf.parse("25/02/1990")));
        // hs.create(new Homme("Rahhali","Ali","0706359877","Zagora",sdf.parse("31/07/1969")));
        //Créer des mariages
        // Mariage m1 = new Mariage(hs.findById(11),fs.findById(1),new Date(),new Date(),5);
        //m1.setPk(new HommeFemmePk(11,1,new Date()));
        // ms.create(m1);
       // Mariage m2 = new Mariage(hs.findById(12), fs.findById(2), sdf.parse("25/02/2020"), null, 3);
       // m2.setPk(new HommeFemmePk(12, 2, new Date()));
        //ms.create(m2);
        // Entrer des dates depuis le clavier
        Date dateDebut = entrerDate("Entrez la date de début (format dd/MM/yyyy) : ");
        Date dateFin = entrerDate("Entrez la date de fin (format dd/MM/yyyy) : ");

        // Afficher la liste des femmes
        List<Femme> femmes = fs.findAll();
        if (femmes != null) {
            for (Femme femme : femmes) {
                System.out.println(femme.getNom() + " " + femme.getPrenom());
            }
        } else {
            System.out.println("Erreur lors de la récupération des femmes.");
        }

        // Afficher la femme la plus âgée (simple exemple avec la première de la liste triée)
        femmes.sort((f1, f2) -> f1.getDateNaissance().compareTo(f2.getDateNaissance()));
        Femme plusAgee = femmes.get(0);
        System.out.println("Femme la plus âgée: " + plusAgee.getNom() + " née le " + formatDate(plusAgee.getDateNaissance()));

        // Afficher les épouses d'un homme passé en paramètre
        List<Mariage> mariages = hs.getEpousesEntreDates(hs.findById(11), dateDebut, dateFin);
        mariages.forEach(m -> System.out.println("Femme : " + m.getFemme().getNom() + " Date Début : " + formatDate(m.getDateDebut())));

        // Afficher le nombre d'enfants d'une femme passée en paramètre entre deux dates
        long nombreEnfants = fs.getNombreEnfantsEntreDates(fs.findById(1), dateDebut, dateFin);
        System.out.println("Nombre d'enfants: " + nombreEnfants);

        // Afficher la liste des femmes mariées deux fois ou plus
        List<Femme> femmesMarieesDeuxFois = fs.getFemmesMarieesDeuxFoisOuPlus();
        femmesMarieesDeuxFois.forEach(f -> System.out.println(f.getNom()));

        // Afficher le nombre d'hommes mariés par quatre femmes entre deux dates
        long nombreHommesMariees = hs.getNombreHommesMarieParQuatreFemmesEntreDates(dateDebut, dateFin);
        System.out.println("Nombre d'hommes mariés par quatre femmes: " + nombreHommesMariees);

        // Afficher les mariages d'un homme passé en paramètre avec des dates formatées
        Homme homme = hs.findById(11);
        if (homme != null) {
            hs.afficherMariages(homme);
        } else {
            System.out.println("Aucun homme trouvé avec cet ID.");
        }

        Homme homme1 = hs.findById(12);
        if (homme1 != null) {
            hs.afficherMariages(homme1);
        } else {
            System.out.println("Aucun homme trouvé avec cet ID.");
        }
    }

}
