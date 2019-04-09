package musicPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * A database of all existing user accounts within the system.
 *
 * @author James Williamson, Alexander Yaroslavtsev
 */
public class AccountDatabase {
	private static final HashMap<String, Account> userAccounts = new LinkedHashMap<>(); // retain key ordering

	static {
		userAccounts.put("Sample User", new Account(false));
	}

	public static void addUser(String userName, boolean admin) {
		userAccounts.put(userName, new Account(admin));
	}

	public static ArrayList<String> getNames() {
		return new ArrayList<>(userAccounts.keySet());
	}

	public static Account getAccount(String userName) {
		if (userName == null)
			return null;
		return userAccounts.get(userName);
	}
}
