package com.example.yasserahmed.onlineshopping;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yasserahmed.onlineshopping.Adapters.Adapter_forOrders;
import com.example.yasserahmed.onlineshopping.Adapters.Adapter_forShowProducts;
import com.example.yasserahmed.onlineshopping.Adapters_Classes.Customers;
import com.example.yasserahmed.onlineshopping.Adapters_Classes.Orders;
import com.example.yasserahmed.onlineshopping.Constants.CONSTANS;
import com.example.yasserahmed.onlineshopping.DataBase.ShoppingDataBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserAcount_Activity extends AppCompatActivity {
    int userID;
    Context context;
    int is_open_order, is_open_intrist;
    ArrayList<Orders> ordersArrayList, interistsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_acount);
        userID = getIntent().getIntExtra(CONSTANS.USERID, -1);
        context = this;
        final ShoppingDataBase shoppingDataBase = new ShoppingDataBase(context);
        // Toast.makeText(context, ""+ordersArrayList.size(), Toast.LENGTH_SHORT).show();
        interistsArrayList = shoppingDataBase.getinterist(userID);

        // ordersArrayList.add(new Orders(0,"1/8/2017", 1, "437b new cairo", "3",0, R.drawable.t3));
        // interistsArrayList.add(new Orders(0,"1/8/2017", 1, "437b new cairo", "3",0, R.drawable.t3));
        //   interistsArrayList.add(new Orders(0,"1/8/2017", 1, "437b new cairo", "3",0, R.drawable.t3));

        ImageView user_Image = (ImageView) findViewById(R.id.user_Image);
        TextView user_name = (TextView) findViewById(R.id.user_name);
        //===================================================================
        final LinearLayout Order_layout = (LinearLayout) findViewById(R.id.Order_layout);
        final GridView Orders_grid = (GridView) findViewById(R.id.Orders_grid);
        final ImageButton imag_buton_order = (ImageButton) findViewById(R.id.imag_buton_order);
        is_open_order = 0;

        //===================================================================

        final LinearLayout interist_layout = (LinearLayout) findViewById(R.id.interist_layout);
        final GridView iterest_grid = (GridView) findViewById(R.id.iterest_grid);
        final ImageButton imag_buton_interist = (ImageButton) findViewById(R.id.imag_buton_interist);

        //==================================================================

        ArrayList<Customers> user_inf = shoppingDataBase.getTheCustomerInf(userID);
        setTitle(user_inf.get(0).Custname);
        user_name.setText(user_inf.get(0).Custname);
        user_Image.setImageResource(R.drawable.user_chat1);

        //============================================================
        Order_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (is_open_order == 0) {
                    ordersArrayList = shoppingDataBase.getOrders(userID);

                    Adapter_forOrders adapter_forOrders = new Adapter_forOrders(context, ordersArrayList, 0);
                    Orders_grid.setVisibility(View.VISIBLE);
                    Orders_grid.setAdapter(adapter_forOrders);
                    is_open_order = 1;
                    imag_buton_order.setBackgroundResource(R.drawable.arrow_up_float);
                } else if (is_open_order == 1) {
                    Orders_grid.setVisibility(View.GONE);
                    is_open_order = 0;
                    imag_buton_order.setBackgroundResource(R.drawable.arrow_down_float);
                }
            }
        });

        Orders_grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Remove From Your Order")
                        .setCancelable(true)
                        .setMessage("")
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                shoppingDataBase.deleteitemformOreders(ordersArrayList.get(i).OrdID);
                                ordersArrayList = shoppingDataBase.getOrders(userID);
                                Adapter_forOrders adapter_forOrders = new Adapter_forOrders(context, ordersArrayList, 0);
                                Orders_grid.setAdapter(adapter_forOrders);

                                adapter_forOrders.notifyDataSetChanged();
                            }
                        })
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return false;
            }
        });


//===================================================================================================================================
        interist_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_open_intrist == 0) {
                    Adapter_forOrders adapter_forOrders = new Adapter_forOrders(context, interistsArrayList, 1);
                    iterest_grid.setVisibility(View.VISIBLE);
                    iterest_grid.setAdapter(adapter_forOrders);

                    is_open_intrist = 1;
                    imag_buton_interist.setBackgroundResource(R.drawable.arrow_up_float);

                } else if (is_open_intrist == 1) {
                    iterest_grid.setVisibility(View.GONE);

                    is_open_intrist = 0;
                    imag_buton_interist.setBackgroundResource(R.drawable.arrow_down_float);

                }
            }
        });

        iterest_grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Choose Action")
                        .setCancelable(true)
                        .setMessage("")
                        .setNegativeButton("Remove From Interest", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                shoppingDataBase.deleteitemforminterst(interistsArrayList.get(i).OrdID);
                                interistsArrayList = shoppingDataBase.getinterist(userID);
                                Adapter_forOrders adapter_forint = new Adapter_forOrders(context, interistsArrayList, 0);
                                iterest_grid.setAdapter(adapter_forint);

                                adapter_forint.notifyDataSetChanged();
                            }
                        })
                        .setPositiveButton("Add to Orders", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Orders orders = interistsArrayList.get(i);
                                shoppingDataBase.deleteitemforminterst(interistsArrayList.get(i).OrdID);
                                interistsArrayList = shoppingDataBase.getinterist(userID);
                                Adapter_forOrders adapter_forint = new Adapter_forOrders(context, interistsArrayList, 0);
                                iterest_grid.setAdapter(adapter_forint);
                                shoppingDataBase.AddOrder(orders.OrdDatate, orders.CustID, orders.Address, orders.quantity, orders.ProductID);


                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log0ut, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.log_out) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Log Out")
                    .setCancelable(true)
                    .setMessage("Are You Sure You Want To Log Out")
                    .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences preferences = getSharedPreferences(CONSTANS.MY_PREFS_NAME, MODE_PRIVATE);
                            preferences.edit().clear().apply();
                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setPositiveButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return true;
        }
        return false;
    }


    public void StartOrder(View view) {

        Intent intent=new Intent(this,StartOrder.class);
        intent.putExtra(CONSTANS.USERID,userID);
        startActivity(intent);
    }







}
