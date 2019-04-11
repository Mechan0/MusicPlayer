package musicPlayer;

import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Represents a user account, storing their PlayLists and other information
 * 
 * @author Tony Vu, James Williamson, Alexander Yaroslavtsev
 */
public class Account {
	private final HashMap<String, Playlist> playLists = new LinkedHashMap<>();
	private boolean admin;
	private final SongQueue songQueue = new SongQueue();
	private ArrayList<Song> recentlyPlayed = new ArrayList<Song>();

	public Account(boolean admin) {
		this.admin = admin;
		createPlayList("Sample Playlist");
	}

	public boolean isAdmin() {
		return admin;
	}

	public void removeRestricted() {
		//if (admin) return;
		playLists.values().forEach(playlist -> playlist.getSongs().removeIf(Song::isRestricted));
		songQueue.removeIf(Song::isRestricted);
	}
	
	public void changePermissions() {
		
		admin = !admin;
		
	}

	public void promoteUser(Account user){
		
		if(user.isAdmin()) 
			{user.changePermissions();}
		
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

	public boolean removePlaylist(Playlist playlist) {
		return playLists.remove(playlist.getTitle(), playlist);
	}
	public SongQueue getSongQueue() {
		return songQueue;
	}
	public void addRecentlyPlayed(Song song) {
		recentlyPlayed.add(song);
	}
	public ArrayList<Song> getRecentlyPlayed() {
		return recentlyPlayed;
	}
}
