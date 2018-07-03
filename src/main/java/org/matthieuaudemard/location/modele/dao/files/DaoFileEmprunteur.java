package org.matthieuaudemard.location.modele.dao.files;

import org.matthieuaudemard.location.modele.dao.AbstractDaoFile;
import org.matthieuaudemard.location.modele.dao.interfaces.IDao;
import org.matthieuaudemard.location.modele.dao.interfaces.IDaoEmprunteur;
import org.matthieuaudemard.location.modele.entitee.Emprunteur;

public class DaoFileEmprunteur extends AbstractDaoFile<Emprunteur, Integer> implements IDaoEmprunteur, IDao<Emprunteur, Integer>{
	
	DaoFileLocation daoLocation = null;

	public DaoFileEmprunteur(String filename) {
		super(filename);
	}

	@Override
	public void add(Emprunteur element) {
		elements.add(element);
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(Emprunteur element) {
		// TODO Auto-generated method stub
		return false;
	}

}
