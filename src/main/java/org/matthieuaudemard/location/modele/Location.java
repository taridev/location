package org.matthieuaudemard.location.modele;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author matthieu
 *
 */
public class Location {
	
	public Exemplaire getExemplaire() {
		return exemplaire;
	}

	public void setExemplaire(Exemplaire exemplaire) {
		this.exemplaire = exemplaire;
	}

	public void setAssurance(boolean assurance) {
		this.assurance = assurance;
	}

	/**
	 * 
	 */
	private int numeroLocation = -1;
	/**
	 * 
	 */
	private Emprunteur emprunteur;
	/**
	 * 
	 */
	private Exemplaire exemplaire;
	/**
	 * 
	 */
	private Date dateRetrait;
	/**
	 * 
	 */
	private Date dateRetourPrevue;
	/**
	 * 
	 */
	private Date dateRetour;
	/**
	 * 
	 */
	private boolean assurance;
	
	/**
	 * @param numeroLocation
	 * @param emprunteur
	 * @param dateRetrait
	 * @param dateRetourPrevue
	 * @param dateRetour
	 */
	public Location(int numeroLocation, Emprunteur emprunteur, Exemplaire exemplaire, Date dateRetrait, Date dateRetourPrevue,
			Date dateRetour, boolean assurance) {
		super();
		this.numeroLocation = numeroLocation;
		this.emprunteur = emprunteur;
		this.exemplaire = exemplaire;
		this.dateRetrait = dateRetrait;
		this.dateRetourPrevue = dateRetourPrevue;
		this.dateRetour = dateRetour;
		this.assurance = assurance;
	}
	
	public Location(Emprunteur emprunteur, Exemplaire exemplaire, Date dateRetrait, Date dateRetourPrevue,
			boolean assurance) {
		this(-1, emprunteur, exemplaire, dateRetrait, dateRetourPrevue, null, assurance);
	}

	public int getNumeroLocation() {
		return numeroLocation;
	}

	public void setNumeroLocation(int numeroLocation) {
		this.numeroLocation = numeroLocation;
	}

	public Emprunteur getEmprunteur() {
		return emprunteur;
	}

	public void setEmprunteur(Emprunteur emprunteur) {
		this.emprunteur = emprunteur;
	}

	public Date getDateRetrait() {
		return dateRetrait;
	}

	public void setDateRetrait(Date dateRetrait) {
		this.dateRetrait = dateRetrait;
	}

	public Date getDateRetourPrevue() {
		return dateRetourPrevue;
	}

	public void setDateRetourPrevue(Date dateRetourPrevue) {
		this.dateRetourPrevue = dateRetourPrevue;
	}

	public Date getDateRetour() {
		return dateRetour;
	}

	public void setDateRetour(Date dateRetour) {
		this.dateRetour = dateRetour;
	}

	public Boolean getAssurance() {
		return assurance;
	}

	public void setAssurance(Boolean assurance) {
		this.assurance = assurance;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		return numeroLocation + " " + emprunteur.getIdEmprunteur() + " " + exemplaire.getImmatriculation() + " " + (assurance ? "true" : false) + " " + 
				format.format(dateRetrait) + " " + format.format(dateRetourPrevue) + " " + (dateRetour != null ? format.format(dateRetour) : "");
	}

}
