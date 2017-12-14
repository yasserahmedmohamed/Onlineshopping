package com.example.yasserahmed.onlineshopping.AddItems;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.yasserahmed.onlineshopping.Adapters_Classes.Categories;
import com.example.yasserahmed.onlineshopping.DataBase.ShoppingDataBase;
import com.example.yasserahmed.onlineshopping.R;

import java.util.ArrayList;

public class Add_Product extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__product);
        ImageView prod_img=(ImageView)findViewById(R.id.prod_img);
        EditText prod_name=(EditText)findViewById(R.id.prod_name);
        EditText prod_price=(EditText)findViewById(R.id.prod_price);
        EditText prod_Quantity=(EditText) findViewById(R.id.prod_Quantity);

        ShoppingDataBase shoppingDataBase=new ShoppingDataBase(this);


        ArrayList<Categories> arrayList=shoppingDataBase.Getcategories();
        Spinner spinner=(Spinner)findViewById(R.id.prod_category);

        ArrayList data=new ArrayList();

        for (int i = 0; i <arrayList.size() ; i++) {
            data.add(arrayList.get(i).category);
        }
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        //   shoppingDataBase.AddProduct(prod_name.getText().toString(),prod_price.getText().toString(),prod_Quantity.getText().toString(),);



    }
}
