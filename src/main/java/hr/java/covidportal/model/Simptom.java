package main.java.hr.java.covidportal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * Predstavlja model simptom
 */
@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class Simptom extends ImenovaniEntitet{

    String vrijednost;

    /**
     * Nasljeduje klasu ImenovaniEntitet. Konstruktor klase Simptom služi za definiranje objeka simptom, nasljeđuje parametar naziv, a prima naziv objkta klase tipa String i vrijednost objkta klase tipa String.
     * @param naziv String naziv bolesti
     * @param vrijednost String vrijednost bolesti
     */
    public Simptom(String naziv, String vrijednost) {
        super(naziv);
        this.naziv = naziv;
        this.vrijednost = vrijednost;
    }
}
