package view;

/**
 * The Message interface defines methods for printing various types of messages in the application.
 * It is intended to be implemented by classes responsible for displaying user messages such as
 * welcome messages, success messages, and error messages.
 */
public interface Message {

  /**
   * Prints a welcome message to the user. This message is typically shown when the application
   * starts or when the user first interacts with it.
   */
  void printWelcomeMessage();

  /**
   * Prints a success message to the user. This method is used to display messages indicating that
   * an operation has been successfully completed.
   *
   * @param message The success message to be displayed.
   */
  void printSuccess(String message);

  /**
   * Prints an error message to the user. This method is used to display messages indicating that an
   * error has occurred during an operation.
   *
   * @param message The error message to be displayed.
   */
  void printError(String message);

}
