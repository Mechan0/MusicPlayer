package musicPlayer;

import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Generates TableView objects with the given collection
 * @author James Williamson
 *
 */
public class SongTableFactory {
	@SuppressWarnings("unchecked")
	public TableView<Song> createSongTable(Collection<Song> songs) {
		TableView<Song> songTable = new TableView<Song>();
		// title
		TableColumn<Song, String> title = new TableColumn<Song, String>("Title");
		title.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
		// artist
		TableColumn<Song, String> artist = new TableColumn<Song, String>("Artist");
		artist.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));
		// album
		TableColumn<Song, String> album = new TableColumn<Song, String>("Album");
		album.setCellValueFactory(new PropertyValueFactory<Song, String>("album"));
		// songNumber
		TableColumn<Song, Integer> songNumber = new TableColumn<Song, Integer>("#");
		songNumber.setCellValueFactory(new PropertyValueFactory<Song, Integer>("songNumber"));
		// year
		TableColumn<Song, Integer> year = new TableColumn<Song, Integer>("Year");
		year.setCellValueFactory(new PropertyValueFactory<Song, Integer>("year"));
		// genre
		TableColumn<Song, String> genre = new TableColumn<Song, String>("Genre");
		genre.setCellValueFactory(new PropertyValueFactory<Song, String>("genre"));
		ObservableList<Song> data = FXCollections.observableArrayList();
		data.addAll(songs);
		songTable.setItems(data);
		songTable.getColumns().addAll(title, artist, album, songNumber, genre, year);
		return songTable;
	}
}
