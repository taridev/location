package org.matthieuaudemard.location.vue.exemplaire;

import javax.swing.JTable;

public class JTableExemplaire extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2024797364163239707L;

	public JTableExemplaire(TableModelExemplaire tableModelExemplaire) {
		super(tableModelExemplaire);
		setAutoCreateRowSorter(true);
	}

}
