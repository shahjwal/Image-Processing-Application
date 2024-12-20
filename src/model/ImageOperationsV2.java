package model;


import static utils.MyUtils.calculateFrequency;
import static utils.MyUtils.clamp;
import static utils.MyUtils.cloneMatrix;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * ImageOperationsV2 extends the base ImageOperations class to include advanced image processing
 * operations, such as image compression, histogram normalization, level adjustment, and color
 * correction. It also provides a split preview function for applying operations to a portion of the
 * image.
 */
public class ImageOperationsV2 extends ImageOperations {

  /**
   * Compresses the input image by reducing its pixel values according to a specified compression
   * percentage. Clamps each color channel value to ensure it remains within the valid range after
   * compression.
   *
   * @param inputImage The image to be compressed.
   * @param percentage The compression percentage (0-100).
   * @return A 3D array representing the compressed image.
   */
  int[][][] imageCompression(ImageData inputImage, int percentage) {
    int[][][] rgb = cloneMatrix(inputImage.getRgb());
    int[][][] outputRGB = (new ImageCompression(rgb, percentage)).getMatrix();
    for (int i = 0; i < outputRGB.length; i++) {
      for (int j = 0; j < outputRGB[0].length; j++) {
        for (int k = 0; k < outputRGB[0][0].length; k++) {
          outputRGB[i][j][k] = clamp(outputRGB[i][j][k]);
        }
      }
    }
    return outputRGB;
  }

  /**
   * Generates a normalized histogram representation of the input image.
   *
   * @param inputImage The image for which the histogram is generated.
   * @return A 3D array representing the normalized histogram.
   */
  int[][][] imageHistogram(ImageData inputImage) {
    int[][][] rgb = cloneMatrix(inputImage.getRgb());
    return (new NormalizedHistogram(rgb)).getMatrix();
  }

  /**
   * Calculates curve coefficients for level adjustment to create a mapping function from input
   * values to adjusted values, based on black, mid, and white points.
   *
   * @param black The black point.
   * @param mid   The mid-tone point.
   * @param white The white point.
   * @return An array of coefficients for the quadratic function.
   */
  private double[] curveCoefficients(int black, int mid, int white) {
    double a = (
        (Math.pow(black, 2) * (mid - white)) - (black * (Math.pow(mid, 2) - Math.pow(white, 2))) + (
            white * Math.pow(mid, 2)) - (mid * Math.pow(white, 2)));

    double aA = (-1 * black * (128 - 255)) + 128 * white - 255 * mid;

    double aB = ((Math.pow(black, 2) * (128 - 255)) + (255 * Math.pow(mid, 2)) - (128 * Math.pow(
        white, 2)));

    double aC = ((Math.pow(black, 2) * (255 * mid - 128 * white)) - (black * (
        (255 * Math.pow(mid, 2)) - (128 * Math.pow(white, 2)))));
    return new double[]{aA / a, aB / a, aC / a};
  }

