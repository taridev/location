package org.matthieuaudemard.location.vue.exemplaire;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;
import org.matthieuaudemard.location.controlleur.ControlleurExemplaire;
import org.matthieuaudemard.location.modele.Auto;
import org.matthieuaudemard.location.modele.Exemplaire;
import org.matthieuaudemard.location.modele.Moto;

public class TableModelExemplaire extends AbstractTableModel {
	
	final static Logger logger = Logger.getLogger(TableModelExemplaire.class);

	public ControlleurExemplaire getCtrl() {
		return ctrl;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8002268242479178842L;
	private ControlleurExemplaire ctrl;
	private static final String[] nomColonnes = { "Immatriculation", "Marque", "Type", "Modèle/Cylindrée", "Kilométrage" };

	public TableModelExemplaire() {
		ctrl = new ControlleurExemplaire();

		ctrl.find();

		addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent arg0) {
				logger.debug(
						"TableModelExemplaire.TableModelExemplaire().new TableModelListener() {...}.tableChanged()>>");
				ctrl.sort();
				ctrl.save();
				logger.debug(
						"<<TableModelExemplaire.TableModelExemplaire().new TableModelListener() {...}.tableChanged()");
			}
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
		logger.debug("TableModelExemplaire.getValueAt()");
		Exemplaire e = ctrl.getValueAt(row);
		Object result = null;

		switch (col) {
		case 0:
			result = e.getImmatriculation();
			break;
		case 1:
			result = e.getVehicule().getMarque();
			break;
		case 2:
			result = (e.getVehicule() instanceof Auto) ? "Auto" : "Moto";
			break;
		case 3:
			if (e.getVehicule() instanceof Auto) {
				result = ((Auto) e.getVehicule()).getModele();
			} else if (e.getVehicule() instanceof Moto) {
				result = ((Moto) e.getVehicule()).getCylindree();
			}
			break;
		case 4:
			result = e.getKilometrage();
			break;
		default:
			result = null;
			break;
		}

		return result;

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
			e1.printStackTrace();
		}
	}

	public void removeRow(int index) {
		ctrl.delete(index);
	}

}
