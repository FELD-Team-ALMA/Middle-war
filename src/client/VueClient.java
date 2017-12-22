package client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import serveur.Objet;

public class VueClient extends JFrame implements ActionListener{

	private static final long serialVersionUID = 9070911591784925769L;
	
	// Informations de l'Etat de la vente
	private Client currentClient;
	
	// Elements SWING
	
	private JPanel inscriptionPanel;
	private JPanel mainPanel;
	
	//TODO : mettre à jour les noms pour ceux qui varient
	private JLabel nomObjet = new JLabel();
	private JLabel gagnant = new JLabel();
	
	private JLabel lblChrono = new JLabel(ParamsConfig.CHRONO);
	private JLabel chrono = new JLabel();
	//on peut pas choisir le serveur mais on peut au moins afficher ou on va
	private JLabel lblServer = new JLabel(ParamsConfig.SERVER);
	private JLabel lblChoixPseudo = new JLabel(ParamsConfig.CHOIX_PSEUDO);

	private JButton btnEncherir = new JButton(ParamsConfig.BUTTON_ENCHERIR);
	private JButton btnInscrire = new JButton(ParamsConfig.BUTTON_INSCRIPTION);
	private JButton btnCreerEnchere = new JButton(ParamsConfig.BUTTON_SOUMETTRE_ENCHERE);
	private JButton btnSoumettreObjet = new JButton(ParamsConfig.BUTTON_SOUMETTRE_OBJET);
	private JButton btnStop = new JButton(ParamsConfig.BUTTON_PASSER);
	
	private JTextField txtEncherir = new JTextField();
	private JTextField txtPseudo = new JTextField();
	private JTextField txtSoumettreNomObjet = new JTextField();
	private JTextField txtSoumettreDescriptionObjet = new JTextField();
	private JTextField txtSoumettrePrixObjet = new JTextField();
	

	private JLabel prixObjet = new JLabel();
	private JTextArea descriptionObjet = new JTextArea();

	//private JList<String> listCatalogue = new JList();
	//private JScrollPane scrollCatalogue;
	//private JLabel lblCatalogue = new JLabel(ParamsConfig.CATALOGUE);

	
	private JFrame frmSoumettre = new JFrame(ParamsConfig.BUTTON_SOUMETTRE_ENCHERE);
		
	public void makeInscriptionPanel() {
		inscriptionPanel = new JPanel();
		inscriptionPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		//textfield et bouton pour choisir le pseudo
	    txtPseudo.setPreferredSize(new Dimension(400, 40));   
	    btnInscrire.setPreferredSize(new Dimension(100,40));

	    gbc.gridx = 0;
	    gbc.gridy = 2;
	    gbc.gridheight = 1;
	    gbc.gridwidth = 3;
		inscriptionPanel.add(txtPseudo, gbc);
		
	    gbc.gridx = 4;
	    gbc.gridy = 2;
	    gbc.gridheight = 1;
	    gbc.gridwidth = 1;
		inscriptionPanel.add(btnInscrire, gbc);
		
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

		btnInscrire.addActionListener(this);
	}
	/**
	 * Crée le panel pour l'interface principale d'enchères
	 * @throws RemoteException
	 */
	public void makeVentePanel() throws RemoteException {
		
		JPanel ventePanel = new JPanel();
		ventePanel.setLayout(new BorderLayout());
		
		JPanel objetPanel = makeObjetPanel();
		objetPanel.setBorder(new TitledBorder(ParamsConfig.LABEL_OBJET_COURANT));
		ventePanel.add(objetPanel, BorderLayout.EAST);
		
		JPanel cataloguePanel = makeCataloguePanel();
		ventePanel.add(cataloguePanel, BorderLayout.WEST);
		
		JPanel buttonPanel = makeBottomPanel();
		ventePanel.add(buttonPanel, BorderLayout.SOUTH);
		
		//les actionlisteners
		btnCreerEnchere.addActionListener(this);
		btnSoumettreObjet.addActionListener(this);
		btnStop.addActionListener(this);
		btnEncherir.addActionListener(this);
		
		mainPanel = ventePanel;		
	}
	
