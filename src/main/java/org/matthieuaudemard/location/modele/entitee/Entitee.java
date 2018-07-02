package org.matthieuaudemard.location.modele.entitee;

public abstract class Entitee<T extends Comparable<T>> {
	
	protected T primaryKey;
	
	public final T getPrimaryKey() {
		return primaryKey;
	}
}
