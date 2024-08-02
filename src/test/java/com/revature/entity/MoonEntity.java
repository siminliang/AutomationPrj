package com.revature.entity;

import java.io.File;

public class MoonEntity {

    public String name;

    public String owner;

    public File image;

    public String id;

    public MoonEntity(String name, String owner, File image){
        this.name = name;
        this.owner = owner;
        this.image = image;
    }

    public MoonEntity(String name, String owner){
        this.name = name;
        this.owner = owner;
    }
}
