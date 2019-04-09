package musicPlayer;

import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author James Williamson, Alex Yaro
 *
 */
public class Interface {
	private final Stage window;
	private Scene userAccountSelectScene, newUserAccountScene, mainMenuScene;
	private SongDatabase songDatabase;
	private AccountDatabase accounts;
	private Account activeAccount;
	Interface(Stage primaryStage) {
		songDatabase = new SongDatabase();
		accounts = new AccountDatabase();
		window = primaryStage;
		window.setTitle("Music Player");
		// 1: user account scene
		newUserAccountScene = createNewUserAccountScene();
		userAccountSelectScene = createUserAccountSelectScene();
		mainMenuScene = createMainMenuScene();
		window.setScene(userAccountSelectScene);
		window.show();
	}
	private Scene createUserAccountSelectScene() {
		ListView<String> userAccounts = new ListView<>();
		ObservableList<String> users = FXCollections.observableArrayList();
		users.addAll(accounts.getNames());
		users.sort(Comparator.naturalOrder());
		Button newUser = new Button("Create New User");
		// lambda on button press load newAccountScene window
		newUser.setOnAction(e -> window.setScene(newUserAccountScene));
		Button loadUser = new Button("Login as user");
		loadUser.setOnAction(e -> {
			// must have an account selected to log in
			if((activeAccount = accounts.getAccount(userAccounts.getSelectionModel().getSelectedItem())) != null) {
				window.setScene(mainMenuScene);
			}
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
		newAccLayout.getChildren().add(new Label("Username:"));
		//Defining the userName text field
		final TextField userName = new TextField();
		userName.setPromptText("Enter your userName.");
		userName.setPrefColumnCount(10);
		GridPane.setConstraints(userName, 1, 0);
		newAccLayout.getChildren().add(userName);
		//Defining the Submit button
		Button submit = new Button("Submit");
		submit.setOnAction(e -> {
			accounts.addUser(userName.getText());
			userName.clear();
			userAccountSelectScene = createUserAccountSelectScene();
			window.setScene(userAccountSelectScene);
		});
		GridPane.setConstraints(submit, 2, 0);
		newAccLayout.getChildren().add(submit);
		// Defining return button
		Button returnButton = new Button("Cancel");
		returnButton.setOnAction(e -> window.setScene(userAccountSelectScene));
		GridPane.setConstraints(returnButton, 0, 1);
		newAccLayout.getChildren().add(returnButton);
		
		return new Scene(newAccLayout, 300, 100);
	}
	private Scene createMainMenuScene() {
		//TODO: Flesh out main panel, add buttons to toolbar
		BorderPane mainLayout = new BorderPane();
		ToolBar toolbar = new ToolBar();
		VBox centerContent = new VBox();
	    HBox statusbar = new HBox(); 
	    SongTableFactory stf = new SongTableFactory();
	    TableView<Song> songTable = stf.createSongTable(songDatabase.songs);
	    centerContent.getChildren().add(songTable);
	    mainLayout.setTop(toolbar);
	    mainLayout.setCenter(centerContent);
	    mainLayout.setBottom(statusbar);
	    return new Scene(mainLayout);
	}
}
