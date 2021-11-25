public class Validations
{
    public static boolean validateInt(String str) 
	{
		try 
		{
		    Integer.parseInt(str);
		    return true;
		    
		} catch (NumberFormatException error) {
		    return false;
		}
	}
	
	public static boolean validateDouble(String str) 
	{
		try 
		{
			Double.parseDouble(str); 
			return true;
			
		} catch (NumberFormatException error) {
		    return false;
		}
	}
	
	public static boolean validateGridSize(int row, int column) 
	{
		return row > 0 && column > 0;
	}
	
	public static boolean validateZero(int row, int column, int x, int y) 
	{
		return x > 0 && x <= row && y > 0 && y <= column;
	}

	public static boolean validateAlpha(double alpha) 
	{
		return alpha >= 0 && alpha <= 1;
	}

	public static boolean validateBeta(double beta) 
	{
		return beta >= 0 && beta <= 1;
	}

	public static boolean validateSteps(int steps) 
	{
		return steps > 0;
	}
}