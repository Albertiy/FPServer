package com.albertiy.elderhelper.pojo;

import java.io.Serializable;

public class User implements Serializable {
    public User() {
        this.u_pic = null;
    }

    public User(String u_tel, String u_password) {
        this.u_tel = u_tel;
        this.u_password = u_password;
        this.u_name = u_tel;
        this.u_pic = null;
    }

    public User(String u_name, String u_tel, String u_password) {
        this.u_name = u_name;
        this.u_tel = u_tel;
        this.u_password = u_password;
        this.u_pic = null;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_tel() {
        return u_tel;
    }

    public void setU_tel(String u_tel) {
        this.u_tel = u_tel;
    }

    public String getU_password() {
        return u_password;
    }

    public void setU_password(String u_password) {
        this.u_password = u_password;
    }

    public byte[] getU_pic() {
        return u_pic;
    }

    public void setU_pic(byte[] u_pic) {
        this.u_pic = u_pic;
    }

    String u_name;
    String u_tel;
    String u_password;
    byte[] u_pic;

    @Override
    public String toString() {
        String s = "User{" +
                "u_name='" + u_name + '\'' +
                ", u_tel='" + u_tel + '\'' +
                ", u_password=" + u_password;
        if (u_pic != null && u_pic.length > 0)
            s += ", u_pic=" + u_pic.length;
        return s + '}';
    }

}
