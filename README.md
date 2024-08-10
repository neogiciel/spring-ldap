﻿## <h1>Hadoop : Apache Hive</h1>
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
Copie du fichier dans le pod:<br/>
```
docker cp breweries.csv namenode:breweries.csv
```
Accès au shell du pod:<br/>
```
docker exec -it namenode bash
```
Création du dossier dans HDFS:<br/>
```
hdfs dfs -mkdir -p /data/openbeer/breweries
```
Transfert du fichier dans HDFS:<br/>
```
hdfs dfs -put breweries.csv /data/openbeer/breweries/breweries.csv
```
## Quick Start Hive
***
Démmarrer le serveur Hive
```
 docker exec -it hive-server bash

  hiveserver2
```
Utiliser les lignes de commande pour vous connecter au serveur<br>
```
beeline -u jdbc:hive2://localhost:10000 -n root
```
Voir la base de données
```
show databases;
+----------------+
| database_name  |
+----------------+
| default        |
+----------------+
1 row selected (0.335 seconds)
```
Créer une base de données
```
create database openbeer;
  use openbeer;
```
Créer la Table associé qui va permettre de définir le modéle de données
```
CREATE EXTERNAL TABLE IF NOT EXISTS breweries(
    NUM INT,
    NAME CHAR(100),
    CITY CHAR(100),
    STATE CHAR(100),
    ID INT )
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
location '/data/openbeer/breweries';
```
Nous pouvons maintenant faire la Requête suivante

```
select name from breweries limit 10;
+----------------------------------------------------+
|                        name                        |
+----------------------------------------------------+
| name                                               |
| NorthGate Brewing                                  |
| Against the Grain Brewery                          |
| Jack's Abby Craft Lagers                           |
| Mike Hess Brewing Company                          |
| Fort Point Beer Company                            |
| COAST Brewing Company                              |
| Great Divide Brewing Company                       |
| Tapistry Brewing                                   |
| Big Lake Brewing                                   |
+----------------------------------------------------+
10 rows selected (0.113 seconds
```


## Application Spring-Boot
***
Vous trouverez ici le code source d'une application Spring-Boot permettant d'éffectuer des requêtes sur Apache Hive
```
mvn clean
mvn spring-boot:run
```


