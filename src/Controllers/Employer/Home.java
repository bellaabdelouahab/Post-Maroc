package Controllers.Employer;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import Main.App;
import Main.Employer_Connection;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Home {
    private Employer_Connection connection;

    @FXML
    private Pane Pane1_button_animation;
    @FXML
    private Circle Pane1_circle_animation;
    @FXML
    private Label msglabel;
    @FXML Pane Extraoption;
    @FXML
    private Pane parent_pane;
    @FXML private ImageView icon1;
    // Switch To Waiting Courier
    @FXML
    private void SwitchToWaitingCourier() {
        SwitchTo(true,false,false);
    }
    
    //SwitchToAllCourier
    @FXML
    private void SwitchToAllCourier(){
        SwitchTo(false,false,true);
    }
    //SwitchToConfirmedCourier
    @FXML
    private void SwitchToConfirmedCourier(){
        SwitchTo(false,true,false);
    }
    // Switcher
    private void SwitchTo(Boolean Waiting, Boolean Confirmed,Boolean All)  {
        try {
            FXMLLoader loder = new FXMLLoader(getClass().getResource("/Resources/VIEW/Employer/Courier.fxml"));
            Pane root=new Pane();


            try{
                root = loder.load();
            }
            catch (Exception e) {
                System.out.println(System.getProperty("user.dir")+"/../Resources/VIEW/Employer/Courier.fxml");
                System.exit(0);
            }
            CourierTable controller = loder.getController();
            controller.setConnection(connection);
            controller.SetData(Waiting, Confirmed,All);
            App.changeStage(root);
        } catch (IOException e) {
            // notifiy that it couldn't switch
            e.printStackTrace();
        }
        
    }
    @FXML
    private void ShowClientSearchForm(){
        if(!connection.getUser_account().getjobclass().equals("A")){
            App.CurrentNotification = "You are not allowed to access this page";
        }
        
        GaussianBlur blur2 = new GaussianBlur();
        blur2.setRadius(10);
        parent_pane.setEffect(blur2);
        String style_ = "-fx-background-color: #11111199;-fx-border-raduis:0;-fx-text-fill: white;-fx-font-size: 10px;-fx-font-family: 'Segoe UI';-fx-border-radius:0;";
        Pane search = new Pane();
        search.setPrefSize(280, 113);
        search.setLayoutX(192);
        search.setLayoutY(200);
        search.setStyle("-fx-background-color:#575656;-fx-background-radius:25");
        TextField search_text = new TextField();
        search_text.setPrefSize(200, 35);
        search_text.setLayoutX(45);
        search_text.setLayoutY(20);
        search_text.setPromptText("Enter Client ID");
        search_text.setStyle(style_);
        search_text.setAlignment(Pos.CENTER);
        JFXButton search_button = new JFXButton("Search");
        search_button.setPrefSize(93, 32);
        search_button.setLayoutX(96);
        search_button.setLayoutY(70);
        search_button.setRipplerFill(new Color(0.4353, 0.1451, 0.051, 1.0));
        search_button.setStyle("-fx-border-color:#919191;-fx-border-raduis:5;");
        search_button.setOnAction(e->{
            if(search_text.getText().equals("")){
                return;
            }
            Extraoption.setVisible(false);
            FXMLLoader loder = new FXMLLoader(getClass().getResource("/Resources/VIEW/Employer/ClientInfo.fxml"));
            try {
                Pane root = loder.load();
                ClientInfoForm controller = loder.getController();
                controller.setConnection(connection);   
                controller.fillinfo(search_text.getText());
                controller.closebutton.setOnAction(e1->{
                    parent_pane.getChildren().remove(root);
                });
                root.setOnMouseClicked(arg0->{
                    parent_pane.getChildren().remove(root);
                });
                root.setLayoutY(60);
                parent_pane.getChildren().add(root);
            } catch (IOException e1) {
                e1.printStackTrace();
                App.CurrentNotification = "Couldn't load the page, please report essue";
            }
        });
        Extraoption.setVisible(true);
        Extraoption.setOnMouseClicked(arg0->{
            Extraoption.getChildren().clear();
            Extraoption.setVisible(false);
        });
        search.getChildren().addAll(search_text,search_button);
        Extraoption.getChildren().add(search);
    }
    @FXML
    private void ShowCourierSearchForm(){
        if(!connection.getUser_account().getjobclass().equals("A")){
            App.CurrentNotification = "You are not allowed to access this page";
        }
        String style_ = "-fx-background-color: #11111199;-fx-border-raduis:0;-fx-text-fill: white;-fx-font-size: 10px;-fx-font-family: 'Segoe UI';-fx-border-radius:0;";
        Pane search = new Pane();
        search.setPrefSize(280, 113);
        search.setLayoutX(192);
        search.setLayoutY(200);
        search.setStyle("-fx-background-color:#575656;-fx-background-radius:25");
        TextField search_text = new TextField();
        search_text.setPrefSize(200, 35);
        search_text.setLayoutX(45);
        search_text.setLayoutY(20);
        search_text.setPromptText("Enter Courier ID");
        search_text.setStyle(style_);
        search_text.setAlignment(Pos.CENTER);
        Label RR = new Label("RR");
        RR.setPrefSize(30, 35);
        RR.setLayoutX(15);
        RR.setLayoutY(20);
        RR.setStyle(style_);
        RR.setAlignment(Pos.CENTER);
        Label MA = new Label("MA");
        MA.setPrefSize(30, 35);
        MA.setLayoutX(245);
        MA.setLayoutY(20);
        MA.setStyle(style_);   
        MA.setAlignment(Pos.CENTER);
        JFXButton search_button = new JFXButton("Search");
        search_button.setPrefSize(93, 32);
        search_button.setLayoutX(96);
        search_button.setLayoutY(70);
        search_button.setRipplerFill(new Color(0.4353, 0.1451, 0.051, 1.0));
        search_button.setStyle("-fx-border-color:#919191;-fx-border-raduis:5;");
        search_button.setOnAction(e->{
            System.out.println("saving");
            if(search_text.getText().equals("")){
                return;
            }
            Extraoption.setVisible(false);
            FXMLLoader loder = new FXMLLoader(getClass().getResource("/Resources/VIEW/Employer/courierinfo.fxml"));
            try {
                Pane root = loder.load();
                CourierInfoForm controller = loder.getController();
                Label status = new Label("Status");
                status.setPrefSize(60 , 25);
                status.setLayoutX(205);
                status.setLayoutY(335);
                status.setStyle(style_);
                status.setAlignment(Pos.CENTER);
                ComboBox<String> status_menu = new ComboBox<String>();
                status_menu.setPrefSize(120, 25);
                status_menu.setLayoutX(350);
                status_menu.setLayoutY(335);
                // add css class
                status_menu.getStyleClass().add("combo-box-css");
                status_menu.getItems().addAll("Waiting","Supported","Delivered","Cancel");
                Button validate = new Button ("Validate");
                validate.setPrefSize(100, 25);
                validate.setLayoutX(540);
                validate.setLayoutY(335);
                validate.setStyle(style_);
                validate.setOnAction(value->{
                    connection.UpdateCourierStatus(controller.Courier_id.getText(),status_menu.getValue());
                    parent_pane.getChildren().remove(root);
                });
                root.getChildren().addAll(status,status_menu,validate);
                controller.setConnection(connection);   
                controller.Fillinfo("RR"+search_text.getText()+"MA",false);
                controller.closebutton.setOnAction(e1->{
                    parent_pane.getChildren().remove(root);
                });
                root.setOnMouseClicked(arg0->{
                    parent_pane.getChildren().remove(root);
                });
                root.setLayoutY(90);
                parent_pane.getChildren().add(root);
            } catch (IOException e1) {
                e1.printStackTrace();
                App.CurrentNotification = "Couldn't load the page, please report essue";
            }
            });
        Extraoption.setVisible(true);
        Extraoption.setOnMouseClicked(arg0->{
            Extraoption.getChildren().clear();
            Extraoption.setVisible(false);
        });
        search.getChildren().addAll(search_text,search_button,RR,MA);
        Extraoption.getChildren().add(search);
    }
    // create a setter for Data Base Connection
    public void setConnection(Employer_Connection connection){
        this.connection = connection;
    }
    @FXML
    private void StartAnimation1() {
        Timeline timeline = App.GetButtonAnimtation(Pane1_button_animation, Pane1_circle_animation,100,53);
        timeline.play();
    }
    @FXML
    private void EndAnimation1(){
        Timeline timeline = App.GetButtonAnimtation(Pane1_button_animation, Pane1_circle_animation,0,50);
        timeline.play();
    }
    private void showlabel(){
        msglabel.setEffect(null);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(msglabel.layoutYProperty(), 440, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.2), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
    private void hidelabel(){
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(msglabel.layoutYProperty(), 462, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.2), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
    @FXML
    private void set_text_1(){
        msglabel.setText("Search All Data Base");
        showlabel();
    }
    @FXML
    private void set_text_2(){
        msglabel.setText("Search Client Information");
        showlabel();
    }
    @FXML
    private void set_text_3(){
        msglabel.setText("Validate Payment");
        showlabel();
    }
    @FXML   
    private void set_text_4(){
        msglabel.setText("Somthing Wrong");
        showlabel();
    }
    @FXML
    private void unset_text(){
        hidelabel();
        msglabel.setText("");
    }
    @FXML
    private void showProfile(){
        App.ShowProfile();
    }
    @FXML
    private void CloseWindow() {
        App.CloseWindow();
    }
    @FXML   
    private void MinimizeWindow() {
        App.getpStage().setIconified(true);
    }
    @FXML
    private void Logout(){
        App.Logout();
    }

}
