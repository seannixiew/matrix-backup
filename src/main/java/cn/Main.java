package cn;

import cn.controller.OverviewController;
import impl.org.controlsfx.skin.ToggleSwitchSkin;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.controlsfx.control.ToggleSwitch;

public class Main extends Application{

    public static void main(String[] args) {

        Application.launch(args);

    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.getIcons().add(new Image("img/matrix_icon.png"));

        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/gui.fxml"));
        Pane root = fxmlLoader.load();
        root.setStyle("-fx-background-color: gray");

        OverviewController overviewController=fxmlLoader.getController();

        Scene scene =new Scene(root,1200,680); //最佳视效
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");   //bootstrapFX样式库
//        new JMetro(Style.DARK).setScene(scene);  //Jmetro 主题


        GridPane gpViewerA=(GridPane) root.lookup("#gpViewerA");
        for(int row=0;row<8;row++){
            for(int column=0;column<16;column++){
                int number=column>=8?56+row*8+column+1:row*8+column+1;
                Button bt=new Button("A"+number);
                bt.setAlignment(Pos.CENTER);
                bt.setContentDisplay(ContentDisplay.CENTER);
                bt.setMnemonicParsing(false);
                bt.setStyle("-fx-background-radius: 6em; -fx-min-height: 40px; -fx-max-height: 40px; -fx-max-width: 40px; -fx-min-width: 40px; -fx-font-size: 10px;-fx-font-style: italic;");
                bt.setId("A"+number);
                overviewController.channelStatesA[number-1].addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldVal, Boolean newVal) {
                        if(newVal==false){
                            bt.setStyle("-fx-background-radius: 6em; -fx-min-height: 40px; -fx-max-height: 40px; -fx-max-width: 40px; -fx-min-width: 40px; -fx-font-size: 10px;-fx-font-style: italic");

                        }else {
                            bt.setStyle("-fx-background-radius: 6em; -fx-min-height: 40px; -fx-max-height: 40px; -fx-max-width: 40px; -fx-min-width: 40px; -fx-font-size: 10px;-fx-font-style: italic;-fx-background-color: green");
                        }
                    }
                });
                gpViewerA.add(bt,column,row);
            }
        }
        Label lbSplit0=(Label)root.lookup("#lbSplit0");
        lbSplit0.prefHeightProperty().bind(gpViewerA.heightProperty());
        lbSplit0.prefWidthProperty().bind(gpViewerA.widthProperty().divide(2));

        GridPane gpViewerB=(GridPane) root.lookup("#gpViewerB");
        for(int row=0;row<8;row++){
            for(int column=0;column<16;column++){
                int number=column>=8?56+row*8+column+1:row*8+column+1;
                Button bt=new Button("B"+number);
                bt.setAlignment(Pos.CENTER);
                bt.setContentDisplay(ContentDisplay.CENTER);
                bt.setMnemonicParsing(false);
                bt.setStyle("-fx-background-radius: 6em; -fx-min-height: 40px; -fx-max-height: 40px; -fx-max-width: 40px; -fx-min-width: 40px; -fx-font-size: 10px;-fx-font-style: italic;");
                bt.setId("B"+number);
                overviewController.channelStatesB[number-1].addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldVal, Boolean newVal) {
                        if(newVal==false){
                            bt.setStyle("-fx-background-radius: 6em; -fx-min-height: 40px; -fx-max-height: 40px; -fx-max-width: 40px; -fx-min-width: 40px; -fx-font-size: 10px;-fx-font-style: italic");

                        }else {
                            bt.setStyle("-fx-background-radius: 6em; -fx-min-height: 40px; -fx-max-height: 40px; -fx-max-width: 40px; -fx-min-width: 40px; -fx-font-size: 10px;-fx-font-style: italic;-fx-background-color: green");
                        }
                    }
                });
                gpViewerB.add(bt,column,row);
            }
        }
        Label lbSplit1=(Label)root.lookup("#lbSplit1");
        lbSplit1.prefHeightProperty().bind(gpViewerB.heightProperty());
        lbSplit1.prefWidthProperty().bind(gpViewerB.widthProperty().divide(2));

        ToggleSwitch toggle=overviewController.getToggle();
        toggle.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldVal, Boolean newVal) {
                if(newVal==false){
                    toggle.setText("灭灯");
                    root.setStyle("-fx-background-color: black");
                    lbSplit0.setStyle("-fx-background-color: black");
                    lbSplit1.setStyle("-fx-background-color: black");
                }
                if(newVal==true) {
                    toggle.setText("亮灯");
                    root.setStyle("-fx-background-color: gray");
                    lbSplit0.setStyle("-fx-background-color: #d7d2d2");
                    lbSplit1.setStyle("-fx-background-color: #d7d2d2");
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();

    }


    @Override
    public void init() throws Exception{
        //例如：新建线程，建立数据库链接
        System.out.println("Application init...");

    }

    @Override
    public void stop() throws Exception{
        System.out.println("Application stop...");
    }

}


