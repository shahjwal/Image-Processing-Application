package controller;

import model.ImageModel;
import model.ImageModelV2;

/**
 * The CompressCommand class is an implementation of the command pattern that compresses a specified
 * image by a given percentage. It saves the compressed image under a new specified name.
 */
class CompressCommand extends AbstractCommand {

  private final int percentage;
  private final String imageName;
  private final String saveImage;

  /**
   * Constructs a CompressCommand with the specified compression percentage, image name, and save
   * image name.
   *
   * @param percentage The percentage by which to compress the image. Must be an integer between 0
   *                   and 100.
   * @param imageName  The name of the image to compress.
   * @param saveImage  The name to assign to the compressed image.
   * @throws IllegalArgumentException if the percentage is not a valid integer or not in the valid
   *                                  range (0-100).
   */
  CompressCommand(String percentage, String imageName, String saveImage) {
    try {
      this.percentage = Integer.parseInt(percentage);
      if (this.percentage > 100 || this.percentage < 0) {
        throw new IllegalArgumentException("Percentage must be between 0 and 100");
      }
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Percentage must be an integer");
    }
    this.imageName = imageName;
    this.saveImage = saveImage;
  }

  /**
   * Executes the command to compress the specified image by the given percentage. This method
   * delegates the compression operation to the `applyCompress` method of `ImageModelV2`.
   * TypeCasting to ImageModelV2 is done explicitly as instance checking is done on interface field
   * type and by doing this we can use the existing old code and makes no need to change any older
   * version code.
   *
   * @param model The ImageModel instance used to perform the compression operation.
   * @throws IllegalArgumentException if the provided model is not an instance of ImageModelV2.
   */
  @Override
  void execute(ImageModel model) {
    if (model instanceof ImageModelV2) {
      ((ImageModelV2) model).applyCompress(this.percentage, this.imageName, this.saveImage);
    } else {
      throw new IllegalArgumentException("Invalid Command");
    }
  }

}
