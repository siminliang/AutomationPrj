package com.revature.entity;

public class UserEntity {
    public String username;
    public String password;
    public String id;

    public UserEntity(String username, String password){
        this.username = username;
        this.password = password;
    }

    public UserEntity(String username){
        this.username = username;
        this.password = "default";
    }

    public UserEntity(){
    }
}
