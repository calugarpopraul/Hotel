package com.example.raul.hotel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.raul.hotel.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    EditText mPhoneUp,mEmailUp,mPasswordUp,mNameUp;
    Button mBtnSignUp;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    Intent i = new Intent(SignUpActivity.this,MainActivity.class);
                    startActivity(i);

                }else{
                    //startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                }
            }
        };

        //initialize widgets
        mPasswordUp = (EditText) findViewById(R.id.etPasswordUp);
        mPhoneUp = (EditText) findViewById(R.id.etPhoneUp);
        mEmailUp = (EditText) findViewById(R.id.etEmailUp);
        mBtnSignUp = (Button) findViewById(R.id.button);
        mNameUp = (EditText) findViewById(R.id.etNameUp);

        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = mPhoneUp.getText().toString().trim();
                final String email = mEmailUp.getText().toString().trim();
                final String password = mPasswordUp.getText().toString().trim();
                final String name = mNameUp.getText().toString().trim();

                if(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) &&
                        !TextUtils.isEmpty(name)){
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference current_user_id = mDatabase.child(user_id);
                                current_user_id.child("email").setValue(email);
                                current_user_id.child("password").setValue(password);
                                current_user_id.child("name").setValue(name);
                                current_user_id.child("phone").setValue(phone);
                            } else {

                            }
                        }
                    });
                }
                Toast.makeText(SignUpActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
            }
        });

        /*
        //old style
        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if user already exists
                        if(dataSnapshot.child(mPhoneUp.getText().toString()).exists()){
                            Toast.makeText(SignUpActivity.this, "Phone number already register", Toast.LENGTH_SHORT).show();
                        } else {
                            UserModel user = new UserModel(mEmailUp.getText().toString(),mPasswordUp.getText().toString());
                            table_user.child(mPhoneUp.getText().toString()).setValue(user);
                            Toast.makeText(SignUpActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }



                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        */
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}
