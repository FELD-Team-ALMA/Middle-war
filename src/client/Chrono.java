package client;

/**
 * Classe décrivant le comportement d'un chronomètre pour les enchères
 * 
 * @author Léo CASSIAU, Geoffrey DESBROSSES, Jean-Christophe GUERIN, Ugo MAHEY (original repository)
 * @author Montalvo ARAYA, Charles BEGAUDEAU, Marie DELAVERGNE, Charlène SERVANTIE (FELD-TEAM fork )
 */
public class Chrono extends Thread {

	private Client client;
	private long tempsFin;
	private long tempsEcoule;
	private boolean enCours = false;
	
	/**
	 * Constructeur de la classe Chrono 
	 * 
	 * @param secondes : durée maximum 
	 * @param c : le client associé à chronométrer
	 */
	public Chrono(long secondes, Client c) {
		tempsFin = secondes;
		client = c;
	}
	
	/**
	 * Décrémente le chrono si celui-ci est actif (en cours)
	 */
	public void run() {
		while(true) {
			if(enCours) {
				tempsEcoule = 0;
				while(tempsFin >= tempsEcoule && enCours) {
					try {
						sleep(1); // Attends 1ms
						tempsEcoule++;
						this.client.updateChrono();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				if(enCours) {
					try {
						client.encherir(-1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			System.out.print("");
		}
	}
	


	/**
	 * Récupère le temps écoulé du chrono
	 * @return long : le temps écoulé.
	 */
	public long getTempsEcoule() {
		return tempsEcoule;
	}

	/**
	 * Démarre le chrono. Attention ne vérifie pas si celui-ci a déjà démarré. 
	 */
	public void demarrer() {
		enCours = true;
	}
	/**
	 * Stop le chrono. Attention ne vérifie pas si celui-ci est déjà stoppé.
	 */
	public void arreter() {
		enCours = false;
	}
	
	/**
	 * Regarde si le chrono est en train de tourner.
	 * @return boolean : true si le chrono est en train de tourner. False sinon.
	 */
	public boolean getFini() {
		return enCours;
	}
	/**
	 * Récupère le temps auquel le chrono doit s'arrêter.
	 * @return long : le temps d'arrêt du chrono
	 */
	public long getTempsFin() {
		return tempsFin;
	}	
	
}
