package com.nie.jdbc;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Akihi
 * @date 2023/6/17 3:52 PM
 */
public class C3P0_ {
    @Test
    public void testC3P0_01() throws IOException, SQLException {
        //1. 创建一个数据源对象
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource(); //2. 通过配置文件 mysql.properties 获取相关连接的信息
        Properties properties = new Properties();
        properties.load(new FileInputStream("./src/mysql.properties"));
//读取相关的属性值
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driver = properties.getProperty("driver");
        //给数据源 comboPooledDataSource 设置相关的参数
//注意：连接管理是由 comboPooledDataSource 来管理 comboPooledDataSource.setDriverClass(driver);
        comboPooledDataSource.setJdbcUrl(url);
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(password);
        //设置初始化连接数
        comboPooledDataSource.setInitialPoolSize(10);
//最大连接数
        comboPooledDataSource.setMaxPoolSize(50);
//测试连接池的效率, 测试对 mysql 5000 次操作
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            Connection connection = comboPooledDataSource.getConnection(); //这个方法就是从 DataSource 接口实现的
            System.out.println("连接 OK");
            connection.close();
        }
        long end = System.currentTimeMillis();
//c3p0 5000 连接 mysql 耗时=391
        System.out.println("c3p0 5000 连接 mysql 耗时=" + (end - start));
    }

    @Test
    public void test02() throws SQLException {
//        c3p0-config.xml 配置文件名不可更改
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource("akihi");
        //测试 5000 次连接 mysql
        long start = System.currentTimeMillis();
        System.out.println("开始执行....");
        for (int i = 0; i < 500000; i++) {
            Connection connection = comboPooledDataSource.getConnection();
            System.out.println("连接 OK~");
            connection.close();
        }
        long end = System.currentTimeMillis();
        //c3p0 的第二种方式 耗时=413
        System.out.println("c3p0 的第二种方式(500000) 耗时=" + (end - start));//1917
    }

}

