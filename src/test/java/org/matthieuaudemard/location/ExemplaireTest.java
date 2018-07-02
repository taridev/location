package org.matthieuaudemard.location;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.matthieuaudemard.location.modele.entitee.Exemplaire;

public class ExemplaireTest {

	String immatriculation = new String("ARK-203-BB");
	Exemplaire e = new Exemplaire(immatriculation, null, 220);
	
	@Test
	public void test() {
		assertEquals(immatriculation, e.getPrimaryKey());
		System.out.println("immatriculation : " + e.getPrimaryKey());
	}

}
