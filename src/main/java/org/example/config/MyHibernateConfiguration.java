package org.example.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;

//@Configuration(proxyBeanMethods = false)
public class MyHibernateConfiguration {

    @Autowired
    private SessionFactory sessionFactory;

    @Bean
    public HibernateTemplate hibernateTemplateConfigurer() {
        HibernateTemplate template = new HibernateTemplate();
        template.setSessionFactory(sessionFactory);
        return template;
    }
}
