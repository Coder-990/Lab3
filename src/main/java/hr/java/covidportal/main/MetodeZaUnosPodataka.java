package main.java.hr.java.covidportal.main;

import main.java.hr.java.covidportal.iznimke.DuplikatKontaktiraneOsobeException;
import main.java.hr.java.covidportal.model.*;

import java.util.Scanner;

import static main.java.hr.java.covidportal.main.MetodeZaValidaciju.hvatanjeOznaceneIznimkeDuplikataOsobe;

/**
 * Klasa svih pomocnih metoda za unos podataka koje se pozivaju prilikom izvrsavanja programa
 */
public class MetodeZaUnosPodataka {
    /**
     * Služi za stvaranje objekta zupanije, prima objekt klase tipa Scaner i Integer, a vraca objekt klase tipa Zupanija
     * @param scan Scanner objekt za slanje podataka u racunalo preko tipkovnice
     * @param i Integer cjelobrojna vrijednost oznacava redBr zupanije
     * @return Zupanija stvoren novi objekt zupanije
     */
    protected static Zupanija unosZupanije(Scanner scan, Integer i) {

        System.out.print("Unesite naziv " + (i + 1) + ". zupanije: ");
        String nazivZupanije = MetodeZaValidaciju.provjeraPraznogStringaBroja(scan);
        System.out.print("Unesite broj stanovnika za " + (i + 1) + ". zupaniju: ");
        Integer brojStanovnika = MetodeZaValidaciju.IntegerExHandler(scan);

        return new Zupanija(nazivZupanije, brojStanovnika);
    }

    /**
     * Služi za stvaranje objekta simptoma, prima objekt klase tipa Scaner i Integer, a vraca objekt klase tipa Simptom
     * @param scan Scanner objek za slanje podataka u racunalo preko tipkovnice
     * @param i Integer cjelobrojna vrijednost oznacava redBr simptoma
     * @return Simptom stvoren novi objekt simptoma
     */
    protected static Simptom unosSimptoma(Scanner scan, Integer i) {

        System.out.print("Unesite naziv " + (i + 1) + ". simptoma: ");
        String nazivSimptoma = MetodeZaValidaciju.provjeraPraznogStringaBroja(scan);
        System.out.print("Unesite vrijednost " + (i + 1) + ". simptoma (RIJETKO, SREDNJE ILI CESTO): ");
        String vrijednostSimptoma = MetodeZaOdabir.odabirVrijednostiBolesti(scan);

        return new Simptom(nazivSimptoma, vrijednostSimptoma);
    }

    /**
     * Služi za stvaranje objekta bolesti, prima objekt klase tipa Scaner, polje objekata klase tipa Simptom i objekt klase tipa Integer, a vraca objekt klase tipa Bolest
     * @param scan Scanner objekt za slanje podataka u racunalo preko tipkovnice
     * @param simptomi Simptom[] polje objekata služi za unos simptoma za svaku bolest
     * @param i Integer cjelobrojna vrijednost oznacava redBr bolesti
     * @return Bolest stvoren novi objekt bolesti
     */
    protected static Bolest unosBolesti(Scanner scan, Simptom[] simptomi, Integer i) {

        Integer odabirVirusaBolesti = MetodeZaOdabir.odabirBolestiVirusa(scan);
        System.out.print("Unesite naziv " + (i + 1) + ". bolesti ili virusa: ");
        String nazivBolesti = MetodeZaValidaciju.provjeraPraznogStringaBroja(scan);
        System.out.print("Unesite broj simptoma: ");
        Integer brojSimptoma = MetodeZaValidaciju.IntegerExHandler(scan);
        Simptom[] simptomBolesti = new Simptom[brojSimptoma];
        for (int j = 0; j < brojSimptoma; j++) {
            System.out.println("Odaberite " + (j + 1) + ". simptom: ");
            simptomBolesti[j] = MetodeZaOdabir.odabirSimptoma(scan, simptomi);
        }
        if (odabirVirusaBolesti == 1) {
            return new Bolest(nazivBolesti, simptomBolesti);
        } else {
            return new Virus(nazivBolesti, simptomBolesti);
        }
    }

