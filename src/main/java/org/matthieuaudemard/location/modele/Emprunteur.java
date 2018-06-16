package org.matthieuaudemard.location.modele;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author matthieu
 *
 */
public class Emprunteur implements Iterable<Location> {
	private int idEmprunteur;
	private String nomEmprunteur;
	private String prenomEmprunteur;
	private Adresse adresseEmprunteur;
	private ArrayList<Location> locations = new ArrayList<Location>();
	
	
	/**
	 * @param idEmprunteur
	 * @param nomEmprunteur
	 * @param prenomEmprunteur
	 * @param adresseEmprunteur
	 */
	public Emprunteur(int idEmprunteur, String nomEmprunteur, String prenomEmprunteur, Adresse adresseEmprunteur) {
		super();
		this.idEmprunteur = idEmprunteur;
		this.nomEmprunteur = nomEmprunteur;
		this.prenomEmprunteur = prenomEmprunteur;
		this.adresseEmprunteur = adresseEmprunteur;
	}
	
	public Emprunteur(int idEmprunteur, String nomEmprunteur, String prenomEmprunteur, String adresseEmprunteur) {
		this(idEmprunteur, nomEmprunteur, prenomEmprunteur, new Adresse(adresseEmprunteur));
	}
	
	public Emprunteur(String nomEmprunteur, String prenomEmprunteur, String adresseEmprunteur) {
		this(-1, nomEmprunteur, prenomEmprunteur, new Adresse(adresseEmprunteur));
	}
	
	public int getIdEmprunteur() {
		return idEmprunteur;
	}
	public void setIdEmprunteur(int idEmprunteur) {
		this.idEmprunteur = idEmprunteur;
	}
	public String getNomEmprunteur() {
		return nomEmprunteur;
	}
	public void setNomEmprunteur(String nomEmprunteur) {
		this.nomEmprunteur = nomEmprunteur;
	}
	public String getPrenomEmprunteur() {
		return prenomEmprunteur;
	}
	public void setPrenomEmprunteur(String prenomEmprunteur) {
		this.prenomEmprunteur = prenomEmprunteur;
	}
	public Adresse getAdresseEmprunteur() {
		return adresseEmprunteur;
	}
	public void setAdresseEmprunteur(Adresse adresseEmprunteur) {
		this.adresseEmprunteur = adresseEmprunteur;
	}
	
	public void louer(Location location) {
		locations.add(location);
	}
	
	public void removeLocation(Location location) {
		locations.remove(location);
	}
	
	public void removeLocation(int locationId) {
		for(Location l : locations)
			if(l.getNumeroLocation() == locationId)
				locations.remove(l);
	}

	public void ramener(Location location) {
		System.out.println("Emprunteur.ramener()>>");
		if(locations.contains(location)) {
			locations.remove(location);
		}
		System.out.println("<<Emprunteur.ramener()");
	}
	
	public void ramener(int index) {
		if(locations.size() > index)
			locations.remove(index);
	}
		
	@Override
	public Iterator<Location> iterator() {
		return locations.iterator();
	}
	
	public String toString() {
		return idEmprunteur + " " + nomEmprunteur + " " + prenomEmprunteur + " " + adresseEmprunteur;
	}

	public void setLocations(ArrayList<Location> l) {
		this.locations = l;
	}
}
