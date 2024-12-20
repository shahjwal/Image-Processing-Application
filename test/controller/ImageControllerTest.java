package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import model.ImageModel;
import model.ImageModelImplV2;
import org.junit.Before;
import org.junit.Test;
import view.Message;
import view.MessageView;

/**
 * A Junit Test class for Image Controller.
 */
public class ImageControllerTest {

  ImageModel model;
  Message view;
  StringBuilder out;
  List<String> log;
  ControllerInterface controller;
  Readable in;

  @Before
  public void setUp() {
    log = new ArrayList<>();
    out = new StringBuilder();
    view = new MessageView(out);
    model = new MockImageModelImplV3(log);
  }

  @Test
  public void loadCommandTest() {
    in = new StringReader("load images/manhattan-small.png man");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("loadImage with imageName: man", log.get(0));
  }

  @Test
  public void loadCommandJPGTest() {
    in = new StringReader("load res/lion.jpeg lion");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("loadImage with imageName: lion", log.get(0));
  }

  @Test
  public void loadCommandPPMTest() {
    in = new StringReader("load res/bird.ppm bird");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("loadImage with imageName: bird", log.get(0));
  }

  @Test
  public void invalidLoadExtensionTest() {
    in = new StringReader("load images/manhattan-small.pnr man");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid extension!\u001B[0m", output);
  }

  @Test
  public void noExtensionLoadTest() {
    in = new StringReader("load images/manhattan-small man");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid path without extension!\u001B[0m", output);
  }

  @Test
  public void invalidLoadCommandPathTest() {
    in = new StringReader("load image/manhattan-small.png man");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mError loading Image!\u001B[0m", output);
  }

  @Test
  public void LoadCommandTokensTest() {
    in = new StringReader("load Images/manhattan-small.png man man");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid Command parameters!\u001B[0m", output);
  }

  @Test
  public void saveCommandTest() {
    in = new StringReader("save test/controller/tempTestImages/test.ppm test");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Save image with imageName: test", log.get(0));
  }

  @Test
  public void invalidPathSaveTest() {
    int[][][] rgb = {{{1, 2, 3}, {2, 3, 4}, {4, 5, 6}}};
    model.loadImage("test", rgb);
    in = new StringReader("save testt/controller/tempTestImages/test.ppm test");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mError while saving the image!\u001B[0m", output);
  }

  @Test
  public void invalidExtensionSaveTest() {
    int[][][] rgb = {{{1, 2, 3}, {2, 3, 4}, {4, 5, 6}}};
    model.loadImage("test", rgb);
    in = new StringReader("save images/test.ppr test");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid extension!\u001B[0m", output);
  }

  @Test
  public void noExtensionSaveTest() {
    int[][][] rgb = {{{1, 2, 3}, {2, 3, 4}, {4, 5, 6}}};
    model.loadImage("test", rgb);
    in = new StringReader("save images/test test");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid path without extension!\u001B[0m", output);
  }

  @Test
  public void saveCommandLength() {
    in = new StringReader("save images/test test test");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid Command parameters!\u001B[0m", output);
  }

  @Test
  public void saveDifferentExtensionTest1() {
    in = new StringReader("save test/controller/tempTestImages/new.ppm man");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Save image with imageName: man", log.get(0));
  }

  @Test
  public void saveDifferentExtensionTest2() {
    in = new StringReader("save test/controller/"
        + "tempTestImages/new.jpg man");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Save image with imageName: man", log.get(0));
  }

  @Test
  public void saveDifferentExtensionTest3() {
    in = new StringReader("save test/controller/tempTestImages/new.ppm man");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Save image with imageName: man", log.get(0));
  }

  @Test
  public void saveDifferentExtensionTest4() {
    in = new StringReader("save test/controller/tempTestImages/new.jpg man");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Save image with imageName: man", log.get(0));
  }

  @Test
  public void saveDifferentExtensionTest5() {
    in = new StringReader("save test/controller/tempTestImages/new.png man");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Save image with imageName: man", log.get(0));
  }

  @Test
  public void saveDifferentExtensionTest6() {
    in = new StringReader("save test/controller/tempTestImages/new.png man");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Save image with imageName: man", log.get(0));
  }

  @Test
  public void blurCommandTest() {
    in = new StringReader("blur test test1");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applyBlur with imageName test and saveImage test1", log.get(0));
  }

  @Test
  public void blurLengthTest() {
    in = new StringReader("blur test test1 test2 test3 test4");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid Command parameters!\u001B[0m", output);
  }

  @Test
  public void sharpenCommandTest() {
    in = new StringReader("sharpen test test1");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applySharpen with imageName test and saveImage test1", log.get(0));
  }

  @Test
  public void sharpenLengthTest() {
    in = new StringReader("sharpen test");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid Command parameters!\u001B[0m", output);
  }

  @Test
  public void sepiaCommandTest() {
    in = new StringReader("sepia test test1");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applySepia with imageName test and saveImage test1", log.get(0));
  }

  @Test
  public void sepiaLengthTest() {
    in = new StringReader("sepia test");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid Command parameters!\u001B[0m", output);
  }

  @Test
  public void HFlipCommandTest() {
    in = new StringReader("horizontal-flip test test1");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("apply Horizontal Flip for imageName: test saving as: test1", log.get(0));
  }

  @Test
  public void HFlipLengthTest() {
    in = new StringReader("horizontal-flip test test1 test2");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid Command parameters!\u001B[0m", output);
  }

  @Test
  public void VFlipCommandTest() {
    in = new StringReader("vertical-flip test test1");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("apply Vertical Flip for imageName: test saving as: test1", log.get(0));
  }

  @Test
  public void VFlipLengthTest() {
    in = new StringReader("vertical-flip test test1 test2");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid Command parameters!\u001B[0m", output);
  }

  @Test
  public void brightenPosCommandTest() {
    in = new StringReader("brighten 100 test test1");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applyBrighten with intensity 100 and imageName "
        + "test and saveImage test1", log.get(0));
  }


  @Test
  public void brightenNegCommandTest() {
    in = new StringReader("brighten -100 test test1");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applyBrighten with intensity -100 and imageName test "
        + "and saveImage test1", log.get(0));
  }

  @Test
  public void brightenZeroCommandTest() {
    in = new StringReader("brighten 0 test test1");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applyBrighten with intensity 0 and imageName test and saveImage test1",
        log.get(0));
  }

  @Test
  public void brightenWrongIntensityTest1() {
    in = new StringReader("brighten intensity test test1");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mPlease provide a valid intensity value!\u001B[0m", output);
  }

  @Test
  public void brightenWrongIntensityTest2() {
    in = new StringReader("brighten 10.22 test test1");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mPlease provide a valid intensity value!\u001B[0m", output);
  }

  @Test
  public void brightenLengthTest() {
    in = new StringReader("brighten 10.22 test");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid Command parameters!\u001B[0m", output);
  }

  @Test
  public void redComponentTest() {
    in = new StringReader("red-component test test1");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applyComponent with type: red for imageName: test saving as: test1",
        log.get(0));
  }

  @Test
  public void greenComponentTest() {
    in = new StringReader("green-component test test1");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applyComponent with type: green for imageName: test saving as: test1",
        log.get(0));
  }

  @Test
  public void blueComponentTest() {
    in = new StringReader("blue-component test test1");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applyComponent with type: blue for imageName: test saving as: test1",
        log.get(0));
  }

  @Test
  public void lumaComponentTest() {
    in = new StringReader("luma-component test test1");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applyComponent with type: luma for imageName: test saving as: test1",
        log.get(0));
  }

  @Test
  public void valueComponentTest() {
    in = new StringReader("value-component test test1");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applyComponent with type: value for imageName: test saving as: test1",
        log.get(0));
  }

  @Test
  public void intensityComponentTest() {
    in = new StringReader("intensity-component test test1");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applyComponent with type: intensity for imageName: "
            + "test saving as: test1",
        log.get(0));
  }

  @Test
  public void redComponentLengthTest() {
    in = new StringReader("red-component test test1 test2 test3 test4");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid Command parameters!\u001B[0m", output);
  }

  @Test
  public void greenComponentLengthTest() {
    in = new StringReader("green-component test test1 test2 test3 test4");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid Command parameters!\u001B[0m", output);
  }

  @Test
  public void blueComponentLengthTest() {
    in = new StringReader("blue-component test2");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid Command parameters!\u001B[0m", output);
  }

  @Test
  public void lumaComponentLengthTest() {
    in = new StringReader("luma-component test test1 test2 test3 test4");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid Command parameters!\u001B[0m", output);
  }

  @Test
  public void valueComponentLengthTest() {
    in = new StringReader("value-component test test1 test2 test3 test4");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid Command parameters!\u001B[0m", output);
  }

  @Test
  public void intensityComponentLengthTest() {
    in = new StringReader("intensity-component test test1 test2 test3 test4");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid Command parameters!\u001B[0m", output);
  }

  @Test
  public void RGBSplitCommandTest() {
    in = new StringReader("rgb-split test test1 test2 test3");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applySplit for imageName: test into redImage: test1, "
        + "greenImage: test2, blueImage: test3", log.get(0));
  }

  @Test
  public void RGBSplitLengthTest() {
    in = new StringReader("rgb-split test test1 test2 test3 test4");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid Command parameters!\u001B[0m", output);
  }

  @Test
  public void RGBCombineCommandTest() {
    in = new StringReader("rgb-combine test test1 test2 test3");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applyCombine for redImage: test1, greenImage: test2,"
        + " blueImage: test3 saving as: test", log.get(0));
  }

  @Test
  public void RGBCombineLengthTest() {
    in = new StringReader("rgb-combine test test1 test2 test3 test4");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid Command parameters!\u001B[0m", output);
  }


  @Test
  public void runCommandTest() {
    in = new StringReader("run test/controller/testTrueScript.txt");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("loadImage with imageName: man", log.get(0));
    assertEquals("loadImage with imageName: bird", log.get(1));
    assertEquals("loadImage with imageName: lion", log.get(2));
    assertEquals("applyBrighten with intensity 100 and imageName man and saveImage "
        + "bright", log.get(3));
    assertEquals("applyBlur with imageName man and saveImage blur", log.get(4));
    assertEquals("applySharpen with imageName man and saveImage sharpen", log.get(5));
    assertEquals("apply Horizontal Flip for imageName: man saving as: VFlip", log.get(6));
    assertEquals("apply Vertical Flip for imageName: man saving as: VFlip", log.get(7));
    assertEquals("applySepia with imageName man and saveImage sepia", log.get(8));
    assertEquals("applyComponent with type: red for imageName: man saving as: redC",
        log.get(9));
    assertEquals("applyComponent with type: green for imageName: man saving as: greenC",
        log.get(10));
    assertEquals("applyComponent with type: blue for imageName: man saving as: blueC",
        log.get(11));
    assertEquals("applyComponent with type: luma for imageName: man saving as: lumaC",
        log.get(12));
    assertEquals("applyComponent with type: value for imageName: man saving as: valueC",
        log.get(13));
    assertEquals("applyComponent with type: intensity for imageName: man saving as: "
        + "intensityC", log.get(14));
    assertEquals("applySplit for imageName: man into redImage: red, greenImage: green, "
        + "blueImage: blue", log.get(15));
    assertEquals("applyCombine for redImage: red, greenImage: green, blueImage: "
        + "blue saving as: new", log.get(16));
    assertEquals("applyBrighten with intensity 50 and imageName bird and saveImage "
        + "birdbright", log.get(17));
    assertEquals("applyBlur with imageName lion and saveImage blurlion", log.get(18));
    assertEquals("applySharpen with imageName bird and saveImage blurlion", log.get(19));
  }

  @Test
  public void runCommandFalseTest() {
    in = new StringReader("run test/controller/testFalseScript.txt");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Enter commands (enter 'exit' to terminate the program) : \n" +
        "\u001B[33mload command executed successfully!\u001B[0m\n" +
        "\u001B[91mPlease provide a valid intensity value!\u001B[0m\n" +
        "\u001B[91mCommand does not exist :blurr\u001B[0m\n" +
        "\u001B[33msharpen command executed successfully!\u001B[0m\n" +
        "\u001B[91mCommand does not exist :flip-horizontal\u001B[0m\n" +
        "\u001B[33mvertical-flip command executed successfully!\u001B[0m\n" +
        "\u001B[33msepia command executed successfully!\u001B[0m\n" +
        "\u001B[91mCommand does not exist :red-componen\u001B[0m\n" +
        "\u001B[91mCommand does not exist :component\u001B[0m\n" +
        "\u001B[91mCommand does not exist :bluee-component\u001B[0m\n" +
        "\u001B[91mCommand does not exist :puma-component\u001B[0m\n" +
        "\u001B[91mInvalid Command parameters!\u001B[0m\n" +
        "\u001B[91mInvalid Command parameters!\u001B[0m\n" +
        "\u001B[91mInvalid Command parameters!\u001B[0m\n" +
        "\u001B[91mInvalid Command parameters!\u001B[0m\n", out.toString());
  }

  @Test
  public void CompressCommandTest() {
    in = new StringReader("compress 60 man save");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applying Compression with percentage: 60 on imageName man "
        + "and saveImage save", log.get(0));
  }

  @Test
  public void CompressCommandLengthTest() {
    in = new StringReader("compress 60 man save new");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid Command parameters!\u001B[0m", output);
  }

  @Test
  public void CompressPercentageTest() {
    in = new StringReader("compress 160 man save");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mPercentage must be between 0 and 100\u001B[0m", output);
  }

  @Test
  public void CompressPercentageNegTest() {
    in = new StringReader("compress -100 man save");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mPercentage must be between 0 and 100\u001B[0m", output);
  }

  @Test
  public void CompressPercentageInvalidTest() {
    in = new StringReader("compress twenty man save");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mPercentage must be an integer\u001B[0m", output);
  }

  @Test
  public void HistogramCommandTest() {
    in = new StringReader("histogram man save");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applying Histogram on imageName man and saveImage save", log.get(0));
  }

  @Test
  public void HistogramCommandLengthTest() {
    in = new StringReader("histogram man save new");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid Command parameters!\u001B[0m", output);
  }

  @Test
  public void LevelAdjustCommandTest() {
    in = new StringReader("levels-adjust 0 100 255 man save");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applying LevelAdjustment with black: 0, mid: 100 and "
        + "white: 255 on imageName man and saveImage save", log.get(0));
  }

  @Test
  public void levelsAdjustCommandLengthTest() {
    in = new StringReader("levels-adjust 0 100 255 man save new");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid Command parameters!\u001B[0m", output);
  }

  @Test
  public void levelsAdjustCommandInvalidParameterTest1() {
    in = new StringReader("levels-adjust zero 100 255 man save");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mParameters must be integers\u001B[0m", output);
  }

  @Test
  public void levelsAdjustCommandInvalidParameterTest2() {
    in = new StringReader("levels-adjust zero 100 twoHundred man save");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mParameters must be integers\u001B[0m", output);
  }

  @Test
  public void ColorCorrectionTest() {
    in = new StringReader("color-correct man save");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applying ColorCorrection on imageName man and saveImage save"
        , log.get(0));
  }

  @Test
  public void colorCorrectCommandLengthTest() {
    in = new StringReader("color-correct man save new");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid Command parameters!\u001B[0m", output);
  }

  @Test
  public void blurSplitPreviewTest() {
    in = new StringReader("blur man new split 70");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Applying SplitPreview with operationName: blur, percentage: 70.0, "
        + "imageName: man, saveImage: new, params: none", log.get(0));
  }

  @Test
  public void sharpenSplitPreviewTest() {
    in = new StringReader("sharpen man new split 70");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Applying SplitPreview with operationName: sharpen, percentage: 70.0, "
        + "imageName: man, saveImage: new, params: none", log.get(0));
  }

  @Test
  public void sepiaSplitPreviewTest() {
    in = new StringReader("sepia man new split 70");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Applying SplitPreview with operationName: sepia, percentage: 70.0, "
        + "imageName: man, saveImage: new, params: none", log.get(0));
  }

  @Test
  public void redComponentSplitPreviewTest() {
    in = new StringReader("red-component man new split 70");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Applying SplitPreview with operationName: red-component, "
        + "percentage: 70.0, "
        + "imageName: man, saveImage: new, params: none", log.get(0));
  }

  @Test
  public void greenComponentSplitPreviewTest() {
    in = new StringReader("green-component man new split 70");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Applying SplitPreview with operationName: green-component, "
        + "percentage: 70.0, "
        + "imageName: man, saveImage: new, params: none", log.get(0));
  }

  @Test
  public void blueComponentSplitPreviewTest() {
    in = new StringReader("blue-component man new split 70");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Applying SplitPreview with operationName: blue-component, "
        + "percentage: 70.0, "
        + "imageName: man, saveImage: new, params: none", log.get(0));
  }

  @Test
  public void lumaComponentSplitPreviewTest() {
    in = new StringReader("luma-component man new split 70");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Applying SplitPreview with operationName: luma-component, "
        + "percentage: 70.0, "
        + "imageName: man, saveImage: new, params: none", log.get(0));
  }

  @Test
  public void valueComponentSplitPreviewTest() {
    in = new StringReader("value-component man new split 70");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Applying SplitPreview with operationName: value-component, "
        + "percentage: 70.0, "
        + "imageName: man, saveImage: new, params: none", log.get(0));
  }

  @Test
  public void intensityComponentSplitPreviewTest() {
    in = new StringReader("intensity-component man new split 70");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Applying SplitPreview with operationName: intensity-component, "
        + "percentage: 70.0, "
        + "imageName: man, saveImage: new, params: none", log.get(0));
  }

  @Test
  public void levelAdjustSplitPreviewTest() {
    in = new StringReader("levels-adjust 0 100 255 man new split 70");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Applying SplitPreview with operationName: levels-adjust, "
        + "percentage: 70.0, "
        + "imageName: man, saveImage: new, params: [0, 100, 255]", log.get(0));
  }

  @Test
  public void colorCorrectSplitPreviewTest() {
    in = new StringReader("color-correct man new split 70");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Applying SplitPreview with operationName: color-correct, "
        + "percentage: 70.0, "
        + "imageName: man, saveImage: new, params: none", log.get(0));
  }

  @Test
  public void SplitPreviewInvalidCommand() {
    in = new StringReader("blur man new splitt 10");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid Command\u001B[0m", output);
  }

  @Test
  public void SplitPreviewInvalidPercentagePosCommand() {
    in = new StringReader("blur man new split 105");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mPercentage must be between 0 and 100\u001B[0m", output);
  }

  @Test
  public void SplitPreviewInvalidPercentageNegCommand() {
    in = new StringReader("blur man new split -105");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mPercentage must be between 0 and 100\u001B[0m", output);
  }

  @Test
  public void SplitPreviewInvalidPercentageCommand() {
    in = new StringReader("blur man new split forty");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mParameters must be integers\u001B[0m", output);
  }

  @Test
  public void SplitPreviewInvalidLevelsCommand() {
    in = new StringReader("levels-adjust 0 100 vre man new split 90");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mParameters must be integers\u001B[0m", output);
  }

  @Test
  public void SplitPreviewLengthTest() {
    in = new StringReader("blur man new split 90 100");
    controller = new ImageController(model, view, in);
    controller.start();
    String output = out.toString().trim().split("\n")[1];
    assertEquals("\u001B[91mInvalid Command parameters!\u001B[0m", output);
  }

  @Test
  public void blurSplitDoublePreviewTest() {
    in = new StringReader("blur man new split 70.25");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Applying SplitPreview with operationName: blur, percentage: 70.25, "
        + "imageName: man, saveImage: new, params: none", log.get(0));
  }

  @Test
  public void sharpenSplitDoublePreviewTest() {
    in = new StringReader("sharpen man new split 70.25");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Applying SplitPreview with operationName: sharpen, percentage: 70.25, "
        + "imageName: man, saveImage: new, params: none", log.get(0));
  }

  @Test
  public void sepiaSplitDoublePreviewTest() {
    in = new StringReader("sepia man new split 70.25");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Applying SplitPreview with operationName: sepia, percentage: 70.25, "
        + "imageName: man, saveImage: new, params: none", log.get(0));
  }

  @Test
  public void redComponentSplitDoublePreviewTest() {
    in = new StringReader("red-component man new split 70.25");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Applying SplitPreview with operationName: red-component, "
        + "percentage: 70.25, "
        + "imageName: man, saveImage: new, params: none", log.get(0));
  }

  @Test
  public void greenComponentSplitDoublePreviewTest() {
    in = new StringReader("green-component man new split 70.25");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Applying SplitPreview with operationName: green-component, "
        + "percentage: 70.25, "
        + "imageName: man, saveImage: new, params: none", log.get(0));
  }

  @Test
  public void blueComponentSplitDoublePreviewTest() {
    in = new StringReader("blue-component man new split 70.25");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Applying SplitPreview with operationName: blue-component, "
        + "percentage: 70.25, "
        + "imageName: man, saveImage: new, params: none", log.get(0));
  }

  @Test
  public void lumaComponentSplitDoublePreviewTest() {
    in = new StringReader("luma-component man new split 70.25");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Applying SplitPreview with operationName: luma-component, "
        + "percentage: 70.25, "
        + "imageName: man, saveImage: new, params: none", log.get(0));
  }

  @Test
  public void valueComponentSplitDoublePreviewTest() {
    in = new StringReader("value-component man new split 70.25");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Applying SplitPreview with operationName: value-component, "
        + "percentage: 70.25, "
        + "imageName: man, saveImage: new, params: none", log.get(0));
  }

  @Test
  public void intensityComponentSplitDoublePreviewTest() {
    in = new StringReader("intensity-component man new split 70.25");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Applying SplitPreview with operationName: intensity-component, "
        + "percentage: 70.25, "
        + "imageName: man, saveImage: new, params: none", log.get(0));
  }

  @Test
  public void levelAdjustSplitDoublePreviewTest() {
    in = new StringReader("levels-adjust 0 100 255 man new split 70.25");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Applying SplitPreview with operationName: levels-adjust, "
        + "percentage: 70.25, "
        + "imageName: man, saveImage: new, params: [0, 100, 255]", log.get(0));
  }

  @Test
  public void colorCorrectSplitDoublePreviewTest() {
    in = new StringReader("color-correct man new split 70.25");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("Applying SplitPreview with operationName: color-correct, "
        + "percentage: 70.25, "
        + "imageName: man, saveImage: new, params: none", log.get(0));
  }


  @Test
  public void IntegratedSaveTest() {
    ImageModel model2 = new ImageModelImplV2();
    in = new StringReader("load images/manhattan-small.png man\nsave res/manhattan-small.png man"
        + "\nsave res/manhattan-small.jpg man \nsave res/manhattan-small.ppm man");
    controller = new ImageController(model2, view, in);
    controller.start();
    assertTrue(Files.exists(Paths.get("res/manhattan-small.png")));
    assertTrue(Files.exists(Paths.get("res/manhattan-small.jpg")));
    assertTrue(Files.exists(Paths.get("res/manhattan-small.ppm")));
  }

  @Test
  public void IntegratedLoadTest2() {
    assertTrue(Files.exists(Paths.get("test/controller/tempTestImages/new.jpg")));
    assertTrue(Files.exists(Paths.get("test/controller/tempTestImages/new.png")));
    assertTrue(Files.exists(Paths.get("test/controller/tempTestImages/test.ppm")));
    ImageModel model2 = new ImageModelImplV2();
    in = new StringReader("load test/controller/tempTestImages/new.jpg new1"
        + "\nload test/controller/tempTestImages/new.png new2"
        + "\nload test/controller/tempTestImages/test.ppm new3");
    controller = new ImageController(model2, view, in);
    controller.start();
    assertEquals("\u001B[33mload command executed successfully!\u001B[0m",
        out.toString().split("\n")[1]);
    assertEquals("\u001B[33mload command executed successfully!\u001B[0m",
        out.toString().split("\n")[2]);
  }

  @Test
  public void blurMaskTest() {
    in = new StringReader("blur man mask save");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applying masking on image: man with save image name: save with "
        + "mask image: mask with operation type: blur",log.get(0));
  }

  @Test
  public void sharpenMaskTest() {
    in = new StringReader("sharpen man mask save");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applying masking on image: man with save image name: save with "
        + "mask image: mask with operation type: sharpen",log.get(0));
  }

