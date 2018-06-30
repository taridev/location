package org.matthieu.location.presentation;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public abstract class AbstractTablePanel extends JPanel {

	protected JTable table;

	JScrollPane scrollPan;

	protected Window wParent;

	protected JPanel panelOption = new JPanel();

	protected JButton btnAdd = new JButton("+");
	protected JButton btnDel = new JButton("-");
	protected JButton btnPrint = new JButton("Imprimer");
	protected JButton btnPDF = new JButton("PDF");

	protected JTextField txtSearch = new JTextField(16);
	protected JComboBox<String> comboFiltre = null;
	
	protected transient List<String> strOptions = new ArrayList<>();
	
	public AbstractTablePanel(Window parent, JTable table) {
		this.table = table;
		this.wParent = parent;
	}

	protected void prepareGUI() {
		GridBagConstraints c = new GridBagConstraints();
		String[] stockArr = null;
		if(!strOptions.isEmpty()) {
			stockArr = new String[strOptions.size()];
			strOptions.toArray(stockArr);
			comboFiltre = new JComboBox<>(stockArr);
		} else {
			comboFiltre = new JComboBox<>() ;
		}

		setLayout(new BorderLayout());

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
		c.gridx++;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHEAST;
		panelOption.add(txtSearch, c);
		panelOption.add(comboFiltre, c);

		scrollPan = new JScrollPane(table);
		add(scrollPan, BorderLayout.CENTER);
		add(panelOption, BorderLayout.NORTH);
	}

	protected void prepareActionListener() {
		
	}

}
