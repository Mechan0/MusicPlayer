package musicPlayer.scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import musicPlayer.Account;
import musicPlayer.AccountDatabase;
import musicPlayer.MusicPlayer;

import java.util.Comparator;

/**
 * Account-related scene factory class.
 * @author Alex
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
                MusicPlayer.setScene(MainPageScene.getScene());
            }
        });
        userAccounts.setItems(users);
        VBox userAccountLayout = new VBox(20);
        userAccountLayout.setPadding(new Insets(10));
        userAccountLayout.getChildren().addAll(new Label("Users: "), userAccounts, loadUser, newUser);
        return new Scene(userAccountLayout, 200, 200);
    }
    public static Scene createAccount() {
        //Creating a GridPane container
        GridPane newAccLayout = new GridPane();
        newAccLayout.setPadding(new Insets(10));
        newAccLayout.setVgap(5);
        newAccLayout.setHgap(5);

        // Defining header label
        newAccLayout.getChildren().add(new Label("Create New User:"));

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

        return new Scene(newAccLayout, 300, 100);
    }
}
