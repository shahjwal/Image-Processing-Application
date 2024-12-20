package model;

import java.util.HashMap;
import java.util.Map;

/**
 * The ImageModelImpl class provides the implementation for the ImageModel interface. It manages the
 * image data and applies various image processing operations.
 */
public class ImageModelImpl implements ImageModel {

  private final Map<String, ImageData> images;
  private final ImageOperations imageOps;

  /**
   * Constructs an ImageModelImpl instance with an empty image collection and initializes the
   * ImageOperations object used for image manipulations.
   */
  public ImageModelImpl() {
    imageOps = new ImageOperations();
    images = new HashMap<>();
  }

  /**
   * Helper method to add a new image to the image collection with the given name and RGB data.
   *
   * @param name the name of the image to add.
   * @param rgb  the 3D RGB array of the image.
   */
  void addImage(String name, int[][][] rgb) {
    ImageData imageNew = new ImageData(name, rgb.length, rgb[0].length, rgb[0][0].length, rgb);
    images.put(imageNew.getName(), imageNew);
  }

  /**
   * helper method to retrieves the ImageData object for the specified image name.
   *
   * @param imageName the name of the image to retrieve.
   * @return the ImageData object for the image.
   * @throws IllegalArgumentException if the image does not exist in the collection.
   */
  ImageData getImage(String imageName) {
    if (images.containsKey(imageName)) {
      return images.get(imageName);
    } else {
      throw new IllegalArgumentException("Image : '" + imageName + "' not found and"
          + " hence cannot be manipulated.");
    }
  }

  /**
   * Loads an image with the given name and RGB data into the image collection.
   *
   * @param name the name of the image to load.
   * @param rgb  the 3D RGB array of the image.
   * @throws IllegalArgumentException if the RGB data is null or invalid.
   */
  @Override
  public void loadImage(String name, int[][][] rgb) {
    if (rgb != null) {
      ImageData imageNew = new ImageData(name, rgb.length, rgb[0].length, rgb[0][0].length, rgb);
      images.put(imageNew.getName(), imageNew);
    } else {
      throw new IllegalArgumentException("Image : " + name + " is invalid.");
    }
  }

  /**
   * Saves and returns the RGB data of the specified image.
   *
   * @param imageName the name of the image to save.
   * @return the 3D RGB array of the image.
   * @throws IllegalArgumentException if the image is not found.
   */
  @Override
  public int[][][] saveImage(String imageName) {
    if (images.containsKey(imageName)) {
      return images.get(imageName).getRgb();
    } else {
      throw new IllegalArgumentException("Image :'" + imageName + "' not found and "
          + "hence cannot be saved.");
    }
  }

  /**
   * Applies a brightness adjustment to the specified image and saves the result. Delegates the
   * brightening operation to the ImageOperations class.
   *
   * @param intensity the amount to brighten or darken the image.
   * @param imageName the name of the image to brighten.
   * @param saveImage the name under which the modified image is saved.
   */
  @Override
  public void applyBrighten(int intensity, String imageName, String saveImage) {
    ImageData inputImage = this.getImage(imageName);
    int[][][] newRGB = imageOps.brightenDarken(intensity, inputImage);
    this.addImage(saveImage, newRGB);
  }

  /**
   * Applies a blur effect to the specified image and saves the result. Delegates the blur operation
   * to the ImageOperations class.
   *
   * @param imageName the name of the image to blur.
   * @param saveImage the name under which the blurred image is saved.
   */
  @Override
  public void applyBlur(String imageName, String saveImage) {
    ImageData inputImage = this.getImage(imageName);
    int[][][] newRGB = imageOps.kernelOperation(false, inputImage);
    this.addImage(saveImage, newRGB);
  }

  /**
   * Applies a sharpening effect to the specified image and saves the result. Delegates the
   * sharpening operation to the ImageOperations class.
   *
   * @param imageName the name of the image to sharpen.
   * @param saveImage the name under which the sharpened image is saved.
   */
  @Override
  public void applySharpen(String imageName, String saveImage) {
    ImageData inputImage = this.getImage(imageName);
    int[][][] newRGB = imageOps.kernelOperation(true, inputImage);
    this.addImage(saveImage, newRGB);
  }

  /**
   * Applies a sepia tone effect to the specified image and saves the result. Delegates the sepia
   * operation to the ImageOperations class.
   *
   * @param imageName the name of the image to apply sepia to.
   * @param saveImage the name under which the sepia-toned image is saved.
   */
  @Override
  public void applySepia(String imageName, String saveImage) {
    ImageData inputImage = this.getImage(imageName);
    int[][][] newRGB = imageOps.sepia(inputImage);
    this.addImage(saveImage, newRGB);
  }

  /**
   * Flips the image either horizontally or vertically and saves the result. Delegates the flip
   * operation to the ImageOperations class.
   *
   * @param imageName the name of the image to flip.
   * @param saveImage the name under which the flipped image is saved.
   * @param flip      the direction of the flip (true for vertical, false for horizontal).
   */
  @Override
  public void applyFlip(String imageName, String saveImage, boolean flip) {
    ImageData inputImage = this.getImage(imageName);
    int[][][] newRGB = imageOps.doFlip(inputImage, flip);
    this.addImage(saveImage, newRGB);

  }

  /**
   * Extracts a specific color component (red, green, blue, luma, value or intensity) from the image
   * and saves it. Delegates the component extraction to the ImageOperations class.
   *
   * @param imageName the name of the image to extract the component from.
   * @param saveImage the name under which the extracted component image is saved.
   * @param type      the color component to extract.
   */
  @Override
  public void applyComponent(String imageName, String saveImage, String type) {
    ImageData inputImage = this.getImage(imageName);
    int[][][] newRGB = imageOps.typeComponent(inputImage, type);
    this.addImage(saveImage, newRGB);

  }

  /**
   * Splits the image into its red, green, and blue components and saves them separately. Delegates
   * the component extraction to the ImageOperations class.
   *
   * @param imageName  the name of the image to split.
   * @param redImage   the name under which the red component image is saved.
   * @param greenImage the name under which the green component image is saved.
   * @param blueImage  the name under which the blue component image is saved.
   */
  @Override
  public void applySplit(String imageName, String redImage, String greenImage, String blueImage) {
    ImageData inputImage = this.getImage(imageName);
    int[][][] redRGB = imageOps.typeComponent(inputImage, "red");
    int[][][] greenRGB = imageOps.typeComponent(inputImage, "green");
    int[][][] blueRGB = imageOps.typeComponent(inputImage, "blue");
    this.addImage(redImage, redRGB);
    this.addImage(greenImage, greenRGB);
    this.addImage(blueImage, blueRGB);

  }

  /**
   * Combines separate red, green, and blue component images into a single image. Delegates the
   * image combination to the ImageOperations class.
   *
   * @param saveImage  the name under which the combined image is saved.
   * @param redImage   the name of the red component image.
   * @param greenImage the name of the green component image.
   * @param blueImage  the name of the blue component image.
   */
  @Override
  public void applyCombine(String saveImage, String redImage, String greenImage, String blueImage) {
    ImageData inputRed = this.getImage(redImage);
    ImageData inputGreen = this.getImage(greenImage);
    ImageData inputBlue = this.getImage(blueImage);
    int[][][] outputRGB = imageOps.combineImage(inputRed, inputGreen, inputBlue);
    this.addImage(saveImage, outputRGB);
  }


}
