package com.revature.entity;

import java.io.File;
import java.nio.file.Path;

public class PlanetEntity {

    public String name;

    public String owner;

    public File image;

    public String id;

    public PlanetEntity(String name, String owner, File image){
        this.name = name;
        this.owner = owner;
        this.image = image;
    }

    public PlanetEntity(String name, String owner){
        this.name = name;
        this.owner = owner;
    }

    public PlanetEntity(String name){
        this.name = name;
    }
}
