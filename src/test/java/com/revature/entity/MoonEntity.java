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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MoonEntity(String name){
        this.name = name;
    }
}
