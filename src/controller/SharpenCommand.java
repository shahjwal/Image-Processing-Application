package controller;

import model.ImageModel;

/**
 * The following class SharpensCommand that applies a sharpening filter. This takes the name of an
 * image to be processed and then the name to save the resulting image.
 */
class SharpenCommand extends AbstractCommand {

  private final String imageName;
  private final String saveImage;

  /**
   * Constructs a SharpenCommand with the given image name and save image name.
   *
   * @param imageName the name of the image to be processed.
   * @param saveImage the name under which the processed image will be saved.
   */
  SharpenCommand(String imageName, String saveImage) {
    this.imageName = imageName;
    this.saveImage = saveImage;
  }

  /**
   * Executes the sharpening filter operation on the image by delegating it to the respective
   * model's method.
   *
   * @param model the ImageModel instance to perform operations on.
   */
  @Override
  void execute(ImageModel model) {
    model.applySharpen(this.imageName, this.saveImage);
  }

}