/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Patient;
import utils.ObjectCache;
import utils.SQLStatements;

/**
 *
 * @author chase
 */
public class MainController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
    
    public void SceneChange(String string) throws IOException {
        scene = FXMLLoader.load(getClass().getResource(string));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    @FXML
    private TableView<Patient> patientTable;

    @FXML
    private TableColumn<Patient, Integer> patientId;

    @FXML
    private TableColumn<Patient, String> patientName;

    @FXML
    private TableColumn<Patient, String> patientAddress;

    @FXML
    private TableColumn<Patient, String> patientInsurance;

    @FXML
    private Button addPatient;

    @FXML
    private Button updatePatient;

    @FXML
    private Button deletePatient;
    
    @FXML
    private Label viewLabel;

    @FXML
    private TableView<Appointment> weeklyTable;

    @FXML
    private TableColumn<Appointment, Integer> weeklyAppId;

    @FXML
    private TableColumn<Appointment, String> weeklyPatient;

    @FXML
    private TableColumn<Appointment, String> weeklyCounselor;

    @FXML
    private TableColumn<Appointment, String> weeklyType;

    @FXML
    private TableColumn<Appointment, String> weeklyNotes;

    @FXML
    private TableColumn<Appointment, String> weeklyStart;

    @FXML
    private Button addAppointment;

    @FXML
    private Button updateAppointment;

    @FXML
    private Button deleteAppointment;
    
    @FXML
    private Button nextTableButton;

    @FXML
    private Button reports;
    
    @FXML
    void onAddPatient(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        SceneChange("/view/PatientScreen.fxml");
    }
    
    @FXML
    void onUpdatePatient(ActionEvent event) throws IOException {
        if(patientTable.getSelectionModel().getSelectedItem() == null)
            return;
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/PatientScreen.fxml"));
        loader.load();
        PatientController PatientScreenController = loader.getController();
        PatientScreenController.sendPatient(patientTable.getSelectionModel().getSelectedItem());
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onDeletePatient(ActionEvent event) throws SQLException {
        if(patientTable.getSelectionModel().getSelectedItem() != null){
            confirmation.setTitle("Delete Patient Record");
            confirmation.setContentText("Are you sure you want to delete the patient record? "
                    + "This process can not be undone once completed.");

            Optional<ButtonType> result = confirmation.showAndWait();
        
            if(result.get() == ButtonType.OK){
                SQLStatements.deletePatient(patientTable.getSelectionModel().getSelectedItem().getPt_id());
                ObjectCache.patientList.remove(patientTable.getSelectionModel().getSelectedItem());
            }
        }
    }
    
    @FXML
    void onAddAppointment(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        SceneChange("/view/AppointmentScreen.fxml");
    }

    @FXML
    void onUpdateAppointment(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/AppointmentScreen.fxml"));
        loader.load();
        AppointmentController AppointmentScreenController = loader.getController();
        
        if(weeklyTable.getSelectionModel().getSelectedItem() != null)
            AppointmentScreenController.sendAppointment(weeklyTable.getSelectionModel().getSelectedItem());
            
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }    

    @FXML
    void onDeleteAppointment(ActionEvent event) throws SQLException {
        if(weeklyTable.getSelectionModel().getSelectedItem() != null){
            confirmation.setTitle("Delete Appointment");
            confirmation.setContentText("Are you sure you want to delete the appointment? "
                    + "This process can not be undone once completed.");

            Optional<ButtonType> result = confirmation.showAndWait();
        
            if(result.get() == ButtonType.OK){
                Appointment app = new Appointment();
                app = weeklyTable.getSelectionModel().getSelectedItem();
                SQLStatements.deleteAppointment(weeklyTable.getSelectionModel().getSelectedItem().getApt_id());
                ObjectCache.appointmentList.remove(app);//remove(weeklyTable.getSelectionModel().getSelectedItem());
                ObjectCache.weeklyAppointmentList.remove(app); //.remove(weeklyTable.getSelectionModel().getSelectedItem());
                ObjectCache.biweeklyAppointmentList.remove(app); //.remove(weeklyTable.getSelectionModel().getSelectedItem());
                ObjectCache.monthlyAppointmentList.remove(app);//.remove(weeklyTable.getSelectionModel().getSelectedItem());
            }
        }
    }
    
    @FXML
    void onNextTable(ActionEvent event) {
        switch (nextTableButton.getText()) {
            case "Weekly View":
                viewLabel.setText("Weekly View");
                nextTableButton.setText("Biweekly View");
                weeklyTable.setItems(ObjectCache.weeklyAppointmentList);
                weeklyAppId.setCellValueFactory(new PropertyValueFactory<>("apt_id"));
                weeklyPatient.setCellValueFactory(new PropertyValueFactory<>("displayPatient"));
                weeklyCounselor.setCellValueFactory(new PropertyValueFactory<>("displayCounselor"));
                weeklyType.setCellValueFactory(new PropertyValueFactory<>("displayType"));
                weeklyNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
                weeklyStart.setCellValueFactory(new PropertyValueFactory<>("displayTime"));
                break;
            case "Biweekly View":
                viewLabel.setText("Biweekly View");
                nextTableButton.setText("Monthly View");
                weeklyTable.setItems(ObjectCache.biweeklyAppointmentList);
                weeklyAppId.setCellValueFactory(new PropertyValueFactory<>("apt_id"));
                weeklyPatient.setCellValueFactory(new PropertyValueFactory<>("displayPatient"));
                weeklyCounselor.setCellValueFactory(new PropertyValueFactory<>("displayCounselor"));
                weeklyType.setCellValueFactory(new PropertyValueFactory<>("displayType"));
                weeklyNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
                weeklyStart.setCellValueFactory(new PropertyValueFactory<>("displayTime"));
                break;
            case "Monthly View":
                viewLabel.setText("Monthly View");
                nextTableButton.setText("Weekly View");
                weeklyTable.setItems(ObjectCache.monthlyAppointmentList);
                weeklyAppId.setCellValueFactory(new PropertyValueFactory<>("apt_id"));
                weeklyPatient.setCellValueFactory(new PropertyValueFactory<>("displayPatient"));
                weeklyCounselor.setCellValueFactory(new PropertyValueFactory<>("displayCounselor"));
                weeklyType.setCellValueFactory(new PropertyValueFactory<>("displayType"));
                weeklyNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
                weeklyStart.setCellValueFactory(new PropertyValueFactory<>("displayTime"));
                break;            
            default:
                break;
        }
    }
    
    @FXML
    void onReports(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        SceneChange("/view/Report.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Populates and updates the lists as needed.
        ObjectCache.getWeeklyAppointments();
        ObjectCache.getBiweeklyAppointments();
        ObjectCache.getMonthlyAppointments();
        
        //Sets the patient table
        patientTable.setItems(ObjectCache.patientList);
        patientId.setCellValueFactory(new PropertyValueFactory<>("pt_id"));
        patientName.setCellValueFactory(new PropertyValueFactory<>("pt_name"));
        patientAddress.setCellValueFactory(new PropertyValueFactory<>("displayAddress"));
        patientInsurance.setCellValueFactory(new PropertyValueFactory<>("INS_PR"));
                
        //Sets the appointment table
        viewLabel.setText("Weekly");
        weeklyTable.setItems(ObjectCache.weeklyAppointmentList);
        weeklyAppId.setCellValueFactory(new PropertyValueFactory<>("apt_id"));
        weeklyPatient.setCellValueFactory(new PropertyValueFactory<>("displayPatient"));
        weeklyCounselor.setCellValueFactory(new PropertyValueFactory<>("displayCounselor"));
        weeklyType.setCellValueFactory(new PropertyValueFactory<>("displayType"));
        weeklyNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        weeklyStart.setCellValueFactory(new PropertyValueFactory<>("displayTime"));   
    }  
}
