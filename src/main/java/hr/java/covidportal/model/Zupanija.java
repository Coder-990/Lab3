package main.java.hr.java.covidportal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * Predstavlja model Županija
 */
@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class Zupanija extends ImenovaniEntitet{

    Integer brojStanovnikaZupanije;

    /**
     * Nasljeduje klasu ImenovaniEntitet. Konstruktor klase Županija služi za definiranje objeka zupanija, nasljeđuje parametar: naziv, a prima naziv objkta klase tipa String i broj stanovnika po županiji objeta klase tipa Integer.
     * @param naziv String naziv županije
     * @param brojStanovnikaZupanije Integer broj stanovnika po županiji
     */
    public Zupanija(String naziv, Integer brojStanovnikaZupanije) {
        super(naziv);
        this.naziv = naziv;
        this.brojStanovnikaZupanije = brojStanovnikaZupanije;
    }
}
