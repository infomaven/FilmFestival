package src.main.java;

import java.util.Collections;
import java.util.List;

/**
 * Created by nwhit8 on 11/1/15.
 */
public class Day {

    String name = "";
    List<Film> populatedTrack;


    public Day(String name, List<Film> completedTrackSchedule) {

        this.name = name;
        this.populatedTrack = completedTrackSchedule;
    }

    public Film getFilmFromTrack( String FilmTitle, Integer FilmDuration) {
        Film searchTerm = new Film( FilmTitle, FilmDuration );
        if (!this.populatedTrack.contains( searchTerm)) {
            return null;
        } else {
            int foundIndex = this.populatedTrack.indexOf( searchTerm);
            return this.populatedTrack.get( foundIndex);
        }
    }

    public int calculateTrackDuration() {
        Integer result = 0;
        for ( Film t : populatedTrack ) {
            result = result + (Integer) t.getDuration();
        }
        return result;
    }
    public void  printTrack() {

        Collections.sort( this.populatedTrack );
        System.out.println(    this.name );
        for (Film t : populatedTrack ) {
            System.out.println( t.toString() );
        }
    }

    public List<Film> getScheduledListOfFilms() {
        return this.populatedTrack;
    }

}
