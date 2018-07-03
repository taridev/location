package org.matthieuaudemard.location.modele.dao.interfaces;

import org.matthieuaudemard.location.modele.entitee.AbstractEntitee;

public interface IDaoFile<T extends AbstractEntitee<N>, N extends Comparable<N>> extends IDao<T, N>{

	String jsonEncode();
	void save();
	void load();
}
