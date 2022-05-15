package Controllers.Client;

import Main.DataBaseConnection;

public class form_2 {

    private DataBaseConnection connection;


    // Float weight_ = 0f;
    // Float price_ = 0f;
    // try{
    // weight_ = weight.getText().isEmpty() ? 0 : Float.parseFloat(weight.getText());
    // price_ = Pricefield.getText().isEmpty() ? 0 : Float.parseFloat(Pricefield.getText());
    // CheckPriceField();
    // }catch(Exception E){
    //     App.ShowNotificationWindow("Error",  "Please enter a valid weight",null);
    //     return;
    // }
    // // get date from text fields
    // LocalDate collect_date_ = collect_date.getValue();
    // String collectHour = combohour.getValue();
    // String collectMinutes = combominutes.getValue();
    // // check if the entered date bigger  current date
    // if(!collect_date_.isAfter(LocalDate.now()) ){
    //     App.ShowNotificationWindow("Error",  "Please enter a valid date",null);
    //     System.out.println(collect_date_+"<==>"+LocalDate.now());
    //     return;
    // }

    public void setConnection(DataBaseConnection connection) {
        this.connection = connection;
    }
    
}
