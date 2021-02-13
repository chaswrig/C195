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
public class APTtype {
    
    private int APTtype_id = -1; //Set to -1 as first item in database will be 0.
    private String description = null;
    private ZonedDateTime created_at = null; //Needed?
    private String created_by = null;
    private ZonedDateTime updated_at = null; //Needed?
    private String updated_by = null;

    public APTtype() {
    }
    
    public APTtype(int APTtype_id, String description, ZonedDateTime created_at,
            String created_by, ZonedDateTime updated_at, String updated_by){
        this.APTtype_id = APTtype_id;
        this.description = description;
        this.created_at = created_at;
        this.created_by = created_by;
        this.updated_at = updated_at;
        this.updated_by = updated_by;
    }

    public int getAPTtype_id() {
        return APTtype_id;
    }

    public void setAPTtype_id(int APTtype_id) {
        this.APTtype_id = APTtype_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return description;
    }
}