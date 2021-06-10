package main.java.hr.java.covidportal.main;

import main.java.hr.java.covidportal.iznimke.DuplikatKontaktiraneOsobeException;
import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Osoba;
import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.model.Zupanija;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

import static main.java.hr.java.covidportal.main.MetodeZaValidaciju.hvatanjeNeoznaceneIznimkeBolestIstihSimptoma;

/**
 * Glavna klasa za, unos podataka i ispis podataka.
 */
public class Glavna {

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    protected static final int BROJ_ZUPANIJA = 3;
    protected static final int BROJ_SIMPTOMA = 3;
    protected static final int BROJ_BOLESTI = 4;
    protected static final int BROJ_OSOBA = 4;

    /**
     * Glavna metoda za izvršavanje programa, u kojoj se radi unos podataka i pozivaju
     * dodatne druge metode.
     *
     * @param args ulazni parametri kod pokretanja glavne metode
     */
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        Zupanija[] zupanije = new Zupanija[BROJ_ZUPANIJA];
        Simptom[] simptomi = new Simptom[BROJ_SIMPTOMA];
        Bolest[] bolesti = new Bolest[BROJ_BOLESTI];
        Osoba[] osobe = new Osoba[BROJ_OSOBA];

        System.out.println("Unesite podatke o " + BROJ_ZUPANIJA + " zupanije!");
        for (int i = 0; i < BROJ_ZUPANIJA; i++) {
            zupanije[i] = MetodeZaUnosPodataka.unosZupanije(scan, i);
        }

        System.out.println("Unesite podatke o " + BROJ_SIMPTOMA + " simptoma!");
        for (int i = 0; i < BROJ_SIMPTOMA; i++) {
            simptomi[i] = MetodeZaUnosPodataka.unosSimptoma(scan, i);
        }

        System.out.println("Unesite podatke o " + BROJ_BOLESTI + " bolesti!");
        for (int i = 0; i < BROJ_BOLESTI; i++) {
            hvatanjeNeoznaceneIznimkeBolestIstihSimptoma(scan, simptomi, bolesti, i);
        }

        System.out.println("Unesite podatke o " + BROJ_OSOBA + " osobe!");
        for (int i = 0; i < BROJ_OSOBA; i++) {
            try {
                osobe[i] = MetodeZaUnosPodataka.unosOsobe(scan, zupanije, bolesti, osobe, i);
            } catch (DuplikatKontaktiraneOsobeException ex) {
                logger.error("Dogodila se pogreska u programu!", ex);
            }
        }

        System.out.println("Popis osoba: ");
        for (Osoba osoba : osobe) {
            System.out.println(osoba.toString());
        }

        scan.close();
    }
}
