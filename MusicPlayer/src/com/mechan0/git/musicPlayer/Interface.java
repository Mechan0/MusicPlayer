package com.mechan0.git.musicPlayer;

import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Interface {
	private final Stage window;
	private Scene userAccountScene, newUserAccountScene;

	Interface(Stage primaryStage) {
		window = primaryStage;
		window.setTitle("Music Player");
		// 1: user account scene
		newUserAccountScene = createNewUserAccountScene();
		userAccountScene = createUserAccountScene();
		window.setScene(userAccountScene);
		window.show();
	}
	private Scene createUserAccountScene() {
		ListView<String> userAccounts = new ListView<>();
		// TODO: load users from database file
		ObservableList<String> users = FXCollections.observableArrayList (
			    "James", "Alex", "Tony", "Brandon");
		users.sort(Comparator.naturalOrder());
		Button newUser = new Button("Create New User");
		// lambda on button press load newAccountScene window
		newUser.setOnAction(e -> window.setScene(newUserAccountScene));
		Button loadUser = new Button("Login as user");
		loadUser.setOnAction(e -> {
			//TODO: Retrieve text box value account and load corresponding panel
		});
		userAccounts.setItems(users);
		VBox userAccountLayout = new VBox(20);
		userAccountLayout.getChildren().addAll(new Label("Users: "), userAccounts, loadUser, newUser);
		return new Scene(userAccountLayout, 200, 200);
	}
	private Scene createNewUserAccountScene() {
		//Creating a GridPane container
		GridPane newAccLayout = new GridPane();
		
		newAccLayout.setPadding(new Insets(10, 10, 10, 10));
		newAccLayout.setVgap(5);
		newAccLayout.setHgap(5);
		// Defining header label
		Label headLabel = new Label("Username:");
		//GridPane.setConstraints(headLabel, 1, 0);
		newAccLayout.getChildren().add(new Label("Username"));
		//Defining the userName text field
		final TextField userName = new TextField();
		userName.setPromptText("Enter your userName.");
		userName.setPrefColumnCount(10);
		userName.getText();
		GridPane.setConstraints(userName, 1, 0);
		newAccLayout.getChildren().add(userName);
		//Defining the Submit button
		Button submit = new Button("Submit");
		submit.setOnAction(e -> {
			//TODO: add value of textbox to user accounts
			window.setScene(userAccountScene);
		});
		GridPane.setConstraints(submit, 2, 0);
		newAccLayout.getChildren().add(submit);
		// Defining return button
		Button returnButton = new Button("Cancel");
		returnButton.setOnAction(e -> window.setScene(userAccountScene));
		GridPane.setConstraints(returnButton, 0, 1);
		newAccLayout.getChildren().add(returnButton);
		
		return new Scene(newAccLayout, 200, 200);
	}
}
