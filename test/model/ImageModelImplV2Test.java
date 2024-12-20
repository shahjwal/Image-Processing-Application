package model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.Before;
import org.junit.Test;

/**
 * A Junit Test class for ImageModelImplV2 class.
 */
public class ImageModelImplV2Test {

  ImageModelV2 modelV2;
  int[][][] orgRGB;


  public static int[][][] getStaticRGBMatrix() {
    return new int[][][]{
        {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}},
        {{128, 128, 128}, {192, 192, 192}, {64, 64, 64}},
        {{100, 200, 150}, {50, 50, 150}, {150, 50, 50}},
        {{0, 128, 64}, {128, 0, 128}, {64, 128, 0}}
    };
  }

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
    modelV2 = new ImageModelImplV2();
    BufferedImage imageOrg = ImageIO.read(new File("images/galaxy.png"));
    this.orgRGB = getRGBMatrix(imageOrg);
    this.modelV2.loadImage("orgImage", this.orgRGB);
    this.modelV2.loadImage("staticImage", getStaticRGBMatrix());
  }

  @Test
  public void ColorCorrectTest() throws IOException {
    BufferedImage refImage = ImageIO.read(new File("images/galaxy-corrected.png"));
    int[][][] refRGB = getRGBMatrix(refImage);
    this.modelV2.applyColorCorrection("orgImage", "newImage");
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertArrayEquals(refRGB, newRGB);
  }

  @Test
  public void LevelAdjustTest() throws IOException {
    BufferedImage refImage = ImageIO.read(new File("images/galaxy-adjusted.png"));
    int[][][] refRGB = getRGBMatrix(refImage);
    this.modelV2.applyLevelAdjustment("orgImage", "newImage",
        20, 100, 255);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertEquals(true, matricesAreApproximatelyEqual(refRGB, newRGB, 1));
  }

  @Test
  public void LevelAdjustCorrectTest() throws IOException {
    BufferedImage refImage = ImageIO.read(new File(
        "images/galaxy-adjusted-color-corrected.png"));
    int[][][] refRGB = getRGBMatrix(refImage);
    this.modelV2.applyLevelAdjustment("orgImage", "tempImage",
        20, 100, 255);
    this.modelV2.applyColorCorrection("tempImage", "newImage");
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertEquals(true, matricesAreApproximatelyEqual(refRGB, newRGB, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void LevelAdjustIncorrectTest1() throws IOException {
    this.modelV2.applyLevelAdjustment("orgImage", "tempImage",
        256, 100, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void LevelAdjustIncorrectTest2() {
    this.modelV2.applyLevelAdjustment("orgImage", "tempImage",
        20, 300, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void LevelAdjustIncorrectTest3() {
    this.modelV2.applyLevelAdjustment("orgImage", "tempImage",
        20, 100, 455);
  }

  @Test(expected = IllegalArgumentException.class)
  public void LevelAdjustIncorrectTest4() {
    this.modelV2.applyLevelAdjustment("orgImage", "tempImage",
        200, 30, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void LevelAdjustIncorrectTest5() {
    this.modelV2.applyLevelAdjustment("orgImage", "tempImage",
        20, 250, 155);
  }

  @Test
  public void BlurSplitPreview0() throws IOException {
    BufferedImage image = ImageIO.read(new File("images/manhattan-small.png"));
    int[][][] manRGB = getRGBMatrix(image);
    this.modelV2.loadImage("man", manRGB);
    this.modelV2.applySplitPreview("blur", "man",
        "newImage", 0, null);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertArrayEquals(manRGB, newRGB);
  }

  @Test
  public void BlurSplitPreview100() throws IOException {
    BufferedImage image = ImageIO.read(new File("images/manhattan-small.png"));
    BufferedImage image2 = ImageIO.read(new File("images/manhattan-small-blur.png"));
    int[][][] manRGB = getRGBMatrix(image);
    int[][][] manRGB2 = getRGBMatrix(image2);
    this.modelV2.loadImage("man", manRGB);
    this.modelV2.applySplitPreview("blur", "man",
        "newImage", 100, null);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertArrayEquals(manRGB2, newRGB);
  }

  @Test
  public void sharpenSplitPreview0() throws IOException {
    BufferedImage image = ImageIO.read(new File("images/manhattan-small.png"));
    int[][][] manRGB = getRGBMatrix(image);
    this.modelV2.loadImage("man", manRGB);
    this.modelV2.applySplitPreview("sharpen", "man",
        "newImage", 0, null);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertArrayEquals(manRGB, newRGB);
  }

  @Test
  public void sharpenSplitPreview100() throws IOException {
    BufferedImage image = ImageIO.read(new File("images/manhattan-small.png"));
    BufferedImage image2 = ImageIO.read(new File("images/manhattan-small-sharper.png"));
    int[][][] manRGB = getRGBMatrix(image);
    int[][][] manRGB2 = getRGBMatrix(image2);
    this.modelV2.loadImage("man", manRGB);
    this.modelV2.applySplitPreview("sharpen", "man",
        "newImage", 100, null);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertArrayEquals(manRGB2, newRGB);
  }

  @Test
  public void sepiaSplitPreview0() throws IOException {
    BufferedImage image = ImageIO.read(new File("images/manhattan-small.png"));
    int[][][] manRGB = getRGBMatrix(image);
    this.modelV2.loadImage("man", manRGB);
    this.modelV2.applySplitPreview("sepia", "man",
        "newImage", 0, null);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertArrayEquals(manRGB, newRGB);
  }

  @Test
  public void sepiaSplitPreview100() throws IOException {
    BufferedImage image = ImageIO.read(new File("images/manhattan-small.png"));
    BufferedImage image2 = ImageIO.read(new File("images/manhattan-small-sepia.png"));
    int[][][] manRGB = getRGBMatrix(image);
    int[][][] manRGB2 = getRGBMatrix(image2);
    this.modelV2.loadImage("man", manRGB);
    this.modelV2.applySplitPreview("sepia", "man",
        "newImage", 100, null);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertArrayEquals(manRGB2, newRGB);
  }

  @Test
  public void redComponentSplitPreview0() throws IOException {
    BufferedImage image = ImageIO.read(new File("images/manhattan-small.png"));
    int[][][] manRGB = getRGBMatrix(image);
    this.modelV2.loadImage("man", manRGB);
    this.modelV2.applySplitPreview("red-component", "man",
        "newImage", 0, null);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertArrayEquals(manRGB, newRGB);
  }

  @Test
  public void redComponentSplitPreview100() throws IOException {
    BufferedImage image = ImageIO.read(new File("images/manhattan-small.png"));
    BufferedImage image2 = ImageIO.read(new File("images/manhattan-small-red.png"));
    int[][][] manRGB = getRGBMatrix(image);
    int[][][] manRGB2 = getRGBMatrix(image2);
    this.modelV2.loadImage("man", manRGB);
    this.modelV2.applySplitPreview("red-component", "man",
        "newImage", 100, null);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertArrayEquals(manRGB2, newRGB);
  }

  @Test
  public void greenComponentSplitPreview0() throws IOException {
    BufferedImage image = ImageIO.read(new File("images/manhattan-small.png"));
    int[][][] manRGB = getRGBMatrix(image);
    this.modelV2.loadImage("man", manRGB);
    this.modelV2.applySplitPreview("green-component", "man",
        "newImage", 0, null);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertArrayEquals(manRGB, newRGB);
  }

  @Test
  public void greenComponentSplitPreview100() throws IOException {
    BufferedImage image = ImageIO.read(new File("images/manhattan-small.png"));
    BufferedImage image2 = ImageIO.read(new File("images/manhattan-small-green.png"));
    int[][][] manRGB = getRGBMatrix(image);
    int[][][] manRGB2 = getRGBMatrix(image2);
    this.modelV2.loadImage("man", manRGB);
    this.modelV2.applySplitPreview("green-component", "man",
        "newImage", 100, null);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertArrayEquals(manRGB2, newRGB);
  }

  @Test
  public void blueComponentSplitPreview0() throws IOException {
    BufferedImage image = ImageIO.read(new File("images/manhattan-small.png"));
    int[][][] manRGB = getRGBMatrix(image);
    this.modelV2.loadImage("man", manRGB);
    this.modelV2.applySplitPreview("blue-component", "man",
        "newImage", 0, null);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertArrayEquals(manRGB, newRGB);
  }

  @Test
  public void blueComponentSplitPreview100() throws IOException {
    BufferedImage image = ImageIO.read(new File("images/manhattan-small.png"));
    BufferedImage image2 = ImageIO.read(new File("images/manhattan-small-blue.png"));
    int[][][] manRGB = getRGBMatrix(image);
    int[][][] manRGB2 = getRGBMatrix(image2);
    this.modelV2.loadImage("man", manRGB);
    this.modelV2.applySplitPreview("blue-component", "man",
        "newImage", 100, null);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertArrayEquals(manRGB2, newRGB);
  }

  @Test
  public void lumaComponentSplitPreview0() throws IOException {
    BufferedImage image = ImageIO.read(new File("images/manhattan-small.png"));
    int[][][] manRGB = getRGBMatrix(image);
    this.modelV2.loadImage("man", manRGB);
    this.modelV2.applySplitPreview("luma-component", "man",
        "newImage", 0, null);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertArrayEquals(manRGB, newRGB);
  }

  @Test
  public void lumaComponentSplitPreview100() throws IOException {
    BufferedImage image = ImageIO.read(new File("images/manhattan-small.png"));
    BufferedImage image2 = ImageIO.read(new File(
        "images/manhattan-small-luma-greyscale.png"));
    int[][][] manRGB = getRGBMatrix(image);
    int[][][] manRGB2 = getRGBMatrix(image2);
    this.modelV2.loadImage("man", manRGB);
    this.modelV2.applySplitPreview("luma-component", "man",
        "newImage", 100, null);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertArrayEquals(manRGB2, newRGB);
  }

  @Test
  public void valueComponentSplitPreview0() throws IOException {
    BufferedImage image = ImageIO.read(new File("images/manhattan-small.png"));
    int[][][] manRGB = getRGBMatrix(image);
    this.modelV2.loadImage("man", manRGB);
    this.modelV2.applySplitPreview("value-component", "man",
        "newImage", 0, null);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertArrayEquals(manRGB, newRGB);
  }

  @Test
  public void valueComponentSplitPreview100() throws IOException {
    BufferedImage image = ImageIO.read(new File("images/manhattan-small.png"));
    BufferedImage image2 = ImageIO.read(new File(
        "images/manhattan-small-value-greyscale.png"));
    int[][][] manRGB = getRGBMatrix(image);
    int[][][] manRGB2 = getRGBMatrix(image2);
    this.modelV2.loadImage("man", manRGB);
    this.modelV2.applySplitPreview("value-component", "man",
        "newImage", 100, null);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertArrayEquals(manRGB2, newRGB);
  }

  @Test
  public void intesnsityComponentSplitPreview0() throws IOException {
    BufferedImage image = ImageIO.read(new File("images/manhattan-small.png"));
    int[][][] manRGB = getRGBMatrix(image);
    this.modelV2.loadImage("man", manRGB);
    this.modelV2.applySplitPreview("intensity-component", "man",
        "newImage", 0, null);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertArrayEquals(manRGB, newRGB);
  }

  @Test
  public void intensityComponentSplitPreview100() throws IOException {
    BufferedImage image = ImageIO.read(new File("images/manhattan-small.png"));
    BufferedImage image2 = ImageIO.read(new File(
        "images/manhattan-small-intensity-greyscale.png"));
    int[][][] manRGB = getRGBMatrix(image);
    int[][][] manRGB2 = getRGBMatrix(image2);
    this.modelV2.loadImage("man", manRGB);
    this.modelV2.applySplitPreview("intensity-component", "man",
        "newImage", 100, null);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertEquals(true, matricesAreApproximatelyEqual(manRGB2, newRGB, 1));
  }

  @Test
  public void colorCorrectSplitPreview0() throws IOException {
    BufferedImage image = ImageIO.read(new File("images/galaxy.png"));
    int[][][] manRGB = getRGBMatrix(image);
    this.modelV2.loadImage("galaxy", manRGB);
    this.modelV2.applySplitPreview("color-correct", "galaxy",
        "newImage", 0, null);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertArrayEquals(manRGB, newRGB);
  }

  @Test
  public void colorCorrectSplitPreview100() throws IOException {
    BufferedImage image = ImageIO.read(new File("images/galaxy.png"));
    BufferedImage image2 = ImageIO.read(new File("images/galaxy-corrected.png"));
    int[][][] manRGB = getRGBMatrix(image);
    int[][][] manRGB2 = getRGBMatrix(image2);
    this.modelV2.loadImage("galaxy", manRGB);
    this.modelV2.applySplitPreview("color-correct", "galaxy",
        "newImage", 100, null);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertArrayEquals(manRGB2, newRGB);
  }

  @Test
  public void levelAdjustSplitPreview0() throws IOException {
    BufferedImage image = ImageIO.read(new File("images/galaxy.png"));
    int[][][] manRGB = getRGBMatrix(image);
    this.modelV2.loadImage("galaxy", manRGB);
    int[] params = {20, 100, 255};
    this.modelV2.applySplitPreview("levels-adjust", "galaxy",
        "newImage", 0, params);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertArrayEquals(manRGB, newRGB);
  }

  @Test
  public void levelAdjustSplitPreview100() throws IOException {
    BufferedImage image = ImageIO.read(new File("images/galaxy.png"));
    BufferedImage image2 = ImageIO.read(new File("images/galaxy-adjusted.png"));
    int[][][] manRGB = getRGBMatrix(image);
    int[][][] manRGB2 = getRGBMatrix(image2);
    this.modelV2.loadImage("galaxy", manRGB);
    int[] params = {20, 100, 255};
    this.modelV2.applySplitPreview("levels-adjust", "galaxy",
        "newImage", 100, params);
    int[][][] newRGB = modelV2.saveImage("newImage");
    assertEquals(true, matricesAreApproximatelyEqual(manRGB2, newRGB, 1));
  }

  @Test
  public void blurSplitPreviewRandom2() {
    this.modelV2.applySplitPreview("blur", "staticImage",
        "blur70", 70.3, null);
    int[][][] blur70 = this.modelV2.saveImage("blur70");
    int[][][] blur70test = {
        {{91, 59, 28}, {63, 95, 32}, {0, 0, 255}},
        {{103, 100, 84}, {92, 114, 92}, {64, 64, 64}},
        {{67, 100, 100}, {73, 77, 108}, {150, 50, 50}},
        {{31, 60, 60}, {44, 34, 68}, {64, 128, 0}}
    };
    assertEquals(blur70test, blur70);
  }

  @Test
  public void SharpenSplitPreviewRandom2() {
    this.modelV2.applySplitPreview("sharpen", "staticImage",
        "sharpen70", 70.3, null);
    int[][][] sharpen70 = this.modelV2.saveImage("sharpen70");
    int[][][] sharpen70test = {
        {{255, 112, 42}, {125, 255, 42}, {0, 0, 255}},
        {{255, 255, 227}, {255, 255, 255}, {64, 64, 64}},
        {{192, 255, 255}, {155, 180, 255}, {150, 50, 50}},
        {{29, 150, 131}, {125, 54, 179}, {64, 128, 0}}
    };
    assertEquals(sharpen70test, sharpen70);
  }

  @Test
  public void SepiaSplitPreviewRandom2() {
    this.modelV2.applySplitPreview("sepia", "staticImage",
        "sepia70", 70.3, null);
    int[][][] sepia70 = this.modelV2.saveImage("sepia70");
    int[][][] sepia70test = {
        {{100, 88, 69}, {196, 174, 136}, {0, 0, 255}},
        {{172, 153, 119}, {255, 230, 179}, {64, 64, 64}},
        {{221, 197, 153}, {86, 76, 59}, {150, 50, 50}},
        {{110, 98, 76}, {74, 66, 51}, {64, 128, 0}}
    };
    assertEquals(sepia70test, sepia70);
  }

  @Test
  public void redComponentSplitPreviewRandom2() {
    this.modelV2.applySplitPreview("red-component", "staticImage",
        "red70", 70.3, null);
    int[][][] red70 = this.modelV2.saveImage("red70");
    int[][][] red70test = {
        {{255, 255, 255}, {0, 0, 0}, {0, 0, 255}},
        {{128, 128, 128}, {192, 192, 192}, {64, 64, 64}},
        {{100, 100, 100}, {50, 50, 50}, {150, 50, 50}},
        {{0, 0, 0}, {128, 128, 128}, {64, 128, 0}}
    };
    assertEquals(red70test, red70);
  }

  @Test
  public void greenComponentSplitPreviewRandom2() {
    this.modelV2.applySplitPreview("green-component", "staticImage",
        "green70", 70.3, null);
    int[][][] green70 = this.modelV2.saveImage("green70");
    int[][][] green70test = {
        {{0, 0, 0}, {255, 255, 255}, {0, 0, 255}},
        {{128, 128, 128}, {192, 192, 192}, {64, 64, 64}},
        {{200, 200, 200}, {50, 50, 50}, {150, 50, 50}},
        {{128, 128, 128}, {0, 0, 0}, {64, 128, 0}}
    };
    assertEquals(green70test, green70);
  }

  @Test
  public void blueComponentSplitPreviewRandom2() {
    this.modelV2.applySplitPreview("blue-component", "staticImage",
        "blue70", 70.3, null);
    int[][][] blue70 = this.modelV2.saveImage("blue70");
    int[][][] blue70test = {
        {{0, 0, 0}, {0, 0, 0}, {0, 0, 255}},
        {{128, 128, 128}, {192, 192, 192}, {64, 64, 64}},
        {{150, 150, 150}, {150, 150, 150}, {150, 50, 50}},
        {{64, 64, 64}, {128, 128, 128}, {64, 128, 0}}
    };
    assertEquals(blue70test, blue70);
  }

  @Test
  public void lumaComponentSplitPreviewRandom2() {
    this.modelV2.applySplitPreview("luma-component", "staticImage",
        "luma70", 70.3, null);
    int[][][] luma70 = this.modelV2.saveImage("luma70");
    int[][][] luma70test = {
        {{54, 54, 54}, {182, 182, 182}, {0, 0, 255}},
        {{128, 128, 128}, {192, 192, 192}, {64, 64, 64}},
        {{175, 175, 175}, {57, 57, 57}, {150, 50, 50}},
        {{96, 96, 96}, {36, 36, 36}, {64, 128, 0}}
    };
    assertEquals(luma70test, luma70);
  }

  @Test
  public void valueComponentSplitPreviewRandom2() {
    this.modelV2.applySplitPreview("value-component", "staticImage",
        "value70", 70.3, null);
    int[][][] value70 = this.modelV2.saveImage("value70");
    int[][][] value70test = {
        {{255, 255, 255}, {255, 255, 255}, {0, 0, 255}},
        {{128, 128, 128}, {192, 192, 192}, {64, 64, 64}},
        {{200, 200, 200}, {150, 150, 150}, {150, 50, 50}},
        {{128, 128, 128}, {128, 128, 128}, {64, 128, 0}}
    };
    assertEquals(value70test, value70);
  }

  @Test
  public void intensityComponentSplitPreviewRandom2() {
    this.modelV2.applySplitPreview("intensity-component", "staticImage",
        "intensity70", 70.3, null);
    int[][][] intensity70 = this.modelV2.saveImage("intensity70");
    int[][][] intensity70test = {
        {{85, 85, 85}, {85, 85, 85}, {0, 0, 255}},
        {{128, 128, 128}, {192, 192, 192}, {64, 64, 64}},
        {{150, 150, 150}, {83, 83, 83}, {150, 50, 50}},
        {{64, 64, 64}, {85, 85, 85}, {64, 128, 0}}
    };
    assertEquals(intensity70test, intensity70);
  }

  @Test
  public void levelAdjustSplitPreviewRandom2() {
    int[] params = {0, 100, 255};
    this.modelV2.applySplitPreview("levels-adjust", "staticImage",
        "levels70", 70.3, params);
    int[][][] levels70 = this.modelV2.saveImage("levels70");
    int[][][] levels70test = {
        {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}},
        {{157, 157, 157}, {213, 213, 213}, {64, 64, 64}},
        {{128, 219, 178}, {68, 68, 178}, {150, 50, 50}},
        {{0, 157, 86}, {157, 0, 157}, {64, 128, 0}}
    };
    assertEquals(levels70test, levels70);
  }

  @Test
  public void colorCorrectSplitPreviewRandom2() {
    this.modelV2.applySplitPreview("color-correct", "staticImage",
        "color70", 70.3, null);
    int[][][] color70 = this.modelV2.saveImage("color70");
    int[][][] color70test = {
        {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}},
        {{128, 128, 128}, {192, 192, 192}, {64, 64, 64}},
        {{100, 200, 150}, {50, 50, 150}, {150, 50, 50}},
        {{0, 128, 64}, {128, 0, 128}, {64, 128, 0}}
    };
    assertEquals(color70test, color70);
  }


  @Test
  public void blurSplitPreviewRandom() {
    this.modelV2.applySplitPreview("blur", "staticImage",
        "blur70", 70, null);
    int[][][] blur70 = this.modelV2.saveImage("blur70");
    int[][][] blur70test = {
        {{91, 59, 28}, {63, 95, 32}, {0, 0, 255}},
        {{103, 100, 84}, {92, 114, 92}, {64, 64, 64}},
        {{67, 100, 100}, {73, 77, 108}, {150, 50, 50}},
        {{31, 60, 60}, {44, 34, 68}, {64, 128, 0}}
    };
    assertEquals(blur70test, blur70);
  }

  @Test
  public void SharpenSplitPreviewRandom() {
    this.modelV2.applySplitPreview("sharpen", "staticImage",
        "sharpen70", 70, null);
    int[][][] sharpen70 = this.modelV2.saveImage("sharpen70");
    int[][][] sharpen70test = {
        {{255, 112, 42}, {125, 255, 42}, {0, 0, 255}},
        {{255, 255, 227}, {255, 255, 255}, {64, 64, 64}},
        {{192, 255, 255}, {155, 180, 255}, {150, 50, 50}},
        {{29, 150, 131}, {125, 54, 179}, {64, 128, 0}}
    };
    assertEquals(sharpen70test, sharpen70);
  }

  @Test
  public void SepiaSplitPreviewRandom() {
    this.modelV2.applySplitPreview("sepia", "staticImage",
        "sepia70", 70, null);
    int[][][] sepia70 = this.modelV2.saveImage("sepia70");
    int[][][] sepia70test = {
        {{100, 88, 69}, {196, 174, 136}, {0, 0, 255}},
        {{172, 153, 119}, {255, 230, 179}, {64, 64, 64}},
        {{221, 197, 153}, {86, 76, 59}, {150, 50, 50}},
        {{110, 98, 76}, {74, 66, 51}, {64, 128, 0}}
    };
    assertEquals(sepia70test, sepia70);
  }

  @Test
  public void redComponentSplitPreviewRandom() {
    this.modelV2.applySplitPreview("red-component", "staticImage",
        "red70", 70, null);
    int[][][] red70 = this.modelV2.saveImage("red70");
    int[][][] red70test = {
        {{255, 255, 255}, {0, 0, 0}, {0, 0, 255}},
        {{128, 128, 128}, {192, 192, 192}, {64, 64, 64}},
        {{100, 100, 100}, {50, 50, 50}, {150, 50, 50}},
        {{0, 0, 0}, {128, 128, 128}, {64, 128, 0}}
    };
    assertEquals(red70test, red70);
  }

  @Test
  public void greenComponentSplitPreviewRandom() {
    this.modelV2.applySplitPreview("green-component", "staticImage",
        "green70", 70, null);
    int[][][] green70 = this.modelV2.saveImage("green70");
    int[][][] green70test = {
        {{0, 0, 0}, {255, 255, 255}, {0, 0, 255}},
        {{128, 128, 128}, {192, 192, 192}, {64, 64, 64}},
        {{200, 200, 200}, {50, 50, 50}, {150, 50, 50}},
        {{128, 128, 128}, {0, 0, 0}, {64, 128, 0}}
    };
    assertEquals(green70test, green70);
  }

  @Test
  public void blueComponentSplitPreviewRandom() {
    this.modelV2.applySplitPreview("blue-component", "staticImage",
        "blue70", 70, null);
    int[][][] blue70 = this.modelV2.saveImage("blue70");
    int[][][] blue70test = {
        {{0, 0, 0}, {0, 0, 0}, {0, 0, 255}},
        {{128, 128, 128}, {192, 192, 192}, {64, 64, 64}},
        {{150, 150, 150}, {150, 150, 150}, {150, 50, 50}},
        {{64, 64, 64}, {128, 128, 128}, {64, 128, 0}}
    };
    assertEquals(blue70test, blue70);
  }

  @Test
  public void lumaComponentSplitPreviewRandom() {
    this.modelV2.applySplitPreview("luma-component", "staticImage",
        "luma70", 70, null);
    int[][][] luma70 = this.modelV2.saveImage("luma70");
    int[][][] luma70test = {
        {{54, 54, 54}, {182, 182, 182}, {0, 0, 255}},
        {{128, 128, 128}, {192, 192, 192}, {64, 64, 64}},
        {{175, 175, 175}, {57, 57, 57}, {150, 50, 50}},
        {{96, 96, 96}, {36, 36, 36}, {64, 128, 0}}
    };
    assertEquals(luma70test, luma70);
  }

  @Test
  public void valueComponentSplitPreviewRandom() {
    this.modelV2.applySplitPreview("value-component", "staticImage",
        "value70", 70, null);
    int[][][] value70 = this.modelV2.saveImage("value70");
    int[][][] value70test = {
        {{255, 255, 255}, {255, 255, 255}, {0, 0, 255}},
        {{128, 128, 128}, {192, 192, 192}, {64, 64, 64}},
        {{200, 200, 200}, {150, 150, 150}, {150, 50, 50}},
        {{128, 128, 128}, {128, 128, 128}, {64, 128, 0}}
    };
    assertEquals(value70test, value70);
  }

  @Test
  public void intensityComponentSplitPreviewRandom() {
    this.modelV2.applySplitPreview("intensity-component", "staticImage",
        "intensity70", 70, null);
    int[][][] intensity70 = this.modelV2.saveImage("intensity70");
    int[][][] intensity70test = {
        {{85, 85, 85}, {85, 85, 85}, {0, 0, 255}},
        {{128, 128, 128}, {192, 192, 192}, {64, 64, 64}},
        {{150, 150, 150}, {83, 83, 83}, {150, 50, 50}},
        {{64, 64, 64}, {85, 85, 85}, {64, 128, 0}}
    };
    assertEquals(intensity70test, intensity70);
  }

  @Test
  public void levelAdjustSplitPreviewRandom() {
    int[] params = {0, 100, 255};
    this.modelV2.applySplitPreview("levels-adjust", "staticImage",
        "levels70", 70, params);
    int[][][] levels70 = this.modelV2.saveImage("levels70");
    int[][][] levels70test = {
        {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}},
        {{157, 157, 157}, {213, 213, 213}, {64, 64, 64}},
        {{128, 219, 178}, {68, 68, 178}, {150, 50, 50}},
        {{0, 157, 86}, {157, 0, 157}, {64, 128, 0}}
    };
    assertEquals(levels70test, levels70);
  }

  @Test
  public void colorCorrectSplitPreviewRandom() {
    this.modelV2.applySplitPreview("color-correct", "staticImage",
        "color70", 70, null);
    int[][][] color70 = this.modelV2.saveImage("color70");
    int[][][] color70test = {
        {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}},
        {{128, 128, 128}, {192, 192, 192}, {64, 64, 64}},
        {{100, 200, 150}, {50, 50, 150}, {150, 50, 50}},
        {{0, 128, 64}, {128, 0, 128}, {64, 128, 0}}
    };
    assertEquals(color70test, color70);
  }

  @Test
  public void Compression0Test() {
    this.modelV2.applyCompress(0, "staticImage", "compress0");
    int[][][] compress0 = this.modelV2.saveImage("compress0");
    assertEquals(getStaticRGBMatrix(), compress0);
  }

  @Test
  public void Compression100Test() {
    this.modelV2.applyCompress(100, "staticImage", "compress100");
    int[][][] compress100 = this.modelV2.saveImage("compress100");
    assertEquals(new int[compress100.length][compress100[0].length][3], compress100);
  }

  @Test
  public void Compression60Test() {
    this.modelV2.applyCompress(60, "staticImage", "compress60");
    int[][][] compress60 = this.modelV2.saveImage("compress60");
    int[][][] compress60test = {
        {{214, 0, 10}, {55, 241, 10}, {42, 60, 184}},
        {{55, 92, 138}, {214, 146, 138}, {42, 60, 56}},
        {{79, 194, 129}, {79, 44, 129}, {97, 60, 65}},
        {{79, 194, 129}, {79, 44, 129}, {97, 60, 65}}
    };
    assertEquals(compress60test, compress60);
  }

  @Test
  public void TestHistogramDimension() {
    this.modelV2.applyHistogram("orgImage", "hist");
    int[][][] hist = this.modelV2.saveImage("hist");
    int height = hist.length;
    int width = hist[0].length;
    assertEquals(256, height);
    assertEquals(256, width);
  }

  @Test
  public void TestHistogramComponent() {
    int[][][] matrix = {
        {{50, 20, 65}, {50, 20, 65}, {50, 20, 65}},
        {{50, 20, 65}, {50, 20, 65}, {50, 20, 65}},
        {{50, 20, 65}, {50, 20, 65}, {50, 20, 65}},
    };
    this.modelV2.loadImage("temp", matrix);
    this.modelV2.applyHistogram("temp", "hist");
    int[][][] hist = this.modelV2.saveImage("hist");

    for (int i = 1; i < 255; i++) {
      for (int j = 1; j < 255; j++) {
        if (j == 50 || j == 51) {
          assertEquals(hist[i][j][0], 255);
          assertEquals(hist[i][j][1], 0);
          assertEquals(hist[i][j][2], 0);
        } else if (j == 20 || j == 21) {
          assertEquals(hist[i][j][0], 0);
          assertEquals(hist[i][j][1], 255);
          assertEquals(hist[i][j][2], 0);
        } else if (j == 65 || j == 66) {
          assertEquals(hist[i][j][0], 0);
          assertEquals(hist[i][j][1], 0);
          assertEquals(hist[i][j][2], 255);
        } else {
          if (i % 15 == 0 || j % 15 == 0) {
            assertEquals(hist[i][j][0], 170);
            assertEquals(hist[i][j][1], 170);
            assertEquals(hist[i][j][2], 170);
          } else {
            assertEquals(hist[i][j][0], 255);
            assertEquals(hist[i][j][1], 255);
            assertEquals(hist[i][j][2], 255);
          }
        }
      }
    }
  }

}