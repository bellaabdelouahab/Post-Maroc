package Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import Controllers.Login;
import animatefx.animation.FadeIn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


// Main Class
public class App extends Application {
    static DataBaseConnection connection;
    public static void main(String[] args) throws Exception {
        connection = new DataBaseConnection();
        launch(args);
    }
    // connect to database
    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        Properties Prop = new Properties();
        FileInputStream config = new FileInputStream(System.getProperty("user.dir") + "/src/Config.properties");
        Prop.load(config);
        FXMLLoader loader = new FXMLLoader(App.class.getResource("../Resources/VIEW/LogIn.fxml"));
        Parent root=loader.load();
        Login controller = loader.getController();
        controller.connection = connection;
        controller.initializ();
        // if (Prop.getProperty("MainPage").equals("null")) {
        // } else if (Prop.getProperty("MainPage").equals("0")) {
        //     // FirstPageConroller controller = loader.getController();
        //     // controller.SwitchToEmployerPage(new ActionEvent());
        // } else if (Prop.getProperty("MainPage").equals("1")){
        //     // FirstPageConroller controller = loader.getController();
        //     // controller.SwitchToAdminPage(new ActionEvent());
        // }
        
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Hotel BBBE");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
        new FadeIn(root).play();
        primaryStage.setResizable(false);
    }
}
