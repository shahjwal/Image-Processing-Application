package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import model.ImageModel;

/**
 * The SaveCommand class is a concrete implementation of the AbstractCommand class, responsible for
 * saving an image from a specified file path and the specific name provided by the user via
 * command.
 */
class SaveCommand extends AbstractCommand {

  private final String path;
  private final String fetchImage;
  private final String extension;

  /**
   * Constructs a SaveCommand with the specified parameters.
   *
   * @param path       The file path where the image should be saved.
   * @param fetchImage The name of the image to be fetched from the model.
   * @param extension  The file extension for the saved image.
   */
  SaveCommand(String path, String fetchImage, String extension) {
    this.path = path;
    this.fetchImage = fetchImage;
    this.extension = extension;
  }

  /**
   * Saves a PPM image file from a 3D RGB matrix.
   *
   * @param file    The file writer object for saving the image.
   * @param saveRGB The 3D RGB matrix representing the image.
   * @throws IOException if an error occurs while writing the file.
   */
  private void savePPM(FileWriter file, int[][][] saveRGB) throws IOException {
    int[][][] rgb = saveRGB.clone();
    int height = rgb.length;
    int width = rgb[0].length;

    PrintWriter writer = new PrintWriter(file);
    writer.println("P3");
    writer.println(width + " " + height);
    writer.println("255");

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        writer.println(rgb[i][j][0] + " " + rgb[i][j][1] + " " + rgb[i][j][2]);
      }
    }
    writer.close();
  }

  /**
   * Converts a 3D RGB matrix into a BufferedImage for saving in other formats.
   *
   * @param saveRGB The 3D RGB matrix.
   * @return A BufferedImage created from the RGB matrix.
   */
  private BufferedImage toBufferedImage(int[][][] saveRGB) {
    int height = saveRGB.length;
    int width = saveRGB[0].length;

    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    int[] pixels = new int[width * height];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int red = Math.max(0, Math.min(255, saveRGB[i][j][0]));
        int green = Math.max(0, Math.min(255, saveRGB[i][j][1]));
        int blue = Math.max(0, Math.min(255, saveRGB[i][j][2]));
        int rgbValue = (red << 16) | (green << 8) | blue;
        pixels[i * width + j] = rgbValue;
      }
    }
    bufferedImage.setRGB(0, 0, width, height, pixels, 0, width);
    return bufferedImage;
  }

  /**
   * Executes the command to save the specified image using delegation to the provided model
   * method.
   *
   * @param model The ImageModel instance used to perform the save operation.
   */
  @Override
  void execute(ImageModel model) {
    int[][][] fetchedRGB = model.saveImage(fetchImage);
    try {
      switch (extension) {
        case "png":
        case "jpg":
        case "jpeg":
          File fileToPass = new File(path);
          if (fileToPass.getParentFile() != null && fileToPass.getParentFile().exists()) {
            BufferedImage imageToSave = toBufferedImage(fetchedRGB);
            ImageIO.write(imageToSave, extension, fileToPass);
          }
          break;
        case "ppm":
          savePPM(new FileWriter(path), fetchedRGB);
          break;
        default:
          throw new RuntimeException("Invalid extension!");
      }
    } catch (IOException e) {
      throw new RuntimeException("Error while saving the image!");
    }
  }

}