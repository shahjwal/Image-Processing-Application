package controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * The CommandFactory class is responsible for creating and returning specific command instances
 * based on user input. It uses a map to associate command strings with factory functions, allowing
 * dynamic and efficient command creation based on the provided input tokens. This factory class
 * helps to simplify the command creation logic, enabling the application to handle various commands
 * with different parameters. The class supports commands for image processing operations such as
 * color correction, levels adjustment, histogram generation, and more.
 */
class CommandFactory {

  /**
   * A static map that links command strings to factory functions that generate corresponding
   * command objects. Each entry associates a command keyword with a lambda function that parses the
   * input tokens and constructs an appropriate AbstractCommand instance.
   */
  private static final Map<String, Function<String[], AbstractCommand>> commandMap =
      new HashMap<>();

  static {
    commandMap.put("color-correct", tokens -> {
      validateCommandLength(tokens, 3, 5);
      if (tokens.length == 3) {
        return new ColorCorrectCommand(tokens[1], tokens[2]);
      } else {
        return new SplitPreviewCommand(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
      }
    });
    commandMap.put("levels-adjust", tokens -> {
      validateCommandLength(tokens, 6, 8);
      if (tokens.length == 6) {
        return new LevelsAdjustCommand(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
      } else {
        return new SplitPreviewCommand(tokens[0], tokens[4], tokens[5], tokens[6], tokens[7],
            tokens[1], tokens[2], tokens[3]);
      }
    });
    commandMap.put("histogram", tokens -> {
      validateCommandLength(tokens, 3);
      return new HistogramCommand(tokens[1], tokens[2]);
    });
    commandMap.put("compress", tokens -> {
      validateCommandLength(tokens, 4);
      return new CompressCommand(tokens[1], tokens[2], tokens[3]);
    });
    commandMap.put("exit", tokens -> {
      validateCommandLength(tokens, 1);
      System.exit(0);
      return null;
    });
    commandMap.put("load", tokens -> {
      validateCommandLength(tokens, 3);
      return new LoadCommand(tokens[1], tokens[2], getFileExtension(tokens[1]));
    });
    commandMap.put("save", tokens -> {
      validateCommandLength(tokens, 3);
      return new SaveCommand(tokens[1], tokens[2], getFileExtension(tokens[1]));
    });
    commandMap.put("brighten", tokens -> {
      validateCommandLength(tokens, 4);
      return new BrightenCommand(tokens[1], tokens[2], tokens[3]);
    });
    commandMap.put("blur", tokens -> {
      validateCommandLength(tokens, 3, 4, 5);
      if (tokens.length == 3) {
        return new BlurCommand(tokens[1], tokens[2]);
      } else if (tokens.length == 4) {
        return new MaskImageCommand(tokens[0], tokens[1], tokens[2], tokens[3]);
      } else {
        return new SplitPreviewCommand(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
      }
    });
    commandMap.put("sharpen", tokens -> {
      validateCommandLength(tokens, 3, 4, 5);
      if (tokens.length == 3) {
        return new SharpenCommand(tokens[1], tokens[2]);
      } else if (tokens.length == 4) {
        return new MaskImageCommand(tokens[0], tokens[1], tokens[2], tokens[3]);
      } else {
        return new SplitPreviewCommand(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
      }
    });
    commandMap.put("sepia", tokens -> {
      validateCommandLength(tokens, 3, 4, 5);
      if (tokens.length == 3) {
        return new SepiaCommand(tokens[1], tokens[2]);
      } else if (tokens.length == 4) {
        return new MaskImageCommand(tokens[0], tokens[1], tokens[2], tokens[3]);
      } else {
        return new SplitPreviewCommand(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
      }
    });
    commandMap.put("vertical-flip", tokens -> {
      validateCommandLength(tokens, 3);
      return new FlipCommand(tokens[1], tokens[2], true);
    });
    commandMap.put("horizontal-flip", tokens -> {
      validateCommandLength(tokens, 3, 5);
      return new FlipCommand(tokens[1], tokens[2], false);
    });
    commandMap.put("red-component", tokens -> {
      validateCommandLength(tokens, 3, 4, 5);
      if (tokens.length == 3) {
        return new ComponentCommand(tokens[1], tokens[2], "red");
      } else if (tokens.length == 4) {
        return new MaskImageCommand(tokens[0], tokens[1], tokens[2], tokens[3]);
      } else {
        return new SplitPreviewCommand(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
      }
    });
    commandMap.put("green-component", tokens -> {
      validateCommandLength(tokens, 3, 4, 5);
      if (tokens.length == 3) {
        return new ComponentCommand(tokens[1], tokens[2], "green");
      } else if (tokens.length == 4) {
        return new MaskImageCommand(tokens[0], tokens[1], tokens[2], tokens[3]);
      } else {
        return new SplitPreviewCommand(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
      }
    });
    commandMap.put("blue-component", tokens -> {
      validateCommandLength(tokens, 3, 4, 5);
      if (tokens.length == 3) {
        return new ComponentCommand(tokens[1], tokens[2], "blue");
      } else if (tokens.length == 4) {
        return new MaskImageCommand(tokens[0], tokens[1], tokens[2], tokens[3]);
      } else {
        return new SplitPreviewCommand(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
      }
    });
    commandMap.put("luma-component", tokens -> {
      validateCommandLength(tokens, 3, 4, 5);
      if (tokens.length == 3) {
        return new ComponentCommand(tokens[1], tokens[2], "luma");
      } else if (tokens.length == 4) {
        return new MaskImageCommand(tokens[0], tokens[1], tokens[2], tokens[3]);
      } else {
        return new SplitPreviewCommand(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
      }
    });
    commandMap.put("value-component", tokens -> {
      validateCommandLength(tokens, 3, 4, 5);
      if (tokens.length == 3) {
        return new ComponentCommand(tokens[1], tokens[2], "value");
      } else if (tokens.length == 4) {
        return new MaskImageCommand(tokens[0], tokens[1], tokens[2], tokens[3]);
      } else {
        return new SplitPreviewCommand(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
      }
    });
    commandMap.put("intensity-component", tokens -> {
      validateCommandLength(tokens, 3, 4, 5);
      if (tokens.length == 3) {
        return new ComponentCommand(tokens[1], tokens[2], "intensity");
      } else if (tokens.length == 4) {
        return new MaskImageCommand(tokens[0], tokens[1], tokens[2], tokens[3]);
      } else {
        return new SplitPreviewCommand(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
      }
    });
    commandMap.put("rgb-split", tokens -> {
      validateCommandLength(tokens, 5);
      return new SplitCommand(tokens[1], tokens[2], tokens[3], tokens[4]);
    });
    commandMap.put("rgb-combine", tokens -> {
      validateCommandLength(tokens, 5);
      return new CombineCommand(tokens[1], tokens[2], tokens[3], tokens[4]);
    });
  }

  /**
   * Extracts the file extension from a file path.
   *
   * @param path the file path from which to extract the extension.
   * @return the file extension in lowercase as a string.
   * @throws IllegalArgumentException if the path does not contain an extension.
   */
  private static String getFileExtension(String path) {
    if (!path.contains(".")) {
      throw new IllegalArgumentException("Invalid path without extension!");
    }
    String[] tokens = path.split("[.]");
    return tokens[tokens.length - 1].toLowerCase();
  }


  /**
   * Validates that the command input length matches one of the expected lengths. This method checks
   * if the length of the provided tokens array corresponds to any of the valid lengths specified in
   * the expectedLength parameter. If none of the lengths match, an exception is thrown, indicating
   * that the command is invalid.
   *
   * @param tokens         the command tokens from user input.
   * @param expectedLength the valid lengths for the command.
   * @throws IllegalArgumentException if the length of tokens does not match any expected length.
   */
  private static void validateCommandLength(String[] tokens, int... expectedLength) {
    boolean isValid = Arrays.stream(expectedLength)
        .anyMatch(length -> length == tokens.length);
    if (!isValid) {
      throw new IllegalArgumentException("Invalid Command parameters!");
    }
  }

  /**
   * Creates and returns the appropriate command instance based on the input tokens. This method
   * retrieves the command keyword from the input tokens, searches for the corresponding factory
   * function in the commandMap, and invokes it to create the command instance. If the command
   * keyword does not match any entry, an exception is thrown.
   *
   * @param tokens the command input tokens from user input.
   * @return the corresponding AbstractCommand instance based on the command type.
   * @throws IllegalArgumentException if the command keyword is not recognized.
   */
  static AbstractCommand createCommand(String[] tokens) {
    String commandType = tokens[0].toLowerCase();
    Function<String[], AbstractCommand> commandFunction = commandMap.get(commandType);
    if (commandFunction != null) {
      return commandFunction.apply(tokens);
    } else {
      throw new IllegalArgumentException("Command does not exist :" + tokens[0]);
    }
  }
}
