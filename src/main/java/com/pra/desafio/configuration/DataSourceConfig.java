package com.pra.desafio.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


/**
 *
 */


public class DataSourceConfig {




    @Bean

    /*
        Neste ponto devemos implenatr a busca das credenciais do BD  de forma  segura :
         Ex  Openshift  Secret , Cyberark etc
         Colocar credencias de banco de dados no codigo fonte é uma falha grave de segurança

     */

    public DataSource dataSource() {
        var dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/estudos");
        // dataSource.setUsername("root");
        // dataSource.setPassword("Marina2009");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }


}
