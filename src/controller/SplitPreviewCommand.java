package controller;

import java.util.Objects;
import model.ImageModel;
import model.ImageModelV2;

/**
 * SplitPreviewCommand is an implementation of the command pattern that applies a preview operation
 * (such as split preview of an image) based on the specified operation name, percentage, and
 * parameters. This command will delegate the operation to the appropriate method in the
 * ImageModelV2 class.
 */
class SplitPreviewCommand extends AbstractCommand {

  private final String imageName;
  private final String saveImage;
  private final String operationName;
  private final double percentage;
  private final int[] params;

  /**
   * Constructs a SplitPreviewCommand with the specified operation name, image names, split type,
   * percentage, and additional parameters for the operation.
   *
   * @param operationName The name of the operation (e.g., "split").
   * @param imageName     The name of the image to apply the operation to.
   * @param saveImage     The name to save the resulting image.
   * @param split         The type of operation to be applied, should be "split".
   * @param params        Additional parameters for the operation (e.g., split positions).
   * @throws IllegalArgumentException if the operation name is invalid or if parameters are not
   *                                  formatted correctly.
   */
  SplitPreviewCommand(String operationName, String imageName, String saveImage, String split,
      String... params) {
    if (!Objects.equals(split, "split")) {
      throw new IllegalArgumentException("Invalid Command");
    }
    this.operationName = operationName;
    this.imageName = imageName;
    this.saveImage = saveImage;
    try {
      this.percentage = Double.parseDouble(params[0]);
      if (this.percentage < 0.0 || this.percentage > 100.0) {
        throw new IllegalArgumentException("Percentage must be between 0 and 100");
      }
      this.params = new int[params.length - 1];
      for (int i = 1; i < params.length; i++) {
        this.params[i - 1] = Integer.parseInt(params[i]);
      }
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Parameters must be integers", e);
    }
  }

  /**
   * Executes the command to apply the split preview operation by delegating the operation to the
   * ImageModelV2 instance. If the model is not of type ImageModelV2, an exception is thrown.
   * TypeCasting to ImageModelV2 is done explicitly as instance checking is done on interface field
   * type and by doing this we can use the existing old code and makes no need to change any older
   * version code.
   *
   * @param model The ImageModel instance used to apply the split preview operation.
   * @throws IllegalArgumentException if the model is not of type ImageModelV2.
   */
  @Override
  void execute(ImageModel model) {
    if (model instanceof ImageModelV2) {
      ((ImageModelV2) model).applySplitPreview(operationName, imageName, saveImage, percentage,
          params);
    } else {
      throw new IllegalArgumentException("Invalid Command");
    }
  }
}
