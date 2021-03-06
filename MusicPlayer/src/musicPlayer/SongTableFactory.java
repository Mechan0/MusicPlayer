package musicPlayer;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Collection;

/**
 * Generates TableView objects containing the given Collection of Song objects
 * @author James Williamson, Alexander Yaroslavtsev
 *
 */
public class SongTableFactory {
	@SuppressWarnings("unchecked")
	public static TableView<Song> createSongTable(Collection<Song> songs) {
	
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

		// duration
		TableColumn<Song, String> duration = new TableColumn<>("Duration");
		duration.setSortable(false);
		duration.setEditable(false);
		duration.setCellValueFactory(param -> new ObservableValue<String>() {
			@Override
			public void addListener(InvalidationListener listener) {}

			@Override
			public void removeListener(InvalidationListener listener) {}

			@Override
			public void addListener(ChangeListener<? super String> listener) {}

			@Override
			public void removeListener(ChangeListener<? super String> listener) {}

			@Override
			public String getValue() {
				return param.getValue().getFormattedDuration();
			}
		});

		//Stats
		TableColumn<Song, String> stats = new TableColumn<Song, String>("Statistics");
		stats.setCellValueFactory(new PropertyValueFactory<Song, String>("button"));
		stats.setSortable(false);
		stats.setEditable(false);
		ObservableList<Song> data = FXCollections.observableArrayList();
		data.addAll(songs);
		songTable.setItems(data);
		songTable.getColumns().addAll(title, artist, album, songNumber, genre, year, duration, stats);
		return songTable;
	}
}
