package model;

import static utils.MyUtils.clamp;
import static utils.MyUtils.cloneMatrix;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * The ImageOperations class provides various image manipulation methods, such as applying filters
 * (blur, sharpen, sepia), brightening/darkening images, flipping, and component extraction (luma,
 * red, green, blue, etc.). It uses a kernel-based approach for convolution operations like blur and
 * sharpen, and applies functions to manipulate RGB data for tasks like splitting and combining
 * color channels. The class operates on {@link ImageData} objects.
 */
class ImageOperations {

  /**
   * A map to store component extraction operations, such as "luma", "red", "green", etc.
   */
  private final Map<String, Function<int[], Integer>> typeComponentMap = new HashMap<>();

  /**
   * kernel for blur filter.
   */
  private static final double[][] blurKernel = {
      {1.0 / 16.0, 1.0 / 8.0, 1.0 / 16.0},
      {1.0 / 8.0, 1.0 / 4.0, 1.0 / 8.0},
      {1.0 / 16.0, 1.0 / 8.0, 1.0 / 16.0}
  };

  /**
   * kernel for sharpen filter.
   */
  private static final double[][] sharpenKernel = {
      {-1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0},
      {-1.0 / 8.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0, -1.0 / 8.0},
      {-1.0 / 8.0, 1.0 / 4.0, 1.0, 1.0 / 4.0, -1.0 / 8.0},
      {-1.0 / 8.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0, -1.0 / 8.0},
      {-1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0}};

  /**
   * Initializes the filter operations and configures the component extraction types (luma, value,
   * intensity, red, green, blue).
   */
  ImageOperations() {
    typeComponentMap.put("value", (rgb) -> Math.max(rgb[0], Math.max(rgb[1], rgb[2])));
    typeComponentMap.put("intensity", (rgb) -> (rgb[0] + rgb[1] + rgb[2]) / 3);
    typeComponentMap.put("red", (rgb) -> rgb[0]);
    typeComponentMap.put("green", (rgb) -> rgb[1]);
    typeComponentMap.put("blue", (rgb) -> rgb[2]);
  }

