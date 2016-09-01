package gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;

import java.awt.Insets;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileFilter;
import javax.swing.JTextArea;

import source.ButtonFunctions;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JOptionPane;

public class StarFixGUI {

	private static JFrame frame;
	private static JCheckBox chckbxExitOnLaunch;
	private static JCheckBox chckbxRunWithSteam;
	private static JTextArea textArea;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public StarFixGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setBounds(100, 100, 400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		JPanel pathPanel = new JPanel();
		pathPanel.setVisible(false);

		JOptionPane insertPath = new JOptionPane();
		insertPath.add(pathPanel);

		JPanel topPanel = new JPanel();
		FlowLayout fl_topPanel = (FlowLayout) topPanel.getLayout();
		fl_topPanel.setVgap(0);
		fl_topPanel.setHgap(0);
		topPanel.setPreferredSize(new Dimension(393, 260));
		frame.getContentPane().add(topPanel);

		JPanel textAreaPanel = new JPanel();
		FlowLayout fl_textAreaPanel = (FlowLayout) textAreaPanel.getLayout();
		fl_textAreaPanel.setAlignment(FlowLayout.LEFT);
		fl_textAreaPanel.setVgap(0);
		fl_textAreaPanel.setHgap(0);
		textAreaPanel.setPreferredSize(new Dimension(300, 260));
		topPanel.add(textAreaPanel);

		JScrollPane scrollPaneTextArea = new JScrollPane();
		scrollPaneTextArea.setBorder(null);
		scrollPaneTextArea.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneTextArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneTextArea.setPreferredSize(new Dimension(300, 260));
		textAreaPanel.add(scrollPaneTextArea);

		textArea = new JTextArea();
		textArea.setBackground(new Color(255, 255, 255));
		textArea.setBorder(null);
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 10));
		scrollPaneTextArea.setViewportView(textArea);
		textArea.setEditable(false);

		JPanel topSidePanel = new JPanel();
		topSidePanel.setBorder(null);
		topSidePanel.setPreferredSize(new Dimension(93, 260));
		topPanel.add(topSidePanel);
		
		//***SERVER EDITOR***
		//TODO
		JButton btnServerEditor = new JButton("Server Editor");
		btnServerEditor.setMargin(new Insets(2, 8, 2, 8));
		topSidePanel.add(btnServerEditor);
		
		//***RESET CONFIGS
		JButton btnResetConfig = new JButton("Reset Config");
		btnResetConfig.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ButtonFunctions.checkPaths();
				ButtonFunctions.resetConfigPublic();
				try {
					FileReader reader = new FileReader(ButtonFunctions.getConfigPath());
					BufferedReader br = new BufferedReader(reader);
					textArea.read(br, null);
					br.close();
				} catch (Exception e) {
					userInfo("Some problems with config path");
				}
			}
		});
		btnResetConfig.setMargin(new Insets(2, 8, 2, 8));
		topSidePanel.add(btnResetConfig);

		JPanel botPanel = new JPanel();
		botPanel.setPreferredSize(new Dimension(393, 110));
		frame.getContentPane().add(botPanel);
		SpringLayout sl_botPanel = new SpringLayout();
		botPanel.setLayout(sl_botPanel);

		// ***START SERVER***
		JButton btnLaunchServer = new JButton("Start Server");
		sl_botPanel.putConstraint(SpringLayout.NORTH, btnLaunchServer, 10, SpringLayout.NORTH, botPanel);
		btnLaunchServer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ButtonFunctions.checkPaths();
				ButtonFunctions.launchServer();
			}
		});
		sl_botPanel.putConstraint(SpringLayout.EAST, btnLaunchServer, -10, SpringLayout.EAST, botPanel);
		botPanel.add(btnLaunchServer);
		btnLaunchServer.setMargin(new Insets(5, 19, 5, 19));

		// ***PLAY***
		JButton btnPlay = new JButton("Play");
		sl_botPanel.putConstraint(SpringLayout.EAST, btnPlay, -10, SpringLayout.EAST, botPanel);
		btnPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ButtonFunctions.checkPaths();
				if (chckbxRunWithSteam.isSelected()){
					ButtonFunctions.launchStarboundSteam();
				} else
					ButtonFunctions.launchStarbound();

				if (chckbxExitOnLaunch.isSelected()) {
					System.exit(0);
				}
			}
		});
		sl_botPanel.putConstraint(SpringLayout.SOUTH, btnPlay, -10, SpringLayout.SOUTH, botPanel);
		botPanel.add(btnPlay);
		btnPlay.setMargin(new Insets(5, 38, 5, 38));

		// ***GAME FOLDER***
		JButton btnStarboundFolder = new JButton("Game Folder");
		sl_botPanel.putConstraint(SpringLayout.WEST, btnStarboundFolder, 10, SpringLayout.WEST, botPanel);
		btnStarboundFolder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ButtonFunctions.checkPaths();
				ButtonFunctions.openGameFolder();
			}
		});
		btnStarboundFolder.setMargin(new Insets(1, 23, 1, 23));
		botPanel.add(btnStarboundFolder);

		//***APPLY MODS***
		JButton btnApplyMods = new JButton("Apply Mods");
		sl_botPanel.putConstraint(SpringLayout.NORTH, btnApplyMods, 10, SpringLayout.NORTH, botPanel);
		sl_botPanel.putConstraint(SpringLayout.WEST, btnApplyMods, 10, SpringLayout.WEST, botPanel);
		btnApplyMods.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0){
				ButtonFunctions.checkPaths();
				ButtonFunctions.editConfigPublic();
				try {
					FileReader reader = new FileReader(ButtonFunctions.getConfigPath());
					BufferedReader br = new BufferedReader(reader);
					textArea.read(br, null);
					br.close();
				} catch (Exception e) {
					userInfo("Some problems with config path");
				}
			}
		});
		btnApplyMods.setMargin(new Insets(1, 26, 1, 26));
		botPanel.add(btnApplyMods);

		//***REVERT CHANGES***
		JButton btnRevertChanges = new JButton("Revert Changes");
		sl_botPanel.putConstraint(SpringLayout.NORTH, btnStarboundFolder, 33, SpringLayout.NORTH, btnRevertChanges);
		sl_botPanel.putConstraint(SpringLayout.NORTH, btnRevertChanges, 33, SpringLayout.NORTH, btnApplyMods);
		sl_botPanel.putConstraint(SpringLayout.WEST, btnRevertChanges, 10, SpringLayout.WEST, botPanel);
		btnRevertChanges.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ButtonFunctions.checkPaths();
				ButtonFunctions.revertConfigPublic();
				try {
					FileReader reader = new FileReader(ButtonFunctions.getConfigPath());
					BufferedReader br = new BufferedReader(reader);
					textArea.read(br, null);
					br.close();
				} catch (Exception e2) {
					System.out.println("goshDARNit STEWIE YOU F UP AGAIN!");
				}
			}
		});
		btnRevertChanges.setMargin(new Insets(1, 14, 1, 14));
		botPanel.add(btnRevertChanges);

		//***EXIT ON LAUNCH***
		chckbxExitOnLaunch = new JCheckBox("Exit on Launch");
		sl_botPanel.putConstraint(SpringLayout.EAST, chckbxExitOnLaunch, -15, SpringLayout.WEST, btnPlay);
		chckbxExitOnLaunch.setSelected(true);
		botPanel.add(chckbxExitOnLaunch);

		//***RUN WITH STEAM***
		chckbxRunWithSteam = new JCheckBox("Run with Steam");
		sl_botPanel.putConstraint(SpringLayout.NORTH, chckbxExitOnLaunch, 33, SpringLayout.NORTH, chckbxRunWithSteam);
		sl_botPanel.putConstraint(SpringLayout.NORTH, chckbxRunWithSteam, 42, SpringLayout.NORTH, botPanel);
		sl_botPanel.putConstraint(SpringLayout.EAST, chckbxRunWithSteam, -120, SpringLayout.EAST, botPanel);
		chckbxRunWithSteam.setSelected(true);
		botPanel.add(chckbxRunWithSteam);

	}

	public static String getNewPath(int num) {
		final JFileChooser fileDialog = new JFileChooser();
		if(num == 1){
			fileDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		} else {
			fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileDialog.setFileFilter(new FileFilter() {
				@Override
				public boolean accept(File f) {
					if(f.isDirectory()){
						return true;
					} else if(f.getAbsolutePath().endsWith(".config")){
						return true;
					}
					return false;
				}

				@Override
				public String getDescription() {
					return null;
				}
			});
		}
		int returnVal = fileDialog.showOpenDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fileDialog.getSelectedFile().getAbsolutePath();
		} else {
			userInfo();
			return null;
		}
	}

	public static void userInfo() {
		textArea.setText("(Problem with finding steam/starbound folders)");
	}

	public static void userInfo(String info) {
		textArea.setText("(" + info + ")");
	}

	public static JFrame getFrame() {
		return frame;
	}

}
