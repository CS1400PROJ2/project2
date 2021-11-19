/* This is a stub code. You can modify it as you wish. */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
class AppClient{
	
	public void readAppFile(String file){ // method to read the comma seperated appliance file.
		
		Scanner scan;
		
		try {
			File myFile=new File(file);
			scan=new Scanner(myFile);//each line has the format
			//locationID,name of app,onPower,probability of staying on, smart or not,Smart appliances (if "on") power reduction percent when changed to "low" status(floating point, i.e..33=33%).
			String str;
			int locationID;
			String appName;
			int onPower;
			double probOn;
			String appType;
			int lowPower;
			Appliance aAppl;
			
			/*Complete the method*/
			
			
			scan.close();
		}catch(IOException ioe){ 
			System.out.println("The file can not be read");
		}
	}
	
	
	public static void main(String []args){
		
		AppClient app = new AppClient();

		
		//User interactive part
		String option1, option2, app_string;
		Scanner scan = new Scanner(System.in);

		//file stream
		String fileName = "output.txt";
		BufferedReader file = null;
		
		// Get the user inputs

		boolean flag = true;
		
		try
		{
			while(flag){// Application menu to be displayed to the user.
				System.out.println("Select an option:");
				System.out.println("Type \"A\" Add an appliance");
				System.out.println("Type \"D\" Delete an appliance");
				System.out.println("Type \"F\" Read in the CSV file");	
				System.out.println("Type \"L\" List the appliances");
				System.out.println("Type \"Q\" to Quit");
				option1=scan.nextLine();
				
				/* Complete the skeleton code below */
				switch (option1) {
					case "A":
	
						break;
					case "D":
	
						break;
					case "F":

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
	}
}