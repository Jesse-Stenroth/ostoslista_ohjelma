/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ostoslista;

import java.io.File;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Jesse
 */
public class Ostoslista extends Application{
tuotteita_korissa kori = new tuotteita_korissa();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }
    @Override
    public void start(Stage ikkuna){
        
        Scene nakyy = new Scene(nakymaa(ikkuna));
        ikkuna.setScene(nakyy);
        ikkuna.show();
    }

    public BorderPane nakymaa(Stage ikkuna) {
        BorderPane paneeli = new BorderPane();
        HBox boksi = new HBox();
        HBox apu = new HBox();
        Button nappi1 = new Button();
        boksi.setPadding(new Insets(15, 12, 15, 12));
        boksi.setSpacing(10);
        nappi1.setText("Etusivu");
        nappi1.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                ikkuna.setScene(new  Scene(nakymaa(ikkuna)));
            }
        });
        boksi.getChildren().add(nappi1);
        Button nappi2 = new Button();
        nappi2.setText("Lisää tuote");
        nappi2.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                tuotteenLisays lisataan = new tuotteenLisays();
                lisataan.suorita(ikkuna, boksi);
            }
        });
        boksi.getChildren().add(nappi2);
        Button nappiH = new Button();
        nappiH.setText("Helppo lisäys");
        nappiH.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                helppoLisays helppo = new helppoLisays();
                helppo.suorita(ikkuna, boksi);
            }
        });
        boksi.getChildren().add(nappiH);
        Button nappi3 = new Button("Lista ulos");
        nappi3.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                Lista_ulos lisataan = new Lista_ulos();
                lisataan.suorita(ikkuna, boksi, kori);
            }
        });
        Button nappi4 = new Button("Haku");
        nappi4.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                haku h = new haku();
                h.suorita(ikkuna, boksi, kori);
            }
        });
        Button nappi5 = new Button("Luokittain");
        nappi5.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                luokittelu lu = new luokittelu();
                lu.suorita(ikkuna, boksi, kori);
            }
        });
        Button nappi6 = new Button("Asetukset");
        nappi6.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                asetukset aset = new asetukset();
                aset.suorita(ikkuna, boksi);
            }
        });
        boksi.getChildren().add(nappi4);
        boksi.getChildren().add(nappi5);
        boksi.getChildren().add(nappi6);
        boksi.getChildren().add(nappi3);
        paneeli.setTop(boksi);
        ListView nakyma1 = new ListView();
        apu.getChildren().add(nakyma1);
        paneeli.setCenter(apu);
        ListView nakyma2 = new ListView();
        apu.getChildren().add(nakyma2);
        File inputFile=new File("Tuotteet.xml");
        if (inputFile.exists()){
        XmlApu a = new XmlApu();
        ObservableList<String> items =FXCollections.observableArrayList (a.getTuotteidenNimet());
        nakyma1.setItems(items);
        nakyma1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


                nakyma1.setOnMouseClicked(new EventHandler<Event>() {

                    @Override
                    public void handle(Event event) {
                        ObservableList<String> selectedItems =  nakyma1.getSelectionModel().getSelectedItems();

                        for(String s : selectedItems){
                            kori.lisaa(s);
                        ObservableList<String> items2 =FXCollections.observableArrayList (kori.getLista());
                        nakyma2.setItems(items2);
                        nakyma2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

//test
                        
                nakyma2.setOnMouseClicked(new EventHandler<Event>() {

                    @Override
                    public void handle(Event event) {
                        ObservableList<String> selectedItems =  nakyma2.getSelectionModel().getSelectedItems();

                        for(String s : selectedItems){
                            System.out.println("pois");
                            System.out.println("alku lista: " + kori.getLista());
                            kori.poista(s);
                            System.out.println("lista:" + kori.getLista());
                           ObservableList<String> items2 =FXCollections.observableArrayList (kori.getLista());
                        nakyma2.setItems(items2);
                        }

                    }

                }); 
                //testi loppuu
                                
                        }

                    }

                });
                if(kori.getKoko() >0){
                    ObservableList<String> items2 =FXCollections.observableArrayList (kori.getLista());
                        nakyma2.setItems(items2);
                        
                }
        } else{
            
        }
        if(nakyma2.getItems().size() > 0){
            System.out.println("lausekkeessa");
        nakyma2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


                nakyma2.setOnMouseClicked(new EventHandler<Event>() {

                    @Override
                    public void handle(Event event) {
                        ObservableList<String> selectedItems =  nakyma2.getSelectionModel().getSelectedItems();

                        for(String s : selectedItems){
                            System.out.println("pois");
                             System.out.println("alku lista: " + kori.getLista());
                            kori.poista(s);
                            System.out.println("lista:" + kori.getLista());
                            ObservableList<String> items2 =FXCollections.observableArrayList (kori.getLista());
                        nakyma2.setItems(items2);
                        }

                    }

                });
        }
        
        return paneeli;
    }
    
}
