import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Klassen håndterer fil ind- og output. Omdøbt fra LoaderClass til WashIO
 */
class WashFiles {
    /* strengene relative stier, de peger på salgslisten-filen og vasketype-filen */
    private final String pathToLedger = "SalesLedger.txt";
    private final String pathToWashTypes = "WashTypes.txt";
    private final String pathToUserList = "UserList.txt";

    /* metoden returnerer en arraylist af WashType objekter */
    public ArrayList<WashType> loadWashTypes() throws IOException {

        ArrayList<WashType> typesList = new ArrayList<WashType>();

        /* load washtypes from txt file */
        BufferedReader bufferedReader = new BufferedReader(new FileReader(pathToWashTypes));

        String barrierString = null;

        while ((barrierString = bufferedReader.readLine()) != null) {
            Scanner scanner = new Scanner(barrierString);

            WashType washType = new WashType(scanner.next(), scanner.nextDouble());
            typesList.add(washType);
        }
		bufferedReader.close();
        return typesList;
    }
	/* metoden indlæser listen over udførte handler fra fil; og printer dem på skærmen 
	* metoden har egentlig for meget 'interface' over sig; den præsenterende
	* del af koden burde ligge i UserInteraction */
    public void loadLedger() throws IOException {

        /* wrapping a bufferedreader around a filereader */
        BufferedReader bufferedReader = new BufferedReader(new FileReader(pathToLedger));

        String barrierString = null;

        /********
         * Michael har bare lige smidt en count som printes ind i den her
         *************/
        int runningTotal = 0;
        int count = 0;
        /*
         * Så længe at bufferedReader kan læse en linje i SalesLedger.txt vil while
         * loopet fortsætte
         */
        while ((barrierString = bufferedReader.readLine()) != null) {
            Scanner scanner = new Scanner(barrierString);

            String vaskeType = scanner.next();
            double vaskepris = scanner.nextDouble();
            String dato = scanner.next();
            String biltype = scanner.next();

            System.out.println(TO.blue(dato) + " " + TO.red(biltype) + " " + TO.green(vaskeType) + " " + vaskepris);

            runningTotal += vaskepris;
            count++;

			scanner.close();
        }
		bufferedReader.close();
        System.out.println("Total: " + runningTotal);
        System.out.println("Antal salg: " + count);
    }

    /* metoden returnerer en liste af WashCards; hvert washcard konstrueres via linie-for-linie
    * læsning af en fil indlæst vha. en FileReader og en bufferedReader (klassen); */
    public ArrayList<WashCard> loadUserList() throws IOException { // exception burde håndteres, men... demo

        /* wrapping a bufferedreader around a filereader */
        BufferedReader bufferedReader = new BufferedReader(new FileReader(pathToUserList));
        /* forbereder en liste til kort */
        ArrayList<WashCard> washCards = new ArrayList<WashCard>();

        String barrierString = null; /* string er defaulter egentlig */
        /*indlæser kort-liste*/
        while ((barrierString = bufferedReader.readLine()) != null) {

            Scanner scanner = new Scanner(barrierString);

            /* samler data fra tekst-filen og konstruerer nyt objekt */
            int readKortID = scanner.nextInt();
            double readAmountOnCard = scanner.nextDouble();

            WashCard card = new WashCard(readKortID, readAmountOnCard);
            /* kort lægges til listen */
            washCards.add(card);
        }
		bufferedReader.close();
        /* vaske-kort returneres */
        return washCards;
    }

	/* metoden skriver en handel til handelsliste-filen */
    void writeSalesToLedger(String washType, double price, String carType) {

        // DateFormat objektet kan formatere en dato som ønsket:
        DateFormat datoFormat = new SimpleDateFormat("dd/MM/yyyy");
        // Date objektet indeholder et 'tidsstempel' til formatering.
        Date dato = new Date();
        // format() formaterer dato efter det definerede datoFormat
        String formattedDate = datoFormat.format(dato);

        String formattedString =  "\n" + washType + " " + price + " " +	formattedDate + " " + carType;

        /* Herefter skrives formattedString til enden af salgslisten - write metoden skriver bytes til filen på supplerede path*/

        try{
            Files.write(Paths.get(pathToLedger), formattedString.getBytes(), StandardOpenOption.APPEND);
        } catch(IOException e){
            System.out.println("Filen fandtes ikke. Angiv en ordentlig sti.");
        }
    }
}

/***
 * Lidt om BufferedReader
 * https://alvinalexander.com/java/java-bufferedreader-readline-string-examples
 * Lidt om Thread.sleep
 * https://docs.oracle.com/javase/tutorial/essential/concurrency/sleep.html
 * Lidt om Path klassen:
 * https://docs.oracle.com/javase/8/docs/api/java/nio/file/Paths.html
 * Lidt om Files klassen:
 * https://docs.oracle.com/javase/7/docs/api/java/nio/file/Files.html
 */