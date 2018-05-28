package com.albertiy.controller;

import com.albertiy.elderhelper.pojo.User;
import com.sun.istack.internal.NotNull;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;

@Controller
public class MainController {

    @RequestMapping("/GetData")
    @ResponseBody
    public void GetDataFromAndroid(Map<String, String> map) throws UnsupportedEncodingException {
        String sb = "";
        if (!map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb = sb.concat("&");
                sb = sb.concat(entry.getKey() + "=");
                sb = sb.concat(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            sb.replaceFirst("&", "");
        }
        System.out.println("get info from Android: " + sb);
        return;
    }

    @RequestMapping("/login")
    @ResponseBody
    public void Login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String sb = "\nLogin:";
        try {
            sb += "\n类型：" + httpServletRequest.getContentType();
            sb += "\n长度： " + httpServletRequest.getContentLength();
            if (httpServletRequest.getContentLength() > 0) {

                InputStream inputStream = httpServletRequest.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                User user = (User) objectInputStream.readObject();
                if (user != null) {
                    sb += "\n" + user.toString();
                    User me = checkFromDB(user);
                    if (me != null) {  //登陆成功
                        System.out.println("登陆成功");
                        OutputStream outStrm = httpServletResponse.getOutputStream();
                        ObjectOutputStream objOutputStrm = new ObjectOutputStream(outStrm);
                        objOutputStrm.writeObject(me);
                        objOutputStrm.flush();
                        objOutputStrm.close();
                    } else {  //登陆失败
                        System.out.println("登陆失败");
                    }
                } else
                    sb += "\n没有User对象";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("get info from Android: " + sb);
        return;
    }

    private User checkFromDB(@NotNull User user) {
        try {
            String resource = "sqlMapConfig.xml";           // 定位核心配置文件
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);    // 创建 SqlSessionFactory
            SqlSession sqlSession = sqlSessionFactory.openSession();
            User me = sqlSession.selectOne(
                    "com.albertiy.mapper.UserMapper.findUserByTel",
                    user.getU_tel());
            if (me != null)
                if (me.getU_password().equals(user.getU_password())) {
                    return me;
                } else
                    System.out.println("密码不相等："+me.getU_password()+" != "+user.getU_password());
            else
                System.out.println("没有这个用户："+user.getU_tel());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
        return null;
    }

    @RequestMapping("/register")
    @ResponseBody
    public void Register(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String sb = "\nRegister:";
        try {
            sb += "\n类型：" + httpServletRequest.getContentType();
            sb += "\n长度： " + httpServletRequest.getContentLength();
            if (httpServletRequest.getContentLength() > 0) {
                InputStream inputStream = httpServletRequest.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                User user = (User) objectInputStream.readObject();
                if (user != null) {
                    sb += "\n" + user.toString();
                    OutputStream outStrm = httpServletResponse.getOutputStream();
                    ObjectOutputStream objOutputStrm = new ObjectOutputStream(outStrm);
                    if (registerToDB(user)) {  //注册成功
                        System.out.println("注册成功");
                        objOutputStrm.writeObject(new Boolean(true));
                    } else {  //注册失败
                        System.out.println("注册失败");
                        objOutputStrm.writeObject(new Boolean(false));
                    }
                    objOutputStrm.flush();
                    objOutputStrm.close();
                } else
                    sb += "\n没有User对象";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("get info from Android: " + sb);
        return;
    }

    private boolean registerToDB(User user) {
        try {
            String resource = "sqlMapConfig.xml";           // 定位核心配置文件
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);    // 创建 SqlSessionFactory
            SqlSession sqlSession = sqlSessionFactory.openSession();
            sqlSession.insert("com.albertiy.mapper.UserMapper.insertUserInfo", user);
            sqlSession.commit();
            sqlSession.close();
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping("/updatepic")
    @ResponseBody
    public void UpdatePic(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String sb = "\nUpdatePic:";
        try {
            sb += "\n类型：" + httpServletRequest.getContentType();
            sb += "\n长度： " + httpServletRequest.getContentLength();
            if (httpServletRequest.getContentLength() > 0) {
                InputStream inputStream = httpServletRequest.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                User user = (User) objectInputStream.readObject();
                if (user != null) {
                    sb += "\n" + user.toString();
                    OutputStream outStrm = httpServletResponse.getOutputStream();
                    ObjectOutputStream objOutputStrm = new ObjectOutputStream(outStrm);
                    if (updateUserPic(user)) {
                        System.out.println("更新图片成功");
                        objOutputStrm.writeObject(new Boolean(true));
                    } else {  //登陆失败
                        System.out.println("更新图片失败");
                        objOutputStrm.writeObject(new Boolean(false));
                    }
                    objOutputStrm.flush();
                    objOutputStrm.close();
                } else
                    sb += "\n没有User对象";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("get info from Android: " + sb);
        return;
    }

    private boolean updateUserPic(User user) {
        try {
            String resource = "sqlMapConfig.xml";           // 定位核心配置文件
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);    // 创建 SqlSessionFactory
            SqlSession sqlSession = sqlSessionFactory.openSession();
            sqlSession.insert("com.albertiy.mapper.UserMapper.updateUserPic", user);
            sqlSession.commit();
            sqlSession.close();
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping("/updateinfo")
    @ResponseBody
    public void UpdateInfo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String sb = "\nUpdateInfo:";
        try {
            sb += "\n类型：" + httpServletRequest.getContentType();
            sb += "\n长度： " + httpServletRequest.getContentLength();
            if (httpServletRequest.getContentLength() > 0) {
                InputStream inputStream = httpServletRequest.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                User user = (User) objectInputStream.readObject();
                if (user != null) {
                    sb += "\n" + user.toString();
                    OutputStream outStrm = httpServletResponse.getOutputStream();
                    ObjectOutputStream objOutputStrm = new ObjectOutputStream(outStrm);
                    if (updateUserInfo(user)) {
                        System.out.println("更新信息成功");
                        objOutputStrm.writeObject(new Boolean(true));
                    } else {  //登陆失败
                        System.out.println("更新信息失败");
                        objOutputStrm.writeObject(new Boolean(false));
                    }
                    objOutputStrm.flush();
                    objOutputStrm.close();
                } else
                    sb += "\n没有User对象";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("get info from Android: " + sb);
        return;
    }

    private boolean updateUserInfo(User user) {
        try {
            String resource = "sqlMapConfig.xml";           // 定位核心配置文件
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);    // 创建 SqlSessionFactory
            SqlSession sqlSession = sqlSessionFactory.openSession();
            sqlSession.insert("com.albertiy.mapper.UserMapper.updateUserInfo", user);
            sqlSession.commit();
            sqlSession.close();
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
