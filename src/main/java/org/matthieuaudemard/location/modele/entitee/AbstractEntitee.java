package org.matthieuaudemard.location.modele.entitee;

public abstract class AbstractEntitee<T extends Comparable<T>> {
	
	protected T primaryKey;
	
	public final T getPrimaryKey() {
		return primaryKey;
	}
}
