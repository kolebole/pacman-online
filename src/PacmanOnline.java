import javax.swing.SwingUtilities;

/* File: PacmanOnline.java
 * Start: 2010/06/07
 * Modification: 2010/06/XX
 * Description: Sample code for coding style.
 */
public class PacmanOnline {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello, Pacman-Online!");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				PacFrame inst = new PacFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

}
