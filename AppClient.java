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



	public static void main(String[] args) throws Exception {
		AppSim appSim = new AppSim();
		MainMenu menu = new MainMenu();
		Validations validation = new Validations();
		NonStaticValidations nsv = new NonStaticValidations();
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
		int nextID = 1;
		try {
			// parsing a CSV file into BufferedReader class constructor
			while ((line = reader.readLine()) != null) {
				allApps.add(menu.StringToAppliance(line, nextID));
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for(int i = 0; i < allApps.size(); i++){
			allApps.get(i).setUniqueID(90000000 + nextID);
			nextID++;
		}
		try {

			first: while (flag) {
			// Application menu to be displayed to the user.
				menu.printMenu();
				option1 = scan.nextLine();
				while(!Validations.validateMenuChoice(option1)){
					System.out.println("Your output was invalid. Please try again");
					option1 = scan.nextLine();
				}
				/* Complete the skeleton code below */
				switch (option1.toUpperCase()) {
					case "A":
						String appToAdd = menu.CreateAppliance(scan, nsv);
						Appliance applToAdd = (menu.StringToAppliance(appToAdd, nextID));
						nextID++;
						allApps.add(applToAdd);
						System.out.println("your appliance was successfully added");
						Collections.sort(allApps, Appliance.ByLocation);
						continue first;
					case "D":
						System.out.println("The appliance simulator uses an ID system to delete appliances.");
						System.out.println("Please enter the ID of the appliance that you want to delete, from " + allApps.get(0).getUniqueID() + " to " + allApps.get(allApps.size() - 1).getUniqueID());
						String toDelete = scan.nextLine();
						while(!validation.validatePositiveInt(toDelete)||(Integer.parseInt(toDelete) < allApps.get(0).getUniqueID()) || (Integer.parseInt(toDelete) > allApps.get(allApps.size() - 1).getUniqueID())){
							System.out.println("Invalid appliance ID. Try again");
							toDelete = scan.nextLine();
						}
						int deleteID = scan.nextInt();
						boolean deleted = false;
						for(int i = 0; i < allApps.size(); i++){
							int currID = allApps.get(i).getUniqueID();
							if(currID == deleteID){
								allApps.remove(allApps.get(i));
								System.out.println("The appliance with id " + deleteID +" has been successfully deleted");
								break;
							}
							if(currID > deleteID){
								System.out.println("There was an error in deleting your appliance, or we couldn't find it. Redirecting you to the main menu…");
								break;
							}
						}
						continue first;
					case "L":
						menu.ListAppliances(allApps, scan);
						continue first;
					case "S":
						boolean flag2 = true;
						System.out.println(
								"Are you sure? If you start the simulation now, you won't be able to add/delete any appliances later");
						System.out.println(
								"Type \"Y\" to start the simulation, or type anything else to go back to the main menu");
						String startSim = scan.nextLine();

						switch (startSim.toUpperCase()) {
							case "Y":
								while (flag2) {
									System.out.println("Enter the total allowed wattage(power) in watts: ");
									System.out.println("For reference, the total wattage for your provided appliances in the on state is "+calculateCurrentWattageAllOn(allApps) + " watts");
									String wattageInput = scan.nextLine();
									while (!validation.validatePositiveInt(wattageInput)){
										System.out.println("Invalid input. Try again!");
										wattageInput = scan.nextLine();
									}

										int wattage = Integer.parseInt(wattageInput);
										appSim.setAllowedWattage(wattage);
										break;

								}
								// Get steps
								while (flag2) {
									System.out.println("Enter the number of steps you want this simulation to run: ");
									String stepsInput = scan.nextLine();
									while (!validation.validatePositiveInt(stepsInput)){
										System.out.println("Invalid input. Try again!");
										stepsInput = scan.nextLine();
									}
										steps = Integer.parseInt(stepsInput);
										appSim.simulationLoop(steps, allApps);
										break;

								}
									//readAppFile(inputFileName, appSim);
								while (flag2) {
									System.out.println("Would you like to go back to the main menu?");
									System.out.println("Type \"yes\" for yes, or type anything else to terminate");
									String back = scan.nextLine().toLowerCase();
									switch (back) {
										case "yes":
											continue first;
										default:
											flag2 = false;
											flag = false;
											System.out.println("Thank you for using the appliance simulator!");
											break;

									}
								}
							default:
								continue first;

						}
					case "Q":
						flag = false;
						System.out.println("Thank you for using the appliance simulator!");
						break;

					default:
						System.out.println("There was an error in your input. Please try again with a valid input");
				}
			}
		} catch (Exception error) {
		}
		scan.close();

	}
	public static int calculateCurrentWattageAllOn(ArrayList<Appliance> apps){
		int currWatt = 0;
		for(int i = 0; i < apps.size(); i++){
			currWatt = currWatt + apps.get(i).getConsumption();
		}
		return  currWatt;
	}

	// Run simulation


}