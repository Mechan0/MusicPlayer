package musicPlayer.scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import musicPlayer.Account;
import musicPlayer.AccountDatabase;
import musicPlayer.MusicPlayer;

import java.util.Comparator;

/**
 * Account-related scene factory class.
 * @author Alexander Yaroslavtsev, James Williamson
 */
public class AccountScenes {
    public static Scene selectAccount() {
        ListView<String> userAccounts = new ListView<>();
        ObservableList<String> users = FXCollections.observableArrayList();
        users.addAll(AccountDatabase.getNames());
        users.sort(Comparator.naturalOrder());
        Button newUser = new Button("Create New User");
        // lambda on button press load newAccountScene window
        newUser.setOnAction(e -> MusicPlayer.setScene(createAccount()));
        Button loadUser = new Button("Login as user");
        loadUser.setOnAction(e -> {
            // must have an account selected to log in
            Account account = AccountDatabase.getAccount(userAccounts.getSelectionModel().getSelectedItem());
            if (account != null) {
                MusicPlayer.setActiveAccount(account);
                account.removeRestricted();
                MusicPlayer.setScene(MainPageScene.getScene());
            }
        });
        userAccounts.setItems(users);
        VBox userAccountLayout = new VBox(10);
        userAccountLayout.setPadding(MusicPlayer.DEFAULT_PADDING);
        userAccountLayout.getChildren().addAll(MusicPlayer.createTitle("Select an Account"), userAccounts, loadUser, newUser);
        return new Scene(userAccountLayout, 200, 200);
    }
    public static Scene createAccount() {
        //Creating a GridPane container
        GridPane newAccLayout = new GridPane();
        newAccLayout.setPadding(MusicPlayer.DEFAULT_PADDING);
        newAccLayout.setVgap(10);
        newAccLayout.setHgap(10);

        // Defining header label
        newAccLayout.getChildren().add(MusicPlayer.createTitle("Create New User"));

        //Defining the userName text field
        final TextField userName = new TextField();
        userName.setPromptText("Enter your userName.");
        userName.setPrefColumnCount(10);
        GridPane.setConstraints(userName, 0, 1);
        newAccLayout.getChildren().add(userName);

        // admin checkbox
        CheckBox adminCheckbox = new CheckBox("Admin User");
        GridPane.setConstraints(adminCheckbox, 1, 1);
        newAccLayout.getChildren().add(adminCheckbox);

        GridPane buttonLayout = new GridPane();
        buttonLayout.setHgap(5);

        //Defining the Submit button
        Button submit = new Button("Submit");
        submit.setDisable(true);
        userName.textProperty().addListener((observable, oldValue, newValue) -> {
            submit.setDisable(newValue.isEmpty());
        });
        submit.setOnAction(e -> {
            AccountDatabase.addUser(userName.getText(), adminCheckbox.isSelected());
            userName.clear();
            MusicPlayer.setScene(selectAccount());
        });
        GridPane.setConstraints(submit, 0, 0);
        buttonLayout.getChildren().add(submit);

        // Defining return button
        Button returnButton = new Button("Cancel");
        returnButton.setOnAction(e -> MusicPlayer.setScene(selectAccount()));
        GridPane.setConstraints(returnButton, 1, 0);
        buttonLayout.getChildren().add(returnButton);

        GridPane.setConstraints(buttonLayout, 0, 2);
        newAccLayout.getChildren().addAll(buttonLayout);

        return new Scene(newAccLayout);
    }
}
