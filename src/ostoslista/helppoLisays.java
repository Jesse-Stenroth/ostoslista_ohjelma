/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ostoslista;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Jesse
 */
public class helppoLisays {
    public void suorita(Stage ikkuna, HBox boksi) {
        BorderPane paneeli = new BorderPane();
        paneeli.setTop(boksi);
        VBox listaus = new VBox();
        TextField title = new TextField();
        Label tit = new Label("Title: ");
        HBox eka = new HBox();
        eka.getChildren().add(tit);
        eka.getChildren().add(title);
        listaus.getChildren().add(eka);
        Label luok = new Label("Luokka: ");
        //muutettava
        ChoiceBox<String> luokittelu = new ChoiceBox(FXCollections.observableArrayList(valinnat()));
        //loppuu muutettava osuus
        HBox toka = new HBox();
        toka.getChildren().add(luok);
        toka.getChildren().add(luokittelu);
        Label k = new Label("koko: ");
        TextField koko = new TextField();
        toka.getChildren().add(k);
        toka.getChildren().add(koko);
        TextField tyyppi = new TextField();
        Label ty = new Label("Tyyppi: ");
        toka.getChildren().add(ty);
        toka.getChildren().add(tyyppi);
        listaus.getChildren().add(toka);
        Button nappia = new Button();
        nappia.setText("Lisää");
        listaus.getChildren().add(nappia);
        nappia.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                System.out.println("Value: " + luokittelu.getValue());
                if(title.getText().equals("") || luokittelu.getValue() == null || koko.getText().equals("") || tyyppi.getText().equals("")){
                     Alert alert = new Alert(Alert.AlertType.ERROR, "Sinun on täytettävä kaikki kentät.");
                    alert.showAndWait();
                } else{
                lisaaToXml(luokittelu.getValue(), title.getText(), koko.getText(), tyyppi.getText());
                }
            }

        });
        paneeli.setCenter(listaus);
        ikkuna.setScene(new Scene(paneeli));
    }
    public void lisaaToXml(String luokka, String nimi, String koko, String tyyppii) {
        try{
        File inputFile=new File("Tuotteet.xml");
       if (!inputFile.exists()) {
           inputFile .createNewFile();
            BufferedWriter tulos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Tuotteet.xml"), "UTF8"));
            tulos.write("<root>");
            tulos.newLine();
            tulos.write("<item>");
            tulos.newLine();
            tulos.write("<title>" + nimi + "</title>");
            tulos.newLine();
            tulos.write("<luokka>" + luokka + "</luokka>");
            tulos.newLine();
            tulos.write("<koko>" + koko + "</koko>");
            tulos.newLine();
            tulos.write("<tyyppi>" + tyyppii + "</tyyppi>");
            tulos.newLine();
            tulos.write("</item>");
            tulos.newLine();
            tulos.write("</root>");
            tulos.close();
       } else{
        //Scanner sc = new Scanner(file);
       // System.out.println("ok");
           XmlApu apu = new XmlApu();
        ArrayList<Tuote> tuotteet = apu.getTuotteet();
         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Tuotteet.xml"), "UTF8"));
         writer.write("<root>");
         writer.newLine();
         for(int kierros=0; kierros<tuotteet.size();kierros++){
             Tuote tuo = tuotteet.get(kierros);
            writer.write("<item>");
            writer.newLine();
            writer.write("<title>" + tuo.getNimi() + "</title>");
            writer.newLine();
            writer.write("<luokka>" + tuo.getLuokka() + "</luokka>");
            writer.newLine();
            writer.write("<koko>" + tuo.getKoko() + "</koko>");
            writer.newLine();
            writer.write("<tyyppi>" + tuo.getKoon_tyyppi() + "</tyyppi>");
            writer.newLine();
            writer.write("</item>");
            writer.newLine();
         }
         writer.write("<item>");
         writer.newLine();
            writer.write("<title>" + nimi + "</title>");
            writer.newLine();
            writer.write("<luokka>" + luokka + "</luokka>");
            writer.newLine();
            writer.write("<koko>" + koko + "</koko>");
            writer.newLine();
            writer.write("<tyyppi>" + tyyppii + "</tyyppi>");
            writer.newLine();
            writer.write("</item>");
            writer.newLine();
            writer.write("</root>");
         writer.close();
       }
        } catch(Exception e){
            
        }
    }
    private ArrayList<String> valinnat(){
        ArrayList<String> list = new ArrayList<>();
        list.add("juomat");
        list.add("juusto");
        list.add("valmisruuat");
        list.add("lihat/kalat");
        list.add("mehut");
        list.add("maito");
        list.add("palvelutiski");
        list.add("leivät");
        list.add("kuivat elintarvikkeet");
        list.add("tuoretuotteet");
        list.add("leipomo");
        list.add("hedelmät");
        list.add("vihannekset");
        list.add("pakasteet");
        list.add("lasten ruuat ja tarvikkeet");
        list.add("urheilu");
        list.add("kosmetiikka");
        list.add("makeiset");
        list.add("pakastus");
        list.add("kertakäyttö");
        list.add("siivous");
        list.add("astiat");
        list.add("jalkineet");
        list.add("pukeutuminen");
        list.add("sisustus");
        list.add("lelut");
        list.add("kirjat");
        list.add("paperit");
        list.add("kukat");
        list.add("elokuvat");
        list.add("pelit");
        list.add("musiikki");
        list.add("pienkoneet");
        list.add("kodinkoneet");
        list.add("kodintekstiilit");
        list.add("saunatarvikkeet");
        list.add("matot");
        list.add("valaisimet ja sähkötarvikkeet");
        list.add("paristot, lamput");
        list.add("maalit ja -tarvikkeet, työkalut");
        return list;
    }
}
