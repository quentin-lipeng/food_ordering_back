package org.quentin.security.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("org.quentin.security.mapper")
public class MybatisConfig {
}
