package mainPackage;

import java.awt.BorderLayout;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{
	
	private Toolbar toolbar;
	private TextPanel textArea;
	private SidePanel sidePanel;
	
	public MainFrame()
	{
		super("Hello World!");
		
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		toolbar = new Toolbar();
		textArea = new TextPanel();
		sidePanel = new SidePanel();
		
		toolbar.SetTextListener(new TextListener() {
			public void textEmitted(String text) {
				textArea.append(text);
			}
		});
		
		add(toolbar, BorderLayout.NORTH);
		add(textArea, BorderLayout.CENTER);
		add(sidePanel, BorderLayout.WEST);
		
		setVisible(true);
	}
}
