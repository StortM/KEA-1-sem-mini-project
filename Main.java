import java.io.IOException;
import java.util.ArrayList;

class Main
{
    public static void main(String[] args) throws Exception    
    {
		/* Shiner terminal op! */
        TO.refreshTerminal();

		/* Indlæser nødvendige filer */
        WashFiles files = new WashFiles(); /* klassen håndterer indlæsning/aflæsning af data */
        ArrayList<WashType> washTypeList = files.loadWashTypes(); /* indlæser WashTypes.txt */

		Login login = new Login(files.loadUserList(),washTypeList);

		login.loginLoop();

		System.out.println("Programmet afsluttes nu. Bup.");
    }
}
    /* Ikke længere brugt, fordi vi har en funktionel indlæsning af en fil med en brugerliste */
	/* these are just to have some users in the system
	* læg flere til!!
	* */
//	private static ArrayList<WashCard> proxyWashCardList()
//	{
//		cards.add(new WashCard(1000, 201));
//		cards.add(new WashCard(1200, 21));
//		cards.add(new WashCard(1230, 951));
//		cards.add(new WashCard(1234, 321));
//		cards.add(new WashCard(4444, 0)); /* breaker of chains */
//		cards.add(new WashCard(9999, 0)); /* one admin, there is*/
//
//		return cards;
//	}
