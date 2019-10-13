import java.util.ArrayList;

/** Klassen tager sig af at håndtere brugere: første udgaver skriver ingen filer, men bruger en intern
run-time-begrænset liste; det kan laves om */
public class Accounts
{
    ArrayList<WashCard> userList;

    Accounts(ArrayList<WashCard> USERLIST)
    {
        userList = USERLIST;
    }

    /* Vi skal bruge createUser. opgave!
    * metoden tager et kortID (int) og  */
    void createUser(int kortID, double beløbIndsat)
    {
        userList.add(new WashCard(kortID,beløbIndsat));
    }

    /* metoden læser listen over users igennem og genkender brugeren eller ej
    *
    * */
    WashCard verifyUser(int kortID){
            /*går igennem userList og sender en brugbar bruger tilbage, hvis den findes */
            for(WashCard card: userList){
                if(card.cardID == kortID){return card;}
            }

            /* koden når kun herned hvis ingen bruger findes, */
            return null;
    }
}
