package Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import org.controlsfx.control.Notifications;

import Controllers.Login;
import animatefx.animation.FadeIn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;





// Main Class
public class App extends Application {
    static DataBaseConnection connection;
    private static Stage pStage;
    public static void main(String[] args) throws Exception {
        
        connection = new DataBaseConnection();
        // System.out.println(BcryptTool.hashPassword("123456"));
        // create a pdf file reader
        

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
        Properties Prop = new Properties();
        FileInputStream config = new FileInputStream(System.getProperty("user.dir") + "/src/Config.properties");
        Prop.load(config);
        FXMLLoader loader = new FXMLLoader(App.class.getResource("../Resources/VIEW/LogIn.fxml"));
        Pane root=loader.load();
        Login controller = loader.getController();
        controller.connection = connection;
        controller.initializ();
        // JFXDatePicker dp = new JFXDatePicker();
        // root.getChildren().clear();
        // root.getChildren().add(dp);
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
        Pane X = new Pane();
        X.setPrefSize(300, 100);
        X.setLayoutX(250);
        X.setLayoutY(200);
        X.setStyle("-fx-background-color:red;-fx-background-radius:25");
        holder.getChildren().add(X);
        holder.setStyle("-fx-background:#00000000");
        Scene scene = new Scene(holder);
        scene.setFill(Color.TRANSPARENT);
        App.getpStage().setScene(scene);
    }
    public static void showAlert(String type, String message) {
        Notifications alert = Notifications.create()
                .title(type)
                .text(message)
                .hideAfter(javafx.util.Duration.seconds(2))
                .position(Pos.CENTER)
                .darkStyle()
                .hideCloseButton();
        if(type=="Success"){
            alert.showInformation();
        }else if(type=="Error"){
            alert.showError();
        }
        else if(type=="info"){
            alert.showInformation();
        }
    }
}
