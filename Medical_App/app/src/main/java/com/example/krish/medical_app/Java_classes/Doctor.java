package com.example.krish.medical_app.Java_classes;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by parini on 16-06-2017.
 */

public class Doctor
{
    private String Username;
    private String Password;
    private String Email;
    private String Fullname;
    private String Gender;
    private String Mobile;
    private String Qualification;
    private DatabaseReference doctor = FirebaseDatabase.getInstance().getReference();

    public Doctor(String Username, String Password,String Email,String Fullname,String Gender,String Mobile,String Qualification)
    {
        this.Username = Username;
        this.Password = Password;
        this.Email = Email;
        this.Fullname = Fullname;
        this.Gender = Gender;
        this.Mobile = Mobile;
        this.Qualification = Qualification;

    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void firebase_doctor()
    {
        Map<String,String> map_doctor = new HashMap<String,String>();
        map_doctor.put("email",Email);
        map_doctor.put("password",Password);
        map_doctor.put("name",Fullname);
        map_doctor.put("mobile",Mobile);
        map_doctor.put("gender",Gender);
        map_doctor.put("qualification",Qualification);



        doctor.child(Username).setValue(map_doctor);
    }
}
