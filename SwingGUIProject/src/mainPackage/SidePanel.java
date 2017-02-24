package mainPackage;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class SidePanel extends JPanel {
	
	private JLabel nameLabel;
	private JLabel orgLabel;
	private JTextField nameTxt;
	private JTextField orgTxt;
	
	public SidePanel()
	{	
		nameLabel = new JLabel("Name: ");
		orgLabel = new JLabel("Organization: ");
		nameTxt = new JTextField(10);
		orgTxt = new JTextField(10);
		
		Dimension  dim = getPreferredSize();
		dim.width = 300;
		
		this.setPreferredSize(dim);
		
		Border outer = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border inner = BorderFactory.createTitledBorder("Info");
		
		this.setBorder(BorderFactory.createCompoundBorder(outer, inner));
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.weightx = 1.0;
		gbc.weighty = 0.1;
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		
		add(nameLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 5);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		
		add(nameTxt, gbc);
		
		gbc.weightx = 1.0;
		gbc.weighty = 2.5;
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		
		add(orgLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 0, 5);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		
		add(orgTxt, gbc);
		
	}
}
