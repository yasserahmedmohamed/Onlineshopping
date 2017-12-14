package com.example.yasserahmed.onlineshopping;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yasserahmed.onlineshopping.Adapters_Classes.Customers;
import com.example.yasserahmed.onlineshopping.Constants.CONSTANS;
import com.example.yasserahmed.onlineshopping.DataBase.ShoppingDataBase;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static android.support.design.widget.Snackbar.*;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    Context context;
    ShoppingDataBase shoppingDataBase;
    ArrayList<Customers> All_emails;
int num_selcted;
    CheckBox remember_me;
    SharedPreferences.Editor saveCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();
        context = this;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }
        SharedPreferences getdata = getSharedPreferences(CONSTANS.MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = getdata.getString(CONSTANS.USER_NAME, null);
        String restoredText2 = getdata.getString(CONSTANS.GENDER, null);

        if (restoredText != null) {
            int id = getdata.getInt(CONSTANS.USERID, -1);
            Intent intent = new Intent(this, Categories_And_Products.class);
            intent.putExtra(CONSTANS.USERID, id);
            intent.putExtra(CONSTANS.GENDER, restoredText2);
            startActivity(intent);
            finish();
        }
        shoppingDataBase = new ShoppingDataBase(this);

        remember_me = (CheckBox) findViewById(R.id.remember_me);
        // customersArrayList=shoppingDataBase.getCustomers();
        All_emails = shoppingDataBase.getCustomers();

        TextView forget_password=(TextView)findViewById(R.id.forget_password);
        final View parentLayout = findViewById(android.R.id.content);

        forget_password.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailView.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    mEmailView.setError("Enter Your Email");
                    mEmailView.requestFocus();
                }
                else {

                    //https://shpkty.000webhostapp.com/SendEmails.php?to=yasserahmed2999@yahoo.com&sub=hi yasser&mes=it's work right"
                //    String uri="https://shpkty.000webhostapp.com/SendEmails.php?to="+email+"&sub=your password"+"&mes="+All_emails.get(num_selcted).password;
                    Snackbar.make(parentLayout,"You will receive email with password", LENGTH_SHORT).show();
                    Ion.with(context)
                            .load("https://shpkty.000webhostapp.com/SendEmails.php?to="+email+"&sub=hi%20your%20password%20is&mes=password:"+All_emails.get(num_selcted).password)
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




                }

            }
        });


        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);

        Button email_sign_up_button = (Button) findViewById(R.id.email_sign_up_button);

        email_sign_up_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), SignUPActivity.class);
                startActivity(intent);
            }
        });

    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            make(mEmailView, R.string.permission_rationale, LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {


        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
       /* if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }*/

        if (TextUtils.isEmpty(password) || password.equals(" ")) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            Customers customer = null;


            for (int i = 0; i < All_emails.size(); i++) {
                if (All_emails.get(i).username.equals(email)) {
                    customer = All_emails.get(i);

                }

            }
             if (TextUtils.isEmpty(email)==false && customer == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("you don't have An Account")
                        .setCancelable(true)
                        .setMessage("Do you want to sign up")
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences preferences = getSharedPreferences(CONSTANS.MY_PREFS_NAME, MODE_PRIVATE);
                                preferences.edit().clear().apply();
                                Intent intent = new Intent(context, SignUPActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                finish();

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
           else if (customer == null) {
                mEmailView.setError("Please Add Email");
                focusView = mEmailView;
                focusView.requestFocus();


            }  else if (email.equals(customer.username)) {
                if (password.equals(customer.password)) {
                    Intent intent = new Intent(this, Categories_And_Products.class);
                    if (remember_me.isChecked()) {
                        saveCustomer = getSharedPreferences(CONSTANS.MY_PREFS_NAME, MODE_PRIVATE).edit();
                        saveCustomer.putString(CONSTANS.USER_NAME, customer.username);
                        saveCustomer.putString(CONSTANS.PASSWORD, customer.password);
                        saveCustomer.putInt(CONSTANS.USERID, customer.CustID);
                        saveCustomer.putString(CONSTANS.GENDER, customer.gender);
                        saveCustomer.apply();


                    }
                    intent.putExtra("gender", customer.gender);
                    intent.putExtra(CONSTANS.USERID, customer.CustID);
                    startActivity(intent);
                    finish();
                } else {
                    mPasswordView.setError("Please Add A Right Password");
                    focusView = mPasswordView;
                    focusView.requestFocus();
                }
            } else {
                mEmailView.setError("Please Add A Right Email");
                focusView = mEmailView;
                focusView.requestFocus();
            }

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    @Override
    protected void onResume() {
        All_emails = shoppingDataBase.getCustomers();
        addEmailsToAutoComplete(All_emails);

        super.onResume();
    }



    /**
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }



     * Shows the progress UI and hides the login form.
     */



    /*
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }*/
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {


        addEmailsToAutoComplete(All_emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(ArrayList<Customers> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.

        ArrayList<String> emails = new ArrayList<>();

        for (int i = 0; i < emailAddressCollection.size(); i++) {
            emails.add(emailAddressCollection.get(i).username);
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emails);

        mEmailView.setAdapter(adapter);
        mEmailView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                num_selcted=i;
            }
        });

    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {


/*
            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }*/

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            //  mAuthTask = null;
            //showProgress(false);

            if (success) {

                //  we will work here

                Intent intent = new Intent(getApplicationContext(), Categories_And_Products.class);
                startActivity(intent);

            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }


    }
}

