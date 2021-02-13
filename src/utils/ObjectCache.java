/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
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
public class ObjectCache {
    
    public static ObservableList<APTtype> APTtypeList = FXCollections.observableArrayList();
    public static ObservableList<Address> addressList = FXCollections.observableArrayList();
    
    public static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    public static ObservableList<Appointment> fourHourList = FXCollections.observableArrayList();
    public static ObservableList<Appointment> weeklyAppointmentList = FXCollections.observableArrayList();
    public static ObservableList<Appointment> biweeklyAppointmentList = FXCollections.observableArrayList();
    public static ObservableList<Appointment> monthlyAppointmentList = FXCollections.observableArrayList();
    public static ObservableList<Appointment> yearlyAppointmentList = FXCollections.observableArrayList();
    
    public static ObservableList<Counselor> counselorList = FXCollections.observableArrayList();
    public static ObservableList<Patient> patientList = FXCollections.observableArrayList();
    
    public static ObservableList<String> timeList = FXCollections.observableArrayList();
    public static ObservableList<LocalDate> holidayList = FXCollections.observableArrayList();
    public static ObservableList<String> stateList = FXCollections.observableArrayList();
    
    public static Counselor currentUser = new Counselor();
    
    public static void clearCache(){
        APTtypeList.clear();
        addressList.clear();
        appointmentList.clear();
        weeklyAppointmentList.clear();
        biweeklyAppointmentList.clear();
        monthlyAppointmentList.clear();
        counselorList.clear();
        patientList.clear();
    }
    
    public static void getWeeklyAppointments(){
        weeklyAppointmentList.clear();
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime next_week = now.plusWeeks(1); //Filters the first list into a second list
        appointmentList.stream().filter((Appointment a) -> a.getStart_datetime().isBefore(next_week)).forEachOrdered((Appointment a) -> {
            if(a.getStart_datetime().isAfter(now)){
                weeklyAppointmentList.add(a);
            }
        });
    }
    
    public static void getBiweeklyAppointments(){
        biweeklyAppointmentList.clear();
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime two_weeks = now.plusWeeks(2); //Filters the first list into a second list
        appointmentList.stream().filter((a) -> (a.getStart_datetime().isBefore(two_weeks))).forEachOrdered((a) -> {
            //Excludes past appointments
            if(a.getStart_datetime().isAfter(now)){
                biweeklyAppointmentList.add(a);
            }
        });
    }
        
    public static void getMonthlyAppointments(){
        monthlyAppointmentList.clear();
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime one_month = now.plusMonths(1); //Filters the first list into a second list
        appointmentList.stream().filter((a) -> (a.getStart_datetime().isBefore(one_month))).forEachOrdered((a) -> {
            if(a.getStart_datetime().isAfter(now)){
                monthlyAppointmentList.add(a);
            }
        });
    }

    //Will convert Timestamp to ZDT for better Java use
    public static ZonedDateTime convertTimestamp(Timestamp timestamp){
        ZonedDateTime zdt = timestamp.toLocalDateTime().atZone(ZoneId.of("UTC"));
        ZonedDateTime sys = zdt.withZoneSameInstant(ZoneId.systemDefault());
        return sys;
    }
    
    //Converts ZDT to Timestamp for database storage
    public static Timestamp convertZDT(ZonedDateTime zdt){
        ZonedDateTime utc = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        Timestamp t = Timestamp.valueOf(utc.toLocalDateTime());
        return t;
    }
    
