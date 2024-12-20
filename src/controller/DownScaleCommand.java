package controller;

import model.ImageModel;
import model.ImageModelV3;

/**
 * The DownScaleCommand class encapsulates the action of applying downscaling to a specified image.
 * It extends the AbstractCommand class, following the Command design pattern, which allows for
 * encapsulating requests as objects. This command is responsible for invoking the downscaling
 * functionality provided by the ImageModelV3 class. It ensures that the correct model version is
 * used and handles any discrepancies by throwing appropriate exceptions.
 */
class DownScaleCommand extends AbstractCommand {

  private final String imageName;
  private final String saveImage;
  private final int height;
  private final int width;

  /**
   * Constructs a DownScaleCommand with the specified image names and scaling dimensions.
   *
   * @param imageName The name of the input image to be downscaled.
   * @param saveImage The name of the output image where the downscaled image will be saved.
   * @param height    The desired height for the downscaled image.
   * @param width     The desired width for the downscaled image.
   * @throws IllegalArgumentException if the height or width is not a valid integer.
   */
  DownScaleCommand(String imageName, String saveImage, String height, String width) {
    this.imageName = imageName;
    this.saveImage = saveImage;
    try {
      this.height = Integer.parseInt(height);
      this.width = Integer.parseInt(width);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Height and width should be integers!");
    }
  }

  /**
   * Executes the downscaling command on the provided ImageModel. This method checks if the provided
   * model is an instance of ImageModelV3, which contains the downscaling functionality. If so, it
   * invokes the downscaling method with the specified image names and scaling dimensions. If the
   * model is not an instance of ImageModelV3, it throws an IllegalArgumentException to indicate
   * that the command is invalid for the given model type.
   *
   * @param model The ImageModel instance on which the command is to be executed.
   * @throws IllegalArgumentException if the provided model does not support downscaling.
   */
  @Override
  void execute(ImageModel model) {
    if (model instanceof ImageModelV3) {
      ((ImageModelV3) model).applyDownScaling(imageName, saveImage, height, width);
    } else {
      throw new IllegalArgumentException("Invalid Command");
    }
  }

}
