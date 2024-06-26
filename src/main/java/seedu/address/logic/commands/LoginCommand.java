package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.account.account.Account;
import seedu.address.account.account.Password;
import seedu.address.account.account.Username;
import seedu.address.logic.AccountManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a Login command with hidden internal logic and the ability to be executed.
 */
public class LoginCommand extends Command {

    /**
     * The keyword that identifies this command.
     */
    public static final String COMMAND_WORD = "login";

    /**
     * The usage message shown to the user detailing how this command should be invoked.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs in the user. "
            + "Parameters: "
            + PREFIX_USERNAME + " USERNAME "
            + PREFIX_PASSWORD + " PASSWORD \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + " john1234 "
            + PREFIX_PASSWORD + " qweasd123 ";

    public static final String MESSAGE_LOGIN_SUCCESS = "Login successful.";
    public static final String MESSAGE_LOGIN_FAILURE = "Login failed. Invalid username or password.";
    public static final String MESSAGE_ALREADY_LOGGED_IN = "You are already logged in.";

    /**
     * The account to be logged in.
     */
    private final Account account;

    /**
     * The account manager that handles the login process.
     */
    private AccountManager accountManager;

    /**
     * Creates a LoginCommand to log in the specified {@code Account}.
     *
     * @param account The account to be logged in.
     */
    public LoginCommand(Account account) {
        this.account = account;
    }

    /**
     * Sets the account manager for this command.
     *
     * @param accountManager The account manager to be set.
     */
    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    /**
     * Executes the login command.
     *
     * @param model The model to be used in command execution.
     * @return A CommandResult representing the result of the login operation.
     * @throws CommandException If the login operation fails.
     */
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(accountManager);
        boolean isUserLogin = accountManager.getLoginStatus();
        if (isUserLogin) {
            throw new CommandException(MESSAGE_ALREADY_LOGGED_IN);
        }

        Account accountToLogin = searchAccountInList();
        if (accountToLogin != null) {
            accountManager.login(accountToLogin);
            return new CommandResult(MESSAGE_LOGIN_SUCCESS, false, false, false, true, false);
        } else {
            throw new CommandException(MESSAGE_LOGIN_FAILURE);
        }
    }

    /**
     * Searches for the account in the account list.
     *
     * @return The account if found, null otherwise.
     */
    public Account searchAccountInList() {
        Username username = account.getUsername();
        Password passwordHash = account.getPasswordHash();
        return accountManager.getAccountList().authenticate(username, passwordHash);
    }
}
