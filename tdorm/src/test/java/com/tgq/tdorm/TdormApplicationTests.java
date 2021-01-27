package com.tgq.tdorm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class TdormApplicationTests {

    @Autowired
    private DataSource dataSource;
    @Test
    void contextLoads() throws SQLException {
        String pass = "123456";
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashPass = bcryptPasswordEncoder.encode(pass);
        System.out.println(hashPass);
        boolean f = bcryptPasswordEncoder.matches("123",hashPass);
        System.out.println(f);
    }

}
