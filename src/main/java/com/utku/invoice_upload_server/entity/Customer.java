package com.utku.invoice_upload_server.entity;

public class Customer {
    private int id;
    private String nameSurname;
    private String ssNumber;

    public Customer() {
    }

    public Customer(int id, String nameSurname, String ssNumber) {
        this.id = id;
        this.nameSurname = nameSurname;
        this.ssNumber = ssNumber;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNameSurname(){
        return this.nameSurname;
    }

    public void setNameSurname(String nameSurname){
        this.nameSurname = nameSurname;
    }

    public String getSsNumber(){
        return this.ssNumber;
    }

    public void setSsNumber(String ssNumber){
        this.ssNumber = ssNumber;
    }
}
