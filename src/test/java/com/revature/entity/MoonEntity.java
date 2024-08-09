package com.revature.entity;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

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


    public MoonEntity(){}

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

    public void setDefaultImage(){
        Path relativePath = Paths.get("src/test/resources/images/moon-1.jpg");
        String absolutePath = relativePath.toAbsolutePath().toString();
        this.image = new File(absolutePath);
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
