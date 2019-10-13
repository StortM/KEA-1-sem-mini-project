/* Text Ornaments - TO
 * Klassen tilbyder en række justeringer af din tekst.
 */

class TO {
    /**
     * VARIABLES
     **/
    /* Farver!! :D */
    /* disse strings er ANSI escape codes; kort forklaring: strengene fortolkes som farve-koder.
     * ANSI_RESET afslører hvad der egentlig sker; det er kommandoer til terminalen / 'System.out' ..
     * Referencer nederst i Main */
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BBLUE = "\u001B[94m";

    public static final String ANSI_U = "\u001B[4m"; /* underline */
    public static final String ANSI_ALTFONT1 = "\u001B[11m";
    public static final String ANSI_ITALIC = "\u001B[3m";

    public static final String ANSI_CLS = "\u001b[2J";
    public static final String ANSI_HOME = "\u001b[H";

    /** METHODS **/

    /**
     * Direct Terminal Command Methods
     **/
	/* method sends a string that instructs the terminal to 
	* clear screen and move the cursor position to 0 */
    public static void refreshTerminal() {
        System.out.print(ANSI_CLS + ANSI_HOME);
    }

    /**
     * String augmenting methods
     **/

    /* method returns input string in colour  */
    private static String modifyString(String userInput, String aNSI_CODE) {
        return String.join("", aNSI_CODE, userInput, ANSI_RESET);
    }

    /* method returns input string in colour  */
    public static String blueDark(String input) {
        return modifyString(input, ANSI_BLUE);
    }

    /* method returns input string in colour  */
    public static String black(String input) {
        return modifyString(input, ANSI_BLACK);
    }

    /* method returns input string in colour  */
    public static String purple(String input) {
        return modifyString(input, ANSI_PURPLE);
    }

    /* method returns input string in colour  */
    public static String red(String input) {
        return modifyString(input, ANSI_RED);
    }

    /* method returns input string in colour  */
    public static String green(String input) {
        return modifyString(input, ANSI_GREEN);
    }

    /* method returns input string in colour  */
    public static String yellow(String input) {
        return modifyString(input, ANSI_YELLOW);
    }

    /* method returns input string in colour blue */
    public static String blue(String input) {
        return modifyString(input, ANSI_BBLUE);
    }

    /* method returns input string in colour underlined */
    public static String underline(String input) {
        return modifyString(input, ANSI_U);
    }

    /* method returns input string in italic style, if available */
    public static String italic(String input) {
        return modifyString(input, ANSI_ITALIC);
    }

    
    /* returns a red exclamation mark */
    public static String redExclamation() {
        return ANSI_RED + "!" + ANSI_RESET;
    }

}

/*
 * https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/lang/String.html#join(java.lang.CharSequence,java.lang.CharSequence...)
 * https://www.alexjamesbrown.com/blog/development/c-string-concat-vs-string-join/ - on String.join String.concat
 */
