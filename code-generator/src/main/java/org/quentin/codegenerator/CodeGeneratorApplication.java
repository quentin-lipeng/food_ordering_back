package org.quentin.codegenerator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Types;
import java.util.Collections;

@SpringBootApplication
public class CodeGeneratorApplication {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;


    public static void main(String[] args) {
        SpringApplication.run(CodeGeneratorApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner() {
        return (arg) -> {
            FastAutoGenerator.create(url, username, password)
                    .globalConfig(builder -> {
                        builder.author("quentin") // 设置作者
                                .fileOverride() // 覆盖已生成文件
                                .outputDir("/home/quinlan/code/generator"); // 指定输出目录
                    })
                    .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                        int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                        if (typeCode == Types.SMALLINT) {
                            // 自定义类型转换
                            return DbColumnType.INTEGER;
                        }
                        return typeRegistry.getColumnType(metaInfo);

                    }))
                    .packageConfig(builder -> {
                        builder.parent("org.quentin.security") // 设置父包名
                                .moduleName("web") // 设置父包模块名
                                .pathInfo(Collections.singletonMap(OutputFile.xml, "/home/quinlan/code/generator")); // 设置mapperXml生成路径
                    })
                    .strategyConfig(builder -> {
                        builder.addInclude("food") // 设置需要生成的表名
                                .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                    })
                    .execute();
        };
    }

}
