package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import model.ImageModel;

/**
 * The LoadCommand class is a concrete implementation of the AbstractCommand class, responsible for
 * loading an image from a specified file path into the ImageModel.
 */
class LoadCommand extends AbstractCommand {

  private final String path;
  private final String name;
  private final String extension;

  /**
   * Constructs a LoadCommand with the specified parameters.
   *
   * @param path      the file path of the image to be loaded.
   * @param name      the name to assign to the loaded image in the model.
   * @param extension the file extension of the image (e.g., "png", "jpeg", "ppm").
   */
  LoadCommand(String path, String name, String extension) {
    this.path = path;
    this.name = name;
    this.extension = extension;
  }

  /**
   * Loads a PPM (Portable Pixel Map) image file into a 3D RGB matrix. This method parses the PPM
   * file, reading its width, height, and pixel data, and returns a 3D array representing the RGB
   * values of each pixel.
   *
   * @param file The PPM file to be loaded.
   * @return A 3D array representing the image's RGB pixel data.
   * @throws IOException              If there is an issue reading the file.
   * @throws IllegalArgumentException If the file format is not valid (i.e., it doesn't start with
   *                                  "P3").
   */
  private static int[][][] loadPPM(File file) throws IOException {
    Scanner sc = new Scanner(file);
    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String line = sc.nextLine().trim();
      if (!line.isEmpty() && line.charAt(0) != '#') {
        builder.append(line).append(System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());
    String token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    int[][][] rgb = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        rgb[i][j][0] = sc.nextInt();
        rgb[i][j][1] = sc.nextInt();
        rgb[i][j][2] = sc.nextInt();
      }
    }
    return rgb;
  }

  /**
   * This method helps in converting a BufferImage into a 3D matrix of pixels.
   *
   * @param image the Buffered image that has to be converted.
   * @return the 3D matrix representation of the buffered image.
   */
  private static int[][][] getRGBMatrix(BufferedImage image) {
    int height = image.getHeight();
    int width = image.getWidth();
    int[][][] rgbMatrix = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int rgbValue = image.getRGB(j, i);

        int red = (rgbValue >> 16) & 0xFF;
        int green = (rgbValue >> 8) & 0xFF;
        int blue = rgbValue & 0xFF;

        rgbMatrix[i][j][0] = red;
        rgbMatrix[i][j][1] = green;
        rgbMatrix[i][j][2] = blue;
      }
    }
    return rgbMatrix;
  }

  /**
   * Executes the command to load the image into the specified ImageModel.
   *
   * @param model the ImageModel instance where the image will be loaded.
   */
  @Override
  void execute(ImageModel model) {
    try {
      switch (extension) {
        case "png":
        case "jpg":
        case "jpeg":
          BufferedImage image = ImageIO.read(new File(path));
          model.loadImage(name, getRGBMatrix(image));
          break;
        case "ppm":
          model.loadImage(name, loadPPM(new File(path)));
          break;
        default:
          throw new RuntimeException("Invalid extension!");
      }
    } catch (IOException e) {
      throw new RuntimeException("Error loading Image!");
    }
  }

}