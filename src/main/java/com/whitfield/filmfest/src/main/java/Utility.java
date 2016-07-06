package src.main.java;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by nwhit8 on 10/25/15.
 */
public class Utility {

    /// represents full list of topics offered  (180m max for  AM +  240min max  in PM)
    private int count = 0;

    private String[] linesFromFile;
    private ArrayList<Film> sessionPool;
    private ArrayList<Session> finalResults;
    private String current;



    public Utility(String fileName) {
        this.current = fileName;
        init();

    }

    private void  init() {
        String textFromFile = ingestFile( this.current );
        String[] dividedText = textFromFile.split("\n");
        this.linesFromFile = dividedText.clone();

    }

    public String ingestFile(String fileName) {

        StringBuilder result = new StringBuilder("");

        ClassLoader cLoader = getClass().getClassLoader();
        File file = new File( cLoader.getResource( fileName).getFile() );

        try (Scanner scanner = new Scanner( file )) {
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                result.append( nextLine).append("\n");
            }

            scanner.close();
        } catch (IOException e ) {
            e.printStackTrace();
        }

        return result.toString();
    }


    public List<Film> getListOfFilms() {

       List<Film> topicList = new ArrayList<>();

        for (int i=0; i < linesFromFile.length; i++ ) {
            String temp = linesFromFile[i].toString();
            String[] tokenArray = temp.split(" ");
            int lastIndex = tokenArray.length - 1;
            String durationToken = tokenArray[ lastIndex ].toString().toLowerCase();
            Integer duration;
            if( durationToken.contains("lightning")) { duration = 5;} else {
                duration = extractDurationFromString(durationToken);
            }
            String title = temp.replace( durationToken, "");
            Film topic = new Film(title,duration);
            topicList.add( topic);

        }
        return topicList;

    }

    private Integer extractDurationFromString(String durationStr) {
        /// default find
        Integer foundValue = 5;

        if( durationStr.contains("min"))  {
            String temp = durationStr.replace("min", "");
            foundValue = Integer.parseInt( temp);
        }

        return foundValue;
    }

    public Integer calcProgrammingTime( List<Film> inputList ) {

        Integer programInMinutes = 0;
        for ( Film f : inputList ) {
            programInMinutes = programInMinutes + (Integer)f.getDuration();
        }
        return programInMinutes;
    }
}
