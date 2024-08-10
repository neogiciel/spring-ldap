package com.neogiciel.spring_ldap.ldap;

import com.neogiciel.spring_ldap.ldap.LdapClient;
import com.neogiciel.spring_ldap.util.Trace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@PropertySource("classpath:application.properties")
@Profile("default")
@EnableLdapRepositories(basePackages = "com.baeldung.ldap.**")
public class LdapConfig {

    @Autowired
    private Environment env;

    @Bean
    public LdapContextSource contextSource() {
        Trace.info("Ldap contaxtSource");
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("ldap://localhost:389");
        contextSource.setUserDn("cn=admin,dc=example,dc=in");
        contextSource.setBase("dc=example,dc=in");
        contextSource.setPassword("password");
        contextSource.afterPropertiesSet();
        return contextSource;
 


        //contextSource.setUrl(env.getRequiredProperty("ldap.url"));
        //contextSource.setBase(env.getRequiredProperty("ldap.partitionSuffix"));
        //contextSource.setUserDn(env.getRequiredProperty("ldap.principal"));
        //contextSource.setPassword(env.getRequiredProperty("ldap.password"));
        //return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }

    @Bean
    public LdapClient ldapClient() {
        return new LdapClient();
    }

}