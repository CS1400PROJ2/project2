import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
class AppClient{
	
	public void readAppFile(String fileName, AppSim appSim)
	{ 
		BufferedReader file;
		String appStr;

		try {
			file = new BufferedReader(new FileReader("output.txt"));

			while ((appStr = file.readLine()) != null) {
				String[] appliance = appStr.split(",");
				
				if(appliance[4] == "true")
				{
					int locationID = Integer.parseInt(appliance[0]);
					String appName = appliance[1]; 
					int onPower = Integer.parseInt(appliance[2]);
					double probOn = Double.parseDouble(appliance[3]); 
					int lowPower = Integer.parseInt(appliance[5]);

					SmartAppliance smartApp = new SmartAppliance(locationID, appName, onPower, probOn,lowPower);
					appSim.addAppliance(smartApp);

					Location location;
					ArrayList<Location> locations = appSim.getLocations();
					boolean isUniqueLoc = true;
					for(int i = 0; i < locations.size(); i++)
					{
						location = locations.get(i);
						int locID = location.getLocationID();
						if(locID == locationID)
						{
							isUniqueLoc = false;
							location.addAppliance(smartApp);
						}
					}

					if (isUniqueLoc)
					{
						location = new Location(locationID);
						location.addAppliance(smartApp);
						appSim.addLocation(location);
					}
				}
				else if(appliance[4] == "false")
				{
					int locationID = Integer.parseInt(appliance[0]);
					String appName = appliance[1]; 
					int onPower = Integer.parseInt(appliance[2]);
					double probOn = Double.parseDouble(appliance[3]); 

					Appliance regApp = new Appliance(locationID, appName, onPower, probOn);

					appSim.addAppliance(regApp);

					Location location;
					ArrayList<Location> locations = appSim.getLocations();
					boolean isUniqueLoc = true;
					for(int i = 0; i < locations.size(); i++)
					{
						location = locations.get(i);
						int locID = location.getLocationID();
						if(locID == locationID)
						{
							isUniqueLoc = false;
							location.addAppliance(regApp);
						}
					}

					if (isUniqueLoc)
					{
						location = new Location(locationID);
						location.addAppliance(regApp);
						appSim.addLocation(location);
					}
				}
			}

			file.close();
		}catch(IOException ioe){ 
			System.out.println("The file can not be read");
		}
	}
	
	
	public static void main(String []args){
		
		AppClient app = new AppClient();
		AppSim appSim = new AppSim();

		
		//User interactive part
		String option1, option2, app_string;
		Scanner scan = new Scanner(System.in);
		int wattage, steps;
		boolean flag = true;

		//file stream
		String fileName = "output.txt";
		BufferedReader file = null;
		
		// Get the user inputs
		System.out.println ("Enter the total allowed wattage(power): ");
		wattage = scan.nextInt();

		//System.out.println("Enter the CSV filename: ");
		//fileName = scan.nextLine();

		//System.out.println("Enter the number of steps: ");
		//steps = scan.nextInt();

		try
		{
			while(flag){// Application menu to be displayed to the user.
				System.out.println("Select an option:");
				System.out.println("Type \"A\" Add an appliance");
				System.out.println("Type \"D\" Delete an appliance");
				System.out.println("Type \"L\" List the appliances");
				System.out.println("Type \"Q\" to Quit");
				option1=scan.nextLine();
				
				/* Complete the skeleton code below */
				switch (option1) {
					case "A": 
						FileWriter fw = new FileWriter(fileName,true);
						String a, b, c, d, e, f;
						System.out.println("Enter the eight digit location ID: ");
						a = scan.nextLine();
						System.out.println("Enter the appliance description: ");
						b = scan.nextLine();
						System.out.println("Enter power used by the appliance: ");
						c = scan.nextLine();
						System.out.println("Enter the probability that the appliance is on: ");
						d = scan.nextLine();
						System.out.println("Is this a smart appliance? Enter T/F: ");
						e = scan.nextLine();
						System.out.println("Enter the power used in low state: ");
						f = scan.nextLine();
						String newAppliance = a + "," + b + "," + c + "," + d + "," + e + "," + f;
						fw.write(newAppliance);
						fw.close();
						break;
					case "D":

						break;
					case "L":
						file = new BufferedReader(new FileReader("output.txt"));
						System.out.println("Select list option:");
						System.out.println("Type \"A\" All appliances for a location");
						System.out.println("Type \"B\" All appliances of a particular type across all locations");
						option2=scan.nextLine();
						switch(option2)
						{
							case "A":
								System.out.println("Enter a location ID");
								option2=scan.nextLine();

								while ((app_string = file.readLine()) != null) {
									String[] appliance = app_string.split(",");
									if (appliance[0].equals(option2))
									{
										System.out.println(app_string);
									}
								}
								break;
							case "B":
								System.out.println("Smart or not-smart?");
								System.out.println("Type \"S\" Smart");
								System.out.println("Type \"R\" Regular");
								option2=scan.nextLine();

								switch(option2)
								{
									case "S":
										while ((app_string = file.readLine()) != null) {
											String[] appliance = app_string.split(",");
											if (appliance[4].equals("true"))
											{
												System.out.println(app_string);
											}
										}
										break;
									case "R":
										while ((app_string = file.readLine()) != null) {
											String[] appliance = app_string.split(",");
											if (appliance[4].equals("false"))
											{
												System.out.println(app_string);
											}
										}
										break;
								}
								break;
						}
						file.close();
						break;
					case "Q":
						flag = false;
						break;
				}
			}
		}
		catch(Exception error){}
		scan.close();
	}
}