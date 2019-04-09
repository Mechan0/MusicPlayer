package musicPlayer;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author James Williamson, Alexander Yaroslavtsev
 *
 */
public class Playlist {
	private final String title;
	private final Queue<Song> songs = new LinkedList<>();

	public Playlist(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void addSong(Song song) {
		if (songs.contains(song))
			return;
		songs.add(song);
	}

	public Queue<Song> getSongs() {
		return songs;
	}

	public void removeSong(Song song) {
		songs.remove(song);
	}

	@Override
	public String toString() {
		return title+" ("+songs.size()+" song"+(songs.size() != 1 ? "s" : "")+")";
	}
}
