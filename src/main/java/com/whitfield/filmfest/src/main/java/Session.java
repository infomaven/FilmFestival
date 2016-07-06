package src.main.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Session is a collection of Films. It has two parts - AM and PM
 *
 */
public class Session implements Comparable<Session>{
    int sessionStartTime = 0;
    int programmedDay = 0;
    int totalProgramTime = 0;
    private static List<Film> INPUT;
    public static List<Film> OUTPUT;

    public static int STANDARD_SESSION = 180;
    public static int START_AM = 9 * 60;
    public static int END_AM = 12 * 60;
    public static int START_PM = 13 * 60;
    public static int END_PM = 17 * 60;
    public static  int START_EVENING = 18 * 60;
    public static int END_EVENING = 23 * 60 + 59;
    // cumulative programmed time for a FESTIVAL DAY. When reached, start new Day of programming
    public static  int DAY_TRACK = (24- START_AM) * 60;

    public Session(List<Film> choices) {

       this.INPUT = choices;
    }

    public List<Film> getOriginalListOfFilms() {
        return this.INPUT;
    }

    public List<Film> getScheduledSession() {
        return this.OUTPUT;
    }

    /**
     * Uses hashcode of title + duration to find a Film from a given List
     * @param title
     * @param duration
     * @return
     */
     public Film getFilmFromSchedule(String title, Integer duration) {
         Film searchTerm = new Film(title, duration);
         if (!this.OUTPUT.contains(searchTerm)) {
             return null;
         } else {
             int foundIndex = this.OUTPUT.indexOf( searchTerm);
             return this.OUTPUT.get( foundIndex);
         }
     }

    /**
     * Gets all unscheduled Films that havea a certain duration
     * @param searchTerm duration sought by user
     * @return list of Films
     */
    public List<Film> searchInputByDuration(int searchTerm) {

        ArrayList<Film> foundMatches = new ArrayList<Film>();
        String result ="";
        if(FilmIsFound(this.INPUT)) {
            for (Film t : INPUT) {
                Integer duration = (Integer) t.getDuration();
                if (duration == searchTerm) {
                    foundMatches.add(t);
                }
            }
        }
        return foundMatches;
    }

    /**
     * Creates a full track of programming  (AM Session, Lunch, PM session, Networking, Evening)
     * @return Possible Day Schedule   (DAY TRACK)
     * @param trackNumber Identifier for Day Schedule
     */
    public Day assignStartTimes(int trackNumber) {

        Day completedDay;
        boolean closestBreakIsLunch = true;
        this.programmedDay = trackNumber;
        List<Film> shortFilms = searchInputByDuration(5);
        int availableMinutes = INPUT.size() * 60;
        int startNext = START_AM;
        Integer cumulativeFilmTime = 0;
        int firstElement = 0;
        int startFilm;
        Integer currDuration;
        this.OUTPUT = new ArrayList<Film>();
        // extend Day from 6 pm to 11:59 pm
        while (startNext <= END_EVENING  && FilmIsFound( this.INPUT) ) {
            // determine if lunch or networking break is applicable
            closestBreakIsLunch = startNext <= END_AM;
            // if 60  min of  an official  break, try to schedule  a short film
            if ( FilmIsFound(shortFilms) &&( startNext == END_AM
                    || Math.abs(END_AM - startNext) < 30 || startNext == END_PM
                    || Math.abs( END_PM - startNext) < 30) ) {
                    Film t = shortFilms.get(firstElement);
                    startFilm = startNext;
                    currDuration = (Integer) t.getDuration();
                    t.setStartTime(startFilm);
                    t.setFinishTime(startFilm + currDuration);
                    availableMinutes = availableMinutes - currDuration;
                    t.setTrackAssignment(this.programmedDay);
                    startNext = t.getFinishTime();
                    this.OUTPUT.add(t);
                    shortFilms.remove(t);
                    this.INPUT.remove(t);
                    if ( closestBreakIsLunch ) {
                        assignLunch();
                        startNext = START_PM;
                    } else {
                        assignNetworking();
                        startNext = START_EVENING;
                    }

            } else {
                /// schedule regular  Films
                Film s = INPUT.get(firstElement);
                currDuration = (Integer) s.getDuration();
                startFilm = startNext;
                s.setStartTime(startFilm);
                s.setTrackAssignment(this.programmedDay);
                s.setFinishTime(startFilm + currDuration);
                this.OUTPUT.add(s);
                availableMinutes = availableMinutes - currDuration;
                startNext = s.getFinishTime();
                this.INPUT.remove(firstElement);
            }
        }  /// end the overarching while loop
                    String trackName = "Day - Schedule #  " + this.programmedDay;
                     completedDay = new Day(trackName, this.OUTPUT);
        return completedDay;
    }

    private boolean FilmIsFound(List<Film> listBeingSearched) {
        return listBeingSearched.size() > 0;
    }

    private void assignLunch() {
        Film lunch = new Film("Lunch", 60);
        /// assign lunch
        lunch.setStartTime(END_AM);
        lunch.setFinishTime(START_PM);
        this.OUTPUT.add(lunch);
        this.INPUT.remove( lunch );
    }

    private void assignNetworking() {
        Film nw = new Film("Networking", 60);
        /// assign lunch
        nw.setStartTime(END_PM);
        nw.setFinishTime(START_EVENING);
        this.OUTPUT.add(nw);
        this.INPUT.remove( nw );
    }

    public void printSchedule(List<Film> listOfFilms) {
        Collections.sort( listOfFilms );
        for (Film s : listOfFilms) {
            System.out.println( s.toString() );
        }
    }

    @Override
    public int compareTo(Session compareTracks) {

        int comparison = ((Session) compareTracks).getProgrammedDay();
        return this.programmedDay;
    }

    public int getProgrammedDay() {
        return programmedDay;
    }


}
