package org.matthieuaudemard.location.vue.location;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;

import org.matthieuaudemard.location.modele.entitee.Emprunteur;
import org.matthieuaudemard.location.modele.entitee.Exemplaire;

public class JTableLocation extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8285894423978289092L;

	public JTableLocation(TableModelLocation tableModelLocation) {
		super(tableModelLocation);
		JComboBox<String> comboEmprunteur = new JComboBox<>();
		JComboBox<String> comboExemplaire   = new JComboBox<>();
		
		for(Emprunteur e : ((TableModelLocation)getModel()).getCtrl().getCtrlEmprunteur() ) {
			comboEmprunteur.addItem(e.getPrimaryKey() + " " + e.getNomEmprunteur() + " " + e.getPrenomEmprunteur());
		}
		
		for(Exemplaire e : ((TableModelLocation)getModel()).getCtrl().getCtrlExemplaire() ) {
			comboExemplaire.addItem(e.getPrimaryKey());
		}
		
		comboEmprunteur.setEditable(false);
		comboExemplaire.setEditable(false);
		
		getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(comboEmprunteur));
		getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comboExemplaire));
		getColumnModel().getColumn(0).setMaxWidth(50);
		
		setAutoCreateRowSorter(true);
	}

}
