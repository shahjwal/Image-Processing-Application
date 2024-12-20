package model;

/**
 * AbstractSpecificOps is an abstract base class that defines the common structure for specific
 * image processing operations, such as generating histograms, compression, or normalization. It
 * holds the common input data (the image matrix in RGB format) and enforces that subclasses
 * implement the `getMatrix()` method to return a processed image matrix. This class provides a base
 * for concrete classes that perform various transformations or analyses on images, ensuring that
 * they all return the processed image data as a 3D array.
 */
abstract class AbstractSpecificOps {

  protected final int[][][] inputRGB;

  /**
   * Constructor to initialize the image processing operation with the input image matrix.
   *
   * @param inputRGB A 3D array representing the input image matrix (height x width x 3 for RGB
   *                 channels).
   */
  AbstractSpecificOps(int[][][] inputRGB) {
    this.inputRGB = inputRGB;
  }

  /**
   * Abstract method to be implemented by subclasses to perform specific image processing
   * operations. The implementation should return the processed image matrix (a 3D array of RGB
   * values).
   *
   * @return A 3D array representing the processed image matrix(height x width x3 for RGB channels).
   */
  abstract int[][][] getMatrix();

}
