package com.dong.service;


import com.dong.entry.User;

public interface UserService {
    boolean checkUser(User user);
    boolean addUser(User user);
    boolean checkName(String username);
}
