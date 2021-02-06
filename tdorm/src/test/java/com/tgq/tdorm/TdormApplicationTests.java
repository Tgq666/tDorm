package com.tgq.tdorm;

import com.tgq.tdorm.service.redis.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

@SpringBootTest
class TdormApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisService redisService;

    @Test
    void contextLoads() throws SQLException {
        redisService.set("111", 111);
        Object o = redisService.get("111");
        redisService.expire("111",60);
        System.out.println(o);
    }

}
