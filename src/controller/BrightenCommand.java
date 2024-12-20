package controller;

import model.ImageModel;

/**
 * BrightenCommand is an implementation of the command pattern that encapsulates the action of
 * applying a brightness adjustment to a specified image. It allows for specifying the intensity of
 * the brightness change and saves the modified image under a new name. This class will delegate the
 * requirements to the specific model's method.
 */
class BrightenCommand extends AbstractCommand {

  private final String imageName;
  private final String saveImage;
  private final int intensity;

  /**
   * Constructs a BrightenCommand with the specified intensity, image name, and save name.
   *
   * @param intensity The intensity of the brightness adjustment.
   * @param imageName The name of the image to be brightened.
   * @param saveImage The name to associate with the saved brightened image.
   */
  BrightenCommand(String intensity, String imageName, String saveImage) {
    this.imageName = imageName;
    this.saveImage = saveImage;
    try {
      this.intensity = Integer.parseInt(intensity);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Please provide a valid intensity value!");
    }
  }

  /**
   * Executes the brightness adjustment command by delegating it to the model's `applyBrighten`
   * method. This method utilizes the specified {@code ImageModel} instance to perform the
   * brightness adjustment. The original image is located by {@code imageName}, and the adjusted
   * image is saved with the name {@code saveImage}.
   *
   * @param model The ImageModel instance used to perform the brighten operation.
   */
  @Override
  void execute(ImageModel model) {
    model.applyBrighten(this.intensity, this.imageName, this.saveImage);
  }


}
