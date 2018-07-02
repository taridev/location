package org.matthieuaudemard.location.vue.emprunteur;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.print.PrinterException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.matthieu.location.presentation.AbstractTablePanel;
import org.matthieuaudemard.location.modele.entitee.Emprunteur;

@SuppressWarnings("serial")
public class JPanelEmprunteur extends AbstractTablePanel {

	static final Logger logger = Logger.getLogger(JPanelEmprunteur.class);

	public TableModelEmprunteur getTableModel() {
		return (TableModelEmprunteur) table.getModel();
	}

	/**
	 * 
	 */
	@Override
	protected void prepareActionListener() {
		btnAdd.addActionListener(evt -> {
			DialogEmprunteur d = new DialogEmprunteur(wParent);
			d.setVisible(true);
		});

		btnDel.addActionListener(evt -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow > -1) {
				TableModelEmprunteur model = ((TableModelEmprunteur) table.getModel());
				model.removeRow(selectedRow);
				model.fireTableDataChanged();
			}
		});

		btnPrint.addActionListener(evt -> {
			try {
				table.print();
			} catch (PrinterException e) {
				logger.error(e.getMessage());
			}
		});

		btnPDF.addActionListener(evt -> logger.info("Handle a click button PDF"));
	}

	/**
	 * @param parent
	 */
	public JPanelEmprunteur(JFrame parent, JTableEmprunteur table) {
		super(parent, table);
		prepareGUI();
		prepareActionListener();
	}
	
	@Override
	protected void prepareGUI() {
		super.prepareGUI();
		comboFiltre.setVisible(false);
		txtSearch.setVisible(false);
	}

	class DialogEmprunteur extends JDialog {

		/**
		 * 
		 */
		private static final long serialVersionUID = 81849338570652284L;

		JTextField txtNom = new JTextField(16);
		JTextField txtPrenom = new JTextField(16);
		JTextField txtNumRue = new JTextField(5);
		JTextField txtAdresse = new JTextField(16);
		JTextField txtCodePostal = new JTextField(5);
		JTextField txtVille = new JTextField(16);

		JButton btnCreer = new JButton("OK");
		JButton btnAnnuler = new JButton("Annuler");

		JPanel pnlControls = new JPanel();
		JPanel pnlFields = new JPanel();

		Window wParent;

		void prepareGUI() {
	
				GridBagConstraints c = new GridBagConstraints();
	
				setSize(500, 300);
				setLayout(new BorderLayout());
	
				pnlControls.add(btnAnnuler);
				pnlControls.add(btnCreer);
	
				pnlFields.setLayout(new GridBagLayout());
	
				c.gridx = 0;
				c.gridy = 0;
	
				pnlFields.add(new JLabel("Nom: "), c);
				c.gridy++;
	
				pnlFields.add(new JLabel("Prénom: "), c);
				c.gridy++;
	
				pnlFields.add(new JLabel("Numéro: "), c);
				c.gridy++;
	
				pnlFields.add(new JLabel("Rue: "), c);
				c.gridy++;
	
				pnlFields.add(new JLabel("Code Postal: "), c);
				c.gridy++;
	
				pnlFields.add(new JLabel("Ville: "), c);
				c.gridy++;
	
				c.gridx = 1;
				c.gridy = 0;
	
				pnlFields.add(txtNom, c);
				c.gridy++;
				c.anchor = GridBagConstraints.LINE_START;
	
				pnlFields.add(txtPrenom, c);
				c.gridy++;
	
				pnlFields.add(txtNumRue, c);
				c.gridy++;
	
				pnlFields.add(txtAdresse, c);
				c.gridy++;
	
				pnlFields.add(txtCodePostal, c);
				c.gridy++;
	
				pnlFields.add(txtVille, c);
				c.gridy++;
	
				add(pnlFields, BorderLayout.CENTER);
				add(pnlControls, BorderLayout.SOUTH);

		}

		private void prepareActionListener() {
			btnCreer.addActionListener(evt -> {
				((TableModelEmprunteur) table.getModel())
						.addRow((new Emprunteur(txtNom.getText(), txtPrenom.getText(), txtNumRue.getText() + " "
								+ txtAdresse.getText() + " " + txtCodePostal.getText() + " " + txtVille.getText())));
				((TableModelEmprunteur) table.getModel()).fireTableDataChanged();
				dispose();
			});

			btnAnnuler.addActionListener(evt -> dispose());
		}

		public DialogEmprunteur(Window parent) {
			super();
			this.wParent = parent;

			prepareGUI();
			prepareActionListener();

			setModal(true);
			setSize(375, 245);
			setResizable(false);
			setLocationRelativeTo(parent);

		}

	}

}
