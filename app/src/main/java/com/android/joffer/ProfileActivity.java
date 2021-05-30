package com.android.joffer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    TextView profileNameTextView;
    EditText profilePhone;
    TextView profileEmailTextView;
    EditText profileCity;
    Button saveProfileButton;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        profileNameTextView = findViewById(R.id.profile_name);
        profileEmailTextView = findViewById(R.id.profile_email);

        profilePhone = findViewById(R.id.profile_phone);
        profileCity =  findViewById(R.id.profile_city);

        saveProfileButton = findViewById(R.id.save_profile_button);


        profileNameTextView.setText( getProfileName() );
        profileEmailTextView.setText(getProfileEmail());

        if(getProfilePhone() != null) profilePhone.setText(getProfilePhone());

        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });
    }
    private String getProfilePhone(){

        return "0636108193";
    }

    private String getProfileEmail(){
        String Email = (String)currentUser.getEmail();
        return "Your Email :" +Email;
    }

    private String getProfileName(){
        String Name = (String)currentUser.getDisplayName();

        return "Your Name is :"+Name;
    }
    public void saveProfile(){
        String userPhone = ""+profilePhone.getText();
        String userCity = ""+profileCity.getText();
        Toast.makeText(this,userPhone,Toast.LENGTH_LONG).show();
        Toast.makeText(this,userCity,Toast.LENGTH_LONG).show();
    }

    public static Intent makeIntent(Context c){
        return new Intent(c, ProfileActivity.class);
    }
}
