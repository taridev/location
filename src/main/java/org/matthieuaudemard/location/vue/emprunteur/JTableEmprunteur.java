package org.matthieuaudemard.location.vue.emprunteur;

import javax.swing.JTable;

public class JTableEmprunteur extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2957700832472602472L;

	public JTableEmprunteur(TableModelEmprunteur tableModelEmprunteur) {
		super(tableModelEmprunteur);
		getColumnModel().getColumn(0).setMaxWidth(80);

		setAutoCreateRowSorter(true);
	}

}
