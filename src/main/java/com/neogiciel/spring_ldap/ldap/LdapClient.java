package com.neogiciel.spring_ldap.ldap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Component;

import javax.naming.Name;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@Component
public class LdapClient {

    //@Autowired
   // private Environment env;

    @Autowired
    private ContextSource contextSource;

    @Autowired
    private LdapTemplate ldapTemplate;

    /*
     * auth
     */
    public void authenticate(final String username, final String password) {
        contextSource.getContext("cn=" + username + ",ou=user," + "dc=example,dc=in", password);
    }

    /*
     * search
     */
    //public List<String> search(final String username) {
    public List<String> search(final String username) {
        return ldapTemplate.search(
          "ou=user",
          "cn=" + username,
          (AttributesMapper<String>) attrs -> (String) attrs.get("cn").get().toString()
          +","+(String) attrs.get("sn").get().toString()
          +","+(String) attrs.get("description").get().toString()
          +","+(String) attrs.get("telephoneNumber").get().toString()
          );

    }

    /*
     * create
     */
    public void create(final String username, final String password) {
        Name dn = LdapNameBuilder
          .newInstance()
          .add("ou", "user")
          .add("cn", username)
          .build();
        DirContextAdapter context = new DirContextAdapter(dn);

        context.setAttributeValues("objectclass", new String[]{"top", "person", "organizationalPerson", "inetOrgPerson"});
        context.setAttributeValue("cn", username);
        context.setAttributeValue("sn", username);
        context.setAttributeValue("userPassword", digestSHA(password));

        ldapTemplate.bind(context);
    }

    /*
     * modify
     */
    public void modify(final String username, final String password) {
        Name dn = LdapNameBuilder
          .newInstance()
          .add("ou", "user")
          .add("cn", username)
          .build();
        DirContextOperations context = ldapTemplate.lookupContext(dn);

        context.setAttributeValues("objectclass", new String[]{"top", "person", "organizationalPerson", "inetOrgPerson"});
        context.setAttributeValue("cn", username);
        context.setAttributeValue("sn", username);
        context.setAttributeValue("userPassword", digestSHA(password));

        ldapTemplate.modifyAttributes(context);
    }

    /*
     * digestSHA
     */
    private String digestSHA(final String password) {
        String base64;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            digest.update(password.getBytes());
            base64 = Base64
              .getEncoder()
              .encodeToString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return "{SHA}" + base64;
    }
}