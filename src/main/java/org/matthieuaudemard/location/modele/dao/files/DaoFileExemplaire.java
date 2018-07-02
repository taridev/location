package org.matthieuaudemard.location.modele.dao.files;

import org.matthieuaudemard.location.modele.dao.AbstractDaoFile;
import org.matthieuaudemard.location.modele.dao.interfaces.IDaoExemplaire;
import org.matthieuaudemard.location.modele.entitee.Exemplaire;

public class DaoFileExemplaire extends AbstractDaoFile<Exemplaire, String> implements IDaoExemplaire {

	public DaoFileExemplaire(String filename) {
		super(filename);
	}

	@Override
	public void add(Exemplaire element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(Exemplaire element) {
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
