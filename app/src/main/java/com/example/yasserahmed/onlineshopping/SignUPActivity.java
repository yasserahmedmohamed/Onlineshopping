package com.example.yasserahmed.onlineshopping;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yasserahmed.onlineshopping.DataBase.ShoppingDataBase;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.Date;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;

public class SignUPActivity extends AppCompatActivity {

    public String Custname="";
    public String username="";
    public String password="";
    public String gender="";
    public String costImage="";
    public String birthdate="";
    public String job="";
    String repassword="";
Context context;
    //=====================================
    Button button_get_Date;
    Button email_register_button;
    DatePicker datapicker_get_date;
    LinearLayout Calender_Layout;
    AutoCompleteTextView Sign_email;
    AutoCompleteTextView Sign_password;
    AutoCompleteTextView Sign_Reenterpassword;
    AutoCompleteTextView Sign_BirthDate;
    ImageView open_calender;
    AutoCompleteTextView Sign_job;
    RadioGroup radioGroup;
    ImageView Sign_image;
    AutoCompleteTextView Sign_name;
    RadioButton male;
    RadioButton female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
context=this;
        setTitle("Sing Up");

         Sign_image=(ImageView)findViewById(R.id.Sign_image);
         Sign_name=(AutoCompleteTextView)findViewById(R.id.Sign_name);
         Sign_email=(AutoCompleteTextView)findViewById(R.id.Sign_email);
         Sign_password=(AutoCompleteTextView)findViewById(R.id.Sign_password);
         Sign_Reenterpassword=(AutoCompleteTextView)findViewById(R.id.Sign_Reenterpassword);
         male=(RadioButton)findViewById(R.id.male);
         female=(RadioButton)findViewById(R.id.female);
         Sign_BirthDate=(AutoCompleteTextView)findViewById(R.id.Sign_BirthDate);
         open_calender=(ImageView)findViewById(R.id.open_calender);
         Sign_job=(AutoCompleteTextView)findViewById(R.id.Sign_job);
        Calender_Layout=(LinearLayout)findViewById(R.id.Calender_Layout);
         datapicker_get_date=(DatePicker)findViewById(R.id.get_date);
        button_get_Date=(Button)findViewById(R.id.button_get_Date);
        email_register_button=(Button)findViewById(R.id.email_register_button);
         radioGroup=(RadioGroup)findViewById(R.id.err);

         email_register_button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 ClearFoucs();
                 StratSignup();
             }
         });

        open_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calender_Layout.setVisibility(View.VISIBLE);
            }
        });
        button_get_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                birthdate=datapicker_get_date.getDayOfMonth()+"/"+(datapicker_get_date.getMonth()+1)+"/"+datapicker_get_date.getYear();
                Sign_BirthDate.setText(birthdate);
                Calender_Layout.setVisibility(View.GONE);
            }
        });

    }

    void StratSignup(){
        Custname=Sign_name.getText().toString();
        username=Sign_email.getText().toString();
        password=Sign_password.getText().toString();
        repassword=Sign_Reenterpassword.getText().toString();
        if (male.isChecked())
            gender="male";
        else if (female.isChecked())
            gender="female";

        job=Sign_job.getText().toString();

        if (checkName(Custname)&&checkEmail(username)&&checkPssword(password,repassword)&&checkgender(gender)&&checkJob(job)&&checkbirthdate(birthdate)){
            Toast.makeText(this,"Done you signed up",Toast.LENGTH_SHORT).show();
            ClearFoucs();
            ShoppingDataBase shoppingDataBase=new ShoppingDataBase(this);
            shoppingDataBase.AddCustomer(Custname,username,password,gender,birthdate,job,"");

            final View parentLayout = findViewById(android.R.id.content);


            Ion.with(context)
                    .load("https://shpkty.000webhostapp.com/SendEmails.php?to="+username+"&sub=Confirm%20email&mes=Thanks%20for%20using%20OnlineShopping")
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {

                        @Override
                        public void onCompleted(Exception e, JsonObject result) {


                            if (e == null) {

                                Snackbar.make(parentLayout,"Done you received An Email With Password", LENGTH_SHORT).show();


                            } else {
                                Snackbar.make(parentLayout,"Done you received An Email With Password", LENGTH_SHORT).show();

                            }

                        }
                    });






            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();





        }
    }
    boolean checkEmail(String username){
        if (TextUtils.isEmpty(username)){
            Sign_email.setError(getString(R.string.error_field_required));
            Sign_email.requestFocus();
            return false;
        }
        if (!username.contains("@")&&!TextUtils.isEmpty(username)){
            Sign_email.setError(getString(R.string.error_invalid_email)+"must Contain @");
            Sign_email.requestFocus();
            return false;
        }
        return true;
    }
    boolean checkName(String custname){

        if (TextUtils.isEmpty(custname)){
            Sign_name.setError(getString(R.string.error_field_required));
            Sign_name.requestFocus();
            return false;
        }
        return true;
    }
    boolean checkPssword(String password,String repassword){
        if (TextUtils.isEmpty(password)){
            Sign_password.setError(getString(R.string.error_field_required));
            Sign_password.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(repassword)){
            Sign_Reenterpassword.setError(getString(R.string.error_field_required));
            Sign_Reenterpassword.requestFocus();
            return false;
        }
        if (!password.equals(repassword)){
            Sign_Reenterpassword.setError("It's not the same password");
            Sign_Reenterpassword.requestFocus();
            return false;

        }
        return true;
    }
  boolean  checkgender(String gender){

      if (TextUtils.isEmpty(gender)){
          male.setError("please chose one");
          male.requestFocus();
          return false;
      }
      return true;
  }
  boolean checkJob(String job){
      if (TextUtils.isEmpty(job)){
          Sign_job.setError(getString(R.string.error_field_required));
          Sign_job.requestFocus();
          return false;
      }
      return true;

  }
  boolean checkbirthdate(String birthdate){
      if (TextUtils.isEmpty(birthdate)){
          Sign_BirthDate.setError(getString(R.string.error_field_required));
          Sign_BirthDate.requestFocus();
          return false;
      }
      return true;
  }


  void ClearFoucs(){
      Sign_email.setError(null);
      Sign_BirthDate.setError(null);
      Sign_job.setError(null);
      Sign_password.setError(null);
      Sign_Reenterpassword.setError(null);
      Sign_job.setError(null);
      male.setError(null);
  }
}
