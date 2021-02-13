/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import utils.ObjectCache;
import static utils.ObjectCache.patientList;

/**
 *
 * @author chase
 */
public class Appointment {
    
    private int apt_id = -1; //Set to -1 as first item in database will be 0.
    private int pt_id = -1; //Set to -1 as first item in database will be 0.
    private int cr_id = -1; //Set to -1 as first item in database will be 0.
    private int apt_type_id = -1; //Set to -1 as first item in database will be 0.
    private String notes = null;
    private ZonedDateTime start_datetime = null;
    private ZonedDateTime created_at = null;
    private String created_by = null;
    private ZonedDateTime updated_at = null;
    private String updated_by = null;

    public Appointment() {
    }
    
    public Appointment(int apt_id, int pt_id, int cr_id, int apt_type_id, 
            String notes, ZonedDateTime start_datetime, ZonedDateTime created_at,
            String created_by, ZonedDateTime updated_at, String updated_by){
        this.apt_id = apt_id;
        this.pt_id = pt_id;
        this.cr_id = cr_id;
        this.apt_type_id = apt_type_id;
        this.notes = notes;
        this.start_datetime = start_datetime;
        this.created_at = created_at;
        this.created_by = created_by;
        this.updated_at = updated_at;
        this.updated_by = updated_by;
    }

    public int getApt_id() {
        return apt_id;
    }

    public void setApt_id(int apt_id) {
        this.apt_id = apt_id;
    }

    public int getPt_id() {
        return pt_id;
    }

    public void setPt_id(int pt_id) {
        this.pt_id = pt_id;
    }
    
    public String getDisplayPatient(){
        String name = null;
        int id = -1;
        for (Patient p : patientList){
           id = p.getPt_id();
           
           if(id == pt_id)
               name = p.getPt_name();
        }
        return name;
    }

    public int getCr_id() {
        return cr_id;
    }

    public void setCr_id(int cr_id) {
        this.cr_id = cr_id;
    }
    
    public String getDisplayCounselor(){
        String name = null;
        int id = -1;
        for(Counselor c : ObjectCache.counselorList){
            id = c.getC_id();
            
            if(id == cr_id)
                name = c.getC_name();
        }
        return name;
    }

    public int getApt_type_id() {
        return apt_type_id;
    }

    public void setApt_type_id(int apt_type_id) {
        this.apt_type_id = apt_type_id;
    }
    
    public String getDisplayType(){
        String type = null;
        int id = -1;
        
        for (APTtype a : ObjectCache.APTtypeList) {
            id = a.getAPTtype_id();
            if(id == apt_type_id)
                type = a.getDescription();
        }
        return type;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ZonedDateTime getStart_datetime() {
        return start_datetime;
    }

    public void setStart_datetime(ZonedDateTime start_datetime) {
        this.start_datetime = start_datetime;
    }

    public String getDisplayTime(){
        return start_datetime.format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a"));
    }
    public ZonedDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(ZonedDateTime created_at) {
        this.created_at = created_at;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public ZonedDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(ZonedDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    @Override
    public String toString() {
        String patient_name = null;
        for(Patient p : ObjectCache.patientList)
            if(p.getPt_id() == pt_id)
                patient_name = p.getPt_name();
        return "Patient: " + patient_name + "\tStart Time: " + start_datetime.format(DateTimeFormatter.ofPattern("hh:mm a"));
    }
}
