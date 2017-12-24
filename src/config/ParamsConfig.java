package config;

/**
 * Pour une configuration plus facile
 * @author Léo CASSIAU, Geoffrey DESBROSSES, Jean-Christophe GUERIN, Ugo MAHEY (original repository)
 * @author Montalvo ARAYA, Charles BEGAUDEAU, Marie DELAVERGNE, Charlène SERVANTIE (FELD-TEAM fork )
 */
public class ParamsConfig {

	public static final int WINDOW_HEIGHT = 800;
	public static final int WINDOW_WIDTH = 600;
	public static final String WINDOW_TITLE = "Vente aux Enchères";

	//temps du chrono en ms
	public static final int CHRONO_TIME = 30000;

	//adresse server
	public static final String ADRESSE_SERVEUR = "localhost:8090/enchere";


	//textes pour les labels
	public static final String CHOIX_PSEUDO = "Choisissez votre pseudo";
	public static final String SERVER = "Connexion au serveur : localhost:8090/enchere";

	public static final String LABEL_NOM_OBJET = "Nom de l'objet";
	public static final String LABEL_OBJET_COURANT = "Objet Courant";
	public static final String LABEL_DESCRIPTION = "Description";
	public static final String LABEL_PRIX_ACTUEL = "Prix Actuel";
	public static final String LABEL_PRIX_INITIAL = "Prix Initial";
	public static final String LABEL_GAGNANT = "Gagnant";

	public static final String CHRONO = "Temps restant";
	public static final String CATALOGUE = "Catalogue";
	
	//largeur du catalogue
	public static final int CATALOGUE_WIDTH = 300;


	//textes pour les boutons
	public static final String BUTTON_INSCRIPTION = "Inscription";

	public static final String BUTTON_ENCHERIR = "Encherir";
	public static final String BUTTON_SOUMETTRE_ENCHERE = "Soumettre une nouvelle enchère";
	public static final String BUTTON_SOUMETTRE_OBJET = "Soumettre un objet";
	public static final String BUTTON_PASSER = "Passer";


	//textes pour les erreurs
	public static final String ERROR = "Erreur";
	public static final String ERROR_TITLE = "Problème";
	public static final String ERROR_INSCRIPTION = "Inscription impossible.";
	public static final String ERROR_PRIX_NOT_INT = "Le prix doit être un entier.";
	public static final String ERROR_SOUMISSION_OBJET = "Impossible de soumettre cet objet.";
	public static final String ERROR_INSCRIPTION_LOGIN_PRIS = "Login déjà pris.";
	public static final String ERROR_CONNEXION = "Connexion au serveur impossible.";
	public static final String ERROR_PRIX_TROP_BAS = "Le prix doit être supérieur à l'enchère actuelle";
	public static final String ERROR_URL_SERVEUR_DEFAILLANTE = "L'URL du serveur n'est pas bonne.";
	public static final String ERROR_CONNEXION_IMPOSSIBLE = "La connexion au serveur est impossible.";
	public static final String ERROR_REMOTE = "Erreur de connexion.";
	public static final String ERROR_ECHEC_COMMUNICATION_SERVEUR = "Echec de communication avec le serveur.";
	public static final String ERROR_INTERRUPTION = "Interruption de l'enchère";

	public static final String FIN = "Fin des enchères";
	public static final String FIN_ENCHERES = "Plus d'objets à proposer";
}
