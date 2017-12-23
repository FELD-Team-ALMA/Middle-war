package client;

import java.awt.Font;

/**
 * For easier config
 * 
 */
public class ParamsConfig {
	
	static int WINDOW_HEIGHT = 800;
	static int WINDOW_WIDTH = 600;
	static String WINDOW_TITLE = "Vente aux Enchères";
	static Font BUTTON_FONT = new Font("Serif", Font.PLAIN, 10);
	
	//temps du chrono en ms
	static int CHRONO_TIME = 1000000;
	
	//adresse server
	static String ADRESSE_SERVEUR = "localhost:8090/enchere";
	
	
	//textes pour les labels 
	static String CHOIX_PSEUDO = "Choisissez votre pseudo";
	static String SERVER = "Connexion au serveur : localhost:8090/enchere";
	
	static String LABEL_NOM_OBJET = "Nom de l'objet";
	static String LABEL_OBJET_COURANT = "Objet Courant";
	static String LABEL_DESCRIPTION = "Description";
	static String LABEL_PRIX_ACTUEL = "Prix Actuel";
	static String LABEL_PRIX_INITIAL = "Prix Initial";
	static String LABEL_GAGNANT = "Gagnant";

	static String CHRONO = "Temps restant"; 
	static String CATALOGUE = "Catalogue";
	
	
	//textes pour les boutons
	static String BUTTON_INSCRIPTION = "Inscription";
	
	static String BUTTON_ENCHERIR = "Encherir";
	static String BUTTON_SOUMETTRE_ENCHERE = "Soumettre une nouvelle enchère";
	static String BUTTON_SOUMETTRE_OBJET = "Soumettre un objet"; //"un objet"
	static String BUTTON_PASSER = "Passer";
	
	
	//textes pour les erreurs
	static String ERROR_TITLE = "Problème";
	static String ERROR_INSCRIPTION = "Inscription Impossible";
	static String ERROR_SOUMISSION_OBJET = "Impossible de soumettre cet objet.";
	static String ERROR_INSCRIPTION_LOGIN_PRIS = "Login déjà pris.";
	
	
	
	
	

}
