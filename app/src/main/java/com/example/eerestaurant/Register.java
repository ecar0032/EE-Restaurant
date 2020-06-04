package com.example.eerestaurant;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//REGISTER PAGE

public class Register extends AppCompatActivity {

    private static final int SELECT_PHOTO = 7777;
    EditText mTextUsername;
    EditText mTextPassword;
    EditText mTextConfirmPassword;
    EditText mTextFullName;
    ImageView imageView;
    Button btn_choose;
    Button mButtonRegister;
    TextView mTextViewLogin;
    DBHelper db;
    ImageButton mImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Variables containing the Id of it's respective

        db = new DBHelper(this);
        mImageButton =  (ImageButton) findViewById(R.id.imageButton);
        mTextUsername = (EditText) findViewById(R.id.edtUsername);
        mTextFullName = (EditText) findViewById(R.id.edtFullName);
        mTextPassword = (EditText) findViewById(R.id.edtPassword);
        mTextConfirmPassword = (EditText) findViewById(R.id.edtConfirmPassword);
        mButtonRegister = (Button) findViewById(R.id.buttonRegister);
        mTextViewLogin = (TextView) findViewById(R.id.txtLogin);

        //On click listener for button Id: txtLogin
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(Register.this, MainActivity.class);
                startActivity(LoginIntent);
            }
        });

        //On click listener for the imageButton so that the user can add an image to his profile.
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,SELECT_PHOTO);
            }
        });

        //This is an on click listener for when the user has entered all of his information and clicks the button register
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                String cnf_pwd = mTextConfirmPassword.getText().toString().trim();
                String fullN = mTextFullName.getText().toString();
                //When the user chooses a picture, make sure that the picture is less than 1mb, This is because this limit applies to Cursor BLOBS and will crash if this limit is exceeded.
                Bitmap bitmap = ((BitmapDrawable)mImageButton.getDrawable()).getBitmap();
                Boolean res = db.checkUsername(user);
                //I was going to add roles to each user that has registered, but a problem persisted; The database was not being updated with the user's details and the variable "long res" found
                //in DBHelper class line 63 kept on giving the value '-1' which means there was something wrong with the entry. If that was not the case I would have had that the first entry
                //in the Database would be an admin while the rest would have normal access to the app.
                String role = "Admin";

                //Checking if there is a username that is the same as the entered username, if there is a Toast "Username already taken" is shown.
                if(res == false) {
                    //Checking if the passwords are the same, if not a toast is shown to the user.
                    if (pwd.equals(cnf_pwd)) {
                        long val = db.addUser(user, pwd, fullN, Utils.getBytes(bitmap));
                        if (val > 0) {
                            Toast.makeText(Register.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            Intent movetoLogin = new Intent(Register.this, MainActivity.class);
                            startActivity(movetoLogin);
                        } else {
                            Toast.makeText(Register.this, "Error occurred while Registering", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(Register.this, "Username already taken", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //This is to check that the selected photo and the result code are the same and not null, photo will then be saved.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null){
            Uri pickedImage = data.getData();

            mImageButton.setImageURI(pickedImage);

        }
    }

}
