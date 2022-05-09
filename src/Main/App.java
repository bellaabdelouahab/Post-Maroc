package Main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;


import Controllers.Login;
import animatefx.animation.FadeIn;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

// Main Class
public class App extends Application {
    static DataBaseConnection connection;
    private static Stage pStage;
    private static Pane CurrentNotification;
    public static void main(String[] args) throws Exception {
        
        connection = new DataBaseConnection();
        System.out.println(BcryptTool.hashPassword("123456"));
        
        
        launch(args);
    }
    
    public static Stage getpStage() {
        return pStage;
    }
    public static void setpStage(Stage pStage) {
        App.pStage = pStage;
    }
    // connect to database
    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("../Resources/VIEW/LogIn.fxml"));
        Pane root=loader.load();
        Login controller = loader.getController();
        controller.setConnection(connection);
        controller.initializ();
        controller = loader.getController();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Hotel BBBE");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
        new FadeIn(root).play();
        primaryStage.setResizable(false);
        setpStage(primaryStage);
        // System.exit(0);
    }
    public static void changeStage(Pane root){
        AnchorPane holder = new AnchorPane();
        holder.getChildren().add(root);
        
        holder.setStyle("-fx-background:#00000000");
        Scene scene = new Scene(holder);
        scene.setFill(Color.TRANSPARENT);
        App.getpStage().setScene(scene);
    }
    public static void ShowNotificationWindow(String type, String message,ArrayList<Button> btns_list) {
        App.closeNotification();
        Pane X = new Pane();
        X.setPrefSize(300, 100);
        X.setLayoutX(250);
        X.setLayoutY(200);
        X.setStyle("-fx-background-radius:25;-fx-background-color:gray");
        JFXButton close_window = new JFXButton("X");
        close_window.setStyle("-fx-background-radius:25;-fx-background-color:#594875;-fx-text-fill:white");
        close_window.setLayoutX(270);
        close_window.setLayoutY(7);
        close_window.setOnAction(e -> App.closeNotification());
        Label message_label = new Label(message);
        // message_label.setStyle("-fx-background-color:red");
        message_label.setTextAlignment(TextAlignment.CENTER);
        message_label.setAlignment(Pos.CENTER);
        message_label.setPrefSize(250, 60);
        message_label.setLayoutX(25);
        message_label.setLayoutY(10);
        message_label.setWrapText(true);
        FlowPane buttons_area = new FlowPane();
        buttons_area.setPrefSize(250, 25);
        buttons_area.setLayoutX(25);
        buttons_area.setLayoutY(70);
        buttons_area.setAlignment(Pos.CENTER);
        // loop over all buttons
        if(btns_list!=null){
            for (Button btn : btns_list) {
                btn.setStyle(App.getStyle());
                buttons_area.getChildren().add(btn);
            }
        }
        X.getChildren().addAll(close_window, message_label, buttons_area);
        AnchorPane anchor_pane= (AnchorPane)(App.getpStage().getScene().getRoot());
        Pane x_ = (Pane) anchor_pane.getChildren().get(0);
        Pane Xoutter = new Pane();
        Xoutter.setPrefSize(800, 500);
        Xoutter.getChildren().add(X);
        CurrentNotification = Xoutter;
        x_.getChildren().add(Xoutter);
    }

    private static String getStyle() {
        return "-fx-padding: 0 25 0 25;-fx-background-radius: 8;";
    }

    public static void closeNotification() {
        AnchorPane anchor_pane= (AnchorPane)(App.getpStage().getScene().getRoot());
        Pane x_ = (Pane) anchor_pane.getChildren().get(0);
        x_.getChildren().remove(CurrentNotification);
    }
    public static Timeline GetButtonAnimtation(Pane Container,Circle rotatingcircle,int Endvalue1,int Endvalue2){
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(Container.rotateProperty(), Endvalue1, Interpolator.EASE_IN);
        KeyValue kv1 = new KeyValue(rotatingcircle.radiusProperty(), Endvalue2, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
        KeyFrame kf1 = new KeyFrame(Duration.seconds(0.5), kv1);
        timeline.getKeyFrames().add(kf);
        timeline.getKeyFrames().add(kf1);
        timeline.play();
        return timeline;
    }
    public static void CloseWindow(){
        this.connection.Disconnect();
        App.getpStage().close();
    }

}


