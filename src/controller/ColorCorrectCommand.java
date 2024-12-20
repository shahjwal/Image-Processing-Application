package controller;

import model.ImageModel;
import model.ImageModelV2;

/**
 * The ColorCorrectCommand class encapsulates the action of applying color correction to a specified
 * image. It extends the AbstractCommand class, adhering to the Command design pattern, which allows
 * for the encapsulation of requests as objects. This command is responsible for invoking the color
 * correction functionality provided by the ImageModelV2 class. It ensures that the correct model
 * version is used and handles any discrepancies by throwing appropriate exceptions.
 */
class ColorCorrectCommand extends AbstractCommand {

  private final String imageName;
  private final String saveImage;

  /**
   * Constructs a ColorCorrectCommand with the specified image names.
   *
   * @param imageName The name of the input image to be color corrected.
   * @param saveImage The name of the output image where the corrected image will be saved.
   */
  ColorCorrectCommand(String imageName, String saveImage) {
    this.imageName = imageName;
    this.saveImage = saveImage;
  }

  /**
   * Executes the color correction command on the provided ImageModel. This method checks if the
   * provided model is an instance of ImageModelV2, which contains the color correction
   * functionality. If so, it invokes the {@code applyColorCorrection} method with the specified
   * image names. If the model is not an instance of ImageModelV2, it throws an
   * IllegalArgumentException to indicate that the command is invalid for the given model type.
   * TypeCasting to ImageModelV2 is done explicitly as instance checking is done on interface field
   * type and by doing this we can use the existing old code and makes no need to change any older
   * version code.
   *
   * @param model The ImageModel instance on which the command is to be executed.
   * @throws IllegalArgumentException if the provided model does not support color correction.
   */
  @Override
  void execute(ImageModel model) {
    if (model instanceof ImageModelV2) {
      ((ImageModelV2) model).applyColorCorrection(imageName, saveImage);
    } else {
      throw new IllegalArgumentException("Invalid Command");
    }
  }

}
