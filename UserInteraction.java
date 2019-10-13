import java.util.ArrayList;
import java.util.Scanner;

/* Klassen indeholder alle overordnede menuer
 * Intentionen er at den kun new'es fra Login, når bruger credentials gives
 *  */
class UserInteraction
{
    String bord = "*--**--*-*-*-**-*-**--*";
    /* Listen over mulige vaske, der forevises kunden*/

    ArrayList<WashType> washTypes;
    WashCard card;

    public UserInteraction(WashCard CARD, ArrayList<WashType> WASHTYPES)
    {
        card = CARD;
        washTypes = WASHTYPES;
    }

    /* main menu loop: kaldes fra Login:loginLoop() */
    void userLoop()
    {
        boolean abort = false; // abort bruges til at kvitte eller forblive i loopet
        while(!abort)
        {
            /* Terminalen pyntes lidt */
            prepareTerminalAndWrite("\t\t[1] Køb vask!"
                                    + "\t\t[2] Top op!"
                                    + "\n\n\t\t\t[0] Log ud.\n");
            /*  */
            int choice = GET.getIntegerFromUser();
            switch(choice)
            {
                case 0:
                    abort = true;
                    break;
                case 1:
                    buyWash();
                    break;
                case 2:
                    topUp();
                    break;
            }
        }

        // rent fjol; printer farvede beskeder i tids-intervaller på 0.9 sekund
        animateMessageInColor("\n\n\tLogging out. Beeep.\t\n", 2);
        animateMessageInColor("\n\n\t\t Bip.\t\n", 2);
        animateMessageInColor("\n\n\t System Ready.\t\n", 1);
        System.out.println();
    }

    /* Admin loop der præsenterer det normale user loop + mulighed for at åbne admin  */
    void adminLoop()
    {
        boolean abort = false; // abort bruges til at kvitte eller forblive i loopet
        while(!abort)
        {
            /* Terminalen pyntes lidt */
            prepareTerminalAndWrite("\t\t[1] Køb vask!"
                                    + "\t\t[2] Top op!"
                                    + "\n\n\t\t[3] Admin"
                                    + "\t\t\t[0] Log ud.\n");

            int choice = GET.getIntegerFromUser();
            switch(choice)
            {
                case 0:
                    abort = true;
                    break;
                case 1:
                    buyWash();
                    break;
                case 2:
                    topUp();
                    break;
                case 3:
                    adminInnerLoop();
                    break;
            }
        }

        // rent fjol; printer farvede beskeder i tids-intervaller på ~0.9 sekund
        animateMessageInColor("\n\n\tLogging out. Beeep.\t\n", 2);
        animateMessageInColor("\n\n\t\t Bip.\t\n", 2);
        animateMessageInColor("\n\n\t System Ready.\t\n", 1);
        System.out.println();
    }

    /* admin main menu */
    //***************Christian har lavet denne metode
    //Her oprettes metoden adminInnerLoop, der viser administratoren muligheden for at se statistik
    void adminInnerLoop()
    {
        WashFiles admin = new WashFiles(); // WashFiles instancer har en metode, der kan indlæse
        // listen over salg. Derfor.

        String adminText = "\t[1] Se statistik\n\n\t[0] Tilbage.\n";
        prepareTerminalAndWrite(adminText);

        //Venter på at få en integer fra brugeren. Hvis int = 0, vil man ryge tilbage til start menuen
        //Hvis int = 1, vises statistik
        try
        {
            int choice = GET.getIntegerFromUser();
            switch(choice)
            {
                case 0:
                    break;
                case 1:
                    admin.loadLedger();
                    Scanner inp = new Scanner(System.in);
                    System.out.println("\nDette var den fulde liste over salg.\n");
                    String temp = inp.nextLine();
                    break;
            }
        } catch(Exception e)
        {
            System.out.println(e);
        }
    }

    /* -- bruger sektion -- */

