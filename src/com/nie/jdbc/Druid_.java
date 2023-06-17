package com.nie.jdbc;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author Akihi
 * @date 2023/6/17 4:25 PM
 */
public class Druid_ {
    @Test
    public void testDruid() throws Exception {
        //1. 加入 Druid jar 包
//2. 加入 配置文件 druid.properties , 将该文件拷贝项目的 src 目录 //3. 创建 Properties 对象, 读取配置文件
        Properties properties = new Properties();
        properties.load(new FileInputStream("./src/druid.properties"));
        //4. 创建一个指定参数的数据库连接池, Druid 连接池 DataSource dataSource =
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {
            Connection connection = dataSource.getConnection();
            System.out.println(connection.getClass());
            System.out.println("连接成功!");
            connection.close();
        }
        long end = System.currentTimeMillis();
//druid 连接池 操作 5000 耗时=412
        System.out.println("druid 连接池 操作 500000 耗时=" + (end - start));//539
    }
}
