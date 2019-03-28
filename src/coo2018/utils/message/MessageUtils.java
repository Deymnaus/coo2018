package coo2018.utils.message;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

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
	public static Optional<ButtonType> messageAlert(AlertType alertType, String title, String description) {

		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(description);

		Optional<ButtonType> result = alert.showAndWait();
		return result;
	}
}
