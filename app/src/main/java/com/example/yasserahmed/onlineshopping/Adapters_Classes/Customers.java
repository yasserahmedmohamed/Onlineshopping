package com.example.yasserahmed.onlineshopping.Adapters_Classes;

/**
 * Created by yasser ahmed on 12/5/2017.
 */

public class Customers {

    //CustID integer ,Custname ,username text NOT NULL,password text NOT NULL ,gender text,costImage text,birthdate text ,job text

    public int CustID;
    public String Custname;
    public String username;
    public String password;
    public String gender;
    public String costImage;
    public String birthdate;
    public String job;

    public Customers(int custID, String custname, String username, String password, String gender, String costImage, String birthdate, String job) {
        CustID = custID;
        Custname = custname;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.costImage = costImage;
        this.birthdate = birthdate;
        this.job = job;
    }
}
