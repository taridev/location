package org.matthieuaudemard.location.vue.emprunteur;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import org.matthieuaudemard.location.controlleur.ControlleurEmprunteur;
import org.matthieuaudemard.location.modele.Adresse;
import org.matthieuaudemard.location.modele.Emprunteur;

public class TableModelEmprunteur extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8096307685943874189L;
	private ControlleurEmprunteur ctrl;
	private final String [] nomColonnes = {"Identifiant", "Nom", "Prénom", "Adresse" };
	
	public TableModelEmprunteur() {
		ctrl = new ControlleurEmprunteur();
		
		ctrl.find();
		
		addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent arg0) {
				System.out.println(
						"TableModelEmprunteur.TableModelEmprunteur().new TableModelListener() {...}.tableChanged()>>");
				ctrl.sort();
				ctrl.save();
				System.out.println(
						"<<TableModelEmprunteur.TableModelEmprunteur().new TableModelListener() {...}.tableChanged()");
			}
		});
		
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		if(col != 0) return true;
		return false;
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
          
            default:
                return String.class;
        }
    }

	@Override
	public Object getValueAt(int row, int col) {
		
		Emprunteur selectedEmprunteur = ctrl.getValueAt(row);
		Object result = null;
		
		switch (col) {
		case 0:
			result = selectedEmprunteur.getIdEmprunteur();
			break;
		case 1:
			result = selectedEmprunteur.getNomEmprunteur();
			break;
		case 2:
			result = selectedEmprunteur.getPrenomEmprunteur();
			break;
		case 3:
			result = selectedEmprunteur.getAdresseEmprunteur().toString();
			break;
		default:
			result = null;
			break;
		}
		return result;
	
	}
	
	@Override
	public void setValueAt(Object value, int row, int col) {
		Emprunteur e = ctrl.getValueAt(row);
		System.out.println("TableModelEmprunteur.setValueAt()");
		switch(col) {
		case 0:
			e.setIdEmprunteur(Integer.parseInt(value.toString()));
			break;
		case 1:
			e.setNomEmprunteur(value.toString());
			break;
		case 2:
			e.setPrenomEmprunteur(value.toString());
			System.out.println("prenom");
			break;
		case 3:
			e.setAdresseEmprunteur(new Adresse(value.toString()));
			break;
		default:
			break;
		}
		
		ctrl.save();
		fireTableCellUpdated(row , col);
		
	}
	
	public void addRow(Emprunteur e) {
		try {
			ctrl.insert(e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void removeRow(int index) {
		ctrl.delete(index);
	}

	public ControlleurEmprunteur getCtrl() {
		return ctrl;
	}
	

}