    /* denne metode til vise brugeren nu-værende saldo, og tilbyde optankning*/
    private void topUp()
    {
        /* while loopet har præcis samme struktur som ovenfor*/
        boolean abort = false;
        while(!abort)
        {
            String menuText = "\t\t[1] Sæt penge ind nu."
                              + "\n\n\t\t[0] Annullér.\n";

            prepareTerminalAndWrite(menuText);
            int choice = GET.getIntegerFromUser();
            switch(choice)
            {
                case 0:
                    abort = true;
                    break;
                case 1:
                    doTopUpAction();
            }
        }
    }

    private void doTopUpAction()
    {
        prepareTerminalAndWrite("\n\t Hvor meget vil du gerne indsætte?\n");

        double amountToAddToAccount = GET.getIntegerFromUser();

        double potentialTotalt = card.amountOnCard + amountToAddToAccount;

        if((potentialTotalt) <= 1000)
        {
            card.amountOnCard = potentialTotalt;
            animateMessageInColor("\n\tDu indsatte: " + amountToAddToAccount
                                  + "\n\tNy konto-saldo: " + potentialTotalt, 3);
        } else
        {
           double potentialPayment = 1000 - card.amountOnCard;

            animateMessageInColor("\t Din konto kan max indeholde"+  TO.green(" 1000 kr,") +
                                  "\n\t\tså du kan maks indsætte "
                                  +TO.yellow(Double.toString(potentialPayment)) +"\n", 4);
        }
    }


    /* i denne metode vælger brugeren bil-type og hvilken type vaske de vil have */
    private void buyWash()
    {
        boolean abort = false;

        while(!abort)
        {
            double carTypeMultiplier = getCarTypeChoiceFromUser();
            WashType washType = getWashChoiceFromUser(washTypes);


            String nameOfCarSize = "NaN";
            if(carTypeMultiplier == 1.0){ nameOfCarSize = "Regulær"; }
            if(carTypeMultiplier == 1.3){ nameOfCarSize = "Stor";}

            /* Vælg biltype
             * Typerne er hard-codede: Regulær og Stor
             * */
            double priceTotal = carTypeMultiplier * washType.price;

            prepareTerminalAndWrite("\t\t Du har valgt" +
                                    "\n\t\t Biltype: " + nameOfCarSize +
                                    "\n\t\t Vasketype: " + washType.washName +
                                    "\n\t\t Pris: " + priceTotal +
                                    "\n\n\t\t [1] Bekræft" +
                                    "\n\n\t\t [0] Annullér");


            int choice = GET.getIntegerFromUser();
            switch(choice)
            {
                case 1:
                    processAcceptedPayment(washType.washName, priceTotal, nameOfCarSize);
                    abort = true;
                    break;
                case 0:
                    abort = true;
                    break;
                default:
                    break;
            }
        }
    }

    /* metoden udfritter brugeren om de vil vaske en stor eller
    * almindelig bil, og returnerer en et tal, der er det
    prisen for vasken skal ganges med.*/
    private double getCarTypeChoiceFromUser()
    {
        boolean abort = false;
        while(!abort)
        {
            prepareTerminalAndWrite("\n\t\tDu kan nu vælge bil-type" +
                                    "\n\n\t\t[1] Regulær\t[2] Stor\n");

            int choice = GET.getIntegerFromUser();
            switch(choice)
            {
                case 1:
                    return 1.0;
                case 2:
                    return 1.3;
                default:
                    break;
            }
        }

        /*koden vil aldrig nå herned, men det er nødvendigt med et return statement.*/
        return -1.0;
    }

