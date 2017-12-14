package com.example.yasserahmed.onlineshopping.Adapters_Classes;

import com.example.yasserahmed.onlineshopping.R;

/**
 * Created by yasser ahmed on 12/9/2017.
 */

public class Orders {

    public int OrdID;
    public String OrdDatate;
    public int CustID;
    public String Address;
    public String quantity;
    public int ProductID;
    public String OrdImage;


    public Orders(int ordID, String ordDatate, int custID, String address, String quantity, int productID, String ordImage) {
        OrdID = ordID;
        OrdDatate = ordDatate;
        CustID = custID;
        Address = address;
        this.quantity = quantity;
        ProductID = productID;
        OrdImage = ordImage;
    }
}
