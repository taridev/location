package org.matthieuaudemard.location.vue.emprunteur;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.matthieuaudemard.location.modele.Emprunteur;

public class JPanelEmprunteur extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4704734305456655829L;

	/**
	 * 
	 */
	JTableEmprunteur tblEmprunteur = new JTableEmprunteur(new TableModelEmprunteur());

	/**
	 * 
	 */
	JScrollPane scrollPan;

	/**
	 * 
	 */
	JPanel panelOption = new JPanel();
	
	/**
	 * 
	 */
	Window parent;

	JButton btnAdd = new JButton("+");
	JButton btnDel = new JButton("-");
	JButton btnPrint = new JButton("Imprimer");
	JButton btnPDF = new JButton("PDF");
	
	public TableModelEmprunteur getTableModel() {
		return (TableModelEmprunteur) tblEmprunteur.getModel();
	}

	/**
	 * 
	 */
	private void prepareGUI() {

		GridBagConstraints c = new GridBagConstraints();

		setLayout(new BorderLayout());

		scrollPan = new JScrollPane(tblEmprunteur);

		c.gridx = 0;
		c.gridy = 0;

		panelOption.setLayout(new GridBagLayout());

		panelOption.add(btnAdd, c);
		c.gridx++;
		panelOption.add(btnDel, c);
		c.gridx++;
		panelOption.add(btnPrint, c);
		c.gridx++;
		panelOption.add(btnPDF, c);

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
				DialogEmprunteur d = new DialogEmprunteur(parent);
				d.setVisible(true);

			}
		});

		btnDel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = tblEmprunteur.getSelectedRow();
				
				if(selectedRow > -1) {
					TableModelEmprunteur model = ((TableModelEmprunteur)tblEmprunteur.getModel());
					model.removeRow(selectedRow);
					model.fireTableDataChanged();
				}
			}
		});

		btnPrint.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					tblEmprunteur.print();
				} catch (PrinterException e) {
					e.printStackTrace();
				}
			}
		});

		btnPDF.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * @param parent
	 */
	public JPanelEmprunteur(JFrame parent) {
		super();
		this.parent = parent;
		prepareGUI();
		prepareActionListener();
	}
	
	class DialogEmprunteur extends JDialog {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 81849338570652284L;
		
		JTextField txtNom        = new JTextField(16);
		JTextField txtPrenom     = new JTextField(16);
		JTextField txtNumRue     = new JTextField(5);
		JTextField txtAdresse    = new JTextField(16);
		JTextField txtCodePostal = new JTextField(5);
		JTextField txtVille      = new JTextField(16);
		
		JButton btnCreer   = new JButton("OK");
		JButton btnAnnuler = new JButton("Annuler");
		
		JPanel pnlControls = new JPanel();
		JPanel pnlFields   = new JPanel();
		
		Window parent;
		
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
			c.gridy ++;
			
			pnlFields.add(new JLabel("Prénom: "), c);
			c.gridy ++;
			
			pnlFields.add(new JLabel("Numéro: "), c);
			c.gridy ++;
			
			pnlFields.add(new JLabel("Rue: "), c);
			c.gridy ++;
			
			pnlFields.add(new JLabel("Code Postal: "), c);
			c.gridy ++;
			
			pnlFields.add(new JLabel("Ville: "), c);
			c.gridy ++;
			
			c.gridx = 1;
			c.gridy = 0;
			
			pnlFields.add(txtNom, c);
			c.gridy ++;
			c.anchor = GridBagConstraints.LINE_START;
			
			pnlFields.add(txtPrenom, c);
			c.gridy ++;
			
			pnlFields.add(txtNumRue, c);
			c.gridy ++;
			
			pnlFields.add(txtAdresse, c);
			c.gridy ++;
			
			pnlFields.add(txtCodePostal, c);
			c.gridy ++;
			
			pnlFields.add(txtVille, c);
			c.gridy ++;
			
			add(pnlFields, BorderLayout.CENTER);
			add(pnlControls, BorderLayout.SOUTH);
			
		}
		
		private void prepareActionListener()  {
			btnCreer.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					((TableModelEmprunteur)tblEmprunteur.getModel()).addRow((new Emprunteur(txtNom.getText(), txtPrenom.getText(), 
							txtNumRue.getText() + " " + txtAdresse.getText() + " " + txtCodePostal.getText() + " " + txtVille.getText())));
					((TableModelEmprunteur)tblEmprunteur.getModel()).fireTableDataChanged();
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

		public DialogEmprunteur(Window parent) {
			super();
			this.parent = parent;
			
			prepareGUI();
			prepareActionListener();

			setModal(true);
			setSize(375, 245);
			setResizable(false);
			setLocationRelativeTo(parent);
			
		}
		
		
		
	}

}
