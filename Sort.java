import java.util.ArrayList;
import java.util.*;

public class Sort {

	public void selectionSort(int[] array) {

		int temp;
		int index;
		int subarraylength = 0;
		for (int j = 0; j <= array.length - 1; j++) // we are going to go through the array size-1 times.​
		{
			subarraylength = array.length - j; // now find index of largest element in subarray​
			index = 0;
			for (int k = 1; k < subarraylength; k++) {
				if (array[k] > array[index]) {
					index = k;
				}
			}
			// Now swap array elements ​
			temp = array[index];
			array[index] = array[array.length - j - 1];
			array[array.length - j - 1] = temp;
		} // end top for​
	}

	public void powerControlAttempt1(ArrayList<SmartAppliance> appliances, int totalpower, int currpower) {
		Collections.sort(appliances);
		boolean isOk = currpower < totalpower;
		for (int i = 0; i < appliances.size(); i++) {
			if (isOk) {
				break;
			} else {
				SmartAppliance currApp = appliances.get(i);
				if (currApp.getState() == "ON") {
					currApp.setState("LOW");
					currpower = (int) (currpower - currApp.getConsumption()
							+ currApp.getConsumption() * currApp.getPowerReduction());
					isOk = currpower < totalpower;
				} else
					continue;
			}
		}

	}

}
