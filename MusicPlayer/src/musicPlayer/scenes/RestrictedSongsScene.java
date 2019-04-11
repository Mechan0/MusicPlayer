package musicPlayer.scenes;

import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import musicPlayer.MusicPlayer;
import musicPlayer.Song;
import musicPlayer.SongDatabase;
import musicPlayer.SongTableFactory;

import java.util.function.Consumer;

/**
 * @author Alexander Yaroslavtsev
 */
public class RestrictedSongsScene {
	public static Scene getScene() {
		VBox layout = new VBox(10);
		layout.setPadding(MusicPlayer.DEFAULT_PADDING);

		TableView<Song> songTable = SongTableFactory.createSongTable(SongDatabase.getSongs(true));
		songTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		//Callback<TableView<Song>, TableRow<Song>> rowFactory = songTable.getRowFactory();
		songTable.setRowFactory(param -> {
			//TableRow tableRow = rowFactory.call(param);
			return new TableRow<Song>() {
				@Override
				protected void updateItem(Song song, boolean empty) {
					super.updateItem(song, empty);
					if (song != null && song.isRestricted()) {
						setStyle("-fx-background-color: "+(isSelected() ? "red" : "#ffd6d6")+";");
					}
				}
			};
		});
		layout.getChildren().add(songTable);

		HBox buttons = new HBox(10);

		Button restrictButton = new Button("Restrict Selected");
		restrictButton.setDisable(true);

		Consumer<Boolean> updateTask = restrict -> {
			songTable.getSelectionModel().getSelectedItems().forEach(song -> song.setRestricted(restrict));
			songTable.getSelectionModel().clearSelection();
			MusicPlayer.setScene(getScene());
		};

		restrictButton.setOnAction(e -> updateTask.accept(true));

		Button unrestrictButton = new Button("Unrestrict Selected");
		unrestrictButton.setDisable(true);
		unrestrictButton.setOnAction(e -> updateTask.accept(false));

		Button backButton = new Button("Back to Songs");
		backButton.setOnAction(e -> {
			MusicPlayer.getActiveAccount().removeRestricted();
			MusicPlayer.setScene(MainPageScene.getScene());
		});

		songTable.getSelectionModel().getSelectedCells().addListener((ListChangeListener<? super TablePosition>) change -> {
			change.next();
			boolean selected = songTable.getSelectionModel().getSelectedCells().isEmpty();
			restrictButton.setDisable(selected);
			unrestrictButton.setDisable(selected);
		});

		buttons.getChildren().add(restrictButton);
		buttons.getChildren().add(unrestrictButton);
		buttons.getChildren().add(backButton);
		buttons.getChildren().add(new Label("Restricted songs are highlighted red."));

		layout.getChildren().add(buttons);

		return new Scene(layout);
	}
}
