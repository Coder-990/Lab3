package main.java.hr.java.covidportal.main;

import main.java.hr.java.covidportal.iznimke.BolestIstihSimptomaException;
import main.java.hr.java.covidportal.iznimke.DuplikatKontaktiraneOsobeException;
import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Osoba;
import main.java.hr.java.covidportal.model.Simptom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.regex.Pattern;

import static main.java.hr.java.covidportal.main.MetodeZaOdabir.brojKontaktiranihOsoba;

/**
 * Klasa svih pomocnih metoda za validaciju kod unosa i odabir podataka koje se pozivaju prilikom izvrsavanja programa
 */
public class MetodeZaValidaciju {

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
    private static String messageRepeatInputEmptyString = "Pogreska, pokusali ste unijeti prazan string: ";
    private static String messageRepeatNumberFormatEx = "Pogreska, pokusali ste unijeti: ";
    private static String messageRepeatInput = ", molim ponovite Vas unos: ";

    /**
     * Kod unosa stringova služi za provjeru koja se izvršava nad unesenim podatkom da li je string prazan, broj ili sadrži razmake, prima objekt klase tipa Scanner, a vraca objekt klase tipa String.
     * @param scan Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @return String unesena validirana vrijednost klase tipa String.
     */
    protected static String provjeraPraznogStringaBroja(Scanner scan) {

        String inputString;
        String regex = "-?\\d+(\\.\\d+)?";
        Pattern pattern = Pattern.compile(regex);
        do {
            inputString = scan.nextLine().trim();
            if (pattern.matcher(inputString).matches()) {
                logger.error(messageRepeatNumberFormatEx + " '" + inputString + "' ");
                System.err.print(messageRepeatNumberFormatEx + " '" + inputString + "' " + messageRepeatInput);
            } else if (inputString.isBlank() || inputString.isEmpty()) {
                logger.error(messageRepeatInputEmptyString + " '" + inputString + "' ");
                System.err.print(messageRepeatInputEmptyString + " '" + inputString + "' " + messageRepeatInput);
            }
        } while (inputString.isBlank() || pattern.matcher(inputString).matches());
        return inputString;
    }

    /**
     * Kod unosa cjelobrojnih vrijednosti provjerava da li je unesena vrijednost negativan broj i slovo, znak ili bilo koji drugi tip podatka koji ne može pretvoriti u broj, prima objekt klase tipa Scanner, a vraca objekt klase tipa Integer.
     * @param scan Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @return Integer unesena validirana vrijednost klase tipa Integer.
     */
    protected static Integer IntegerExHandler(Scanner scan) {

        int inputInt = 0;
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        boolean vrtiPetlju;
        do {
            vrtiPetlju = false;
            try {
                inputInt = scan.nextInt();
                scan.nextLine();
                if (inputInt < 0) {
                    logger.error(messageRepeatNumberFormatEx + " '" + inputInt + "' " + messageRepeatInput);
                    System.err.print(messageRepeatNumberFormatEx + " '" + inputInt + "' " + messageRepeatInput);
                    vrtiPetlju = true;
                } else if (pattern.matcher(String.valueOf(inputInt)).matches()) {
                    return inputInt;
                }
            } catch (InputMismatchException ex) {
                logger.error(messageRepeatNumberFormatEx + " '" + inputInt + "' ", ex);
                System.err.print(messageRepeatNumberFormatEx + " '" + inputInt + "' " + messageRepeatInput);
                vrtiPetlju = true;
                scan.nextLine();
            }
        } while (vrtiPetlju);
        return inputInt;
    }

    /**
     * Služi za provjeru nad objektima klase tipa Bolest da li objekti imaju iste simptomske vrijednosti, a drugaciji naziv bolesti, prima objekt klase tipa Bolest, a ne vraća ništa.
     * @param bolestA Bolest provjera jednakosti jednog objekta tipa Bolest nad drugim tipa Bolest
     * @param bolestB Bolest provjera jednakosti jednog objekta tipa Bolest nad drugim tipa Bolest
     */
    private static void istiSimptom(Bolest bolestA, Bolest bolestB) {

        Simptom[] simptomiA = bolestA.getSimptomi();
        Simptom[] simptomiB = bolestB.getSimptomi();
        int brojac = 0;
        if (simptomiA.length == simptomiB.length) {
            for (Simptom simA : simptomiA) {
                for (Simptom simB : simptomiB) {
                    if (simA.getVrijednost().equals(simB.getVrijednost())) {
                        brojac++;
                    }
                }
            }
            if (brojac == simptomiA.length) {
                logger.error("Pogreska, dogodila se nova neoznacena iznimka, odabrana je bolest/virus sa istim simptomima!");
                throw new BolestIstihSimptomaException("Pogreska, odabrana je bolest/virus sa istim simptomima! '" + bolestA.getNaziv() + "' - '" + Arrays.toString(bolestA.getSimptomi()) + "'\n'" + bolestB.getNaziv() + "' - '" + Arrays.toString(bolestB.getSimptomi()) + "'");
            }
        }
    }

