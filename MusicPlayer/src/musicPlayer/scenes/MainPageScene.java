package musicPlayer.scenes;

import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import musicPlayer.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author James Williamson, Alexander Yaroslavtsev
 */
public class MainPageScene {
	public static Scene getScene() {
		//TODO: Flesh out main panel, add buttons to toolbar
		BorderPane mainLayout = new BorderPane();
		//VBox centerContent = new VBox();
		TableView<Song> songTable = SongTableFactory.createSongTable(SongDatabase.getSongs(MusicPlayer.getActiveAccount().isAdmin()));
		songTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // allow multiple selection
		//centerContent.getChildren().add(songTable);
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(createFilterMenu(songTable));
		mainLayout.setTop(menuBar);

		HBox songListButtons = new HBox(10);

		MenuButton addToPlaylistButton = new MenuButton("Add to Playlist");
		addToPlaylistButton.setDisable(true);
		for (Playlist playlist : MusicPlayer.getActiveAccount().getPlaylists()) {
			MenuItem playlistButton = new MenuItem(playlist.getTitle());
			playlistButton.setOnAction(e -> {
				songTable.getSelectionModel().getSelectedItems().forEach(playlist::addSong);
				songTable.getSelectionModel().clearSelection();
			});
			addToPlaylistButton.getItems().add(playlistButton);
		}
		songListButtons.getChildren().add(addToPlaylistButton);

		Button addToQueueButton = new Button("Add to Queue");
		addToQueueButton.setDisable(true);
		addToQueueButton.setOnAction(e -> {
			MusicPlayer.getActiveAccount().getSongQueue().addAll(songTable.getSelectionModel().getSelectedItems());
			MusicPlayer.setScene(MainPageScene.getScene());
		});
		songListButtons.getChildren().add(addToQueueButton);

		songTable.getSelectionModel().getSelectedCells().addListener((ListChangeListener<? super TablePosition>) change -> {
			change.next();
			addToPlaylistButton.setDisable(change.getTo() == 0 || MusicPlayer.getActiveAccount().getPlaylists().isEmpty());
			addToQueueButton.setDisable(change.getTo() == 0);
		});

		HBox contentBox = new HBox(10);
		contentBox.setPadding(MusicPlayer.DEFAULT_PADDING);
		VBox box1 = new VBox(10);
		box1.getChildren().add(MusicPlayer.createTitle("Songs"));
		box1.getChildren().add(songTable);
		box1.getChildren().add(songListButtons);
		VBox.setVgrow(songTable, Priority.ALWAYS);
		contentBox.getChildren().add(box1);
		HBox.setHgrow(box1, Priority.ALWAYS);

		VBox box2 = new VBox(10);
		box2.getChildren().add(MusicPlayer.createTitle("Queue"));

		TableView<Song> queueTable = SongTableFactory.createSongTable(MusicPlayer.getActiveAccount().getSongQueue());
		queueTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		queueTable.getColumns().removeIf(column -> {
			column.setSortable(false);
			return !column.getText().equals("Title") &&
					!column.getText().equals("Artist") &&
					!column.getText().equals("Duration");
		});

		box2.getChildren().add(queueTable);

		Button playQueueButton = new Button("Play Queue");
		playQueueButton.setDisable(!MusicPlayer.getActiveAccount().getSongQueue().isDurationBetween1And3Hours());
		Button removeSelectedButton = new Button("Remove Selected");
		removeSelectedButton.setDisable(queueTable.getSelectionModel().getSelectedCells().isEmpty());
		queueTable.getSelectionModel().getSelectedCells().addListener((ListChangeListener<? super TablePosition>) e -> {
			e.next();
			removeSelectedButton.setDisable(queueTable.getSelectionModel().getSelectedCells().isEmpty());
		});
		removeSelectedButton.setOnAction(e -> {
			List<Integer> indices = new ArrayList<>(queueTable.getSelectionModel().getSelectedIndices());
			indices.sort((a, b) -> b-a); // sort descending
			for (int i: indices) {
				MusicPlayer.getActiveAccount().getSongQueue().remove(i);
			}
			queueTable.getSelectionModel().clearSelection();
			MusicPlayer.setScene(MainPageScene.getScene());
		});
		HBox queueButtons = new HBox(10);
		queueButtons.getChildren().add(playQueueButton);
		queueButtons.getChildren().add(removeSelectedButton);

		box2.getChildren().add(queueButtons);
		contentBox.getChildren().add(box2);
		HBox.setHgrow(box2, Priority.ALWAYS);

		//HBox.setHgrow(button2, Priority.ALWAYS);

		mainLayout.setCenter(contentBox);
		mainLayout.setBottom(getStatusBar());
		return new Scene(mainLayout);
	}

