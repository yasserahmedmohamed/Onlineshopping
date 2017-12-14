package com.example.yasserahmed.onlineshopping.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.yasserahmed.onlineshopping.Adapters_Classes.Categories;
import com.example.yasserahmed.onlineshopping.Adapters_Classes.Customers;
import com.example.yasserahmed.onlineshopping.Adapters_Classes.Orders;
import com.example.yasserahmed.onlineshopping.Adapters_Classes.Products;

import java.util.ArrayList;

/**
 * Created by yasser ahmed on 12/3/2017.
 */

public class ShoppingDataBase extends SQLiteOpenHelper {

    public static final String name = "my_codes.db";
    public static final int version = 3;

    Context context;

    public ShoppingDataBase(Context context) {
        super(context, name, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table if not exists Customers(CustID integer primary key AUTOINCREMENT,Custname text NOT NULL,username text NOT NULL,password text NOT NULL ,gender text,costImage text,birthdate text ,job text)");

        db.execSQL("create table if not exists Categories(CatID integer primary key AUTOINCREMENT,CatName text,ImageURl text,NumofOffers text)");

        db.execSQL("create table if not exists Products(ProID integer primary key AUTOINCREMENT,ProName text,Price text,Quantity text,CatID integer,prodImage text,FOREIGN KEY(CatID) REFERENCES Categories (CatID))");

        db.execSQL("create table if not exists Orders(OrdID integer primary key AUTOINCREMENT ,OrdDatate text,CustID integer,Address text,quantity text,ProductID integer,OrdImage text,FOREIGN KEY(CustID) REFERENCES Customers (CustID),FOREIGN KEY(ProductID) REFERENCES Products (ProID))");

        db.execSQL("create table if not exists interiset(interID integer primary key AUTOINCREMENT ,interDatate text,CustID integer,ProductID integer,interImage text,FOREIGN KEY(CustID) REFERENCES Customers (CustID),FOREIGN KEY(ProductID) REFERENCES Products (ProID))");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Customers");
        db.execSQL("drop table if exists Categories");
        db.execSQL("drop table if exists Products");
        db.execSQL("drop table if exists Orders");
    }


    public void AddCustomer(String Custname, String username, String password, String gender, String birthdate, String job, String costImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Custname", Custname);
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("gender", gender);
        contentValues.put("birthdate", birthdate);
        contentValues.put("costImage", costImage);
        contentValues.put("job", job);
        db.insert("Customers", null, contentValues);
    }

    public void AddCategory(String CatName, int ImageURl, String NumofOffers) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CatName", CatName);
        contentValues.put("ImageURl", ImageURl);
        contentValues.put("NumofOffers", NumofOffers);
        db.insert("Categories", null, contentValues);


    }


    public void AddProduct(String ProName, String Price, String Quantity, String CatID, int prodImage) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ProName", ProName);
        contentValues.put("prodImage", prodImage);
        contentValues.put("Price", Price);
        contentValues.put("Quantity", Quantity);
        contentValues.put("CatID", CatID);
        db.insert("Products", null, contentValues);

