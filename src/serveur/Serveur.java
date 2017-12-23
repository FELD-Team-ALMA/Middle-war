package serveur;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;



/**
 * Classe du serveur. Classe éxécutable à lancer en premier dans l'application pour que le serveur tourne.
 * 
 * port du serveur : 8090
 * @author Léo CASSIAU, Geoffrey DESBROSSES, Jean-Christophe GUERIN, Ugo MAHEY
 *
 */
public class Serveur{

	private final static int port = 8090;
	private static Donnees bdd = new Donnees();


	public static void main(String[] argv) {
		
		try {
			System.out.println("@ IP : " + InetAddress.getLocalHost());
			
			bdd.initObjets();
			ServeurVente vente = new ServeurVente(bdd.getListeObjets());
			LocateRegistry.createRegistry(port);
			Naming.bind("//localhost:"+port+"/enchere", vente);

	
		while(true){
			
			//On recrée une nouvelle vente
			if(vente.getEtatVente() == EtatVente.TERMINE){
				bdd.initObjets();
				vente = new ServeurVente(bdd.getListeObjets());
			}
			
		}
			
		} catch(RemoteException |  MalformedURLException | UnknownHostException | AlreadyBoundException e){
			e.printStackTrace();
		}		
	}	
}
	
