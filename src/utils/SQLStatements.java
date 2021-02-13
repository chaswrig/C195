/*
 * Prepared statements to use throughout the program.
 * Tutorial found here: https://youtu.be/gU3DLOsw0Eg
 * Selects use .executeQuery
 * Insert/Update/Delete uses .executeUpdate. ExecuteUpdate returns the number 
 * of rows changed.
 * https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
 */
package utils;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.APTtype;
import model.Address;
import model.Appointment;
import model.Counselor;
import model.Patient;

/**
 *
 * @author chase
 */
public class SQLStatements {
    
    //Variables to be used throughout class and rest of program
    static Connection conn = database.getConnection(); //Will be used throughout class
    static ResultSet rs = null;
    static PreparedStatement preparedStatement = null;
    
    public static ObservableList<Address> getAddress() throws SQLException{
        ObservableList<Address> ol = FXCollections.observableArrayList();
        preparedStatement = conn.prepareStatement("SELECT * FROM address");
        rs = preparedStatement.executeQuery();
        while(rs.next()){
            Address address = new Address();
            address.setAddress_id(rs.getInt("address_id"));
            address.setAddressline_1(rs.getString("addressline_1"));
            address.setAddressline_2(rs.getString("addressline_2"));
            address.setCity(rs.getString("city"));
            address.setState(rs.getString("state"));
            address.setPostal_code(rs.getString("postal_code"));
            address.setPhone(rs.getString("phone"));
            address.setCreated_at(ObjectCache.convertTimestamp(rs.getTimestamp("created_at")));
            address.setCreated_by(rs.getString("created_by"));
            address.setUpdated_at(ObjectCache.convertTimestamp(rs.getTimestamp("updated_at")));
            address.setUpdated_by(rs.getString("updated_by"));
            ol.add(address);  
        }
        return ol;
    }
    
    public static int getNextAddressPK() throws SQLException{
        int pk = -1;
        preparedStatement = conn.prepareStatement("SELECT MAX(address_id) + 1 FROM address");
        rs = preparedStatement.executeQuery();
        if(rs.next())
            pk = rs.getInt(1);
        return pk;
    }
    
    public static void insertAddress(int address_id, String addressline_1, 
            String addressline_2, String city, String state, String postal_code,
            String phone, ZonedDateTime created_at, String created_by, ZonedDateTime updated_at, String updated_by) throws SQLException{
        preparedStatement = conn.prepareStatement("INSERT INTO address(address_id, addressline_1, addressline_2, city, state, postal_code, phone, created_at, created_by, updated_at, updated_by) "
                                                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, address_id);
        preparedStatement.setString(2, addressline_1);
        preparedStatement.setString(3, addressline_2);
        preparedStatement.setString(4, city);
        preparedStatement.setString(5, state);
        preparedStatement.setString(6, postal_code);
        preparedStatement.setString(7, phone);
        preparedStatement.setTimestamp(8, ObjectCache.convertZDT(created_at));
        preparedStatement.setString(9, created_by);
        preparedStatement.setTimestamp(10, ObjectCache.convertZDT(updated_at));
        preparedStatement.setString(11, updated_by);
        preparedStatement.executeUpdate();
    }
    
    public static void updateAddress(int address_id, String addressline_1, String addressline_2, 
            String city, String state, String postal_code, String phone, 
            ZonedDateTime updated_at, String updated_by) throws SQLException{
        preparedStatement = conn.prepareStatement("UPDATE address "
                + "SET addressline_1 = ?"
                + "SET addressline_2 = ?"
                + "SET city = ?" //3
                + "SET state = ?" //4
                + "SET postal_code = ?" //5
                + "SET phone = ?" //6
                + "SET updated_at = ?" //7
                + "SET updated_by = ?" //8
                + "WHERE address_id = ?"); //9
        preparedStatement.setString(1, addressline_1);
        preparedStatement.setString(2, addressline_2);
        preparedStatement.setString(3, city);
        preparedStatement.setString(4, state);
        preparedStatement.setString(5, postal_code);
        preparedStatement.setString(6, phone);
        preparedStatement.setTimestamp(7, ObjectCache.convertZDT(updated_at));
        preparedStatement.setString(8, updated_by);
        preparedStatement.setInt(9, address_id);
        preparedStatement.executeUpdate();
    }  
    
