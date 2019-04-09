package musicPlayer.scenes;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import musicPlayer.MusicPlayer;
import musicPlayer.Playlist;

public class PlaylistScenes {
	public static void createPlaylistDialog() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Create new Playlist");

		VBox layout = new VBox();
		layout.setSpacing(10);
		layout.setPadding(MusicPlayer.DEFAULT_PADDING);
		layout.getChildren().add(MusicPlayer.createTitle("Create new Playlist"));
		final TextField input = new TextField();
		input.setPromptText("Name...");
		layout.getChildren().add(input);

		HBox buttons = new HBox();
		buttons.setSpacing(10);
		Button createButton = new Button("Create");
		Button cancelButton = new Button("Cancel");
		createButton.setDisable(true);
		input.textProperty().addListener((observable, oldValue, newValue) -> {
			createButton.setDisable(newValue.isEmpty());
		});
		createButton.setOnAction(e -> {
			MusicPlayer.getActiveAccount().createPlayList(input.getText());
			window.close();
			//MusicPlayer.setScene(returnScene.get());
		});
		cancelButton.setOnAction(e -> window.close()/*MusicPlayer.setScene(returnScene.get())*/);
		buttons.getChildren().add(createButton);
		buttons.getChildren().add(cancelButton);

		layout.getChildren().add(buttons);

		window.setScene(new Scene(layout));
		window.showAndWait();
	}

	public static Scene playlistsScene() {
		GridPane gridPane = new GridPane();
		gridPane.setPadding(MusicPlayer.DEFAULT_PADDING);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.add(MusicPlayer.createTitle("Playlists"), 0, 0, 3, 1);

		ListView<String> listView = new ListView<>();
		ObservableList<String> playlists = FXCollections.observableArrayList();
		MusicPlayer.getActiveAccount().getPlaylists().forEach(playlist -> playlists.add(playlist.getTitle()));
		listView.setItems(playlists);

		gridPane.add(listView, 0, 1, 2, 1);

		VBox buttonBox = new VBox(10);

		Button viewButton = new Button("View Playlist");
		viewButton.setDisable(true);
		buttonBox.getChildren().add(viewButton);
		viewButton.setOnAction(e -> {
			MusicPlayer.setScene(playlistScene(MusicPlayer.getActiveAccount().getPlaylist(listView.getSelectionModel().getSelectedItem())));
		});

		Button deleteButton = new Button("Delete Playlist");
		deleteButton.setDisable(true);
		buttonBox.getChildren().add(deleteButton);
		deleteButton.setOnAction(e -> {
			MusicPlayer.getActiveAccount().removePlaylist(listView.getSelectionModel().getSelectedItem());
			MusicPlayer.setScene(playlistsScene());
		});

		Button createButton = new Button("Create Playlist");
		createButton.setOnAction(e -> {
			createPlaylistDialog();
			MusicPlayer.setScene(playlistsScene());
		});
		buttonBox.getChildren().add(createButton);

		Button backButton = new Button("Back to Songs");
		backButton.setOnAction(e -> MusicPlayer.setScene(MainPageScene.getScene()));
		buttonBox.getChildren().add(backButton);

		listView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<? super String>) change -> {
			change.next();
			viewButton.setDisable(change.getTo() == 0);
			deleteButton.setDisable(change.getTo() == 0);
		});

		gridPane.add(buttonBox, 2, 1);

		return new Scene(gridPane);
	}

	public static Scene playlistScene(Playlist playlist) {
		throw new IllegalStateException(); //TODO
	}
}
