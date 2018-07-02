package org.matthieuaudemard.location.modele.dao.files;

import org.matthieuaudemard.location.modele.dao.AbstractDaoFile;
import org.matthieuaudemard.location.modele.dao.DaoFactory;
import org.matthieuaudemard.location.modele.dao.interfaces.IDaoLocation;
import org.matthieuaudemard.location.modele.entitee.Location;

public class DaoFileLocation extends AbstractDaoFile<Location, Integer> implements IDaoLocation {
	
	DaoFileEmprunteur daoEmprunteur = null;
	DaoFileExemplaire daoExemplaire = null;

	public DaoFileLocation(String filename) {
		super(filename);
		daoEmprunteur = (DaoFileEmprunteur) DaoFactory.getInstance("Emprunteur");
		daoExemplaire = (DaoFileExemplaire) DaoFactory.getInstance("Exemplaire");
	}

	@Override
	public void add(Location element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(Location element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

}
