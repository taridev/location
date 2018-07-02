package org.matthieuaudemard.location.modele.dao;

import java.util.ArrayList;

public class DaoFileFactory {
	
	private static DaoFileEmprunteur daoEmprunteur;
	private static DaoFileExemplaire daoExemplaire;
	private static DaoFileLocation   daoLocation;
	
	private DaoFileFactory() { }
	
	@SuppressWarnings({ "rawtypes" })
	public static AbstractDaoFile getInstance(String classname) {
		
		if(classname.compareToIgnoreCase("Emprunteur") == 0 ) {
			if(daoEmprunteur == null) {
				daoEmprunteur = new DaoFileEmprunteur("emprunteur.db.txt", new ArrayList<String>());
			}
			return daoEmprunteur;
		} else if(classname.compareToIgnoreCase("Location") == 0) {
			if(daoLocation == null) {
				daoLocation = new DaoFileLocation("location.db.txt", new ArrayList<String>());
			}
			return daoLocation;
		} else if(classname.compareToIgnoreCase("Exemplaire") == 0) {
			if(daoExemplaire == null) {
				daoExemplaire = new DaoFileExemplaire("exemplaire.db.txt", new ArrayList<String>());
			}
			return daoExemplaire;
		}	
		
		return null;
		
	}

}
