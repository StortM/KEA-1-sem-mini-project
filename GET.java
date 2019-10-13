import java.util.Scanner;
import java.util.ArrayList;

class GET /* GET klassen indeholder statiske metoder til at hente eller pynte data */
{
    /* Method returns an integer */
    public static int getIntegerFromUser()
    {
        Scanner scanner;
        int enteredInt = -1; /* user entered integer, initialized to -1 to */
        boolean integerDetected = false; /* boolean registers if an int was entered */

        /* loop runs while integer is not detected */
        while(!integerDetected)
        {

            try
            {
                System.out.print("\n|| Indtast et tal:\t");

                /* take user input */
                scanner = new Scanner(System.in);

                /* read next int from scanner object */
                /* AKA attempt to get entered int */
                enteredInt = scanner.nextInt();

                /* code only reaches this point if what was entered was an int */
                /* adjustment makes the while loop end */
                integerDetected = true;

            } catch(Exception e)
            {
                /* catch exception */
                System.out.println("||\t" + e + "\n||\tDu indtastede noget, der ikke er et heltal.");
                break;
            }
        }
        return enteredInt;
    }


    /* method returns an int that represents the choice the user made
    *- if method does not return -1
    * [Note-note: jeg tænker denne metode hører til i UserInteraction
    */


    /* denne metode tager imod en liste a WashTypes
    *  og sorterer de WashType-objeker fra, hvor prisen er 0;
    *  til sidst returneres alle valide kandidater.
    * */
    public static ArrayList<WashType> returnValidListMembers(ArrayList<WashType> list)
    {
        ArrayList<WashType> tempList = new ArrayList<WashType>();

        for(WashType instance : list)
        {
            if((instance.price > 0))
            {
                tempList.add(instance);
            }
        }

        return tempList; /* an empty list might be returned */
    }

    static void prepareTerminalAndWrite(String menuText)
    {
				String bord = "*--**--*-*-*-**-*-**--*-*--**--*-*-*-**-*-*";
        TO.refreshTerminal();
        /// pynt, lav gerne om
        System.out.println(bord + bord);
        /* printer konto-saldo */
        System.out.print(TO.yellow("\t\t\t\t\t\t\t\t  " +
                         "\n\n" + menuText));
    }

		static void messageWithDelay(String message)
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

    static void animateMessageInColor(String message, int repeats)
    {
        if(repeats > 4 || repeats < 0) // afbryd metoden, hvis dårligt tal givet
        {
            System.out.println("Bedre input, tak. ");
            return;
        }

        for(int i = repeats; i >= 0; i--)
        {
            switch(i)
            {
                case 4:
                    messageWithDelay(TO.red(message));
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