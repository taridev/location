package org.matthieuaudemard.location;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.matthieuaudemard.location.modele.entitee.Adresse;
import org.matthieuaudemard.location.modele.entitee.Emprunteur;

public class EmprunteurTest {
	
	Integer id = 10;
	Emprunteur emprunteur = new Emprunteur(10, "Audemard", "Matthieu", new Adresse(8, "rue des immeubles industriels", 75011, "Paris"));

	@Test
	public void test() {
		assertEquals(id, emprunteur.getPrimaryKey());
		System.out.println(emprunteur.getPrimaryKey());
	}

}
