package org.matthieuaudemard.location.vue.emprunteur;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.matthieuaudemard.location.controlleur.ControlleurEmprunteur;
import org.matthieuaudemard.location.modele.entitee.Adresse;
import org.matthieuaudemard.location.modele.entitee.Emprunteur;

public class TableModelEmprunteur extends AbstractTableModel {

	private static final Logger logger = LogManager.getLogger(TableModelEmprunteur.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 8096307685943874189L;
	private transient ControlleurEmprunteur ctrl;
	private static final String[] nomColonnes = { "Identifiant", "Nom", "Prénom", "Adresse" };

	public TableModelEmprunteur() {
		ctrl = new ControlleurEmprunteur();

		ctrl.find();

		addTableModelListener(evt -> {
			ctrl.sort();
			ctrl.save();
		});

	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return (col != 0);
	}

	@Override
	public int getColumnCount() {
		return nomColonnes.length;
	}

	@Override
	public int getRowCount() {
		return ctrl.count();
	}

	@Override
	public String getColumnName(int col) {
		return nomColonnes[col];
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int column) {
		return (column == 0) ? Integer.class : String.class;
	}

	@Override
	public Object getValueAt(int row, int col) {
		switch (col) {
		case 0:
			return ctrl.getValueAt(row).getPrimaryKey();
		case 1:
			return ctrl.getValueAt(row).getNomEmprunteur();
		case 2:
			return ctrl.getValueAt(row).getPrenomEmprunteur();
		case 3:
			return ctrl.getValueAt(row).getAdresseEmprunteur();
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		Emprunteur e = ctrl.getValueAt(row);
		logger.debug("TableModelEmprunteur.setValueAt()");
		switch (col) {
		case 0:
			e.setIdEmprunteur(Integer.parseInt(value.toString()));
			break;
		case 1:
			e.setNomEmprunteur(value.toString());
			break;
		case 2:
			e.setPrenomEmprunteur(value.toString());
			break;
		case 3:
			e.setAdresseEmprunteur(new Adresse(value.toString()));
			break;
		default:
			break;
		}
		ctrl.save();
		fireTableCellUpdated(row, col);
	}

	public void addRow(Emprunteur e) {
		try {
			ctrl.insert(e);
		} catch (Exception e1) {
			logger.error(e1.getStackTrace());
		}
	}

	public void removeRow(int index) {
		ctrl.delete(index);
	}

	public ControlleurEmprunteur getCtrl() {
		return ctrl;
	}
}
