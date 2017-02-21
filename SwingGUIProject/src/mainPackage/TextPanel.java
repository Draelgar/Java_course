package mainPackage;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class TextPanel extends JPanel implements TextListener{

	private JTextArea textArea;
	
	public TextPanel() {
		textArea = new JTextArea();
		
		setLayout(new BorderLayout());
		
		add(new JScrollPane(textArea), BorderLayout.CENTER);
		
	}

	public void textEmitted(String text) {
		textArea.append(text);
	}
}
