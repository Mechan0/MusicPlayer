package musicPlayer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import musicPlayer.scenes.AccountScenes;

/**
 * @author James Williamson, Alex Yaro
 *
 */
public class MusicPlayer extends Application {
	private static Stage window;
	private static double width = 200, height = 300; // default dimensions
	private static Account activeAccount;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		window.setOnHiding(e -> {
			width = window.getWidth();
			height = window.getHeight();
		});
		window.setOnShowing(e -> {
			// FIXME ListView poorly responding to the following update
			// (this is for keeping the same stage dimensions when switching scenes)
			//window.setWidth(width);
			//window.setHeight(height);
		});
		window.setTitle("Music Player");
		window.setScene(AccountScenes.selectAccount());
		window.show();
	}

	public static void setScene(Scene scene) {
		window.setScene(scene);
	}

	public static Account getActiveAccount() {
		return activeAccount;
	}

	public static void setActiveAccount(Account account) {
		activeAccount = account;
	}
}
