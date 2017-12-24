package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.RemoteException;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import client.Client;
import config.ParamsConfig;
import exceptions.ConnexionImpossibleException;
import exceptions.LoginPrisException;
import exceptions.PrixTropBasException;
import serveur.Objet;
/**
 * 
 * @author Léo CASSIAU, Geoffrey DESBROSSES, Jean-Christophe GUERIN, Ugo MAHEY (original repository)
 * @author Montalvo ARAYA, Charles BEGAUDEAU, Marie DELAVERGNE, Charlène SERVANTIE (FELD-TEAM fork )
 */
public class VueClient extends JFrame implements ActionListener{

	private static final long serialVersionUID = 9070911591784925769L;

	// Informations de l'Etat de la vente
	private Client currentClient;

	// Elements SWING

	private JPanel inscriptionPanel;
	private JPanel mainPanel;
	private JPanel cataloguePanel;


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
	private JList<String> catalogueList;

	private JFrame frmSoumettre;

	public void makeInscriptionPanel() {
		//la frame a pas besoin d'être giganormique vu les infos
		this.setSize(new Dimension(500, 150));
		Dimension labelSize = new Dimension(200,40);

		inscriptionPanel = new JPanel();
		inscriptionPanel.setPreferredSize(new Dimension(500, 240));
		inscriptionPanel.setLayout(new GridLayout(4,3));
		//textfield et bouton pour choisir le pseudo
		txtPseudo.setMinimumSize(new Dimension(80, 40));
		txtPseudo.setPreferredSize(labelSize);
		txtPseudo.setAlignmentX(AbstractButton.CENTER);

		btnInscrire.setPreferredSize(labelSize);
		btnInscrire.setAlignmentX(AbstractButton.CENTER);

		lblServer.setPreferredSize(labelSize);

		inscriptionPanel.add(lblServer);
		inscriptionPanel.add(lblChoixPseudo);
		inscriptionPanel.add(txtPseudo);
		inscriptionPanel.add(btnInscrire);

		btnInscrire.addActionListener(this);
	}
	/**
	 * Crée le panel pour l'interface principale d'enchères
	 * @throws RemoteException
	 */
	public void makeVentePanel() throws RemoteException {
		//on repasse la taille normale
		this.setSize(ParamsConfig.WINDOW_HEIGHT,ParamsConfig.WINDOW_WIDTH);


		JPanel ventePanel = new JPanel();
		ventePanel.setLayout(new BorderLayout());

		JPanel objetPanel = makeObjetPanel();
		objetPanel.setBorder(new TitledBorder(ParamsConfig.LABEL_OBJET_COURANT));
		ventePanel.add(objetPanel, BorderLayout.EAST);

		JPanel cataloguePanel = makeCataloguePanel();
		ventePanel.add(cataloguePanel, BorderLayout.CENTER);

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
	 * Crée le panel comportant le bas de l'IHM
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
		//bordure pour un peu distinguer
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
	 * @return an empty JPanel for now
	 * @throws RemoteException 
	 */
	private JPanel makeCataloguePanel() throws RemoteException {
		cataloguePanel = new JPanel();
		cataloguePanel.setBorder(new TitledBorder(ParamsConfig.CATALOGUE));
		catalogueList = new JList<String>(currentClient.getCatalogue());
		catalogueList.setFixedCellWidth(ParamsConfig.CATALOGUE_WIDTH);
		JScrollPane pane = new JScrollPane(catalogueList);
		cataloguePanel.add(pane);
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
		int width = 400;
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


	/**
	 * Constructeur de la frame
	 */
	public VueClient() {
		super();
		//Definition de la fenêtre
		this.setTitle(ParamsConfig.WINDOW_TITLE);

		// au lancement on crée le panel d'inscription
		makeInscriptionPanel();				
		this.setContentPane(inscriptionPanel);
		this.setLocationRelativeTo(null);
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
			nomObjet.setText(objet.getNom() + " (disponible)");
		}
		else{
			nomObjet.setText(objet.getNom() + " (vendu)");
		}
	}

	/**
	 * Met à jour l'affichage du catalogue
	 * @throws RemoteException 
	 * 
	 */
	public void updateCatalogue(String[] catalogue) throws RemoteException {
		currentClient.setCatalogue(catalogue);
		String[] catalogue2 = currentClient.getCatalogue();
		catalogueList.setListData(catalogue2);
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
				}
				//distinguer une erreur d'entrée d'une erreur autre (et pas casser le programme dessus)
				catch (NumberFormatException e) {
					afficheMessage(ParamsConfig.ERROR_PRIX_NOT_INT, ParamsConfig.ERROR);
				}
				catch (PrixTropBasException e) {
					afficheMessage(ParamsConfig.ERROR_PRIX_TROP_BAS, ParamsConfig.ERROR);
				} 
				catch (RemoteException e) {
					afficheMessage(ParamsConfig.ERROR_ECHEC_COMMUNICATION_SERVEUR, ParamsConfig.ERROR);
				} 
				catch (InterruptedException e) {
					//ne devrait normalement pas arriver ? (ou arrive quand l'enchère est finie?)
					afficheMessage(ParamsConfig.ERROR_INTERRUPTION, ParamsConfig.ERROR);
				}
			}
		}
		//STOP
		else if(arg0.getSource().equals(this.btnStop)){
			try {
				currentClient.encherir(-1);
			}
			catch (RemoteException e) {
				afficheMessage(ParamsConfig.ERROR_ECHEC_COMMUNICATION_SERVEUR, ParamsConfig.ERROR);
			}
			catch (InterruptedException e) {
				//ne devrait normalement pas arriver ? (ou arrive quand l'enchère est finie?)
				afficheMessage(ParamsConfig.ERROR_INTERRUPTION, ParamsConfig.ERROR);
			} 
			catch (PrixTropBasException e) {
				//ne doit pas passer dedans (vu qu'on arrête)
				afficheMessage(ParamsConfig.ERROR_PRIX_TROP_BAS, ParamsConfig.ERROR);
			}
		}
		// INSCRIPTION
		else if(arg0.getSource().equals(btnInscrire)) {
			try {
				setClient(new Client(txtPseudo.getText()));
				currentClient.inscription();
				makeVentePanel();
				changerGUI(this.mainPanel);
			}
			catch (ConnectException e) {
				afficheMessage(ParamsConfig.ERROR_CONNEXION, ParamsConfig.ERROR);
			} 
			catch (LoginPrisException e) {
				afficheMessage(ParamsConfig.ERROR_INSCRIPTION_LOGIN_PRIS, ParamsConfig.ERROR);
			} 
			catch (MalformedURLException e) {
				afficheMessage(ParamsConfig.ERROR_URL_SERVEUR_DEFAILLANTE, ParamsConfig.ERROR);
			} 
			catch (ConnexionImpossibleException e) {
				afficheMessage(ParamsConfig.ERROR_CONNEXION_IMPOSSIBLE, ParamsConfig.ERROR);
			}		
			catch (RemoteException e) {
				afficheMessage(ParamsConfig.ERROR_REMOTE, ParamsConfig.ERROR);
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
			} 
			catch (NumberFormatException e) {
				afficheMessage(ParamsConfig.ERROR_SOUMISSION_OBJET, ParamsConfig.ERROR);
			}
			txtSoumettreNomObjet.setText("");
			txtSoumettreDescriptionObjet.setText("");
			txtSoumettrePrixObjet.setText("");

			frmSoumettre.dispose();
		}
	}


	/**
	 * Méthode servant à changer l'affichage pour le panel passé en paramètre.
	 * @param vue le JPanel à afficher.
	 * @throws RemoteException 
	 */
	public void changerGUI(JPanel vue) throws RemoteException{
		if(this.currentClient.getCurrentObjet() != null){
			actualiserObjet();
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
		frmSoumettre = new JFrame(ParamsConfig.BUTTON_SOUMETTRE_ENCHERE);
		frmSoumettre.setSize(400,300);
		JPanel pnlSoumettre = new JPanel(new GridLayout(3,3));
		frmSoumettre.add(pnlSoumettre);

		descriptionObjet.setPreferredSize(new Dimension(500,300));
		txtEncherir.setPreferredSize(new Dimension(300,40));
		btnEncherir.setPreferredSize(new Dimension(100,40));
		btnStop.setPreferredSize(new Dimension(100,40));
		btnCreerEnchere.setPreferredSize(new Dimension(100,40));

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

	/**
	 * Affiche des messages d'erreurs à part
	 * @param message : le message 
	 * @param type : le type de message (sert de titre)
	 */
	public void afficheMessage(String message, String type) {
		JDialog dialog = new JDialog();
		dialog.setTitle(type);
		JPanel messagePanel = new JPanel();
		messagePanel.add(new JLabel(message));
		dialog.add(messagePanel);
		JPanel buttonPanel = new JPanel();
		JButton button = new JButton("OK"); 
		buttonPanel.add(button); 
		//une classe anonyme pour gérer juste la fermeture par le bouton ok du message
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getSource().equals(button)){
					dialog.dispose();
				}
			}	    	
		});
		dialog.add(buttonPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dialog.pack(); 
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
