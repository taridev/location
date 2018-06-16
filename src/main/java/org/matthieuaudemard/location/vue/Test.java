package org.matthieuaudemard.location.vue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.matthieuaudemard.location.controlleur.ControlleurEmprunteur;
import org.matthieuaudemard.location.controlleur.ControlleurExemplaire;
import org.matthieuaudemard.location.controlleur.ControlleurLocation;
import org.matthieuaudemard.location.modele.Location;

public class Test {

	public static void main(String[] args) {
		
		ControlleurExemplaire cex = new ControlleurExemplaire();
		ControlleurEmprunteur cem = new ControlleurEmprunteur();
		
		cex.find();
		cem.find();
		
		ControlleurLocation   cel = new ControlleurLocation(cem, cex);
		
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		
		//cem.getById(2).louer(location);
		

		cel.find();
		
		try {
			cel.insert(new Location(cem.getValueAt(2), cex.getValueAt(5), format.parse("2018-12-03"), format.parse("2018-12-03"), true));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cel.save();
		
		
		System.out.println(cel);
		
	}

}
