/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.ZonedDateTime;

/**
 *
 * @author chase
 */
public class Counselor {
    
    private int c_id = -1; //Set to -1 as first item in database will be 0.
    private String c_name = null;
    private String c_password = null;
    private String c_pin = null;
    private ZonedDateTime created_at = null;
    private String created_by = null;
    private ZonedDateTime updated_at = null;
    private String updated_by = null;

    public Counselor() {
        //Override default constructor
    }
    
    public Counselor(int c_id, String c_name, String c_password, String c_pin,
            ZonedDateTime created_at, String created_by, ZonedDateTime updated_at, String updated_by){
        this.c_id = c_id;
        this.c_name = c_name;
        this.c_password = c_password;
        this.c_pin = c_pin;
        this.created_at = created_at;
        this.created_by = created_by;
        this.updated_at = updated_at;
        this.updated_by = updated_by;        
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_password() {
        return c_password;
    }

    public void setC_password(String c_password) {
        this.c_password = c_password;
    }

    public String getC_pin() {
        return c_pin;
    }

    public void setC_pin(String c_pin) {
        this.c_pin = c_pin;
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
        return c_name;
    }
    
    
}
