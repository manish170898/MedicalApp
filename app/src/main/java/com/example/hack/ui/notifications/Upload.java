package com.example.hack.ui.notifications;

public class Upload {
    private String mname;
    private String mImageUrl;

    public Upload(){
        //Empty constructor needed
    }

    public Upload(String name, String ImageUrl){
        mname = name;
        mImageUrl = ImageUrl;
    }

    public String getname(){
        return mname;
    }
    public void setname(String name){
        mname = name;
    }
    public String getImageUrl(){
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl){
        mImageUrl = imageUrl;
    }

}
