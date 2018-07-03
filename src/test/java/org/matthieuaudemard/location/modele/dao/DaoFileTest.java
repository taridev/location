package org.matthieuaudemard.location.modele.dao;

import org.junit.Before;
import org.junit.Test;
import org.matthieuaudemard.location.modele.dao.files.DaoFileEmprunteur;
import org.matthieuaudemard.location.modele.dao.interfaces.IDaoEmprunteur;
import org.matthieuaudemard.location.modele.entitee.Emprunteur;

public class DaoFileTest {
	
	IDaoEmprunteur daoEmprunteur = null; 
			
	@Before
	public void setUp() throws Exception {
		daoEmprunteur =  (DaoFileEmprunteur) DaoFactory.getInstance("FileEmprunteur");
		daoEmprunteur.add(new Emprunteur(10, "Audemard", "Matthieu", "76 rue de la Concorde 91700 Sainte Geneviève des Bois"));
		daoEmprunteur.add(new Emprunteur(11, "Courtin", "Marie-Claude", "7 rue Daubenton 91700 Sainte Genevièvre des Bois"));
	}
	
	@Test
	public void serializeTest() {
		((DaoFileEmprunteur) daoEmprunteur).save();
	}

}
