package musicPlayer.scenes;

import javafx.scene.Scene;
import musicPlayer.Song;

import java.util.function.Supplier;

@Deprecated
public class SongStatisticsScene {
	public static Scene statisticsScene(Song song, Supplier<Scene> previousScene) {
		// the page must have a button to return the user to the previous scene
		// using previousScene#get
		throw new IllegalStateException("not implemented"); // TODO branden
	}
}
