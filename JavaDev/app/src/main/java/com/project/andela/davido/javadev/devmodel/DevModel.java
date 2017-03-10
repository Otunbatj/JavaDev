package com.project.andela.davido.javadev.devmodel;

/**
 * Created by DAVIDO on 3/10/2017.
 */

public class DevModel {

    private String name;
    private String imageURL;
    private String htmlURL;

    public DevModel (String name, String imageURL, String htmlURL){
        this.name = name;
        this.imageURL = imageURL;
        this.htmlURL = htmlURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getHtmlURL() {
        return htmlURL;
    }

    public void setHtmlURL(String htmlURL) {
        this.htmlURL = htmlURL;
    }
}


