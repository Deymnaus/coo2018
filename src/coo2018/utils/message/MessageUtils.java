package coo2018.utils.message;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MessageUtils {

	public static void messageAlert(AlertType alertType, String title, String description) {

		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(description);
		alert.showAndWait();
	}
}
