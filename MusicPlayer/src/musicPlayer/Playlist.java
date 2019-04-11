package musicPlayer;

/**
 * @author James Williamson, Alexander Yaroslavtsev
 *
 */
public class Playlist {
	private final String title;
	private final SongQueue songs = new SongQueue();

	public Playlist(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void addSong(Song song) {
		// FIXME allow duplicates (don't forget to change how songs are removed in playlist screen)
		if (songs.contains(song))
			return;
		songs.add(song);
	}

	public SongQueue getSongs() {
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
