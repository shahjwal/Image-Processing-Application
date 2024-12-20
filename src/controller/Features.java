package controller;

/**
 * This interface defines a set of methods for performing various image manipulation and
 * transformation operations in an image processing system. These operations allow users to load,
 * save, and modify images, as well as apply various effects such as color correction, level
 * adjustments, compression, downscaling, and other filters. The methods in this interface are
 * designed to provide essential image processing capabilities, such as:
 * Loading and saving images from and to specific file paths.
 * Applying transformations like sepia, blur, sharpen, and component-based filtering.
 * Performing image manipulations like flipping, brightness adjustments, and color correction.
 * Supporting advanced image processing techniques such as histogram adjustments, downscaling,
 * and compression.
 * Previewing transformations through split-preview images.
 * This interface serves as a blueprint for the implementation of the image
 * processing functionality and is expected to be implemented by classes responsible
 * for executing the corresponding image transformations.
 */
public interface Features {

  /**
   * Loads an image from a given file path.
   *
   * @param path The path of the image to be loaded.
   */
  void loadImageFromPath(String path);

  /**
   * Loads a default image chosen from the file dropbox.
   */
  void loadImage();

  /**
   * Saves the current image with a specified name.
   *
   * @param saveImage The name to save the image as.
   */
  void saveImage(String saveImage);

  /**
   * Applies a sepia tone effect to the specified image.
   *
   * @param imageName The name of the image to apply the sepia effect to.
   */
  void sepiaImage(String imageName);

  /**
   * Applies a blur effect to the specified image.
   *
   * @param imageName The name of the image to apply the blur effect to.
   */
  void blurImage(String imageName);

  /**
   * Checks if the specified image exists or is valid.
   *
   * @param imageName The name of the image to check.
   * @return {@code true} if the image exists, otherwise {@code false}.
   */
  boolean checkImage(String imageName);

  /**
   * Applies a sharpen effect to the specified image.
   *
   * @param imageName The name of the image to apply the sharpen effect to.
   */
  void sharpenImage(String imageName);

  /**
   * Flips the specified image based on the given flip type.
   *
   * @param flipType  The type of flip to apply (e.g., horizontal, vertical).
   * @param imageName The name of the image to flip.
   */
  void flipImage(String flipType, String imageName);

  /**
   * Applies a color component transformation (e.g., luma, red, blue) to the specified image.
   *
   * @param componentType The type of component transformation (e.g., "luma", "red").
   * @param imageName     The name of the image to apply the component transformation to.
   */
  void componentImage(String componentType, String imageName);

  /**
   * Applies color correction to the specified image.
   *
   * @param imageName The name of the image to apply color correction to.
   */
  void colorCorrectImage(String imageName);

  /**
   * Adjusts the levels of the specified image using black, mid, and white values.
   *
   * @param imageName The name of the image to adjust the levels for.
   * @param black     The black-point value.
   * @param mid       The mid-point value.
   * @param white     The white-point value.
   */
  void levelAdjustImage(String imageName, String black, String mid, String white);

  /**
   * Applies a preview of a split filter to the specified image.
   *
   * @param filterType The type of filter to apply (e.g., "luma", "red").
   * @param imageName  The name of the image to apply the split preview filter to.
   * @param intensity  The intensity value for the filter.
   * @param params     Additional parameters for the filter, if needed.
   */
  void splitPreviewImage(String filterType, String imageName, String intensity, String... params);

  /**
   * Compresses the specified image by a given percentage.
   *
   * @param imageName  The name of the image to compress.
   * @param percentage The compression percentage.
   * @param flag       A flag indicating whether to keep or discard the original image.
   */
  void compressImage(String imageName, String percentage, boolean flag);

  /**
   * Performs downscaling on the specified image, adjusting its height and width.
   *
   * @param imageName The name of the image to downscale.
   * @param height    The target height for the downscaled image.
   * @param width     The target width for the downscaled image.
   * @param flag      A flag indicating whether to keep or discard the original image.
   */
  void downScaling(String imageName, String height, String width, boolean flag);

  /**
   * Brightens the specified image by a given intensity value.
   *
   * @param imageName The name of the image to brighten.
   * @param intensity The intensity value for the brightness.
   * @param flag      A flag indicating whether to keep or discard the original image.
   */
  void brightening(String imageName, String intensity, boolean flag);

}
