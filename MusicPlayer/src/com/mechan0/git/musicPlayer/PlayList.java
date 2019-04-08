package com.mechan0.git.musicPlayer;

import java.util.LinkedList;
import java.util.Queue;

public class PlayList {
	private Queue<Song> songs;
	public PlayList() {
		songs = new LinkedList<Song>();
	}
	public void addSong(Song song) {
		songs.add(song);
	}
}
