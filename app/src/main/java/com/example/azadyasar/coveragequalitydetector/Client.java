package com.example.azadyasar.coveragequalitydetector;

/**
 * Created by azadyasar on 22/05/2016.
 */

public class Client implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    private String mName;
    private String username;
    private String password;

    public Client(String name, String username, String password) {
        mName = name;

        this.username = username;
        this.password = password;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
