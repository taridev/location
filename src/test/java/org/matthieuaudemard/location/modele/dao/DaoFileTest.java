package org.matthieuaudemard.location.modele.dao;

import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.matthieuaudemard.location.modele.entitee.Emprunteur;

public class DaoFileTest {
	
	DaoFileEmprunteur daoEmprunteur = null; 
			
	@Before
	public void setUp() throws Exception {
		daoEmprunteur =  (DaoFileEmprunteur) DaoFileFactory.getInstance("Emprunteur");
		daoEmprunteur.add(new Emprunteur(10, "Audemard", "Matthieu", "76 rue de la Concorde 91700 Sainte Geneviève des Bois"));
	}

	@Test
	public void getTest() {
		System.out.println(daoEmprunteur.get(11));
		assertNull(daoEmprunteur.get(11));
		assertNull(daoEmprunteur.get(-1));
	}

}
