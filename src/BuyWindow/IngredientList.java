package BuyWindow;

// IngredientList class manages quantities of ingredients for buying window
public class IngredientList {

    // Static variables to hold quantities of ingredients
    private static int numSugar = 0;
    private static int numJuice = 0;
    private static int numWater = 0;
    private static int numCups = 0;
    private static int numIce = 0;

    // Getter methods for each ingredient quantity

    public static int getNumSugar(){
        return numSugar;
    }

    public static int getNumJuice(){
        return numJuice;
    }

    public static int getNumWater(){
        return numWater;
    }

    public static int getNumCups(){
        return numCups;
    }

    public static int getNumIce(){
        return numIce;
    }

    // Setter methods for each ingredient quantity

    public static void setNumSugar(int n){
        numSugar = n;
    }

    public static void setNumJuice(int n){
        numJuice = n;
    }

    public static void setNumWater(int n){
        numWater = n;
    }

    public static void setNumCups(int n){
        numCups = n;
    }

    public static void setNumIce(int n){
        numIce = n;
    }
}
