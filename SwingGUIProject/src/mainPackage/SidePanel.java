package mainPackage;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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
		nameTxt = new JTextField();
		orgTxt = new JTextField();
		
		Dimension  dim = getPreferredSize();
		dim.width = 300;
		
		this.setPreferredSize(dim);
		
		Border outer = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border inner = BorderFactory.createTitledBorder("Info");
		
		this.setBorder(BorderFactory.createCompoundBorder(outer, inner));
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.NONE;
		
		add(nameLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		
		add(nameTxt, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		add(orgLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		
		add(nameLabel, gbc);
		
	}
}
