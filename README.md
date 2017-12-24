# Encheres

From [Enchere](https://github.com/Gueoff/Enchere). The goal is to make the project better.

## Instructions (récupération de celles du Readme original)

### Utiliser l'application 

L'application possède un serveur et un client.

Il faut d'abord lancer le serveur, donc le main de la classe Serveur. L'adresse IP se trouve dans la console.

Ensuite, dans la classe Client, il faut mettre la bonne adresse IP dans la variable "adresseServeur" (localhost par défaut, marche sur un seul PC). Il suffit ensuite de lancer le client.

La vente commence par défaut avec 2 clients. A partir de ce point, d'autres clients peuvent se rajouter et participeront à la vente du prochain objet.

## Ajout de la part de FELD-Team

Nous listerons ici toutes les améliorations que nous aurons effectuées.

* Amélioration de l'interface
* Ajout d'exceptions
* Refactoring du code pour organiser en packages et extraire des interfaces
* Documentation javadoc de l'ensemble
* Ajout d'un catalogue
* Changement de la gestion d'ordre des enchères (Lifo -> Fifo)