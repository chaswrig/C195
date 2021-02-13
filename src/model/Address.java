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
public class Address {
    
    private int address_id = -1; //Set to negative 1 because the first item in the database will be 0.
    private String addressline_1 = null;
    private String addressline_2 = null;
    private String city = null;
    private String state = null;
    private String postal_code = null; //Database shows String, not int
    private String phone = null; //Database using string, not int/double
    private ZonedDateTime created_at = null;
    private String created_by = null;
    private ZonedDateTime updated_at = null;
    private String updated_by = null;

    public Address() {
        //Overrides default constructor
    }
    
    public Address(int address_id, String addressline_1, String addressline_2, 
            String city, String state, String postal_code, String phone, 
            ZonedDateTime created_at, String created_by, ZonedDateTime updated_at, String updated_by){
        this.address_id = address_id;
        this.addressline_1 = addressline_1;
        this.addressline_2 = addressline_2;
        this.city = city;
        this.state = state;
        this.postal_code = postal_code;
        this.phone = phone;
        this.created_at = created_at;
        this.created_by = created_by;
        this.updated_at = updated_at;
        this.updated_by = updated_by;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }
    
    public String getAddressline_1() {
        return addressline_1;
    }

    public void setAddressline_1(String addressline_1) {
        this.addressline_1 = addressline_1;
    }

    public String getAddressline_2() {
        return addressline_2;
    }

    public void setAddressline_2(String addressline_2) {
        this.addressline_2 = addressline_2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        return "Address{" + "address_id=" + address_id + ", addressline_1=" + addressline_1 + ", addressline_2=" + addressline_2 + ", city=" + city + ", state=" + state + ", postal_code=" + postal_code + ", phone=" + phone + ", created_at=" + created_at + ", created_by=" + created_by + ", updated_at=" + updated_at + ", updated_by=" + updated_by + '}';
    }
}
