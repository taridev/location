package org.matthieuaudemard.location.vue.location;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;
import org.matthieuaudemard.location.controlleur.ControlleurEmprunteur;
import org.matthieuaudemard.location.controlleur.ControlleurExemplaire;
import org.matthieuaudemard.location.controlleur.ControlleurLocation;
import org.matthieuaudemard.location.modele.entitee.Emprunteur;
import org.matthieuaudemard.location.modele.entitee.Location;

public class TableModelLocation extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -807525530848101240L;
	private transient ControlleurLocation ctrl;
	static final Logger logger = Logger.getLogger(TableModelLocation.class);

	private static final String[] nomColonnes = { "Identifiant", "Emprunteur", "Véhicule", "Assurance", "Retrait",
			"Retour prévu", "Retour" };
	private final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-mm-dd");

	public TableModelLocation(ControlleurExemplaire ctrlExemplaire, ControlleurEmprunteur ctrlEmprunteur) {
		logger.debug("Construction d'un TableModelLocation.");
		ctrl = new ControlleurLocation(ctrlEmprunteur, ctrlExemplaire);
		ctrl.find();

		addTableModelListener(evt -> {
			ctrl.sort();
			ctrl.save();
		});

	}

	@Override
	public boolean isCellEditable(int row, int col) {
		switch (col) {
		case 0:
			return false;
		case 4:
			return false;
		case 6:
			return (getValueAt(row, 6) == "");
		default:
			return true;
		}
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
		switch (column) {
		case 0:
			return Integer.class;
		case 3:
			return Boolean.class;
		default:
			return String.class;
		}
	}

	@Override
	public Object getValueAt(int row, int col) {
		Location selectedLocation = ctrl.getValueAt(row);
		Object result = null;

		switch (col) {
		case 0: // numéroLocation
			result = selectedLocation.getPrimaryKey();
			break;
		case 1: // numEmprunteur + nomEmprunteur + prénomEmprunteur
			result = selectedLocation.getEmprunteur().getPrimaryKey() + " "
					+ selectedLocation.getEmprunteur().getNomEmprunteur() + " "
					+ selectedLocation.getEmprunteur().getPrenomEmprunteur();
			break;
		case 2: // immatricuation
			result = selectedLocation.getExemplaire().getPrimaryKey();
			break;
		case 3: // assurance
			result = selectedLocation.getAssurance();
			break;
		case 4: // dateRetraitVéhicule
			result = (selectedLocation.getDateRetrait() == null ? ""
					: dateformat.format(selectedLocation.getDateRetrait()));
			break;
		case 5: // dateRetourPrévuVéhicule
			result = (selectedLocation.getDateRetourPrevue() == null ? ""
					: dateformat.format(selectedLocation.getDateRetourPrevue()));
			break;
		case 6: // dateRetour
			result = (selectedLocation.getDateRetour() == null ? ""
					: dateformat.format(selectedLocation.getDateRetour()));
			break;
		default:
			result = null;
			break;
		}
		return result;

	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		Location l = ctrl.getValueAt(row);
		logger.debug("TableModelEmprunteur.setValueAt()");
		switch (col) {
		case 0: // numéroLocation
			l.setNumeroLocation((Integer.parseInt(value.toString())));
			break;
		case 1: // numEmprunteur
			try (Scanner scanner = new Scanner(value.toString())) {
				l.setEmprunteur(ctrl.getCtrlEmprunteur().getById(scanner.nextInt()));
			} catch (NoSuchElementException | IllegalStateException e) {
				logger.error(e.getStackTrace());
			}
			break;
		case 2: // immatriculation Véhicule
			l.setExemplaire(ctrl.getCtrlExemplaire().getById(value.toString()));
			break;
		case 3: // assurance
			l.setAssurance((boolean) value);
			break;
		case 4: // dateRetraitVéhicule
			try {
				l.setDateRetrait(dateformat.parse(value.toString()));
			} catch (ParseException e) {
				logger.error(e.getStackTrace());
			}
			break;
		case 5: // dateRetourPrévuVéhicule
			try {
				l.setDateRetourPrevue(dateformat.parse(value.toString()));
			} catch (ParseException e) {
				logger.error(e.getStackTrace());
			}
			break;
		case 6: // dateRetour
			try (Scanner scan = new Scanner(getValueAt(row, 1).toString())) {
				l.setDateRetour(dateformat.parse(value.toString()));
				if (l.getDateRetour() != null) {
					@SuppressWarnings("resource")
					int eId = scan.nextInt();
					Emprunteur e = ctrl.getCtrlEmprunteur().getById(eId);
					e.ramener(ctrl.getById((int) getValueAt(row, 0)));
					logger.debug("ramenage de :" + e.getPrimaryKey());
					for (Location loc : e) {
						logger.debug("\t" + loc);
					}
				}
			} catch (ParseException e) {
				logger.error(e.getStackTrace());
			}
			break;
		default:
			break;
		}

		ctrl.save();
		fireTableCellUpdated(row, col);

	}

	public void addRow(Location l) {
		try {
			ctrl.insert(l);
		} catch (Exception e) {
			logger.error(e.getStackTrace());
		}
	}

	/**
	 * @param row
	 */
	public void removeRow(int row) {
		Scanner sc1 = new Scanner(getValueAt(row, 1).toString()); // Scanner de Emprunteur

		int emprunteurId = sc1.nextInt();
		int locationId = (int) getValueAt(row, 0);

		sc1.close();

		ctrl.getCtrlEmprunteur().getById(emprunteurId).removeLocation(locationId);
		ctrl.delete(row);

		for (Location l : ctrl.getCtrlEmprunteur().getById(emprunteurId))
			logger.debug(l);
	}

	public ControlleurLocation getCtrl() {
		return ctrl;
	}

}