    public static ObservableList<Patient> getPatient() throws SQLException{
        ObservableList<Patient> ol = FXCollections.observableArrayList();
        preparedStatement = conn.prepareStatement("SELECT * FROM patient");
        rs = preparedStatement.executeQuery();
        while(rs.next()){
            Patient patient = new Patient();
            patient.setPt_id(rs.getInt("pt_id"));
            patient.setPt_name(rs.getString("pt_name"));
            patient.setAddress_id(rs.getInt("address_id"));
            patient.setINS_PR(rs.getString("INS_PR"));
            patient.setCreated_at(ObjectCache.convertTimestamp(rs.getTimestamp("created_at")));
            patient.setCreated_by(rs.getString("created_by"));
            patient.setUpdated_at(ObjectCache.convertTimestamp(rs.getTimestamp("updated_at")));
            patient.setUpdated_by(rs.getString("updated_by"));
            ol.add(patient);
        }     
        return ol;
    }
    
    public static int getNextPatientPK() throws SQLException{
        int pk = -1;
        preparedStatement = conn.prepareStatement("SELECT MAX(pt_id) + 1 FROM patient");
        rs = preparedStatement.executeQuery();
        if(rs.next())
            pk = rs.getInt(1);
        return pk;
    }
    
    public static void insertPatient(int pt_id, String pt_name, int address_id, String INS_PR,
            ZonedDateTime created_at, String created_by, ZonedDateTime updated_at, 
            String updated_by) throws SQLException{
        preparedStatement = conn.prepareStatement("INSERT INTO patient(pt_id, pt_name, address_id, INS_PR, created_at, created_by, updated_at, updated_by, address_address_id) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
                preparedStatement.setInt(1, pt_id);
                preparedStatement.setString(2, pt_name);
                preparedStatement.setInt(3, address_id);
                preparedStatement.setString(4, INS_PR);
                preparedStatement.setTimestamp(5, ObjectCache.convertZDT(created_at));
                preparedStatement.setString(6, created_by);
                preparedStatement.setTimestamp(7, ObjectCache.convertZDT(updated_at));
                preparedStatement.setString(8, updated_by);
                preparedStatement.setInt(9, address_id);
                preparedStatement.executeUpdate();
    }
    
    public static void deletePatient(int pt_id) throws SQLException{
        preparedStatement = conn.prepareStatement("DELETE FROM patient WHERE pt_id = ?");
        preparedStatement.setInt(1, pt_id);
        preparedStatement.executeUpdate();
    }
    
    public static void updatePatient(int pt_id, String pt_name, int address_id, String INS_PR,
            ZonedDateTime updated_at, String updated_by) throws SQLException{
        preparedStatement = conn.prepareStatement("UPDATE patient "
                + "SET pt_name = ?, "
                + "address_id = ?, "
                + "INS_PR = ?, "
                + "updated_at = ?,"
                + "updated_by = ?"
                + "WHERE pt_id = ?");
        preparedStatement.setString(1, pt_name);
        preparedStatement.setInt(2, address_id);
        preparedStatement.setString(3, INS_PR);
        preparedStatement.setTimestamp(4, ObjectCache.convertZDT(updated_at));
        preparedStatement.setString(5, updated_by);
        preparedStatement.setInt(6, pt_id);
        preparedStatement.executeUpdate();
    }
    
    //Insert/Update not needed as the types are set.
    public static ObservableList<APTtype> getAPTtype() throws SQLException{
        ObservableList<APTtype> ol = FXCollections.observableArrayList();
        preparedStatement = conn.prepareStatement("SELECT * FROM APTtype");
        rs = preparedStatement.executeQuery();
        while(rs.next()){
            APTtype apttype = new APTtype();
            apttype.setAPTtype_id(rs.getInt("APTtype_id"));
            apttype.setDescription(rs.getString("description"));
            apttype.setCreated_at(ObjectCache.convertTimestamp(rs.getTimestamp("created_at")));
            apttype.setCreated_by(rs.getString("created_by"));
            apttype.setUpdated_at(ObjectCache.convertTimestamp(rs.getTimestamp("updated_at")));
            apttype.setUpdated_by(rs.getString("updated_by"));
            ol.add(apttype);
        }
        return ol;
    }
    
    //Insert/update not needes as there is only one counselor, the admin.
    public static ObservableList<Counselor> getCounselor() throws SQLException{
        ObservableList<Counselor> ol = FXCollections.observableArrayList();
        preparedStatement = conn.prepareStatement("SELECT * FROM counselor");
        rs = preparedStatement.executeQuery();
        while(rs.next()){
            Counselor counselor = new Counselor();
            counselor.setC_id(rs.getInt("c_id"));
            counselor.setC_name(rs.getString("c_name"));
            counselor.setC_password(rs.getString("c_password"));
            counselor.setC_pin(rs.getString("c_pin"));
            counselor.setCreated_at(ObjectCache.convertTimestamp(rs.getTimestamp("created_at")));
            counselor.setCreated_by(rs.getString("created_by"));
            counselor.setUpdated_at(ObjectCache.convertTimestamp(rs.getTimestamp("updated_at")));
            counselor.setUpdated_by(rs.getString("updated_by"));
            ol.add(counselor);
        }
        return ol;
    }
    
    public static ObservableList<Appointment> getAppointment() throws SQLException {
        ObservableList<Appointment> ol = FXCollections.observableArrayList();
        preparedStatement = conn.prepareStatement("SELECT * FROM appointment");
        rs = preparedStatement.executeQuery();
        while(rs.next()){
            Appointment appointment = new Appointment();
            appointment.setApt_id(rs.getInt("apt_id"));
            appointment.setPt_id(rs.getInt("pt_id"));
            appointment.setCr_id(rs.getInt("cr_id"));
            appointment.setApt_type_id(rs.getInt("apt_type_id"));
            appointment.setNotes(rs.getString("notes"));
            appointment.setStart_datetime(ObjectCache.convertTimestamp(rs.getTimestamp("start_datetime")));
            appointment.setCreated_at(ObjectCache.convertTimestamp(rs.getTimestamp("created_at")));
            appointment.setCreated_by(rs.getString("created_by"));
            appointment.setUpdated_at(ObjectCache.convertTimestamp(rs.getTimestamp("updated_at")));
            appointment.setUpdated_by(rs.getString("updated_by"));
            ol.add(appointment);
        }
           
        return ol;
    }
    
    public static int getNextAppointmentPK() throws SQLException{
        int pk = -1;
        preparedStatement = conn.prepareStatement("SELECT MAX(apt_id) + 1 FROM appointment");
        rs = preparedStatement.executeQuery();
        if(rs.next())
            pk = rs.getInt(1);
        return pk;
    }
    
    public static void insertAppointment(int apt_id, int pt_id, int cr_id, int apt_type_id, 
            String notes, ZonedDateTime start_datetime, ZonedDateTime created_at,
            String created_by, ZonedDateTime updated_at, String updated_by, int patient_pt_id, int counselor_c_id, int APTtype_APTtype_id) throws SQLException{
        preparedStatement = conn.prepareStatement("INSERT INTO appointment(apt_id, pt_id, cr_id, apt_type_id, notes, start_datetime, created_at, created_by, updated_at, updated_by, patient_pt_id, counselor_c_id, APTtype_APTtype_id) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, apt_id);
        preparedStatement.setInt(2, pt_id);
        preparedStatement.setInt(3, cr_id);
        preparedStatement.setInt(4, apt_type_id);
        preparedStatement.setString(5, notes);
        preparedStatement.setTimestamp(6, ObjectCache.convertZDT(start_datetime));
        preparedStatement.setTimestamp(7, ObjectCache.convertZDT(created_at));
        preparedStatement.setString(8, created_by);
        preparedStatement.setTimestamp(9, ObjectCache.convertZDT(updated_at));
        preparedStatement.setString(10, updated_by);
        preparedStatement.setInt(11, patient_pt_id);
        preparedStatement.setInt(12, counselor_c_id);
        preparedStatement.setInt(13, APTtype_APTtype_id);
        preparedStatement.executeUpdate();
    }
    
    public static void deleteAppointment(int apt_id) throws SQLException{
        preparedStatement = conn.prepareStatement("DELETE FROM appointment WHERE apt_id = ?");
        preparedStatement.setInt(1, apt_id);
        preparedStatement.executeUpdate();
    }
      
    public static void updateAppointment(int apt_id, int pt_id, int cr_id, int apt_type_id, 
            String notes, ZonedDateTime start_datetime, ZonedDateTime updated_at, String updated_by, int patient_pt_id, int counselor_c_id, int APTtype_APTtype_id) throws SQLException{
        preparedStatement = conn.prepareStatement("UPDATE appointment "
                + "SET pt_id = ?, " //1
                + "cr_id = ?, " //2
                + "apt_type_id = ?, " //3
                + "notes = ?, " //4
                + "start_datetime = ?, " //5
                + "updated_at = ?, " //6
                + "updated_by = ?, " //7
                + "patient_pt_id = ?, " //8
                + "counselor_c_id = ?, " //9
                + "APTtype_APTtype_id = ? " //10
                + "WHERE apt_id = ?"); //11
        preparedStatement.setInt(1, pt_id);
        preparedStatement.setInt(2, cr_id);
        preparedStatement.setInt(3, apt_type_id);
        preparedStatement.setString(4, notes);
        preparedStatement.setTimestamp(5, ObjectCache.convertZDT(start_datetime));
        preparedStatement.setTimestamp(6, ObjectCache.convertZDT(updated_at));
        preparedStatement.setString(7, updated_by);
        preparedStatement.setInt(8, patient_pt_id);
        preparedStatement.setInt(9, counselor_c_id);
        preparedStatement.setInt(10, APTtype_APTtype_id);
        preparedStatement.setInt(11, apt_id);
        preparedStatement.executeUpdate();
    }
}
