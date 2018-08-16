/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ostoslista;

import java.util.ArrayList;

/**
 *
 * @author Jesse
 */
public class luokka {
    private ArrayList<Tuote> lista;
    private String luokka;
    
    public luokka(String luokan_nimi){
        this.lista = new ArrayList<>();
        this.luokka = luokan_nimi;
    }

    public ArrayList<Tuote> getLista() {
        return lista;
    }

    public String getLuokka() {
        return luokka;
    }
    public void lisaaTuote(Tuote tuote){
        this.lista.add(tuote);
    }
}
