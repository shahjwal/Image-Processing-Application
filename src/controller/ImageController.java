package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import model.ImageModel;
import view.Message;

/**
 * The ImageController class is responsible for managing and executing user commands for image
 * processing operations. This class acts as the controller in the MVC (Model-View-Controller)
 * architecture, coordinating between the model (ImageModel) that performs the actual image
 * manipulation and the view (Message) that displays feedback to the user. The controller supports
 * two types of commands: - Single commands, entered directly by the user. - Scripted commands from
 * a specified .txt file, allowing batch processing. Commands are parsed and dispatched to the model
 * for execution. Feedback on command success or errors is communicated to the user via the view.
 * The class also includes robust error handling, ensuring meaningful messages are shown if a
 * command fails or if an invalid file is provided. Primary functionalities include: - Parsing and
 * executing commands in both interactive and script modes. - Validating and reading commands from
 * an input file. - Using a command factory to dynamically create and execute appropriate commands.
 * This design promotes flexibility, enabling users to work with individual commands or scripts,
 * while keeping the command execution flow modular and easy to extend.
 */
public class ImageController implements ControllerInterface {

  private final ImageModel imageModel;
  private final Message view;
  private final Readable in;

  /**
   * Constructor to initialize the controller with model, view, and input source.
   *
   * @param model The image model instance for image operations.
   * @param view  The view instance for user communication.
   * @param in    Input source for command reading.
   */
  public ImageController(ImageModel model, Message view, Readable in) {
    this.imageModel = model;
    this.view = view;
    this.in = in;
  }

  /**
   * Parses a command to open and return a BufferedReader for reading commands from a file. Expects
   * a valid command with the format "run filename.txt".
   *
   * @param command The input command containing the file path.
   * @return BufferedReader for reading the command file.
   */
  private BufferedReader readFileFromCommand(String command) {
    String[] cmdTokens = command.split(" ");
    if (cmdTokens.length != 2) {
      throw new IllegalArgumentException("Invalid command parameter!");
    }
    String path = cmdTokens[1];
    if (!path.toLowerCase().endsWith(".txt")) {
      throw new IllegalArgumentException("Invalid file type. Please provide a .txt file.");
    }

    try {
      return new BufferedReader(new FileReader(path));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(
          "Unable to open file: " + path); // Rethrow or handle gracefully
    }
  }

  /**
   * Reads and executes commands line by line from a provided BufferedReader. Commands in the file
   * can be commented out by starting with "#" and are skipped.
   *
   * @param reader BufferedReader from which to read commands.
   * @throws IllegalArgumentException If an error occurs during file reading.
   */
  private void getCommandsFromScript(BufferedReader reader) {
    try {
      String line;
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (!line.isEmpty() && (line.charAt(0) != '#')) {
          this.executeCommand(line);
        }
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("File not found!");
    }
  }

  /**
   * Parses and executes a single command. Uses a factory to create the appropriate command and
   * executes it on the image model.
   *
   * @param command The command string to be executed.
   * @throws IOException If an error occurs while executing the command.
   */
  private void executeCommand(String command) throws IOException {
    command = command.replaceAll("\\s+", " ").trim();
    String[] tokens = command.split(" ");
    if (tokens.length == 0) {
      return;
    }
    tokens[0] = tokens[0].toLowerCase();
    AbstractCommand cmd;
    try {
      cmd = CommandFactory.createCommand(tokens);
      cmd.execute(imageModel);
      this.view.printSuccess(tokens[0]);
    } catch (Exception e) {
      this.view.printError(e.getMessage());
    }
  }

  /**
   * Starts the controller to continuously read commands from the input source until terminated.
   * Recognizes "run filename.txt" commands to execute a batch of commands from a file. Otherwise,
   * processes individual commands.
   */
  @Override
  public void start() {
    try (Scanner scanner = new Scanner(this.in)) {
      this.view.printWelcomeMessage();
      while (scanner.hasNextLine()) {
        String command = scanner.nextLine().trim();
        if (command.startsWith("run")) {
          this.getCommandsFromScript(this.readFileFromCommand(command));
        } else {
          this.executeCommand(command);
        }
      }
    } catch (Exception e) {
      this.view.printError(e.getMessage());
    }
  }
}
