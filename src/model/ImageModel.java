package model;

/**
 * The ImageModel interface provides an abstraction for manipulating images. It defines various
 * operations that can be performed on images, including loading, saving, and applying image
 * processing transformations such as brightening, blurring, flipping, and color adjustments.
 * Implementations of this interface are responsible for storing image data and applying the
 * specified transformations. Each image is represented as a 3D array of RGB values, where the first
 * two dimensions represent the image's height and width, and the third dimension represents the
 * color channels (red, green, and blue). These operations can be used to create a wide range of
 * effects on images, such as enhancing brightness, applying blur or sharpen filters, and working
 * with individual color components. Images can be loaded from an external source, modified using
 * these operations, and then saved or further manipulated.
 */
public interface ImageModel {

  /**
   * Loads an image into the model.
   *
   * @param imageName the name to associate with the image.
   * @param rgb       the 3D array representing the image's RGB values.
   */
  void loadImage(String imageName, int[][][] rgb);

  /**
   * Saves the RGB data of an image in the model.
   *
   * @param imageName the name of the image to save.
   * @return the 3D array representing the image's RGB values.
   */
  int[][][] saveImage(String imageName);

  /**
   * Applies a brightness or darkening effect to the specified image.
   *
   * @param intensity the value by which to brighten (positive) or darken (negative) the image.
   * @param imageName the name of the image to modify.
   * @param saveImage the name under which the modified image will be saved.
   */
  void applyBrighten(int intensity, String imageName, String saveImage);

  /**
   * Applies a blur effect to the specified image.
   *
   * @param imageName the name of the image to blur.
   * @param saveImage the name under which the blurred image will be saved.
   */
  void applyBlur(String imageName, String saveImage);

  /**
   * Applies a sharpening effect to the specified image.
   *
   * @param imageName the name of the image to sharpen.
   * @param saveImage the name under which the sharpened image will be saved.
   */
  void applySharpen(String imageName, String saveImage);

  /**
   * Applies a sepia tone effect to the specified image.
   *
   * @param imageName the name of the image to modify.
   * @param saveImage the name under which the sepia-toned image will be saved.
   */
  void applySepia(String imageName, String saveImage);

  /**
   * Flips the specified image either horizontally or vertically.
   *
   * @param imageName the name of the image to flip.
   * @param saveImage the name under which the flipped image will be saved.
   * @param flip      the direction of the flip (true for vertical, false for horizontal).
   */
  void applyFlip(String imageName, String saveImage, boolean flip);

  /**
   * Extracts a specific color component from the image.
   *
   * @param imageName the name of the image to modify.
   * @param saveImage the name under which the modified image will be saved.
   * @param type      the color component to extract.
   */
  void applyComponent(String imageName, String saveImage, String type);

  /**
   * Splits the specified image into its red, green, and blue components, saving each one as a
   * separate image.
   *
   * @param imageName  the name of the image to split.
   * @param redImage   the name under which the red component image will be saved.
   * @param greenImage the name under which the green component image will be saved.
   * @param blueImage  the name under which the blue component image will be saved.
   */
  void applySplit(String imageName, String redImage, String greenImage, String blueImage);

  /**
   * Combines separate red, green, and blue component images into a single image.
   *
   * @param saveImage  the name under which the combined image will be saved.
   * @param redImage   the name of the red component image.
   * @param greenImage the name of the green component image.
   * @param blueImage  the name of the blue component image.
   */
  void applyCombine(String saveImage, String redImage, String greenImage, String blueImage);

}