package controller;

import model.ImageModel;

/**
 * The SepiaCommand class is responsible for applying a sepia filter to an image.It takes the name
 * of the image to be processed and the name to save the resulting image.
 */
class SepiaCommand extends AbstractCommand {

  private final String imageName;
  private final String saveImage;

  /**
   * Constructs a SepiaCommand with the given image name and save image name.
   *
   * @param imageName the name of the image to be processed.
   * @param saveImage the name under which the processed image will be saved.
   */
  SepiaCommand(String imageName, String saveImage) {
    this.imageName = imageName;
    this.saveImage = saveImage;
  }

  /**
   * Executes the sepia filter operation on the image by delegating it to the respective model's
   * method.
   *
   * @param model the ImageModel instance to perform operations on.
   */
  @Override
  void execute(ImageModel model) {
    model.applySepia(this.imageName, this.saveImage);
  }
}
