/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

Localization techniques demonstrated by Malcom Wabara during Localization lecture

Additional Resource/Example: https://riptutorial.com/javafx/example/19339/loading-resource-bundle

Printing to login success/failure record adapted from lecture presented by
Malcom Wabara
 */
package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Counselor;
import utils.ObjectCache;
import utils.SQLStatements;

/**
 *
 * @author chase
 */
public class LogInController implements Initializable {
    
    //Initialized strings to use with resourcebundle. 
    //They are given values when the screen is launched.
    String pin_header;
    String pin_text;
    String pass_header;
    String pass_text;
    String user_header;
    String user_text;
    
    Stage stage;
    Parent scene;
    
    Alert error = new Alert(Alert.AlertType.ERROR,"");

    
    public void SceneChange(String string) throws IOException {
        scene = FXMLLoader.load(getClass().getResource(string));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    @FXML
    private Label companyTitleLabel;

    @FXML
    private TextField usernameTxtBx;

    @FXML
    private TextField passwordTxtBx;

    @FXML
    private TextField pinTxtBx;

    @FXML
    private Button loginButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label mainOfficeLabel;

    @FXML
    private Label officeTimeLabel;
    
    @FXML
    private Label officeDayLabel;

    @FXML
    private Label localOfficeLabel;

    @FXML
    private Label localTimeLabel;
    
    @FXML
    private Label localDayLabel;

    @FXML //Closes the program.
    void onExit(ActionEvent event) {
        stage = (Stage)exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML //Goes to the main screen.
    void onLogin(ActionEvent event) throws IOException, SQLException {
        String username = usernameTxtBx.getText();
        String password = passwordTxtBx.getText();
        String pin = pinTxtBx.getText();
        boolean userFound = false;
        boolean correct = false;
        
        for(Counselor counselor : ObjectCache.counselorList){
            if(username.equals(counselor.getC_name())){
                userFound = true;
                if(password.equals(counselor.getC_password())){
                    if(pin.equals(counselor.getC_pin())){
                        ObjectCache.currentUser = counselor;
                        correct = true;
                    } else{
                        FileWriter fw = new FileWriter("failure.txt", true);
                        PrintWriter outputFile = new PrintWriter(fw);
                        outputFile.println(username + "\t" + ZonedDateTime.now().withZoneSameInstant(ZoneId.of("America/New_York")).format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a")));
                        outputFile.close();
                        
                        error.setTitle(pin_header); //This is a security risk. Should not be telling person what they got wrong.
                        error.setContentText(pin_text);
                        error.showAndWait();
                        return;
                    }
                } else{
                    
                    FileWriter fw = new FileWriter("failure.txt", true);
                    PrintWriter outputFile = new PrintWriter(fw);
                    outputFile.println(username + "\t" + ZonedDateTime.now().withZoneSameInstant(ZoneId.of("America/New_York")).format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a")));
                    outputFile.close();
                    
                    error.setTitle(pass_header); //This is a security risk. Should not be telling person what they got wrong.
                    error.setContentText(pass_text);
                    error.showAndWait();
                    return;
                }
            }
        }
        
        if(userFound == false){
            
            FileWriter fw = new FileWriter("failure.txt", true);
            PrintWriter outputFile = new PrintWriter(fw);
            outputFile.println(username + "\t" + ZonedDateTime.now().withZoneSameInstant(ZoneId.of("America/New_York")).format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a")));
            outputFile.close();
            
            error.setTitle(user_header); //This is a security risk. Should not be telling person what they got wrong.
            error.setContentText(user_text);
            error.showAndWait();
        }
        
        
        
        if(correct){
            
            //Write to success file. Adapted from Malcom Wabara webinar on PrintWriter
            FileWriter fw = new FileWriter("success.txt", true);
            PrintWriter outputFile = new PrintWriter(fw);
            outputFile.println(ObjectCache.currentUser.getC_name() + "\t" + ZonedDateTime.now().withZoneSameInstant(ZoneId.of("America/New_York")).format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a")));
            outputFile.close();
            
            //Create an alert that notifies user of appointments in the next four hours.
             String text = "You have the following appointments in the next four hours:\n";

             for(Appointment a: ObjectCache.getFourHourList()){
                 text += a.toString();
                 text += "\n";
             }

             Alert alert = new Alert(AlertType.INFORMATION);
             alert.setHeaderText("Upcoming Appointments");

             //If no appointments overrides the text box to say so.
             if(ObjectCache.getFourHourList().isEmpty())
                 text = "You have no appointments in the next four hours.";

             alert.setContentText(text);    
             alert.show();
            
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        SceneChange("/view/Main.fxml");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        usernameTxtBx.setPromptText(rb.getString("username"));
        passwordTxtBx.setPromptText(rb.getString("password"));
        pinTxtBx.setPromptText(rb.getString("pin"));
        
        pin_header = rb.getString("PIN_ERROR");
        pin_text = rb.getString("pin_message");
        pass_header = rb.getString("PASSWORD_ERROR");
        pass_text = rb.getString("password_message");
        user_header = rb.getString("USER_ERROR");
        user_text = rb.getString("user_message");
        companyTitleLabel.setText(rb.getString("group"));
        loginButton.setText(rb.getString("login"));
        exitButton.setText(rb.getString("exit"));
        localOfficeLabel.setText(rb.getString("local"));
        mainOfficeLabel.setText(rb.getString("main"));
        
        
        try {
            //Populates cache objects for use with rest of program.
            ObjectCache.APTtypeList.addAll(SQLStatements.getAPTtype());
            ObjectCache.addressList.addAll(SQLStatements.getAddress());
            ObjectCache.appointmentList.addAll(SQLStatements.getAppointment());
            ObjectCache.counselorList.addAll(SQLStatements.getCounselor());
            ObjectCache.patientList.addAll(SQLStatements.getPatient());
            ObjectCache.getStateList();
            ObjectCache.getAppointmentTimes();
            ObjectCache.getProhibitedDays();
                        
            //Gets the current time in UTC, main office, and local time
            Instant instant = Instant.now(); //Gets the UTC time for the moment of startup
            ZonedDateTime officeTime = instant.atZone(ZoneId.of("America/New_York")); //Main office's time
            ZonedDateTime localTime = instant.atZone(TimeZone.getDefault().toZoneId()); //Program user's time
            //Formats time and updates labels
            officeTimeLabel.setText(officeTime.format(DateTimeFormatter.ofPattern("hh:mm a"))); //Formats main office time to US standards
            officeDayLabel.setText(officeTime.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))); //Formats main office date to US standards
            localTimeLabel.setText(localTime.format(DateTimeFormatter.ofPattern("hh:mm a"))); //Formats local time to US standards
            localDayLabel.setText(localTime.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))); //Formats local date to US standards
            
        } catch (SQLException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
