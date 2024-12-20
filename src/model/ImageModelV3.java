package model;

/**
 * The ImageModelV3 interface extends the ImageModelV2 interface and defines additional methods for
 * advanced image processing functionalities, such as downscaling and masking operations. It
 * represents the contract for a model that supports these functionalities, ensuring that
 * implementations provide the necessary methods for performing these operations on images.
 */
public interface ImageModelV3 extends ImageModelV2 {

  /**
   * Applies downscaling to the specified image. This method resizes the image to the given height
   * and width, modifying its resolution.
   *
   * @param imageName The name of the input image to be downscaled.
   * @param saveImage The name of the output image where the downscaled image will be saved.
   * @param height    The target height for the downscaled image.
   * @param width     The target width for the downscaled image.
   */
  void applyDownScaling(String imageName, String saveImage, int height, int width);

  /**
   * Applies a masking operation to the specified image using the provided mask image. This method
   * allows for applying various types of mask operations (e.g., blur, sharpen) based on the
   * operation type to the input image.
   *
   * @param operationType The type of operation to be applied (e.g., "blur", "sharpen").
   * @param imageName     The name of the image to which the mask will be applied.
   * @param maskImage     The name of the mask image used for the operation.
   * @param saveImage     The name of the output image where the resulting image with the mask
   *                      applied will be saved.
   */
  void applyMasking(String operationType, String imageName, String maskImage, String saveImage);

}