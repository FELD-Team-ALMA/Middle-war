package client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import api.IAcheteur;
import api.IServeurVente;
import config.ParamsConfig;
import serveur.Objet;
import ui.VueClient;
/**
 * Classe client, tout les utilisateurs de l'application sont des clients.
 *  Implémente l'interface acheteur.
 *  Ceci est une classe éxécutable.
 * @author Léo CASSIAU, Geoffrey DESBROSSES, Jean-Christophe GUERIN, Ugo MAHEY
 *
 */
public class Client extends UnicastRemoteObject implements IAcheteur {

	private static final long serialVersionUID = 1L;

	private String pseudo;
	private VueClient vue;
	private IServeurVente serveur;
	private Objet currentObjet;
	private EtatClient etat = EtatClient.ATTENTE;
	private Chrono chrono = new Chrono(ParamsConfig.CHRONO_TIME, this);
	private String[] catalogue;
	/**
	 * Constructeur du client. Récupère les paramètres autre que le pseudo via le serveur.
	 * @param pseudo : pseudo de l'acheteur
	 * @throws RemoteException
	 */
	public Client(String pseudo) throws RemoteException {
		super();
		this.chrono.start();
		this.pseudo = pseudo;
		this.serveur = connexionServeur();
		this.currentObjet = serveur.getObjet();
		this.catalogue = serveur.getCatalogue();
	}
	/**
	 * Methode static connectant le client au serveur.
	 * @return Vente :le serveur de vente auquel le client se connecte. Si connection fail : return null et signal l'erreur + print la stack.
	 */
	public static IServeurVente connexionServeur() {
		try {
			IServeurVente serveur = (IServeurVente) Naming.lookup("//" + ParamsConfig.ADRESSE_SERVEUR);
			System.out.println("Connexion au serveur " + ParamsConfig.ADRESSE_SERVEUR + " reussi.");
			return serveur;
		} catch (Exception e) {
			System.out.println("Connexion au serveur " + ParamsConfig.ADRESSE_SERVEUR + " impossible.");
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Inscrit le client à une vente
	 * @throws Exception : -RemoteException : si fail de connection au serveur
	 * 	-LoginPrisException : si le pseudo est déjà pris.
	 */
	public void inscription() throws Exception {
		if(!serveur.inscriptionAcheteur(pseudo, this)){
			this.vue.attente();
		}
	}
	/**
	 * Encherie sur le prix de l'objet courrant est le met à jours. Attention : si le prix proposé est
	 * en-dessous du prix actuel la fonction print un message et ne fait rien. 
	 * @param prix : Le prix proposé.
	 * @throws RemoteException : si fail de connection
	 * @throws Exception : en aucun cas.
	 */
	public void encherir(int prix) throws RemoteException, Exception {		
		if (prix <= this.currentObjet.getPrixCourant() && prix != -1) {
			System.out.println("Prix trop bas, ne soyez pas radin !");
		} else if (etat == EtatClient.RENCHERI) {
			chrono.arreter();
			vue.attente();
			etat = EtatClient.ATTENTE;
			serveur.rencherir(prix, this);
		}
	}

	@Override
	public void objetVendu(String gagnant) throws RemoteException {
		this.currentObjet = serveur.getObjet();
		this.catalogue = serveur.getCatalogue();
		this.vue.actualiserObjet();
		this.vue.reprise();
		
		if (gagnant != null) { //Fin de l'objet
			this.etat = EtatClient.ATTENTE;
		}else{ //inscription & objet suivant
			this.etat = EtatClient.RENCHERI;
			this.chrono.demarrer();
		}
	}

	@Override
	public void nouveauPrix(int prix, IAcheteur gagnant) throws RemoteException {
		try {
			this.currentObjet.setPrixCourant(prix);
			this.currentObjet.setGagnant(gagnant.getPseudo());
			this.vue.actualiserPrix();
			this.vue.reprise();
			this.etat = EtatClient.RENCHERI;
			this.chrono.demarrer();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void finEnchere() throws RemoteException {
		this.etat = EtatClient.TERMINE;
		System.exit(0);
	}
	
	/**
	 * Soumission d'un nouveau objet au serveur d'enchère
	 * @param nom : le nom de l'objet
	 * @param description : déscription de l'objet
	 * @param prix : le prix de départ de l'enchère sur l'objet
	 */
	public void nouvelleSoumission(String nom, String description, int prix) {
		Objet nouveau = new Objet(nom, description, prix);
		try {
			serveur.ajouterObjet(nouveau);
			System.out.println("Soumission de l'objet " + nom + " au serveur.");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	/**
	 *  Main de la classe client -> classe éxécutable.
	 * @param argv
	 */
	public static void main(String[] argv) {
		try {
			new VueClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * get l'objet courrant.
	 * @return Objet : objet courrant
	 */
	public Objet getCurrentObjet() {
		return currentObjet;
	}

	@Override
	public long getChrono() {
		return chrono.getTempsEcoule();
	}

	/**
	 * get le serveur
	 * @return Vente : le serveur
	 */
	public IServeurVente getServeur() {
		return serveur;
	}
	/**
	 * set le sereur
	 * @param serveur : le nouveau serveur
	 */
	public void setServeur(IServeurVente serveur) {
		this.serveur = serveur;
	}
	/**
	 * Set la vueClient (IHM)
	 * @param vueClient : la nouvelle vue
	 */
	public void setVue(VueClient vueClient) {
		vue = vueClient;
	}
	/**
	 * Get l'état
	 * @return EtatClient : l'état du client -> ATTENTE,RENCHERI,TERMINE
	 */
	public EtatClient getEtat() {
		return this.etat;
	}
	
	@Override
	public String getPseudo() throws RemoteException {
		return pseudo;
	}
	/**
	 * Met à jour le chrono dans la vue (IHM)
	 */
	public void updateChrono(){
		this.vue.updateChrono(this.getDisplayableTime());
	}
	/**
	 * récupère le catalogue
	 * @return String[] : le catalogue
	 * @throws RemoteException : si fail de connection
	 */
	public String[] getCatalogue() throws RemoteException {
		return this.catalogue;
	}
	
	/**
	 * Obtient un affichage pour le chrono
	 * TODO: check for arrondis
	 * commenté les heures parce que les enchères ne durent pas si longtemps dans l'état du programme
	 * @return String : la durée du chrono en String.
	 */	
	public String getDisplayableTime() {
		long timeLeft = this.chrono.getTempsFin() - this.chrono.getTempsEcoule();
		int totalSecondes = (int) (timeLeft / 1000);
		int secondes = 0;
		int minutes = 0;
		//int heures = 0;
		while (totalSecondes > 60 ) {
			minutes ++;
			totalSecondes = totalSecondes -60;
		}
		secondes = totalSecondes;
		/*
		while (minutes > 60) {
			heures++;
			minutes = minutes -60;
		}
		*/
		StringBuilder sb = new StringBuilder();
		//sb.append(Integer.toString(heures));
		//sb.append(" heure(s), ");
		sb.append(Integer.toString(minutes));
		sb.append(" minute(s) et ");
		sb.append(Integer.toString(secondes));
		sb.append(" seconde(s).");
		
		String stringChrono = sb.toString();
		return stringChrono;
	}

}
