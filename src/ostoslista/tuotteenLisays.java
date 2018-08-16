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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
public class tuotteenLisays {

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
        TextField luokittelu = new TextField();
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
                if(title.getText().equals("") || luokittelu.getText().equals("") || koko.getText().equals("") || tyyppi.getText().equals("")){
                     Alert alert = new Alert(Alert.AlertType.ERROR, "Sinun on täytettävä kaikki kentät.");
                    alert.showAndWait();
                } else{
                lisaaToXml(luokittelu.getText(), title.getText(), koko.getText(), tyyppi.getText());
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
    
}
