package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration(proxyBeanMethods = false)
public class MyHibernateConfiguration {

  /*  @Autowired
    private SessionFactory sessionFactory;

    @Bean
    public HibernateTemplate hibernateTemplateConfigurer() {
        HibernateTemplate template = new HibernateTemplate();
        template.setSessionFactory(sessionFactory);
        return template;
    }*/
}
