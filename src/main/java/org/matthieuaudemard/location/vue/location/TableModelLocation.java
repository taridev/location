package org.matthieuaudemard.location.vue.location;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import org.matthieuaudemard.location.controlleur.ControlleurEmprunteur;
import org.matthieuaudemard.location.controlleur.ControlleurExemplaire;
import org.matthieuaudemard.location.controlleur.ControlleurLocation;
import org.matthieuaudemard.location.modele.Emprunteur;
import org.matthieuaudemard.location.modele.Location;

public class TableModelLocation extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -807525530848101240L;
	private ControlleurLocation ctrl;
	private final String[] nomColonnes = { "Identifiant", "Emprunteur", "Véhicule", "Assurance", "Retrait",
			"Retour prévu", "Retour" };

	public TableModelLocation(ControlleurExemplaire ctrlExemplaire, ControlleurEmprunteur ctrlEmprunteur) {
		System.out.println("TableModelLocation.TableModelLocation()");
		ctrl = new ControlleurLocation(ctrlEmprunteur, ctrlExemplaire);
		ctrl.find();

		addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent arg0) {
				System.out.println(
						"TableModelLocation.TableModelLocation(...).new TableModelListener() {...}.tableChanged()>>");
				ctrl.sort();
				ctrl.save();
				System.out.println(
						"<<TableModelLocation.TableModelLocation(...).new TableModelListener() {...}.tableChanged()");
			}
		});

	}
	
	// MODIF
	
	
	/// fin modif

	@Override
	public boolean isCellEditable(int row, int col) {
		switch(col) {
			case 0:
				return false;
			case 4:
				return false;
			case 6:
				if(getValueAt(row, 6) != "")
					return false;
				return true;
			default: return true;
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
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		
		switch (col) {
		case 0: // numéroLocation
			result = selectedLocation.getNumeroLocation();
			break;
		case 1: // numEmprunteur + nomEmprunteur + prénomEmprunteur
			result = selectedLocation.getEmprunteur().getIdEmprunteur() + " "
					+ selectedLocation.getEmprunteur().getNomEmprunteur() + " "
					+ selectedLocation.getEmprunteur().getPrenomEmprunteur();
			break;
		case 2: // immatricuation
			result = selectedLocation.getExemplaire().getImmatriculation();
			break;
		case 3: // assurance
			result = selectedLocation.getAssurance();
			break;
		case 4: // dateRetraitVéhicule
			result = (selectedLocation.getDateRetrait() == null ? ""
					: format.format(selectedLocation.getDateRetrait()));
			break;
		case 5: // dateRetourPrévuVéhicule
			result = (selectedLocation.getDateRetourPrevue() == null ? ""
					: format.format(selectedLocation.getDateRetourPrevue()));
			break;
		case 6: // dateRetour
			result = (selectedLocation.getDateRetour() == null ? "" : format.format(selectedLocation.getDateRetour()));
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
		System.out.println("TableModelEmprunteur.setValueAt()");
		switch (col) {
		case 0: // numéroLocation
			l.setNumeroLocation((Integer.parseInt(value.toString())));
			break;
		case 1: // numEmprunteur
			l.setEmprunteur(ctrl.getCtrlEmprunteur().getById(new Scanner(value.toString()).nextInt()));
			break;
		case 2: // immatriculation Véhicule
			l.setExemplaire(ctrl.getCtrlExemplaire().getById(value.toString()));
			break;
		case 3: // assurance
			l.setAssurance((boolean) value);
			break;
		case 4: // dateRetraitVéhicule
			try {
				l.setDateRetrait(new SimpleDateFormat("yyyy-mm-dd").parse(value.toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		case 5: // dateRetourPrévuVéhicule
			try {
				l.setDateRetourPrevue(new SimpleDateFormat("yyyy-mm-dd").parse(value.toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		case 6: // dateRetour
			try {
				l.setDateRetour(new SimpleDateFormat("yyyy-mm-dd").parse(value.toString()));
				if(l.getDateRetour() != null) {
					@SuppressWarnings("resource")
					int eId = new Scanner(getValueAt(row, 1).toString()).nextInt();
					Emprunteur e = ctrl.getCtrlEmprunteur().getById(eId);
					e.ramener(ctrl.getById((int)getValueAt(row, 0)));
					System.out.println("ramenage de :" + e.getIdEmprunteur());
					for(Location loc : e) {
						System.out.println("\t" + loc);
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
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
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * @param row
	 */
	public void removeRow(int row) {
		Scanner sc1 = new Scanner(getValueAt(row, 1).toString()); // Scanner de Emprunteur
		
		int emprunteurId = sc1.nextInt();
		int locationId   = (int) getValueAt(row, 0);
		
		sc1.close();
		
		ctrl.getCtrlEmprunteur().getById(emprunteurId).removeLocation(locationId);
		ctrl.delete(row);
		
		for(Location l : ctrl.getCtrlEmprunteur().getById(emprunteurId))
			System.out.println(l);
	}

	public ControlleurLocation getCtrl() {
		return ctrl;
	}

}