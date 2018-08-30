package com.example.msi_.mychat;

public class Show_Chat_Activity_Data_Items {
    private String Phone;
    private String Image_Url;
    private String Name;

    public Show_Chat_Activity_Data_Items() {

    }

    public Show_Chat_Activity_Data_Items(String phone, String image_Url, String name) {

        Phone = phone;
        Image_Url = image_Url;
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void getPhone(String phone) {
        Phone = phone;
    }

    public String getImage_Url() {
        return Image_Url;
    }

    public void setImage_Url(String image_Url) {
        Image_Url = image_Url;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
