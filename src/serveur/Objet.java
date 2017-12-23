package serveur;

import java.io.Serializable;
/**
 * Classe représentant les objets mis aux enchères. 
 * Implémente Serializable
 * @author Léo CASSIAU, Geoffrey DESBROSSES, Jean-Christophe GUERIN, Ugo MAHEY (original repository)
 * @author Montalvo ARAYA, Charles BEGAUDEAU, Marie DELAVERGNE, Charlène SERVANTIE (FELD-TEAM fork )
 */
public class Objet implements Serializable{

	private static final long serialVersionUID = 1L;
	private String nom;
	private String description;
	private int prixBase;
	private int prixCourant;
	private boolean disponible;
	private String gagnant;
	
	
	/**
	 * Constructeur d'un objet à mettre aux enchères.
	 * A sa création, il est disponible et n'a pas d'acheteur gagnant de l'enchère.
	 * @param nom : nom de l'objet
	 * @param description : description  de l'objet
	 * @param prixBase : prix de départ de l'objet
	 */
	public Objet(String nom, String description, int prixBase) {
		super();
		this.nom = nom;
		this.description = description;
		this.prixBase = prixBase;
		this.prixCourant = prixBase;
		this.disponible = true;
		this.gagnant = "";
	}
	/**
	 * Récupère le nom de l'objet
	 * @return String : le nom
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * Fixe le nom de l'objet
	 * @param nom : le nouveau nom.
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * Récupère la description de l'objet
	 * @return String : la déscription
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Fixe la description de l'objet.
	 * @param description : la nouvelle déscription.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Récupère le prix de base de l'objet
	 * @return int : le prix de base
	 */
	public int getPrixBase() {
		return prixBase;
	}
	/**
	 * Fixe le prix de base de l'objet
	 * @param prixBase : le nouveau prix de base
	 */
	public void setPrixBase(int prixBase) {
		this.prixBase = prixBase;
	}
	/**
	 * Retourne si l'objet est disponible ou non
	 * @return boolean : return true si l'objet est disponible. False sinon.
	 */
	public boolean isDisponible() {
		return disponible;
	}
	/**
	 * Fixe la disponibilité de l'objet
	 * @param disponible : true si on veut l'objet disponible. False sinon.
	 */
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	/**
	 * Récupère le prix courant de l'objet
	 * @return int : le prix courant
	 */
	public int getPrixCourant() {
		return prixCourant;
	}
	/**
	 * Fixe le prix courant de l'objet
	 * @param prixCourant : le nouveau prix courant
	 */
	public void setPrixCourant(int prixCourant) {
		this.prixCourant = prixCourant;
	}

	/**
	 * Récupère le gagnant actuel de l'objet. 
	 * Le gagnant est celui qui va avoir l'objet si personne enchérie.
	 * @return String : le pseudo du gagnant
	 */
	public String getGagnant() {
		return gagnant;
	}

	/**
	 * Fixe le gagnant de l'objet
	 * @param gagnant : le pseudo du nouveau gagnant.
	 */
	public void setGagnant(String gagnant) {
		this.gagnant = gagnant;
	}
	
	
	
}
