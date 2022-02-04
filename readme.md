# MyResto

## Accès au site

Pour ce rendre sur le site merci de cliquer sur le lien : [MyResto](http://3.213.147.10:5000/myresto)


---
## Projet

Le restaurant MyResto souhaitent créer une application web. ayant comme fonctionnalitées :

* Aux visiteurs de voir la carte du restaurant.
* Aux clients connecter de passer une commande.
* Au gérant d'analyser les commandes en cours.


---
## Partie technique du projet

Java : version 11.0.0
Base de donnée: MySQL version : 8.0.27 

## Déploiement de l'application sur AWS

Le déploiement est réaliser à l'aide d'une pipeline jenkins.
Celle-ci permet à l'aide de Terraform de créer notre infrastructure sur AWS ou de la supprimer
De plus, Ansible permet de gérer l'installation de dépences sur notre machine et d'éxcuter la commandes pour démarrer la bdd et l'application spring
---