    //Generates all the times available for appointments
    public static void getAppointmentTimes(){
        String hour = null;
        String minute = null;
        int h = 0;
        
        LocalTime st = LocalTime.of(9, 0);
        LocalDateTime sdt = LocalDateTime.of(LocalDate.now(), st);
        ZonedDateTime e_zdt = sdt.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime s_zdt = e_zdt.withZoneSameInstant(ZoneId.systemDefault());
        
        LocalTime rst = s_zdt.toLocalDateTime().toLocalTime();
        
        LocalTime et = LocalTime.of(21, 0);
        LocalDateTime edt = LocalDateTime.of(LocalDate.now(), et);
        ZonedDateTime e2_zdt = edt.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime s2_zdt = e2_zdt.withZoneSameInstant(ZoneId.systemDefault());
        
        LocalTime est = s2_zdt.toLocalDateTime().toLocalTime();
        
        //for(h = 9; h < 12; h++){
        for(h = rst.getHour(); h < 12; h++){
            for(int m = 0; m< 60; m+=15){
                
                if(h<10){
                    if (m < 10){
                    hour = "0" + Integer.toString(h);
                    minute = "0" + Integer.toString(m);
                    String time = hour + ":" + minute + " AM";
                    timeList.add(time);
                    } else{
                    hour = "0" + Integer.toString(h);
                    minute = Integer.toString(m);
                    String time = hour + ":" + minute + " AM";
                    timeList.add(time);
                    }
                } else{               
                    if (m < 10){
                    hour = Integer.toString(h);
                    minute = "0" + Integer.toString(m);
                    String time = hour + ":" + minute + " AM";
                    timeList.add(time);
                    } else{
                        hour = Integer.toString(h);
                        minute = Integer.toString(m);
                        String time = hour + ":" + minute + " AM";
                        timeList.add(time);
                    }
                }
            }
        }
        
        h = 12;
        
        for(int m = 0; m< 60; m+=15){
            if (m < 10){
                hour = Integer.toString(h);
                minute = "0" + Integer.toString(m);
                String time = hour + ":" + minute + " PM";
                timeList.add(time);
            } else{
                hour = Integer.toString(h);
                minute = Integer.toString(m);
                String time = hour + ":" + minute + " PM";
                timeList.add(time);
            }
        }
        
        for(h = 1; h < est.getHour() - 12; h++){
            for(int m = 0; m< 60; m+=15){
                
                if(h<10){
                    if (m < 10){
                    hour = "0" + Integer.toString(h);
                    minute = "0" + Integer.toString(m);
                    String time = hour + ":" + minute + " PM";
                    timeList.add(time);
                    } else{
                    hour = "0" + Integer.toString(h);
                    minute = Integer.toString(m);
                    String time = hour + ":" + minute + " PM";
                    timeList.add(time);
                    }
                } else{
                    if (m < 10){
                        hour = Integer.toString(h);
                        minute = "0" + Integer.toString(m);
                        String time = hour + ":" + minute + " PM";
                        timeList.add(time);
                    } else{
                        hour = Integer.toString(h);
                        minute = Integer.toString(m);
                        String time = hour + ":" + minute + "PM";
                        timeList.add(time);
                    }
                }
            }
        }
    }
    
    public static void getProhibitedDays(){
        //gets all of the days where an appointment cannot be scheduled.
        holidayList.clear();
        //Saturdays
        LocalDate firstSaturday = LocalDate.of(2020, Month.JANUARY, 4);
        LocalDate nextSaturday;
        holidayList.add(firstSaturday);
        for(int i = 1; i < 208; i++){
            nextSaturday = firstSaturday.plusWeeks(i);
            holidayList.add(nextSaturday);
        }
        
        //Sundays
        LocalDate firstSunday = LocalDate.of(2020, Month.JANUARY, 5);
        LocalDate nextSunday;
        holidayList.add(firstSunday);
        for(int i = 1; i < 208; i++){
            nextSunday = firstSunday.plusWeeks(i);
            holidayList.add(nextSunday);
        }
        
        //Martin Luther King, Jr. Day (3rd Monday of January)
        LocalDate mlkDay2020 = LocalDate.of(2020, Month.JANUARY, 20);
        LocalDate mlkDay2021 = LocalDate.of(2021, Month.JANUARY, 18);
        LocalDate mlkDay2022 = LocalDate.of(2022, Month.JANUARY, 17);
        LocalDate mlkDay2023 = LocalDate.of(2023, Month.JANUARY, 16);
        holidayList.add(mlkDay2020);
        holidayList.add(mlkDay2021);
        holidayList.add(mlkDay2022);
        holidayList.add(mlkDay2023);
                
        //Memorial Day (last Monday of May)
        LocalDate memorialDay2020 = LocalDate.of(2020, Month.MAY, 25);
        LocalDate memorialDay2021 = LocalDate.of(2021, Month.MAY, 31);
        LocalDate memorialDay2022 = LocalDate.of(2022, Month.MAY, 30);
        LocalDate memorialDay2023 = LocalDate.of(2023, Month.MAY, 29);
        holidayList.add(memorialDay2020);
        holidayList.add(memorialDay2021);
        holidayList.add(memorialDay2022);
        holidayList.add(memorialDay2023);
        
        //Veterans Day (November 11th)
        LocalDate veteransDay2020 = LocalDate.of(2020, Month.NOVEMBER, 11);
        LocalDate veteransDay2021 = veteransDay2020.plusYears(1);
        LocalDate veteransDay2022 = veteransDay2020.plusYears(2);
        LocalDate veteransDay2023 = veteransDay2020.plusYears(3);
        holidayList.add(veteransDay2020);
        holidayList.add(veteransDay2021);
        holidayList.add(veteransDay2022);
        holidayList.add(veteransDay2023);
        
        //Thanksgiving and the day after (4th Thursday of November and the day after)
        LocalDate thanksgiving2020 = LocalDate.of(2020, Month.NOVEMBER, 26);
        LocalDate fridayAfter2020 = thanksgiving2020.plusDays(1);
        LocalDate thanksgiving2021 = LocalDate.of(2021, Month.NOVEMBER, 25);
        LocalDate fridayAfter2021 = thanksgiving2021.plusDays(1);
        LocalDate thanksgiving2022 = LocalDate.of(2022, Month.NOVEMBER, 24);
        LocalDate fridayAfter2022 = thanksgiving2022.plusDays(0);
        LocalDate thanksgiving2023 = LocalDate.of(2023, Month.NOVEMBER, 23);
        LocalDate fridayAfter2023 = thanksgiving2023.plusDays(1);
        holidayList.add(thanksgiving2020);
        holidayList.add(thanksgiving2021);
        holidayList.add(thanksgiving2022);
        holidayList.add(thanksgiving2023);
        holidayList.add(fridayAfter2020);
        holidayList.add(fridayAfter2021);
        holidayList.add(fridayAfter2022);
        holidayList.add(fridayAfter2023);
    }
    