    /**
     * Služi za provjeru nad objektima klase tipa Bolest da li objekti imaju iste simptomske vrijednosti, a drugaciji naziv bolesti, prima objekt klase tipa Bolest i polje objekata klase tipaBolest, a ne vraća ništa.
     * @param bolest Bolest provjera jednakosti simptoma jednog objekta tipa Bolest nad poljem objekata klase tipa Bolest.
     * @param bolestiIstihSimptoma Bolest[] prosljeđivanje svih bolesti sa kojima se uspoređuje da li postoji bolest sa istim simptomima.
     */
    protected static void provjeraBolestiSaIstimSimptomima(Bolest bolest, Bolest[] bolestiIstihSimptoma) {

        for (Bolest b : bolestiIstihSimptoma) {
            if (Optional.ofNullable(b).isPresent() && Optional.ofNullable(bolest).isPresent()) {
                istiSimptom(b, bolest);
            }
        }
    }

    /**
     * Služi za provjeru nad objektima klase tipa Osoba da li objekti imaju iste odabrane osobe dva puta gdje se onda baca nova oznadena iznimka 'DuplikatKontaktiraneOsobeException', prima objekt klase tipa Osoba i polje objekata klase tipa Osoba, a ne vraća ništa.
     * @param osoba Osoba provjera jednakosti jednog objekta tipa Osoba nad poljem objekata klase tipa Osoba[].
     * @param osobe Osoba[] prosljeđivanje svih osoba sa kojima se uspoređuje da li postoji dva puta isto odabrana osoba.
     */
    protected static void provjeraDuplikataKontaktiranihOsoba(Osoba osoba, Osoba[] osobe) throws DuplikatKontaktiraneOsobeException {

        for (Osoba o : osobe) {
            if (Optional.ofNullable(o).isPresent() && Optional.ofNullable(osoba).isPresent() && o.equals(osoba)) {
                logger.error("Pogreska, dogodila se nova oznacena iznimka, odabrana je ista osoba vise puta!");
                throw new DuplikatKontaktiraneOsobeException("Pogreska, odabrana je ista osoba vise puta '" + osoba.getIme() + " " + osoba.getPrezime() + "'");
            }
        }
    }

    /**
     * Služi za hvatanje označene iznimke 'DuplikatKontaktiraneOsobeException' nad objektom klase tipa Osoba da li objekti imaju iste odabrane osobe dva puta gdje se onda hvata i obrađuje nova oznadena iznimka, prima objekt klase tipa Scanner, polje objekata klase tipa Osoba i objekt klase tipa Integer, a ne vraća ništa.
     * @param scan Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @param osobe Osoba[] polje objekata služi za provjeru duplikata nad kontaktirnom osobom za svaku osobu koja se unosi.
     * @param kontaktiraneOsobe Osoba[] polje objekata služi za provjeru duplikata svake kontaktirane osobe koja je bila sa više istih osoba u kontaktu.
     * @param i Integer indeks elementa od objekta kontaktirane osobe.
     */
    protected static void hvatanjeOznaceneIznimkeDuplikataOsobe(Scanner scan, Osoba[] osobe, Osoba[] kontaktiraneOsobe, Integer i) {

        boolean vrtiPetlju = false;
        do {
            vrtiPetlju = false;
            try {
                Osoba kontaktirana = MetodeZaOdabir.odabirOsobe(scan, osobe);
                provjeraDuplikataKontaktiranihOsoba(kontaktirana, kontaktiraneOsobe);
                kontaktiraneOsobe[i] = kontaktirana;
            } catch (DuplikatKontaktiraneOsobeException ex) {
                logger.error("Pogreska, dogodila se nova oznacena iznimka, odabrana je ista osoba vise puta!", ex);
                System.err.println("Odabrana je ista osoba vise puta" + messageRepeatInput+"!\n");
                vrtiPetlju = true;
            }
        }
        while (vrtiPetlju);
    }

