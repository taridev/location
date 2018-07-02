package org.matthieuaudemard.location.modele.dao;

import org.matthieuaudemard.location.modele.dao.files.DaoFileEmprunteur;
import org.matthieuaudemard.location.modele.dao.files.DaoFileExemplaire;
import org.matthieuaudemard.location.modele.dao.files.DaoFileLocation;
import org.matthieuaudemard.location.modele.dao.interfaces.IDao;

public class DaoFactory {
	
	private static DaoFileEmprunteur daoEmprunteur;
	private static DaoFileExemplaire daoExemplaire;
	private static DaoFileLocation   daoLocation;
	
	private DaoFactory() { }
	
	@SuppressWarnings({ "rawtypes" })
	public static IDao getInstance(String classname) {
		
		if(classname.compareToIgnoreCase("FileEmprunteur") == 0 ) {
			if(daoEmprunteur == null) {
				daoEmprunteur = new DaoFileEmprunteur("emprunteur.db.txt");
			}
			return daoEmprunteur;
		} else if(classname.compareToIgnoreCase("FileLocation") == 0) {
			if(daoLocation == null) {
				daoLocation = new DaoFileLocation("location.db.txt");
			}
			return daoLocation;
		} else if(classname.compareToIgnoreCase("FileExemplaire") == 0) {
			if(daoExemplaire == null) {
				daoExemplaire = new DaoFileExemplaire("exemplaire.db.txt");
			}
			return daoExemplaire;
		}	
		
		return null;
		
	}

}
