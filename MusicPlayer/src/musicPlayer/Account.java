package musicPlayer;

import java.util.HashMap;

/**
 * Represents a user account, storing their PlayLists and other information
 * 
 *
 */
public class Account {
	private HashMap<String, PlayList> playLists;
	public Account() {
		playLists = new HashMap<String, PlayList>();
	}
	public void addPlayList(String title) {
		playLists.put(title, new PlayList());
	}
}
