package model;

import utils.MyUtils;

/**
 * The NormalizedHistogram class processes an image represented by a 3D RGB array, computes
 * histograms for each of the color channels (Red, Green, Blue), normalizes the frequency values,
 * and generates a background image with color plots to visualize the distribution of pixel
 * intensity values in the image. The normalization ensures that the frequency values for each
 * channel are scaled to fit within a 0-255 range, making the histogram more comparable and visually
 * interpretable.
 */
class NormalizedHistogram extends AbstractSpecificOps {

  /**
   * Constructs a NormalizedHistogram instance with the given image data.
   *
   * @param inputRGB The input image represented as a 3D RGB array.
   */
  NormalizedHistogram(int[][][] inputRGB) {
    super(inputRGB);
  }

  /**
   * Normalizes the frequency values of a color channel by scaling them to fit within the range of 0
   * to 255 based on the maximum frequency value.
   *
   * @param frequency    The frequency array for a color channel.
   * @param maxFrequency The maximum frequency value among the channels.
   * @return The normalized frequency values.
   */
  private int[] normalizeFrequencies(int[] frequency, int maxFrequency) {
    int[] normalized = new int[256];
    for (int i = 0; i < 256; i++) {
      normalized[i] = (int) Math.round(frequency[i] * 255.0 / maxFrequency);
    }
    return normalized;
  }

  /**
   * Generates a background image (checkerboard pattern) that will serve as the base image for
   * plotting the histograms.
   *
   * @return A 256x256 checkerboard background image.
   */
  private int[][][] generateBackgroundImage() {
    int[][][] image = new int[256][256][3];
    for (int i = 0; i < 256; i++) {
      for (int j = 0; j < 256; j++) {
        if (i % 15 == 0 || j % 15 == 0) {
          image[i][j][0] = 170;
          image[i][j][1] = 170;
          image[i][j][2] = 170;
        } else {
          image[i][j][0] = 255;
          image[i][j][1] = 255;
          image[i][j][2] = 255;
        }
      }
    }
    return image;
  }

  /**
   * Computes the maximum frequency value from a given frequency array.
   *
   * @param frequencies The frequency array for a color channel.
   * @return The maximum frequency value.
   */
  private int maxFrequency(int[] frequencies) {
    int max = 0;
    for (int freq : frequencies) {
      max = Math.max(max, freq);
    }
    return max;
  }

  /**
   * Plots a vertical line on the image at a specified index (representing color intensity) using
   * the given RGB color values.
   *
   * @param start The starting index for plotting the line.
   * @param end   The ending index for plotting the line.
   * @param image The image to which the line is being plotted.
   * @param index The index of the image column to plot.
   * @param rgb   The RGB color values for the plotted line.
   */
  private static void plotter(int start, int end, int[][][] image, int index, int[] rgb) {
    int increment = start < end ? 1 : -1;
    for (int i = start; i != end + increment; i += increment) {
      image[i][index][0] = rgb[0];
      image[i][index][1] = rgb[1];
      image[i][index][2] = rgb[2];
    }
  }

  /**
   * Processes the input image, computes histograms for the red, green, and blue channels,
   * normalizes the frequencies, and plots the resulting histograms onto the background image.
   *
   * @param inputRGB The input image data.
   * @param height   The height of the input image.
   * @param width    The width of the input image.
   * @return A 256x256 image with the plotted histograms for each color channel.
   */
  private int[][][] pixelPreprocessing(int[][][] inputRGB, int height, int width) {

    int[] redFrequency = MyUtils.calculateFrequency(inputRGB, height, width, 0);
    int[] greenFrequency = MyUtils.calculateFrequency(inputRGB, height, width, 1);
    int[] blueFrequency = MyUtils.calculateFrequency(inputRGB, height, width, 2);

    int maxRGB = Math.max(
        Math.max(this.maxFrequency(redFrequency), this.maxFrequency(greenFrequency)),
        this.maxFrequency(blueFrequency));

    int[] normalizedRed = new int[256];
    int[] normalizedGreen = new int[256];
    int[] normalizedBlue = new int[256];

    for (int i = 0; i < 256; i++) {
      normalizedRed[i] = (int) Math.round(redFrequency[i] * 255.0 / maxRGB);
      normalizedGreen[i] = (int) Math.round(greenFrequency[i] * 255.0 / maxRGB);
      normalizedBlue[i] = (int) Math.round(blueFrequency[i] * 255.0 / maxRGB);
    }

    int[][][] newImage = this.generateBackgroundImage();
    int[][] colors = {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}};
    int prevRed = 255 - normalizedRed[0];
    int prevGreen = 255 - normalizedGreen[0];
    int prevBlue = 255 - normalizedBlue[0];

    for (int j = 1; j < 256; j++) {
      int currRed = 255 - normalizedRed[j];
      int currGreen = 255 - normalizedGreen[j];
      int currBlue = 255 - normalizedBlue[j];
      this.plotter(prevRed, currRed, newImage, j, colors[0]);
      this.plotter(prevGreen, currGreen, newImage, j, colors[1]);
      this.plotter(prevBlue, currBlue, newImage, j, colors[2]);
      prevRed = currRed;
      prevGreen = currGreen;
      prevBlue = currBlue;
    }

    return newImage;
  }

  /**
   * Gets the processed image with normalized histograms plotted for each color channel.
   *
   * @return The image with the plotted histograms.
   */
  @Override
  int[][][] getMatrix() {
    int height = inputRGB.length;
    int width = inputRGB[0].length;
    return this.pixelPreprocessing(inputRGB, height, width);
  }
}
