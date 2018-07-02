package org.matthieuaudemard.location.modele.entitee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author matthieu
 *
 */
public abstract class Vehicule implements Iterable<Exemplaire> {

	/**
	 * 
	 */
	private String marque;

	/**
	 * 
	 */
	private List<Exemplaire> exemplaires = new ArrayList<>();

	/**
	 * @param m
	 * @param ex
	 */
	public Vehicule(String m, List<Exemplaire> ex) {
		this.marque = m;
		this.exemplaires.addAll(ex);
	}

	/**
	 * @param marque
	 */
	public Vehicule(String marque) {
		this(marque, new ArrayList<Exemplaire>());
	}

	/**
	 * @return
	 */
	public String getMarque() {
		return marque;
	}

	/**
	 * @param marque
	 */
	public void setMarque(String marque) {
		this.marque = marque;
	}

	public void setExemplaires(List<Exemplaire> exemplaires) {
		this.exemplaires = exemplaires;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Exemplaire> iterator() {
		return exemplaires.iterator();
	}

	public String toString() {
		return marque;
	}

}