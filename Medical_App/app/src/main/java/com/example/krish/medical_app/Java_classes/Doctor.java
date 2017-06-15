package com.example.krish.medical_app.Java_classes;

/**
 * Created by parini on 16-06-2017.
 */

public class Doctor
{
    private String Username;
    private String Password;

    Doctor(String Username, String Password)
    {
        this.Username = Username;
        this.Password = Password;

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
}
