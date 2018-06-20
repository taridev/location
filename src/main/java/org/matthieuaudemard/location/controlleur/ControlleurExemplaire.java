package org.matthieuaudemard.location.controlleur;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.matthieuaudemard.location.modele.Auto;
import org.matthieuaudemard.location.modele.Emprunteur;
import org.matthieuaudemard.location.modele.Exemplaire;
import org.matthieuaudemard.location.modele.Moto;

public class ControlleurExemplaire implements Controlleur<Exemplaire>, Iterable<Exemplaire> {

	/**
	 * 
	 */
	ArrayList<Exemplaire> exemplaires = new ArrayList<>();

	/**
	 * 
	 */
	private String source = "exemplaire.db.txt";

	public ControlleurExemplaire() {
		// Empty Constructor
	}

	@Override
	public int count() {
		return exemplaires.size();
	}
	
	public int countAuto() {
		return getAuto().size();
	}
	
	public int countMoto() {
		return getMoto().size();
	}
	
	
	private void parseLine(String ligne) {
		Scanner sc2 = new Scanner(ligne);
		String immatriculation = sc2.next();
		String marque = sc2.next();
		String type = sc2.next();
		if (type.equalsIgnoreCase("Auto")) {
			exemplaires.add(new Exemplaire(immatriculation, new Auto(marque, sc2.next()), sc2.nextInt()));
		}
		else if (type.equalsIgnoreCase("Moto")) {
			exemplaires.add(new Exemplaire(immatriculation, new Moto(marque, sc2.nextInt()), sc2.nextInt()));
		}
		sc2.close();
	}

	@Override
	public void find() {
		System.out.println("ControlleurExemplaire.find()>>");
		exemplaires = new ArrayList<>();
		File base = new File(source);
		int nbLigne = 0;
		// Ouverture d'un Scanner pour lire le fichier ligne par ligne
		try (Scanner sc1 = new Scanner(base);){
			nbLigne++;

			while (sc1.hasNextLine()) {
				String ligne = sc1.nextLine();
				// Ouverture d'un scanner pour parser chaque ligne
				parseLine(ligne);

				nbLigne++;
			}

		} catch (NullPointerException|FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchElementException | IllegalStateException e) {
			System.err.println("Trying to retrieve Exemplaire data from " + source + "Invalid line format at line " + nbLigne);
		}
		System.out.println("<<ControlleurEmprunteur.find()");

	}

	/**
	 * @param id
	 * @return
	 */
	public Exemplaire findById(String immatriculation) {
		find();
		for (Exemplaire e : exemplaires)
			if (e.getImmatriculation().equals(immatriculation))
				return e;
		return null;
	}

	public ArrayList<Exemplaire> getAuto() {
		ArrayList<Exemplaire> result = null;

		for (Exemplaire e : exemplaires) {
			if (e.getVehicule() instanceof Auto) {
				if (result == null)
					result = new ArrayList<Exemplaire>();
				result.add(e);
			}
		}

		return result;

	}

	public ArrayList<Exemplaire> getMoto() {
		ArrayList<Exemplaire> result = null;

		for (Exemplaire e : exemplaires) {
			if (e.getVehicule() instanceof Moto) {
				if (result == null)
					result = new ArrayList<>();
				result.add(e);
			}
		}

		return result;
	}
	
	public Exemplaire getById(String immatriculation) {
		for(Exemplaire e : this) 
			if(e.getImmatriculation().equals(immatriculation)) return e;
		return null;
	}

	@Override
	public void update(Exemplaire element) {
		
		System.out.println("ControlleurExemplaire.update()");
		// On recherche l'immatriculation de l'élement à mettre à jour
		for (Exemplaire e : exemplaires)
			if (e.getImmatriculation() == element.getImmatriculation())
				e = element;

		save();
	}

	@Override
	public void delete(int index) {
		exemplaires.remove(index);
	}

	@Override
	public void insert(Exemplaire element) throws Exception {

		System.out.println("ControlleurExemplaire.insert()>>");

		// Si l'élément à insérer existe déjà dans la liste alors Exception
		if (exemplaires.contains(element))
			throw new Exception("Unable to insert element " + element + ". Already exists");

		// S'il existe déjà un exemplaire associé à cette immatriculation alors Exception
		else if (getById(element.getImmatriculation()) != null)
			throw new Exception(
					"Unable to insert element with index:" + element.getImmatriculation() + ". Index Already exists");

		// Insertion dans la liste
		exemplaires.add(element);
		sort();

		// et sauvegarde dans la base
		save();

		System.out.println("<<ControlleurExemplaire.insert()");

	}

	@Override
	public void save(String pathfile) {
		System.out.println("Saving ...");
		

		try (Scanner scanFile =
				new Scanner(pathfile);
			FileWriter fstream =
				new FileWriter(pathfile);
			BufferedWriter out =
				new BufferedWriter(fstream)){
			StringBuilder result =
				new StringBuilder();
			for (Exemplaire e : exemplaires) {
				System.out.println("Saving id " + e.getImmatriculation() + "...");
				result.append(e.toString() + "\n");
			}
			out.write(result.deleteCharAt(result.length() - 1).toString());

		} catch (IOException e1) {
			System.err.println("IOException while trying to export emprunteurs to " + pathfile);
		}
	}

	public void save() {
		save(source);
	}

	@Override
	public void sort() {
		Collections.sort(exemplaires, (Exemplaire e1, Exemplaire e2)->e1.getImmatriculation().compareTo(e2.getImmatriculation()));
	}

	@Override
	public Exemplaire getValueAt(int index) {
		return exemplaires.get(index);
	}

	@Override
	public String toString() {
		System.out.println("ControlleurExemplaire.toString()>>");
		StringBuilder result = new StringBuilder();

		for (Exemplaire e : exemplaires) {
			System.out.println(e);
			result.append(e.toString() + "\n");
		}
		
		System.out.println("<<ControlleurExemplaire.toString()");
		return result.deleteCharAt(result.length() - 1).toString();
	}

	@Override
	public Iterator<Exemplaire> iterator() {
		return exemplaires.iterator();
	}

}
