package model;

import static utils.MyUtils.clamp;
import static utils.MyUtils.cloneMatrix;

import java.util.function.Function;

/**
 * A class that provides image manipulation operations, including downscaling and masking. This
 * class extends ImageOperationsV2 and overrides or adds new image processing functions. The
 * operations in this class involve manipulation of pixel values through bilinear interpolation for
 * resizing images and using a mask for selectively applying operations to certain areas of an
 * image.
 */
public class ImageOperationsV3 extends ImageOperationsV2 {

  /**
   * Calculates the surrounding points for bi-linear interpolation based on the new coordinates. The
   * surrounding points are the top-left, top-right, bottom-left, and bottom-right points in the
   * original image corresponding to the target coordinates.
   *
   * @param newX      The X coordinate in the downscaled image.
   * @param newY      The Y coordinate in the downscaled image.
   * @param heightRGB The height of the original image.
   * @param widthRGB  The width of the original image.
   * @return A 2D array containing the coordinates of the four surrounding points.
   */
  private int[][] calculateSurroundingPoints(double newX, double newY, int heightRGB,
      int widthRGB) {
    int aX = Math.min((int) Math.floor(newX), heightRGB - 1);
    int aY = Math.min((int) Math.floor(newY), widthRGB - 1);
    int bX = Math.min((int) Math.ceil(newX), heightRGB - 1);
    int cY = Math.min((int) Math.ceil(newY), widthRGB - 1);

    return new int[][]{
        {aX, aY},
        {bX, aY},
        {aX, cY},
        {bX, cY}
    };
  }

  /**
   * Downscales an image to the specified height and width using bi-linear interpolation. The
   * function adjusts the pixel values based on surrounding pixels in the original image to generate
   * a smaller image with smoother transitions.
   *
   * @param inputImage The original image to be downscaled.
   * @param height     The target height for the downscaled image.
   * @param width      The target width for the downscaled image.
   * @return A 3D array representing the downscaled image in RGB format.
   * @throws IllegalArgumentException if the provided height or width is invalid (i.e., non-positive
   *                                  or larger than the original image dimensions).
   */
  int[][][] downscaleImage(ImageData inputImage, int height, int width) {
    int[][][] inputRGB = cloneMatrix(inputImage.getRgb());
    int heightRGB = inputImage.getHeight();
    int widthRGB = inputImage.getWidth();
    int channels = inputImage.getChannels();

    if (height <= 0 || width <= 0 || height > heightRGB || width > widthRGB) {
      throw new IllegalArgumentException("Height and width should be within 0 and "
          + "image height and image width respectively");
    }

    double ratioHeight = (double) heightRGB / height;
    double ratioWidth = (double) widthRGB / width;

    int[][][] outputRGB = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        double newX = i * ratioHeight;
        double newY = j * ratioWidth;

        int[][] surroundingPoints = calculateSurroundingPoints(newX, newY, heightRGB, widthRGB);
        int aX = surroundingPoints[0][0];
        int aY = surroundingPoints[0][1];
        int bX = surroundingPoints[1][0];
        int bY = surroundingPoints[1][1];
        int cX = surroundingPoints[2][0];
        int cY = surroundingPoints[2][1];
        int dX = surroundingPoints[3][0];
        int dY = surroundingPoints[3][1];

        double xFraction = newX - aX;
        double yFraction = newY - aY;

        for (int channel = 0; channel < channels; channel++) {
          double n =
              inputRGB[aX][aY][channel] * (1 - xFraction) + inputRGB[bX][bY][channel] * xFraction;
          double m =
              inputRGB[cX][cY][channel] * (1 - xFraction) + inputRGB[dX][dY][channel] * xFraction;

          int interpolatedValue = (int) Math.round(n * (1 - yFraction) + m * yFraction);
          interpolatedValue = clamp(interpolatedValue);
          outputRGB[i][j][channel] = interpolatedValue;
        }
      }
    }
    return outputRGB;
  }

  /**
   * Applies an image operation (such as blur, sharpen, etc.) to an image, but only to the parts of
   * the image specified by a mask. The mask is used to determine which pixels of the input image
   * are affected by the operation.
   *
   * @param operationType The type of operation to apply (e.g., blur, sharpen).
   * @param inputImage    The original image to apply the operation to.
   * @param maskedImage   A mask image that defines which areas of the input image will be
   *                      modified.
   * @return A 3D array representing the image after the operation has been applied to masked area.
   * @throws IllegalArgumentException if the dimensions of the input image and mask image do not
   *                                  match.
   */
  int[][][] masking(String operationType, ImageData inputImage, ImageData maskedImage) {
    int[][][] inputRGB = cloneMatrix(inputImage.getRgb());
    int heightRGB = inputImage.getHeight();
    int widthRGB = inputImage.getWidth();
    int channels = inputImage.getChannels();

    int[][][] maskedRGB = cloneMatrix(maskedImage.getRgb());
    int maskHeight = maskedImage.getHeight();
    int maskWidth = maskedImage.getWidth();
    int maskChannels = maskedImage.getChannels();

    if (heightRGB != maskHeight || widthRGB != maskWidth || channels != maskChannels) {
      throw new IllegalArgumentException(
          "Image dimensions and the mask image dimensions are different");
    }

    Function<ImageData, int[][][]> operation = getImageDataFunction(operationType, new int[0]);

    int[][][] outputRGB = cloneMatrix(inputRGB);
    int[][][] operatedRGB = operation.apply(inputImage);

    for (int i = 0; i < heightRGB; i++) {
      for (int j = 0; j < widthRGB; j++) {
        if (maskedRGB[i][j][0] == 0 &&
            maskedRGB[i][j][1] == 0 && maskedRGB[i][j][2] == 0) {
          outputRGB[i][j][0] = operatedRGB[i][j][0];
          outputRGB[i][j][1] = operatedRGB[i][j][1];
          outputRGB[i][j][2] = operatedRGB[i][j][2];
        }
      }
    }
    return outputRGB;
  }

}
