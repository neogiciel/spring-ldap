## <h1>Spring-Boot: Open LDAP</h1>
***
<table><tr>
  <td><img src="https://github.com/user-attachments/assets/d23a1ecc-9a2d-4b51-bba8-98768fa50144" alt="drawing" height="240px"/></td>
</tr></table>

## Informations Générales!
***
Création d'une application Spring-Boot permettant d'interoger un LDAP.<br/>
Avec la création, la modification et l'authentification d'utilisateur.<br/>

## Déploiement Docker
***
Déploiement du LDAP et de l'interface PHPMyLDAP:<br/>
```
docker-compose up
```
Pour accèder à l'interafec PHPMyLDAP:<br>
* http://localhost:8090
Pour se connecter au LDAP
```
login: cn=admin,dc=example,dc=in
mot de passe: password
```
 
## CLI Ligne de commande d'utilisation du LDAP
Access au pod :<br/>
```
docker exec -it ldap /bin/bash
```
Creation d'une organisation:<br/>
```
ldapadd -x -w password -D "cn=admin,dc=example,dc=in" << EOF
dn: ou=user,dc=example,dc=in
objectClass: organizationalUnit
ou: user
EOF
```
Creation d'un utilisateur:<br/>
```
ldapadd -x -w password -D "cn=admin,dc=example,dc=in" << EOF
dn: cn=amrutha,ou=user,dc=example,dc=in
objectClass: person
cn: amrutha
sn: Amrutha
userPassword: test
EOF
```
Creation d'un groupe:<br/>
```
ldapadd -x -w password -D "cn=admin,dc=example,dc=in" << EOF
dn: cn=appdev-team,dc=example,dc=in
objectClass: top
objectClass: groupOfNames
cn: appdev-team
description: App Development Team
member: cn=amrutha,ou=user,dc=example,dc=in
EOF
```
Recherche d'un utilisateur:<br/>
```
ldapsearch -x -b dc=example,dc=in -D "cn=admin,dc=example,dc=in" -w password -s sub "objectclass=*"
```

## Application Spring-Boot
***
Application Spring-boot
```
mvn clean
mvn spring-boot:run
```
Accéder aux différentes ressources:<br>
* Authentification
* http://localhost:8090/auth
* Ajouter un utilisateur
* http://localhost:8090/add 
* Rechercher un utilisateur
* http://localhost:8090/search 



