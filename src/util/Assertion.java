package util;
/**
 * @author Francesco
 * creato il 18/mar/2016 09:19:29
 */
public class Assertion {

	public static void check(boolean b){
		if (!b) {
		    System.out.println("Asserzione violata.");
		    new Throwable().printStackTrace();
		    System.exit(1);
		}
	}
	
}
