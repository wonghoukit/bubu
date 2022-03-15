package com.tencent.wxcloudrun;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class FastAutoGeneratorTest {

    public static void main(String[] args) {

        String dbUrl = "jdbc:mysql://sh-cynosdbmysql-grp-1wld9xli.sql.tencentcdb.com:25698/bubu";
        String username = "root";
        String pwd = "#Rm2vVrQ";
        String outputDir = "/Users/kit/Sites/mybatis-code";

        String[] table = {"users"};

        FastAutoGenerator.create(dbUrl, username, pwd)
                .globalConfig(builder -> {
                    builder.author("kit") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(outputDir); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.tencent.wxcloudrun") // 设置父包名
                            .entity("model")
                            .mapper("dao")
                            .xml("dao.xml");
                })
                .strategyConfig(builder -> {
                    builder.serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .build();

                    builder.controllerBuilder()
                            .enableRestStyle()
                            .build();

                    builder.entityBuilder()
                            .enableLombok()
                            .build();

                    builder.addInclude(table); // 设置需要生成的表名
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
