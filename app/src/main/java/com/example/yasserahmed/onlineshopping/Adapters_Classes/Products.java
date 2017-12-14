package com.example.yasserahmed.onlineshopping.Adapters_Classes;

/**
 * Created by yasser ahmed on 12/4/2017.
 */

public class Products {


    //  Products(ProID integer primary key AUTOINCREMENT,ProName text,Price text,Quantity text,CatID integer,prodImage text

    public int ProID;
    public String ProName;
    public String Price;
    public String Quantity;
    public String CatID;
    public String prodImage;


    public Products(int proID, String proName, String price, String quantity, String catID, String prodImage) {
        ProID = proID;
        ProName = proName;
        Price = price;
        Quantity = quantity;
        CatID = catID;
        this.prodImage = prodImage;
    }
}
