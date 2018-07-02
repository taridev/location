package org.matthieuaudemard.location.modele.dao;

import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.matthieuaudemard.location.modele.dao.files.DaoFileEmprunteur;
import org.matthieuaudemard.location.modele.dao.interfaces.IDaoEmprunteur;
import org.matthieuaudemard.location.modele.entitee.Emprunteur;

import flexjson.JSONSerializer;

public class DaoFileTest {
	
	IDaoEmprunteur daoEmprunteur = null; 
			
	@BeforeClass
	public void setUp() throws Exception {
		daoEmprunteur =  (DaoFileEmprunteur) DaoFactory.getInstance("FileEmprunteur");
		daoEmprunteur.add(new Emprunteur(10, "Audemard", "Matthieu", "76 rue de la Concorde 91700 Sainte Geneviève des Bois"));
	}
	
	@Test
	public void serializeTest() {
		System.out.println(daoEmprunteur.get(10));
		System.out.println(new JSONSerializer().serialize(daoEmprunteur.get(10)));
	}

	@Test
	public void getTest() {
		System.out.println(daoEmprunteur.get(10));
		assertNull(daoEmprunteur.get(11));
		assertNull(daoEmprunteur.get(-1));
	}

}
