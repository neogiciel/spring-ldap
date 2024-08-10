package com.neogiciel.spring_ldap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.neogiciel.spring_ldap.util.Trace;
import com.neogiciel.spring_ldap.ldap.LdapClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {
    
    @Autowired
    LdapClient ldap;

    
    /*
     * search
    */
    //@GetMapping(value = "/search",produces="application/json") 
    @GetMapping("/search")
    public ResponseEntity<String> search()  {
        Trace.info("[HomeController] Appel Search");
        List<String> list = ldap.search("patrice");
        Trace.info("Size = "+list.size());

        for(int i=0;i<list.size();i++){
            Trace.info("get = "+list.get(i));
        }

        return ResponseEntity.ok("Search");
    }

    /*
     * auth
    */
    @GetMapping("/auth")
    public ResponseEntity<String> auth() {
        Trace.info("[HomeController] connect");
        ldap.authenticate("test","test");
        return ResponseEntity.ok("auth");   
    }


    /*
     * add
    */
    @GetMapping("/add")
    public ResponseEntity<String> add() {
        Trace.info("[HomeController] add");
        ldap.create("test", "test");
        return ResponseEntity.ok("add");   
    }


}
