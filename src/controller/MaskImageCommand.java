package controller;

import model.ImageModel;
import model.ImageModelV3;

/**
 * The MaskImageCommand class encapsulates the action of applying a mask to a specified image using
 * a mask image. It extends the AbstractCommand class, following the Command design pattern to
 * encapsulate requests as objects. This command is responsible for invoking the masking
 * functionality provided by the ImageModelV3 class. It ensures that the correct model version is
 * used and handles any discrepancies by throwing appropriate exceptions.
 */
class MaskImageCommand extends AbstractCommand {

  private final String operationType;
  private final String imageName;
  private final String maskImage;
  private final String saveImage;

  /**
   * Constructs a MaskImageCommand with the specified operation, image names, and mask image.
   *
   * @param operationType The type of masking operation (e.g., "blur", "sharpen", etc.).
   * @param imageName     The name of the input image to which the mask will be applied.
   * @param maskImageName The name of the mask image that will be used.
   * @param saveImageName The name of the output image where the resulting image with the mask
   *                      applied will be saved.
   */
  MaskImageCommand(String operationType, String imageName, String maskImageName,
      String saveImageName) {
    this.operationType = operationType;
    this.imageName = imageName;
    this.maskImage = maskImageName;
    this.saveImage = saveImageName;
  }

  /**
   * Executes the masking command on the provided ImageModel. This method checks if the provided
   * model is an instance of ImageModelV3, which contains the masking functionality. If so, it
   * invokes the masking method with the specified operation type, image names, and mask image. If
   * the model is not an instance of ImageModelV3, it throws an IllegalArgumentException to indicate
   * that the command is invalid for the given model type.
   *
   * @param model The ImageModel instance on which the command is to be executed.
   * @throws IllegalArgumentException if the provided model does not support image masking.
   */
  @Override
  void execute(ImageModel model) {
    if (model instanceof ImageModelV3) {
      ((ImageModelV3) model).applyMasking(operationType, imageName, maskImage, saveImage);
    } else {
      throw new IllegalArgumentException("Invalid Command");
    }
  }

}
