package org.matthieuaudemard.location.modele.dao;

import java.util.List;

import org.matthieuaudemard.location.modele.entitee.Entitee;

public interface Dao<T extends Entitee<N>, N extends Comparable<N>> extends Iterable<T> {
	
	Integer count();
	void load();
	void save();
	T get(N id);
	List<T> getAll();
	void add(T element);
	boolean delete(T element);
	
}
