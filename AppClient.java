import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import javax.print.DocFlavor.URL;
import javax.sound.sampled.SourceDataLine;

public class AppClient {

	public static void readAppFile(String fileName, AppSim appSim) {
		BufferedReader file;
		try {
			file = new BufferedReader(new FileReader(fileName));

			String appStr;
			while ((appStr = file.readLine()) != null) {
				String[] appliance = appStr.split(",");

				int locationID = Integer.parseInt(appliance[0]);
				String appName = appliance[1];
				int onPower = Integer.parseInt(appliance[2]);
				double probOn = Double.parseDouble(appliance[3]);
				boolean isSmart = Boolean.parseBoolean(appliance[4]);
				double lowPower = Double.parseDouble(appliance[5]);

				Location location;
				ArrayList<Location> locations = appSim.getLocations();
				boolean isUniqueLoc = true;

				Appliance regApp = null;

				regApp = new Appliance(locationID, appName, onPower, probOn, isSmart, lowPower);

				for (int i = 0; i < locations.size(); i++) {
					location = locations.get(i);
					int locID = location.getLocationID();
					if (locID == locationID) {
						isUniqueLoc = false;
						location.addAppliance(regApp);
					}
				}

				if (isUniqueLoc) {
					location = new Location(locationID);
					location.addAppliance(regApp);
					appSim.addLocation(location);
				}
			}

			file.close();
		} catch (IOException ioe) {
			System.out.println("The file cannot be read");
		}
	}

	public static void main(String[] args) throws Exception {

		AppSim appSim = new AppSim();
		MainMenu menu = new MainMenu();
		// User interactive part
		String option1, option2, app_string;
		Scanner scan = new Scanner(System.in);
		boolean flag = true;
		int steps;
		String line = "";
		// file stream
		File appsToUse = new File("resource/appliancesToUse.csv");
		String inputFileName = appsToUse.getAbsolutePath();
		// File appsPersisted = new File("resource/appliancesPersisted.csv");
		// String outputFileName = appsPersisted.getAbsolutePath();

		ArrayList<String> lines = new ArrayList<String>();
		ArrayList<Appliance> allApps = new ArrayList<Appliance>();
		FileWriter fw = new FileWriter(inputFileName, true);
		BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
		try {
			// parsing a CSV file into BufferedReader class constructor
			while ((line = reader.readLine()) != null) {
				allApps.add(menu.StringToAppliance(line));
			}
			reader.close();
			// System.out.println(allApps);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			first: while (flag) {// Application menu to be displayed to the user.
				menu.printMenu();
				option1 = scan.nextLine();
				/* Complete the skeleton code below */
				switch (option1) {
					case "A":
						allApps.add(menu.StringToAppliance(menu.CreateAppliance(scan)));
						Collections.sort(allApps, Appliance.ByLocation);
						continue first;
					case "D":
						System.out.println("Please enter a number between 0 and " + allApps.size());
						int toDelete = scan.nextInt();
						allApps.remove(toDelete);
						continue first;
					case "L":
						menu.ListAppliances(allApps, scan);
						continue first;
					case "S":
						System.out.println(
								"Are you sure? If you start the simulation now, you won't be able to add/delete any appliances later");
						System.out.println(
								"Type \"Y\" to start the simulation, or type \"N\" to go back to the main menu");
						String startSim = scan.nextLine();
						switch (startSim) {
							case "Y":
								while (true) {
									System.out.print("Enter the total allowed wattage(power): ");
									String wattageInput = scan.nextLine();

									if (Validations.validateInt(wattageInput)) {
										int wattage = Integer.parseInt(wattageInput);
										appSim.setAllowedWattage(wattage);
										break;
									}
								}
								// Get steps
								while (true) {
									System.out.println("Enter the number of steps: ");
									String stepsInput = scan.nextLine();

									if (Validations.validateInt(stepsInput)) {
										steps = Integer.parseInt(stepsInput);
										break;
									}
								}
								readAppFile(inputFileName, appSim);
								appSim.simulationLoop(steps);
								System.out.println("Would you like to go back to the main menu?");
								System.out.println("Type \"Y\" for yes, or type \"N\" to terminate");

							case "N":
								break;
						}
					case "Q":
						flag = false;
						break;
				}
			}
		} catch (Exception error) {
		}
		scan.close();

	}

	// Run simulation

	public static void copyContent(File a, File b)
			throws Exception {
		FileInputStream in = new FileInputStream(a);
		FileOutputStream out = new FileOutputStream(b);

		try {

			int n;

			// read() function to read the
			// byte of data
			while ((n = in.read()) != -1) {
				// write() function to write
				// the byte of data
				out.write(n);
			}
		} finally {
			if (in != null) {

				// close() function to close the
				// stream
				in.close();
			}
			// close() function to close
			// the stream
			if (out != null) {
				out.close();
			}
		}
	}

}