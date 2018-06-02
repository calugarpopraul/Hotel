package com.example.raul.hotel;



import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.raul.hotel.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private FirebaseAuth firebaseAuth;

    private FirebaseDatabase database;

    private UserModel mCurrentUser;

    private DatabaseReference databaseUsers;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;


    ImageView mImgView;
    Button mBtnLogin;
    EditText mEmail;
    EditText mPassword;
    EditText mFirstNameReg, mLastNameReg, mEmailReg, mPasswordReg;
    Button mButtonLogin;
    Button mButtonRegisterReg;
    Dialog myDialog;
    Dialog registerDialog;
    TextView mRegisterHere;
    TextView mTextView3;
    TextView mHelloUser;


    public String email, password,username;
    public String firstNameReg, lastNameReg, emailReg, passwordReg;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);



    }



    private void updateUI(FirebaseUser user) {

        if (user != null) {

            mBtnLogin.setVisibility(View.INVISIBLE);
            mRegisterHere.setVisibility(View.INVISIBLE);



        } else {

            mBtnLogin.setVisibility(View.INVISIBLE);
            mRegisterHere.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_home, container, false);
        final Button mBtnLogin = (Button) v.findViewById(R.id.btnLogin);
        mTextView3 = (TextView) v.findViewById(R.id.textView3);
        final TextView mRegisterHere = (TextView) v.findViewById(R.id.etRegisterHere);
        mHelloUser = (TextView) v.findViewById(R.id.helloUser);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");






        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    mAuth = FirebaseAuth.getInstance();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
                    String uid = mAuth.getCurrentUser().getUid();
                    final DatabaseReference current_user_db = mDatabase.child(uid);
                    final DatabaseReference name = current_user_db.child("name");

                    //startActivity(new Intent(RegisterActivity.this,UI.class));
                    mBtnLogin.setVisibility(View.INVISIBLE);
                    mRegisterHere.setVisibility(View.INVISIBLE);
                    mTextView3.setVisibility(View.INVISIBLE);



                    name.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            username = dataSnapshot.getValue(String.class);
                            mHelloUser.setText(username);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    mHelloUser.setVisibility(View.VISIBLE);




                }else{
                    //startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    mBtnLogin.setVisibility(View.VISIBLE);
                    mRegisterHere.setVisibility(View.VISIBLE);
                    mTextView3.setVisibility(View.VISIBLE);
                    mHelloUser.setVisibility(View.INVISIBLE);
                }
            }
        };





        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //LoginAlert();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);


            }
        });

        mRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),SignUpActivity.class);
                startActivity(i);

            }
        });


        return v;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //final DatabaseReference table_user = database.getReference("Users");


    }



    public void LoginAlert() {

        myDialog = new Dialog(getContext());
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.dialog_login);
        myDialog.setTitle("Please login");


        mButtonLogin = (Button) myDialog.findViewById(R.id.buttonLogin);
        mEmail = (EditText) myDialog.findViewById(R.id.etEmail);
        mPassword = (EditText) myDialog.findViewById(R.id.etPassword);

        mButtonLogin.setEnabled(true);


        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = mEmail.getText().toString();
                password = mPassword.getText().toString();

                //login();


                myDialog.cancel();


            }
        });

        myDialog.show();


    }

    public void RegisterAlert() {

        registerDialog = new Dialog(getContext());
        registerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        registerDialog.setContentView(R.layout.dialog_register);
        registerDialog.setTitle("Register");

        mRegisterHere = (TextView) registerDialog.findViewById(R.id.buttonRegisterReg);
        mFirstNameReg = (EditText) registerDialog.findViewById(R.id.etFirstNameReg);
        mLastNameReg = (EditText) registerDialog.findViewById(R.id.etLastNameReg);
        mEmailReg = (EditText) registerDialog.findViewById(R.id.etEmailReg);
        mPasswordReg = (EditText) registerDialog.findViewById(R.id.etPasswordReg);
        mButtonRegisterReg = (Button) registerDialog.findViewById(R.id.buttonRegisterReg);

        mButtonRegisterReg.setEnabled(true);

        mButtonRegisterReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //firstNameReg  = mFirstNameReg.getText().toString();
                //lastNameReg = mLastNameReg.getText().toString();
                //emailReg = mEmailReg.getText().toString();
                //passwordReg = mPasswordReg.getText().toString();


            }
        });

        //registerDialog.show();

    }


    private void LoginUser(String mail, final String password) {
        firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener((Activity) getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment mFrag = new AccountFragment();
                    ft.replace(R.id.main_frame, mFrag);
                    ft.commit();
                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content),
                            task.getException().getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                }

            }
        });

    }

    public FirebaseAuth getFirebaseAuth() {
        return this.firebaseAuth;
    }

    public FirebaseDatabase getDatabase() {
        return this.database;
    }

    public UserModel getmCurrentUser() {
        return mCurrentUser;
    }
}

