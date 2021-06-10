package main.java.hr.java.covidportal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * Predstavlja model bolest
 */
@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class Bolest extends ImenovaniEntitet {

    Simptom[] simptomi;
    /**
     * Nasljeduje klasu ImenovaniEntitet. Konstruktor klase Bolest služi za definiranje objeka bolest, nasljeđuje dva parametra: naziv i polje objekata klase tipa Simptom, a prima naziv objkta klase tipa String i polje objeta klase tipa Simptom.
     * @param naziv String naziv bolesti
     * @param simptomi Simptom[] polje simptoma
     */
    public Bolest(String naziv, Simptom[] simptomi) {
        super(naziv);
        this.naziv = naziv;
        this.simptomi = simptomi;
    }
}
