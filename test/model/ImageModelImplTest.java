package model;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit Test class for testing Model package.
 */
public class ImageModelImplTest {

  private ImageModel model;
  private int[][][] setupRGB;

  /**
   * Helper method to compare two 3D matrices with a tolerance of ±1 for each value.
   *
   * @param expected  The expected RGB matrix.
   * @param actual    The actual RGB matrix.
   * @param tolerance The allowed difference between corresponding values.
   * @return true if matrices are approximately equal within the given tolerance, false otherwise.
   */
  private boolean matricesAreApproximatelyEqual(int[][][] expected, int[][][] actual,
      int tolerance) {
    if (expected.length != actual.length || expected[0].length != actual[0].length
        || expected[0][0].length != actual[0][0].length) {
      return false;
    }

    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        for (int k = 0; k < expected[i][j].length; k++) {
          int diff = Math.abs(expected[i][j][k] - actual[i][j][k]);
          if (diff > tolerance) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * Converts a BufferedImage into a 3D array representing the RGB color.
   *
   * @param image the BufferedImage to be converted into an RGB matrix.
   * @return a 3D array of integers containing the RGB values for each pixel.
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

  @Before
  public void setUp() throws IOException {
    this.model = new ImageModelImplV2();
    BufferedImage image = ImageIO.read(new File("images/manhattan-small.png"));
    this.setupRGB = getRGBMatrix(image);
    this.model.loadImage("setupRGB", this.setupRGB);
  }

  /**
   * Test for loading a valid image.
   */
  @Test
  public void LoadTest() {
    try {
      BufferedImage image = ImageIO.read(new File("images/manhattan-small.png"));
      int[][][] rgb = getRGBMatrix(image);
      this.model.loadImage("test", rgb);
      assertEquals(this.setupRGB, rgb);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Test for loading invalid(null) image.
   */
  @Test(expected = IllegalArgumentException.class)
  public void invalidLoadTest() {
    int[][][] rgb = null;
    this.model.loadImage("invalid", rgb);
  }

  /**
   * Test for saving a valid image.
   */
  @Test
  public void SaveTest() {
    int[][][] rgbSave = this.model.saveImage("setupRGB");
    assertEquals(this.setupRGB, rgbSave);
  }

  /**
   * Test for saving a invalid image.
   */
  @Test(expected = IllegalArgumentException.class)
  public void invalidSaveTest() {
    int[][][] rgbSave = this.model.saveImage("invalidName");
    assertEquals(this.setupRGB, rgbSave);
  }

  /**
   * Test for brighten image with positive (+50) intensity.
   */
  @Test
  public void BrightPosTest() {
    try {
      BufferedImage image = ImageIO.read(new File("images/"
          + "manhattan-small-brighter-by-50.png"));
      int[][][] refRGB = getRGBMatrix(image);
      this.model.applyBrighten(50, "setupRGB", "brightRGB");
      int[][][] testRGB = this.model.saveImage("brightRGB");
      assertEquals(refRGB, testRGB);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Test for brighten image command with negative (-50) intensity.
   */
  @Test
  public void BrightNegTest() {
    try {
      BufferedImage image = ImageIO.read(new File("images/"
          + "manhattan-small-darker-by-50.png"));
      int[][][] refRGB = getRGBMatrix(image);
      this.model.applyBrighten(-50, "setupRGB", "darkenRGB");
      int[][][] testRGB = this.model.saveImage("darkenRGB");
      assertEquals(refRGB, testRGB);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Test for brighten image with zero intensity.
   */
  @Test
  public void BrightZeroTest() {
    try {
      BufferedImage image = ImageIO.read(new File("images/"
          + "manhattan-small.png"));
      int[][][] refRGB = getRGBMatrix(image);
      this.model.applyBrighten(0, "setupRGB", "sameRGB");
      int[][][] testRGB = this.model.saveImage("sameRGB");
      assertEquals(refRGB, testRGB);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * Test for vertical flip.
   */
  @Test
  public void VerticalFlipTest() {
    try {
      BufferedImage image = ImageIO.read(new File("images/"
          + "manhattan-small-vertical.png"));
      int[][][] refRGB = getRGBMatrix(image);
      this.model.applyFlip("setupRGB", "verticalFlip", true);
      int[][][] testRGB = this.model.saveImage("verticalFlip");
      assertEquals(refRGB, testRGB);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Test for horizontal flip.
   */
  @Test
  public void HorizontalFlipTest() {
    try {
      BufferedImage image = ImageIO.read(new File("images/"
          + "manhattan-small-horizontal.png"));
      int[][][] refRGB = getRGBMatrix(image);
      this.model.applyFlip("setupRGB", "horizontalFlip", false);
      int[][][] testRGB = this.model.saveImage("horizontalFlip");
      assertEquals(refRGB, testRGB);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Test for horizontal then vertical flip.
   */
  @Test
  public void HorizontalVerticalFlipTest() {
    try {
      BufferedImage image = ImageIO.read(
          new File("images/manhattan-small-horizontal-vertical.png"));
      int[][][] refRGB = getRGBMatrix(image);
      this.model.applyFlip("setupRGB", "horizontalFlip", false);
      this.model.applyFlip("horizontalFlip", "HVFlip", true);
      int[][][] testRGB = this.model.saveImage("HVFlip");
      assertEquals(refRGB, testRGB);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Test for vertical flip.
   */
  @Test
  public void VerticalHorizontalFlipTest() {
    try {
      BufferedImage image = ImageIO.read(
          new File("images/manhattan-small-vertical-horizontal.png"));
      int[][][] refRGB = getRGBMatrix(image);
      this.model.applyFlip("setupRGB", "verticalFlip", true);
      this.model.applyFlip("verticalFlip", "VHFlip", false);
      int[][][] testRGB = this.model.saveImage("VHFlip");
      assertEquals(refRGB, testRGB);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Test for sepia filtering of image.
   */
  @Test
  public void SepiaTest() {
    try {
      BufferedImage image = ImageIO.read(new File("images/manhattan-small-sepia.png"));
      int[][][] refRGB = getRGBMatrix(image);
      this.model.applySepia("setupRGB", "sepiaRGB");
      int[][][] testRGB = this.model.saveImage("sepiaRGB");
      assertEquals(refRGB, testRGB);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Test for blur filtering of image.
   */
  @Test
  public void BlurTest() {
    try {
      BufferedImage image = ImageIO.read(new File("images/manhattan-small-blur.png"));
      int[][][] refRGB = getRGBMatrix(image);
      this.model.applyBlur("setupRGB", "blurRGB");
      int[][][] testRGB = this.model.saveImage("blurRGB");
      assertEquals(refRGB, testRGB);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Test for Sharpen filtering of image.
   */
  @Test
  public void SharpenTest() {
    try {
      BufferedImage image = ImageIO.read(new File("images/manhattan-small-sharper.png"));
      int[][][] refRGB = getRGBMatrix(image);
      this.model.applySharpen("setupRGB", "sharpRGB");
      int[][][] testRGB = this.model.saveImage("sharpRGB");
      assertEquals(refRGB, testRGB);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Test for red-component filtering of image.
   */
  @Test
  public void RedComponentTest() {
    try {
      BufferedImage image = ImageIO.read(new File("images/manhattan-small-red.png"));
      int[][][] refRGB = getRGBMatrix(image);
      this.model.applyComponent("setupRGB", "redRGB", "red");
      int[][][] testRGB = this.model.saveImage("redRGB");
      assertEquals(refRGB, testRGB);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Test for blue-component filtering of image.
   */
  @Test
  public void BlueComponentTest() {
    try {
      BufferedImage image = ImageIO.read(new File("images/manhattan-small-blue.png"));
      int[][][] refRGB = getRGBMatrix(image);
      this.model.applyComponent("setupRGB", "blueRGB", "blue");
      int[][][] testRGB = this.model.saveImage("blueRGB");
      assertEquals(refRGB, testRGB);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Test for green-component filtering of image.
   */
  @Test
  public void GreenComponentTest() {
    try {
      BufferedImage image = ImageIO.read(new File("images/manhattan-small-green.png"));
      int[][][] refRGB = getRGBMatrix(image);
      this.model.applyComponent("setupRGB", "greenRGB", "green");
      int[][][] testRGB = this.model.saveImage("greenRGB");
      assertEquals(refRGB, testRGB);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Test for luma filtering of image.
   */
  @Test
  public void LumaTest() {
    try {
      BufferedImage image = ImageIO.read(new File("images/"
          + "manhattan-small-luma-greyscale.png"));
      int[][][] refRGB = getRGBMatrix(image);
      this.model.applyComponent("setupRGB", "lumaRGB", "luma");
      int[][][] testRGB = this.model.saveImage("lumaRGB");
      assertEquals(refRGB, testRGB);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Test for value filtering of image.
   */
  @Test
  public void ValueTest() {
    try {
      BufferedImage image = ImageIO.read(new File("images/"
          + "manhattan-small-value-greyscale.png"));
      int[][][] refRGB = getRGBMatrix(image);
      this.model.applyComponent("setupRGB", "valueRGB", "value");
      int[][][] testRGB = this.model.saveImage("valueRGB");
      assertEquals(refRGB, testRGB);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Test for intensity filtering of image.
   */
  @Test
  public void IntensityTest() {
    try {
      BufferedImage image = ImageIO.read(
          new File("images/manhattan-small-intensity-greyscale.png"));
      int[][][] refRGB = getRGBMatrix(image);
      model.applyComponent("setupRGB", "intensityRGB", "intensity");
      int[][][] testRGB = model.saveImage("intensityRGB");

      assertEquals(true, matricesAreApproximatelyEqual(refRGB, testRGB, 1));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Test for rgb-split.
   */
  @Test
  public void splitCommandTest() {
    this.model.applySplit("setupRGB", "red", "green",
        "blue");
    this.model.applyComponent("setupRGB", "redRGB", "red");
    this.model.applyComponent("setupRGB", "greenRGB", "green");
    this.model.applyComponent("setupRGB", "blueRGB", "blue");
    int[][][] red = model.saveImage("red");
    int[][][] green = model.saveImage("green");
    int[][][] blue = model.saveImage("blue");
    int[][][] redC = model.saveImage("redRGB");
    int[][][] greenC = model.saveImage("greenRGB");
    int[][][] blueC = model.saveImage("blueRGB");
    assertEquals(redC, red);
    assertEquals(greenC, green);
    assertEquals(blueC, blue);
  }

  /**
   * Test for combine image function.
   */
  @Test
  public void CombineImage() {
    try {
      BufferedImage imageRed = ImageIO.read(new File("images/manhattan-small-red.png"));
      BufferedImage imageGreen = ImageIO.read(new File("images/"
          + "manhattan-small-green.png"));
      BufferedImage imageBlue = ImageIO.read(new File("images/manhattan-small-blue.png"));
      int[][][] redRGB = getRGBMatrix(imageRed);
      this.model.loadImage("redImage", redRGB);
      int[][][] greenRGB = getRGBMatrix(imageGreen);
      this.model.loadImage("greenImage", greenRGB);
      int[][][] blueRGB = getRGBMatrix(imageBlue);
      this.model.loadImage("blueImage", blueRGB);
      this.model.applyCombine("testImage", "redImage",
          "greenImage", "blueImage");
      int[][][] testRGB = this.model.saveImage("testImage");
      assertEquals(this.setupRGB, testRGB);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void combineDimensionFailTest() {
    int[][][] image1 = {{{1, 2, 3}}};
    int[][][] image2 = {{{1, 2, 3}, {6, 7, 8}}, {{2, 3, 4}}};
    int[][][] image3 = {{{1, 2, 3}}, {{6, 7, 8}}, {{2, 3, 4}}, {{3, 4, 5}}};
    model.loadImage("image1", image1);
    model.loadImage("image2", image2);
    model.loadImage("image3", image3);
    model.applyCombine("test", "image1",
        "image2", "image3");
  }

}