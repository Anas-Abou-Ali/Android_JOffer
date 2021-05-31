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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.android.joffer.DAO.Users;

public class ProfileActivity extends AppCompatActivity {
    TextView profileNameTextView;
    EditText profilePhone;
    TextView profileEmailTextView;
    EditText profileCity;
    Button saveProfileButton;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("Users");

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
        if(getProfileCity() != null) profileCity.setText(getProfileCity());

        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });
    }

    private String getProfilePhone(){

        return "NUll";
    }

    private String getProfileCity(){
        return "Null";
    }
    private String getProfileEmail(){
        String Email = (String)currentUser.getEmail();
        return Email;
    }

    private String getProfileName(){
        String Name = (String)currentUser.getDisplayName();

        return Name;
    }
    public void saveProfile(){

        Users user = new Users();
        String userPhone = profilePhone.getText().toString().trim();
        String userCity = profileCity.getText().toString().trim();
        user.setUID(currentUser.getUid());
        user.setEmail(getProfileEmail().trim());
        user.setName(getProfileName().trim());
        user.setCity(userCity.trim());
        user.setPhone(userPhone.trim());
        myRef.child(user.getUID().toString()).setValue(user);
        Toast.makeText(this,userPhone,Toast.LENGTH_LONG).show();
        Toast.makeText(this,userCity,Toast.LENGTH_LONG).show();
    }

    public static Intent makeIntent(Context c){
        return new Intent(c, ProfileActivity.class);
    }
}
