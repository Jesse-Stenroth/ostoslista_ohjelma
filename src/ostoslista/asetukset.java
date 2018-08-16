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
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Jesse
 */
public class asetukset {
    public void suorita(Stage ikkuna, HBox boksi){
        BorderPane paneeli = new BorderPane();
        paneeli.setTop(boksi);
        VBox lista = new VBox();
        lista.setPadding(new Insets(15, 12, 15, 12));
        lista.setSpacing(10);
        paneeli.setCenter(lista);
        paneeli.setAlignment(lista,Pos.CENTER);
        Label t = new Label("Asetukset");
        lista.getChildren().add(t);
        ChoiceBox<String> cb = new ChoiceBox(FXCollections.observableArrayList(valinnat()));
        lista.getChildren().add(cb);
        cb.getSelectionModel().selectedItemProperty().addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) -> asetus(newValue));
        ikkuna.setScene(new Scene(paneeli));
    }
    public ArrayList<String> valinnat(){
        ArrayList<String> list = new ArrayList<>();
        list.add("oletus");
        list.add("lielahti");
        list.add("nokia");
        list.add("westeri");
        return list;
    }

    private void asetus(String newValue) {
        try{
        File inputFile=new File("asetukset.xml");
        if (!inputFile.exists()) {
            inputFile .createNewFile();
           
        } else{
           
        }
            BufferedWriter tulos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("asetukset.xml"), "UTF8"));
            tulos.write("<root>");
            tulos.newLine();
            tulos.write("<item>");
            tulos.newLine();
            tulos.write(newValue);
            tulos.newLine();
            tulos.write("</item>");
            tulos.newLine();
            tulos.write("</root>");
            tulos.close();
        } catch(Exception e){
            
        }
    }
}
