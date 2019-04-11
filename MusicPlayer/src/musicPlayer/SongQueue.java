package musicPlayer;

import java.util.LinkedList;

/**
 * @author Alexander Yaroslavtsev
 */
public class SongQueue extends LinkedList<Song> {
	public boolean isDurationBetween1And3Hours() {
		int duration = 0;
		for (Song song: this) {
			duration += song.getDuration();
		}
		return duration >= 3600 && duration < 3600*3;
	}
}
