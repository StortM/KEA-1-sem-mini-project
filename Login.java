import java.util.ArrayList;


/* Login klassen kører kører faktisk showet; mellem logins går programmet i en stor bue tilbage
 * til loginLoopet */
public class Login
{

    Accounts userCardAccounts;
    private ArrayList<WashType> washTypes;

    Login(ArrayList<WashCard> USERLIST, ArrayList<WashType> WASHTYPES)
    {
        userCardAccounts = new Accounts(USERLIST);
        washTypes = WASHTYPES;
    }

    /* login loop */
    /* login-loopet er rent for show
     * bruger kan indtaste et mock-up kort-id
     * admin kan indtaste 9999 for admin muligheder */
    public void loginLoop()
    {

        int kortID = -1;

        String awesomeLogo = ("\t\t  /$$$$$$                                                        /$$      \n") +
                             ("\t\t /$$__  $$                                                      | $$      \n") +
                             ("\t\t| $$  \\__/  /$$$$$$   /$$$$$$  /$$  /$$  /$$  /$$$$$$   /$$$$$$$| $$$$$$$ \n") +
                             ("\t\t| $$       |____  $$ /$$__  $$| $$ | $$ | $$ |____  $$ /$$_____/| $$__  $$\n") +
                             ("\t\t| $$        /$$$$$$$| $$  \\__/| $$ | $$ | $$  /$$$$$$$|  $$$$$$ | $$  \\ $$\n") +
                             ("\t\t| $$    $$ /$$__  $$| $$      | $$ | $$ | $$ /$$__  $$ \\____  $$| $$  | $$\n") +
                             ("\t\t|  $$$$$$/|  $$$$$$$| $$      |  $$$$$/$$$$/|  $$$$$$$ /$$$$$$$/| $$  | $$\n") +
                             ("\t\t \\______/  \\_______/|__/       \\_____/\\___/  \\_______/|_______/ |__/  |__/\n") +
                             ("\n\t\t\t\t\t\t\t\t\t    Velkommen" + "\n\t\t\t\t\t\t\t\t      Indlæs kort ||\n" +
                              "                    \\/----------------------------/" + " \n");

        while(kortID != 4444)
        {
            System.out.println("\n\n");

            GET.animateMessageInColor(awesomeLogo, 4);

            kortID = GET.getIntegerFromUser(); // denne linie simulerer at et brugerkort swipes og aflæses

            /* verifyUser sender null tilbage, hvis ingen bruger ved det id-nummer fandtes */
            WashCard card = userCardAccounts.verifyUser(kortID);

            /* afbryd denne iteration af while loopet; ingen valg gives ingen bruger  */
            if(card == null){ continue; }
            /* hver  user interaktion er knyttet sammen med det WashCard brugeren har */
            UserInteraction ui = new UserInteraction(card, washTypes);

            switch(card.cardID) // alt efter kort id vises menu; admin (9999) er den eneste der kan se
            // admin menuen.
            {
                case 9999:
                    ui.adminLoop();
                    break;
                case 4444:
                    break; // effekten af 4444 er at loopet sluttes næste iteration.
                default:
                    ui.userLoop(); // i denne demo gælder alle andre tal som et brugernavn
                    // ... det skal jo ændres, sådan at kortID tjekkes ...                   
            }
        }
        System.out.println("Systemet lukker ned. Bip.");
    }
}
