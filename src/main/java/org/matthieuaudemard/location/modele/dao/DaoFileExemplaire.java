package org.matthieuaudemard.location.modele.dao;

import java.util.List;

import org.matthieuaudemard.location.modele.entitee.Exemplaire;

public class DaoFileExemplaire extends AbstractDaoFile<Exemplaire, String> {

	public DaoFileExemplaire(String filename, List<String> shema) {
		super(filename, shema);
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
