package com.dong.dao;

import com.dong.entry.User;
import com.dong.util.JdbcUtil;


public class UserDao {
    public boolean insertUser(User user) {
        boolean result = false;
        String sql = "insert into user(username,password,email,qq) values(?,?,?,?)";
        result = JdbcUtil.executeUpdate(sql, user.getUsername(), user.getPassword(),user.getEmail(),user.getQq());
        return result;
    }
    
    public boolean queryUser(User user){
        boolean result = false;
        String sql = "select count(*) from user where username=? and password=?";
        long i = JdbcUtil.queryForRow(Long.class, sql, user.getUsername(), user.getPassword());
        if (i == 1){
            result = true;
        }
        return result;
    }

    public boolean queryByName(String username){
        boolean result = false;
        String sql = "select count(*) from user where username=?";
        long i = JdbcUtil.queryForRow(Long.class, sql, username);
        if (i == 1){
            result = true;
        }
        return result;
    }
}
