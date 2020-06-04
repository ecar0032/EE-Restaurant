package com.example.eerestaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//LOGIN PAGE

public class MainActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;
    DBHelper db;

    //declaring shared preferences so that I could use these variables in other activities
    public static final String SHARED_PREFS = "sharedprefs";
    public static final String USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        //declaring variables and creating an instance of DBHelper called db
        db = new DBHelper(this);
        mTextUsername = (EditText) findViewById(R.id.edtUsername);
        mTextPassword = (EditText) findViewById(R.id.edtPassword);
        mButtonLogin = (Button) findViewById(R.id.buttonLogin);
        mTextViewRegister = (TextView) findViewById(R.id.txtRegister);

        //Intent to go to the register page from the login page
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent (MainActivity.this, Register.class);
                startActivity(registerIntent);
            }
        });


        //Setting a listener for when the user clicks the login button, storing his information in variables and comparing it to the Database. If not found, the user is required to register
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                Boolean res = db.checkUser(user,pwd);

                //res will have the boolean response from the method checkUser in DBHelper class, this will check whether the entered username & password are the same as one of the entries
                if(res == true){
                    Toast.makeText(MainActivity.this,"Successsfully Logged in", Toast.LENGTH_SHORT).show();
                    //saving the user's data to a shared preference variable so it can be used anywhere in the app, it's done here so that anyone who logs in will have this variable as his information
                    saveData(user);
                    Intent appAccess = new Intent (MainActivity.this, Fragments.class);
                    startActivity(appAccess);
                }
                else
                {
                    //res is false therefore no entry was found with the user's entered information, redirects to the register page.
                    Toast.makeText(MainActivity.this,"Wrong Credentials, Register an Account!", Toast.LENGTH_SHORT).show();
                    Intent registerIntent = new Intent (MainActivity.this, Register.class);
                    startActivity(registerIntent);
                }
            }
        });

    }

    //saveData method to save the username of the logged in user in the variable username.
    public void saveData(String username){

        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(USERNAME, username);

        editor.apply();
    }
}
