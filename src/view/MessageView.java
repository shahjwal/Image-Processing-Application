package view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The MessageView class implements the Message interface and provides concrete implementations for
 * printing messages to the user. The class handles output to an Appendable (such as Console or
 * File) and includes logging for error handling.
 */

public class MessageView implements Message {

  private final Appendable out;
  private static final Logger logger = Logger.getLogger(MessageView.class.getName());

  /**
   * Constructs a MessageView with the specified Appendable for output.
   *
   * @param out The Appendable (e.g., System.out or a file) where messages will be printed.
   */
  public MessageView(Appendable out) {
    this.out = out;
  }

  /**
   * Prints the welcome message to the user. This message is typically displayed when the
   * application starts or when the user first interacts with it.
   */
  @Override
  public void printWelcomeMessage() {
    try {
      this.out.append("Enter commands (enter 'exit' to terminate the program) : \n");
    } catch (IOException e) {
      logger.log(Level.WARNING, "Failed to print welcome message", e);
    }
  }

  /**
   * Prints a success message to the user indicating that a command was executed successfully.
   *
   * @param message The message to be displayed, indicating which command was successfully
   *                executed.
   */
  @Override
  public void printSuccess(String message) {
    try {
      this.out.append("\033[33m").append(message).append(" command executed successfully!")
          .append("\033[0m\n");
    } catch (IOException e) {
      logger.log(Level.WARNING, "Failed to print success message", e);
    }
  }

  /**
   * Prints an error message to the user indicating that something went wrong.
   *
   * @param message The error message to be displayed.
   */
  @Override
  public void printError(String message) {
    try {
      this.out.append("\033[91m").append(message).append("\033[0m\n");
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Failed to print error message", e);
    }
  }
}
