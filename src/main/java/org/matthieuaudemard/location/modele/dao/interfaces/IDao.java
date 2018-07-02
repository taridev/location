package org.matthieuaudemard.location.modele.dao.interfaces;

import java.util.List;

import org.matthieuaudemard.location.modele.entitee.AbstractEntitee;

public interface IDao<T extends AbstractEntitee<N>, N extends Comparable<N>> extends Iterable<T> {
	
	Integer count();
	void load();
	void save();
	T get(N id);
	List<T> getAll();
	void add(T element);
	boolean delete(T element);
	
}