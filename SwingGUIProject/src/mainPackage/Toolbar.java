package mainPackage;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Toolbar extends JPanel implements ActionListener {

	private JButton helloBtn;
	private JButton byeBtn;
	private TextListener textListener;
	
	public Toolbar() {
		helloBtn = new JButton("Hello");
		byeBtn = new JButton("Good Bye");
		
		helloBtn.addActionListener(this);
		byeBtn.addActionListener(this);
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		add(helloBtn);
		add(byeBtn);
	}
	
	public void SetTextListener(TextListener tl) {
		this.textListener = tl;
	}

	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		
		if(clicked == helloBtn)
			textListener.textEmitted("Hello!\n");
		else if(clicked == byeBtn)
			textListener.textEmitted("Good Bye!\n");
	}
}
