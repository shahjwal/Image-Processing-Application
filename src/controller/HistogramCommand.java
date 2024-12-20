package controller;

import model.ImageModel;
import model.ImageModelV2;

/**
 * HistogramCommand is an implementation of the command pattern that encapsulates the action of
 * applying a histogram operation to an image. It allows for generating and saving the histogram of
 * a specified image.
 */
class HistogramCommand extends AbstractCommand {

  private final String imageName;
  private final String saveImage;

  /**
   * Constructs a HistogramCommand with the specified image name and save image name.
   *
   * @param imageName The name of the image to apply the histogram operation.
   * @param saveImage The name of the image where the histogram will be saved.
   */
  HistogramCommand(String imageName, String saveImage) {
    this.imageName = imageName;
    this.saveImage = saveImage;
  }

  /**
   * Executes the command to apply a histogram operation to the specified image by delegating the
   * task to the model's method. If the model is an instance of `ImageModelV2`, it applies the
   * histogram operation using the `applyHistogram` method. Otherwise, it throws an
   * IllegalArgumentException. TypeCasting to ImageModelV2 is done explicitly as instance checking
   * is done on interface field type and by doing this we can use the existing old code and makes no
   * need to change any older version code.
   *
   * @param model The ImageModel instance used to perform the histogram operation.
   * @throws IllegalArgumentException If the model is not an instance of ImageModelV2.
   */
  @Override
  void execute(ImageModel model) {
    if (model instanceof ImageModelV2) {
      ((ImageModelV2) model).applyHistogram(this.imageName, this.saveImage);
    } else {
      throw new IllegalArgumentException("Invalid Command");
    }
  }

}
