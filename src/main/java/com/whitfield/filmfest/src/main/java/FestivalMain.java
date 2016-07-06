package src.main.java;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nwhit8 on 11/1/15.
 */
public class FestivalMain {

    public static void main( String[] args )  {

        String FILE = "src/main/java/input.txt";

        Utility utility = new Utility( FILE);
        List<Day> dayCollection = new ArrayList<Day>();
        Session session = new Session (utility.getListOfFilms());
        Day results;
        int trackCounter = 1;
        while( session.getOriginalListOfFilms().size() > 0) {
            results = session.assignStartTimes( trackCounter);
            dayCollection.add( results);
            trackCounter++;
        }
        for ( Day tr : dayCollection) {
            tr.printTrack();
            System.out.println("\n");

        }
    }

}
