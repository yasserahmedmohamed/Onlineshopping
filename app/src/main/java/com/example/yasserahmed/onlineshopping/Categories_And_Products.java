package com.example.yasserahmed.onlineshopping;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yasserahmed.onlineshopping.Adapters.Adapter_forSearchProducts;
import com.example.yasserahmed.onlineshopping.Adapters.Adapter_forShowCategories;
import com.example.yasserahmed.onlineshopping.Adapters_Classes.Categories;
import com.example.yasserahmed.onlineshopping.Adapters_Classes.Products;
import com.example.yasserahmed.onlineshopping.AddItems.Add_Product;
import com.example.yasserahmed.onlineshopping.Constants.CONSTANS;
import com.example.yasserahmed.onlineshopping.DataBase.ShoppingDataBase;

import java.util.ArrayList;
import java.util.Locale;

public class Categories_And_Products extends AppCompatActivity {
    Context context;
    String gender;
    int USERID;
    GridView gridview;
    ArrayList<Categories> arrayList;
    GridView searchedView;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    LinearLayout Layout_Speech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories__and__products);

        Intent intent = getIntent();
        USERID = intent.getIntExtra(CONSTANS.USERID, -1);
        gender = intent.getStringExtra("gender");
     //   Toast.makeText(this, "user id is: " + USERID, Toast.LENGTH_SHORT).show();
        // Toast.makeText(this, "gender  is: " + gender, Toast.LENGTH_SHORT).show();


        context = this;
        setTitle("Categories");
        gridview = (GridView) findViewById(R.id.gridview);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.addelm);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Add_Product.class);
                startActivity(intent);


            }
        });


        ShoppingDataBase shoppingDataBase = new ShoppingDataBase(context);
        int arr[] = new int[4];

/*
        shoppingDataBase.AddCategory("properties", R.drawable.homeicon,""+ arr[0]);
        shoppingDataBase.AddCategory("Cars", R.drawable.caricon,""+ arr[1]);
        shoppingDataBase.AddCategory("Clothes", R.drawable.clothesicon,""+ arr[2]);
        shoppingDataBase.AddCategory("Mobiles", R.drawable.mobileicon,""+ arr[3]);
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

        shoppingDataBase.AddProduct("cairo new cairo", "400000", "1", "1", R.drawable.shq1);
        shoppingDataBase.AddProduct("Giza", "350000", "1", "1", R.drawable.shq2);
        shoppingDataBase.AddProduct("Tanta", "540000", "1", "1", R.drawable.shq3);
        shoppingDataBase.AddProduct("new cairo", "5000 for one meter", "1", "1", R.drawable.shq4);

        shoppingDataBase.AddProduct("Nissan", "250000", "3", "2", R.drawable.car1);
            shoppingDataBase.AddProduct("cheforlet", "150000", "3", "2", R.drawable.car2);
        shoppingDataBase.AddProduct("Jeep", "350000", "3", "2", R.drawable.car3);





       /*
*/

        arrayList = shoppingDataBase.Getcategories();
        for (int i = 0; i <=3 ; i++) {
            arr[i]=shoppingDataBase.NumberofOffers(i+1);

            arrayList.get(i).numberOfCateg=""+arr[i];

        }

        Adapter_forShowCategories adapter_forShowCategories = new Adapter_forShowCategories(arrayList, this);

        gridview.setAdapter(adapter_forShowCategories);


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(context, CategoryItems.class);

                intent.putExtra("catid", arrayList.get(position).id);
                intent.putExtra(CONSTANS.USERID,USERID);
              //   Toast.makeText(context, "num is : " + arrayList.get(position).id, Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
        });

    }

    SearchView menusearch;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.Search_Text);

        // Toast.makeText(context,"done",Toast.LENGTH_SHORT).show();
        menusearch = (SearchView) item.getActionView();
        menusearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                gridview.setVisibility(View.GONE);
                searchedView = (GridView) findViewById(R.id.Search_items);
                searchedView.setVisibility(View.VISIBLE);

                SearchText(newText, searchedView);


                return false;
            }
        });
        menusearch.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                gridview.setVisibility(View.VISIBLE);
                if (searchedView!=null)
                searchedView.setVisibility(View.GONE);

                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (gender == null) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        } else if (gender.equals("male")) {
            menu.findItem(R.id.Account).setIcon(R.drawable.user_chat1);
        } else if (gender.equals("female")) {
            menu.findItem(R.id.Account).setIcon(R.drawable.user_chat2);

        }


        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Account: {
                Intent intent = new Intent(this, UserAcount_Activity.class);
                intent.putExtra(CONSTANS.USERID, USERID);
                startActivity(intent);
                return true;
            }

            case R.id.Search_Voice: {

                SearchVoice();
            }


          /* case R.id.log_out:{
               AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);

               builder.setTitle("Log Out")
                       .setCancelable(true)
                       .setMessage("Are You Sure You Want To Log Out")
                       .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int which) {
                               SharedPreferences preferences = getSharedPreferences(CONSTANS.MY_PREFS_NAME, MODE_PRIVATE);
                               preferences.edit().clear().apply();
                               Intent intent=new Intent(context,LoginActivity.class);
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
           }*/

        }
        return false;
    }


    private void SearchVoice() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Say your search");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    ("Sorry! Your device doesn\\'t support speech input"),
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void SearchText(String newText, GridView gridView) {

        ShoppingDataBase dataBase = new ShoppingDataBase(context);
        ArrayList<Products> arrayList = dataBase.GetAllProducts();
        ArrayList<Products> searchedArraylist = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).ProName.toLowerCase().contains(newText.toLowerCase())) {

                searchedArraylist.add(new Products(arrayList.get(i).ProID,
                        arrayList.get(i).ProName.replace(newText.toLowerCase(), "<font color='#DF013A'>" + newText + "</font>"),
                        arrayList.get(i).Price,
                        arrayList.get(i).Quantity,
                        arrayList.get(i).CatID,
                        arrayList.get(i).prodImage
                ));

            }
        }


        Adapter_forSearchProducts adapter_forSearchProducts = new Adapter_forSearchProducts(context, searchedArraylist);

        gridView.setAdapter(adapter_forSearchProducts);

        adapter_forSearchProducts.notifyDataSetChanged();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Toast.makeText(context, result.get(0), Toast.LENGTH_SHORT).show();

                    Layout_Speech = (LinearLayout) findViewById(R.id.Layout_Speech);
                    SearchView Got_text = (SearchView) findViewById(R.id.Got_text);
                    final GridView Search_itemsSpeech = (GridView) findViewById(R.id.Search_itemsSpeech);
                    Button close_speech = (Button) findViewById(R.id.close_speech);


                    Layout_Speech.setVisibility(View.VISIBLE);
                    gridview.setVisibility(View.GONE);

                    Got_text.setVerticalScrollBarEnabled(true);
                    Got_text.setQuery(result.get(0), true);

                    Got_text.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            SearchText(newText, Search_itemsSpeech);
                            return false;
                        }
                    });

                    //  SearchText(result.get(0));
                    // gridview.setVisibility(View.GONE);
                    // searchedView.setVisibility(View.VISIBLE);

                    // menusearch.setQuery(result.get(0),false);


                    close_speech.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Layout_Speech.setVisibility(View.GONE);
                            gridview.setVisibility(View.VISIBLE);
                        }
                    });
                }
                break;
            }

        }
    }
}
