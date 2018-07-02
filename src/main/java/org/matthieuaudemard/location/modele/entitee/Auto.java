package org.matthieuaudemard.location.modele.entitee;

/**
 * @author matthieu
 *
 */
public class Auto extends Vehicule {
	
	/**
	 * 
	 */
	private String modele;

	/**
	 * @param marque
	 * @param model
	 */
	public Auto(String marque, String model) {
		super(marque);
		this.modele = model;
	}

	/**
	 * @return
	 */
	public String getModele() {
		return modele;
	}

	/**
	 * @param modele
	 */
	public void setModele(String modele) {
		this.modele = modele;
	}
	
	/* (non-Javadoc)
	 * @see modele.Vehicule#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " Auto " + modele;
	}

}
