package utils;

/**
 * Utility class providing common helper functions for image processing. The myUtils class contains
 * static methods that facilitate operations frequently required in image processing, such as
 * creating deep copies of RGB matrices and clamping values within a specified range. These methods
 * are designed to be reusable across different parts of the image processing application.
 */
public class MyUtils {

  /**
   * Creates a deep copy of a 3D RGB matrix. This method performs a deep copy on a 3-dimensional
   * matrix where the dimensions represent the height, width, and RGB channels of an image,
   * respectively. Each element in the input matrix is copied to a new matrix, ensuring changes to
   * the returned matrix do not affect the original.
   *
   * @param inputMatrix the 3D RGB matrix to clone, with dimensions [height][width][3] where 3
   *                    represents RGB channels.
   * @return a deep copy of the input matrix, preserving the original values in a new matrix.
   */
  public static int[][][] cloneMatrix(int[][][] inputMatrix) {
    int height = inputMatrix.length;
    int width = inputMatrix[0].length;
    int[][][] clonedMatrix = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        clonedMatrix[i][j][0] = inputMatrix[i][j][0];
        clonedMatrix[i][j][1] = inputMatrix[i][j][1];
        clonedMatrix[i][j][2] = inputMatrix[i][j][2];
      }
    }
    return clonedMatrix;
  }

  /**
   * Clamps an integer value to the range [0, 255]. This method ensures that any integer value
   * passed to it falls within the valid range for RGB color channels, which is between 0 and 255.
   * Values below 0 are set to 0, and values above 255 are set to 255, preserving valid RGB values
   * and avoiding overflow issues in image processing.
   *
   * @param value the integer value to clamp.
   * @return the clamped value within the range [0, 255].
   */
  public static int clamp(int value) {
    return Math.max(0, Math.min(255, value));
  }

  /**
   * Calculates the frequency of pixel intensity values for a specific color channel (R, G, or B) in
   * a given RGB image matrix. This method iterates through the entire matrix, which represents an
   * image in the form of a 3D array [height][width][3], where each element contains RGB values for
   * a pixel. It counts how many times each possible pixel intensity (0 to 255) appears for the
   * specified color channel.
   *
   * @param matrix  the 3D RGB matrix representing the image, with dimensions [height][width][3],
   *                where the third dimension holds the RGB values.
   * @param height  the number of rows (height) of pixels in the image.
   * @param width   the number of columns (width) of pixels in the image.
   * @param channel the index of the color channel to calculate frequency for (0 for Red, 1 for
   *                Green, 2 for Blue).
   * @return        an array of integers.
   */
  public static int[] calculateFrequency(int[][][] matrix, int height, int width, int channel) {
    int[] frequency = new int[256];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        frequency[matrix[i][j][channel]]++;
      }
    }
    return frequency;
  }

  /**
   * Extracts a specific color channel (Red, Green, or Blue) from a 3D RGB matrix. This method
   * creates a 2D array representing the specified color channel (R, G, or B) by iterating over the
   * entire 3D RGB matrix and copying the corresponding channel value for each pixel. The returned
   * 2D array contains the pixel intensities for the selected channel, while discarding the other
   * color channels.
   *
   * @param matrix  the 3D RGB matrix representing the image, with dimensions [height][width][3],
   *                where each element holds the RGB values for a pixel.
   * @param height  the number of rows (height) of pixels in the image.
   * @param width   the number of columns (width) of pixels in the image.
   * @param channel the index of the color channel to extract (0 for Red, 1 for Green, 2 for Blue).
   * @return        a 2D array.
   * @throws IllegalArgumentException if the channel index is not 0 (Red), 1 (Green), or 2 (Blue).
   */
  public static int[][] extractChannel(int[][][] matrix, int height, int width, int channel) {
    if (channel < 0 || channel > 2) {
      throw new IllegalArgumentException(
          "Invalid channel. Must be 0 (Red), 1 (Green), or 2 (Blue).");
    }
    int[][] extractedChannel = new int[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        extractedChannel[i][j] = matrix[i][j][channel];
      }
    }

    return extractedChannel;
  }
}
