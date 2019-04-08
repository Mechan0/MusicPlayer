package musicPlayer;
import javafx.application.Application;
import javafx.stage.Stage;

public class MusicPlayer extends Application {
	private Stage window;
	private double width = 200, height = 300; // default dimensions

	public static void main(String[] args) {
		//SongDatabase sd = new SongDatabase();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.window = primaryStage;
		this.window.setOnHiding(e -> {
			width = window.getWidth();
			height = window.getHeight();
		});
		this.window.setOnShowing(e -> {
			// FIXME ListView poorly responding to the following update
			// (this is for keeping the same stage dimensions when switching scenes)
			//window.setWidth(width);
			//window.setHeight(height);
		});
		new Interface(primaryStage);
	}
}
