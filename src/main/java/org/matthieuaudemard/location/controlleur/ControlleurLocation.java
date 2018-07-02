package org.matthieuaudemard.location.controlleur;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.matthieuaudemard.location.modele.entitee.Location;

/**
 * @author matthieu
 *
 */
/**
 * @author matthieu
 *
 */
public class ControlleurLocation implements Controlleur<Location>, Iterable<Location> {
	
	static final Logger logger = Logger.getLogger(ControlleurLocation.class);
	
	/**
	 * 
	 */
	private ArrayList<Location> locations = new ArrayList<>();
	private ControlleurEmprunteur ctrlEmprunteur;
	private ControlleurExemplaire ctrlExemplaire;
	
	/**
	 * 
	 */
	private String source = "location.db.txt";

	/**
	 * 
	 */
	private int lastInsertId;

	public ControlleurLocation(ControlleurEmprunteur ctrlEmprunteur, ControlleurExemplaire ctrlExemplaire) {
		lastInsertId = getLastInsertId();
		this.ctrlEmprunteur = ctrlEmprunteur;
		this.ctrlExemplaire = ctrlExemplaire;
	}

	@Override
	public int count() {
		return locations.size();
	}

	@Override
	public void find() {
		
		logger.debug("Appel de la méthode find().");
		
		File base = new File(source);
		
		locations = new ArrayList<>();
		
		try (Scanner sc1 = new Scanner(base)){
			// Ouverture d'un Scanner pour lire le fichier ligne par ligne
			int nbLigne = 0;
			lastInsertId = getLastInsertId();

			if (sc1.hasNextLine())
				sc1.nextLine();

			nbLigne++;
			
			while (sc1.hasNextLine()) {
				String ligne = sc1.nextLine();
				// Ouverture d'un scanner pour parser chaque ligne
				Scanner sc2 = new Scanner(ligne);
				nbLigne++;
				try {
					int numLocation = sc2.nextInt();
					int numEmprunteur = sc2.nextInt();
					String immatriculation = sc2.next();
					boolean assurance = sc2.next().equals("true");
					DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
					Date dateRetrait = format.parse(sc2.next());
					Date dateRetourPrevue = format.parse(sc2.next());
					Date dateRetour = (sc2.hasNext() ? format.parse(sc2.next()) : null);
					Location l = new Location(numLocation, ctrlEmprunteur.getById(numEmprunteur), ctrlExemplaire.getById(immatriculation), dateRetrait, dateRetourPrevue, dateRetour, assurance);
					locations.add(l);
					
				} catch (NoSuchElementException | IllegalStateException e) {
					logger.error("Invalid line format at line " + nbLigne + " in " + source);
				} catch (ParseException e) {
					logger.error("Invalid date format");
				} finally {
					sc2.close();
				}
			}

		} catch (NullPointerException|FileNotFoundException e) {
			logger.error(e.getMessage());
		}
		
	}
	
	/**
	 * @param index
	 * @return
	 */
	public Location findById(int index) {
		find();
		
		for(Location l : this)
			if(l.getPrimaryKey() == index)
				return l;
		
		return null;
	}
	
	
	/**
	 * @param id
	 * @return
	 */
	public List<Location> getByEmprunteurId(int id) {
		ArrayList<Location> result = new ArrayList<>();
		for(Location l : this) {
			if(l.getEmprunteur() == null) continue;
			if(l.getEmprunteur().getPrimaryKey() == id) result.add(l);
		}
		return result;
	}
	
	public Location getById(int id) {
		for(Location l : locations)
			if(l.getPrimaryKey() == id) return l;
		return null;
	}

	public ControlleurEmprunteur getCtrlEmprunteur() {
		return ctrlEmprunteur;
	}

	public ControlleurExemplaire getCtrlExemplaire() {
		return ctrlExemplaire;
	}

	@Override
	public void update(Location element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int index) {
		locations.remove(index);
		
		save();
	}

	@Override
	public void insert(Location element) throws Exception {
		
		logger.debug("Appel de la méthode insert()." + element);


		// Si l'identifiant de l'emprunteur n'est pas encore défini
		if (element.getPrimaryKey() == -1) {
			element.setNumeroLocation(++lastInsertId);
			element.getEmprunteur().louer(element);
		}

		// Si l'élément à insérer existe déjà dans la liste alors Exception
		else if (locations.contains(element))
			throw new Exception("Unable to insert element " + element + ". Already exists");

		// S'il existe déjà un emprunteur associé à cet identifiant alors Exception
		else if (getById(element.getPrimaryKey()) != null)
			throw new Exception(
					"Unable to insert element with index:" + element.getPrimaryKey() + ". Index Already exists");

		// Insertion dans la liste
		locations.add(element);
		sort();

		logger.debug("<<ControlleurEmprunteur.insert()");
	}

	@Override
	public void save(String pathfile) {
		logger.debug("Saving ...");
		
		try (
			Scanner scanFile =
				new Scanner(pathfile);
			FileWriter fstream =
				new FileWriter(pathfile);
			BufferedWriter out =
				new BufferedWriter(fstream)){
			StringBuilder result = new StringBuilder();
			result.append(lastInsertId + "\n");
			result.append(this.toString());
			out.write(result.deleteCharAt(result.length() - 1).toString());

		} catch (IOException e1) {
			logger.error("IOException while trying to export emprunteurs to " + pathfile);
		}
		
		ctrlEmprunteur.save();
		
	}

	/**
	 * 
	 */
	public void save() {
		save(source);
	}

	@Override
	public void sort() {
		Collections.sort(
				locations, 
				(Location loc1, Location loc2) -> loc1.getPrimaryKey().compareTo(loc2.getPrimaryKey())
		);
	}

	@Override
	public Location getValueAt(int index) {
		return locations.get(index);
	}
	
	/**
	 * @return
	 */
	public int getLastInsertId() {
		File input = new File(source);
		Scanner sc;
		try {
			sc = new Scanner(input);
			int lastInsertIdFound = sc.nextInt();
			sc.close();
			return lastInsertIdFound;
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			return -1;
		}

	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		for (Location e : locations)
			result.append(e.toString() + "\n");

		return result.toString();

	}
	
	@Override
	public Iterator<Location> iterator() {
		return locations.iterator();
	}

}
