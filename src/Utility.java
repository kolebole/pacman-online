/* File: Utility.java
 * Start: 2010/06/14
 * Modification: 2010/06/XX
 * Description: class with utility functions
 */

class Utility {
	/* Print an exception */ 
	public static void error(Exception e) {
		System.out.println(e.toString());
	}
	
	/* Print an error message */
	public static void error(String error) {
		System.out.println("Error: " + error);
	}
}
