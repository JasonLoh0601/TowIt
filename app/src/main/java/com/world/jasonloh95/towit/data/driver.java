package com.world.jasonloh95.towit.data;

/**
 * Created by JasonLoh95 on 27/2/2018.
 */

public class driver {
    String email;
    String name;
    String contactNumber;

    public  driver (){

    }

    public driver (String name, String email, String contactNumber ){
        this.name = name;
        this.email =email;
        this.contactNumber = contactNumber;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setContactNumber(String contactNumber){
        this.contactNumber = contactNumber;
    }
}
