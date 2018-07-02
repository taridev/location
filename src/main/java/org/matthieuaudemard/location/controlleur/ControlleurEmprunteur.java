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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.matthieuaudemard.location.modele.entitee.Emprunteur;

/**
 * @author matthieu
 * Fourni les méthodes de manipulation d'une ArrayList d'emprunteurs. Représente l'ensemble
 * des Emprunteurs référencés dans la base.
 * Les données proviennent d'un fichier du format suivant:
 * lastInsertID
 * numEmprunteur nomEmprunter prenomEmprunteur numRueEmprunteur adresseEmprunteur codePostalEmprunteur villeEmprunteur
 */
public class ControlleurEmprunteur implements Controlleur<Emprunteur>, Iterable<Emprunteur> {
	
	private static final Logger logger = LogManager.getLogger(ControlleurEmprunteur.class);

	/**
	 * la liste de tous les emprunteurs
	 */
	private ArrayList<Emprunteur> emprunteurs = new ArrayList<>();
	/**
	 * le chemin du fichier de base de données
	 */
	private String source = "emprunteur.db.txt";

	/**
	 * dernier indice d'emprunteur (permet un auto-increment)
	 */
	private int lastInsertId;
	
	/**
	 * initialise le lastInsertID
	 */
	public ControlleurEmprunteur() {
		lastInsertId = getLastInsertId();
	}

	/*
	 * (non-Javadoc)
	 * renvoi le nombre d'emprunteurs présents dans la liste emprunteurs
	 * @see controlleur.Controlleur#count()
	 */
	@Override
	public int count() {
		return emprunteurs.size();
	}

	/*
	 * (non-Javadoc)
	 * Récupère les données dans la base et met à jour la liste emprunteurs avec les données trouvées
	 * dans le fichier
	 * @see controlleur.Controlleur#find(java.lang.String)
	 */
	@Override
	public void find() {

		logger.debug("Appel de la méthode find.");
		File base = new File(source);

		emprunteurs = new ArrayList<>();

		int nbLigne = 0;
		
		try (Scanner sc1 = new Scanner(base);){
			// Ouverture d'un Scanner pour lire le fichier ligne par ligne
			
			lastInsertId = getLastInsertId();

			if (sc1.hasNextLine())
				sc1.nextLine();

			nbLigne++;
			
			while (sc1.hasNextLine()) {
				String ligne = sc1.nextLine();
				// Ouverture d'un scanner pour parser chaque ligne
				
				nbLigne++;
				parseLine(ligne);
			}

		} catch (NullPointerException|FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (NoSuchElementException | IllegalStateException e) {
			logger.error("Invalid line format at line " + nbLigne + " in " + source);
		}
	}
	
	private void parseLine(String ligne) {
		try (Scanner sc2 = new Scanner(ligne)) {
			Emprunteur e = new Emprunteur(sc2.nextInt(), sc2.next(), sc2.next(),
					sc2.useDelimiter("\\r").next());
			emprunteurs.add(e); // Création d'un emprunteur et ajout à la liste
		}
	}

	/**
	 * Recherche l'emprunteur correspondant à l'id "id" dans le fichier
	 * @param id identifiant de l'emprunteur dans la base
	 * @return l'emprunteur correspondant ou null si il n'existe pas
	 */
	public Emprunteur findById(int id) {
		logger.debug("ControlleurEmprunteur.findById()>>");
		
		find();
		
		for (Emprunteur e : emprunteurs) {
			if (e.getPrimaryKey() == id) {
				logger.debug("<<ControlleurEmprunteur.findById()");
				return e;
			}
		}
		
		logger.debug("<<ControlleurEmprunteur.findById()");
		return null;
	}
	
	/**
	 * Recherche l'emprunteur correspondant à l'id "id" dans l'ArraysList emprunteurs
	 * @param id identifiant de l'emprunteur dans la base
	 * @return l'emprunteur correspondant ou null si il n'existe pas
	 */
	public Emprunteur getById(int id) {
		for(Emprunteur e : this) 
			if(e.getPrimaryKey() == id) return e;
		return null;
	}

	/*
	 * (non-Javadoc)
	 * Met à jour l'emprunteur correspondant à element.id
	 * @see controlleur.Controlleur#update(java.lang.Object)
	 */
	@Override
	public void update(Emprunteur element) {

		// On recherche l'id de l'élement à mettre à jour
		for (Emprunteur e : emprunteurs)
			if (e.getPrimaryKey() == element.getPrimaryKey())
				e = new Emprunteur(element);

	}

	/*
	 * (non-Javadoc)
	 * Supprime un emprunteur dans l'ArrayList emprunteurs
	 * @see controlleur.Controlleur#delete(int)
	 */
	@Override
	public void delete(int id) {
		emprunteurs.remove(id);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controlleur.Controlleur#insert(java.lang.Object)
	 */
	@Override
	public void insert(Emprunteur element) throws Exception {

		logger.debug("ControlleurEmprunteur.insert()>>");


		// Si l'identifiant de l'emprunteur n'est pas encore défini
		if (element.getPrimaryKey() == -1) {
			element.setIdEmprunteur(++lastInsertId);
		}

		// Si l'élément à insérer existe déjà dans la liste alors Exception
		else if (emprunteurs.contains(element))
			throw new Exception("Unable to insert element " + element + ". Already exists");

		// S'il existe déjà un emprunteur associé à cet identifiant alors Exception
		else if (getById(element.getPrimaryKey()) != null)
			throw new Exception(
					"Unable to insert element with index:" + element.getPrimaryKey() + ". Index Already exists");

		// Insertion dans la liste
		emprunteurs.add(element);
		sort();

		logger.debug("<<ControlleurEmprunteur.insert()");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controlleur.Controlleur#save(java.lang.String)
	 */
	@Override
	public void save(String pathfile) {
		
		logger.debug("Saving ...");
		
		try (Scanner scanFile =
				new Scanner(pathfile);
			FileWriter fstream =
				new FileWriter(pathfile);
			BufferedWriter out =
				new BufferedWriter(fstream);){
			StringBuilder result = new StringBuilder();
			result.append(lastInsertId + "\n");
			for (Emprunteur e : emprunteurs) {
				logger.debug("Saving id " + e.getPrimaryKey() + "...");
				result.append(e.toString() + "\n");
			}
			out.write(result.deleteCharAt(result.length() - 1).toString());
		} catch (IOException e1) {
			logger.error("IOException while trying to export emprunteurs to " + pathfile);
		}
	}

	/**
	 * 
	 */
	public void save() {
		save(source);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		for (Emprunteur e : emprunteurs)
			result.append(e.toString() + "\n");

		return result.deleteCharAt(result.length() - 1).toString();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controlleur.Controlleur#sort()
	 */
	@Override
	public void sort() {
		Collections.sort(emprunteurs, (Emprunteur e1, Emprunteur e2)->e1.getPrimaryKey() - e2.getPrimaryKey());
	}

	
	/**
	 * @return Le dernier indice de l'auto-incrémente
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see controlleur.Controlleur#getValueAt(int)
	 */
	@Override
	public Emprunteur getValueAt(int index) {
		return emprunteurs.get(index);
	}

	@Override
	public Iterator<Emprunteur> iterator() {
		return emprunteurs.iterator();
	}

}
