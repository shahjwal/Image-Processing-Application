package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import utils.MyUtils;

/**
 * This class performs image compression using Haar wavelet transformations. It applies compression
 * to the RGB channels of an image, reduces the amount of data, and then reconstructs the image with
 * reduced data based on the given percentage.
 */
class ImageCompression extends AbstractSpecificOps {

  private final int percentage;

  /**
   * Constructs an ImageCompression object.
   *
   * @param inputRGB   The 3D array representing the input image in RGB format.
   * @param percentage The percentage of compression to apply.
   */
  ImageCompression(int[][][] inputRGB, int percentage) {
    super(inputRGB);
    this.percentage = percentage;
  }

  /**
   * Adjusts the padding of the input image to make its dimensions a power of 2. This ensures the
   * image can be processed correctly by the Haar wavelet transform.
   *
   * @param inputRGB The input image.
   * @return A new padded image with dimensions as a power of 2.
   */
  private int[][][] adjustPadding(int[][][] inputRGB) {
    int square = 1;
    int maxVal = Math.max(inputRGB.length, inputRGB[0].length);
    while (square < maxVal) {
      square *= 2;
    }

    int[][][] newPaddedRGB = new int[square][square][3];
    for (int i = 0; i < square; i++) {
      for (int j = 0; j < square; j++) {
        if (i < inputRGB.length && j < inputRGB[0].length) {
          newPaddedRGB[i][j][0] = inputRGB[i][j][0];
          newPaddedRGB[i][j][1] = inputRGB[i][j][1];
          newPaddedRGB[i][j][2] = inputRGB[i][j][2];
        } else {
          newPaddedRGB[i][j][0] = 0;
          newPaddedRGB[i][j][1] = 0;
          newPaddedRGB[i][j][2] = 0;
        }
      }
    }
    return newPaddedRGB;
  }

  /**
   * Applies the Haar wavelet transformation to a row of pixels. The Haar transformation averages
   * and differs adjacent pixels in pairs.
   *
   * @param iterator  The row of pixel values to transform.
   * @param condition The current size of the transformation block.
   * @return The transformed row of pixel values.
   */
  private double[] getTransformation(double[] iterator, int condition) {
    List<Double> average = new ArrayList<>();
    List<Double> difference = new ArrayList<>();
    for (int j = 0; j < condition - 1; j += 2) {
      average.add((iterator[j] + iterator[j + 1]) / Math.sqrt(2));
      difference.add((iterator[j] - iterator[j + 1]) / Math.sqrt(2));
    }
    for (int j = 0; j < average.size(); j++) {
      iterator[j] = average.get(j);
      iterator[j + average.size()] = difference.get(j);
    }
    return iterator;
  }

  /**
   * Inversely applies the Haar transformation to a row of pixels. This reconstructs the pixel
   * values after the transformation.
   *
   * @param iterator  The row of transformed pixel values to invert.
   * @param condition The current size of the block being inverted.
   * @return The reconstructed row of pixel values.
   */
  private double[] getInversion(double[] iterator, int condition) {
    List<Double> average = new ArrayList<>();
    List<Double> difference = new ArrayList<>();
    for (int j = 0; j < condition / 2; j++) {
      double avg = (iterator[j] + iterator[j + (condition / 2)]) / Math.sqrt(2);
      average.add(avg);
      double diff = (iterator[j] - iterator[j + (condition / 2)]) / Math.sqrt(2);
      difference.add(diff);
    }
    for (int j = 0; j < condition; j += 2) {
      iterator[j] = average.get(j / 2);
      iterator[j + 1] = difference.get(j / 2);
    }
    return iterator;
  }

