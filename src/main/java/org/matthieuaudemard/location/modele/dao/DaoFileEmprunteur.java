package org.matthieuaudemard.location.modele.dao;

import java.util.List;

import org.matthieuaudemard.location.modele.entitee.Emprunteur;

public class DaoFileEmprunteur extends AbstractDaoFile<Emprunteur, Integer> {
	
	DaoFileLocation daoLocation = null;

	public DaoFileEmprunteur(String filename, List<String> shema) {
		super(filename, shema);
	}

	@Override
	public void add(Emprunteur element) {
		elements.add(element);
	}

	@Override
	public boolean delete(Emprunteur element) {
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