    /**
     * Služi za hvatanje neoznačene iznimke 'BolestiIstihSimptomaException' nad poljem objekata klase tipa Simptom i Bolest da li objekti imaju iste odabrane simptome više puta, gdje se onda hvata i obrađuje nova neoznačena iznimka, prima objekt klase tipa Scanner, polje objekata klase tipa Simtom i Bolest, objekt klase tipa Integer, a ne vraća ništa.
     * @param scan Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @param simptomi Simptom[] polje objekata služi za provjeru istih simptoma.
     * @param bolesti Bolest[] polje objekata služi za provjeru istih simptoma za naziv druge bolesti koja se unosi.
     * @param i Integer indeks elementa od objekta bolesti.
     */
    protected static void hvatanjeNeoznaceneIznimkeBolestIstihSimptoma(Scanner scan, Simptom[] simptomi, Bolest[] bolesti, Integer i) {

        boolean vrtiPetlju = false;
        do {
            vrtiPetlju = false;
            try {
                Bolest bolest = MetodeZaUnosPodataka.unosBolesti(scan, simptomi, i);
                MetodeZaValidaciju.provjeraBolestiSaIstimSimptomima(bolest, bolesti);
                bolesti[i] = bolest;
            } catch (BolestIstihSimptomaException ex) {
                logger.error("Pogreska, dogodila se nova neoznacena iznimka, odabrana je bolest sa istim simptomima vise puta!", ex);
                System.err.print("Odabrana je bolest sa istim simptomima vise puta" + messageRepeatInput+"!\n");
                vrtiPetlju = true;
            }
        }
        while (vrtiPetlju);
    }

    /**
     * Kod unosa cjelobrojnih vrijednosti provjerava da li je unesena vrijednost unutar granice svakog od veličine polja, da li je broj negativan ili jednak nuli, prima objekt klase tipa Scanner i Integer, a vraca objekt klase tipa Integer.
     * @param scan Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @param granica Integer predstavlja konstantu veličinu polja.
     * @return Integer odabrani indeks odnosno cjelobrojna vrijednost.
     */
    protected static Integer provjeraBrojaSaListe(Scanner scan, Integer granica) {

        int odabir;
        do {
            odabir = MetodeZaValidaciju.IntegerExHandler(scan);
            if (odabir < 0 || odabir > granica) {
                System.out.print("Odabir mora biti redni broj sa ponudene liste" + messageRepeatInput);
                logger.error(messageRepeatNumberFormatEx + " '" + odabir + "'");
            } else if (odabir == 0)
                System.out.print("Odabir ne moze biti nula" + messageRepeatInput);
            logger.error(messageRepeatNumberFormatEx + " '" + odabir + "'");
        } while (odabir < 0 || odabir > granica || odabir == 0);
        return odabir;
    }

    /**
     * Kod unosa cjelobrojnih vrijednosti provjerava da li je broj kontaktiranih osoba manji ili jednak broju koji je poslan sa indeksom, prima objekt klase tipa Scanner i Integer, a vraca objekt klase tipa Integer.
     * @param scan scan – Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @param brojacOsoba Integer predstavlja prosljeđeni broj osoba koji je uvjek za jedan manji od ukupno unesenih.
     * @return Integer vraca broj kontaktiranih odnosno cjelobrojnu vrijednost.
     */
    protected static Integer provjeraBrojaKontaktiranih(Scanner scan, Integer brojacOsoba) {

        Integer brojKontaktiranih;
        do {
            brojKontaktiranih = MetodeZaValidaciju.IntegerExHandler(scan);
            if (brojKontaktiranih <= brojacOsoba) {
                return brojKontaktiranih;
            } else if (brojKontaktiranih == 0) {
                logger.error(messageRepeatNumberFormatEx + " '" + brojKontaktiranih + "'");
                System.err.print("Nema kontaktiranih osoba" + messageRepeatInput);
            } else {
                logger.error("Broj kontaktiranih osoba ne moze biti veci od broja upisanih osoba " + " '" + brojKontaktiranih + "' ");
                System.err.print("Broj kontaktiranih osoba ne moze biti veci od broja upisanih osoba" + messageRepeatInput);
            }
        } while (true);
    }
}