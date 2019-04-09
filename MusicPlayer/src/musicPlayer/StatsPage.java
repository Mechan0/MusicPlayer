package musicPlayer;

import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
/*
 * The pop-up creation window for the statistics of a 
 * certain song when the "Statistics" button is clicked
 * 
 *  @author Branden Sangkhavichith
 */
public class StatsPage {
	
	public static void display(String message) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Statistics");
		window.setMinWidth(720);
		window.setMinHeight(480);
		
		Label label = new Label();
		label.setText(message);
		Button closeButton = new Button("Close window");
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label);
	    layout.setAlignment(Pos.TOP_LEFT);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
}
