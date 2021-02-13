/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Address;
import model.Patient;
import utils.ObjectCache;
import utils.SQLStatements;

/**
 *
 * @author chase
 */
public class PatientController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    int patientId = -1;
    Patient oldPatient;
    
    public void SceneChange(String string) throws IOException {
        scene = FXMLLoader.load(getClass().getResource(string));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    @FXML
    private Label patientLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label address1label;

    @FXML
    private Label address2Label;

    @FXML
    private Label cityLabel;

    @FXML
    private Label stateLabel;

    @FXML
    private Label zipLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label insuranceLabel;

    @FXML
    private TextField nameTextBox;

    @FXML
    private TextField addressTextBox;

    @FXML
    private TextField address2TextBox;

    @FXML
    private TextField cityTextBox;

    @FXML
    private ChoiceBox stateCB;

    @FXML
    private TextField zipTextBox;

    @FXML
    private TextField phoneTextBox;

    @FXML
    private TextField insuranceTextBox;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    void onAddButton(ActionEvent event) throws IOException, SQLException {
        
        if(nameTextBox.getText().isEmpty())
            return;
        
        if(addressTextBox.getText().isEmpty())
            return;
        
        if(cityTextBox.getText().isEmpty())
            return;
        
        if(stateCB.getValue() == null)
            return;
        
        if(zipTextBox.getText().isEmpty())
            return;
        
        if(phoneTextBox.getText().isEmpty())
            return;
        
        if(insuranceTextBox.getText().isEmpty())
            return;
        
        int pt_id = -1;
        String pt_name = nameTextBox.getText();
        String addressline_1 = addressTextBox.getText();
        String addressline_2 = address2TextBox.getText();
        String city = cityTextBox.getText();
        String state = stateCB.getValue().toString();
        String postal_code = zipTextBox.getText();
        String phone = phoneTextBox.getText();
        int address_id = -1;
        ZonedDateTime created_at = ZonedDateTime.now();
        String created_by = ObjectCache.currentUser.getC_name();
        ZonedDateTime updated_at = ZonedDateTime.now();
        String updated_by = ObjectCache.currentUser.getC_name();
        String INS_PR = insuranceTextBox.getText();
        
        if(addButton.getText().equalsIgnoreCase("Update")){
            //update an existing patient
            pt_id = patientId;
            oldPatient.setPt_name(pt_name);
            
            //Look for existing address.
            for(Address address : ObjectCache.addressList){
                if(address.getState().equalsIgnoreCase(state)){
                    if(address.getCity().equalsIgnoreCase(city)){
                        if(address.getAddressline_1().equalsIgnoreCase(addressline_1)){
                            if(address.getAddressline_2().equalsIgnoreCase(addressline_2))
                                address_id = address.getAddress_id();
                        }
                    }
                }
            }
            //Create new address if one is not found
            if(address_id == -1){
                address_id = SQLStatements.getNextAddressPK();
                SQLStatements.insertAddress(address_id, addressline_1, addressline_2, city, state, postal_code, phone, created_at, created_by, updated_at, updated_by);
                Address newAddress = new Address(address_id, addressline_1, addressline_2, city, state, postal_code, phone, created_at, created_by, updated_at, updated_by);
                ObjectCache.addressList.add(newAddress);
            }
            
            SQLStatements.updatePatient(pt_id, pt_name, address_id, INS_PR, updated_at, updated_by);
            oldPatient.setAddress_id(address_id);
            oldPatient.setINS_PR(INS_PR);
            oldPatient.setUpdated_at(updated_at);
            oldPatient.setUpdated_by(updated_by);
            
            //If everything works, go back to main screen.
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            SceneChange("/view/Main.fxml");
            
        } else{
            //create a new customer
            pt_id = SQLStatements.getNextPatientPK();            
            
            //Look for existing address.
            for(Address address : ObjectCache.addressList){
                if(address.getState().equalsIgnoreCase(state)){
                    if(address.getCity().equalsIgnoreCase(city)){
                        if(address.getAddressline_1().equalsIgnoreCase(addressline_1)){
                            if(address.getAddressline_2().equalsIgnoreCase(addressline_2))
                                address_id = address.getAddress_id();
                        }
                    }
                }
            }
            //Create new address if one is not found
            if(address_id == -1){
                address_id = SQLStatements.getNextAddressPK();
                SQLStatements.insertAddress(address_id, addressline_1, addressline_2, city, state, postal_code, phone, created_at, created_by, updated_at, updated_by);
                Address newAddress = new Address(address_id, addressline_1, addressline_2, city, state, postal_code, phone, created_at, created_by, updated_at, updated_by);
                ObjectCache.addressList.add(newAddress);
            }
            
            SQLStatements.insertPatient(pt_id, pt_name, address_id, INS_PR, created_at, created_by, updated_at, updated_by);
            Patient newPatient = new Patient(pt_id, pt_name, address_id, INS_PR, created_at, created_by, updated_at, updated_by);
            ObjectCache.patientList.add(newPatient);
            
            //If everything works, go back to main screen.
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            SceneChange("/view/Main.fxml");   
        }
    }

    @FXML //Should probably clear the boxes as it leaves.
    void onCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        SceneChange("/view/Main.fxml");
    }
    
    public void sendPatient(Patient patient){
        patientLabel.setText("Update Patient Information");
        addButton.setText("Update");
        oldPatient = patient;
        patientId = patient.getPt_id();
        nameTextBox.setText(patient.getPt_name());
        
        //This could probably be a lamda expression.
        int id = -1;
        Address ptAddress = null;
        for (Address address : ObjectCache.addressList) {
            id = address.getAddress_id();
            if(id == patient.getAddress_id())
                ptAddress = address;
        }
        
        addressTextBox.setText(ptAddress.getAddressline_1());
        address2TextBox.setText(ptAddress.getAddressline_2());
        cityTextBox.setText(ptAddress.getCity());
        stateCB.getSelectionModel().select(ptAddress.getState());
        zipTextBox.setText(ptAddress.getPostal_code());
        phoneTextBox.setText(ptAddress.getPhone());
        insuranceTextBox.setText(patient.getINS_PR());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObjectCache.getStateList();
        stateCB.setItems(ObjectCache.stateList.sorted());
    }     
}
