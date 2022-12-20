package com.example.zhulinaproject;

public class SelectedsMask {
    private int id_selected;
    private String nameSlected;
    private int id_users;
    private int id_hobby;
    private int personal_assessment;
    private String PhotoSlected;

    public SelectedsMask(int id_selected, int id_hobby, int id_users, String nameSlected, int personal_assessment, String PhotoSlected) {
        this.id_selected = id_selected;
        this.id_hobby = id_hobby;
        this.id_users = id_users;
        this.nameSlected = nameSlected;
        this.personal_assessment = personal_assessment;
        this.PhotoSlected = PhotoSlected;
    }

    public SelectedsMask(int id_hobby, int id_selected, int id_users, int personal_assessment) {
        this.id_selected = id_selected;
        this.id_hobby = id_hobby;
        this.id_users = id_users;
        this.personal_assessment = personal_assessment;
    }

    public SelectedsMask(int id_hobby, int id_users, int personal_assessment) {
        this.id_hobby = id_hobby;
        this.id_users = id_users;
        this.personal_assessment = personal_assessment;
    }


    public int getId_selected() {
        return id_selected;
    }

    public int getId_hobby() {
        return id_hobby;
    }

    public String getNameSlected() {
        return nameSlected;
    }

    public int getId_users() {
        return id_users;
    }

    public int getPersonal_assessment() {
        return personal_assessment;
    }

    public String getPhotoSlected() {
        return PhotoSlected;
    }
}
