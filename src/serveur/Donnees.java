package serveur;

import java.util.Stack;

/**
 * Classe Donnes : stock les objets d'une enchère.
 * @author Léo CASSIAU, Geoffrey DESBROSSES, Jean-Christophe GUERIN, Ugo MAHEY
 *
 */
public class Donnees {

	private Stack<Objet> listeObjets = new Stack<Objet>();
	/**
	 * Get la liste d'objet.
	 * @return Stack<Objet> : la liste d'objet
	 */
	public Stack<Objet> getListeObjets() {
		return listeObjets;
	}
	/**
	 * Set la liste d'objet
	 * @param listeObjets : la nouvelle liste d'objet
	 */
	public void setListeObjets(Stack<Objet> listeObjets) {
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
		
		listeObjets.push(obj1);
		listeObjets.push(obj2);
		listeObjets.push(obj3);
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
