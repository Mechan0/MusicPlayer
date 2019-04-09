package musicPlayer;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author James Williamson, Alexander Yaroslavtsev
 *
 */
public class PlayList {
	private final String title;
	private final Queue<Song> songs = new LinkedList<>();

	public PlayList(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void addSong(Song song) {
		songs.add(song);
	}
}
