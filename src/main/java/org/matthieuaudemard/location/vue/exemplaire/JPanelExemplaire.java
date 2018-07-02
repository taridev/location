package org.matthieuaudemard.location.vue.exemplaire;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.print.PrinterException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.Logger;
import org.matthieu.location.presentation.AbstractTablePanel;
import org.matthieuaudemard.location.modele.entitee.Auto;
import org.matthieuaudemard.location.modele.entitee.Exemplaire;
import org.matthieuaudemard.location.modele.entitee.Moto;

@SuppressWarnings("serial")
public class JPanelExemplaire extends AbstractTablePanel {

	static final Logger logger = Logger.getLogger(JPanelExemplaire.class);

	public TableModelExemplaire getTableModel() {
		return (TableModelExemplaire) table.getModel();
	}

	public JPanelExemplaire(Window parent) {
		super(parent, new JTableExemplaire(new TableModelExemplaire()));
		this.wParent = parent;
		strOptions.add("Tout selectionner");
		strOptions.add("Auto");
		strOptions.add("Moto");
		prepareGUI();
		prepareActionListener();
	}
	
	@Override
	protected void prepareGUI() {
		super.prepareGUI();
		txtSearch.setVisible(false);
		panelOption.add(comboFiltre);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void prepareActionListener() {

		btnAdd.addActionListener(e -> {
			DialogExemplaire d = new DialogExemplaire(wParent);
			d.setVisible(true);
		});

		btnDel.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();

			if (selectedRow > -1) {
				TableModelExemplaire model = ((TableModelExemplaire) table.getModel());
				model.removeRow(selectedRow);
				model.fireTableDataChanged();
			}
		});

		btnPrint.addActionListener(evt -> {
			try {
				table.print();
			} catch (PrinterException e) {
				logger.error(e.getStackTrace());
			}
		});

		btnPDF.addActionListener(e -> logger.info("Handle a click button PDF"));

		comboFiltre.addItemListener(e -> {

			switch (e.getItem().toString()) {
			case "Moto":
				RowFilter<Object, Object> filterMoto = new RowFilter<Object, Object>() {
					public boolean include(Entry<?, ?> entry) {
						return ((String) entry.getValue(2)).equals("Moto");
					}
				};
				((TableRowSorter<TableModelExemplaire>) table.getRowSorter()).setRowFilter(filterMoto);
				break;
			case "Auto":
				RowFilter<Object, Object> filterAuto = new RowFilter<Object, Object>() {
					public boolean include(Entry<?, ?> entry) {
						return ((String) entry.getValue(2)).equals("Auto");
					}
				};
				((TableRowSorter<TableModelExemplaire>) table.getRowSorter()).setRowFilter(filterAuto);
				break;
			default:
				RowFilter<Object, Object> filterAll = new RowFilter<Object, Object>() {
					public boolean include(Entry<?, ?> entry) {
						return true;
					}
				};
				((TableRowSorter<TableModelExemplaire>) table.getRowSorter()).setRowFilter(filterAll);
				break;
			}
		});

	}

	class DialogExemplaire extends JDialog {

		private static final String MOTO = "Moto";
		private static final String AUTO = "Auto";

		/**
		 * 
		 */
		private static final long serialVersionUID = 81849338570652284L;

		String[] listeType = { "Auto", "Moto" };

		JTextField txtImmatriculation = new JTextField(16);
		JTextField txtMarque = new JTextField(16);
		JComboBox<String> comboType = new JComboBox<>(listeType);
		JTextField txtModel = new JTextField(16);
		JTextField txtKilometrage = new JTextField(16);

		JLabel lblModele = new JLabel();

		JButton btnCreer = new JButton("OK");
		JButton btnAnnuler = new JButton("Annuler");

		JPanel pnlControls = new JPanel();
		JPanel pnlFields = new JPanel();

		Window wParent;

		void prepareGUI() {

			GridBagConstraints c = new GridBagConstraints();

			setSize(400, 250);
			setLayout(new BorderLayout());

			pnlControls.add(btnAnnuler);
			pnlControls.add(btnCreer);

			pnlFields.setLayout(new GridBagLayout());

			c.gridx = c.gridy = 0;

			pnlFields.add(new JLabel("Immatriculation "), c);
			c.gridy++;
			pnlFields.add(new JLabel("Marque "), c);
			c.gridy++;
			pnlFields.add(new JLabel("Type "), c);
			c.gridy++;

			lblModele.setText(comboType.getSelectedItem().equals(MOTO) ? "Cylindrée " : "Modèle ");

			pnlFields.add(lblModele, c);
			c.gridy++;
			pnlFields.add(new JLabel("Kilometrage "), c);

			c.gridx = 1;
			c.gridy = 0;

			pnlFields.add(txtImmatriculation, c);
			c.gridy++;
			pnlFields.add(txtMarque, c);
			c.gridy++;
			c.fill = GridBagConstraints.HORIZONTAL;
			pnlFields.add(comboType, c);
			c.gridy++;
			pnlFields.add(txtModel, c);
			c.gridy++;
			pnlFields.add(txtKilometrage, c);

			add(pnlFields, BorderLayout.CENTER);
			add(pnlControls, BorderLayout.SOUTH);

		}

		private void prepareActionListener() {

			comboType.addItemListener(
					evt -> lblModele.setText(comboType.getSelectedItem().equals(MOTO) ? "Cylindrée " : "Modèle "));

			btnCreer.addActionListener(evt -> {
				switch (comboType.getSelectedItem().toString()) {
				case MOTO:
					Moto moto = new Moto(txtMarque.getText().trim(), Integer.parseInt(txtModel.getText()));
					((TableModelExemplaire) table.getModel()).addRow(new Exemplaire(
							txtImmatriculation.getText().trim(), moto, Integer.parseInt(txtKilometrage.getText())));
					break;

				case AUTO:
					Auto auto = new Auto(txtMarque.getText().trim(), txtModel.getText().trim());
					((TableModelExemplaire) table.getModel()).addRow(new Exemplaire(
							txtImmatriculation.getText().trim(), auto, Integer.parseInt(txtKilometrage.getText())));
					break;

				default:
					break;
				}
				((TableModelExemplaire) table.getModel()).fireTableDataChanged();
				dispose();
			});

			btnAnnuler.addActionListener(evt -> dispose());
		}

		public DialogExemplaire(Window parent) {
			this.wParent = parent;

			prepareGUI();
			prepareActionListener();
			setModal(true);
			setResizable(false);
			setLocationRelativeTo(parent);

		}

	}

}
