package mainPackage;

import javax.swing.SwingUtilities;

public class Application {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				@SuppressWarnings("unused")
				MainFrame mainFrame = new MainFrame();
			}	
		});
	}

}
