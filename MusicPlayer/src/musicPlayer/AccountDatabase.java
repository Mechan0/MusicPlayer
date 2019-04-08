package musicPlayer;

import java.util.ArrayList;
import java.util.HashMap; 

/**
 * A database of all existing user accounts within the system
 *
 */
public class AccountDatabase {
	private HashMap<String, Account> userAccounts;
	public AccountDatabase() {
		// TODO: load user accounts into Hashmap with userName as key.
		userAccounts = new HashMap<String, Account>();
	}
	public void addUser(String userName) {
		userAccounts.put(userName, new Account());
	}
	public ArrayList<String> getNames() {
		ArrayList<String> namesList = new ArrayList<String>();
		namesList.addAll(userAccounts.keySet());
		return namesList;
	}
	public  Account getAccount(String userName) {
		return userAccounts.get(userName);
	}
}
