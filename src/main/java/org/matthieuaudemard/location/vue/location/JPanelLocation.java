package org.matthieuaudemard.location.vue.location;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.matthieu.location.presentation.AbstractTablePanel;
import org.matthieuaudemard.location.modele.entitee.Emprunteur;
import org.matthieuaudemard.location.modele.entitee.Exemplaire;
import org.matthieuaudemard.location.modele.entitee.Location;
import org.matthieuaudemard.location.vue.datepicker.DateLabelFormatter;
import org.matthieuaudemard.location.vue.exemplaire.TableModelExemplaire;

@SuppressWarnings("serial")
public class JPanelLocation extends AbstractTablePanel {

	private static final Logger logger = LogManager.getLogger(JPanelLocation.class);

	public JPanelLocation(JFrame parent, JTableLocation table) {
		super(parent, table);
		prepareGUI();
		prepareActionListener();
	}

	@Override
	protected void prepareGUI() {
		super.prepareGUI();
		txtSearch.setVisible(true);
	}

	@Override
	protected void prepareActionListener() {
		btnAdd.addActionListener(evt -> {
			DialogLocation d = new DialogLocation(wParent);
			d.setVisible(true);
		});

		btnDel.addActionListener(evt -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow >= 0) {
				TableModelLocation model = ((TableModelLocation) table.getModel());
				model.removeRow(selectedRow);
				model.fireTableDataChanged();
			}
		});

		txtSearch.addKeyListener(new KeyAdapter() {

			@SuppressWarnings("unchecked")
			@Override
			public void keyReleased(KeyEvent evt) {

				String txt = ((JTextField) evt.getSource()).getText().toLowerCase();
				RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
					public boolean include(Entry<?, ?> entry) {
						return ((String) entry.getValue(1)).toLowerCase().contains(txt)
								|| ((String) entry.getValue(2)).toLowerCase().contains(txt);
					}
				};

				if (txt.length() == 0) {
					((TableRowSorter<TableModelExemplaire>) table.getRowSorter()).setRowFilter(null);
				} else {
					((TableRowSorter<TableModelExemplaire>) table.getRowSorter()).setRowFilter(filter);
				}

			}
		});

	}

	class DialogLocation extends JDialog {

		JTextField txtDateRetrait = new JTextField(16);
		JTextField txtDateRetour = new JTextField(16);

		JComboBox<String> comboEmprunteur = new JComboBox<>();
		JComboBox<String> comboExemplaire = new JComboBox<>();

		JCheckBox chkAssurance = new JCheckBox();

		JButton btnCreer = new JButton("OK");
		JButton btnAnnuler = new JButton("Annuler");

		JPanel pnlControls = new JPanel();
		JPanel pnlFields = new JPanel();

		transient UtilDateModel model1 = new UtilDateModel();
		Properties p1 = new Properties();

		transient UtilDateModel model2 = new UtilDateModel();
		Properties p2 = new Properties();

		JDatePanelImpl datePanelRetrait = new JDatePanelImpl(model1, p1);
		// Don't know about the formatter, but there it is...
		JDatePickerImpl datePickerRetrait = new JDatePickerImpl(datePanelRetrait, new DateLabelFormatter());

		JDatePanelImpl datePanelRetour = new JDatePanelImpl(model2, p2);
		// Don't know about the formatter, but there it is...
		JDatePickerImpl datePickerRetour = new JDatePickerImpl(datePanelRetour, new DateLabelFormatter());

		Window wParent;

		void prepareGUI() {

			GridBagConstraints c = new GridBagConstraints();

			for (Emprunteur e : ((TableModelLocation) table.getModel()).getCtrl().getCtrlEmprunteur()) {
				comboEmprunteur
						.addItem(e.getPrimaryKey() + " " + e.getNomEmprunteur() + " " + e.getPrenomEmprunteur());
			}

			for (Exemplaire e : ((TableModelLocation) table.getModel()).getCtrl().getCtrlExemplaire()) {
				comboExemplaire.addItem(e.getPrimaryKey());
			}

			setSize(500, 300);
			setLayout(new BorderLayout());

			pnlControls.add(btnAnnuler);
			pnlControls.add(btnCreer);

			pnlFields.setLayout(new GridBagLayout());

			c.gridx = 0;
			c.gridy = 0;

			pnlFields.add(new JLabel("Emprunteur: "), c);
			c.gridy++;

			pnlFields.add(new JLabel("Véhicule: "), c);
			c.gridy++;

			pnlFields.add(new JLabel("Assurance: "), c);
			c.gridy++;

			pnlFields.add(new JLabel("Date d'emprunt: "), c);
			c.gridy++;

			pnlFields.add(new JLabel("Date de retour: "), c);
			c.gridy++;

			c.gridx = 1;
			c.gridy = 0;

			c.fill = GridBagConstraints.HORIZONTAL;
			pnlFields.add(comboEmprunteur, c);
			c.gridy++;

			c.fill = GridBagConstraints.HORIZONTAL;
			pnlFields.add(comboExemplaire, c);
			c.gridy++;

			c.anchor = GridBagConstraints.LINE_START;
			pnlFields.add(chkAssurance, c);
			c.gridy++;

			pnlFields.add(datePickerRetrait, c);
			c.gridy++;

			pnlFields.add(datePickerRetour, c);

			add(pnlFields, BorderLayout.CENTER);
			add(pnlControls, BorderLayout.SOUTH);

		}

		private void prepareActionListener() {
			btnCreer.addActionListener(ev -> {
				Emprunteur emprunteur2Add = ((TableModelLocation) table.getModel()).getCtrl().getCtrlEmprunteur()
						.getValueAt(comboEmprunteur.getSelectedIndex());
				Exemplaire exemplaire2Add = ((TableModelLocation) table.getModel()).getCtrl().getCtrlExemplaire()
						.getValueAt(comboExemplaire.getSelectedIndex());

				Date dateRetrait = null;
				Date dateRetourPrevue = null;
				try {

					dateRetrait = new SimpleDateFormat("yyyy-mm-dd").parse(
							"" + datePanelRetrait.getModel().getYear() + "-" + datePanelRetrait.getModel().getMonth()
									+ "-" + datePanelRetrait.getModel().getDay());

					dateRetourPrevue = new SimpleDateFormat("yyyy-mm-dd").parse(
							"" + datePanelRetour.getModel().getYear() + "-" + datePanelRetour.getModel().getMonth()
									+ "-" + datePanelRetour.getModel().getDay());
				} catch (ParseException e) {
					logger.error(e.getStackTrace());
				}

				((TableModelLocation) table.getModel()).addRow(new Location(emprunteur2Add, exemplaire2Add,
						dateRetrait, dateRetourPrevue, chkAssurance.isSelected()));
				((TableModelLocation) table.getModel()).fireTableDataChanged();
				((TableModelLocation) table.getModel()).getCtrl().save();
				((TableModelLocation) table.getModel()).getCtrl().find();
				dispose();
			});

			btnAnnuler.addActionListener(e -> dispose());
		}

		public DialogLocation(Window parent) {
			super();
			this.wParent = parent;

			p1.put("text.today", "Today");
			p1.put("text.month", "Month");
			p1.put("text.year", "Year");

			p2.put("text.today", "Today");
			p2.put("text.month", "Month");
			p2.put("text.year", "Year");

			prepareGUI();
			prepareActionListener();

			setModal(true);
			setSize(375, 245);
			setResizable(false);
			setLocationRelativeTo(parent);

		}

	}

}
