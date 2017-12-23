package serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import api.IAcheteur;
import api.IServeurVente;
import exceptions.LoginPrisException;
/**
 * Classe VenteImpl. Les VenteImpl servent de serveurs  pour l'application.
 * 
 * Extends : UnicastRemoteObject
 * Implémente : Vente
 * 
 * @author Léo CASSIAU, Geoffrey DESBROSSES, Jean-Christophe GUERIN, Ugo MAHEY (original repository)
 * @author Montalvo ARAYA, Charles BEGAUDEAU, Marie DELAVERGNE, Charlène SERVANTIE (FELD-TEAM fork )
 */
public class ServeurVente extends UnicastRemoteObject implements IServeurVente{

	private static final long serialVersionUID = 1L;
	private List<IAcheteur> listeAcheteurs = new ArrayList<IAcheteur>();
	private List<IAcheteur> fileAttente = new ArrayList<IAcheteur>();
	private Map<IAcheteur, Integer> enchereTemp = new HashMap<IAcheteur, Integer>();
	private Objet objetCourant;
	private List<Objet> listeObjets;
	private IAcheteur acheteurCourant;
	private EtatVente etatVente;
	private final int clientMin = 2;

	/**
	 * Constructeur de la classe VenteImpl.
	 * 
	 * Ce constructeur crée une VentImpl sans liste d'objets en état ATTENTE
	 * @throws RemoteException : échec de connexion
	 */
	protected ServeurVente() throws RemoteException {
		super();
		this.etatVente = EtatVente.ATTENTE;
	}
	/**
	 * Constructeur de la classe VenteImpl
	 * 
	 * Ce constructeur crée une VentImpl avec liste d'objets passée en paramètre et en état ATTENTE 
	 * @param listeObjets : la liste d'objet de départ.
	 * @throws RemoteException : échec de connexion
	 */
	public ServeurVente(List<Objet> listeObjets) throws RemoteException {
		super();
		this.listeObjets = listeObjets;
		this.objetCourant = listeObjets.get(0);
		listeObjets.remove(0);
		this.etatVente = EtatVente.ATTENTE;
	}


	@Override
	public synchronized boolean inscriptionAcheteur(String login, IAcheteur acheteur) throws LoginPrisException, RemoteException{
		for(IAcheteur each : listeAcheteurs){
			if(each.getPseudo().equals(login) || each.getPseudo().equals(acheteur.getPseudo())){
				throw new LoginPrisException("Login deja pris");
			}
		}			
		this.fileAttente.add(acheteur);

		if(this.fileAttente.size() >= clientMin && this.etatVente == EtatVente.ATTENTE){
			this.etatVente = EtatVente.ENCHERISSEMENT;	

			for(IAcheteur each : this.fileAttente){
				this.listeAcheteurs.add(each);
				each.objetVendu(null);
			}
			this.fileAttente.clear();
			return true;
		}	
		return false;
	}


	@Override
	public synchronized int rencherir(int nouveauPrix, IAcheteur acheteur) throws Exception{
		this.enchereTemp.put(acheteur, nouveauPrix);
		System.out.println(this.enchereTemp.size()+"/"+this.listeAcheteurs.size());

		//On a recu toutes les encheres
		if(this.enchereTemp.size() == this.listeAcheteurs.size()){
			if(enchereFinie()){
				return objetSuivant();
			}
			else{
				actualiserObjet();
				//On renvoie le resultat du tour
				for(IAcheteur each : this.listeAcheteurs){
					each.nouveauPrix(this.objetCourant.getPrixCourant(), this.acheteurCourant);
				}
			}
		}
		return objetCourant.getPrixCourant();
	}


