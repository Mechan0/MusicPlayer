package com.mechan0.git.musicPlayer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Represents a database of all songs, from which users can read entries.
 * Reads all files from songs.csv and loads them into Song objects
 * @author James
 *
 */
public class SongDatabase {
	private ArrayList<Song> songs;
	public SongDatabase() {
		songs = new ArrayList<Song>();
		generateDatabase();
	}
	private void generateDatabase() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("songs.csv"));
			String line;
			while((line = reader.readLine()) != null) {
				if(!line.startsWith("#")) { // skip comments
					songs.add(lineToSong(line));
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private Song lineToSong(String line) {
		try {
			String[] splitLine = line.split(";");
			return new Song(Integer.parseInt(splitLine[0]), splitLine[1], splitLine[2], splitLine[3], Integer.parseInt(splitLine[4]), Integer.parseInt(splitLine[5]), splitLine[6], splitLine[7]);
		} catch (Exception e) {
			e.printStackTrace();
			return new Song(-1, "ERROR", "ERROR", "ERROR", -1, -1, "ERROR", "ERROR");
		}
	}
	public Song getSong(int songID) {
		return songs.get(songID);
	}
}