    /**
     * Služi za stvaranje objekta osoba, prima objekt klase tipa Scaner, polje objekata klase tipa:Zupanija, Bolest i Osoba, objekt klase tipa Integer, a vraca objekt klase tipa Osoba
     * @param scan Scanner objekt za slanje podataka u racunalo preko tipkovnice
     * @param zupanije Zupanije[] polje objekata služi za odabir zupanija za svaku osobu
     * @param bolesti Bolest[] polje objekata služi za unos bolesti za svaku osobu
     * @param osobe Osobe[] polje objekata služi za unos kontaktirane osobe za svaku osobu
     * @param i Integer cjelobrojna vrijednost oznacava redBr osobe
     * @return Osoba stvoren novi objekt osobe
     * @throws DuplikatKontaktiraneOsobeException nova označena iznimka koja se baca svaki put kada se odabere dva puta ista soba
     */
    protected static Osoba unosOsobe(Scanner scan, Zupanija[] zupanije, Bolest[] bolesti, Osoba[] osobe, Integer i) throws DuplikatKontaktiraneOsobeException {

        System.out.print("Unesite ime " + (i + 1) + ". osobe: ");
        String imeOsobe = MetodeZaValidaciju.provjeraPraznogStringaBroja(scan);
        System.out.print("Unesite prezime " + (i + 1) + ". osobe: ");
        String prezimeOsobe = MetodeZaValidaciju.provjeraPraznogStringaBroja(scan);
        System.out.print("Unesite starost " + (i + 1) + ". osobe: ");
        Integer starostOsobe = MetodeZaValidaciju.IntegerExHandler(scan);
        System.out.println("Odaberite zupaniju prebivalista " + (i + 1) + " osobe: ");
        Zupanija prebivalisteOsobe = MetodeZaOdabir.odabirZupanije(scan, zupanije);
        System.out.println("Odaberite bolest " + (i + 1) + " osobe : ");
        Bolest bolestOsobe = MetodeZaOdabir.odabirBolesti(scan, bolesti);
        Osoba osoba = new Osoba.Builder()
                .ime(imeOsobe)
                .prezime(prezimeOsobe)
                .starost(starostOsobe)
                .zupanija(prebivalisteOsobe)
                .zarazenBolescu(bolestOsobe)
                .build();
        if (i > 0) {
            return unosKontaktiraneOsobe(scan, osobe, osoba, i);
        }
        return osoba;
    }

    /**
     * Služi za stvaranje objekta osoba, prima objekt klase tipa Scaner, polje objekata klase tipa Osoba, objekt klase tipa Osoba i Integer, a vraca objekt klase tipa Osoba
     * @param scan Scanner objekt za slanje podataka u racunalo preko tipkovnice
     * @param osobe Osobe[] polje objekata služi za unos kontaktirane osobe za svaku osobu koja se tek odabire nakon prve unesene osobe
     * @param osoba Osobe objekt prima podatke o osobi kod stvaranja kontaktirane osobe
     * @param i Integer cjelobrojna vrijednost oznacava redBr osobe
     * @return Osoba stvoren novi objekt osobe
     */
    protected static Osoba unosKontaktiraneOsobe(Scanner scan, Osoba[] osobe, Osoba osoba, Integer i) {

        System.out.print("Unesite broj osoba koje su bile u kontaktu s tom osobom: ");
        Integer brojKontaktiranihOsoba = MetodeZaValidaciju.provjeraBrojaKontaktiranih(scan, i);
        Osoba[] kontaktiraneOsobe = new Osoba[brojKontaktiranihOsoba];
        System.out.println("Odaberite osobe koje su bile u kontaktu  s tom osobom :");
        for (int j = 0; j < brojKontaktiranihOsoba; j++) {
            hvatanjeOznaceneIznimkeDuplikataOsobe(scan, osobe, kontaktiraneOsobe, j);
        }
        return new Osoba.Builder()
                .ime(osoba.getIme())
                .prezime(osoba.getPrezime())
                .starost(osoba.getStarost())
                .zupanija(osoba.getZupanija())
                .zarazenBolescu(osoba.getZarazenBolescu())
                .kontaktiraneOsobe(kontaktiraneOsobe)
                .build();
    }
}