	/**
	 * Permet de passer à l'objet suivant avec les bons acheteurs et bons objets.
	 * @throws RemoteException : échec de connexion
	 * @throws InterruptedException : Si ne se termine pas
	 */
	public int objetSuivant() throws RemoteException, InterruptedException{
		this.enchereTemp.clear();
		this.etatVente = EtatVente.ATTENTE;

		if(acheteurCourant != null){
			this.objetCourant.setDisponible(false);
			this.objetCourant.setGagnant(this.acheteurCourant.getPseudo());

			//Envoie des resultats finaux pour l'objet courant
			for(IAcheteur each : this.listeAcheteurs){
				each.objetVendu(this.acheteurCourant.getPseudo());

			}
		}		

		Thread.sleep(5000);
		this.acheteurCourant = null;
		this.listeAcheteurs.addAll(this.fileAttente);
		this.fileAttente.clear();

		//Il y a encore des objets à vendre
		if(!this.listeObjets.isEmpty()){
			this.objetCourant = this.listeObjets.get(0);
			this.listeObjets.remove(0);
			this.objetCourant.setGagnant("");
			this.etatVente = EtatVente.ENCHERISSEMENT;
			for(IAcheteur each : this.listeAcheteurs){
				each.objetVendu(null);
			}
		} else{
			this.etatVente = EtatVente.TERMINE;
			for(IAcheteur each : this.listeAcheteurs){
				each.finEnchere();
			}
			return 0;
		}
		return this.objetCourant.getPrixBase();
	}



	/**
	 * Methode utilitaire permettant d'actualiser le prix de l'objet et le gagnant selon les enchères reçues.
	 * @throws RemoteException : échec de connexion
	 */
	public void actualiserObjet() throws RemoteException{
		Set<IAcheteur> cles = this.enchereTemp.keySet();
		Iterator<IAcheteur> it = cles.iterator();

		while (it.hasNext()){
			IAcheteur cle = it.next();
			Integer valeur = this.enchereTemp.get(cle);

			if(valeur > this.objetCourant.getPrixCourant() || (valeur == this.objetCourant.getPrixCourant() && cle.getChrono() < acheteurCourant.getChrono())){
				this.objetCourant.setPrixCourant(valeur);
				this.acheteurCourant = cle;	
				this.objetCourant.setGagnant(this.acheteurCourant.getPseudo());
			}
		}
		this.enchereTemp.clear();
	}


	/**
	 * méthode utilitaire qui permet de savoir si les enchères sont finies.
	 * @return true si on a reçu que des -1, donc si l'enchère est finie, sinon false.
	 */
	public boolean enchereFinie(){	
		Set<IAcheteur> cles = this.enchereTemp.keySet();
		Iterator<IAcheteur> it = cles.iterator();

		while (it.hasNext()){
			IAcheteur cle = it.next();
			Integer valeur = this.enchereTemp.get(cle);

			if(valeur != -1){
				return false;	
			}
		}
		return true;
	}



	@Override
	public void ajouterObjet(Objet objet) throws RemoteException {
		try {
			this.listeObjets.add(objet);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Objet getObjet() throws RemoteException {
		return this.objetCourant;
	}
	
	public List<IAcheteur> getListeAcheteurs() {
		return listeAcheteurs;
	}

	public void setListeAcheteurs(List<IAcheteur> listeAcheteurs) {
		this.listeAcheteurs = listeAcheteurs;
	}

	public Objet getObjetCourant() {
		return objetCourant;
	}

	public void setObjetCourant(Objet objetCourant) {
		this.objetCourant = objetCourant;
	}

	public List<Objet> getListeObjets() {
		return listeObjets;
	}

	public void setListeObjets(List<Objet> listeObjets) {
		this.listeObjets = listeObjets;
	}

	public IAcheteur getAcheteurCourant() {
		return acheteurCourant;
	}

	public void setAcheteurCourant(IAcheteur acheteurCourant) {
		this.acheteurCourant = acheteurCourant;
	}

	public EtatVente getEtatVente() {
		return etatVente;
	}

	public void setEtatVente(EtatVente etatVente) {
		this.etatVente = etatVente;
	}

	@Override
	public String[] getCatalogue() throws RemoteException {
		String[] catalogue = new String[listeObjets.size()];
		int i = 0;
		for (Objet obj : getListeObjets()) {
			catalogue[i] = obj.getNom();
			++i;
		}
		return catalogue;
	}

}
