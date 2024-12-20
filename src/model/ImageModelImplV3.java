package model;

/**
 * The ImageModelImplV3 class extends the ImageModelImplV2 class and provides additional image
 * processing operations, such as downscaling and masking. It utilizes the ImageOperationsV3 class
 * for performing these operations, which allows for more advanced image manipulations.
 */
public class ImageModelImplV3 extends ImageModelImplV2 implements ImageModelV3 {

  private final ImageOperationsV3 imageOpsV3;

  /**
   * Constructs an ImageModelImplV3 instance, initializing the ImageOperationsV3 object used for the
   * advanced image processing operations like downscaling and masking. Inherits image collection
   * and operations from the parent class.
   */
  public ImageModelImplV3() {
    super();
    imageOpsV3 = new ImageOperationsV3();
  }

  /**
   * Applies downscaling to the specified image and saves the result. The downscaling operation is
   * delegated to the ImageOperationsV3 class.
   *
   * @param imageName The name of the image to downscale.
   * @param saveImage The name under which the downscaled image is saved.
   * @param height    The target height for the downscaled image.
   * @param width     The target width for the downscaled image.
   */
  @Override
  public void applyDownScaling(String imageName, String saveImage, int height, int width) {
    ImageData inputImage = this.getImage(imageName);
    int[][][] newRGB = imageOpsV3.downscaleImage(inputImage, height, width);
    this.addImage(saveImage, newRGB);
  }

  /**
   * Applies a masking operation to the specified image using the provided mask image and saves the
   * result. The masking operation is delegated to the ImageOperationsV3 class.
   *
   * @param operationType The type of masking operation to apply (e.g., "blur", "sharpen").
   * @param imageName     The name of the image to which the mask will be applied.
   * @param maskImage     The name of the mask image.
   * @param saveImage     The name under which the masked image will be saved.
   */
  @Override
  public void applyMasking(String operationType, String imageName, String maskImage,
      String saveImage) {
    ImageData inputImage = this.getImage(imageName);
    ImageData maskedImage = this.getImage(maskImage);
    int[][][] newRGB = imageOpsV3.masking(operationType, inputImage, maskedImage);
    this.addImage(saveImage, newRGB);
  }

}