    public static void getStateList(){
        stateList.clear();
        stateList.add("Alabama");
        stateList.add("Alaska");
        stateList.add("Arizona");
        stateList.add("Arkansas");
        stateList.add("California");
        stateList.add("Colorado");
        stateList.add("Connecticut");
        stateList.add("Delaware");
        stateList.add("Florida");
        stateList.add("Georgia"); //10
        stateList.add("Hawaii");
        stateList.add("Idaho");
        stateList.add("Illinois");
        stateList.add("Indiana");
        stateList.add("Iowa");
        stateList.add("Kansas");
        stateList.add("Kentucky");
        stateList.add("Louisiana");
        stateList.add("Maine");
        stateList.add("Maryland"); //20
        stateList.add("Massachusetts");
        stateList.add("Michigan");
        stateList.add("Minnesota");
        stateList.add("Mississippi");
        stateList.add("Missouri"); //25
        stateList.add("Montana");
        stateList.add("Nebraska");
        stateList.add("Nevada");
        stateList.add("New Hampshire");
        stateList.add("New Jersey"); //30
        stateList.add("New Mexico");
        stateList.add("New York");
        stateList.add("North Carolina");
        stateList.add("North Dakota");
        stateList.add("Ohio");
        stateList.add("Oklahoma");
        stateList.add("Oregon");
        stateList.add("Pennsylvania");
        stateList.add("Rhode Island");
        stateList.add("South Carolina"); //40
        stateList.add("South Dakota");
        stateList.add("Tennessee");
        stateList.add("Texas");
        stateList.add("Utah");
        stateList.add("Vermont");
        stateList.add("Virginia");
        stateList.add("Washington");
        stateList.add("West Virginia");
        stateList.add("Wisconson");
        stateList.add("Wyoming"); //50
        stateList.add("District of Columbia");
    }

    public static ObservableList<Appointment> getFourHourList() {
        for(Appointment appointment : appointmentList){
            if(appointment.getCr_id() == currentUser.getC_id() && (appointment.getStart_datetime().isAfter(ZonedDateTime.now().minusSeconds(1))) && (appointment.getStart_datetime().isBefore(ZonedDateTime.now().plusHours(4)))){
                //add to four hour list
                fourHourList.add(appointment);
                System.out.println(fourHourList);
            }
        }
        return fourHourList;
    }
    
    public static void removePastAppointments(ObservableList<Appointment> ol){
        for(Appointment appointment : ol){
            if(appointment.getStart_datetime().isBefore(ZonedDateTime.now()))
                ol.remove(appointment);
        }
    }
}
