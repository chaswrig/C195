/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.ZonedDateTime;
import utils.ObjectCache;

/**
 *
 * @author chase
 */
public class Patient {
    
    private int pt_id = -1; //Set to -1 as first item in database will be 0.
    private String pt_name = null;
    private int address_id = -1; //Set to -1 as first item in database will be 0.
    private String INS_PR = null;
    private ZonedDateTime created_at = null;
    private String created_by = null;
    private ZonedDateTime updated_at = null;
    private String updated_by = null;

    public Patient() {
    }
    
    public Patient(int pt_id, String pt_name, int address_id, String INS_PR,
            ZonedDateTime created_at, String created_by, ZonedDateTime updated_at, 
            String updated_by){
        this.pt_id = pt_id;
        this.pt_name = pt_name;
        this.address_id = address_id;
        this.INS_PR = INS_PR;
        this.created_at = created_at;
        this.created_by = created_by;
        this.updated_at = updated_at;
        this.updated_by = updated_by;
    }

    public int getPt_id() {
        return pt_id;
    }

    public void setPt_id(int pt_id) {
        this.pt_id = pt_id;
    }

    public String getPt_name() {
        return pt_name;
    }

    public void setPt_name(String pt_name) {
        this.pt_name = pt_name;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }
    
    public String getDisplayAddress(){
        String address = null;
        int id = -1;
        for(Address a: ObjectCache.addressList){
            id = a.getAddress_id();
            if(id == address_id)
                address = a.getAddressline_1() + ", " + a.getAddressline_2();
        }
        return address;
    }

    public String getINS_PR() {
        return INS_PR;
    }

    public void setINS_PR(String INS_PR) {
        this.INS_PR = INS_PR;
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
        return pt_name;
    }
}
