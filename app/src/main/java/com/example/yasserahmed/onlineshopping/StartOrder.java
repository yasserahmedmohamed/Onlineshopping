package com.example.yasserahmed.onlineshopping;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yasserahmed.onlineshopping.Adapters.Adapter_forOrders;
import com.example.yasserahmed.onlineshopping.Adapters.Adapter_forStartingOrder;
import com.example.yasserahmed.onlineshopping.Adapters_Classes.Orders;
import com.example.yasserahmed.onlineshopping.Constants.CONSTANS;
import com.example.yasserahmed.onlineshopping.DataBase.ShoppingDataBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StartOrder extends AppCompatActivity {

    Context context;
    ProgressDialog dialog;
    int is_open_order;
    ArrayList<Orders> ordersArrayList;
    ShoppingDataBase shoppingDataBase;
    ImageButton imag_buton_order;
    ListView Orders_grid;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_order);
        context = this;
        userId = getIntent().getIntExtra(CONSTANS.USERID, -1);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            getmyLatlang();

        }

        LinearLayout Order_layout = (LinearLayout) findViewById(R.id.Order_layout_ord);
        imag_buton_order = (ImageButton) findViewById(R.id.imag_buton_order);
        shoppingDataBase = new ShoppingDataBase(context);
        Orders_grid = (ListView) findViewById(R.id.Orders_gridsatr);
        is_open_order = 0;
        Order_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (is_open_order == 0) {
                    ordersArrayList = shoppingDataBase.getOrders(userId);

                    Adapter_forStartingOrder adapter_forOrders = new Adapter_forStartingOrder(context, ordersArrayList);
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

    }


    public void getmyLatlang() {


        dialog = new ProgressDialog(StartOrder.this);
        dialog.show();
        dialog.setMessage("Getting your Location");
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location lastlocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastlocation == null)
            lastlocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

//        Toast.makeText(context, " longitud is : " + lastlocation.getLongitude() + " latitude : " + lastlocation.getLatitude(), Toast.LENGTH_SHORT).show();
        dialog.dismiss();
  //      GetLocalityName(lastlocation.getLatitude(), lastlocation.getLongitude());
    }


    public String GetLocalityName(double lat, double lng) {


        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        String add = "";
        try {
            addresses = gcd.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0) {
            Address obj = addresses.get(0);
            add = obj.getAddressLine(0);
            add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();
            Toast.makeText(context, "address is : " + add, Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(context, "There is no Internet connection", Toast.LENGTH_SHORT).show();
        }

        return add;
    }
}
