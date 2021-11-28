public class Validations {

	public static boolean validateInt(String str) {
		try {
			Integer.parseInt(str);
			return true;

		} catch (NumberFormatException error) {
			return false;
		}
	}

	public static boolean validateDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;

		} catch (NumberFormatException error) {
			return false;
		}
	}

	public static boolean validateTF(String str) {
		try {
			return (str.equals("T") || str.equals("F"));
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean validateMax(int str, int max) {
		try {
			return (str <= max);
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean validateMenuChoice(String str) {
		try {
			return (str.equals("A") || str.equals("D") || str.equals("L") || str.equals("S") || str.equals("Q"));
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean validateGridSize(int row, int column) {
		try {
			return row > 0 && column > 0;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean validateZero(int row, int column, int x, int y) {
		try {
			return x > 0 && x <= row && y > 0 && y <= column;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean validateAlpha(double alpha) {
		try {
			return alpha >= 0 && alpha <= 1;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean validateBeta(double beta) {
		try {
			return beta >= 0 && beta <= 1;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean validateSteps(int steps) {
		try {
			return steps > 0;
		} catch (Exception e) {
			return false;
		}
	}
}