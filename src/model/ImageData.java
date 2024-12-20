package model;

/**
 * The ImageData class represents an image with its associated metadata, including its name,
 * dimensions (height and width), and its RGB values. The image is stored as a 3D array where the
 * first two dimensions represent the height and width of the image, and the third dimension
 * contains the RGB values for each pixel. This class is used to encapsulate the essential
 * properties of an image and provide access to the image's data through getter methods. The class
 * ensures that the image's data is encapsulated and cannot be modified externally.
 */
class ImageData {

  private final String name;
  private final int height;
  private final int width;
  private final int channels;
  private final int[][][] rgb;

  /**
   * Constructs an ImageData object with the specified name, dimensions, and RGB data.
   *
   * @param name   the name of the image.
   * @param height the height of the image in pixels.
   * @param width  the width of the image in pixels.
   * @param rgb    the 3D array representing the image's RGB values.
   */
  ImageData(String name, int height, int width, int channels, int[][][] rgb) {
    this.name = name;
    this.height = height;
    this.width = width;
    this.channels = channels;
    this.rgb = rgb.clone();
  }

  /**
   * Returns the height of the image.
   *
   * @return the height of the image in pixels.
   */
  int getHeight() {
    return height;
  }

  /**
   * Returns the width of the image.
   *
   * @return the width of the image in pixels.
   */
  int getWidth() {
    return width;
  }

  /**
   * Returns the number of channels of the image.
   *
   * @return the number of channels.
   */
  int getChannels() {
    return channels;
  }

  /**
   * Returns the name of the image.
   *
   * @return the name associated with the image.
   */
  String getName() {
    return this.name;
  }

  /**
   * Returns the RGB data of the image.
   *
   * @return the 3D array representing the image's RGB values.
   */
  int[][][] getRgb() {
    return this.rgb;
  }

}
