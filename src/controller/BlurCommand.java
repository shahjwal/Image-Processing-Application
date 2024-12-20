package controller;

import model.ImageModel;


/**
 * BlurCommand is an implementation of the command pattern that encapsulates the action of applying
 * a blur effect to a specified image. It retrieves the image by its name and saves the blurred
 * result under a new name. This class will delegate the requirements to the specific model's
 * method.
 */
class BlurCommand extends AbstractCommand {

  private final String imageName;
  private final String saveImage;

  /**
   * Constructs a BlurCommand with the specified parameters.
   *
   * @param imageName The name of the image to be blurred.
   * @param saveImage The name to associate with the saved blurred image.
   */
  BlurCommand(String imageName, String saveImage) {
    this.imageName = imageName;
    this.saveImage = saveImage;
  }

  /**
   * Executes the command to apply a blur effect to the specified image. This method delegates the
   * blur operation to the provided {@code ImageModel} instance by invoking its {@code applyBlur}
   * method. The original image is identified by {@code imageName}, and the blurred result is saved
   * with the name {@code saveImage}. If the model does not support the specified blur operation, an
   * exception should be handled by the calling code to ensure a smooth user experience.
   *
   * @param model The ImageModel instance used to perform the blur operation.
   * @throws UnsupportedOperationException if the blur operation is not supported by the model.
   */
  @Override
  void execute(ImageModel model) {
    model.applyBlur(this.imageName, this.saveImage);
  }

}
