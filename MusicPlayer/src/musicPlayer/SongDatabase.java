package musicPlayer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a database of all songs, from which users can read entries. Reads
 * all files from songs.csv and loads them into Song objects.
 * 
 * @author James Williamson, Alexander Yaroslavtsev
 *
 */
public class SongDatabase {
	private static final List<Song> songs = new ArrayList<>();
	private static HashMap<String, ArrayList<Song>> songsArtist, songsAlbum, songsGenre;
	private static HashMap<Integer, ArrayList<Song>> songsYear;

	static {
		load();
		classifySongs();
	}

	public static List<Song> getSongs() {
		return Collections.unmodifiableList(songs);
	}

	public static Song getSong(int songID) {
		return songs.get(songID);
	}

	private static void load() {
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

	private static void classifySongs() {
		songsArtist = new HashMap<>();
		songsAlbum = new HashMap<>();
		songsGenre = new HashMap<>();
		songsYear = new HashMap<>();
		for (Song song : songs) {
			// artist
			if(!songsArtist.containsKey(song.getArtist())) {
				songsArtist.put(song.getArtist(), new ArrayList<>(Collections.singletonList(song)));
			} else {
				songsArtist.get(song.getArtist()).add(song);
			}
			// album
			if(!songsAlbum.containsKey(song.getAlbum())) {
				songsAlbum.put(song.getAlbum(), new ArrayList<>(Collections.singletonList(song)));
			} else {
				songsAlbum.get(song.getAlbum()).add(song);
			}
			// genre
			if(!songsYear.containsKey(song.getYear())) {
				songsYear.put(song.getYear(), new ArrayList<>(Collections.singletonList(song)));
			} else {
				songsYear.get(song.getYear()).add(song);
			}
			// year
			if(!songsGenre.containsKey(song.getGenre())) {
				songsGenre.put(song.getGenre(), new ArrayList<>(Collections.singletonList(song)));
			} else {
				songsGenre.get(song.getGenre()).add(song);
			}
		}
	}
	public static ArrayList<Song> getSongsArtist(String artist) {
		return songsArtist.get(artist);
	}
	public static ArrayList<Song> getSongsGenre(String genre) {
		return songsGenre.get(genre);
	}
	public static ArrayList<Song> getSongsAlbum(String album) {
		return songsAlbum.get(album);
	}
	public static ArrayList<Song> getSongsYear(Integer year) {
		return songsYear.get(year);
	}
	public static ArrayList<String> getArtists() {
		return new ArrayList<>(songsArtist.keySet());
	}
	public static ArrayList<String> getGenres() {
		return new ArrayList<>(songsGenre.keySet());
	}
	public static ArrayList<String> getAlbums() {
		return new ArrayList<>(songsAlbum.keySet());
	}
	public static ArrayList<Integer> getYears() {
		return new ArrayList<>(songsYear.keySet());
	}
	
	private static Song lineToSong(String line) {
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
}
