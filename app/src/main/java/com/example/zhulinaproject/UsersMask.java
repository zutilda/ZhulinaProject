package com.example.zhulinaproject;

public class UsersMask {
    private int id_user;
    private String login;
    private String password;
    private String name;
    private String surname;
    private int age;
    private String patronymic;


    public UsersMask(int id_user, String password, String login, int age, String patronymic, String name, String surname) {
        this.id_user = id_user;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.login = login;
        this.age = age;
        this.patronymic = patronymic;
    }

    public UsersMask(String password, String login, int age, String patronymic, String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.login = login;
        this.age = age;
        this.patronymic = patronymic;

    }


    public int getId_user() {
        return id_user;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public String getPatronymic() {
        return patronymic;
    }
}
