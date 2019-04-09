package musicPlayer;

import com.sun.istack.internal.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * Represents a user account, storing their PlayLists and other information
 * 
 * @author James Williamson, Alexander Yaroslavtsev
 */
public class Account {
	private final HashMap<String, Playlist> playLists = new HashMap<>();
	private final boolean admin;

	public Account(boolean admin) {
		this.admin = admin;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void createPlayList(String title) {
		playLists.put(title, new Playlist(title));
	}

	@Nullable
	public Playlist getPlaylist(String title) {
		return playLists.get(title);
	}

	public Collection<Playlist> getPlaylists() {
		return Collections.unmodifiableCollection(playLists.values());
	}

	public boolean removePlaylist(String title) {
		return playLists.remove(title) != null;
	}
}
