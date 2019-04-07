package com.mechan0.git.musicPlayer;
import javafx.application.Application;
import javafx.stage.Stage;

public class MusicPlayer extends Application {
	Stage window;
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Interface inter = new Interface();
		inter.initialiseInterface(primaryStage);
	}
}
