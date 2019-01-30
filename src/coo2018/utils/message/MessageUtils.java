package coo2018.utils.message;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * @author Andréa Christophe
 *
 */
public class MessageUtils {

	/**
	 * Fait apparaître une pop-up dans la page appelante 
	 * 
	 * @param alertType
	 * @param title
	 * @param description
	 */
	public static void messageAlert(AlertType alertType, String title, String description) {

		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(description);
		alert.showAndWait();
	}
}
