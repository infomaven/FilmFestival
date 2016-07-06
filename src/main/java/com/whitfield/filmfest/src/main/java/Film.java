package src.main.java;
/**
 * Created by nwhit8 on 10/28/15.
 * Data object
 */
public class Film<L,R> implements Comparable<Film>  {

    private final L title;
    private final R duration;
    // one mutable field
    private int startTime=0;
    private int finishTime =0;
    private int trackAssignment = 0;



    public Film(L title, R duration) {
        this.title = title;
        this.duration = duration;


    }

    public L getTitle() { return title;}
    public R getDuration() { return duration; }

    public void setStartTime(int value) {
        this.startTime = value;
    }

    public void setFinishTime( int value) {
        this.finishTime = value;
    }

    public void setTrackAssignment( int value ) {
        this.trackAssignment = value;
    }

    public int getFinishTime() {
        return this.finishTime;
    }
    public int getStartTime() {
        return this.startTime;
    }
    public int getTrackAssignment() { return this.trackAssignment; }

@Override
public int hashCode() { return title.hashCode() ^ duration.hashCode();}

    @Override
    public boolean equals( Object o ) {
        if (!(o instanceof Film)) return false;
        Film pairO = (Film) o;
        return this.title.equals(( pairO.getTitle())) &&
                this.duration.equals( pairO.getDuration());
    }

    public int compareTo( Film compareFilmFinishTimes) {

        int compareFinishTime = ((Film) compareFilmFinishTimes).getFinishTime();
        return this.finishTime - compareFinishTime;
    }

    @Override
    public String toString() {

        String timeLabel = "";
        String durationAsString = "";
        String title = (String) getTitle();
        Integer duration = (Integer) getDuration();

        if (title.contains("Lunch") || title.contains("Networking")) {
            durationAsString = "";
        } else if ( duration == 5) {
            durationAsString = "lightning";
        } else {
                durationAsString = duration + "min";
            }

        int startTimeMinutes = getStartTime();
        String formattedStartTime ="";
        formattedStartTime = convertMinutesToHoursMinutes(startTimeMinutes);
        String track = formattedStartTime + " " + title + " " + durationAsString;

        return track;
    }

    private String convertMinutesToHoursMinutes(int timeInMinutes) {

        String conversion="";
        String timeLabel = "";
        int hours = timeInMinutes / 60;
        int minutes = timeInMinutes % 60;
        if (hours < Session.END_AM) {
            timeLabel = "AM";
        } else {
            timeLabel = "PM";
            hours = hours - 12;
        }

        /// zero pad both
        String prettyHours = String.format( "%02d", hours);
        String prettyMinutes = String.format( "%02d", minutes);
        conversion = prettyHours + ":" + prettyMinutes;
        return conversion;
    }

}
