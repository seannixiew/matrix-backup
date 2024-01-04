package cn.controller;

import cn.visa.MatrixClient;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import org.controlsfx.control.ToggleSwitch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class OverviewController {

    @FXML
    TextField tfIPA;
    @FXML
    Button btConnectionA;
    @FXML
    TextField tfIPB;
    @FXML
    Button btConnectionB;
    @FXML
    TextArea taChannelsA;
    @FXML
    TextArea taChannelsB;
    @FXML
    Button btOn;
    @FXML
    Button btOff;
    @FXML
    Button btReset;
    @FXML
    TextArea taLog;
    @FXML
    Button btExport;
    @FXML
    ToggleSwitch toggle;
@FXML
    TitledPane tpTop;
@FXML
        TitledPane tpBottom;


    MatrixClient matrixA=new MatrixClient();
    MatrixClient matrixB=new MatrixClient();
    SimpleDateFormat sdf=new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] "); //日志
    public BooleanProperty[] channelStatesA=new SimpleBooleanProperty[128];
    public BooleanProperty[] channelStatesB=new SimpleBooleanProperty[128];


    public ToggleSwitch getToggle() {
        return toggle;
    }

    public TitledPane getTpTop() {
        return tpTop;
    }

    public TitledPane getTpBottom() {
        return tpBottom;
    }

    @FXML
    public void onConnectA(){
        new Thread(()-> {
            long port = 3000; //不必支持修改端口，可以使用配置文件
            String ip = tfIPA.getText().trim();
            if (matrixA.connect(ip, port) == 1) {
                Platform.runLater(()->{
                    taLog.appendText(sdf.format(new Date()) + "Matrix A 已连接" + "\n");
                });
            } else {
                Platform.runLater(()->{
                    taLog.appendText(sdf.format(new Date()) + "超时！Matrix A" + "\n");
                });
            }
        }).start();
    }

    @FXML
    public  void onSwitchON(){
        new Thread(()->{
            String textA=taChannelsA.getText().trim();
            String textB=taChannelsB.getText().trim();
            if(matrixA.socket!=null && !textA.isEmpty()){
                String[] channelsA=textA.split("\\s+"); //正则匹配多个换行和空格
                List<String> cmdA = new ArrayList<>();
                for(int i=0;i<128;i++) {
                    channelStatesA[i].set(false);
                }
                for(String ch:channelsA){
                    channelStatesA[Integer.parseInt(ch.trim())-1].set(true);
                    ch="A"+ch.trim();
                    cmdA.add(ch);
                }
                String echo=matrixA.channelSwitch(cmdA);
                Platform.runLater(()->{
                    taLog.appendText(sdf.format(new Date())+"矩阵A："+echo); //echo带换行
                });
            }
            if(matrixB.socket!=null && !textB.isEmpty()){
                String[] channelsB=textB.split("\\s+");
                List<String> cmdB = new ArrayList<>();
                for(int i=0;i<128;i++) {
                    channelStatesB[i].set(false);
                }
                for(String ch:channelsB){
                    channelStatesB[Integer.parseInt(ch.trim())-1].set(true);
                    ch="A"+ch.trim();
                    cmdB.add(ch);
                }
                String echo=matrixB.channelSwitch(cmdB);
                Platform.runLater(()->{
                    taLog.appendText(sdf.format(new Date())+"矩阵B："+echo);
                });
            }
        }).start();
    }

    @FXML
    public  void onSwitchOff(){ // TODO: 2024/1/2 确认对其他通道是否有动作
        new Thread(()->{
            String textA=taChannelsA.getText().trim();
            String textB=taChannelsB.getText().trim();
            if(matrixA.socket!=null && !textA.isEmpty()){
                String[] channelsA=textA.split("\\s+"); //正则匹配多个换行和空格
                List<String> cmdA = new ArrayList<>();
                for(String ch:channelsA){
                    channelStatesA[Integer.parseInt(ch.trim())-1].set(false);
                    ch="A"+ch.trim();
                    cmdA.add(ch);
                }
                String echo=matrixA.channelSwitchOff(cmdA);
                Platform.runLater(()->{
                    taLog.appendText(sdf.format(new Date())+"矩阵A："+echo); //echo带换行
                });
            }
            if(matrixB.socket!=null && !textB.isEmpty()){
                String[] channelsB=textB.split("\\s+");
                List<String> cmdB = new ArrayList<>();
                for(int i=0;i<128;i++) {
                    channelStatesB[i].set(false);
                }
                for(String ch:channelsB){
                    channelStatesB[Integer.parseInt(ch.trim())-1].set(true);
                    ch="A"+ch.trim();
                    cmdB.add(ch);
                }
                String echo=matrixB.channelSwitchOff(cmdB);
                Platform.runLater(()->{
                    taLog.appendText(sdf.format(new Date())+"矩阵B："+echo);
                });
            }
        }).start();
    }

    @FXML
    public void onReset(){
        if(matrixA.socket!=null){
            String echo=matrixA.rst();
            Platform.runLater(()->{
                taLog.appendText(sdf.format(new Date())+"矩阵A复位："+echo);
            });
            for(int i=0;i<128;i++) {
                channelStatesA[i].set(false);
            }
        }
        if(matrixB.socket!=null){
            String echo=matrixB.rst();
            Platform.runLater(()->{
                taLog.appendText(sdf.format(new Date())+"矩阵B复位："+echo);
            });
            for(int i=0;i<128;i++) {
                channelStatesB[i].set(false);
            }
        }
    }


    @FXML
    public void initialize(){
        for(int i=0;i<128;i++){
            channelStatesA[i]=new SimpleBooleanProperty(false);
            channelStatesB[i]=new SimpleBooleanProperty(false);
        }

    }



}
