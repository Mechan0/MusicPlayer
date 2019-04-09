package musicPlayer.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import musicPlayer.MusicPlayer;

public class PlaylistScenes {
	public static Scene createPlaylist() {
		VBox layout = new VBox();
		layout.setPadding(MusicPlayer.DEFAULT_PADDING);
		layout.getChildren().add(new Label("Enter Playlist Name:"));
		final TextField input = new TextField();
		input.setPromptText("Name...");
		layout.getChildren().add(input);

		HBox buttons = new HBox();
		Button createButton = new Button("Create");
		Button cancelButton = new Button("Cancel");
		createButton.setDisable(true);
		input.textProperty().addListener((observable, oldValue, newValue) -> {
			createButton.setDisable(newValue.isEmpty());
		});
		createButton.setOnAction(e -> {
			MusicPlayer.getActiveAccount().createPlayList(input.getText());
			MusicPlayer.setScene(MainPageScene.getScene());
		});
		cancelButton.setOnAction(e -> MusicPlayer.setScene(MainPageScene.getScene()));
		buttons.getChildren().add(createButton);
		buttons.getChildren().add(cancelButton);

		layout.getChildren().add(buttons);

		return new Scene(layout);
	}

	public static Scene playlistsScene() {
		throw new IllegalStateException("Not implemented"); // TODO
	}
}
