package com.example.yasserahmed.onlineshopping;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.yasserahmed.onlineshopping.Adapters.Adapter_forShowProducts;
import com.example.yasserahmed.onlineshopping.Adapters_Classes.Products;
import com.example.yasserahmed.onlineshopping.Constants.CONSTANS;
import com.example.yasserahmed.onlineshopping.DataBase.ShoppingDataBase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CategoryItems extends AppCompatActivity {

    Context context;
    ShoppingDataBase shoppingDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);
        setTitle("Offers ");
        context=this;
        shoppingDataBase = new ShoppingDataBase(this);

        final int userid=getIntent().getIntExtra(CONSTANS.USERID,-1);
/*
       shoppingDataBase.AddProduct("skirt", "100", "20", "3", R.drawable.q1);
        shoppingDataBase.AddProduct("skirt", "100", "20", "3", R.drawable.q3);
        shoppingDataBase.AddProduct("skirt", "100", "20", "3", R.drawable.q4);
        shoppingDataBase.AddProduct("shirt", "100", "20", "3", R.drawable.t1);
        shoppingDataBase.AddProduct("shirt", "100", "20", "3", R.drawable.t2);
        shoppingDataBase.AddProduct("shirt", "100", "20", "3", R.drawable.t3);
        shoppingDataBase.AddProduct("short", "100", "20", "3", R.drawable.s1);
        shoppingDataBase.AddProduct("short", "100", "20", "3", R.drawable.s2);
        shoppingDataBase.AddProduct("short", "100", "20", "3", R.drawable.s3);

       shoppingDataBase.AddProduct("Iphone 6 plus", "10000", "10", "4", R.drawable.m1_iphone6plus);
        shoppingDataBase.AddProduct("Lenovo", "3000", "10", "4", R.drawable.m2_lenovo);
        shoppingDataBase.AddProduct("Moto C plus", "3500", "10", "4", R.drawable.m3_motocplus);
        shoppingDataBase.AddProduct("Nokia *", "4000", "10", "4", R.drawable.m4_nokia8);
        shoppingDataBase.AddProduct("Samsung 1", "2000", "10", "4", R.drawable.m5_samsung1);
        shoppingDataBase.AddProduct("Samsung 2", "3000", "10", "4", R.drawable.m6_samsung2);
        shoppingDataBase.AddProduct("Samsung 7", "7500", "10", "4", R.drawable.m7_samsungs7);
        shoppingDataBase.AddProduct("Samsung Tab 2", "2500", "10", "4", R.drawable.m8_samsungtab);
        shoppingDataBase.AddProduct("Ziox SZI3", "4000", "10", "4", R.drawable.m9_zioxmobileszi3);
*/




        int catid=getIntent().getIntExtra("catid",0);

        ShoppingDataBase dataBase=new ShoppingDataBase(this);


       final ArrayList<Products>arrayList=dataBase.GetProducts(catid);

       GridView gridView=(GridView)findViewById(R.id.gridviewProduct);

        Adapter_forShowProducts adapter_forShowProducts=new Adapter_forShowProducts(this,arrayList);
        gridView.setAdapter(adapter_forShowProducts);


        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("what Do you want to do ?")
                        .setCancelable(true)

                        .setMessage("choose Action")
                        .setNegativeButton("Add to Orders", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Date currentTime = Calendar.getInstance().getTime();

                                shoppingDataBase.AddOrder(currentTime.toString(),userid,"","",arrayList.get(i).ProID);
                            }
                        })
                        .setPositiveButton("Add to interests", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Date currentTime = Calendar.getInstance().getTime();
                                shoppingDataBase.AddInterist(currentTime.toString(),userid,arrayList.get(i).ProID);
                                // Toast.makeText(context,currentTime.toString(),Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                return false;
            }
        });



    }
}
