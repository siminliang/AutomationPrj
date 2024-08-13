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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
