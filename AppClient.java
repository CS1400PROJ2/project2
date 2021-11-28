import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

class AppClient {

	public static void readAppFile(String fileName, AppSim appSim) {
		BufferedReader file;
		try {
			file = new BufferedReader(new FileReader(fileName));

			String appStr;
			int uniqueID = 0;
			while ((appStr = file.readLine()) != null) 
			{
				uniqueID += 1;
				String[] appliance = appStr.split(",");

				int locationID = Integer.parseInt(appliance[0]);
				String appName = appliance[1];
				int onPower = Integer.parseInt(appliance[2]);
				double probOn = Double.parseDouble(appliance[3]);
				boolean isSmart = Boolean.parseBoolean(appliance[4]);
				int lowPower = Integer.parseInt(appliance[5]);

				Location location;
				ArrayList<Location> locations = appSim.getLocations();
				boolean isUniqueLoc = true;

				SmartAppliance smartApp = null;
				Appliance regApp = null;

				if (isSmart == true) 
				{
					smartApp = new SmartAppliance(uniqueID, locationID, appName, onPower, probOn, isSmart, lowPower);
				} 
				else if (isSmart == false) 
				{
					regApp = new Appliance(uniqueID, locationID, appName, onPower, probOn, isSmart);
				}

				for (int i = 0; i < locations.size(); i++) 
				{
					location = locations.get(i);
					int locID = location.getLocationID();
					if (locID == locationID) 
					{
						isUniqueLoc = false;
						if (isSmart == true) 
						{
							location.addAppliance(smartApp);
						} 
						else if (isSmart == false) 
						{
							location.addAppliance(regApp);
						}
					}
				}

				if (isUniqueLoc) {
					location = new Location(locationID);
					if (isSmart == true) 
					{
						location.addAppliance(smartApp);
					} 
					else if (isSmart == false) 
					{
						location.addAppliance(regApp);
					}
					appSim.addLocation(location);
				}
			}

			file.close();
		} catch (IOException ioe) {
			System.out.println("The file can not be read");
		}
	}

