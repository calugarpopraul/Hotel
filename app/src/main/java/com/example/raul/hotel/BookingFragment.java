package com.example.raul.hotel;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends Fragment {

    Button mBook;
    TextView mSelectedDate;
    DatePickerDialog mDatePickerDialog;
    Calendar mCalendar;
    TextView mEtTip;
    Button mCancel;
    public String fullDate;

    int year=0;
    int month=0;
    int dayOfMonth=0;
    boolean booked = false;
    int bookCounter = 0;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;


    public BookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_booking, container, false);
        View v = inflater.inflate(R.layout.fragment_booking,container,false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    //startActivity(new Intent(RegisterActivity.this,UI.class));
                }else{
                    //startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                }
            }
        };

        final ImageView mCalendarSearchImg = (ImageView) v.findViewById(R.id.calendarsearch);
        mBook = (Button) v.findViewById(R.id.buttonBooking);
        mSelectedDate = (TextView) v.findViewById(R.id.dateselected);
        mEtTip = (TextView) v.findViewById(R.id.etTip);
        mCancel = (Button) v.findViewById(R.id.buttonCancel);



        mBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(bookCounter==0) {

                    mCalendarSearchImg.setVisibility(View.INVISIBLE);
                    mCalendar = Calendar.getInstance();
                    year = mCalendar.get(Calendar.YEAR);
                    month = mCalendar.get(Calendar.MONTH);
                    dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);

                    mDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            mSelectedDate.setText(dayOfMonth + "/" + month + "/" + year);
                            fullDate = dayOfMonth + "-" + month + "-" + year;
                        }
                    }, 2018, 5, 18);
                    mDatePickerDialog.getWindow().getAttributes().gravity = Gravity.TOP;
                    mDatePickerDialog.show();
                    mBook.setText("Book now");
                    mCancel.setVisibility(View.VISIBLE);
                    mEtTip.setVisibility(View.VISIBLE);


                    mCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSelectedDate.setText("");
                            mCancel.setVisibility(View.INVISIBLE);
                            mEtTip.setVisibility(View.INVISIBLE);
                            mCalendarSearchImg.setVisibility(View.VISIBLE);
                            mBook.setText("Find booking");

                            bookCounter--;
                        }
                    });

                    bookCounter++;


                }
                else if(bookCounter==1){







                    mBook.setText("Find booking");
                    mCalendarSearchImg.setVisibility(View.VISIBLE);
                    mCancel.setVisibility(View.INVISIBLE);
                    mEtTip.setVisibility(View.INVISIBLE);
                    //mSelectedDate.setText("");
                    Toast.makeText(getActivity(), "You managed to successfully book your date on "+fullDate, Toast.LENGTH_LONG).show();
                    bookCounter--;

                    String user_id = mAuth.getCurrentUser().getUid();
                    DatabaseReference current_user_db = mDatabase.child(user_id).child("Dates");
                    current_user_db.child(fullDate).setValue("booking");

                    //String infos = mGetInfo.getText().toString();







                }

            }
        });

        mBook.setText("Find booking");

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}
