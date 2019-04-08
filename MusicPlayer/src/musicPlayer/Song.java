package musicPlayer;

/**
 * Represents a single song within the system and stores it's attributes
 * Implements Comparable 
 * 
 *
 */
public class Song implements Comparable<Song> {
	int id, songNumber, year;
	String title, artist, album, genre, length;
	
	public Song(int id, String title, String artist, String album, int songNumber, int year, String genre, String length) {
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.songNumber = songNumber;
		this.year = year;
		this.genre = genre;
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
		return title.compareTo(o.title);
	}
}