	public static void main(String[] args) {

		AppSim appSim = new AppSim();

		// User interactive part
		String option1, option2, app_string;
		Scanner scan = new Scanner(System.in);
		boolean flag = true;
		int steps;

		// file stream
		String fileName = "output.txt";
		FileReader fr = null;
		FileWriter fw = null;
		PrintWriter pw = null;
		BufferedReader reader = null;
		BufferedWriter writer = null;

		// Get wattage
		while (true)
		{
			System.out.println("Enter the total allowed wattage(power): ");
			String wattageInput = scan.nextLine();

			if (Validations.validateInt(wattageInput))
			{
				int wattage = Integer.parseInt(wattageInput);
				appSim.setAllowedWattage(wattage);
				break;
			}			
		}

		// Get filename
		System.out.println("Enter the CSV filename: ");
		fileName = scan.nextLine();
		
		// Get steps
		while (true)
		{
			System.out.println("Enter the number of steps: ");
			String stepsInput = scan.nextLine();

			if (Validations.validateInt(stepsInput))
			{
				steps = Integer.parseInt(stepsInput);
				break;
			}			
		}

		try {
			while (flag) {// Application menu to be displayed to the user.
				System.out.println("Select an option:");
				System.out.println("Type \"A\" Add an appliance");
				System.out.println("Type \"D\" Delete an appliance");
				System.out.println("Type \"L\" List the appliances");
				System.out.println("Type \"Q\" to Quit");
				option1 = scan.nextLine();

				/* Complete the skeleton code below */
				switch (option1) {
				case "A":
					pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
					String a, b, c, d, e, f;

					System.out.println("Enter the eight digit location ID: ");
					a = scan.nextLine();

					// while (true)
					// {
					// 	System.out.println("Enter the eight digit location ID: ");
					// 	a = scan.nextLine();

					// 	if (Validations.validateLocationID(a))
					// 	{
					// 		break;
					// 	}			
					// }

					System.out.println("Enter the appliance description: ");
					b = scan.nextLine();

					// while (true)
					// {
					// 	System.out.println("Enter the appliance description: ");
					// 	b = scan.nextLine();

					// 	if (Validations.validateAppDescription(b))
					// 	{
					// 		break;
					// 	}			
					// }

					System.out.println("Enter power used by the appliance: ");
					c = scan.nextLine();

					// while (true)
					// {
					// 	System.out.println("Enter power used by the appliance: ");
					// 	c = scan.nextLine();

					// 	if (Validations.validatePowerUsed(c))
					// 	{
					// 		break;
					// 	}			
					// }

					System.out.println("Enter the probability that the appliance is on: ");
					d = scan.nextLine();

					// while (true)
					// {
					// 	System.out.println("Enter the probability that the appliance is on: ");
					// 	d = scan.nextLine();

					// 	if (Validations.validateProb(d))
					// 	{
					// 		break;
					// 	}			
					// }

					System.out.println("Is this a smart appliance? Enter \"true\" or \"false\": ");
					e = scan.nextLine();

					// while (true)
					// {
					// 	System.out.println("Is this a smart appliance? Enter \"true\" or \"false\": ");
					// 	e = scan.nextLine();

					// 	if (Validations.validateIsSmart(e))
					// 	{
					// 		break;
					// 	}			
					// }

					System.out.println("Enter the power used in low state: ");
					f = scan.nextLine();

					// while (true)
					// {
					// 	System.out.println("Enter the power used in low state: ");
					// 	f = scan.nextLine();

					// 	if (Validations.validateLowPower(f))
					// 	{
					// 		break;
					// 	}			
					// }

					String newAppliance = a + "," + b + "," + c + "," + d + "," + e + "," + f;
					pw.println(newAppliance);
					pw.close();
					break;
				case "D":
					System.out.println("Enter the name of the appliance to delete: ");
					option2= scan.nextLine();

					String tmpFileName = "tmp.txt";
					File tmpFile = new File(tmpFileName);

					pw = new PrintWriter(new BufferedWriter(new FileWriter(tmpFileName, true)));

					BufferedReader file;
					try {
						file = new BufferedReader(new FileReader(fileName));
			
						String appStr;
						while ((appStr = file.readLine()) != null) 
						{
							String[] appliance = appStr.split(",");
			
							String appName = appliance[1];

							if (!appName.equals(option2))
							{
								pw.println(appStr);
							}
						}

						tmpFile.renameTo(new File(fileName));

						file.close();
						pw.close();
					} catch (IOException ioe) {
						System.out.println("The file can not be read");
					}
					break;
				case "L":
					reader = new BufferedReader(new FileReader("output.txt"));
					System.out.println("Select list option:");
					System.out.println("Type \"A\" All appliances for a location");
					System.out.println("Type \"B\" All appliances of a particular type across all locations");
					option2 = scan.nextLine();
					switch (option2) {
					case "A":
						System.out.println("Enter a location ID");
						option2 = scan.nextLine();

						while ((app_string = reader.readLine()) != null) {
							String[] appliance = app_string.split(",");
							if (appliance[0].equals(option2)) {
								System.out.println(app_string);
							}
						}
						break;
					case "B":
						System.out.println("Smart or not-smart?");
						System.out.println("Type \"S\" Smart");
						System.out.println("Type \"R\" Regular");
						option2 = scan.nextLine();

						switch (option2) {
						case "S":
							while ((app_string = reader.readLine()) != null) {
								String[] appliance = app_string.split(",");
								if (appliance[4].equals("true")) {
									System.out.println(app_string);
								}
							}
							break;
						case "R":
							while ((app_string = reader.readLine()) != null) {
								String[] appliance = app_string.split(",");
								if (appliance[4].equals("false")) {
									System.out.println(app_string);
								}
							}
							break;
						}
						break;
					}
					reader.close();
					break;
				case "Q":
					flag = false;
					break;
				}
			}
		} catch (Exception error) {}
		scan.close();

		// Run simulation
		readAppFile(fileName, appSim);
		appSim.simulationLoop(steps);
	}
}