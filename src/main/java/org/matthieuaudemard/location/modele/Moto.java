package org.matthieuaudemard.location.modele;

import java.util.List;

public class Moto extends Vehicule {

	private int cylindree;
	
	public Moto(String m, List<Exemplaire> ex, int cylindree) {
		super(m, ex);
		this.cylindree = cylindree;
	}

	public Moto(String marque,int cylindree) {
		super(marque);
		this.cylindree = cylindree;
	}
	
	public int getCylindree() {
		return cylindree;
	}

	public void setCylindree(int cylindree) {
		this.cylindree = cylindree;
	}

	@Override
	public String toString() {
		return super.toString() + " Moto " + cylindree;
	}

}
