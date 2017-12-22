package client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import serveur.Objet;
import serveur.Vente;

public class Client extends UnicastRemoteObject implements Acheteur {

	private static final long serialVersionUID = 1L;

	private String pseudo;
	private VueClient vue;
	private Vente serveur;
	private Objet currentObjet;
	private EtatClient etat = EtatClient.ATTENTE;
	private Chrono chrono = new Chrono(ParamsConfig.CHRONO_TIME, this);
	private String[] catalogue;

	public Client(String pseudo) throws RemoteException {
		super();
		this.chrono.start();
		this.pseudo = pseudo;
		this.serveur = connexionServeur();
		this.currentObjet = serveur.getObjet();
		this.catalogue = serveur.getCatalogue();
	}

	public static Vente connexionServeur() {
		try {
			Vente serveur = (Vente) Naming.lookup("//" + ParamsConfig.ADRESSE_SERVEUR);
			System.out.println("Connexion au serveur " + ParamsConfig.ADRESSE_SERVEUR + " reussi.");
			return serveur;
		} catch (Exception e) {
			System.out.println("Connexion au serveur " + ParamsConfig.ADRESSE_SERVEUR + " impossible.");
			e.printStackTrace();
			return null;
		}
	}

	public void inscription() throws Exception {
		if(!serveur.inscriptionAcheteur(pseudo, this)){
			this.vue.attente();
		}
	}

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
	public void nouveauPrix(int prix, Acheteur gagnant) throws RemoteException {
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
	
	public void nouvelleSoumission(String nom, String description, int prix) {
		Objet nouveau = new Objet(nom, description, prix);
		try {
			serveur.ajouterObjet(nouveau);
			System.out.println("Soumission de l'objet " + nom + " au serveur.");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] argv) {
		try {
			new VueClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// getters and setters
	public Objet getCurrentObjet() {
		return currentObjet;
	}

	@Override
	public long getChrono() {
		return chrono.getTempsEcoule();
	}

	public Vente getServeur() {
		return serveur;
	}

	public void setServeur(Vente serveur) {
		this.serveur = serveur;
	}

	public void setVue(VueClient vueClient) {
		vue = vueClient;
	}

	public EtatClient getEtat() {
		return this.etat;
	}
	
	@Override
	public String getPseudo() throws RemoteException {
		return pseudo;
	}

	public void updateChrono(){
		this.vue.updateChrono(this.getDisplayableTime());
	}
	
	public String[] getCatalogue() throws RemoteException {
		return this.catalogue;
	}
	
	/**
	 * Obtient un affichage pour le chrono
	 * TODO: check for arrondis
	 * commenté les heures parce que les enchères ne durent pas si longtemps dans l'état du programme
	 * @return
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
