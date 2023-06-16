
package camelinaction;

// We don't start the app directly from the main method of the GUI class.
// Instead, we use an extra starter class GUIStarter that does not inherit from javafx.application.Application.
// Otherwise, there might be an error: "JavaFX runtime components are missing, and are required to run this application."
public class GUIStarter {
	
	// For more information, see: https://stackoverflow.com/questions/56894627/how-to-fix-error-javafx-runtime-components-are-missing-and-are-required-to-ru
	public static void main(final String[] args) throws Exception {
		GUI.main(args);
	}
}
