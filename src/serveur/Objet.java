package serveur;

import java.io.Serializable;
/**
 * Classe représentant les objets mis aux enchères. 
 * Implémente Serializable
 * @author Léo CASSIAU, Geoffrey DESBROSSES, Jean-Christophe GUERIN, Ugo MAHEY
 *
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
	 * Constructeur d'un objet. 
	 * disponible initialisé à true.
	 * gagnant initialisé à "" 
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
	 * Get Nom de l'objet
	 * @return String : le nom
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * Set nom de l'objet
	 * @param nom : le nouveau nom.
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * Get description de l'objet
	 * @return String : la déscription
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Set description de l'objet.
	 * @param description : la nouvelle déscription.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Get le prix de base de l'objet
	 * @return int : le prix de base
	 */
	public int getPrixBase() {
		return prixBase;
	}
	/**
	 * Set le prix de base de l'objet
	 * @param prixBase : le nouveau prix de base
	 */
	public void setPrixBase(int prixBase) {
		this.prixBase = prixBase;
	}
	/**
	 * Dit si l'objet est disponible
	 * @return boolean : return true si l'objet est dispo. False sinon.
	 */
	public boolean isDisponible() {
		return disponible;
	}
	/**
	 * Set la disponibilité de l'objet
	 * @param disponible : true si on veut l'objet dispo. False sinon.
	 */
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	/**
	 * Get le prix courant de l'objet
	 * @return int : le prix courant
	 */
	public int getPrixCourant() {
		return prixCourant;
	}
	/**
	 * Set le prix courant de l'objet
	 * @param prixCourant : le nouveau prix courant
	 */
	public void setPrixCourant(int prixCourant) {
		this.prixCourant = prixCourant;
	}

	/**
	 * Get le gagnant actuelle de l'objet. 
	 * C'est à dire celui qui va avoir l'objet si personne enchérie.
	 * @return String : le pseudo du gagnant
	 */
	public String getGagnant() {
		return gagnant;
	}

	/**
	 * Set le gagnant de l'objet
	 * @param gagnant : le pseudo du nouveau gagnant.
	 */
	public void setGagnant(String gagnant) {
		this.gagnant = gagnant;
	}
	
	
	
}