	/**
	 * Crée le panel comportant le bad de l'ihm
	 * @return
	 */
	private JPanel makeBottomPanel() {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,3));

		//pour soumettre un autre objet
		bottomPanel.add(btnCreerEnchere);	
		bottomPanel.add(makeChronoPanel());	
		bottomPanel.add(makeButtonPanel());
				
		return bottomPanel;
	}

	/**
	 * Crée un panel qui affiche le chrono
	 * @return
	 */
	private Component makeChronoPanel() {
		JPanel chronoPanel = new JPanel();
		chronoPanel.setLayout(new FlowLayout());
		chronoPanel.add(lblChrono);
		chrono.setText(currentClient.getDisplayableTime());
		chronoPanel.add(chrono);
		chronoPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
		return chronoPanel;
	}


	/**
	 * Crée un panel pour les boutons
	 * @return
	 */
	private JPanel makeButtonPanel() {
		JPanel buttonPanel = new JPanel();
		//boutons en une ligne alignés horizontalement
		buttonPanel.setLayout(new GridLayout(2, 2));
		buttonPanel.add(btnEncherir);
		buttonPanel.add(txtEncherir);
		buttonPanel.add(btnStop);
		//buttonPanel.add(txtPseudo);				
		return buttonPanel;
	}

	/**
	 * Crée le panel affichant le catalogue
	 * TODO: update when the catalogue is displayable
	 * @return an empty JPanel for now
	 */
	private JPanel makeCataloguePanel() {
		//TODO:update with catalogue when catalogue works
		JPanel cataloguePanel = new JPanel();
		cataloguePanel.setBorder(new BevelBorder(BevelBorder.RAISED));
		return cataloguePanel;
	}


	/**
	 * Crée le panel affichant les informations sur l'objet en cours de vente
	 *TODO: régler le problème de largeur sur la description
	 * @return le panel à afficher
	 */
	private JPanel makeObjetPanel() {
		JPanel objetPanel = new JPanel();
		
		int rows = 4;
		int columns = 2;
		int horizontalGap = 0;
		int verticalGap = 0;
		objetPanel.setLayout(new GridLayout(rows, columns, horizontalGap, verticalGap));
		
		int height = 360;
		int width = 500;
		objetPanel.setPreferredSize(new Dimension(height, width));
		
		JLabel lblnom = new JLabel(ParamsConfig.LABEL_NOM_OBJET);
		JLabel lblDescription = new JLabel(ParamsConfig.LABEL_DESCRIPTION);
		JLabel lblPrixActuel = new JLabel(ParamsConfig.LABEL_PRIX_ACTUEL);
		JLabel lblGagnant = new JLabel(ParamsConfig.LABEL_GAGNANT);
		
		objetPanel.add(lblnom);
		objetPanel.add(nomObjet);
		
		objetPanel.add(lblDescription);
		descriptionObjet.setEditable(false);
		descriptionObjet.setLineWrap(true);
		objetPanel.add(descriptionObjet);
		
		objetPanel.add(lblPrixActuel);
		objetPanel.add(prixObjet);
		
		objetPanel.add(lblGagnant);
		objetPanel.add(gagnant);
		
		return objetPanel;
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
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actualiserPrix() {
		prixObjet.setText(currentClient.getCurrentObjet().getPrixCourant() + " euros");
		gagnant.setText(this.currentClient.getCurrentObjet().getGagnant());
		txtEncherir.setText("");
	}
	
	/**
	 * Met à jour l'affichage d'un objet
	 */
	public void actualiserObjet() {
		Objet objet = currentClient.getCurrentObjet();
		prixObjet.setText(objet.getPrixCourant() + " euros");
		gagnant.setText(objet.getGagnant());
		descriptionObjet.setText(objet.getDescription());
		txtEncherir.setText("");
		
		if (objet.isDisponible()) {
			nomObjet.setText(objet.getNom() + "(disponible)");
		}
		else{
			nomObjet.setText(objet.getNom() + "(vendu)");
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
		else if(arg0.getSource().equals(btnInscrire)) {
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
		//Bouton pour créer un objet à soumettre aux enchères
		else if(arg0.getSource().equals(btnCreerEnchere)) {
			soumettreNouvelObjet();
		}
		
		//bouton pour envoyer l'objet créé
		else if(arg0.getSource().equals(btnSoumettreObjet)) {
			try {
				currentClient.nouvelleSoumission(txtSoumettreNomObjet.getText(), txtSoumettreDescriptionObjet.getText(), Integer.parseInt(txtSoumettrePrixObjet.getText()));
			} catch (NumberFormatException e) {
				System.out.println("Impossible de soumettre cet objet.");
				
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

	/**
	 * Peuple la frame pour soumettre un nouvel objet à la vente
	 */
	private void soumettreNouvelObjet() {
		frmSoumettre.setSize(400,300);
		JPanel pnlSoumettre = new JPanel(new GridLayout(3,3));
		frmSoumettre.add(pnlSoumettre);
		
		descriptionObjet.setPreferredSize(new Dimension(500,300));
		txtEncherir.setPreferredSize(new Dimension(300,40));
		btnEncherir.setPreferredSize(new Dimension(100,40));
		btnEncherir.setFont(ParamsConfig.BUTTON_FONT);
		btnStop.setPreferredSize(new Dimension(100,40));
		btnStop.setFont(ParamsConfig.BUTTON_FONT);
		btnCreerEnchere.setPreferredSize(new Dimension(100,40));
		btnCreerEnchere.setFont(ParamsConfig.BUTTON_FONT);
		
		pnlSoumettre.add(new JLabel(ParamsConfig.LABEL_NOM_OBJET));
		pnlSoumettre.add(new JLabel(ParamsConfig.LABEL_DESCRIPTION));
		pnlSoumettre.add(new JLabel(ParamsConfig.LABEL_PRIX_INITIAL));

		pnlSoumettre.add(txtSoumettreNomObjet);
		pnlSoumettre.add(txtSoumettreDescriptionObjet);
		pnlSoumettre.add(txtSoumettrePrixObjet);
		
		pnlSoumettre.add(btnSoumettreObjet);
		
		frmSoumettre.setVisible(true);
	}
	
	/**
	 * Met à jour l'affichage du chronomètre
	 * @param chronoDisplay
	 */
	public void updateChrono(String chronoDisplay){		
		this.chrono.setText(chronoDisplay);
	}
	
}
