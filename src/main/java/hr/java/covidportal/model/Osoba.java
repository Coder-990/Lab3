package main.java.hr.java.covidportal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

/**
 *Predstavlja model osoba
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@Data
public class Osoba {

    String ime, prezime;
    Integer starost;
    Zupanija zupanija;
    Bolest zarazenBolescu;
    Osoba[] kontaktiraneOsobe;

    /**
     * Predstavlja staticku klasu oblikovanog obrasca bilder, sastoji se od statičke klase unutar domenske klase s kojima se postavljaju samo oni parametri koji su dostupni.
     */
    public static class Builder {

        private String ime, prezime;
        private Integer starost;
        private Zupanija zupanija;
        private Bolest zarazenBolescu;
        private Osoba[] kontaktiraneOsobe;

        public Builder ime(String ime) {
            this.ime = ime;
            return this;
        }

        public Builder prezime(String prezime) {
            this.prezime = prezime;
            return this;
        }

        public Builder starost(Integer starost) {
            this.starost = starost;
            return this;
        }

        public Builder zupanija(Zupanija zupanija) {
            this.zupanija = zupanija;
            return this;
        }

        public Builder zarazenBolescu(Bolest zarazenBolescu) {
            this.zarazenBolescu = zarazenBolescu;
            return this;
        }

        public Builder kontaktiraneOsobe(Osoba[] kontaktiraneOsobe) {
            this.kontaktiraneOsobe = kontaktiraneOsobe;
            return this;
        }

        /**
         * Konstruktor klase Builder služi za definiranje objeka osoba, ne prima ništa, postavlja samo one parametre koji su dostupni bez potrebe koristenja null parametra, a vraca oblikovani obrazac objekt klase tipa osoba.
         * @return Osoba build vraca oblikovani obrazac objekt klase Osoba.
         */
        public Osoba build() {
            Osoba osoba = new Osoba();
            osoba.ime = this.ime;
            osoba.prezime = this.prezime;
            osoba.starost = this.starost;
            osoba.zupanija = this.zupanija;
            osoba.zarazenBolescu = this.zarazenBolescu;
            osoba.kontaktiraneOsobe = this.kontaktiraneOsobe;

            if (Optional.ofNullable(osoba.kontaktiraneOsobe).isPresent() && this.zarazenBolescu instanceof Virus virus) {
                for (Osoba osb : kontaktiraneOsobe) {
                    virus.prelazakZarazeNaOsobu(osb);
                }
            }
            return osoba;
        }
    }
    /**
     * Služi za ispis svih podataka u programu.
     * @return String objekta klase osoba svakog navedenog podatka, ime, prezime, starost, prebivaliste, bolest i kontaktirane osobe.
     */
    @Override
        public String toString() {
            String kontaktOsoba = (Optional.ofNullable(kontaktiraneOsobe).isPresent() && kontaktiraneOsobe.length > 0) ? Arrays.toString(kontaktiraneOsobe) : "Nema kontaktiranih osoba.";
            return "Osoba: " +
                    "\nIme i prezime = " + ime + " " + prezime +
                    "\nStarost = " + starost +
                    "\nZupanija prebivalista = " + zupanija.getNaziv() +
                    "\nZarazen bolescu = " + zarazenBolescu.getNaziv() +
                    "\nKontaktirane osobe:\n" + kontaktOsoba + "\n";
        }
}
