package api;

import java.rmi.Remote;
import java.rmi.RemoteException;

import exceptions.LoginPrisException;
import serveur.Objet;
/**
 * Interface IServeurVente. Sert de serveur pour l'application.
 * 
 * Extends : Remote
 * 
 * @author Léo CASSIAU, Geoffrey DESBROSSES, Jean-Christophe GUERIN, Ugo MAHEY (original repository)
 * @author Montalvo ARAYA, Charles BEGAUDEAU, Marie DELAVERGNE, Charlène SERVANTIE (FELD-TEAM fork )
 */
public interface IServeurVente extends Remote {

	/**
	 * Méthode servant à inscrire un acheteur à une vente. Ajoute l'acheteur dans la liste des acheteurs
	 * @param login 
	 * @param acheteur
	 * @throws RemoteException
	 */
	public boolean inscriptionAcheteur(String login, IAcheteur acheteur) throws RemoteException, LoginPrisException;
	
	/**
	 * Augmente le prix de l'objet à vendre.
	 * @param nouveauPrix le nouveau prix que le client a donné
	 * @param acheteur l'acheteur ayant enchéri 
	 * @return le nouveau prix de l'objet à vendre
	 * @throws RemoteException
	 * @throws InterruptedException 
	 */
	public int rencherir(int nouveauPrix, IAcheteur acheteur) throws RemoteException, InterruptedException;
	

	/**
	 * Méthode permettant d'ajouter un objet aux enchères.
	 * @param objet l'objet à vendre.
	 * @throws RemoteException
	 */
	public void ajouterObjet(Objet objet) throws RemoteException;
	
	
	public Objet getObjet() throws RemoteException;
	
	/**
	 * Méthode pour renvoyer la liste des objets à venir au client (juste leur nom)
	 * @return
	 * @throws RemoteException
	 */
	public String[] getCatalogue() throws RemoteException;


}
