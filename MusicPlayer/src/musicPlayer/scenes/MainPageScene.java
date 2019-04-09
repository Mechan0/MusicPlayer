package musicPlayer.scenes;

import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import musicPlayer.*;

import java.util.ArrayList;

/**
 * @author James Williamson, Alexander Yaroslavtsev
 */
public class MainPageScene {
    public static Scene getScene() {
        //TODO: Flesh out main panel, add buttons to toolbar
        BorderPane mainLayout = new BorderPane();
        //VBox centerContent = new VBox();
        TableView<Song> songTable = SongTableFactory.createSongTable(SongDatabase.getSongs());
        //centerContent.getChildren().add(songTable);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(createFilterMenu(songTable));
        HBox statusbar = new HBox();
        statusbar.setSpacing(10);
        statusbar.setPadding(MusicPlayer.DEFAULT_PADDING);

        Button playlistsButton = new Button("Playlists");
        playlistsButton.setOnAction(e -> MusicPlayer.setScene(PlaylistScenes.playlistsScene()));
        statusbar.getChildren().add(playlistsButton);

        Button createPlaylistButton = new Button("Create New Playlist");
        createPlaylistButton.setOnAction(e -> MusicPlayer.setScene(PlaylistScenes.createPlaylist()));
        statusbar.getChildren().add(createPlaylistButton);

        MenuButton addToPlaylistButton = new MenuButton("Add to Playlist");
        addToPlaylistButton.setDisable(true);
        for (Playlist playlist: MusicPlayer.getActiveAccount().getPlaylists()) {
            MenuItem playlistButton = new MenuItem(playlist.getTitle());
            playlistButton.setOnAction(e -> {
                songTable.getSelectionModel().getSelectedItems().forEach(playlist::addSong);
            });
            addToPlaylistButton.getItems().add(playlistButton);
        }
        statusbar.getChildren().add(addToPlaylistButton);

        songTable.getSelectionModel().getSelectedCells().addListener((ListChangeListener<? super TablePosition>) change -> {
            change.next();
            addToPlaylistButton.setDisable(change.getTo() == 0);
        });


        mainLayout.setTop(menuBar);
        mainLayout.setCenter(songTable);
        mainLayout.setBottom(statusbar);
        return new Scene(mainLayout);
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
        for(String subFilterItem : items) {
            subFilter.getItems().add(new MenuItem(subFilterItem));
        }
        return subFilter;
    }
    private static Menu createSubFilterMenuInt(String title, ArrayList<Integer> items) {
        Menu subFilter = new Menu(title);
        for(Integer subFilterItem : items) {
            subFilter.getItems().add(new MenuItem(Integer.toString(subFilterItem)));
        }
        return subFilter;
    }
}
