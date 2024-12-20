package controller;

import model.ImageModel;

/**
 * FlipCommand is an implementation of the command pattern that flips an image either horizontally
 * or vertically and saves the result. This class will delegate the requirements to the specific
 * model's method.
 */
class FlipCommand extends AbstractCommand {

  private final String imageName;
  private final String saveImage;
  private final boolean flip;

  /**
   * Constructs a FlipCommand with the specified image name, save image name, and flip direction.
   *
   * @param imageName The name of the image to flip.
   * @param saveImage The name of the image where the flipped result will be saved.
   * @param flip      A boolean flag indicating the flip direction: true for horizontal, false for
   *                  vertical.
   */
  FlipCommand(String imageName, String saveImage, boolean flip) {
    this.imageName = imageName;
    this.saveImage = saveImage;
    this.flip = flip;
  }

  /**
   * Executes the flip operation on the image by delegating the task to the model's method. It
   * passes the necessary information, including the image name, the save name, and the flip
   * direction.
   *
   * @param model The ImageModel instance used to perform the flip operation.
   */
  @Override
  void execute(ImageModel model) {
    model.applyFlip(this.imageName, this.saveImage, this.flip);
  }

}
