import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {

	public void printMenu() {
		System.out.println("Welcome to the appliance simulator");
		System.out.println("Select an option:");
		System.out.println("Type \"A\" to add an appliance");
		System.out.println("Type \"D\" to delete an appliance");
		System.out.println("Type \"L\" to list the appliances");
		System.out.println("Type \"S\" to start the simulation");
		System.out.println("Type \"Q\" to quit");
	}

	public void ListAppliances(ArrayList<Appliance> apps, Scanner scan) {
		System.out.println("Select list option:");
		System.out.println("Type \"A\" All appliances for a location");
		System.out.println("Type \"B\" All appliances of a particular type across all locations");
		String option2 = scan.nextLine();
		switch (option2) {
			case "A":
				System.out.println("Enter an 8 digit location ID");
				option2 = scan.nextLine();
				int option2String = Integer.parseInt(option2);
				for (int i = 0; i < apps.size(); i++) {
					if (apps.get(i).getLocationID() == option2String) {
						apps.get(i).printAppliance();
					}
				}
				break;
			case "B":
				System.out.println("Smart or regular?");
				System.out.println("Type \"S\" Smart");
				System.out.println("Type \"R\" Regular");
				option2 = scan.nextLine();
				switch (option2) {
					case "S":
						for (int i = 0; i < apps.size(); i++) {
							if (apps.get(i).getSmart()) {
								apps.get(i).printAppliance();
							}
						}
						break;
					case "R":
						for (int i = 0; i < apps.size(); i++) {
							if (!(apps.get(i).getSmart())) {
								apps.get(i).printAppliance();
							}
						}
						break;
				}
				break;
		}
	}

	public Appliance StringToAppliance(String appStr, int thisappsID) {
		String[] appliance = appStr.split(",");

		int locationID = Integer.parseInt(appliance[0]);
		String appName = appliance[1];
		int onPower = Integer.parseInt(appliance[2]);
		double probOn = Double.parseDouble(appliance[3]);
		boolean isSmart = Boolean.parseBoolean(appliance[4]);
		double lowPower = Double.parseDouble(appliance[5]);

		Appliance regApp = null;
		{
			regApp = new Appliance(locationID, appName, onPower, probOn, isSmart, lowPower, thisappsID);
			return regApp;
		}
	}

	public String CreateAppliance(Scanner scan, NonStaticValidations validation) {
		String a, b, c, d, e, f = "";
		System.out.println("Enter the eight digit location ID: ");
		a = scan.nextLine();
		validation.validateInt(a);
		System.out.println("Enter the appliance name: ");
		b = scan.nextLine();
		System.out.println("Enter power used by the appliance: ");
		c = scan.nextLine();
		validation.validateInt(c);
		System.out.println("Enter the probability that the appliance is on: ");
		d = scan.nextLine();
		validation.validateDouble(d);
		System.out.println("Is this a smart appliance? Enter T/F: ");
		e = scan.nextLine();
		validation.validateTF(e);
		if (e.equals("T")) {
			e = "True";
			System.out.println("Enter the power used in low state: ");
			f = scan.nextLine();
		} else if (e.equals("F")) {
			e = "False";
			f = "0";
		}
		String newAppliance = a + "," + b + "," + c + "," + d + "," + e + "," + f;
		return newAppliance;
	}

}
