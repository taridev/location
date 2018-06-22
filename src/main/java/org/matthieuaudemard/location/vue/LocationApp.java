package org.matthieuaudemard.location.vue;

import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import org.matthieuaudemard.location.vue.emprunteur.JPanelEmprunteur;
import org.matthieuaudemard.location.vue.exemplaire.JPanelExemplaire;
import org.matthieuaudemard.location.vue.location.JPanelLocation;

public class LocationApp extends JFrame {
	
	private JTabbedPane tabPan = new JTabbedPane();
	JPanelEmprunteur panelEmprunteur = new JPanelEmprunteur(this);
	JPanelExemplaire panelExemplaire = new JPanelExemplaire(this);
	JPanelLocation panelLocation;

	/**
	 * 
	 */
	private static final long serialVersionUID = 7370442456110105799L;

	/**
	 * @param titre
	 * @throws HeadlessException
	 */
	public LocationApp(String titre) {
		super(titre);
		panelLocation = new JPanelLocation(panelExemplaire.getTableModel().getCtrl(), panelEmprunteur.getTableModel().getCtrl(), this);
		tabPan.add(panelEmprunteur, "Emprunteur");
		tabPan.add(panelExemplaire, "Exemplaire");
		tabPan.add(panelLocation, "Location");
		add(tabPan);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
	}
	
	public static void main(String [] args) {
		new LocationApp("Projet Location").setVisible(true);
	}
	
}
