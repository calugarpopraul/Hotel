package com.example.raul.hotel.Model;

public class UserModel {
    private String Email;
    private String Password;

    public UserModel(){}

    public UserModel(String email, String password){
        Email = email;
        Password = password;

    }

    public String getEmail(){
        return Email;
    }

    public void setEmail(String email){
        Email = email;
    }

    public String getPassword(){
        return Password;
    }

    public void setPassword(String password){
        Password = password;

    }

}
