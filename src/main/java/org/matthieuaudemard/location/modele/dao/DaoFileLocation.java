package org.matthieuaudemard.location.modele.dao;

import java.util.List;

import org.matthieuaudemard.location.modele.entitee.Location;

public class DaoFileLocation extends AbstractDaoFile<Location, Integer> {
	
	DaoFileEmprunteur daoEmprunteur = null;
	DaoFileExemplaire daoExemplaire = null;

	public DaoFileLocation(String filename, List<String> shema) {
		super(filename, shema);
		daoEmprunteur = (DaoFileEmprunteur) DaoFileFactory.getInstance("Emprunteur");
		daoExemplaire = (DaoFileExemplaire) DaoFileFactory.getInstance("Exemplaire");
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
