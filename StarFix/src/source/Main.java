package source;

import java.awt.EventQueue;

import javax.swing.UIManager;

import gui.StarFixGUI;
//Code part for getting real architecture of win systems goes to
//stackoverflow I guess it's Chris H, but I am not sure
//If someone knows the author, please notify me, so I can give proper
//credits
//
//WinRegistry and Stream reader goes to Oleg Ryaboy, based on 
//Miguel Enriquez 
//
//Software is based around Starbound(game) by Chucklefish Games.

public class Main {

	public static void main(String[] args) {
		SbinitDefault.setArray();
		ButtonFunctions.initializePaths();
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					StarFixGUI window = new StarFixGUI();
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	
}