  /**
   * Adjusts the levels of an image based on specified black, mid, and white points, applying the
   * calculated curve coefficients to each pixel's RGB channels.
   *
   * @param inputImage The image to adjust.
   * @param black      The black point.
   * @param mid        The mid-tone point.
   * @param white      The white point.
   * @return A 3D array representing the level-adjusted image.
   */
  int[][][] levelAdjustment(ImageData inputImage, int black, int mid, int white) {

    if (black < 0 || mid < 0 || white < 0) {
      throw new IllegalArgumentException("Level Adjustment parameters are invalid");
    }
    if (black > 255 || mid > 255 || white > 255) {
      throw new IllegalArgumentException("Level Adjustment parameters are invalid");
    }
    if (!(black < mid && mid < white)) {
      throw new IllegalArgumentException("Level Adjustment parameters are invalid");
    }

    int[][][] inputRGB = cloneMatrix(inputImage.getRgb());
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();
    int channels = inputImage.getChannels();
    double[] coefficients = curveCoefficients(black, mid, white);

    int[][][] outputRGB = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int k = 0; k < channels; k++) {
          int value = inputRGB[i][j][k];
          outputRGB[i][j][k] = clamp(
              (int) ((coefficients[0] * value * value) + (coefficients[1] * value)
                  + coefficients[2]));
        }
      }
    }
    return outputRGB;
  }

  /**
   * Adjusts a color value to correct color imbalances.
   *
   * @param originalValue The original color value.
   * @param peakValue     The peak color value from the histogram.
   * @param averagePeak   The average peak value across color channels.
   * @return The adjusted color value.
   */
  private int adjustValue(int originalValue, int peakValue, int averagePeak) {
    int adjustedValue = originalValue + (averagePeak - peakValue);
    return clamp(adjustedValue);
  }

  /**
   * Identifies the index of the peak frequency within a color channel histogram.
   *
   * @param frequencies The frequency distribution of a color channel.
   * @return The index of the peak frequency.
   */
  private int findPeakIndex(int[] frequencies) {
    int peak = 0;
    int peakIndex = 0;
    for (int i = 10; i <= 245; i++) {
      if (frequencies[i] > peak) {
        peak = frequencies[i];
        peakIndex = i;
      }
    }
    return peakIndex;
  }

  /**
   * Applies color correction to the image based on histogram peaks, adjusting colors towards the
   * average peak value for balanced color distribution.
   *
   * @param inputImage The image to correct.
   * @return A 3D array representing the color-corrected image.
   */
  int[][][] colorCorrection(ImageData inputImage) {
    int[][][] inputRGB = cloneMatrix(inputImage.getRgb());
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();
    int channels = inputImage.getChannels();

    int[] redFrequencies = calculateFrequency(inputRGB, height, width, 0);
    int[] greenFrequencies = calculateFrequency(inputRGB, height, width, 1);
    int[] blueFrequencies = calculateFrequency(inputRGB, height, width, 2);

    int redIndex = findPeakIndex(redFrequencies);
    int greenIndex = findPeakIndex(greenFrequencies);
    int blueIndex = findPeakIndex(blueFrequencies);
    int[] indexes = {redIndex, greenIndex, blueIndex};
    int averageIndex = (redIndex + greenIndex + blueIndex) / 3;

    int[][][] correctedRGB = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int k = 0; k < channels; k++) {
          correctedRGB[i][j][k] = adjustValue(inputRGB[i][j][k], indexes[k], averageIndex);
        }
      }
    }
    return correctedRGB;
  }

  /**
   * Applies a specified image operation to a percentage portion of the image for preview purposes.
   *
   * @param operationName The name of the image operation.
   * @param inputImage    The image to process.
   * @param percentage    The percentage of the image width to apply the operation.
   * @param params        Parameters for specific operations.
   * @return A 3D array representing the image with the operation previewed on a portion.
   */
  int[][][] splitPreview(String operationName, ImageData inputImage, double percentage,
      int[] params) {

    int[][][] inputRGB = inputImage.getRgb();
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();
    int channels = inputImage.getChannels();

    int splitWidth = (int) ((width * percentage) / 100);
    ImageData tempImage = new ImageData("tempImage", height, splitWidth, channels, inputRGB);

    Function<ImageData, int[][][]> operation = getImageDataFunction(operationName, params);
    if (operation == null) {
      throw new IllegalArgumentException(
          "Unsupported operation for split preview: " + operationName);
    }

    int[][][] modifiedRGB = operation.apply(tempImage);

    int[][][] outputRGB = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      if (splitWidth >= 0) {
        System.arraycopy(modifiedRGB[i], 0, outputRGB[i], 0, splitWidth);
      }
    }
    for (int i = 0; i < height; i++) {
      if (width - splitWidth >= 0) {
        System.arraycopy(inputRGB[i], splitWidth, outputRGB[i], splitWidth, width - splitWidth);
      }
    }
    return outputRGB;
  }

  /**
   * Retrieves the appropriate image processing function based on the specified operation name. This
   * method uses a map to associate operation names with their corresponding image processing
   * functions. It returns a function that can be applied to an `ImageData` object, producing a
   * modified RGB matrix as the result.
   *
   * @param operationName The name of the image operation (e.g., "blur", "sharpen", "sepia").
   * @param params        The parameters for the operation (if any), typically used for operations
   *                      requiring additional input like intensity values or adjustments.
   * @return the modified RGB matrix
   */
  Function<ImageData, int[][][]> getImageDataFunction(String operationName, int[] params) {
    Map<String, Function<ImageData, int[][][]>> splitPreviewMap = new HashMap<>();
    splitPreviewMap.put("blur", img -> this.kernelOperation(false, img));
    splitPreviewMap.put("sharpen", img -> this.kernelOperation(true, img));
    splitPreviewMap.put("sepia", img -> this.sepia(img));
    splitPreviewMap.put("red-component", img -> this.typeComponent(img, "red"));
    splitPreviewMap.put("green-component", img -> this.typeComponent(img, "green"));
    splitPreviewMap.put("blue-component", img -> this.typeComponent(img, "blue"));
    splitPreviewMap.put("luma-component", img -> this.typeComponent(img, "luma"));
    splitPreviewMap.put("value-component", img -> this.typeComponent(img, "value"));
    splitPreviewMap.put("intensity-component", img -> this.typeComponent(img, "intensity"));
    splitPreviewMap.put("color-correct", img -> this.colorCorrection(img));
    splitPreviewMap.put("levels-adjust",
        img -> this.levelAdjustment(img, params[0], params[1], params[2]));

    return splitPreviewMap.get(operationName);
  }

}
