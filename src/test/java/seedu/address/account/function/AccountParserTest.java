package seedu.address.account.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.account.account.Account;
import seedu.address.account.account.Password;
import seedu.address.account.account.Username;

public class AccountParserTest {
    private static final String TEST_USERNAME = "test";
    private static final String TEST_PASSWORD_HASH = "5f4dcc3b5aa765d61d8327deb882cf99"; // md5 hash for "password"

    @Test
    public void testParseToAccount() {
        AccountParser accountParser = new AccountParser();
        List<String> accountData = new ArrayList<>();
        accountData.add("username: " + TEST_USERNAME + " | passwordHash: " + TEST_PASSWORD_HASH);
        List<Account> accounts = accountParser.parseToAccount(accountData);
        assertEquals(1, accounts.size());
        assertEquals(TEST_USERNAME, accounts.get(0).getUsername().toString());
        assertEquals(TEST_PASSWORD_HASH, accounts.get(0).getPasswordHash().toString());
    }

    @Test
    public void testParseToString() {
        AccountParser accountParser = new AccountParser();
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(new Username(TEST_USERNAME), new Password(TEST_PASSWORD_HASH)));
        List<String> accountData = accountParser.parseToString(accounts);
        assertEquals(1, accountData.size());
        assertTrue(accountData.get(0).contains(TEST_USERNAME));
        assertTrue(accountData.get(0).contains(TEST_PASSWORD_HASH));
    }
}
