import java.util.Random;

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

    public String[] daysOfWeek3 = {"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SUNDAY"};

    public String[] dayPart = {"Morning","Daytime","Evening"};
    public String[] dayPart1 = {"Morning",};
    public String[] dayPart2 = {"Daytime"};
    public String[] dayPart3 = {"Evening"};

    public String[] offerType = {
            "PercentageDiscount","MoneyOff"
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

    public String getStation(){
        Random rand = new Random();
        int stationId = rand.nextInt(5) + 1;
        return Integer.toString(stationId);
    }

    public String getStations(){
        String Station;
        Random randomDay = new Random();
        int random2 = randomDay.nextInt(2) + 1;
        switch (random2){
            case 1:
                Station = getStation();
                break;
            case 2:
                Station = null;
                break;
            default:
                Station = getStation();

        } return Station;
    }

    public int getSaving(){
        Random rand = new Random();
        return rand.nextInt(150) + 20;
    }


    public String getOfferType(){
        String offerTypes = "";
        Random randomint2 = new Random();
        int rand = randomint2.nextInt(offerType.length);
        offerTypes = offerType[rand];
        return offerTypes;
    }

    public int getIterests(){
        Random randInterest = new Random();
        return randInterest.nextInt(14) + 1;
    }

    public String[] getDayOfWeek(){
        String[] dayOfWeek;
        Random randomDay = new Random();
        int randomm = randomDay.nextInt(3) + 1;
        switch (randomm){
            case 1:
                dayOfWeek = daysOfWeek1;
                break;
            case 2:
                dayOfWeek = daysOfWeek2;
                break;
            case 3:
                dayOfWeek = daysOfWeek3;
                break;
            default:
                dayOfWeek = daysOfWeek3;

        } return dayOfWeek;
    }

    public String[] getDayPart(){
        String[] DayTime;
        Random randomDay = new Random();
        int random1 = randomDay.nextInt(3) + 1;
        switch (random1){
            case 1:
                DayTime = dayPart1;
                break;
            case 2:
                DayTime = dayPart2;
                break;
            case 3:
                DayTime = dayPart3;
                break;
            default:
                DayTime = dayPart;

        } return DayTime;
    }

}
