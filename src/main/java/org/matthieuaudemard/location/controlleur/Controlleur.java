package org.matthieuaudemard.location.controlleur;

public interface Controlleur<T> {

	/**
	 * @return le nombre d'éléments dans la liste
	 */
	public int count();
	
	/**
	 * Met à jour le Controller à l'aide des données stockées dans la base
	 */
	public void find();
	
	/**
	 * Met à jour l'élément correspondant 
	 * @param element
	 */
	public void update(T element);
	
	/**
	 * @param id
	 */
	public void delete(int id);
	
	/**
	 * @param element
	 * @throws Exception 
	 */
	public void insert(T element) throws Exception;
	
	/**
	 * @param pathfile
	 */
	public void save(String pathfile);	
	
	/**
	 * 
	 */
	public void sort();
	
	/**
	 * @param index
	 * @return
	 */
	public T getValueAt(int index);
	
}
