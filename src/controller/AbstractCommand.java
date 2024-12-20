package controller;

import model.ImageModel;

/**
 * The AbstractCommand class serves as a base class for all command types in the image processing
 * application. It defines a contract for command implementations, requiring an execute method that
 * takes an ImageModel as a parameter.
 */
abstract class AbstractCommand {

  /**
   * Executes the command using the specified ImageModel.
   *
   * @param model the ImageModel instance to perform operations on.
   */
  abstract void execute(ImageModel model);

}
