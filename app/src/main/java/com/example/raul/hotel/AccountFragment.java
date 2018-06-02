package com.example.raul.hotel;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    TextView mEtBooking;
    EditText mShowDate;
    Button mBtnSignOut;
    TextView mtv6name;
    private ListView mDatesList;

    public String username;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    private ArrayList<String> mDates = new ArrayList<>();



    public AccountFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_account, container, false);
        View v = inflater.inflate(R.layout.fragment_account,container,false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){

                }else{
                    //startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                }
            }
        };

        mEtBooking = (TextView) v.findViewById(R.id.bookingEt);
        mBtnSignOut = (Button) v.findViewById(R.id.btnSignOut);
        mtv6name = (TextView) v.findViewById(R.id.textView6);
        mDatesList = (ListView) v.findViewById(R.id.mDatesListView);



        String uid = mAuth.getCurrentUser().getUid();
        final DatabaseReference current_user_db = mDatabase.child(uid);
        final DatabaseReference name = current_user_db.child("name");
        final DatabaseReference date_db = mDatabase.child(uid).child("Dates");

        final ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mDates
        );

        mDatesList.setAdapter(mAdapter);


        name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username = dataSnapshot.getValue(String.class);
                mtv6name.setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        date_db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getKey();
                mDates.add(value);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getKey();
                mDates.add(value);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //String getArgument = getArguments().getString("data");
        //mEtBooking.setText(getArgument);//set string over textview


        //mEtBooking.setText(String.valueOf(bundle.getString("key")));

        mBtnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(), "Signed out", Toast.LENGTH_SHORT).show();

            }
        });



        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        //mtv6name.setText(username);
    }
}
