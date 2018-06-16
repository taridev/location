/**
 * 
 */
package org.matthieuaudemard.location.modele;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author matthieu
 * La classe Adresse définit 
 */
public class Adresse {
	/**
	 * 
	 */
	private int numRue;
	/**
	 * 
	 */
	private String nomRue;
	/**
	 * 
	 */
	private int codePostal;
	/**
	 * 
	 */
	private String nomVille;

	/**
	 * @param numRue entier correspondant au numéro de la rue
	 * @param nomRue chaîne de caractères représentant le nom de la rue ex: "rue de la paix"
	 * @param codePostal entier représentant le code postal ex: "75011"
	 * @param nomVille chaîne de caractères représentant le nom de la ville ex: "Paris"
	 */
	public Adresse(int numRue, String nomRue, int codePostal, String nomVille) {
		super();
		this.numRue = numRue;
		this.nomRue = nomRue;
		this.codePostal = codePostal;
		this.nomVille = nomVille;
	}
	
	/**
	 * Crée un objet de type Adresse en affectant -1 au numéro de la rue et au code postal
	 * et la chaîne vide au nom de la rue et de la ville
	 */
	public Adresse() {
		this(-1, "", -1, "");
	}

	
	/**
	 * Crée un objet de type Adresse à l'aide d'une chaîne de caractère fournie en paramètres
	 * @param adresseString chaîne représentant l'adresse ex: "72 rue de la roquette 75011 Paris
	 */
	public Adresse(String adresseString) {
		Scanner scan = new Scanner(adresseString);
		try {
			numRue = scan.nextInt();
			
			scan.useDelimiter("\\s\\d{3,5}");
			nomRue = scan.next().trim();
			
			scan.useDelimiter("\\s");
			codePostal = scan.nextInt();
			StringBuilder sb = new StringBuilder();
			while (scan.hasNext()) {
				sb.append(scan.next());
				sb.append(" ");
			}
			nomVille = sb.toString().trim();

			scan.close();
		} catch (NoSuchElementException | IllegalStateException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @return le numéro de la rue associé à l'objet
	 */
	public int getNumRue() {
		return numRue;
	}

	/**
	 * permet de définir le numéro de la rue de l'objet
	 * @param numRue l'entier correspondant au numéro de la rue
	 */
	public void setNumRue(int numRue) {
		this.numRue = numRue;
	}

	/**
	 * @return Chaîne de caractère correspondant au nom de la rue de l'objet
	 */
	public String getNomRue() {
		return nomRue;
	}

	/**
	 * @param nomRue le nom de la rue à définir
	 */
	public void setNomRue(String nomRue) {
		this.nomRue = nomRue;
	}

	/**
	 * @return renvoie un entier correspondant au code postal associé à l'objet
	 */
	public int getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}

	public String getNomVille() {
		return nomVille;
	}

	public void setNomVille(String nomVille) {
		this.nomVille = nomVille;
	}

	public String toString() {
		return numRue + " " + nomRue + " " + codePostal + " " + nomVille;
	}

}
