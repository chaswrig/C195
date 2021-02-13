/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.APTtype;
import model.Appointment;
import model.Counselor;
import model.Patient;
import utils.ObjectCache;
import utils.SQLStatements;

/**
 *
 * @author chase
 */
public class AppointmentController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    int appId = -1;
    Appointment oldAppointment;
    
    public void SceneChange(String string) throws IOException {
        scene = FXMLLoader.load(getClass().getResource(string));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    @FXML
    private Label appointmentLabel;

    @FXML
    private Label patientLabel;

    @FXML
    private Label conselorLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label notesLabel;

    @FXML
    private Label startDateLabel;

    @FXML
    private Label startTimeLabel;

    @FXML
    private ComboBox<Patient> patientCB;

    @FXML
    private ComboBox<Counselor> counselorCB;

    @FXML
    private ComboBox<APTtype> typeCB;

    @FXML
    private TextField notesTextBox;

    @FXML
    private DatePicker datePicker;
    
    @FXML
    private ComboBox<String> timeCB;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    void onAddButton(ActionEvent event) throws IOException, SQLException {
        
        if(patientCB.getValue() == null)
            return;
        
        if(counselorCB.getValue() == null)
            return;
        
        if(typeCB.getValue() == null)
            return;
        
        if(notesTextBox.getText().isEmpty())
            return;
        
        if(datePicker.getValue() == null)
            return;
        
        if(timeCB.getValue() == null)
            return;
        
        if(datePicker.getValue().isBefore(LocalDate.now())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Date Exception");
            alert.setContentText("The start date cannot be before the current date.");
            alert.showAndWait();
            return;
        }
            
        //Check to make sure date is not a holiday.
        for(LocalDate ld : ObjectCache.holidayList)
            if(datePicker.getValue().equals(ld)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Office Hours Exception");
                alert.setContentText("The office is closed on weekends and office holidays.");
                alert.showAndWait();
                return;
            }
                        
        if(addButton.getText().equalsIgnoreCase("Update")){
            //updates an existing appointment
            Patient p = patientCB.getValue();
            int pt_id = p.getPt_id();
            Counselor c = counselorCB.getValue();
            int cr_id = c.getC_id();
            APTtype t = typeCB.getValue();
            int apt_type_id = t.getAPTtype_id();
            String notes = notesTextBox.getText();
            String startDate = datePicker.getValue().toString();
            String startTime = timeCB.getValue();
            String start = startDate + " " + startTime;
            String user = ObjectCache.currentUser.getC_name();
            
            //This bit should store the time in Eastern. Currently not doing that.
            LocalDateTime ldt = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a"));
            ZonedDateTime start_datetime = ldt.atZone(ZoneId.systemDefault());
            
            //Checks to make sure start time is not before current time
            if(start_datetime.isBefore(ZonedDateTime.now())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Start Time Exception");
                alert.setContentText("The start time cannot be before the current time.");
                alert.showAndWait();
                return;
            }
            
            //Prevent overlapping appointments
            for(Appointment a: ObjectCache.appointmentList){
                //check to see if counselor or patient already have an existing appointment in the time slot
                if((cr_id == a.getCr_id()) || (pt_id == a.getPt_id())){
                // if counselor or patient are same, make sure no overlap after existing app start time and before the end of the appointment an hour later
                    if((start_datetime.isAfter(a.getStart_datetime()) & start_datetime.isBefore(a.getStart_datetime().plusHours(1)))
                            || (start_datetime.isEqual(a.getStart_datetime()) & start_datetime.isBefore(a.getStart_datetime().plusHours(1)))){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        if(cr_id == a.getCr_id()){
                            alert.setHeaderText("Counselor Unavailable");
                            alert.setContentText("The counselor has an existing appointment in that time slot.");
                            alert.showAndWait();
                            return;
                        } else if(pt_id == a.getPt_id()){
                            alert.setHeaderText("Patient Appointment Exists");
                            alert.setContentText("The patient has an existing appointment in that time slot.");
                            alert.showAndWait();
                            return;
                        }
                    }
                }
            }
            
            ZonedDateTime updated_at = ZonedDateTime.now();
            
            oldAppointment.setPt_id(pt_id);
            oldAppointment.setCr_id(cr_id);
            oldAppointment.setApt_type_id(apt_type_id);
            oldAppointment.setNotes(notes);
            oldAppointment.setStart_datetime(start_datetime);
            oldAppointment.setUpdated_at(updated_at);
            
            SQLStatements.updateAppointment(appId, pt_id, cr_id, apt_type_id, notes, start_datetime, updated_at, user, pt_id, cr_id, apt_type_id);
            
            //If everything works, go back to main.
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            SceneChange("/view/Main.fxml");
            
            
        } else{
            //create a new appointment
            Patient p = patientCB.getValue();
            int pt_id = p.getPt_id();
            Counselor c = counselorCB.getValue();
            int cr_id = c.getC_id();
            APTtype t = typeCB.getValue();
            int apt_type_id = t.getAPTtype_id();
            String notes = notesTextBox.getText();
            String startDate = datePicker.getValue().toString();
            String startTime = timeCB.getValue();
            String start = startDate + " " + startTime;
            String user = ObjectCache.currentUser.getC_name();
            
            //This bit should store the time in UTC. Currently not doing that.
            LocalDateTime ldt = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a"));
            ZonedDateTime start_datetime = ldt.atZone(ZoneId.systemDefault());
            
            //Checks to make sure start time is not before current time
            if(start_datetime.isBefore(ZonedDateTime.now())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Start Time Exception");
                alert.setContentText("The start time cannot be before the current time.");
                alert.showAndWait();
                return;
            }
            
            ZonedDateTime created_at = ZonedDateTime.now();
            ZonedDateTime updated_at = ZonedDateTime.now();
            int apt_id = SQLStatements.getNextAppointmentPK();
            Appointment appointment = new Appointment(apt_id, pt_id, cr_id, apt_type_id,
                    notes, start_datetime, ZonedDateTime.now(), user,
                    ZonedDateTime.now(), user);
            
            //Prevent overlapping appointments
            for(Appointment a: ObjectCache.appointmentList){
                //check to see if counselor or patient already have an existing appointment in the time slot
                if((appointment.getCr_id() == a.getCr_id()) || (appointment.getPt_id() == a.getPt_id())){
                // if counselor or patient are same, make sure no overlap after existing app start time and before the end of the appointment an hour later
                    if((appointment.getStart_datetime().isAfter(a.getStart_datetime()) & appointment.getStart_datetime().isBefore(a.getStart_datetime().plusHours(1)))
                            || (appointment.getStart_datetime().isEqual(a.getStart_datetime()) & appointment.getStart_datetime().isBefore(a.getStart_datetime().plusHours(1)))){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        
                        if(cr_id == a.getCr_id()){
                            alert.setHeaderText("Counselor Unavailable");
                            alert.setContentText("The counselor has an existing appointment in that time slot.");
                            alert.showAndWait();
                            return;
                        } else if(pt_id == a.getPt_id()){
                            alert.setHeaderText("Patient Appointment Exists");
                            alert.setContentText("The patient has an existing appointment in that time slot.");
                            alert.showAndWait();
                            return;
                        }
                    }
                }
            }
            
            ObjectCache.appointmentList.add(appointment);
                    
            SQLStatements.insertAppointment(apt_id, pt_id, cr_id, apt_type_id, 
                    notes, start_datetime, created_at, user, updated_at, 
                    user, pt_id, cr_id, apt_type_id);
            
            //If everything works, go back to main.
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            SceneChange("/view/Main.fxml");
        }
    }

    @FXML
    void onCancelButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        SceneChange("/view/Main.fxml");
    }
    
    public void sendAppointment(Appointment appointment){
        appointmentLabel.setText("Update Appointment Information");
        addButton.setText("Update");
        appId = appointment.getApt_id();
        oldAppointment = appointment;
        
        int patientId = appointment.getPt_id();
        Patient patient = null;
        
        for(Patient p : ObjectCache.patientList)
            if(p.getPt_id() == patientId)
                patient = p;
        
        patientCB.getSelectionModel().select(patient);
        
        int counselorId = appointment.getCr_id();
        Counselor counselor = null;
        
        for(Counselor c : ObjectCache.counselorList)
            if(c.getC_id() == counselorId)
                counselor = c;
        
        counselorCB.getSelectionModel().select(counselor);
        
        int typeId = appointment.getApt_type_id();
        APTtype type = null;
        
        for(APTtype t : ObjectCache.APTtypeList)
            if(t.getAPTtype_id() == typeId)
                type = t;
        
        typeCB.getSelectionModel().select(type);
        
        notesTextBox.setText(appointment.getNotes());
        
        ZonedDateTime time = appointment.getStart_datetime();
        
        datePicker.setValue(time.toLocalDate());
        timeCB.getSelectionModel().select(time.format(DateTimeFormatter.ofPattern("hh:mm a")));
        }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        patientCB.setItems(ObjectCache.patientList);
        counselorCB.setItems(ObjectCache.counselorList);
        typeCB.setItems(ObjectCache.APTtypeList);
        timeCB.setItems(ObjectCache.timeList);       
    }    
    
}
