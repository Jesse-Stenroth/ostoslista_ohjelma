/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ostoslista;

/**
 *
 * @author Jesse
 */
public class Tuote {
    String nimi;
    double koko;
    String koon_tyyppi;
    String luokka;

    public String getLuokka() {
        return luokka;
    }

    public void setLuokka(String luokka) {
        this.luokka = luokka;
    }
    
    public Tuote(String nimea, String luokk, double kokoa, String tyyppi){
        this.nimi = nimea;
        this.koko = kokoa;
        this.koon_tyyppi = tyyppi;
        this.luokka = luokk;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public double getKoko() {
        return koko;
    }

    public void setKoko(double koko) {
        this.koko = koko;
    }

    public String getKoon_tyyppi() {
        return koon_tyyppi;
    }

    public void setKoon_tyyppi(String koon_tyyppi) {
        this.koon_tyyppi = koon_tyyppi;
    }
    @Override
    public String toString(){
        return this.nimi + " (" + this.koko + " " + this.koon_tyyppi + ")";
    }
    
}