  /**
   * Applies a kernel to the image using convolution. We are using optimized calculation based on
   * defining a radius of kernel to eliminate the need of padding the image and also doing redundant
   * checks for the pixels of kernel which are not being superimposed by the image pixel.
   *
   * @param kernel      the kernel to apply.
   * @param kernelSize  the size of the kernel.
   * @param imageMatrix the image's RGB matrix.
   * @param height      the height of the image.
   * @param width       the width of the image.
   * @return the new image with the kernel applied.
   */
  private int[][][] applyKernel(double[][] kernel, int kernelSize, int[][][] imageMatrix,
      int height, int width, int channels) {
    int[][][] result = new int[height][width][3];
    int kRadius = kernelSize / 2;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        double[] pixelValue = {0.0, 0.0, 0.0};

        for (int ki = -kRadius; ki <= kRadius; ki++) {
          for (int kj = -kRadius; kj <= kRadius; kj++) {

            int imgX = i + ki;
            int imgY = j + kj;

            if (imgX >= 0 && imgX < height && imgY >= 0 && imgY < width) {
              for (int k = 0; k < channels; k++) {
                pixelValue[k] += kernel[ki + kRadius][kj + kRadius] * imageMatrix[imgX][imgY][k];
              }
            }
          }
        }
        for (int k = 0; k < channels; k++) {
          result[i][j][k] = (clamp((int) (pixelValue[k])));
        }
      }
    }
    return result;
  }

  /**
   * Brightens or darkens the image by the specified intensity.
   *
   * @param intensity  the amount to brighten (positive) or darken (negative).
   * @param inputImage the image to modify.
   * @return the brightened/darkened image.
   */
  int[][][] brightenDarken(int intensity, ImageData inputImage) {

    int[][][] rgb = cloneMatrix(inputImage.getRgb());

    for (int i = 0; i < inputImage.getHeight(); i++) {
      for (int j = 0; j < inputImage.getWidth(); j++) {
        for (int k = 0; k < inputImage.getChannels(); k++) {
          rgb[i][j][k] = clamp(rgb[i][j][k] + intensity);
        }
      }
    }
    return rgb;
  }

  /**
   * Applies either the blur or sharpen kernel to the image.
   *
   * @param kernel     true for sharpen, false for blur.
   * @param inputImage the image to modify.
   * @return the modified image.
   */
  int[][][] kernelOperation(boolean kernel, ImageData inputImage) {
    int[][][] rgb = cloneMatrix(inputImage.getRgb());

    return applyKernel(kernel ? sharpenKernel : blurKernel, kernel ? 5 : 3, rgb,
        inputImage.getHeight(), inputImage.getWidth(), inputImage.getChannels());
  }

  /**
   * Flips the image either horizontally or vertically based on the specified flag.
   *
   * @param inputImage the image to flip.
   * @param flip       true to flip vertically, false to flip horizontally.
   * @return the flipped image as a 3D array of RGB values.
   */
  int[][][] doFlip(ImageData inputImage, boolean flip) {
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();

    int[][][] rgb = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        rgb[i][j] =
            flip ? inputImage.getRgb()[height - 1 - i][j] : inputImage.getRgb()[i][width - 1 - j];
      }
    }
    return rgb;
  }

  /**
   * Multiplies an RGB vector by a matrix to apply a transformation. This can be used for any future
   * filter that requires matrix multiplication.
   *
   * @param rgb    the RGB values of the pixel (in the form of an array of size 3).
   * @param matrix a 3x3 transformation matrix (e.g., sepia filter matrix).
   * @return the transformed RGB values after applying the matrix.
   */
  private int[] applyMatrixToRgb(int[] rgb, double[][] matrix) {
    double[] newRgb = new double[3];

    for (int i = 0; i < 3; i++) {
      newRgb[i] = 0;
      for (int j = 0; j < 3; j++) {
        newRgb[i] += matrix[i][j] * rgb[j];
      }
      newRgb[i] = clamp((int) newRgb[i]);
    }
    int[] returnRGB = new int[3];
    for (int i = 0; i < 3; i++) {
      returnRGB[i] = (int) newRgb[i];
    }
    return returnRGB;
  }

  /**
   * Applies a color transformation to the image based on the provided matrix.
   *
   * @param inputImage the image to modify.
   * @param matrix     the 3x3 transformation matrix to apply (e.g., sepia or luma matrix).
   * @return the transformed image with the applied color matrix.
   */
  private int[][][] applyColorMatrixTransformation(ImageData inputImage, double[][] matrix) {
    int[][][] rgb = cloneMatrix(inputImage.getRgb());

    for (int i = 0; i < inputImage.getHeight(); i++) {
      for (int j = 0; j < inputImage.getWidth(); j++) {
        int[] newRgb = applyMatrixToRgb(rgb[i][j], matrix);
        for (int k = 0; k < inputImage.getChannels(); k++) {
          rgb[i][j][k] = newRgb[k];
        }
      }
    }
    return rgb;
  }

  /**
   * Applies a sepia tone to the image.
   *
   * @param inputImage the image to modify.
   * @return the sepia-toned image.
   */
  int[][][] sepia(ImageData inputImage) {
    double[][] sepiaMatrix = {
        {0.393, 0.769, 0.189},
        {0.349, 0.686, 0.168},
        {0.272, 0.534, 0.131}
    };
    return applyColorMatrixTransformation(inputImage, sepiaMatrix);
  }

  /**
   * Applies a luma greyscale to the image.
   *
   * @param inputImage the image to modify.
   * @return the luma-greyscale image.
   */
  int[][][] lumaComponent(ImageData inputImage) {
    double[][] lumaMatrix = {
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}
    };
    return applyColorMatrixTransformation(inputImage, lumaMatrix);
  }

  /**
   * Extracts a specific color component from the image based on the provided type.
   *
   * @param inputImage the image from which to extract the component.
   * @param type       the type of component to extract (e.g., "red", "green", "blue").
   * @return the extracted component image as a 3D array of RGB values.
   */
  int[][][] typeComponent(ImageData inputImage, String type) {
    int[][][] rgb = cloneMatrix(inputImage.getRgb());
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();
    int channels = inputImage.getChannels();

    if (Objects.equals(type, "luma")) {
      return this.lumaComponent(inputImage);
    } else {
      Function<int[], Integer> operation = typeComponentMap.get(type);

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int[] pixel = rgb[i][j];
          int temp = operation.apply(pixel);
          for (int k = 0; k < channels; k++) {
            rgb[i][j][k] = temp;
          }
        }
      }
      return rgb;
    }
  }

  /**
   * Combines three color channel images (red, green, and blue) into one RGB image.
   *
   * @param redImage   the image containing the red component.
   * @param greenImage the image containing the green component.
   * @param blueImage  the image containing the blue component.
   * @return the combined RGB image as a 3D array of RGB values.
   * @throws IllegalArgumentException if the dimensions of the input images do not match.
   */
  int[][][] combineImage(ImageData redImage, ImageData greenImage, ImageData blueImage) {

    int redHeight = redImage.getHeight();
    int redWidth = redImage.getWidth();

    int greenHeight = greenImage.getHeight();
    int greenWidth = greenImage.getWidth();

    int blueHeight = blueImage.getHeight();
    int blueWidth = blueImage.getWidth();

    if (!(redHeight == greenHeight && greenHeight == blueHeight) || !(redWidth == greenWidth
        && greenWidth == blueWidth)) {
      throw new IllegalArgumentException("Image dimensions are different so cannot be combined.");
    }

    int[][][] rgb = new int[redHeight][redWidth][3];

    for (int i = 0; i < redHeight; i++) {
      for (int j = 0; j < redWidth; j++) {
        int red = redImage.getRgb()[i][j][0];
        int green = greenImage.getRgb()[i][j][0];
        int blue = blueImage.getRgb()[i][j][0];
        rgb[i][j][0] = red;
        rgb[i][j][1] = green;
        rgb[i][j][2] = blue;
      }
    }
    return rgb;
  }
}
