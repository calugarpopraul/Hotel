package com.example.raul.hotel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.raul.hotel.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class BaseActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private FirebaseDatabase database;

    private UserModel mCurrentUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
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

    public void setmCurrentUser(UserModel mCurrentUser) {
        this.mCurrentUser = mCurrentUser;
    }
/*
    public void addFragmentOnTop(Fragment fragment, int layoutId, String tag, boolean addStack, boolean animateIn, boolean animateOut) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int inAnimation;
        int outAnimation;
        int outAnimationPop = 0;

        if (animateIn) {
            inAnimation = R.anim.slide_up_back;
        } else {
            inAnimation = 0;
        }

        if (animateOut) {
            outAnimation = R.anim.fragment_exit;
            outAnimationPop = R.anim.fragment_exit_pop;

        } else {
            outAnimation = 0;
        }
        if (animateIn || animateOut) {
            // no enter animation
            fragmentTransaction.setCustomAnimations(inAnimation, outAnimation, 0, outAnimationPop);

        }
        fragmentTransaction.add(layoutId, fragment, tag);

        if (addStack)
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commitAllowingStateLoss();
    }
*/
    public boolean removeFragmentWithTag(String tag, FragmentManager fragm) {
        List<Fragment> fragmList = fragm.getFragments();

        for (Fragment fragment : fragmList) {
            if (fragment.getTag().equalsIgnoreCase(tag)) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                return true;
            }
        }

        return false;

    }
/*
    @Override
    public void onBackPressed() {
        FragmentManager fragmMan = getSupportFragmentManager();

        if (removeFragmentWithTag(EditProfileFragment.TAG, fragmMan)) {
            getSupportActionBar().show();
        }
    }
    */


}
