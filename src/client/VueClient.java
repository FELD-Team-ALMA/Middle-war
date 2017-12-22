package client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import serveur.Objet;

public class VueClient extends JFrame implements ActionListener{

	private static final long serialVersionUID = 9070911591784925769L;
	
	// Informations de l'Etat de la vente
	private Client currentClient;
	
	// Elements SWING
	private JPanel mainPanel = new JPanel();
	private JPanel inscriptionPanel;
	
	private JLabel lblPrixObjet = new JLabel();
	private JLabel lblNomObjet = new JLabel();
	private JLabel lblDescriptionObjet = new JLabel();
	private JLabel lblGagnant = new JLabel();
	private JLabel lblEncherir = new JLabel();
	private JLabel lblChrono = new JLabel(ParamsConfig.CHRONO);
	private JLabel lblCatalogue = new JLabel(ParamsConfig.CATALOGUE);
	private JLabel lblUtilisateur = new JLabel();
	//on peut pas choisir le serveur mais on peut au moins afficher ou on va
	private JLabel lblServer = new JLabel(ParamsConfig.SERVER);
	private JLabel lblChoixPseudo = new JLabel(ParamsConfig.CHOIX_PSEUDO);

	private JButton btnEncherir = new JButton(ParamsConfig.BUTTON_ENCHERIR);
	private JButton btnPseudo = new JButton(ParamsConfig.BUTTON_INSCRIPTION);
	private JButton btnSoumettre = new JButton(ParamsConfig.BUTTON_SOUMETTRE_ENCHERE);
	private JButton btnSoumettreObjet = new JButton(ParamsConfig.BUTTON_SOUMETTRE_OBJET);
	private JButton btnStop = new JButton(ParamsConfig.BUTTON_PASSER);
	
	private JTextField txtEncherir = new JTextField();
	private JTextField txtPseudo = new JTextField();
	private JTextField txtSoumettreNomObjet = new JTextField();
	private JTextField txtSoumettreDescriptionObjet = new JTextField();
	private JTextField txtSoumettrePrixObjet = new JTextField();

	//private JList<String> listCatalogue = new JList();
	private JScrollPane scrollCatalogue;
	
	private boolean inscritVente = false;

	private Font fontBtn = new Font("Serif", Font.PLAIN, 10);
	
	
	private JFrame frmSoumettre = new JFrame(ParamsConfig.BUTTON_SOUMETTRE_ENCHERE);
	

