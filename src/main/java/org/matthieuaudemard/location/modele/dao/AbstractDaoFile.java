package org.matthieuaudemard.location.modele.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.matthieuaudemard.location.modele.dao.interfaces.IDao;
import org.matthieuaudemard.location.modele.entitee.AbstractEntitee;


public abstract class AbstractDaoFile<T extends AbstractEntitee<N>, N extends Comparable<N>> implements IDao<T, N> {

	protected List<T> elements = null;
	File file = null;
	
	public AbstractDaoFile(String filename) {
		elements = new ArrayList<>();
		file = new File(filename);
		load();
	}

	@Override
	public Integer count() { return (elements == null) ? null : elements.size(); }

	@Override
	public T get(N id) {
		if(count() == null || count() == 0) return null;
		return elements.stream()
				.filter(element -> element.getPrimaryKey().compareTo(id) == 0)
				.findFirst()
				.orElse(null);
	}	
	
	@Override
	public List<T> getAll() { return elements; }

	@Override
	public Iterator<T> iterator() { return elements.iterator(); }
	
}
