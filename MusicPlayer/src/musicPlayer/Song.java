package musicPlayer;



import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
/**
 * Represents a single song within the system and stores it's attributes
 * Implements Comparable 
 * 
 * @author James Williamson, Alexander Yaroslavtsev
 */
public class Song implements Comparable<Song> {
	private final int id;
	private SimpleIntegerProperty songNumber, year;
	private SimpleStringProperty title, artist, album, genre;
	private final int durationSeconds;
	private Button button;
	private boolean restricted;
	
	public Song(int id, String title, String artist, String album, int songNumber, int year, String genre, int durationSeconds) {
		this.id = id;
		this.title = new SimpleStringProperty(title);
		this.artist = new SimpleStringProperty(artist);
		this.album = new SimpleStringProperty(album);
		this.songNumber = new SimpleIntegerProperty(songNumber);
		this.year = new SimpleIntegerProperty(year);
		this.genre = new SimpleStringProperty(genre);
		this.durationSeconds = durationSeconds;
		this.button = new Button("Statistics");
		button.setOnAction(e -> StatsPage.display(getStats()));
	}
	public String getStats() {
		return this.getTitle()+"\t\t"+this.getArtist()+"\t\t"+this.getAlbum()+"\t\t"
			   +this.getSongNumber()+"\t\t"+this.getGenre()+"\t\t"+this.getYear();
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
	public int getDuration() {
		return durationSeconds;
	}
	public String getFormattedDuration() {
		if (durationSeconds >= 60*60) { // 1 hour
			return String.format(
					"%d:%02d:%02d",
					durationSeconds / 3600,
					(durationSeconds % 3600) / 60,
					durationSeconds % 60);
		} else {
			return String.format(
					"%d:%02d",
					durationSeconds / 60,
					durationSeconds % 60);
		}
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
	//Branden
	public void setButton(Button button) {
		this.button = button;
	}
	public Button getButton() {
		return button;
	}

	public boolean isRestricted() {
		return restricted;
	}

	public void setRestricted(boolean restricted) {
		this.restricted = restricted;
	}
}