  @Test
  public void sepiaMaskTest() {
    in = new StringReader("sepia man mask save");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applying masking on image: man with save image name: save with "
        + "mask image: mask with operation type: sepia",log.get(0));
  }

  @Test
  public void redComponentMaskTest() {
    in = new StringReader("red-component man mask save");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applying masking on image: man with save image name: save with "
        + "mask image: mask with operation type: red-component",log.get(0));
  }

  @Test
  public void greenComponentMaskTest() {
    in = new StringReader("green-component man mask save");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applying masking on image: man with save image name: save with "
        + "mask image: mask with operation type: green-component",log.get(0));
  }

  @Test
  public void blueComponentMaskTest() {
    in = new StringReader("blue-component man mask save");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applying masking on image: man with save image name: save with "
        + "mask image: mask with operation type: blue-component",log.get(0));
  }

  @Test
  public void lumaComponentMaskTest() {
    in = new StringReader("luma-component man mask save");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applying masking on image: man with save image name: save with "
        + "mask image: mask with operation type: luma-component",log.get(0));
  }

  @Test
  public void intensityComponentMaskTest() {
    in = new StringReader("intensity-component man mask save");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applying masking on image: man with save image name: save with "
        + "mask image: mask with operation type: intensity-component",log.get(0));
  }

  @Test
  public void valueComponentMaskTest() {
    in = new StringReader("value-component man mask save");
    controller = new ImageController(model, view, in);
    controller.start();
    assertEquals("applying masking on image: man with save image name: save with "
        + "mask image: mask with operation type: value-component",log.get(0));
  }

}


