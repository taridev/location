package org.matthieuaudemard.location.modele;

public class Exemplaire {
	
	/**
	 * 
	 */
	private String immatriculation;
	
	/**
	 * 
	 */
	private int kilometrage;
	
	/**
	 * 
	 */
	private Vehicule vehicule;

	public Exemplaire(String numero, Vehicule vehicule, int kilometrage) {
		super();
		this.immatriculation = numero;
		this.kilometrage = kilometrage;
		this.vehicule = vehicule;
	}

	public String getImmatriculation() {
		return immatriculation;
	}

	public void setimmatriculation(String numero) {
		this.immatriculation = numero;
	}

	public int getKilometrage() {
		return kilometrage;
	}

	public void setKilometrage(int kilometrage) {
		this.kilometrage = kilometrage;
	}

	public Vehicule getVehicule() {
		return vehicule;
	}

	public void setVehicule(Vehicule vehicule) {
		this.vehicule = vehicule;
	}
	
	public String toString() {
		return immatriculation + " " +  vehicule.toString() + " " + kilometrage;
	}
	
	public void update(Exemplaire e) {
		this.immatriculation = e.immatriculation;
		this.kilometrage = e.kilometrage;
		this.vehicule = e.vehicule;
	}
}