	public JLabel getLblEncherir() {
		return lblEncherir;
	}
	
	
	public void makeInscriptionPanel() {
		inscriptionPanel = new JPanel();
		inscriptionPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		//textfield et bouton pour choisir le pseudo
	    txtPseudo.setPreferredSize(new Dimension(400, 40));   
	    btnPseudo.setPreferredSize(new Dimension(100,40));

	    gbc.gridx = 0;
	    gbc.gridy = 2;
	    gbc.gridheight = 1;
	    gbc.gridwidth = 3;
		inscriptionPanel.add(txtPseudo, gbc);
		
	    gbc.gridx = 4;
	    gbc.gridy = 2;
	    gbc.gridheight = 1;
	    gbc.gridwidth = 1;
		inscriptionPanel.add(btnPseudo, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		inscriptionPanel.add(lblChoixPseudo, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		inscriptionPanel.add(lblServer, gbc);

		btnPseudo.addActionListener(this);
	}
	
	public void makeVentePanel() throws RemoteException {
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setPreferredSize(new Dimension(ParamsConfig.WINDOW_HEIGHT, ParamsConfig.WINDOW_WIDTH));
		GridBagConstraints gbc = new GridBagConstraints();
		/*listCatalogue = new JList<String>(currentClient.getCatalogue());
		scrollCatalogue = new JScrollPane(listCatalogue);
		//test
		//size of the catalogue
		int height = (int) (ParamsConfig.WINDOW_HEIGHT * 0.65);
		int width = (int) (ParamsConfig.WINDOW_WIDTH * 0.25);
		scrollCatalogue.setPreferredSize(new Dimension(height, width));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		mainPanel.add(scrollCatalogue, gbc);
		*/

		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setPreferredSize(new Dimension(800,400));
		lblDescriptionObjet.setPreferredSize(new Dimension(500,300));
		txtEncherir.setPreferredSize(new Dimension(300,40));
		btnEncherir.setPreferredSize(new Dimension(100,40));
		btnEncherir.setFont(fontBtn);
		btnStop.setPreferredSize(new Dimension(100,40));
		btnStop.setFont(fontBtn);
		btnSoumettre.setPreferredSize(new Dimension(100,40));
		btnSoumettre.setFont(fontBtn);
				
		//1ere ligne
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		mainPanel.add(lblNomObjet, gbc);
		
		gbc.gridx = 2;
		mainPanel.add(lblPrixObjet, gbc);
		
		gbc.gridx = 3;
		mainPanel.add(lblUtilisateur, gbc);
		
		gbc.gridx = 4;
		mainPanel.add(lblChrono, gbc);
		
		//2eme ligne
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 2;
		gbc.gridwidth = 6;
		mainPanel.add(lblDescriptionObjet, gbc);
		
		//3eme ligne
		gbc.gridy = 3;
		gbc.gridheight = 1;
		gbc.gridwidth = 3;
		mainPanel.add(txtEncherir, gbc);
		
		gbc.gridx = 4;
		gbc.gridwidth = 1;
		mainPanel.add(btnEncherir, gbc);
		
		gbc.gridx=5;
		gbc.gridwidth=1;
		mainPanel.add(btnStop, gbc);
		
		gbc.gridx=6;
		gbc.gridwidth=1;
		mainPanel.add(btnSoumettre, gbc);

		btnSoumettre.addActionListener(this);
		btnSoumettreObjet.addActionListener(this);
		btnStop.addActionListener(this);
		btnEncherir.addActionListener(this);
	}

	public VueClient() throws Exception {
		super();

		//Definition de la fenêtre
		this.setSize(ParamsConfig.WINDOW_HEIGHT,ParamsConfig.WINDOW_WIDTH);
		this.setTitle(ParamsConfig.WINDOW_TITLE);

		// au lancement on crée le panel d'inscription
		makeInscriptionPanel();
				
		this.setContentPane(inscriptionPanel);
		this.setVisible(true);
	}
	
	public void actualiserPrix() {
		lblPrixObjet.setText("Prix courant : " + currentClient.getCurrentObjet().getPrixCourant() + " euros");
		lblGagnant.setText("Gagnant : " + this.currentClient.getCurrentObjet().getGagnant());
		txtEncherir.setText("");
	}
	
	public void actualiserObjet() {
		Objet objet = currentClient.getCurrentObjet();
		lblPrixObjet.setText("Prix courant : " + objet.getPrixCourant() + " euros");
		lblGagnant.setText("Gagnant : " + objet.getGagnant());
		lblDescriptionObjet.setText(objet.getDescription());
		txtEncherir.setText("");
		
		if (objet.isDisponible()) {
			lblNomObjet.setText(objet.getNom() + "(disponible)");
		}
		else{
			lblNomObjet.setText(objet.getNom() + "(vendu)");
		}
	}
	
	private void setClient(Client client) {
		currentClient = client;
		client.setVue(this);
	}
	
	
	
	@Override
	public synchronized void actionPerformed(ActionEvent arg0) {
		// ENCHERIR			
		if(arg0.getSource().equals(this.btnEncherir)){
			if(!txtEncherir.getText().isEmpty()){
				try {	
					currentClient.encherir(Integer.parseInt(txtEncherir.getText()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//STOP
		else if(arg0.getSource().equals(this.btnStop)){
			try {
				currentClient.encherir(-1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		// INSCRIPTION
		else if(arg0.getSource().equals(btnPseudo)) {
			try {
				setClient(new Client(txtPseudo.getText()));
				currentClient.inscription();
				makeVentePanel();
				changerGUI(this.mainPanel);
			} catch (Exception e) {
				e.printStackTrace();
				
				System.out.println("Inscription impossible");
			}
		}
		
		else if(arg0.getSource().equals(btnSoumettre)) {
			soumettre();
		}
		
		else if(arg0.getSource().equals(btnSoumettreObjet)) {
			try {
				currentClient.nouvelleSoumission(txtSoumettreNomObjet.getText(), txtSoumettreDescriptionObjet.getText(), Integer.parseInt(txtSoumettrePrixObjet.getText()));
			} catch (NumberFormatException e) {
				System.out.println("Impossible de soumettre cet objet.");
				displayErrorMessage("IMPOSSIBLE");
				
			}
			frmSoumettre.dispose();
		}
	}
	

	/**
	 * Methode servant a changer l affichage pour le panel passe en parametre.
	 * @param vue le JPanel a afficher.
	 * @throws RemoteException 
	 */
	public void changerGUI(JPanel vue) throws RemoteException{
		if(this.currentClient.getCurrentObjet() != null){
			actualiserObjet();
			//actualiserCatalogue();
		}
		this.getContentPane().removeAll();
		this.setContentPane(vue);
		this.getContentPane().revalidate();
		this.getContentPane().repaint();
	}
	
	public void attente(){
		this.btnEncherir.setEnabled(false);
		this.btnStop.setEnabled(false);
	}
	
	public void reprise(){
		this.btnEncherir.setEnabled(true);
		this.btnStop.setEnabled(true);
	}

	private void soumettre() {
		frmSoumettre.setSize(400,300);
		JPanel pnlSoumettre = new JPanel(new GridLayout(3,3));
		frmSoumettre.add(pnlSoumettre);
		
		pnlSoumettre.add(new JLabel("Nom de l'objet"));
		pnlSoumettre.add(new JLabel("Une description de l'objet"));
		pnlSoumettre.add(new JLabel("Prix initial"));

		pnlSoumettre.add(txtSoumettreNomObjet);
		pnlSoumettre.add(txtSoumettreDescriptionObjet);
		pnlSoumettre.add(txtSoumettrePrixObjet);
		
		pnlSoumettre.add(btnSoumettreObjet);
		
		frmSoumettre.setVisible(true);
	}
	
	public JPanel getMainPanel() {
		return mainPanel;
	}

	public JPanel getInscriptionPanel() {
		return inscriptionPanel;
	}
	
	public void updateChrono(long temps, long tempsMax){
		
		this.lblChrono.setText("Chrono : "+ temps+"/"+tempsMax);
	}
	
	/**
	 * Afficher  les messages d'erreurs
	 */
	public void displayErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
		
	/*
	 * 
	 */
	public int getHeures(long temps) {
		int heures = 0;
		if (temps >= 60) {
			while (temps >= 60) {
				heures++;
				temps = temps - 60;
			}
		}
		return heures;		
	}
	
	


}