    /* method returns an int that represents the choice the user made
     *- if method does not return -1
     * [Note-note: denne metode er netop migreret fra GET
     */
    private WashType getWashChoiceFromUser(ArrayList<WashType> list)
    {
        /* Kalder returnValidListMembers for at få en pæn liste over vaskemuligheder*/
        ArrayList<WashType> tempList = GET.returnValidListMembers(list);

        int choice = -1;
        while(choice != 999)
        {
            prepareTerminalAndWrite(TO.green("\t\tVælg vask") +
                                    TO.blueDark("\n\t\tVælg én af følgende muligheder:\n"));
            /* printer et valg for hver type vask */
            for(int i = 0; i < tempList.size(); i++)
            {
                System.out.println("\t\tValg [" + i + "] " + TO.green(tempList.get(i).washName)
                                   + TO.yellow("\t " + "KR " + tempList.get(i).price));
            }

            /* Her hentes brugerens valg vha getIntegerFromUser */
            choice = GET.getIntegerFromUser();

            /*hvis det valgte tal er indenfor den accepterede rækkevidde...*/
            if(choice < tempList.size() && choice >= 0)
            {
                /**... så printes en besked til brugeren */
                System.out.println("Du har valgt " + tempList.get(choice).washName + " til \tKR" + tempList.get(choice).price);
                /* og den valgte liste returneres */
                return tempList.get(choice);
            }

            /*hvis det valgte tal er udenfor den accepterede rækkevidde*/

            animateMessageInColor("Du valgte ikke et element på listen.\n", 2);
            animateMessageInColor("Du valgte ikke et element på listen.\n", 1);
//            System.out.println("Du valgte ikke et element på listen.\n");
        }
        /* metoden returnerer null, hvis den når her til - men det bør den ikke... prototype! */
        return null;
    }

    /* metoden tjekker om brugeren har penge på kortet
     * og skriver en passende besked til brugeren:
     * der trækkes eller trækkes ikke penge alt efter saldo
     */
    private void processAcceptedPayment(String washType, double price, String carType)
    {
        boolean buyIsPossible = false;

        /*afgør om der er penge nok på kortet*/
        if(card.amountOnCard > price){ buyIsPossible = true;}
        if(card.amountOnCard <= price){ buyIsPossible = false;}
        // skriv
        if(buyIsPossible)
        {
            WashFiles files = new WashFiles();
            files.writeSalesToLedger(washType, price, carType);
        }

        if(buyIsPossible){ card.amountOnCard -= price; } // hvis køb muligt, træk beløb fra saldo på kortet.

        String message = buyIsPossible ? "\t\tDin vask er købt og klar!\n\tGod fornøjelse, god vask og " +
                                         "god dag.\n" :
                "\t\t Der var desværre ikke kredit" +
                "\n\t\ttil at gennemføre dit ønskede køb.\n\n\tDu kan tanke op når som helst :)\n";

        animateMessageInColor(message, 3);
        animateMessageInColor(message, 1);
        animateMessageInColor(message, 1);
    }

    /* Metoden pynter terminalen lidt op, ved at tømme skærmen,
     * printe en 'dekorativ top', printe bruger-konto-saldo
     * og til sidst den supplerede menu-text:
     * Intentionen er at metoden kaldes hver gang en menu-'skærm'
     * skal vises */
    private void prepareTerminalAndWrite(String menuText)
    {
        TO.refreshTerminal();
        /// pynt, lav gerne om
        System.out.println(bord + bord);
        /* printer konto-saldo */
        System.out.print(TO.yellow("\t\t\t\t\t\t\t\t  " + "Saldo: " + card.amountOnCard) +
                         "\n\n" + menuText);
    }

    /*
     *   PS. De her sidste to metoder har dubletter i GET, for at gøre det muligt at animere loginnet,
     *   det kunne refaktoreres, men vi har forbud mod optimering :P
     *  */
    private void messageWithDelay(String message)
    {
        /* sleep() sætter tråden til at sove, og indrammer sleep()
        i et try/catch-construct; i tilfælde af rod på
        tråden... */
        try
        {
            prepareTerminalAndWrite(message);
            Thread.sleep(900);
        } catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private void animateMessageInColor(String message, int repeats)
    {
        if(repeats > 4 || repeats < 0) // afbryd metoden, hvis dårligt tal givet
        {
            System.out.println("Bedre input, tak. ");
            return;
        }

        for(int i = repeats; i > 0; i--)
        {
            switch(i)
            {
                case 4:
                    messageWithDelay(TO.blueDark(message));
                    break;
                case 3:
                    messageWithDelay(TO.green(message));
                    break;
                case 2:
                    messageWithDelay(TO.blue(message));
                    break;
                case 1:
                    messageWithDelay(TO.yellow(message));
                    break;
            }
        }
    }
}