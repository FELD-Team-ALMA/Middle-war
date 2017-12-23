package serveur;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Donnes : stock les objets d'une enchère.
 * @author Léo CASSIAU, Geoffrey DESBROSSES, Jean-Christophe GUERIN, Ugo MAHEY (original repository)
 * @author Montalvo ARAYA, Charles BEGAUDEAU, Marie DELAVERGNE, Charlène SERVANTIE (FELD-TEAM fork )
 */
public class Donnees {

	private List<Objet> listeObjets = new ArrayList<Objet>();
	/**
	 * Get la liste d'objet.
	 * @return Stack<Objet> : la liste d'objet
	 */
	public List<Objet> getListeObjets() {
		return listeObjets;
	}
	/**
	 * Set la liste d'objet
	 * @param listeObjets : la nouvelle liste d'objet
	 */
	public void setListeObjets(List<Objet> listeObjets) {
		this.listeObjets = listeObjets;
	}

	/**
	 * Initialise la liste d'objet avec 3 objets bidon : jarre, herisson et lit.
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
	 * Methode permettant l'ajout d'un nouvel objet aux enchere. Ajoute l'objet dans la liste des objets a vendre.
	 * @param objet l'objet a vendre.
	 * @throws Exception si l'objet est deja en vente ou si l'acheteur n'est pas encore inscrit.
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
