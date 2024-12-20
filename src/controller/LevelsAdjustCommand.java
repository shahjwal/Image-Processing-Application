package controller;

import model.ImageModel;
import model.ImageModelV2;

/**
 * LevelsAdjustCommand is an implementation of the command pattern that applies a level adjustment
 * to an image, which modifies the image's black, mid, and white levels (brightness levels).
 */
class LevelsAdjustCommand extends AbstractCommand {

  private final int black;
  private final int mid;
  private final int white;
  private final String imageName;
  private final String saveImage;

  /**
   * Constructs a LevelsAdjustCommand with the specified black, mid, and white levels, along with
   * the image names for the input and output images.
   *
   * @param black     The black level adjustment value.
   * @param mid       The mid level adjustment value.
   * @param white     The white level adjustment value.
   * @param imageName The name of the image to adjust.
   * @param saveImage The name to save the adjusted image.
   * @throws RuntimeException if any of the level values are not valid integers.
   */
  LevelsAdjustCommand(String black, String mid, String white, String imageName, String saveImage) {
    try {
      this.black = Integer.parseInt(black);
      this.mid = Integer.parseInt(mid);
      this.white = Integer.parseInt(white);
    } catch (NumberFormatException e) {
      throw new RuntimeException("Parameters must be integers");
    }
    this.imageName = imageName;
    this.saveImage = saveImage;
  }

  /**
   * Executes the command to apply level adjustments to the specified image by delegating it to the
   * model's method. This operation is only performed if the model is an instance of ImageModelV2.
   * TypeCasting to ImageModelV2 is done explicitly as instance checking is done on interface field
   * type and by doing this we can use the existing old code and makes no need to change any older
   * version code.
   *
   * @param model The ImageModel instance used to perform the level adjustment operation.
   * @throws IllegalArgumentException if the model is not of type ImageModelV2.
   */
  @Override
  void execute(ImageModel model) {
    if (model instanceof ImageModelV2) {
      ((ImageModelV2) model).applyLevelAdjustment(imageName, saveImage, black, mid, white);
    } else {
      throw new IllegalArgumentException("Invalid Command");
    }
  }

}
