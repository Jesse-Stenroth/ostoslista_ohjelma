/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ostoslista;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author Jesse
 */
public class Lista_ulos {
    public void suorita(Stage ikkuna, HBox boksi, tuotteita_korissa kori){
        BorderPane paneeli = new BorderPane();
        paneeli.setTop(boksi);
        Label teksti = new Label();
        paneeli.setLeft(teksti);
        Button nappii = new Button("Vie tiedostoon");
        nappii.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                if(teksti.getText().equals("")){
                    Alert alert = new Alert(AlertType.ERROR, "Lisää tuotteita listalle!");
                    alert.showAndWait();
                } else{
                    try{
                        File inputFile=new File("ostoslista.txt");
                        if (!inputFile.exists()) {
                            inputFile .createNewFile();
                        }
                        BufferedWriter tulos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("ostoslista.txt"), "UTF8"));
                        
                        String[] osa = teksti.getText().split("\n");
                        for(int kierros=0;kierros<osa.length;kierros++){
                            tulos.write(osa[kierros]);
                            tulos.newLine();
                        }
                        
                        tulos.close();
                        Alert alert = new Alert(AlertType.INFORMATION,"Tuotteet tiedostossa.");
                        alert.showAndWait();
                    } catch(Exception e){
                        
                    }
                }
            }
        });
        paneeli.setRight(nappii);
        HashMap<Tuote, Integer> map = kori.getMappi();
        if(map.size()>0){
        ArrayList<Tuote> lista1 = new ArrayList<Tuote>(map.keySet());
        ArrayList<Tuote> lista2 = new ArrayList<Tuote>();
        ArrayList<Tuote> lista = new ArrayList<Tuote>();
        for(int kierros=0;kierros<lista1.size();kierros++){
            lista.add(lista1.get(kierros));
        }
        for(int kierros=0;kierros<lista1.size();kierros++){
            Tuote tuote = lista1.get(kierros);
            for(int k=0; k<lista.size();k++){
                if(lista.get(k).getLuokka().equals(tuote.getLuokka())){
                    lista2.add(lista.get(k));
                }
            }
            for(int g=0;g<lista2.size();g++){
                if(lista.contains(lista2.get(g))){
                    lista.remove(lista2.get(g));
                }
            }
        }
        ArrayList<Tuote> lista3 = uusiJarjestus(lista2);
        Tuote edellinen = lista3.get(0);
        String t = edellinen.getLuokka() + "\n" + map.get(edellinen) + " x " + edellinen + "\n";
        for(int kierros=1;kierros<lista3.size();kierros++){
            Tuote uusi = lista3.get(kierros);
            if(edellinen.getLuokka().equals(uusi.getLuokka())){
                t += map.get(uusi) + " x " + uusi + "\n";
            } else{
                t += uusi.getLuokka() + "\n" + map.get(uusi) + " x " + uusi + "\n";
            }
            edellinen = uusi;
        }
        teksti.setText(t);
        }
        ikkuna.setScene(new Scene(paneeli));
    }

    private ArrayList<Tuote> uusiJarjestus(ArrayList<Tuote> lista2) {
        try{
        File inputFile=new File("asetukset.xml");
        ArrayList<Tuote> tuotteet = new ArrayList<>();
       // System.out.println("ok");
        //System.out.println(sc.hasNextLine());
     //  File inputFile = new File("input.txt");
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         FileInputStream in = new FileInputStream(inputFile);
         Document doc = dBuilder.parse(in, "UTF-8");
         doc.getDocumentElement().normalize();
       //  System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
         String asetus = doc.getElementsByTagName("item").item(0).getTextContent();
         if(asetus.equals("oletus")){
             System.out.println("oletus lajittelu");
             return lista2;
         } else{
             ArrayList<luokka> lista = new ArrayList<>();
             for(int kierros=0;kierros<lista2.size();kierros++){
                 Tuote toka = lista2.get(kierros);
                 if(sisaltaakoLuokkaa(lista, toka)){
                     lista.get(missa(lista, toka)).lisaaTuote(toka);
                 } else{
                     lista.add(new luokka(toka.getLuokka()));
                     lista.get(missa(lista, toka)).lisaaTuote(toka);
                 }
             }
             ArrayList<luokka> apu = new ArrayList<>();
                 HashMap<String,luokka> map = new HashMap<>();
                 for(int kierros=0;kierros<lista.size();kierros++){
                     map.put(lista.get(kierros).getLuokka().toLowerCase(), lista.get(kierros));
                 }
                 lista.clear();
                 ArrayList<String> jaljella = new ArrayList<>(map.keySet());
             if(asetus.contains("lielahti")){
                 System.out.println("Lielahti lajittelu");
                 if(map.containsKey("juomat")){
                     apu.add(map.get("juomat"));
                     jaljella.remove("juomat");
                 }
                 if(map.containsKey("mehut")){
                     apu.add(map.get("mehut"));
                     jaljella.remove("mehut");
                 }
                 if(map.containsKey("maito")){
                     apu.add(map.get("maito"));
                     jaljella.remove("maito");
                 }
                 if(map.containsKey("palvelutiski")){
                     apu.add(map.get("palvelutiski"));
                     jaljella.remove("palvelutiski");
                 }
                 if(map.containsKey("leivät")){
                     apu.add(map.get("leivät"));
                     jaljella.remove("leivät");
                 }
                 if(map.containsKey("leipomo")){
                     apu.add(map.get("leipomo"));
                     jaljella.remove("leipomo");
                 }
                 if(map.containsKey("juusto")){
                     apu.add(map.get("juusto"));
                     jaljella.remove("juusto");
                 }
                 if(map.containsKey("lihat/kalat")){
                     apu.add(map.get("lihat/kalat"));
                     jaljella.remove("lihat/kalat");
                 }
                 if(map.containsKey("valmisruuat")){
                     apu.add(map.get("valmisruuat"));
                     jaljella.remove("valmisruuat");
                 }
                 if(map.containsKey("kuivat elintarvikkeet")){
                     apu.add(map.get("kuivat elintarvikkeet"));
                     jaljella.remove("kuivat elintarvikkeet");
                 }
                 if(map.containsKey("tuoretuotteet")){
                     apu.add(map.get("tuoretuotteet"));
                     jaljella.remove("tuoretuotteet");
                 }
                 
                 if(map.containsKey("hedelmät")){
                     apu.add(map.get("hedelmät"));
                     jaljella.remove("hedelmät");
                 }
                 if(map.containsKey("vihannekset")){
                     apu.add(map.get("vihannekset"));
                     jaljella.remove("vihannekset");
                 }
                 if(map.containsKey("pakasteet")){
                     apu.add(map.get("pakasteet"));
                     jaljella.remove("pakasteet");
                 }
                 if(map.containsKey("lasten ruuat ja tarvikkeet")){
                     apu.add(map.get("lasten ruuat ja tarvikkeet"));
                     jaljella.remove("lasten ruuat ja tarvikkeet");
                 }
                 if(map.containsKey("urheilu")){
                     apu.add(map.get("urheilu"));
                     jaljella.remove("urheilu");
                 }
                 if(map.containsKey("kosmetiikka")){
                     apu.add(map.get("kosmetiikka"));
                     jaljella.remove("kosmetiikka");
                 }
                 if(map.containsKey("makeiset")){
                     apu.add(map.get("makeiset"));
                     jaljella.remove("makeiset");
                 }
                 if(map.containsKey("pakastus")){
                     apu.add(map.get("pakastus"));
                     jaljella.remove("pakastus");
                 }
                 if(map.containsKey("kertakäyttö")){
                     apu.add(map.get("kertakäyttö"));
                     jaljella.remove("kertakäyttö");
                 }
                 if(map.containsKey("siivous")){
                     apu.add(map.get("siivous"));
                     jaljella.remove("siivous");
                 }
                 if(map.containsKey("astiat")){
                     apu.add(map.get("astiat"));
                     jaljella.remove("astiat");
                 }
                 if(map.containsKey("jalkineet")){
                     apu.add(map.get("jalkineet"));
                     jaljella.remove("jalkineet");
                 }
                 if(map.containsKey("pukeutuminen")){
                     apu.add(map.get("pukeutuminen"));
                     jaljella.remove("pukeutuminen");
                 }
                 if(map.containsKey("sisustus")){
                     apu.add(map.get("sisustus"));
                     jaljella.remove("sisustus");
                 }
                 if(map.containsKey("lelut")){
                     apu.add(map.get("lelut"));
                     jaljella.remove("lelut");
                 }
                 if(map.containsKey("kirjat")){
                     apu.add(map.get("kirjat"));
                     jaljella.remove("kirjat");
                 }
                 if(map.containsKey("paperit")){
                     apu.add(map.get("paperit"));
                     jaljella.remove("paperit");
                 }
                 if(map.containsKey("kukat")){
                     apu.add(map.get("kukat"));
                     jaljella.remove("kukat");
                 }
                 if(map.containsKey("elokuvat")){
                     apu.add(map.get("elokuvat"));
                     jaljella.remove("elokuvat");
                 }
                 if(map.containsKey("pelit")){
                     apu.add(map.get("pelit"));
                     jaljella.remove("pelit");
                 }
                 if(map.containsKey("musiikki")){
                     apu.add(map.get("musiikki"));
                     jaljella.remove("musiikki");
                 }
                 if(map.containsKey("pienkoneet")){
                     apu.add(map.get("pienkoneet"));
                     jaljella.remove("pienkoneet");
                 }
                 if(map.containsKey("kodinkoneet")){
                     apu.add(map.get("kodinkoneet"));
                     jaljella.remove("kodinkoneet");
                 }
                 if(map.containsKey("kodintekstiilit")){
                     apu.add(map.get("kodintekstiilit"));
                     jaljella.remove("kodintekstiilit");
                 }
                 if(map.containsKey("saunatarvikkeet")){
                     apu.add(map.get("saunatarvikkeet"));
                     jaljella.remove("saunatarvikkeet");
                 }
                 if(map.containsKey("matot")){
                     apu.add(map.get("matot"));
                     jaljella.remove("matot");
                 }
                 if(map.containsKey("valaisimet ja sähkötarvikkeet")){
                     apu.add(map.get("valaisimet ja sähkötarvikkeet"));
                     jaljella.remove("valaisimet ja sähkötarvikkeet");
                 }
                 if(map.containsKey("paristot, lamput")){
                     apu.add(map.get("paristot, lamput"));
                     jaljella.remove("paristot, lamput");
                 }
                 if(map.containsKey("maalit ja -tarvikkeet, työkalut")){
                     apu.add(map.get("maalit ja -tarvikkeet, työkalut"));
                     jaljella.remove("maalit ja -tarvikkeet, työkalut");
                 }
                 for(int kierros=0;kierros<jaljella.size();kierros++){
                     apu.add(map.get(jaljella.get(kierros)));
                 }
                 ArrayList<Tuote> listaa = new ArrayList<>();
                 for(int k=0;k<apu.size();k++){
                     listaa.addAll(apu.get(k).getLista());
                 }
                 return listaa;
             }else if(asetus.contains("westeri")){
                 System.out.println("westeri lajittelu");
                 if(map.containsKey("juusto")){
                     apu.add(map.get("juusto"));
                     jaljella.remove("juusto");
                 }
                 if(map.containsKey("leivät")){
                     apu.add(map.get("leivät"));
                     jaljella.remove("leivät");
                 }
                 if(map.containsKey("leipomo")){
                     apu.add(map.get("leipomo"));
                     jaljella.remove("leipomo");
                 }
                 if(map.containsKey("tuoretuotteet")){
                     apu.add(map.get("tuoretuotteet"));
                     jaljella.remove("tuoretuotteet");
                 }
                 if(map.containsKey("hedelmät")){
                     apu.add(map.get("hedelmät"));
                     jaljella.remove("hedelmät");
                 }
                 if(map.containsKey("vihannekset")){
                     apu.add(map.get("vihannekset"));
                     jaljella.remove("vihannekset");
                 }
                 if(map.containsKey("valmisruuat")){
                     apu.add(map.get("valmisruuat"));
                     jaljella.remove("valmisruuat");
                 }
                 if(map.containsKey("lihat/kalat")){
                     apu.add(map.get("lihat/kalat"));
                     jaljella.remove("lihat/kalat");
                 }
                 if(map.containsKey("palvelutiski")){
                     apu.add(map.get("palvelutiski"));
                     jaljella.remove("palvelutiski");
                 }
                 if(map.containsKey("maito")){
                     apu.add(map.get("maito"));
                     jaljella.remove("maito");
                 }
                 if(map.containsKey("mehut")){
                     apu.add(map.get("mehut"));
                     jaljella.remove("mehut");
                 }
                 
                 if(map.containsKey("juomat")){
                     apu.add(map.get("juomat"));
                     jaljella.remove("juomat");
                 }
                 
                 if(map.containsKey("kuivat elintarvikkeet")){
                     apu.add(map.get("kuivat elintarvikkeet"));
                     jaljella.remove("kuivat elintarvikkeet");
                 }
                 if(map.containsKey("kertakäyttö")){
                     apu.add(map.get("kertakäyttö"));
                     jaljella.remove("kertakäyttö");
                 }
                 if(map.containsKey("astiat")){
                     apu.add(map.get("astiat"));
                     jaljella.remove("astiat");
                 }
                 if(map.containsKey("makeiset")){
                     apu.add(map.get("makeiset"));
                     jaljella.remove("makeiset");
                 }
                 
                 
                 if(map.containsKey("pakasteet")){
                     apu.add(map.get("pakasteet"));
                     jaljella.remove("pakasteet");
                 }
                 if(map.containsKey("paperit")){
                     apu.add(map.get("paperit"));
                     jaljella.remove("paperit");
                 }
                 //ok
                 if(map.containsKey("lasten ruuat ja tarvikkeet")){
                     apu.add(map.get("lasten ruuat ja tarvikkeet"));
                     jaljella.remove("lasten ruuat ja tarvikkeet");
                 }
                 
                 if(map.containsKey("kosmetiikka")){
                     apu.add(map.get("kosmetiikka"));
                     jaljella.remove("kosmetiikka");
                 }
                 
                 if(map.containsKey("pakastus")){
                     apu.add(map.get("pakastus"));
                     jaljella.remove("pakastus");
                 }
                 
                 if(map.containsKey("siivous")){
                     apu.add(map.get("siivous"));
                     jaljella.remove("siivous");
                 }
                 
                 if(map.containsKey("jalkineet")){
                     apu.add(map.get("jalkineet"));
                     jaljella.remove("jalkineet");
                 }
                 if(map.containsKey("pukeutuminen")){
                     apu.add(map.get("pukeutuminen"));
                     jaljella.remove("pukeutuminen");
                 }
                 if(map.containsKey("sisustus")){
                     apu.add(map.get("sisustus"));
                     jaljella.remove("sisustus");
                 }
                 if(map.containsKey("lelut")){
                     apu.add(map.get("lelut"));
                     jaljella.remove("lelut");
                 }
                 if(map.containsKey("kirjat")){
                     apu.add(map.get("kirjat"));
                     jaljella.remove("kirjat");
                 }
                 
                 if(map.containsKey("kukat")){
                     apu.add(map.get("kukat"));
                     jaljella.remove("kukat");
                 }
                 if(map.containsKey("elokuvat")){
                     apu.add(map.get("elokuvat"));
                     jaljella.remove("elokuvat");
                 }
                 if(map.containsKey("pelit")){
                     apu.add(map.get("pelit"));
                     jaljella.remove("pelit");
                 }
                 if(map.containsKey("musiikki")){
                     apu.add(map.get("musiikki"));
                     jaljella.remove("musiikki");
                 }
                 if(map.containsKey("pienkoneet")){
                     apu.add(map.get("pienkoneet"));
                     jaljella.remove("pienkoneet");
                 }
                 if(map.containsKey("kodinkoneet")){
                     apu.add(map.get("kodinkoneet"));
                     jaljella.remove("kodinkoneet");
                 }
                 if(map.containsKey("kodintekstiilit")){
                     apu.add(map.get("kodintekstiilit"));
                     jaljella.remove("kodintekstiilit");
                 }
                 if(map.containsKey("saunatarvikkeet")){
                     apu.add(map.get("saunatarvikkeet"));
                     jaljella.remove("saunatarvikkeet");
                 }
                 if(map.containsKey("matot")){
                     apu.add(map.get("matot"));
                     jaljella.remove("matot");
                 }
                 if(map.containsKey("valaisimet ja sähkötarvikkeet")){
                     apu.add(map.get("valaisimet ja sähkötarvikkeet"));
                     jaljella.remove("valaisimet ja sähkötarvikkeet");
                 }
                 if(map.containsKey("paristot, lamput")){
                     apu.add(map.get("paristot, lamput"));
                     jaljella.remove("paristot, lamput");
                 }
                 if(map.containsKey("maalit ja -tarvikkeet, työkalut")){
                     apu.add(map.get("maalit ja -tarvikkeet, työkalut"));
                     jaljella.remove("maalit ja -tarvikkeet, työkalut");
                 }
                 if(map.containsKey("urheilu")){
                     apu.add(map.get("urheilu"));
                     jaljella.remove("urheilu");
                 }
                 for(int kierros=0;kierros<jaljella.size();kierros++){
                     apu.add(map.get(jaljella.get(kierros)));
                 }
                 ArrayList<Tuote> listaa = new ArrayList<>();
                 for(int k=0;k<apu.size();k++){
                     listaa.addAll(apu.get(k).getLista());
                 }
                 return listaa;
             }else if(asetus.contains("nokia")){
                 System.out.println("Nokia lajittelu");
                 
                 if(map.containsKey("leivät")){
                     apu.add(map.get("leivät"));
                     jaljella.remove("leivät");
                 }
                 if(map.containsKey("maito")){
                     apu.add(map.get("maito"));
                     jaljella.remove("maito");
                 }
                 if(map.containsKey("palvelutiski")){
                     apu.add(map.get("palvelutiski"));
                     jaljella.remove("palvelutiski");
                 }
                 if(map.containsKey("leipomo")){
                     apu.add(map.get("leipomo"));
                     jaljella.remove("leipomo");
                 }
                 if(map.containsKey("tuoretuotteet")){
                     apu.add(map.get("tuoretuotteet"));
                     jaljella.remove("tuoretuotteet");
                 }
                 if(map.containsKey("kuivat elintarvikkeet")){
                     apu.add(map.get("kuivat elintarvikkeet"));
                     jaljella.remove("kuivat elintarvikkeet");
                 }
                 if(map.containsKey("makeiset")){
                     apu.add(map.get("makeiset"));
                     jaljella.remove("makeiset");
                 }
                 if(map.containsKey("hedelmät")){
                     apu.add(map.get("hedelmät"));
                     jaljella.remove("hedelmät");
                 }
                 if(map.containsKey("vihannekset")){
                     apu.add(map.get("vihannekset"));
                     jaljella.remove("vihannekset");
                 }
                 if(map.containsKey("juomat")){
                     apu.add(map.get("juomat"));
                     jaljella.remove("juomat");
                 }
                 if(map.containsKey("mehut")){
                     apu.add(map.get("mehut"));
                     jaljella.remove("mehut");
                 }
                 if(map.containsKey("pakasteet")){
                     apu.add(map.get("pakasteet"));
                     jaljella.remove("pakasteet");
                 }
                 
                 
                 if(map.containsKey("siivous")){
                     apu.add(map.get("siivous"));
                     jaljella.remove("siivous");
                 }
                 if(map.containsKey("kosmetiikka")){
                     apu.add(map.get("kosmetiikka"));
                     jaljella.remove("kosmetiikka");
                 }
                 if(map.containsKey("kirjat")){
                     apu.add(map.get("kirjat"));
                     jaljella.remove("kirjat");
                 }
                 if(map.containsKey("paperit")){
                     apu.add(map.get("paperit"));
                     jaljella.remove("paperit");
                 }
                 if(map.containsKey("astiat")){
                     apu.add(map.get("astiat"));
                     jaljella.remove("astiat");
                 }
                 if(map.containsKey("pakastus")){
                     apu.add(map.get("pakastus"));
                     jaljella.remove("pakastus");
                 }
                 if(map.containsKey("kertakäyttö")){
                     apu.add(map.get("kertakäyttö"));
                     jaljella.remove("kertakäyttö");
                 }
                 
                 if(map.containsKey("lasten ruuat ja tarvikkeet")){
                     apu.add(map.get("lasten ruuat ja tarvikkeet"));
                     jaljella.remove("lasten ruuat ja tarvikkeet");
                 }
                 if(map.containsKey("lelut")){
                     apu.add(map.get("lelut"));
                     jaljella.remove("lelut");
                 }
                 if(map.containsKey("pienkoneet")){
                     apu.add(map.get("pienkoneet"));
                     jaljella.remove("pienkoneet");
                 }
                 if(map.containsKey("kodinkoneet")){
                     apu.add(map.get("kodinkoneet"));
                     jaljella.remove("kodinkoneet");
                 }
                 if(map.containsKey("elokuvat")){
                     apu.add(map.get("elokuvat"));
                     jaljella.remove("elokuvat");
                 }
                 if(map.containsKey("pelit")){
                     apu.add(map.get("pelit"));
                     jaljella.remove("pelit");
                 }
                 if(map.containsKey("musiikki")){
                     apu.add(map.get("musiikki"));
                     jaljella.remove("musiikki");
                 }
                 if(map.containsKey("urheilu")){
                     apu.add(map.get("urheilu"));
                     jaljella.remove("urheilu");
                 }
                 if(map.containsKey("valaisimet ja sähkötarvikkeet")){
                     apu.add(map.get("valaisimet ja sähkötarvikkeet"));
                     jaljella.remove("valaisimet ja sähkötarvikkeet");
                 }
                 if(map.containsKey("paristot, lamput")){
                     apu.add(map.get("paristot, lamput"));
                     jaljella.remove("paristot, lamput");
                 }
                 if(map.containsKey("maalit ja -tarvikkeet, työkalut")){
                     apu.add(map.get("maalit ja -tarvikkeet, työkalut"));
                     jaljella.remove("maalit ja -tarvikkeet, työkalut");
                     
                 }
                 if(map.containsKey("saunatarvikkeet")){
                     apu.add(map.get("saunatarvikkeet"));
                     jaljella.remove("saunatarvikkeet");
                 }
                 if(map.containsKey("sisustus")){
                     apu.add(map.get("sisustus"));
                     jaljella.remove("sisustus");
                 }
                 if(map.containsKey("jalkineet")){
                     apu.add(map.get("jalkineet"));
                     jaljella.remove("jalkineet");
                 }
                 if(map.containsKey("pukeutuminen")){
                     apu.add(map.get("pukeutuminen"));
                     jaljella.remove("pukeutuminen");
                 }
                 if(map.containsKey("kodintekstiilit")){
                     apu.add(map.get("kodintekstiilit"));
                     jaljella.remove("kodintekstiilit");
                 }
                 if(map.containsKey("matot")){
                     apu.add(map.get("matot"));
                     jaljella.remove("matot");
                 }
                 if(map.containsKey("kukat")){
                     apu.add(map.get("kukat"));
                     jaljella.remove("kukat");
                 }
                 
                 for(int kierros=0;kierros<jaljella.size();kierros++){
                     apu.add(map.get(jaljella.get(kierros)));
                 }
                 ArrayList<Tuote> listaa = new ArrayList<>();
                 for(int k=0;k<apu.size();k++){
                     listaa.addAll(apu.get(k).getLista());
                 }
                 return listaa;
             }else{
                 System.out.println("else lajittelu");
                 return lista2;
             }
         }
         
        } catch(Exception e){
            System.out.println("virhe lajittelussa");
            return lista2;
        }
    }

    private boolean sisaltaakoLuokkaa(ArrayList<luokka> lista, Tuote toka) {
        for(int k=0;k<lista.size();k++){
            if(lista.get(k).getLuokka().equals(toka.getLuokka())){
                return true;
            }
        }
        return false;
    }

    private int missa(ArrayList<luokka> lista, Tuote toka) {
        for(int k=0;k<lista.size();k++){
            if(lista.get(k).getLuokka().equals(toka.getLuokka())){
                return k;
            }
        }
        return 0;
    }
}
