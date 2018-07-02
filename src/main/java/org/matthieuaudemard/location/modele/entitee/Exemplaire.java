package org.matthieuaudemard.location.modele.entitee;

public class Exemplaire extends Entitee<String>{
	
	/**
	 * 
	 */
	private int kilometrage;
	
	/**
	 * 
	 */
	private Vehicule vehicule;

	public Exemplaire(String numero, Vehicule vehicule, int kilometrage) {
		this.primaryKey = numero;
		this.kilometrage = kilometrage;
		this.vehicule = vehicule;
	}

	public void setimmatriculation(String numero) {
		this.primaryKey = numero;
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
		return primaryKey + " " +  vehicule.toString() + " " + kilometrage;
	}
	
	public void update(Exemplaire e) {
		this.primaryKey = e.primaryKey;
		this.kilometrage = e.kilometrage;
		this.vehicule = e.vehicule;
	}
}
