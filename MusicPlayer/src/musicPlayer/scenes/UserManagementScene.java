package musicPlayer.scenes;

import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import musicPlayer.Account;
import musicPlayer.AccountDatabase;
import musicPlayer.MusicPlayer;

/**
 * @author Alexander Yaroslavtsev
 */
public class UserManagementScene {
		
	public static Scene getScene() {
		
		
		 
		 Button Return = new Button("Return");
	     Return.setOnAction(e -> MusicPlayer.setScene(MainPageScene.getScene()));
		
		 ListView<String> userAccounts = new ListView<>();
		 ObservableList<String> users = FXCollections.observableArrayList();
		 users.addAll(AccountDatabase.getNames());
	     users.sort(Comparator.naturalOrder());	     
	     userAccounts.setItems(users);
		
	     Button changePerms = new Button("change Permissions");
		changePerms.setOnAction(e -> { 
			AccountDatabase.getAccount(userAccounts.getSelectionModel().getSelectedItem()).changePermissions();			
		});
		    
		
	     VBox userAccountLayout = new VBox(10);
	        userAccountLayout.setPadding(MusicPlayer.DEFAULT_PADDING);
	        userAccountLayout.getChildren().addAll(MusicPlayer.createTitle("Select an Account"), userAccounts, changePerms, Return);
	        return new Scene(userAccountLayout, 200, 200);
                
	}
}
