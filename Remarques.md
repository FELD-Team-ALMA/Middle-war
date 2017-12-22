# Remarques 
* Il n'y a pas d'indication de la déconnexion d'un client sur le serveur (et ça met le système en attente du coup si il a pas répondu à l'enchère en cours)
* L'IHM est peu claire
* Les exceptions ne sont pas très explicites
* Le chrono est en secondes, c'est pas forcément très clair si ça dure plus que 3 minutes.
* Y a des constantes magiques, c'est un peu dommage
* Les entrées sont pas protégées, rentrer un string en prix casse l'appli
