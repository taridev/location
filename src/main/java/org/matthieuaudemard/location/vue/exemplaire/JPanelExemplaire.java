package org.matthieuaudemard.location.vue.exemplaire;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.print.PrinterException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.Logger;
import org.matthieuaudemard.location.modele.Auto;
import org.matthieuaudemard.location.modele.Exemplaire;
import org.matthieuaudemard.location.modele.Moto;

public class JPanelExemplaire extends JPanel {

	static final Logger logger = Logger.getLogger(JPanelExemplaire.class);
			
	/**
	 * 
	 */
	private static final long serialVersionUID = -4704734305456655829L;

	/**
	 * 
	 */
	private JTableExemplaire tblExemplaire = new JTableExemplaire(new TableModelExemplaire());

	/**
	 * 
	 */
	private JPanel panelOption = new JPanel();

	/**
	 * 
	 */
	private Window wParent;

	private JButton btnAdd = new JButton("+");
	private JButton btnDel = new JButton("-");
	private JButton btnPrint = new JButton("Imprimer");
	private JButton btnPDF = new JButton("PDF");

	private String[] strOptions = { "Tout selectionner", "Auto", "Moto" };
	/**
	 * 
	 */
	private JComboBox<String> comboFiltre = new JComboBox<>(strOptions);
	
	public TableModelExemplaire getTableModel() {
		return (TableModelExemplaire) tblExemplaire.getModel();
	}

	public JPanelExemplaire(Window parent) {
		super();
		this.wParent = parent;
		prepareGUI();
		prepareActionListener();
	}

	/**
	 * 
	 */
	private void prepareGUI() {

		GridBagConstraints c = new GridBagConstraints();

		setLayout(new BorderLayout());

		JScrollPane scrollPan = new JScrollPane(tblExemplaire);

		c.gridx = 0;
		c.gridy = 0;

		panelOption.setLayout(new GridBagLayout());

		panelOption.add(btnAdd, c);
		c.gridx ++;
		panelOption.add(btnDel, c);
		c.gridx ++;
		panelOption.add(btnPrint, c);
		c.gridx ++;
		panelOption.add(btnPDF, c);
		c.gridx ++;
		panelOption.add(comboFiltre);
		c.gridx ++;

		add(panelOption, BorderLayout.NORTH);
		add(scrollPan, BorderLayout.CENTER);

	}

	/**
	 * 
	 */
	private void prepareActionListener() {
		
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				DialogExemplaire d = new DialogExemplaire(wParent);
				d.setVisible(true);

			}
		});

		btnDel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = tblExemplaire.getSelectedRow();

				if (selectedRow > -1) {
					TableModelExemplaire model = ((TableModelExemplaire) tblExemplaire.getModel());
					model.removeRow(selectedRow);
					model.fireTableDataChanged();
				}
			}
		});

		btnPrint.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					tblExemplaire.print();
				} catch (PrinterException e) {
					logger.error(e.getMessage());
				}
			}
		});

		btnPDF.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		comboFiltre.addItemListener(new ItemListener() {

			@Override
			@SuppressWarnings("unchecked")
			public void itemStateChanged(ItemEvent e) {
				
				switch (e.getItem().toString()) {
				case "Moto":
					RowFilter<Object, Object> filterMoto = new RowFilter<Object, Object>() {
						public boolean include(Entry<?, ?> entry) {
							return ((String) entry.getValue(2)).equals("Moto");
						}
					};
					((TableRowSorter<TableModelExemplaire>) tblExemplaire.getRowSorter()).setRowFilter(filterMoto);
					break;
				case "Auto":
					RowFilter<Object, Object> filterAuto = new RowFilter<Object, Object>() {
						public boolean include(Entry<?, ?> entry) {
							return ((String) entry.getValue(2)).equals("Auto");
						}
					};
					((TableRowSorter<TableModelExemplaire>) tblExemplaire.getRowSorter()).setRowFilter(filterAuto);
					break;
				default:
					RowFilter<Object, Object> filterAll = new RowFilter<Object, Object>() {
						public boolean include(Entry<?, ?> entry) {
							return true;
						}
					};
					((TableRowSorter<TableModelExemplaire>) tblExemplaire.getRowSorter()).setRowFilter(filterAll);
					break;
				}
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

			comboType.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					lblModele.setText(comboType.getSelectedItem().equals(MOTO) ? "Cylindrée " : "Modèle ");
				}
			});

			btnCreer.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					switch (comboType.getSelectedItem().toString()) {
					case MOTO:

						Moto moto = new Moto(txtMarque.getText().trim(), Integer.parseInt(txtModel.getText()));
						((TableModelExemplaire) tblExemplaire.getModel()).addRow(new Exemplaire(
								txtImmatriculation.getText().trim(), moto, Integer.parseInt(txtKilometrage.getText())));
						break;

					case AUTO:

						Auto auto = new Auto(txtMarque.getText().trim(), txtModel.getText().trim());
						((TableModelExemplaire) tblExemplaire.getModel()).addRow(new Exemplaire(
								txtImmatriculation.getText().trim(), auto, Integer.parseInt(txtKilometrage.getText())));
						break;

					default:
						break;
					}
					((TableModelExemplaire) tblExemplaire.getModel()).fireTableDataChanged();
					dispose();
				}
			});

			btnAnnuler.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
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
