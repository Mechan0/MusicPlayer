package musicPlayer;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a single song within the system and stores it's attributes
 * Implements Comparable 
 * 
 * @author James Williamson
 */
public class Song implements Comparable<Song> {
	private final int id;
	private SimpleIntegerProperty songNumber, year;
	private SimpleStringProperty title, artist, album, genre, length;
	
	public Song(int id, String title, String artist, String album, int songNumber, int year, String genre, String length) {
		this.id = id;
		this.title = new SimpleStringProperty(title);
		this.artist = new SimpleStringProperty(artist);
		this.album = new SimpleStringProperty(album);
		this.songNumber = new SimpleIntegerProperty(songNumber);
		this.year = new SimpleIntegerProperty(year);
		this.genre = new SimpleStringProperty(genre);
	}
	public int getId() {
		return id;
	}
	public int getSongNumber() {
		return songNumber.get();
	}
	public int getYear() {
		return year.get();
	}
	public String getTitle() {
		return title.get();
	}
	public String getArtist() {
		return artist.get();
	}
	public String getAlbum() {
		return album.get();
	}
	public String getGenre() {
		return genre.get();
	}
	public String getLength() {
		return length.get();
	}
	@Override
	public int hashCode() {
		return this.id;
	}
	@Override
	public boolean equals(Object other) {
		return this.hashCode() == other.hashCode();
	}
	@Override
	public String toString() {
		return String.format("%s by: %s", title, artist);
	}
	@Override
	public int compareTo(Song o) {
		return getTitle().compareTo(o.getTitle());
	}
}