        // Toast.makeText(context,"new products added",Toast.LENGTH_SHORT).show();
    }


    public void AddOrder(String OrdDatate, int CustID, String Address, String quantity, int ProductID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("OrdDatate", OrdDatate);
        contentValues.put("CustID", CustID);
        contentValues.put("Address", Address);
        contentValues.put("quantity", quantity);
        contentValues.put("ProductID", ProductID);
        long t = db.insert("Orders", null, contentValues);

        if (t != -1) {
            Toast.makeText(context, "New row added", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(context, "there is something wrong", Toast.LENGTH_SHORT).show();


    }



    //,interDatate text,CustID integer,ProductID integer,interImage text,FOREIGN KEY(CustID) REFERENCES Customers (CustID),FOREIGN KEY(ProductID) REFERENCES Products (ProID))");

    public void AddInterist(String interDatate, int CustID, int ProductID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("interDatate", interDatate);
        contentValues.put("CustID", CustID);
        contentValues.put("ProductID", ProductID);
        long t = db.insert("interiset", null, contentValues);

        if (t != -1) {
            Toast.makeText(context, "New interist added", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(context, "there is something wrong ", Toast.LENGTH_SHORT).show();


    }


    public ArrayList<Categories> Getcategories() {
        ArrayList<Categories> categoriesArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Categories ", null);


        res.moveToFirst();

        while (res.isAfterLast() == false) {
            categoriesArrayList.add(new Categories(res.getInt(0), res.getString(1), res.getInt(2), res.getString(3)));
            res.moveToNext();
        }

        return categoriesArrayList;

    }


    public ArrayList<Products> GetProducts(int CatId) {
        ArrayList<Products> productsArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Products WHERE CatID = " + CatId, null);


        res.moveToFirst();

        while (res.isAfterLast() == false) {

            productsArrayList.add(new Products(res.getInt(0), res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5)));

            res.moveToNext();
        }


        return productsArrayList;

    }

    public ArrayList<Products> GetAllProducts() {
        ArrayList<Products> productsArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Products ", null);


        res.moveToFirst();

        while (res.isAfterLast() == false) {

            productsArrayList.add(new Products(res.getInt(0), res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5)));

            res.moveToNext();
        }


        return productsArrayList;

    }


    public int NumberofOffers(int CatID) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM products WHERE products.CatID =" + CatID, null);


        return cursor.getCount();

    }


    public ArrayList<Customers> getCustomers() {
        ArrayList<Customers> CustomersArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Customers  ", null);

        res.moveToFirst();

        while (res.isAfterLast() == false) {


            CustomersArrayList.add(new Customers(res.getInt(0), res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7)));

            res.moveToNext();
        }


        return CustomersArrayList;
    }


    public ArrayList<Customers> getTheCustomerInf(int userId) {
        ArrayList<Customers> CustomersArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Customers where CustID = " + userId, null);

        res.moveToFirst();

        while (res.isAfterLast() == false) {


            CustomersArrayList.add(new Customers(res.getInt(0), res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7)));

            res.moveToNext();
        }


        return CustomersArrayList;
    }


    public ArrayList<Orders> getOrders(int userid) {

        ArrayList<Orders> orderArray = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Orders where CustID = " + userid, null);
        res.moveToFirst();


        while (res.isAfterLast() == false) {


            orderArray.add(new Orders(res.getInt(0),
                    res.getString(1), res.getInt(2),
                    res.getString(3), res.getString(4),
                    res.getInt(5), res.getString(6)));
            res.moveToNext();
        }


        return orderArray;

    }


    public ArrayList<Orders> getinterist(int userid) {

        ArrayList<Orders> orderArray = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from interiset where CustID = " + userid, null);

        res.moveToFirst();
        //interiset(interID integer primary key AUTOINCREMENT ,interDatate text,CustID integer,ProductID integer,interImage text

        while (res.isAfterLast() == false) {


            orderArray.add(new Orders(res.getInt(0),
                    res.getString(1), res.getInt(2),
                    "", "",
                    res.getInt(3), res.getString(4)));
            res.moveToNext();
        }


        return orderArray;

    }

    public ArrayList<Products> GetAnProducts(int ProID) {
        ArrayList<Products> productsArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Products WHERE ProID = " + ProID, null);


        res.moveToFirst();

        while (res.isAfterLast() == false) {

            productsArrayList.add(new Products(res.getInt(0), res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5)));

            res.moveToNext();
        }


        return productsArrayList;

    }


    public void deleteitemformOreders(int OrdID){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM Orders WHERE OrdID = "+OrdID);
    }

    public void deleteitemforminterst(int intdID){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM interiset WHERE interID = "+intdID);
    }

    public void updateQuantity(String quantity){

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("UPDATE Orders SET quantity = "+quantity);
    }

}
