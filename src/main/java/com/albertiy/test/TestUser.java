package com.albertiy.test;

import com.albertiy.pojo.Person;
import com.albertiy.elderhelper.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestUser {
    @Test
    public void test() throws IOException {
        /**
         *  1、获得 SqlSessionFactory
         *  2、获得 SqlSession
         *  3、调用在 mapper 文件中配置的 SQL 语句
         */
        String resource = "sqlMapConfig.xml";           // 定位核心配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);    // 创建 SqlSessionFactory

        SqlSession sqlSession = sqlSessionFactory.openSession();    // 获取到 SqlSession

        // 调用 mapper 中的方法：命名空间 + id
        List<User> userList = sqlSession.selectList(
                "com.albertiy.mapper.UserMapper.findAllFromUser");
        for (User u : userList) {
            System.out.println(u);
        }

        User me = sqlSession.selectOne(
                "com.albertiy.mapper.UserMapper.findUserByTel",
                "18936987963");
        System.out.println(me.toString());

        /*User newUser = new User("13812310963", "123698");
        sqlSession.insert(
                "com.albertiy.mapper.UserMapper.insertUserInfo",
                newUser);
        System.out.println(sqlSession.selectOne(
                "com.albertiy.mapper.UserMapper.findUserByTel",
                "13812310963").toString());*/
    }
}
