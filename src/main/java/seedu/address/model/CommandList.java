package seedu.address.model;

import seedu.address.logic.commands.*;
import seedu.address.model.person.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Maintains a list of all executed commands.
 * This class provides methods to log commands after execution,
 * and can be utilized to support undo/redo functionality by
 * keeping track of the command history.
 */
public class CommandList {
    private final List<Command> commandHistory;
    private int currentCommandIndex;
    private Model model;


    public CommandList() {
        commandHistory = new ArrayList<>();
        currentCommandIndex = -1; // Indicates that no commands have been executed yet.
    }

    public void linkToModel(Model model) {
        this.model = model;
    }

    /**
     * Adds the executed command to the command history.
     * This command can later be undone or redone.
     *
     * @param command The command that was executed and is to be added to the history.
     */
    public void addCommand(Command command) {
        // Before adding a new command, clear any commands that were executed
        // after the current command's position in the history.
        // This is necessary because executing a new command after an undo operation
        // invalidates the subsequent redo history.
        while (commandHistory.size() > currentCommandIndex + 1) {
            commandHistory.remove(commandHistory.size() - 1);
        }
        commandHistory.add(command);
        currentCommandIndex++;
    }

    /**
     * Reverts the most recent command, if possible. This method should only be called if
     * {@link #canUndo()} returns true.
     *
     * @return true if the operation was successful, false otherwise.
     */
    public Command undo() {
        // Method implementation goes here
        return null;
    }

    /**
     * Reapplies the most recent undone command, if possible. This method should only be called if
     * {@link #canRedo()} returns true.
     *
     * @return true if the operation was successful, false otherwise.
     */
    public boolean redo() {
        // Method implementation goes here
        return false;
    }

    /**
     * Checks if there are commands available to undo.
     * This is determined by whether the current command index points to a valid position
     * within the history list, which means there is at least one executed command that can be undone.
     *
     * @return true if there is at least one command that can be undone, false otherwise.
     */
    public boolean canUndo() {
        return currentCommandIndex >= 0;
    }

    /**
     * Checks if there are commands available to redo.
     * This is determined by comparing the size of the command history list with the current command index.
     * If the size of the list is greater than the current command index + 1, it means there are undone commands
     * available to be redone.
     *
     * @return true if there is at least one command that can be redone, false otherwise.
     */
    public boolean canRedo() {
        return commandHistory.size() > currentCommandIndex + 1;
    }

    //=========== Undo and redo of add ======================================================================

    /**
     * Reverses the addition of a person to the address book.
     *
     * @param command The {@code AddCommand} whose effect is to be undone.
     */
    private void undoAdd(AddCommand command) {
        Person personAdded = command.getPersonToAdd();
        model.deletePerson(personAdded);
    }

    /**
     * Re-executes the addition of a person to the address book.
     *
     * @param command The {@code AddCommand} whose effect is to be redone.
     */
    private void redoAdd(AddCommand command) {
        Person personToAdd = command.getPersonToAdd();
        model.addPerson(personToAdd);
    }

    //=========== Undo and redo of delete ===================================================================

    /**
     * Reverses the deletion of a person from the address book.
     *
     * @param command The {@code DeleteCommand} whose effect is to be undone.
     */
    private void undoDelete(DeleteCommand command) {
        Person personDeleted = command.getPersonToDelete();
        model.addPerson(personDeleted);
    }

    /**
     * Re-executes the deletion of a person from the address book.
     *
     * @param command The {@code DeleteCommand} whose effect is to be redone.
     */
    private void redoDelete(DeleteCommand command) {
        Person personToDelete = command.getPersonToDelete();
        model.addPerson(personToDelete);
    }

    //=========== Undo and redo of edit =====================================================================

    /**
     * Reverses the edit of a person in the address book.
     *
     * @param command The {@code EditCommand} whose effect is to be undone.
     */
    private void undoEdit(EditCommand command) {
        Person personToEdit = command.getPersonToEdit();
        Person editedPerson = command.getEditedPerson();
        model.deletePerson(editedPerson);
        model.addPerson(personToEdit);
    }

    /**
     * Re-executes the edit of a person in the address book.
     *
     * @param command The {@code EditCommand} whose effect is to be redone.
     */
    private void redoEdit(EditCommand command) {
        Person personToEdit = command.getPersonToEdit();
        Person editedPerson = command.getEditedPerson();
        model.deletePerson(personToEdit);
        model.addPerson(editedPerson);
    }

    //=========== Undo and redo of clear ====================================================================

    /**
     * Reverses the effect of a clear operation on the address book.
     * This method restores the state of the address book to what it was before the clear operation
     * was executed.
     *
     * @param command The {@code ClearCommand} whose clearing effect is to be undone.
     */
    private void undoClear(ClearCommand command) {
        ReadOnlyAddressBook addressBookToRestore = command.getAddressBookBeforeClear();
        model.setAddressBook(addressBookToRestore);
    }

    /**
     * Re-executes the clear operation on the address book.
     * After an undo operation has restored the address book to its previous state, this method
     * allows for the redo of the clear operation, effectively emptying the address book once again.
     *
     * @param command The {@code ClearCommand} that is to be redone, clearing the address book.
     */
    private void redoClear(ClearCommand command) {
        model.setAddressBook(new AddressBook());
    }

}