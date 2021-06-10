package main.java.hr.java.covidportal.main;

import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Osoba;
import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.model.Zupanija;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Scanner;

import static main.java.hr.java.covidportal.main.Glavna.*;

/**
 * Klasa svih pomocnih metoda za odabir podataka koje se pozivaju prilikom izvrsavanja programa
 */
public class MetodeZaOdabir {

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
    private static String messageInfoError = "Pogreska, pokusali ste unijeti: ";
    private static String messageRepeatInput = ",molim ponovite Vas unos: ";

    /**
     * Služi za odabir objekta zupanije, prima objekt klase tipa Scaner i polje objekta klase tipa Zupanija, a vraca objekt klase tipa Zupanija pod odabranim indeksom.
     * @param scan Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @param zupanije Zupanije[] polje objekata služi za odabir zupanija.
     * @return Zupanija odaberen objekt zupanije pod određenim indeksom.
     */
    protected static Zupanija odabirZupanije(Scanner scan, Zupanija[] zupanije) {

        int i = 0;
        for (Zupanija zupanija : zupanije) {
            if (Optional.ofNullable(zupanija).isPresent()) {
                System.out.println((i += 1) + ". " + zupanija.getNaziv());
            }
        }
        System.out.print("Odabir: ");
        int odabir = MetodeZaValidaciju.provjeraBrojaSaListe(scan, BROJ_ZUPANIJA);
        return zupanije[odabir - 1];
    }

    /**
     * Služi za odabir objekta simptomi, prima objekt klase tipa Scaner i polje objekta klase tipa Simptom, a vraca objekt klase tipa Simptom pod odabranim indeksom.
     * @param scan Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @param simptomi Simptom[] polje objekata služi za odabir simptoma kod unosa bolesti.
     * @return Simptom odaberen objekt simptom pod određenim indeksom.
     */
    protected static Simptom odabirSimptoma(Scanner scan, Simptom[] simptomi) {

        int i = 0;
        for (Simptom simptom : simptomi) {
            if (Optional.ofNullable(simptom).isPresent()) {
                System.out.println((i += 1) + ". " + simptom.getNaziv() + " - " + simptom.getVrijednost());
            }
        }
        System.out.print("Odabir: ");
        int odabir = MetodeZaValidaciju.provjeraBrojaSaListe(scan, BROJ_SIMPTOMA);
        return simptomi[odabir - 1];
    }

    /**
     * Služi za odabir objekta bolesti, prima objekt klase tipa Scaner i polje objekta klase tipa Bolest, a vraca objekt klase tipa Bolest pod odabranim indeksom.
     * @param scan Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @param bolesti Bolest[] polje objekata služi za odabir bolesti za svaku osobu.
     * @return Bolest odaberen objekt bolest pod određenim indeksom.
     */
    protected static Bolest odabirBolesti(Scanner scan, Bolest[] bolesti) {

        int i = 0;
        for (Bolest bolest : bolesti) {
            if (Optional.ofNullable(bolest).isPresent()) {
                System.out.println((i += 1) + ". " + bolest.getNaziv());
            }
        }
        System.out.print("Odabir: ");
        Integer odabir = MetodeZaValidaciju.provjeraBrojaSaListe(scan, BROJ_BOLESTI);
        return bolesti[odabir - 1];
    }

    /**
     * Služi za odabir objekta osobe, prima objekt klase tipa Scaner i polje objekta klase tipa Osoba, a vraca objekt klase tipa Osoba pod odabranim indeksom.
     * @param scan Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @param osobe Osobe[] polje objekata služi za odabir kontaktirane osobe za svaku osobu koja se tek odabire nakon prve unesene osobe.
     * @return Osoba odaberen objekt osoba pod određenim indeksom.
     */
    protected static Osoba odabirOsobe(Scanner scan, Osoba[] osobe) {

        int i = 0;
        for (Osoba osoba : osobe) {
            if (Optional.ofNullable(osoba).isPresent()) {
                System.out.println((i += 1) + ". " + osoba.getIme() + " " + osoba.getPrezime());
            }
        }
        System.out.print("Odabir: ");
        Integer odabir = MetodeZaValidaciju.provjeraBrojaSaListe(scan, brojKontaktiranihOsoba(osobe));
        return osobe[odabir - 1];
    }

    /**
     * Kod unosa služi za odabir tipa klase Bolest ili Virus koja prima objekt klase tipa Scanner, a vraca objekt klase tipa Integer.
     * @param scan Scanner objekt za slanje podataka u racunalo preko tipkovnice
     * @return Integer odabreni indeks bolesti ili virusa pod kojim se nalazi
     */
    protected static Integer odabirBolestiVirusa(Scanner scan) {

        int odabir;
        System.out.print("Unosite li bolest ili virus osobe?\n1) BOLEST\n2) VIRUS\nOdabir: ");
        do {
            odabir = MetodeZaValidaciju.IntegerExHandler(scan);
            if (odabir < 1 || odabir > 2) {
                logger.info(messageInfoError + " '" + odabir + "'");
                System.out.print("Niste odabrali ni bolest ni virus " + messageRepeatInput);
            }
        } while (odabir < 1 || odabir > 2);
        return odabir;
    }

    /**
     * Kod unosa bolesti služi za unos između tri vrijednosti klase tipa Simptom, prima objekt klase tipa Scanner, a vraca objekt klase tipa String.
     * @param scan Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @return String unesena jedna od tri vrijednosti klase tipa Simptom.
     */
    protected static String odabirVrijednostiBolesti(Scanner scan) {

        do {
            String inputString = scan.nextLine().toUpperCase();
            if ("RIJETKO".equals(inputString) || "SREDNJE".equals(inputString) || "CESTO".equals(inputString)) {
                return inputString;
            } else {
                logger.info(messageInfoError + " '" + inputString + "' ");
                System.out.print("Unesite jedan od ponudenih odgovora (RIJETKO, SREDNJE ILI CESTO), ponovite unos: ");
            }
        } while (true);
    }

    /**
     * Brojac osoba koji služi kod odabira osoba da pošalje točan broj osoba koliko ih mora biti, prima polje objekata klase tipa Osoba, a vraca objekt klase tipa Integer.
     * @param osobe Osobe[] polje objekata služi za prebrojavanje unesenih kontaktiranih osoba.
     * @return Integer broj točan kontaktiranih osoba.
     */
    protected static Integer brojKontaktiranihOsoba(Osoba[] osobe) {

        Integer brojac = 0;
        for (Osoba osoba : osobe) {
            if (Optional.ofNullable(osoba).isPresent()) {
                brojac++;
            }
        }
        return brojac;
    }
}