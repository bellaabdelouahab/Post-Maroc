package Main;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import Controllers.Login;
import Controllers.Profile;
import animatefx.animation.FadeIn;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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
    public static AnchorPane BaseWindow;
    private static Stage primaryStage;
    public static String CurrentNotification;
    public static Button ExtraButton;
    private static Pane CurrentNotificationPane;
    public void Main(String[] args) throws Exception {
        launch(args);
        
    }
    
    public static Stage getprimaryStage() {
        return primaryStage;
    }
    public static void setprimaryStage(Stage primaryStage) {
        App.primaryStage = primaryStage;
    }
    // connect to database
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/Resources/VIEW/LogIn.fxml"));
        Pane root = loader.load();
        Login controller = loader.getController();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    connection = new DataBaseConnection(true);
                    controller.setConnection(connection);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        controller.setConnection(connection);
        controller.initializ();
        connection = controller.getConnection();
        BaseWindow = new AnchorPane();
        BaseWindow.getChildren().add(root);
        BaseWindow.setStyle("-fx-border-radius:25;-fx-background-radius:25;-fx-background-color:#00000000");
        Scene scene = new Scene(BaseWindow);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Courier Managment System");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
        new FadeIn(root).play();
        setprimaryStage(primaryStage);
        NotificationUpdater();
    }
    private void NotificationUpdater() {
        Timeline Updater = new Timeline();
        KeyValue kvs = new KeyValue(new Pane().layoutXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(100), kvs);
        Updater.getKeyFrames().add(kf);
        Updater.setOnFinished(e -> {
            if(CurrentNotification==null){
                Updater.play();
                return;
            }
            ShowNotificationWindow(CurrentNotification);
            System.out.println("showing");
            CurrentNotification = null;
            Updater.play();
        });
        Updater.play();
    }

    public static void changeStage(Pane root){
        BaseWindow.getChildren().clear();
        BaseWindow.getChildren().add(root);
    }
    public static void ShowNotificationWindow( String message) {
        App.closeNotification();
        Pane X = new Pane();
        X.setPrefSize(300, 100);
        X.setLayoutX(250);
        X.setLayoutY(200);
        X.setStyle("-fx-background-radius:10;-fx-background-color:gray");
        JFXButton close_window = new JFXButton("X");
        close_window.setStyle("-fx-background-radius:20;-fx-background-color:#343434;-fx-text-fill:white");
        close_window.setLayoutX(270);
        close_window.setLayoutY(7);
        close_window.setOnAction(e -> App.closeNotification());
        Label message_label = new Label(message);
        message_label.setTextAlignment(TextAlignment.CENTER);
        message_label.setAlignment(Pos.CENTER);
        message_label.setPrefSize(250, 60);
        message_label.setLayoutX(25);
        message_label.setLayoutY(10);
        message_label.setWrapText(true);
        FlowPane buttons_area = new FlowPane();
        
        buttons_area.setPrefSize(250, 25);
        buttons_area.setLayoutX(25);
        buttons_area.setLayoutY(60);
        buttons_area.setAlignment(Pos.CENTER);
        String style = "-fx-background-radius:10;-fx-border-radius:10;-fx-background-color:#434343";
        Button CloseNotification = new Button("OK");
        CloseNotification.setStyle(style);
        CloseNotification.setPrefWidth(65);
        CloseNotification.setPadding(new Insets(3, 10, 3, 10));
        FlowPane.setMargin(CloseNotification, new Insets(0, 10, 0, 10));
        // close notification window
        CloseNotification.setOnAction(E -> closeNotification());
        buttons_area.getChildren().add(CloseNotification);
        if(ExtraButton != null){
            ExtraButton.setStyle(style);
            ExtraButton.setPadding(new Insets(3, 10, 3, 10));
            buttons_area.getChildren().add(ExtraButton);
            
        }
        X.getChildren().addAll(close_window, message_label, buttons_area);
        Pane Xoutter = new Pane();
        Xoutter.setPrefSize(800, 500);
        Xoutter.getChildren().add(X);
        Xoutter.setOnMouseClicked(e->{
            closeNotification();
        });
        CurrentNotificationPane = Xoutter;
        BaseWindow.getChildren().add(Xoutter);
    }

    public static void closeNotification() {
        BaseWindow.getChildren().remove(CurrentNotificationPane);
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
        if(DataBaseConnection.Disconnect()){
            primaryStage.close();
        }
    }

    public static void Logout() {
        DataBaseConnection.Disconnect();
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/Resources/VIEW/LogIn.fxml"));
            Pane root = loader.load();
            Login controller = loader.getController();
            controller.initializ();
            controller.setConnection(connection);
            BaseWindow.getChildren().clear();
            BaseWindow.getChildren().add(root);
            //  animation fadein
            new FadeIn(root).play();
        } catch (IOException ex) {  
            // could not load login window
            CurrentNotification = "Could not load login window";
        }
    }

    public static void ShowProfile() {
        try {
            FXMLLoader loder = new FXMLLoader(App.class.getResource("/Resources/VIEW/Profile.fxml"));
            Pane root = loder.load();
            Profile controller = loder.getController();
            controller.setConnection(connection);
            root.translateXProperty().set(-400);
            BaseWindow.getChildren().add(root);
            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(0.7), kv);
            timeline.getKeyFrames().add(kf);
            timeline.play();
        } catch (IOException e) {
            e.printStackTrace();
            CurrentNotification =  "Could not load page close app and try again";
        }
    }
    
}


