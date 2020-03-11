package com.dong.service.impl;

import com.dong.dao.UserDao;
import com.dong.entry.User;
import com.dong.service.UserService;
import com.dong.util.Tools;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDao();
    @Override
    public boolean checkUser(User user) {
        user.setPasswors(Tools.md5(user.getPassword()));
        return userDao.queryUser(user);
    }

    @Override
    public boolean addUser(User user) {
        user.setPasswors(Tools.md5(user.getPassword()));
        return userDao.insertUser(user);
    }

    @Override
    public boolean checkName(String username) {
        return userDao.queryByName(username);
    }
}
