package com.chen.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Scanner;

/**
 * @author: blkcor
 * @DATE: 2022/5/23  13:09
 * @PROJECT_NAME: yeb
 * @since: jdk1.8
 */
public class CodeGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入模块名:");
        String module = scanner.next();
        System.out.println("请输入作者名:");
        String author = scanner.next();
        AutoGenerator autoGenerator = new AutoGenerator();
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("123456");
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/yeb?useUnicode=true&characterEncoding=UTF-8");
        autoGenerator.setDataSource(dataSourceConfig);
        com.baomidou.mybatisplus.generator.config.GlobalConfig globalConfig = new GlobalConfig();
        // 代码生成后打开目录
        globalConfig.setOpen(false);
        globalConfig.setOutputDir(System.getProperty("user.dir") + "\\" + module + "\\src\\main\\java");
        globalConfig.setAuthor(author);
        // 开启Swaggers模式
        globalConfig.setSwagger2(true);
        globalConfig.setServiceName("%sService");
        autoGenerator.setGlobalConfig(globalConfig);
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.chen.server");
        packageConfig.setEntity("pojo");
        packageConfig.setMapper("mapper");
        packageConfig.setController("controller");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        autoGenerator.setPackageInfo(packageConfig);
        StrategyConfig strategyConfig = new StrategyConfig();
        // 去表前缀 t,根据实际情况填写
        strategyConfig.setTablePrefix("t" + "_");
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.no_change);

        autoGenerator.setStrategy(strategyConfig);

        autoGenerator.execute();
    }

}
