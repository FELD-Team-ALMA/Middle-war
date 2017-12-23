package serveur;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Données : stocke les objets d'une enchère.
 * @author Léo CASSIAU, Geoffrey DESBROSSES, Jean-Christophe GUERIN, Ugo MAHEY (original repository)
 * @author Montalvo ARAYA, Charles BEGAUDEAU, Marie DELAVERGNE, Charlène SERVANTIE (FELD-TEAM fork )
 */
public class Donnees {

	private List<Objet> listeObjets = new ArrayList<Objet>();
	/**
	 * Récupère la liste d'objets.
	 * @return List<Objet> : la liste d'objets
	 */
	public List<Objet> getListeObjets() {
		return listeObjets;
	}
	/**
	 * Fixe la liste d'objets
	 * @param listeObjets : la nouvelle liste d'objets
	 */
	public void setListeObjets(List<Objet> listeObjets) {
		this.listeObjets = listeObjets;
	}

	/**
	 * Initialise la liste d'objets avec 3 objets bidons : jarre, hérisson et lit.
	 * L'enchère commencera avec "lit".
	 */
	//TODO a virer par la suite
	public void initObjets(){
		Objet obj1 = new Objet("jarre","jarre de ramses 3", 250);
		Objet obj2 = new Objet("herisson","herisson des bois", 100);
		Objet obj3 = new Objet("lit","un lit tout doux avec lequel on n'a pas envie de se lever le matin", 300);
		
		listeObjets.add(obj1);
		listeObjets.add(obj2);
		listeObjets.add(obj3);
	}
	
	
	
	
	/**
	 * Méthode permettant l'ajout d'un nouvel objet aux enchères. Ajoute l'objet dans la liste des objets à vendre.
	 * @param objet l'objet à vendre.
	 * @throws Exception si l'objet est déjà en vente ou si l'acheteur n'est pas encore inscrit.
	 */
	public void ajouterArticle(Objet objet) throws Exception{
		for(Objet each : this.listeObjets){
			if(each.equals(objet)){
				throw new Exception("Objet deja existant");
			}
		}

		this.listeObjets.add(objet);
	}
	
}
