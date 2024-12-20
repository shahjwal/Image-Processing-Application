package model;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.Before;
import org.junit.Test;

/**
 * A Junit Test class for ImageModelImplV3 class.
 */
public class ImageModelImplV3Test {

  ImageModelV3 modelV3;
  int[][][] initialRGB;
  int[][][] maskRGB;

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
    modelV3 = new ImageModelImplV3();
    BufferedImage initialImg = ImageIO.read(
        new File("/Users/dhyan/Downloads/group/images/manhattan-small.png"));
    BufferedImage maskImg = ImageIO.read(
        new File("/Users/dhyan/Downloads/group/test/model/testImages/mask50-50.jpg"));
    this.maskRGB = getRGBMatrix(maskImg);
    this.initialRGB = getRGBMatrix(initialImg);
    this.modelV3.loadImage("initialImg", this.initialRGB);
    this.modelV3.loadImage("maskImg", this.maskRGB);
  }

  @Test
  public void blurMask() {
    modelV3.applyMasking("blur", "initialImg", "maskImg",
        "blurMask");
    modelV3.applyBlur("initialImg", "fullBlur");
    int[][][] fullBlurRGB = modelV3.saveImage("fullBlur");
    int[][][] finalRGB = modelV3.saveImage("blurMask");
    for (int i = 0; i < finalRGB.length; i++) {
      for (int j = 0; j < finalRGB[i].length; j++) {
        if (this.maskRGB[i][j][0] == 0 && this.maskRGB[i][j][1] == 0
            && this.maskRGB[i][j][2] == 0) {
          assertEquals(finalRGB[i][j][0], fullBlurRGB[i][j][0]);
          assertEquals(finalRGB[i][j][1], fullBlurRGB[i][j][1]);
          assertEquals(finalRGB[i][j][2], fullBlurRGB[i][j][2]);
        } else {
          assertEquals(finalRGB[i][j][0], this.initialRGB[i][j][0]);
          assertEquals(finalRGB[i][j][1], this.initialRGB[i][j][1]);
          assertEquals(finalRGB[i][j][2], this.initialRGB[i][j][2]);
        }
      }
    }
  }

  @Test
  public void sharpenMask() {
    modelV3.applyMasking("sharpen", "initialImg", "maskImg",
        "sharpenMask");
    modelV3.applySharpen("initialImg", "fullSharpen");
    int[][][] fullSharpenRGB = modelV3.saveImage("fullSharpen");
    int[][][] finalRGB = modelV3.saveImage("sharpenMask");
    for (int i = 0; i < finalRGB.length; i++) {
      for (int j = 0; j < finalRGB[i].length; j++) {
        if (this.maskRGB[i][j][0] == 0 && this.maskRGB[i][j][1] == 0
            && this.maskRGB[i][j][2] == 0) {
          assertEquals(finalRGB[i][j][0], fullSharpenRGB[i][j][0]);
          assertEquals(finalRGB[i][j][1], fullSharpenRGB[i][j][1]);
          assertEquals(finalRGB[i][j][2], fullSharpenRGB[i][j][2]);
        } else {
          assertEquals(finalRGB[i][j][0], this.initialRGB[i][j][0]);
          assertEquals(finalRGB[i][j][1], this.initialRGB[i][j][1]);
          assertEquals(finalRGB[i][j][2], this.initialRGB[i][j][2]);
        }
      }
    }
  }

  @Test
  public void sepiaMask() {
    modelV3.applyMasking("sepia", "initialImg", "maskImg",
        "sepiaMask");
    modelV3.applySepia("initialImg", "fullSepia");
    int[][][] fullSepiaRGB = modelV3.saveImage("fullSepia");
    int[][][] finalRGB = modelV3.saveImage("sepiaMask");
    for (int i = 0; i < finalRGB.length; i++) {
      for (int j = 0; j < finalRGB[i].length; j++) {
        if (this.maskRGB[i][j][0] == 0 && this.maskRGB[i][j][1] == 0
            && this.maskRGB[i][j][2] == 0) {
          assertEquals(finalRGB[i][j][0], fullSepiaRGB[i][j][0]);
          assertEquals(finalRGB[i][j][1], fullSepiaRGB[i][j][1]);
          assertEquals(finalRGB[i][j][2], fullSepiaRGB[i][j][2]);
        } else {
          assertEquals(finalRGB[i][j][0], this.initialRGB[i][j][0]);
          assertEquals(finalRGB[i][j][1], this.initialRGB[i][j][1]);
          assertEquals(finalRGB[i][j][2], this.initialRGB[i][j][2]);
        }
      }
    }
  }

  @Test
  public void redComponentMask() {
    modelV3.applyMasking("red-component", "initialImg",
        "maskImg", "redMask");
    modelV3.applyComponent("initialImg", "fullRed", "red");
    int[][][] fullRedRGB = modelV3.saveImage("fullRed");
    int[][][] finalRGB = modelV3.saveImage("redMask");
    for (int i = 0; i < finalRGB.length; i++) {
      for (int j = 0; j < finalRGB[i].length; j++) {
        if (this.maskRGB[i][j][0] == 0 && this.maskRGB[i][j][1] == 0
            && this.maskRGB[i][j][2] == 0) {
          assertEquals(finalRGB[i][j][0], fullRedRGB[i][j][0]);
          assertEquals(finalRGB[i][j][1], fullRedRGB[i][j][1]);
          assertEquals(finalRGB[i][j][2], fullRedRGB[i][j][2]);
        } else {
          assertEquals(finalRGB[i][j][0], this.initialRGB[i][j][0]);
          assertEquals(finalRGB[i][j][1], this.initialRGB[i][j][1]);
          assertEquals(finalRGB[i][j][2], this.initialRGB[i][j][2]);
        }
      }
    }
  }

  @Test
  public void greenComponentMask() {
    modelV3.applyMasking("green-component", "initialImg",
        "maskImg", "greenMask");
    modelV3.applyComponent("initialImg", "fullGreen", "green");
    int[][][] fullGreenRGB = modelV3.saveImage("fullGreen");
    int[][][] finalRGB = modelV3.saveImage("greenMask");
    for (int i = 0; i < finalRGB.length; i++) {
      for (int j = 0; j < finalRGB[i].length; j++) {
        if (this.maskRGB[i][j][0] == 0 && this.maskRGB[i][j][1] == 0
            && this.maskRGB[i][j][2] == 0) {
          assertEquals(finalRGB[i][j][0], fullGreenRGB[i][j][0]);
          assertEquals(finalRGB[i][j][1], fullGreenRGB[i][j][1]);
          assertEquals(finalRGB[i][j][2], fullGreenRGB[i][j][2]);
        } else {
          assertEquals(finalRGB[i][j][0], this.initialRGB[i][j][0]);
          assertEquals(finalRGB[i][j][1], this.initialRGB[i][j][1]);
          assertEquals(finalRGB[i][j][2], this.initialRGB[i][j][2]);
        }
      }
    }
  }

  @Test
  public void blueComponentMask() {
    modelV3.applyMasking("blue-component", "initialImg",
        "maskImg", "blueMask");
    modelV3.applyComponent("initialImg", "fullBlue", "blue");
    int[][][] fullBlueRGB = modelV3.saveImage("fullBlue");
    int[][][] finalRGB = modelV3.saveImage("blueMask");
    for (int i = 0; i < finalRGB.length; i++) {
      for (int j = 0; j < finalRGB[i].length; j++) {
        if (this.maskRGB[i][j][0] == 0 && this.maskRGB[i][j][1] == 0
            && this.maskRGB[i][j][2] == 0) {
          assertEquals(finalRGB[i][j][0], fullBlueRGB[i][j][0]);
          assertEquals(finalRGB[i][j][1], fullBlueRGB[i][j][1]);
          assertEquals(finalRGB[i][j][2], fullBlueRGB[i][j][2]);
        } else {
          assertEquals(finalRGB[i][j][0], this.initialRGB[i][j][0]);
          assertEquals(finalRGB[i][j][1], this.initialRGB[i][j][1]);
          assertEquals(finalRGB[i][j][2], this.initialRGB[i][j][2]);
        }
      }
    }
  }

  @Test
  public void lumaComponentMask() {
    modelV3.applyMasking("luma-component", "initialImg",
        "maskImg", "lumaMask");
    modelV3.applyComponent("initialImg", "fullLuma", "luma");
    int[][][] fullLumaRGB = modelV3.saveImage("fullLuma");
    int[][][] finalRGB = modelV3.saveImage("lumaMask");
    for (int i = 0; i < finalRGB.length; i++) {
      for (int j = 0; j < finalRGB[i].length; j++) {
        if (this.maskRGB[i][j][0] == 0 && this.maskRGB[i][j][1] == 0
            && this.maskRGB[i][j][2] == 0) {
          assertEquals(finalRGB[i][j][0], fullLumaRGB[i][j][0]);
          assertEquals(finalRGB[i][j][1], fullLumaRGB[i][j][1]);
          assertEquals(finalRGB[i][j][2], fullLumaRGB[i][j][2]);
        } else {
          assertEquals(finalRGB[i][j][0], this.initialRGB[i][j][0]);
          assertEquals(finalRGB[i][j][1], this.initialRGB[i][j][1]);
          assertEquals(finalRGB[i][j][2], this.initialRGB[i][j][2]);
        }
      }
    }
  }

  @Test
  public void valueComponentMask() {
    modelV3.applyMasking("value-component", "initialImg",
        "maskImg", "valueMask");
    modelV3.applyComponent("initialImg", "fullValue", "value");
    int[][][] fullValueRGB = modelV3.saveImage("fullValue");
    int[][][] finalRGB = modelV3.saveImage("valueMask");
    for (int i = 0; i < finalRGB.length; i++) {
      for (int j = 0; j < finalRGB[i].length; j++) {
        if (this.maskRGB[i][j][0] == 0 && this.maskRGB[i][j][1] == 0
            && this.maskRGB[i][j][2] == 0) {
          assertEquals(finalRGB[i][j][0], fullValueRGB[i][j][0]);
          assertEquals(finalRGB[i][j][1], fullValueRGB[i][j][1]);
          assertEquals(finalRGB[i][j][2], fullValueRGB[i][j][2]);
        } else {
          assertEquals(finalRGB[i][j][0], this.initialRGB[i][j][0]);
          assertEquals(finalRGB[i][j][1], this.initialRGB[i][j][1]);
          assertEquals(finalRGB[i][j][2], this.initialRGB[i][j][2]);
        }
      }
    }
  }

  @Test
  public void intensityComponentMask() {
    modelV3.applyMasking("intensity-component", "initialImg",
        "maskImg", "intensityMask");
    modelV3.applyComponent("initialImg", "fullIntensity", "intensity");
    int[][][] fullIntensityRGB = modelV3.saveImage("fullIntensity");
    int[][][] finalRGB = modelV3.saveImage("intensityMask");
    for (int i = 0; i < finalRGB.length; i++) {
      for (int j = 0; j < finalRGB[i].length; j++) {
        if (this.maskRGB[i][j][0] == 0 && this.maskRGB[i][j][1] == 0
            && this.maskRGB[i][j][2] == 0) {
          assertEquals(finalRGB[i][j][0], fullIntensityRGB[i][j][0]);
          assertEquals(finalRGB[i][j][1], fullIntensityRGB[i][j][1]);
          assertEquals(finalRGB[i][j][2], fullIntensityRGB[i][j][2]);
        } else {
          assertEquals(finalRGB[i][j][0], this.initialRGB[i][j][0]);
          assertEquals(finalRGB[i][j][1], this.initialRGB[i][j][1]);
          assertEquals(finalRGB[i][j][2], this.initialRGB[i][j][2]);
        }
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void maskDimensionTest() throws IOException {
    BufferedImage newImg = ImageIO.read(new File("res/bird.png"));
    int[][][] newRGB = getRGBMatrix(newImg);
    modelV3.loadImage("newImg", newRGB);
    modelV3.applyMasking("blur", "initialImg", "newImg",
        "maskDimension");
  }

  @Test(expected = IllegalArgumentException.class)
  public void maskImageNotPresent1() {
    modelV3.applyMasking("blur", "initialImg", "maskImage1",
        "blur");
  }

  @Test(expected = IllegalArgumentException.class)
  public void maskImageNotPresent2() {
    modelV3.applyMasking("luma-component", "initialImg",
        "maskImage1", "blur");
  }

  @Test
  public void downscalingTest() {
    int[][][] matrix = {
        {{5, 2, 8}, {7, 1, 4}, {3, 6, 9}, {0, 2, 3}},
        {{4, 7, 5}, {6, 8, 3}, {1, 0, 9}, {2, 4, 6}},
        {{3, 5, 7}, {8, 9, 0}, {1, 4, 2}, {6, 3, 5}},
        {{9, 8, 6}, {4, 2, 1}, {7, 5, 3}, {0, 9, 8}}
    };

    modelV3.loadImage("matrix", matrix);
    modelV3.applyDownScaling("matrix", "newMatrix", 2, 2);
    int[][][] newMatrix = modelV3.saveImage("newMatrix");

    int[][][] expectedMatrix = {
        {{5, 2, 8}, {3, 6, 9}},
        {{3, 5, 7}, {1, 4, 2}}
    };
    assertEquals(newMatrix, expectedMatrix);
  }

  @Test(expected = IllegalArgumentException.class)
  public void DownscaleFailTest1() {
    int[][][] matrix = {
        {{5, 2, 8}, {7, 1, 4}, {3, 6, 9}, {0, 2, 3}},
        {{4, 7, 5}, {6, 8, 3}, {1, 0, 9}, {2, 4, 6}},
        {{3, 5, 7}, {8, 9, 0}, {1, 4, 2}, {6, 3, 5}},
        {{9, 8, 6}, {4, 2, 1}, {7, 5, 3}, {0, 9, 8}}
    };
    modelV3.loadImage("matrix", matrix);
    modelV3.applyDownScaling("matrix", "newMatrix", 5, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void DownscaleFailTest2() {
    int[][][] matrix = {
        {{5, 2, 8}, {7, 1, 4}, {3, 6, 9}, {0, 2, 3}},
        {{4, 7, 5}, {6, 8, 3}, {1, 0, 9}, {2, 4, 6}},
        {{3, 5, 7}, {8, 9, 0}, {1, 4, 2}, {6, 3, 5}},
        {{9, 8, 6}, {4, 2, 1}, {7, 5, 3}, {0, 9, 8}}
    };
    modelV3.loadImage("matrix", matrix);
    modelV3.applyDownScaling("matrix", "newMatrix", 2, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void DownscaleFailTest3() {
    int[][][] matrix = {
        {{5, 2, 8}, {7, 1, 4}, {3, 6, 9}, {0, 2, 3}},
        {{4, 7, 5}, {6, 8, 3}, {1, 0, 9}, {2, 4, 6}},
        {{3, 5, 7}, {8, 9, 0}, {1, 4, 2}, {6, 3, 5}},
        {{9, 8, 6}, {4, 2, 1}, {7, 5, 3}, {0, 9, 8}}
    };
    modelV3.loadImage("matrix", matrix);
    modelV3.applyDownScaling("matrix", "newMatrix", 0, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void DownscaleFailTest4() {
    int[][][] matrix = {
        {{5, 2, 8}, {7, 1, 4}, {3, 6, 9}, {0, 2, 3}},
        {{4, 7, 5}, {6, 8, 3}, {1, 0, 9}, {2, 4, 6}},
        {{3, 5, 7}, {8, 9, 0}, {1, 4, 2}, {6, 3, 5}},
        {{9, 8, 6}, {4, 2, 1}, {7, 5, 3}, {0, 9, 8}}
    };
    modelV3.loadImage("matrix", matrix);
    modelV3.applyDownScaling("matrix", "newMatrix", 2, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void DownscaleFailTest6() {
    int[][][] matrix = {
        {{5, 2, 8}, {7, 1, 4}, {3, 6, 9}, {0, 2, 3}},
        {{4, 7, 5}, {6, 8, 3}, {1, 0, 9}, {2, 4, 6}},
        {{3, 5, 7}, {8, 9, 0}, {1, 4, 2}, {6, 3, 5}},
        {{9, 8, 6}, {4, 2, 1}, {7, 5, 3}, {0, 9, 8}}
    };
    modelV3.loadImage("matrix", matrix);
    modelV3.applyDownScaling("matrix", "newMatrix", -1, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void DownscaleFailTest7() {
    int[][][] matrix = {
        {{5, 2, 8}, {7, 1, 4}, {3, 6, 9}, {0, 2, 3}},
        {{4, 7, 5}, {6, 8, 3}, {1, 0, 9}, {2, 4, 6}},
        {{3, 5, 7}, {8, 9, 0}, {1, 4, 2}, {6, 3, 5}},
        {{9, 8, 6}, {4, 2, 1}, {7, 5, 3}, {0, 9, 8}}
    };
    modelV3.loadImage("matrix", matrix);
    modelV3.applyDownScaling("matrix", "newMatrix", 2, -3);
  }

}