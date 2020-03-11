package com.dong.entry;

public class User {
    private Integer id;
    private String username;
    private String password;
    private Integer rule;
    private String email;
    private String qq;

    public User() {
    }

    public User(String username, String password, Integer rule, String email, String qq) {
        this.username = username;
        this.password = password;
        this.rule = rule;
        this.email = email;
        this.qq = qq;
    }

    public User(Integer id, String username, String password, Integer rule, String email, String qq) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rule = rule;
        this.email = email;
        this.qq = qq;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPasswors(String password) {
        this.password = password;
    }

    public Integer getRule() {
        return rule;
    }

    public void setRule(Integer rule) {
        this.rule = rule;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rule='" + rule + '\'' +
                ", email='" + email + '\'' +
                ", qq='" + qq + '\'' +
                '}';
    }
}