  /**
   * Applies the Haar wavelet transformation to the entire channel matrix.
   *
   * @param channelMatrix The matrix of pixel values for a color channel (red, green, or blue).
   * @return The transformed matrix of pixel values.
   */
  private double[][] haarTransformation(int[][] channelMatrix) {
    int size = channelMatrix.length;
    double[][] newDoublePixel = new double[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        newDoublePixel[i][j] = channelMatrix[i][j];
      }
    }
    int condition = size;
    while (condition > 1) {
      for (int i = 0; i < size; i++) {
        newDoublePixel[i] = this.getTransformation(newDoublePixel[i], condition);
      }

      for (int j = 0; j < size; j++) {
        double[] column = new double[size];
        for (int i = 0; i < size; i++) {
          column[i] = newDoublePixel[i][j];
        }
        column = this.getTransformation(column, condition);
        for (int i = 0; i < size; i++) {
          newDoublePixel[i][j] = column[i];
        }
      }
      condition /= 2;
    }
    return newDoublePixel;
  }

  /**
   * Applies the inverse Haar transformation to the entire channel matrix to reconstruct the image.
   *
   * @param channelMatrix The matrix of pixel values for a color channel (red, green, or blue).
   * @return The reconstructed matrix of pixel values.
   */
  private double[][] inverseHaarTransformation(double[][] channelMatrix) {
    int size = channelMatrix.length;
    int condition = 2;
    while (condition <= size) {
      for (int i = 0; i < size; i++) {
        channelMatrix[i] = this.getInversion(channelMatrix[i], condition);
      }

      for (int j = 0; j < size; j++) {
        double[] column = new double[size];
        for (int i = 0; i < size; i++) {
          column[i] = channelMatrix[i][j];
        }
        column = this.getInversion(column, condition);
        for (int i = 0; i < size; i++) {
          channelMatrix[i][j] = column[i];
        }
      }
      condition *= 2;
    }
    return channelMatrix;
  }

  /**
   * Sets values in the channel matrix to 0 if they are below the given threshold. This is the final
   * step of compression, where insignificant values are removed.
   *
   * @param channelMatrix The matrix of pixel values for a color channel.
   * @param threshold     The threshold below which values are set to 0.
   * @return The compressed channel matrix.
   */
  private double[][] compressionChannelFinal(double[][] channelMatrix, double threshold) {
    for (int i = 0; i < channelMatrix.length; i++) {
      for (int j = 0; j < channelMatrix.length; j++) {
        if (Math.abs(channelMatrix[i][j]) < threshold) {
          channelMatrix[i][j] = 0.0;
        }
      }
    }
    return channelMatrix;
  }

  /**
   * Compresses the image channel by reducing the number of distinct values based on the given
   * percentage. The threshold is determined based on the sorted distinct values of the matrix.
   *
   * @param channelMatrix The matrix of pixel values for a color channel.
   * @param percentage    The percentage of compression to apply.
   * @return The compressed channel matrix.
   */
  private double[][] compressImage(double[][] channelMatrix, int percentage) {
    double thresholdValue = Double.MAX_VALUE;

    if (percentage == 100) {
      return this.compressionChannelFinal(channelMatrix, thresholdValue);
    }

    Set<Double> distinctValues = new HashSet<>();

    for (double[] doubles : channelMatrix) {
      for (double value : doubles) {
        double num = Math.round(value * 1000.0) / 1000.0;
        distinctValues.add(Math.abs(num));
      }
    }

    List<Double> sortedDistinctValues = new ArrayList<>(distinctValues);
    Collections.sort(sortedDistinctValues);

    int arrayLen = sortedDistinctValues.size();
    int maxIndex = (int) Math.round(arrayLen * (percentage / 100.0));
    if (maxIndex >= arrayLen) {
      maxIndex = arrayLen - 1;
    }
    thresholdValue = sortedDistinctValues.get(maxIndex);

    return this.compressionChannelFinal(channelMatrix, thresholdValue);
  }

  /**
   * A helper method that applies the Haar transformation, compression, and inverse Haar
   * transformation to a single color channel.
   *
   * @param channel the input color channel to be processed.
   * @return the compressed channel after all transformations.
   */
  private double[][] processChannel(int[][] channel) {
    double[][] haarTransformed = this.haarTransformation(channel);
    double[][] compressed = this.compressImage(haarTransformed, this.percentage);
    return this.inverseHaarTransformation(compressed);
  }

  /**
   * Compresses the input image by applying Haar wavelet transformation, followed by compression and
   * reconstruction. The compression level is controlled by the given percentage.
   *
   * @return The compressed image as a 3D array.
   */
  @Override
  int[][][] getMatrix() {
    if (this.percentage < 1) {
      return this.inputRGB;
    }
    int[][][] outputRGB = this.adjustPadding(this.inputRGB);

    int[][] redChannel = MyUtils.extractChannel(outputRGB, outputRGB.length, outputRGB[0].length,
        0);
    int[][] greenChannel = MyUtils.extractChannel(outputRGB, outputRGB.length, outputRGB[0].length,
        1);
    int[][] blueChannel = MyUtils.extractChannel(outputRGB, outputRGB.length, outputRGB[0].length,
        2);

    double[][] redFinal = this.processChannel(redChannel);
    double[][] greenFinal = this.processChannel(greenChannel);
    double[][] blueFinal = this.processChannel(blueChannel);

    int[][][] compressedRGB = new int[this.inputRGB.length][this.inputRGB[0].length][3];
    for (int i = 0; i < this.inputRGB.length; i++) {
      for (int j = 0; j < this.inputRGB[0].length; j++) {
        compressedRGB[i][j][0] = (int) Math.round(redFinal[i][j]);
        compressedRGB[i][j][1] = (int) Math.round(greenFinal[i][j]);
        compressedRGB[i][j][2] = (int) Math.round(blueFinal[i][j]);
      }
    }
    return compressedRGB;
  }
}
