package org.matthieuaudemard.location.controlleur;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.matthieuaudemard.location.modele.entitee.Auto;
import org.matthieuaudemard.location.modele.entitee.Exemplaire;
import org.matthieuaudemard.location.modele.entitee.Moto;

public class ControlleurExemplaire implements Controlleur<Exemplaire>, Iterable<Exemplaire> {

	static final Logger logger = Logger.getLogger(ControlleurExemplaire.class);

	/**
	 * 
	 */
	List<Exemplaire> exemplaires = new ArrayList<>();

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
		return getAutos().size();
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
		} else if (type.equalsIgnoreCase("Moto")) {
			exemplaires.add(new Exemplaire(immatriculation, new Moto(marque, sc2.nextInt()), sc2.nextInt()));
		}
		sc2.close();
	}

	@Override
	public void find() {
		logger.debug("Appel de la méthode find.");
		exemplaires = new ArrayList<>();
		File base = new File(source);
		int nbLigne = 0;
		// Ouverture d'un Scanner pour lire le fichier ligne par ligne
		try (Scanner sc1 = new Scanner(base);) {
			nbLigne++;

			while (sc1.hasNextLine()) {
				String ligne = sc1.nextLine();
				// Ouverture d'un scanner pour parser chaque ligne
				parseLine(ligne);

				nbLigne++;
			}

		} catch (NullPointerException | FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (NoSuchElementException | IllegalStateException e) {
			logger.error(
					"Trying to retrieve Exemplaire data from " + source + "Invalid line format at line " + nbLigne);
		}
	}

	/**
	 * @param id
	 * @return
	 */
	public Exemplaire findById(String immatriculation) {
		find();
		for (Exemplaire e : exemplaires)
			if (e.getPrimaryKey().equals(immatriculation))
				return e;
		return null;
	}

	public List<Exemplaire> getAutos() {
		return exemplaires.stream()
				.filter(e -> e.getVehicule() instanceof Auto)
				.collect(Collectors.toList());
	}

	public List<Exemplaire> getMoto() {
		return exemplaires.stream()
				.filter(e -> e.getVehicule() instanceof Moto)
				.collect(Collectors.toList());
	}

	public Exemplaire getById(String immatriculation) {
		for (Exemplaire e : this)
			if (e.getPrimaryKey().equals(immatriculation))
				return e;
		return null;
	}

	@Override
	public void update(Exemplaire element) {
		logger.debug(element);
		
		// On recherche l'immatriculation de l'élement à mettre à jour
		exemplaires.stream()
			.filter(e -> e.getPrimaryKey() == element.getPrimaryKey())
			.forEach(e -> e.update(element));

		save();
	}

	@Override
	public void delete(int index) {
		exemplaires.remove(index);
	}

	@Override
	public void insert(Exemplaire element) throws Exception {

		logger.debug("ControlleurExemplaire.insert()>>");

		// Si l'élément à insérer existe déjà dans la liste alors Exception
		if (exemplaires.contains(element))
			throw new Exception("Unable to insert element " + element + ". Already exists");

		// S'il existe déjà un exemplaire associé à cette immatriculation alors
		// Exception
		else if (getById(element.getPrimaryKey()) != null)
			throw new Exception(
					"Unable to insert element with index:" + element.getPrimaryKey() + ". Index Already exists");

		// Insertion dans la liste
		exemplaires.add(element);
		sort();

		// et sauvegarde dans la base
		save();

		logger.debug("<<ControlleurExemplaire.insert()");

	}

	@Override
	public void save(String pathfile) {
		logger.debug("Saving ...");

		try (Scanner scanFile = new Scanner(pathfile);
				FileWriter fstream = new FileWriter(pathfile);
				BufferedWriter out = new BufferedWriter(fstream)) {
			StringBuilder result = new StringBuilder();
			for (Exemplaire e : exemplaires) {
				logger.debug("Saving id " + e.getPrimaryKey() + "...");
				result.append(e.toString() + "\n");
			}
			out.write(result.deleteCharAt(result.length() - 1).toString());

		} catch (IOException e1) {
			logger.error("IOException while trying to export emprunteurs to " + pathfile);
		}
	}

	public void save() {
		save(source);
	}

	@Override
	public void sort() {
		Collections.sort(exemplaires,
				(Exemplaire e1, Exemplaire e2) -> e1.getPrimaryKey().compareTo(e2.getPrimaryKey()));
	}

	@Override
	public Exemplaire getValueAt(int index) {
		return exemplaires.get(index);
	}

	@Override
	public String toString() {
		logger.debug("ControlleurExemplaire.toString()>>");
		StringBuilder result = new StringBuilder();

		for (Exemplaire e : exemplaires) {
			logger.info(e);
			result.append(e.toString() + "\n");
		}

		logger.debug("<<ControlleurExemplaire.toString()");
		return result.deleteCharAt(result.length() - 1).toString();
	}

	@Override
	public Iterator<Exemplaire> iterator() {
		return exemplaires.iterator();
	}

}
