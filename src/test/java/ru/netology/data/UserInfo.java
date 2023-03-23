package ru.netology.data;


import lombok.Data;

@Data
public class UserInfo {
    String name;
    String city;
    String phone;
    public UserInfo(String generateCity, String generateName, String generatePhone) {}
}
