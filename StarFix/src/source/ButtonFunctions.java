package source;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import gui.StarFixGUI;

public class ButtonFunctions {
	private static ArrayList<String> arr = new ArrayList<>();
	private static String workshopPath;
	private static String tempTxtPath;
	private static String configPath;
	private static String gameFolderPath;
	private static String currSys = System.getProperty("os.name").contains("Windows") ? "Windows"
			: System.getProperty("os.name").contains("Mac") ? "Mac" : "Unix";
	private static String realArch = wArch();

	private static String wArch() {
		String arch = System.getenv("PROCESSOR_ARCHITECTURE");
		String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");

		String realArch = arch.endsWith("64") || wow64Arch != null && wow64Arch.endsWith("64") ? "64" : "32";
		return realArch;
	}

	private static void workPath() {
		if (currSys.equals("Windows")) {
			workshopPath = getPathWin() + "\\steamapps\\workshop\\content\\211820";
			if (realArch.equals("64")) {
				tempTxtPath = getPathWin() + "\\steamapps\\common\\Starbound\\win64\\text.txt";
				configPath = getPathWin() + "\\steamapps\\common\\Starbound\\win64\\sbinit.config";
				gameFolderPath = getPathWin() + "\\steamapps\\common\\Starbound\\win64\\";
			} else {
				tempTxtPath = getPathWin() + "\\steamapps\\common\\Starbound\\win32\\text.txt";
				configPath = getPathWin() + "\\steamapps\\common\\Starbound\\win32\\sbinit.config";
				gameFolderPath = getPathWin() + "\\steamapps\\common\\Starbound\\win32\\";
			}
		} else if (currSys.contains("Mac")) {
			//TODO (?)
		} else {
			//TODO (?)
		}
	}

	private static void editConfig() {
		revertConfig();
		grabText();
		File f = new File(configPath);
		BufferedReader br;
		FileReader fr;
		PrintWriter writer;
		try {
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			writer = new PrintWriter(new BufferedWriter(new FileWriter(tempTxtPath)));
			String line;

			while ((line = br.readLine()) != null) {
				if (line.contains("mods")) {
					line = "    \"..\\\\mods\\\\\",";
					writer.println(line);
					int i = 0;
					while (i < arr.size()) {
						if (i == arr.size() - 1) {
							writer.println(arr.get(i));
						} else
							writer.println(arr.get(i) + ",");
						i++;
					}
					continue;
				}
				writer.println(line);
			}
			fr.close();
			br.close();
			writer.flush();
			writer.close();

		} catch (Exception e) {
		}
		File conf = new File(configPath);
		conf.delete();
		File text = new File(tempTxtPath);
		text.renameTo(conf);
		arr.clear();

	}

	private static void resetConfig() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(tempTxtPath)));
			for(String s : SbinitDefault.getArray()) {
				writer.println(s);
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
		}
		File conf = new File(configPath);
		conf.delete();
		File text = new File(tempTxtPath);
		text.renameTo(conf);
	}
	
	private static void revertConfig(){
		grabText();
		File f = new File(configPath);
		BufferedReader br;
		FileReader fr;
		PrintWriter writer;
		try {
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			writer = new PrintWriter(new BufferedWriter(new FileWriter(tempTxtPath)));
			String line;

			while ((line = br.readLine()) != null) {
				if (line.contains("mods")) {
					line = "    \"..\\\\mods\\\\\"";
				} else if (line.contains("211820")){
					continue;
				}
				writer.println(line);
			}
			
			fr.close();
			br.close();
			writer.flush();
			writer.close();

		} catch (Exception e) {
		}
		File conf = new File(configPath);
		conf.delete();
		File text = new File(tempTxtPath);
		text.renameTo(conf);
		arr.clear();

	}

	private static void grabText() {
		File f = new File(workshopPath);
		for (File file : f.listFiles()) {
			String s = file.getAbsolutePath();
			s = s.substring(s.indexOf("workshop"));
			s = s.replaceAll("\\\\", "\\\\\\\\");
			s = "\"" + "..\\\\..\\\\..\\\\" + s + "\\\\" + "\"";
			arr.add(s);
		}
	}

	private static String getPathWin() {
		String val = WinRegistryWorker.readRegistry("HKCU\\Software\\Valve\\Steam", "SteamPath");
		val = val.trim();
		int start = val.indexOf(":") - 1;
		int end = val.length();
		String finalVal = val.substring(start, end);
		return finalVal.trim();
	}

	private static void setCustomWorkshopPath() {
		JOptionPane workshopPathJO = new JOptionPane();
		int yesOption = JOptionPane.showConfirmDialog(workshopPathJO, "Please specify where is your Steam folder",
				"Choose Folder", JOptionPane.YES_NO_OPTION);
		if (JOptionPane.YES_OPTION == yesOption) {
				String s = StarFixGUI.getNewPath(1);
				if (s.toLowerCase().contains("\\steam")) {
					String tempHolder = s.substring(0, s.toLowerCase().lastIndexOf("\\steam") + 6);
					workshopPath = tempHolder + "\\steamapps\\workshop\\content\\211820";
				} else
					StarFixGUI.userInfo("wrong path selected");
		}
	}

	private static void setCustomConfigPath() {
		JOptionPane configPathJO = new JOptionPane();
		int yesOption = JOptionPane.showConfirmDialog(configPathJO, "Please specify where is your sbinit.config file",
				"Choose Folder", JOptionPane.YES_NO_OPTION);
		if (JOptionPane.YES_OPTION == yesOption) {
			if (StarFixGUI.getNewPath(2) != null) {
				String s = StarFixGUI.getNewPath(2);
				if (s.contains("sbinit.config")) {
					configPath = s;
					String tempHolder = s.substring(0, s.length() - 13);
					tempTxtPath = tempHolder + "text.txt";
					gameFolderPath = tempHolder + "\\";
				} else
					StarFixGUI.userInfo("Wrong path selected");
			}
		}
	}

	@SuppressWarnings("unused")
	private static String getPathMac() {
		// TODO
		return null;
	}

	@SuppressWarnings("unused")
	private static String getPathUnix() {
		// TODO
		return null;
	}

	public static void openGameFolder() {
		try {
			Desktop.getDesktop().open(new File(gameFolderPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void launchStarbound() {
		try {
			Desktop.getDesktop().open(new File(gameFolderPath + "starbound.exe"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void launchStarboundSteam() {
		try {
			Desktop.getDesktop().browse(new URI("steam://rungameid/211820"));
		} catch (IOException | URISyntaxException e) {
			StarFixGUI.userInfo("Can't run steam version");
		}
	}

	@SuppressWarnings("unused")
	public static void launchServer() {
		if (currSys.equals("Windows")) {
			try {
				File f = new File(gameFolderPath);
				ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", "starbound_server.exe");
				pb.directory(f);
				Process process = pb.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getConfigPath() {
		return configPath;
	}

	public static void initializePaths() {
		workPath();
	}

	public static void revertConfigPublic() {
		revertConfig();
	}
	
	public static void resetConfigPublic(){
		resetConfig();
	}

	public static void editConfigPublic() {
		editConfig();
	}

	public static void checkPaths() {
		if (workshopPath == null || !((new File(workshopPath)).exists())) {
			setCustomWorkshopPath();
		} else if (configPath == null || !((new File(configPath)).exists())) {
			setCustomConfigPath();
		} else if (gameFolderPath == null || !((new File(gameFolderPath)).exists())) {
			setCustomConfigPath();
		}
	}
}