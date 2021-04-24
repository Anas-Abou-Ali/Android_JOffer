package com.android.joffer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user == null)
        {
            setContentView(R.layout.activity_main);
            FacebookSdk.sdkInitialize(getApplicationContext());
            loginButton = (LoginButton)findViewById(R.id.login_button);

            callbackManager = CallbackManager.Factory.create();
            loginButton.setReadPermissions(Arrays.asList("email"));
//            AppEventsLogger.activateApp(this);
        }
        else {
            Intent myIntent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(myIntent);
        }

    }

    public void buttonclickLoginFb(View v)
    {

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"User cancelled it",Toast.LENGTH_LONG);
            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    private void handleFacebookToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    FirebaseUser myuserobj = auth.getCurrentUser();
                    updateUI(myuserobj);
                }
                else {
                    Toast.makeText(getApplicationContext(),"could not register to firebase", Toast.LENGTH_LONG);
                }

            }
        });
    }

    private void updateUI(FirebaseUser myuserobj) {
    }
}