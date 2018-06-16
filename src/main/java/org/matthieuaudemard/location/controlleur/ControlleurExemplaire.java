package org.matthieuaudemard.location.controlleur;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.matthieuaudemard.location.modele.Auto;
import org.matthieuaudemard.location.modele.Exemplaire;
import org.matthieuaudemard.location.modele.Moto;

public class ControlleurExemplaire implements Controlleur<Exemplaire>, Iterable<Exemplaire> {

	/**
	 * 
	 */
	ArrayList<Exemplaire> exemplaires = new ArrayList<Exemplaire>();

	/**
	 * 
	 */
	private String source = "exemplaire.db.txt";

	public ControlleurExemplaire() {

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

	@Override
	public void find() {
		System.out.println("ControlleurExemplaire.find()>>");
		exemplaires = new ArrayList<Exemplaire>();
		File base = new File(source);
		try {
			// Ouverture d'un Scanner pour lire le fichier ligne par ligne
			Scanner sc1 = new Scanner(base);
			int nbLigne = 0;

			nbLigne++;

			while (sc1.hasNextLine()) {
				String ligne = sc1.nextLine();
				// Ouverture d'un scanner pour parser chaque ligne
				Scanner sc2 = new Scanner(ligne);
				String immatriculation = sc2.next();
				String marque = sc2.next();
				String type = sc2.next();
				nbLigne++;
				try {
					switch (type) {
					case "Auto":
						exemplaires.add(new Exemplaire(immatriculation, new Auto(marque, sc2.next()), sc2.nextInt()));
						break;
					case "Moto":
						exemplaires.add(new Exemplaire(immatriculation, new Moto(marque, sc2.nextInt()), sc2.nextInt()));
						break;
					}

				} catch (NoSuchElementException | IllegalStateException e) {
					System.err.println("Trying to retrieve Exemplaire data from " + source + "Invalid line format at line " + nbLigne);
				}

				sc2.close();
			}

			sc1.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
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
					result = new ArrayList<Exemplaire>();
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
		Scanner scanFile = new Scanner(pathfile);
		FileWriter fstream;

		try {
			fstream = new FileWriter(pathfile);
			BufferedWriter out = new BufferedWriter(fstream);
			StringBuilder result = new StringBuilder();
			for (Exemplaire e : exemplaires) {
				System.out.println("Saving id " + e.getImmatriculation() + "...");
				result.append(e.toString() + "\n");
			}
			out.write(result.deleteCharAt(result.length() - 1).toString());
			out.close();
			scanFile.close();

		} catch (IOException e1) {
			System.err.println("IOException while trying to export emprunteurs to " + pathfile);
		}
	}

	public void save() {
		save(source);
	}

	@Override
	public void sort() {
		Collections.sort(exemplaires, new Comparator<Exemplaire>() {

			@Override
			public int compare(Exemplaire arg0, Exemplaire arg1) {
				return arg0.getImmatriculation().compareTo(arg1.getImmatriculation());
			}
		});
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
