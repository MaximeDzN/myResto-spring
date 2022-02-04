# MyResto

## Fonctionalités

Application web et mobile permettant :<br>

* Aux visiteurs de voir la carte
* Aux clients de commander en ligne
* Au gérant d'analyser les commandes en cours et faire un prévisionnel de commandes

## Release V0

* Création de l'application avec les fonctionnalités de base

## Release V1

* Permettre aux utilisateurs non authentifiés de voir la carte
* Aux utilisateurs authentifiés de commander
* Permettre au gérant d'analyser son volume d'affaire mensuel

## Architecture
```
+  ansible
      |
      +  
+  Docker
+  Jenkinsfile
+  mvnw
+  mvnw.cmd
+  pom.xml
+  readme.md
+  src
+  Terraform

```
## Déploiement

#### Pour déployer l'instance ec2 :
> 1. Se connecter sur [Pipeline](http://146.59.154.110:8080/job/MyResto/)
> 2. identifiant : allan
> 3. mot de passe : 5rR1F4ZJw1wBVVyIqwph
> 4. aller dans build avec des paramètres
> 5. Pour déployer cliquer sur build sans cocher la case destroy
> 6. Attendre que toutes les taches se finissent

Pour supprimer l'instance ec2
1. Se connecter sur [Pipeline](http://146.59.154.110:8080/job/MyResto/)
2. identifiant : allan
3. mot de passe : 5rR1F4ZJw1wBVVyIqwph
4. aller dans build avec des paramètres
5. cocher la case 'destroy'
6. cliquer sur build
7. attendre que toutes les taches se finissent

Deux choix sont alors possibles.

* Accéder à l'application avec le compte Restaurateur :

  > Identifiant : gestion@myresto.com
  > <br/>Mot de passe : myresto

* Accéder à l'application avec le compte Client :

  > Identifiant : client1@gmail.com
  > <br/>Mot de passe : client

## Convention de nommage

1. <b/>Package:</b>
   <br/>Utiliser la convention <u/>dotcase</u>.
   <br/>Examples:
    * com.sun.eng
    * com.apple.quicktime.v2
    * edu.cmu.cs.bovik.cheese
      <br/>
2. <b/>Attribut:</b>
   <br/>Utiliser la convention <u/>camelCase</u>.
   <br/>Examples:
    * int     iDea;
    * char     n;
    * float    myWidth;
3. <b/>Méthode:</b>
   <br/>Utiliser la convention <u/>camelCase</u>.
   <br/>Examples:
    * run();
    * runFast();
    * getBackground();
4. <b/>Class:</b>
   <br/>Utiliser la convention <u/>PascalCase</u>.
   <br/>Examples:
    * class Raster;
    * class ImageSprite;
5. <b/>Interface:</b>
   <br/>Utiliser la convention <u/>IPascalCase</u>.
   <br/>Examples:
    * interface IRasterDelegate;
    * interface IStoring;

## Tests unitaires TODO

Pour lancer les tests unitaires

> mvn test
