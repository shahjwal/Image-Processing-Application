package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import model.ImageModel;
import model.ImageModelV2;
import view.IView;

/**
 * This class implements the {@code Features} interface, providing concrete methods for manipulating
 * images and interacting with the model and view layers of the image processing system. This class
 * allows various image operations like loading, saving, applying filters, transformations, and
 * displaying images within a GUI.
 */
public class FeaturesImpl implements Features {

  private final ImageModel model;
  private final IView view;

  /**
   * Constructs object with the specified model and view.
   *
   * @param model The image model used for storing and manipulating images.
   * @param view  The view component responsible for displaying images and error messages.
   */
  public FeaturesImpl(ImageModel model, IView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Applies the command specified by the given array of command tokens. This method creates and
   * executes the appropriate command using the CommandFactory.
   *
   * @param commandTokens An array of strings representing the command and its arguments.
   */
  private void applyCommand(String[] commandTokens) {
    AbstractCommand command = CommandFactory.createCommand(commandTokens);
    command.execute(this.model);
  }

  /**
   * Converts a 3D RGB array to a BufferedImage.
   *
   * @param rgbArray A 3D array representing RGB values of an image.
   * @return A BufferedImage representation of the RGB array.
   */
  private BufferedImage convertRgbArrayToBufferedImage(int[][][] rgbArray) {
    int height = rgbArray.length;
    int width = rgbArray[0].length;
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int rgbPixel = rgbArray[y][x][0] << 16 | rgbArray[y][x][1] << 8 | rgbArray[y][x][2];
        image.setRGB(x, y, rgbPixel);
      }
    }
    return image;
  }

  /**
   * Converts the saved image into a BufferedImage using the model.
   *
   * @param imageName The name of the image to be converted.
   * @return A BufferedImage representation of the saved image.
   */
  private BufferedImage pixelToBufferImage(String imageName) {
    int[][][] rgb = model.saveImage(imageName);
    return convertRgbArrayToBufferedImage(rgb);
  }

  /**
   * Converts the image into a histogram and returns it as a BufferedImage.
   *
   * @param imageName The name of the image for which the histogram is to be generated.
   * @return A BufferedImage representing the image's histogram.
   */
  private BufferedImage pixelToHistogram(String imageName) {
    ((ImageModelV2) model).applyHistogram(imageName, "Histogram" + imageName);
    int[][][] histogramRGB = model.saveImage("Histogram" + imageName);
    return convertRgbArrayToBufferedImage(histogramRGB);
  }

  /**
   * Displays the image and its histogram on the GUI background which is the main display.
   *
   * @param imageName The name of the image to be displayed.
   */
  private void displayImageGUI(String imageName) {
    BufferedImage imageBuffer = this.pixelToBufferImage(imageName);
    this.view.displayImage(imageName, imageBuffer);
    BufferedImage imageHistogram = this.pixelToHistogram(imageName);
    this.view.displayHistogram(imageName, imageHistogram);
  }

  /**
   * Checks if the specified image is valid (i.e., loaded).
   *
   * @param imageName The name of the image to check.
   * @return true if the image is loaded; false otherwise.
   */
  @Override
  public boolean checkImage(String imageName) {
    if (imageName == null) {
      this.view.showError("Image not loaded!");
      return false;
    }
    return true;
  }

  /**
   * Loads an image from a specified file path.
   *
   * @param path The file path of the image to load.
   */
  @Override
  public void loadImageFromPath(String path) {
    File file = new File(path);
    if (file.exists()) {
      String pathName = file.getName();
      String imageName = pathName.substring(0, pathName.lastIndexOf("."));
      String[] commandTokens = {"load", file.getAbsolutePath(), imageName};
      this.applyCommand(commandTokens);
      this.displayImageGUI(imageName);
    }
  }

  /**
   * Loads an image through the file chooser from the view.
   */
  @Override
  public void loadImage() {
    File path = this.view.fetchFile();
    if (path == null || !path.exists()) {
      return;
    }
    String pathName = path.getName();
    String imageName = pathName.substring(0, pathName.lastIndexOf("."));
    String[] commandTokens = {"load", path.getAbsolutePath(), imageName};
    this.applyCommand(commandTokens);
    this.displayImageGUI(imageName);
  }

  /**
   * Saves the image with the specified name to a location determined by the user.
   *
   * @param imageName The name of the image to save.
   */
  @Override
  public void saveImage(String imageName) {
    try {
      if (this.checkImage(imageName)) {
        File path = view.saveFile();
        if (path == null) {
          return;
        }
        String savePath = path.getAbsolutePath();
        String[] commandTokens = {"save", savePath, imageName};
        this.applyCommand(commandTokens);
      }
    } catch (Exception e) {
      this.view.showError(e.getMessage() + "\n Extension should be either PNG/JPG/JPEG/PPM");
    }
  }

  /**
   * Applies a sepia filter to the image with the specified name and saves the result.
   *
   * @param imageName The name of the image to apply the sepia filter to.
   */
  @Override
  public void sepiaImage(String imageName) {
    if (this.checkImage(imageName)) {
      String saveImageName = imageName + "_sepia";
      String[] commandTokens = {"sepia", imageName, saveImageName};
      this.applyCommand(commandTokens);
      this.displayImageGUI(saveImageName);
    }
  }

  /**
   * Applies a blur filter to the image with the specified name and saves the result.
   *
   * @param imageName The name of the image to apply the blur filter to.
   */
  @Override
  public void blurImage(String imageName) {
    if (this.checkImage(imageName)) {
      String saveImageName = imageName + "_blur";
      String[] commandTokens = {"blur", imageName, saveImageName};
      this.applyCommand(commandTokens);
      this.displayImageGUI(saveImageName);
    }
  }

  /**
   * Applies a sharpen filter to the image with the specified name and saves the result.
   *
   * @param imageName The name of the image to apply the sharpen filter to.
   */
  @Override
  public void sharpenImage(String imageName) {
    if (this.checkImage(imageName)) {
      String saveImageName = imageName + "_sharpen";
      String[] commandTokens = {"sharpen", imageName, saveImageName};
      this.applyCommand(commandTokens);
      this.displayImageGUI(saveImageName);
    }
  }

  /**
   * Flips the image in the specified direction (horizontal/vertical) and saves the result.
   *
   * @param flipType  The type of flip (horizontal or vertical).
   * @param imageName The name of the image to flip.
   */
  @Override
  public void flipImage(String flipType, String imageName) {
    if (this.checkImage(imageName)) {
      String saveImageName = imageName + flipType;
      String[] commandTokens = {flipType, imageName, saveImageName};
      this.applyCommand(commandTokens);
      this.displayImageGUI(saveImageName);
    }
  }

  /**
   * Applies the specified component extraction (e.g., red, green, blue, etc.) to the image and
   * saves the result.
   *
   * @param componentType The type of component to extract.
   * @param imageName     The name of the image to apply the component extraction to.
   */
  @Override
  public void componentImage(String componentType, String imageName) {
    if (this.checkImage(imageName)) {
      String saveImageName = imageName + componentType;
      String[] commandTokens = {componentType, imageName, saveImageName};
      this.applyCommand(commandTokens);
      this.displayImageGUI(saveImageName);
    }
  }

  /**
   * Applies color correction to the image with the specified name and saves the result.
   *
   * @param imageName The name of the image to apply color correction to.
   */
  @Override
  public void colorCorrectImage(String imageName) {
    String saveImageName = imageName + "_colorCorrected";
    String[] commandTokens = {"color-correct", imageName, saveImageName};
    this.applyCommand(commandTokens);
    this.displayImageGUI(saveImageName);
  }

  /**
   * Adjusts the levels of the image (black, mid, white) and saves the result.
   *
   * @param imageName The name of the image to adjust the levels for.
   * @param black     The black-point for level adjustment.
   * @param mid       The mid-point for level adjustment.
   * @param white     The white-point for level adjustment.
   */
  @Override
  public void levelAdjustImage(String imageName, String black, String mid, String white) {
    try {
      if (this.checkImage(imageName)) {
        String saveImageName = imageName + "_levelAdjusted";
        String[] commandTokens = {"levels-adjust", black, mid, white, imageName, saveImageName};
        this.applyCommand(commandTokens);
        this.displayImageGUI(saveImageName);
      }
    } catch (Exception e) {
      this.view.showError(e.getMessage());
    }
  }

  /**
   * Previews the image after applying a filter (e.g., blur, sharpen) with a split preview at a
   * specified intensity and optional parameters.
   *
   * @param filterType The type of filter (e.g., blur, sharpen).
   * @param imageName  The name of the image to apply the filter to.
   * @param intensity  The intensity of the filter effect.
   * @param params     Optional additional parameters for the filter.
   */
  @Override
  public void splitPreviewImage(String filterType, String imageName, String intensity,
      String... params) {
    try {
      String saveImageName = imageName + filterType + "_splitPreview";
      String[] commandTokens = null;
      if (params == null) {
        commandTokens = new String[]{filterType, imageName, saveImageName, "split", intensity};
      } else {
        commandTokens = new String[]{filterType, params[0], params[1], params[2], imageName,
            saveImageName, "split", intensity};
      }
      this.applyCommand(commandTokens);
      BufferedImage splitImage = this.pixelToBufferImage(saveImageName);
      this.view.displayInPreview(saveImageName, splitImage);
    } catch (Exception e) {
      this.view.showError(e.getMessage());
    }
  }

  /**
   * Compresses the image with the specified name by the given percentage and either displays it in
   * the GUI or as a preview.
   *
   * @param imageName  The name of the image to compress.
   * @param percentage The percentage by which to compress the image.
   * @param flag       If true, displays the compressed image in the GUI; otherwise, shows as
   *                   preview.
   */
  @Override
  public void compressImage(String imageName, String percentage, boolean flag) {
    if (this.checkImage(imageName)) {
      String saveImageName = imageName + percentage;
      String[] commandTokens = {"compress", percentage, imageName, saveImageName};
      this.applyCommand(commandTokens);
      BufferedImage compressedImage = this.pixelToBufferImage(saveImageName);
      if (flag) {
        this.displayImageGUI(saveImageName);
      } else {
        view.displayInPreview(saveImageName, compressedImage);
      }
    }
  }

  /**
   * Downscales the image to the specified height and width and either displays it in the GUI or as
   * a preview.
   *
   * @param imageName The name of the image to downscale.
   * @param height    The new height for the image.
   * @param width     The new width for the image.
   * @param flag      If true, displays the downscaled image in the GUI; otherwise, shows as
   *                  preview.
   */
  @Override
  public void downScaling(String imageName, String height, String width, boolean flag) {
    try {
      if (this.checkImage(imageName)) {
        String saveImageName = imageName + height + width;
        AbstractCommand downscale = new DownScaleCommand(imageName, saveImageName, height, width);
        downscale.execute(this.model);
        BufferedImage downScaledImage = this.pixelToBufferImage(saveImageName);
        if (flag) {
          this.displayImageGUI(saveImageName);
        } else {
          this.view.displayInPreview(saveImageName, downScaledImage);
        }
      }
    } catch (Exception e) {
      this.view.showError(e.getMessage());
    }
  }

  /**
   * Brightens the image with the specified intensity and either displays it in the GUI or as a
   * preview.
   *
   * @param imageName The name of the image to brighten.
   * @param intensity The intensity to apply to brighten the image.
   * @param flag      If true, displays the brightened image in the GUI; otherwise, shows as
   *                  preview.
   */
  @Override
  public void brightening(String imageName, String intensity, boolean flag) {
    try {
      if (this.checkImage(imageName)) {
        String saveImageName = imageName + intensity;
        String[] commandTokens = {"brighten", intensity, imageName, saveImageName};
        this.applyCommand(commandTokens);
        BufferedImage brightenedImage = this.pixelToBufferImage(saveImageName);
        if (flag) {
          this.displayImageGUI(saveImageName);
        } else {
          this.view.displayInPreview(saveImageName, brightenedImage);
        }
      }
    } catch (Exception e) {
      this.view.showError(e.getMessage());
    }
  }
}
