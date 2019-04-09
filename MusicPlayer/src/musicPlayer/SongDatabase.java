package musicPlayer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Represents a database of all songs, from which users can read entries. Reads
 * all files from songs.csv and loads them into Song objects
 * 
 * @author James Williamson
 *
 */
public class SongDatabase {
	public ArrayList<Song> songs;
	private HashMap<String, ArrayList<Song>> songsArtist, songsAlbum, songsGenre;
	private HashMap<Integer, ArrayList<Song>> songsYear;

	public SongDatabase() {
		songs = new ArrayList<Song>();
		generateDatabase();
		classifySongs();
	}

	private void generateDatabase() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("songs.csv"));
			String line;
			while ((line = reader.readLine()) != null) {
				if (!line.startsWith("#")) { // skip comments
					songs.add(lineToSong(line));
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void classifySongs() {
		songsArtist = new HashMap<String, ArrayList<Song>>();
		songsAlbum = new HashMap<String, ArrayList<Song>>();
		songsGenre = new HashMap<String, ArrayList<Song>>();
		songsYear = new HashMap<Integer, ArrayList<Song>>();
		for (Song song : songs) {
			// artist
			if(!songsArtist.containsKey(song.getArtist())) {
				songsArtist.put(song.getArtist(), new ArrayList<Song>(Arrays.asList(song)));
			} else {
				songsArtist.get(song.getArtist()).add(song);
			}
			// album
			if(!songsAlbum.containsKey(song.getAlbum())) {
				songsAlbum.put(song.getAlbum(), new ArrayList<Song>(Arrays.asList(song)));
			} else {
				songsAlbum.get(song.getAlbum()).add(song);
			}
			// genre
			if(!songsYear.containsKey(song.getYear())) {
				songsYear.put(song.getYear(), new ArrayList<Song>(Arrays.asList(song)));
			} else {
				songsYear.get(song.getYear()).add(song);
			}
			// year
			if(!songsGenre.containsKey(song.getGenre())) {
				songsGenre.put(song.getGenre(), new ArrayList<Song>(Arrays.asList(song)));
			} else {
				songsGenre.get(song.getGenre()).add(song);
			}
		}
	}
	public ArrayList<Song> getSongsArtist (String artist) {
		return songsArtist.get(artist);
	}
	public ArrayList<Song> getSongsGenre (String genre) {
		return songsGenre.get(genre);
	}
	public ArrayList<Song> getSongsAlbum (String album) {
		return songsAlbum.get(album);
	}
	public ArrayList<Song> getSongsYear (Integer year) {
		return songsYear.get(year);
	}
	public ArrayList<String> getArtists() {
		return new ArrayList<String>(songsArtist.keySet());
	}
	public ArrayList<String> getGenres() {
		return new ArrayList<String>(songsGenre.keySet());
	}
	public ArrayList<String> getAlbums() {
		return new ArrayList<String>(songsAlbum.keySet());
	}
	public ArrayList<Integer> getYears() {
		return new ArrayList<Integer>(songsYear.keySet());
	}
	
	private Song lineToSong(String line) {
		try {
			line = line.replaceAll("\"", "");
			String[] splitLine = line.split(";");
			return new Song(Integer.parseInt(splitLine[0]), splitLine[1], splitLine[2], splitLine[3],
					Integer.parseInt(splitLine[4]), Integer.parseInt(splitLine[5]), splitLine[6], splitLine[7]);
		} catch (Exception e) {
			e.printStackTrace();
			return new Song(-1, "ERROR", "ERROR", "ERROR", -1, -1, "ERROR", "ERROR");
		}
	}

	public Song getSong(int songID) {
		return songs.get(songID);
	}
}
