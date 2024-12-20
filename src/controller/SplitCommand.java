package controller;

import model.ImageModel;


/**
 * SplitCommand is an implementation of the command pattern that encapsulates the action of
 * splitting an image into its red, green, and blue color components. It separates the original
 * image and saves each color component as a new image under specified names for the red, green, and
 * blue channels.It stores the names for the original image and the output images corresponding to
 * the red, green, and blue channels.
 */
class SplitCommand extends AbstractCommand {

  private final String imageName;
  private final String redImage;
  private final String greenImage;
  private final String blueImage;

  /**
   * Constructs a SplitCommand with the given image and channel names.
   *
   * @param imageName  the name of the image to be split into color channels.
   * @param redImage   the name to save the red channel image.
   * @param greenImage the name to save the green channel image.
   * @param blueImage  the name to save the blue channel image.
   */
  SplitCommand(String imageName, String redImage, String greenImage, String blueImage) {
    this.imageName = imageName;
    this.redImage = redImage;
    this.greenImage = greenImage;
    this.blueImage = blueImage;
  }

  /**
   * Executes the split operation, which separates the image into red, green, and blue components.
   * This method delegates the execution task to the model's respective method.
   *
   * @param model the ImageModel instance to perform operations on.
   */
  @Override
  void execute(ImageModel model) {
    model.applySplit(this.imageName, this.redImage, this.greenImage, this.blueImage);
  }


}
