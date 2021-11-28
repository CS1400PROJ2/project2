public class NonStaticValidations {
    public boolean validateInt(String str) {
        try {
            Integer.parseInt(str);
            return true;

        } catch (NumberFormatException error) {
            return false;
        }
    }

    public boolean validateDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;

        } catch (NumberFormatException error) {
            return false;
        }
    }

    public boolean validateTF(String str) {
        try {
            return (str.equals("T") || str.equals("F"));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateGridSize(int row, int column) {
        try {
            return row > 0 && column > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateZero(int row, int column, int x, int y) {
        try {
            return x > 0 && x <= row && y > 0 && y <= column;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateAlpha(double alpha) {
        try {
            return alpha >= 0 && alpha <= 1;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateBeta(double beta) {
        try {
            return beta >= 0 && beta <= 1;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateSteps(int steps) {
        try {
            return steps > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