	private static HBox getStatusBar() {
		HBox statusbar = new HBox();
		statusbar.setSpacing(10);
		statusbar.setPadding(MusicPlayer.DEFAULT_PADDING);

		Button playlistsButton = new Button("Playlists");
		playlistsButton.setOnAction(e -> MusicPlayer.setScene(PlaylistScenes.playlistsScene()));
		statusbar.getChildren().add(playlistsButton);

		Button createPlaylistButton = new Button("Create New Playlist");
		createPlaylistButton.setOnAction(e -> {
			PlaylistScenes.createPlaylistDialog();
			MusicPlayer.setScene(getScene());
		});
		statusbar.getChildren().add(createPlaylistButton);

		if (MusicPlayer.getActiveAccount().isAdmin()) {
			
			
			Button button = new Button("Edit Restricted Songs");
			button.setStyle("-fx-background-color: #ffd6d6;");
			button.setOnAction(e -> MusicPlayer.setScene(RestrictedSongsScene.getScene()));
			statusbar.getChildren().add(button);
			
			//Here
			Button userPermsButton = new Button("User Permissions");
			userPermsButton.setOnAction(e -> MusicPlayer.setScene(UserManagementScene.getScene()));
			statusbar.getChildren().add(userPermsButton);
			
			
			
		}

		Button switchAccountButton = new Button("Switch Account");
		switchAccountButton.setOnAction(e -> MusicPlayer.setScene(AccountScenes.selectAccount()));
		statusbar.getChildren().add(switchAccountButton);

		return statusbar;
	}

	private static Menu createFilterMenu(TableView<Song> songTable) {
		// TODO: Refactor to more elegant solution
		Menu filterMenu = new Menu("Filter Songs");
		// artist filter
		Menu artistFilter = createSubFilterMenu("Artist", SongDatabase.getArtists());
		for (MenuItem item : artistFilter.getItems()) {
			item.setOnAction(e -> {
				songTable.getItems().removeAll(songTable.getItems());
				songTable.getItems().addAll(SongDatabase.getSongsArtist(item.getText()));
			});
		}
		// albumFilter
		Menu albumFilter = createSubFilterMenu("Album", SongDatabase.getAlbums());
		for (MenuItem item : albumFilter.getItems()) {
			item.setOnAction(e -> {
				songTable.getItems().removeAll(songTable.getItems());
				songTable.getItems().addAll(SongDatabase.getSongsAlbum(item.getText()));
			});
		}
		// yearFilter
		Menu yearFilter = createSubFilterMenuInt("Year", SongDatabase.getYears());
		for (MenuItem item : yearFilter.getItems()) {
			item.setOnAction(e -> {
				songTable.getItems().removeAll(songTable.getItems());
				songTable.getItems().addAll(SongDatabase.getSongsYear(Integer.parseInt(item.getText())));
			});
		}
		// genreFilter
		Menu genreFilter = createSubFilterMenu("Genre", SongDatabase.getGenres());
		for (MenuItem item : genreFilter.getItems()) {
			item.setOnAction(e -> {
				songTable.getItems().removeAll(songTable.getItems());
				songTable.getItems().addAll(SongDatabase.getSongsGenre(item.getText()));
			});
		}
		MenuItem noFilter = new MenuItem("All Songs");
		noFilter.setOnAction(e -> {
			songTable.getItems().removeAll(songTable.getItems());
			songTable.getItems().addAll(SongDatabase.getSongs());
		});
		filterMenu.getItems().addAll(artistFilter, albumFilter, yearFilter, genreFilter, noFilter);
		return filterMenu;
	}

	private static Menu createSubFilterMenu(String title, ArrayList<String> items) {
		Menu subFilter = new Menu(title);
		for (String subFilterItem : items) {
			subFilter.getItems().add(new MenuItem(subFilterItem));
		}
		return subFilter;
	}

	private static Menu createSubFilterMenuInt(String title, ArrayList<Integer> items) {
		Menu subFilter = new Menu(title);
		for (Integer subFilterItem : items) {
			subFilter.getItems().add(new MenuItem(Integer.toString(subFilterItem)));
		}
		return subFilter;
	}
}
