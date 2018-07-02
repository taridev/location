package org.matthieuaudemard.location.vue.exemplaire;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;
import org.matthieuaudemard.location.controlleur.ControlleurExemplaire;
import org.matthieuaudemard.location.modele.entitee.Auto;
import org.matthieuaudemard.location.modele.entitee.Exemplaire;
import org.matthieuaudemard.location.modele.entitee.Moto;

public class TableModelExemplaire extends AbstractTableModel {

	static final Logger logger = Logger.getLogger(TableModelExemplaire.class);

	public ControlleurExemplaire getCtrl() {
		return ctrl;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8002268242479178842L;
	private transient ControlleurExemplaire ctrl;
	private static final String[] nomColonnes = { "Immatriculation", "Marque", "Type", "Modèle/Cylindrée",
			"Kilométrage" };

	public TableModelExemplaire() {
		ctrl = new ControlleurExemplaire();
		ctrl.find();

		addTableModelListener(e -> {
			ctrl.sort();
			ctrl.save();
		});
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return (col != 0 && col != 2);
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

	@Override
	public Object getValueAt(int row, int col) {

		switch (col) {
		case 0:
			return ctrl.getValueAt(row).getPrimaryKey();
		case 1:
			return ctrl.getValueAt(row).getVehicule().getMarque();
		case 2:
			return (ctrl.getValueAt(row).getVehicule() instanceof Auto) ? "Auto" : "Moto";
		case 3:
			Exemplaire e = ctrl.getValueAt(row);
			return (e.getVehicule() instanceof Auto) ? ((Auto) e.getVehicule()).getModele()
					: ((Moto) e.getVehicule()).getCylindree();
		case 4:
			return ctrl.getValueAt(row).getKilometrage();
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		Exemplaire e = ctrl.getValueAt(row);
		logger.debug("TableModelExemplaire.setValueAt()");
		switch (col) {
		case 0:
			e.setimmatriculation(value.toString());
			break;
		case 1:
			e.getVehicule().setMarque(value.toString());
			break;
		case 2:
			break;
		case 3:
			if (e.getVehicule() instanceof Auto) {
				((Auto) e.getVehicule()).setModele(value.toString());
			}
			if (e.getVehicule() instanceof Moto) {
				((Moto) e.getVehicule()).setCylindree(Integer.parseInt(value.toString()));
			}
			break;
		case 4:
			e.setKilometrage(Integer.parseInt(value.toString()));
			break;
		default:
			break;
		}
		ctrl.save();
		fireTableCellUpdated(row, col);

	}

	public void addRow(Exemplaire e) {
		try {
			ctrl.insert(e);
		} catch (Exception e1) {
			logger.error(e1.getStackTrace());
		}
	}

	public void removeRow(int index) {
		ctrl.delete(index);
	}

}
