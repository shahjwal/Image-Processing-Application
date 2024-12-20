package controller;

import model.ImageModel;


/**
 * CombineCommand is an implementation of the command pattern that combines three images
 * (representing the red, green, and blue color channels) into a single image. The combined image
 * will be saved with the specified name. This class will delegate the requirements to the specific
 * model's method.
 */
class CombineCommand extends AbstractCommand {

  private final String imageName;
  private final String redImage;
  private final String greenImage;
  private final String blueImage;

  /**
   * Constructs a CombineCommand with the specified image names for the red, green, and blue
   * channels.
   *
   * @param imageName  The name for the resulting combined image.
   * @param redImage   The name of the image to use for the red color channel.
   * @param greenImage The name of the image to use for the green color channel.
   * @param blueImage  The name of the image to use for the blue color channel.
   */
  CombineCommand(String imageName, String redImage, String greenImage, String blueImage) {
    this.imageName = imageName;
    this.redImage = redImage;
    this.greenImage = greenImage;
    this.blueImage = blueImage;
  }

  /**
   * Executes the command to combine the specified red, green, and blue channel images into a single
   * image. This method delegates the operation to the provided model's `applyCombine` method, where
   * the actual logic for combining the images is implemented. The original images (red, green, and
   * blue channels) are referenced by {@code redImage}, {@code greenImage}, and {@code blueImage},
   * respectively. The combined result is saved under the name {@code imageName}.
   *
   * @param model The ImageModel instance that performs the combine operation.
   */
  @Override
  void execute(ImageModel model) {
    model.applyCombine(this.imageName, this.redImage, this.greenImage, this.blueImage);
  }

}
