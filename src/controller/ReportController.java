/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

a. count of appointments by type for the current year
b. appointment totals by counselor for the current year
c. appointment totals by state for the current year

Using a switch statement for type count.
https://docs.oracle.com/javase/tutorial/java/nutsandbolts/switch.html

 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.APTtype;
import model.Address;
import model.Appointment;
import model.Counselor;
import model.Patient;
import model.Report;
import utils.ObjectCache;

/**
 *
 * @author chase
 */
public class ReportController implements Initializable {
    
    static int currentYear = LocalDate.now().getYear();
    public static ObservableList<Report> typeCount = FXCollections.observableArrayList();
    public static ObservableList<Report> counselorCount = FXCollections.observableArrayList();
    public static ObservableList<Report> stateCount = FXCollections.observableArrayList();
    
    Stage stage;
    Parent scene;
    
    public void SceneChange(String string) throws IOException {
        scene = FXMLLoader.load(getClass().getResource(string));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    @FXML
    private TableView<Report> numberTable;

    @FXML
    private TableColumn<Report, String> TypeColumn;

    @FXML
    private TableColumn<Report, String> number_numberColumn;

    @FXML
    private TableView<Report> counselorTable;

    @FXML
    private TableColumn<Report, String> counselorColumn;

    @FXML
    private TableColumn<Report, String> counselor_numberColumn;

    @FXML
    private TableView<Report> stateTable;

    @FXML
    private TableColumn<Report, String> stateColumn;

    @FXML
    private TableColumn<Report, String> state_numberColumn;
    
    @FXML
    private Button cancelButton;
    
    public static ObservableList<Report> getTypeCount(){
        typeCount.clear();
    
        //a. count of appointments by type for the current year
        int currentAppType = -1;
        int type0 = 0;
        int type1 = 0;
        int type2 = 0;
        int type3 = 0;
        for(Appointment appointment : ObjectCache.appointmentList){
            if(appointment.getStart_datetime().getYear() == currentYear){
                currentAppType = appointment.getApt_type_id();
                
                switch(currentAppType){
                    case 0: type0++;
                            break;
                            
                    case 1: type1++;
                            break;
                            
                    case 2: type2++;
                            break;
                            
                    case 3: type3++;
                            break;
                }           
            }
        }
        
        for(APTtype apt: ObjectCache.APTtypeList){
            String s = apt.getDescription();
            int id = apt.getAPTtype_id();

            switch(id){
                case 0: Report r1 = new Report(s,type0);
                        typeCount.add(r1);
                        break;

                case 1: Report r2 = new Report(s,type1);
                        typeCount.add(r2);
                        break;

                case 2: Report r3 = new Report(s,type2);
                        typeCount.add(r3);
                        break;

                case 3: Report r4 = new Report(s,type3);
                        typeCount.add(r4);
                        break;
            }
        }
        
        return typeCount;
    }
    
    public static ObservableList<Report> getCounselorCount(){
        //b. appointment totals by counselor for the current year
        counselorCount.clear();
        int currentCounselorId = -1;
        int counselor0 = 0;
        int counselor1 = 0;
        int counselor2 = 0;
        int counselor3 = 0;
        
        for(Appointment appointment : ObjectCache.appointmentList){
            if(appointment.getStart_datetime().getYear() == currentYear){
                currentCounselorId = appointment.getCr_id();
                
                switch(currentCounselorId){
                    case 0: counselor0++;
                            break;
                            
                    case 1: counselor1++;
                            break;
                            
                    case 2: counselor2++;
                            break;
                            
                    case 3: counselor3++;
                            break;
                }           
            }
        }
        
        for(Counselor c : ObjectCache.counselorList){
            String s = c.getC_name();
            int id = c.getC_id();

            switch(id){
                case 0: Report c1 = new Report(s,counselor0);
                        counselorCount.add(c1);
                        break;

                case 1: Report c2 = new Report(s,counselor1);
                        counselorCount.add(c2);
                        break;

                case 2: Report c3 = new Report(s,counselor2);
                        counselorCount.add(c3);
                        break;

                case 3: Report c4 = new Report(s,counselor3);
                        counselorCount.add(c4);
                        break;
            }
        }
        
        return counselorCount;
    }
    
    public static ObservableList<Report> getStateCount(){
        stateCount.clear();
    
        //c. appointment totals by state for the current year
        String currentState = null;
        int pt_id = -1;
        int address_id = -1;
        
        int alabama = 0;
        int alaska = 0;
        int arizona = 0;
        int arkansas = 0;
        int california = 0; //5
        int colorado = 0;
        int connecticut = 0;
        int delaware = 0;
        int florida = 0;
        int georgia = 0; //10
        int hawaii = 0;
        int idaho = 0;
        int illinois = 0;
        int indiana = 0;
        int iowa = 0; //15
        int kansas = 0;
        int kentucky = 0;
        int louisiana = 0;
        int maine = 0;
        int maryland = 0; //20
        int massachusetts = 0;
        int michigan = 0;
        int minnesota = 0;
        int mississippi = 0;
        int missouri = 0; //25
        int montana = 0;
        int nebraska = 0;
        int nevada = 0;
        int new_hampshire = 0;
        int new_jersey = 0;//30
        int new_mexico = 0;
        int new_york = 0;
        int north_carolina = 0;
        int north_dakota = 0;
        int ohio = 0; //35
        int oklahoma = 0;
        int oregon = 0;
        int pennsylvania = 0;
        int rhode_island = 0;
        int south_carolina = 0; //40
        int south_dakota = 0;
        int tennessee = 0;
        int texas = 0;
        int utah = 0;
        int vermont = 0; //45
        int virginia = 0;
        int washington = 0;
        int west_virginia = 0;
        int wisconson = 0;
        int wyoming = 0; //50
        int d_c = 0;
        
        
        for(Appointment appointment : ObjectCache.appointmentList){
            if(appointment.getStart_datetime().getYear() == currentYear){
                pt_id = appointment.getPt_id();
               
                for (Patient patient : ObjectCache.patientList){
                    if(patient.getPt_id() == pt_id){
                         
                         address_id = patient.getAddress_id();
                         for(Address address : ObjectCache.addressList){
                             if(address.getAddress_id() == address_id){
                                
                                currentState = address.getState();

                                switch(currentState){
                                           case "Alabama": alabama++;
                                           break;

                                           case "Alaska": alaska++;
                                           break;

                                           case "Arizona": arizona++;
                                           break;

                                           case "Arkansas": arkansas++;
                                           break;

                                           case "California": california++;
                                           break;

                                           case "Colorado": colorado++;
                                           break;

                                           case "Connecticut": connecticut++;
                                           break;

                                           case "Delaware": delaware++;
                                           break;

                                           case "Florida": florida++;
                                           break;

                                           case "Georgia": georgia++; //10
                                           break;

                                           case "Hawaii": hawaii++;
                                           break;

                                           case "Idaho": idaho++;
                                           break;

                                           case "Illinois": illinois++;
                                           break;

                                           case "Indiana": indiana++;
                                           break;

                                           case "Iowa": iowa++;
                                           break;

                                           case "Kansas": kansas++;
                                           break;

                                           case "Kentucky": kentucky++;
                                           break;

                                           case "Louisiana": louisiana++;
                                           break;

                                           case "Maine": maine++;
                                           break;

                                           case "Maryland": maryland++; //20
                                           break;

                                           case "Massachusetts": massachusetts++;
                                           break;

                                           case "Michigan": michigan++;
                                           break;

                                           case "Minnesota": minnesota++;
                                           break;

                                           case "Mississippi": mississippi++;
                                           break;

                                           case "Missouri": missouri++;
                                           break;

                                           case "Montana": montana++;
                                           break;

                                           case "Nebraska": nebraska++;
                                           break;

                                           case "Nevada": nevada++;
                                           break;

                                           case "New Hampshire": new_hampshire++;
                                           break;

                                           case "New Jersey": new_jersey++; //30
                                           break;

                                           case "New Mexico": new_mexico++;
                                           break;

                                           case "New York": new_york++;
                                           break;

                                           case "North Carolina": north_carolina++;
                                           break;

                                           case "North Dakota": north_dakota++;
                                           break;

                                           case "Ohio": ohio++;
                                           break;

                                           case "Oklahoma": oklahoma++;
                                           break;

                                           case "Oregon": oregon++;
                                           break;

                                           case "Pennsylvania": pennsylvania++;
                                           break;

                                           case "Rhode Island": rhode_island++;
                                           break;

                                           case "South Carolina": south_carolina++; //40
                                           break;

                                           case "South Dakota": south_dakota++;
                                           break;

                                           case "Tennessee": tennessee++;
                                           break;

                                           case "Texas": texas++;
                                           break;

                                           case "Utah": utah++;
                                           break;

                                           case "Vermont": vermont++;
                                           break;

                                           case "Virginia": virginia++;
                                           break;

                                           case "Washington": washington++;
                                           break;

                                           case "West Virginia": west_virginia++;
                                           break;

                                           case "Wisconson": wisconson++;
                                           break;

                                           case "Wyoming": wyoming++;
                                           break;

                                           case "District of Columbia": d_c++;
                                           break;       
                                } 
                            }
                        }
                    }
                }      
            }
        }
        
        for(String string: ObjectCache.stateList){
            switch(string){
                
                    case "Alabama": Report al = new Report(string, alabama);
                    stateCount.add(al);
                    break;

                    case "Alaska": Report ak = new Report(string, alaska);
                    stateCount.add(ak);
                    break;

                    case "Arizona": Report az = new Report(string, arizona);
                    stateCount.add(az);
                    break;

                    case "Arkansas": Report ar = new Report(string, arkansas);
                    stateCount.add(ar);
                    break;

                    case "California": Report ca = new Report(string, california);
                    stateCount.add(ca);
                    break;

                    case "Colorado": Report co = new Report(string, colorado);
                    stateCount.add(co);
                    break;

                    case "Connecticut": Report ct = new Report(string, connecticut);
                    stateCount.add(ct);
                    break;

                    case "Delaware": Report de = new Report(string, delaware);
                    stateCount.add(de);
                    break;

                    case "Florida": Report fl = new Report(string, florida);
                    stateCount.add(fl);
                    break;

                    case "Georgia": Report ga = new Report(string, georgia); //10
                    stateCount.add(ga);
                    break;

                    case "Hawaii": Report hi = new Report(string, hawaii);
                    stateCount.add(hi);
                    break;

                    case "Idaho": Report id = new Report(string, idaho);
                    stateCount.add(id);
                    break;

                    case "Illinois": Report il = new Report(string, illinois);
                    stateCount.add(il);
                    break;

                    case "Indiana": Report in = new Report(string, indiana);
                    stateCount.add(in);
                    break;

                    case "Iowa": Report io = new Report(string, iowa);
                    stateCount.add(io);
                    break;

                    case "Kansas": Report ks = new Report(string, kansas);
                    stateCount.add(ks);
                    break;

                    case "Kentucky": Report ky = new Report(string, kentucky);
                    stateCount.add(ky);
                    break;

                    case "Louisiana": Report la = new Report(string, louisiana);
                    stateCount.add(la);
                    break;

                    case "Maine": Report me = new Report(string, maine);
                    stateCount.add(me);
                    break;

                    case "Maryland": Report md = new Report(string, maryland); //20
                    stateCount.add(md);
                    break;

                    case "Massachusetts": Report ma = new Report(string, massachusetts);
                    stateCount.add(ma);
                    break;

                    case "Michigan": Report mi = new Report(string, michigan);
                    stateCount.add(mi);
                    break;

                    case "Minnesota": Report mn = new Report(string, minnesota);
                    stateCount.add(mn);
                    break;

                    case "Mississippi": Report ms = new Report(string, mississippi);
                    stateCount.add(ms);
                    break;

                    case "Missouri": Report mo = new Report(string, missouri);
                    stateCount.add(mo);
                    break;

                    case "Montana": Report mt = new Report(string, montana);
                    stateCount.add(mt);
                    break;

                    case "Nebraska": Report ne = new Report(string, nebraska);
                    stateCount.add(ne);
                    break;

                    case "Nevada": Report nv = new Report(string, nevada);
                    stateCount.add(nv);
                    break;

                    case "New Hampshire": Report nh = new Report(string, new_hampshire);
                    stateCount.add(nh);
                    break;

                    case "New Jersey": Report nj = new Report(string, new_jersey); //30
                    stateCount.add(nj);
                    break;

                    case "New Mexico": Report nm = new Report(string, new_mexico);
                    stateCount.add(nm);
                    break;

                    case "New York": Report ny = new Report(string, new_york);
                    stateCount.add(ny);
                    break;

                    case "North Carolina": Report nc = new Report(string, north_carolina);
                    stateCount.add(nc);
                    break;

                    case "North Dakota": Report nd = new Report(string, north_dakota);
                    stateCount.add(nd);
                    break;

                    case "Ohio": Report oh = new Report(string, ohio);
                    stateCount.add(oh);
                    break;

                    case "Oklahoma": Report ok = new Report(string, oklahoma);
                    stateCount.add(ok);
                    break;

                    case "Oregon": Report or = new Report(string, oregon);
                    stateCount.add(or);
                    break;

                    case "Pennsylvania": Report pa = new Report(string, pennsylvania);
                    stateCount.add(pa);
                    break;

                    case "Rhode Island": Report ri = new Report(string, rhode_island);
                    stateCount.add(ri);
                    break;

                    case "South Carolina": Report sc = new Report(string, south_carolina); //40
                    stateCount.add(sc);
                    break;

                    case "South Dakota": Report sd = new Report(string, south_dakota);
                    stateCount.add(sd);
                    break;

                    case "Tennessee": Report tn = new Report(string, tennessee);
                    stateCount.add(tn);
                    break;

                    case "Texas": Report tx = new Report(string, texas);
                    stateCount.add(tx);
                    break;

                    case "Utah": Report ut = new Report(string, utah);
                    stateCount.add(ut);
                    break;

                    case "Vermont": Report vt = new Report(string, vermont);
                    stateCount.add(vt);
                    break;

                    case "Virginia": Report va = new Report(string, virginia);
                    stateCount.add(va);
                    break;

                    case "Washington": Report wa = new Report(string, washington);
                    stateCount.add(wa);
                    break;

                    case "West Virginia": Report wv = new Report(string, west_virginia);
                    stateCount.add(wv);
                    break;

                    case "Wisconson": Report wi = new Report(string, wisconson);
                    stateCount.add(wi);
                    break;

                    case "Wyoming": Report wy = new Report(string, wyoming);
                    stateCount.add(wy);
                    break;

                    case "District of Columbia": Report dc = new Report(string, d_c);
                    stateCount.add(dc);
                    break;       
            }
        }
        
        return stateCount;
    }
        

    @FXML
    void onCancelButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        SceneChange("/view/Main.fxml");
    } 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        numberTable.setItems(getTypeCount());
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        number_numberColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        counselorTable.setItems(getCounselorCount());
        counselorColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        counselor_numberColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        
        stateTable.setItems(getStateCount());
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        state_numberColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
    }    
    
}
