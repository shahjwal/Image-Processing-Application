package model;

/**
 * The ImageModelV2 interface extends the ImageModel interface, adding advanced image processing
 * capabilities such as image compression, histogram generation, level adjustments, color
 * correction, and split previews. These additional features provide more control over image
 * quality, color, and tonal adjustments, making the interface suitable for more refined image
 * editing tasks. Implementations of this interface are expected to support these enhanced
 * transformations while maintaining the core functionality defined in the base ImageModel.
 */
public interface ImageModelV2 extends ImageModel {

  /**
   * Compresses the specified image to a given percentage.
   *
   * @param percentage the compression percentage (0-100), where 0 is no compression and 100 is
   *                   maximum compression.
   * @param imageName  the name of the image to compress.
   * @param saveImage  the name under which the compressed image will be saved.
   */
  void applyCompress(int percentage, String imageName, String saveImage);

  /**
   * Generates a histogram for the RGB components of the specified image.
   *
   * @param imageName the name of the image for which the histogram will be generated.
   * @param saveImage the name under which the histogram data will be saved or represented.
   */
  void applyHistogram(String imageName, String saveImage);

  /**
   * Adjusts the levels of the specified image based on the provided black, mid, and white tone
   * values.
   *
   * @param imageName the name of the image to adjust.
   * @param saveImage the name under which the adjusted image will be saved.
   * @param black     the black tone level, representing the darkest parts of the image.
   * @param mid       the mid-tone level, balancing the image’s middle values.
   * @param white     the white tone level, affecting the brightest areas.
   */
  void applyLevelAdjustment(String imageName, String saveImage, int black, int mid, int white);

  /**
   * Applies color correction to the specified image to enhance its color balance.
   *
   * @param imageName the name of the image to correct.
   * @param saveImage the name under which the color-corrected image will be saved.
   */
  void applyColorCorrection(String imageName, String saveImage);

  /**
   * Previews an image operation with a specified split percentage, allowing partial application of
   * the effect for comparison purposes.
   *
   * @param operationName the name of the operation to preview (e.g., blur, brighten).
   * @param imageName     the name of the image to preview.
   * @param saveImage     the name under which the previewed image will be saved.
   * @param percentage    the percentage of the effect to apply for preview (0-100).
   * @param params        additional parameters for specific operations, if needed.
   */
  void applySplitPreview(String operationName, String imageName, String saveImage,
      double percentage, int[] params);

}
