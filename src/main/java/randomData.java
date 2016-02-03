import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by sanoshchenko on 2/3/16.
 */
public class randomData {
    public String[] name1 = {
            "Store", "Restaurant", "Bar", "Spa","Mall", "Cinema", "Theater", "Coffee shop", "Shop", "Movie",
            "Pub","Cafe","Hotel","Massage","Lounge bar", "Asian food", "Chinese foood", "Ukrainian food",
            "American food", "Greek food", "French food","Italian food", "Viennese food", "Jewish food",
            "Latin food","Russian food","British food","German food", "Netherlands food", "Sweden food",
            "Switzerland food","Portugal food", "Spain food","Belgium food"
    };
    public String[] name2 = {
            "Ben", "Aaron", "Omar", "Life","Abdul", "Thomas", "Martin", "Hall", "Allen", "Smith",
            "Paul","Donald","Charles","David","Edward", "Jason", "Sarah", "Deborah",
            "Ruth", "Betty", "Laura","Michelle", "Richard", "Joseph",
            "Brian","Kenneth","Mark","Garcia", "Young", "Taylor",
            "Flower","Bird", "Spoon","Fun"
    };

    public String[] category = {
            "Service", "Food", "Shopping", "Groceries","Entertainment"
    };

    public String[] daysOfWeek1 = {
            "MONDAY","TUESDAY","WEDNESDAY"
    };
    public String[] daysOfWeek2 = {
            "THURSDAY","FRIDAY","SUNDAY"
    };


    public String[] dayPart = {
            "Morning","Daytime","Evening"
    };



    public String getname(){
        String name = "";
        Random randomint = new Random();
        int rand = randomint.nextInt(name1.length);
        int rand1 = randomint.nextInt(name2.length);
        name = name1[rand]+ " " + name2[rand1];
        return name;
    }

    public String getcategory(){
        String category1 = "";
        Random randomint1 = new Random();
        int random = randomint1.nextInt(category.length);
        category1 = category[random];
        return category1;
    }

    public int getStations(){
            Random rand = new Random();
            int randomNum = rand.nextInt((5 - 1) + 1) + 1;
            return randomNum;
    }

    public String[] getDayPart(){
        return dayPart;
    }

}
