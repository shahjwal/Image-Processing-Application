package controller;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import model.ImageModel;
import org.junit.Before;
import org.junit.Test;
import view.IView;

/**
 * This is the test class for the GUI controller.
 */
public class MVCControllerTest {

  IView view;
  ImageModel model;
  List<String> log;
  ControllerInterface controller;
  Features features;
  List<String> expected;

  @Before
  public void setUp() {
    log = new ArrayList<>();
    model = new MockImageModelImplV3(log);
    view = new MockGUIView(log);
    controller = new MVCController(model, view);
    features = new FeaturesImpl(model, view);
    expected = new ArrayList<>();
  }

  @Test
  public void testFeaturesAdd() {
    controller.start();
    assertEquals("features object has been passed to the view successfully", log.get(0));
  }

  @Test
  public void testLoadImage() {
    features.loadImage();
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    assertEquals(expected, log);
  }

  @Test
  public void testSaveImage() {
    features.loadImage();
    features.saveImage("manhattan-smallSave");
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("Saving file");
    expected.add("Save image with imageName: manhattan-smallSave");
    assertEquals(expected, log);
  }

  @Test
  public void showSaveError() {
    features.saveImage(null);
    expected.add("showing error : Image not loaded!");
    assertEquals(expected, log);
  }

  @Test
  public void TestBlurTrue() {
    features.loadImage();
    features.blurImage("manhattan-small");
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("applyBlur with imageName manhattan-small and saveImage manhattan-small_blur");
    expected.add("Save image with imageName: manhattan-small_blur");
    expected.add("displaying image in GUI : manhattan-small_blur");
    expected.add(
        "applying Histogram on imageName manhattan-small_blur and saveImage "
            + "Histogrammanhattan-small_blur");
    expected.add("Save image with imageName: Histogrammanhattan-small_blur");
    expected.add("displaying histogram in GUI : manhattan-small_blur");
    assertEquals(expected, log);
  }

  @Test
  public void TestBlurFalse() {
    features.blurImage(null);
    expected.add("showing error : Image not loaded!");
    assertEquals(expected, log);
  }

  @Test
  public void TestBlurPreview() {
    features.loadImage();
    features.splitPreviewImage("blur", "manhattan", "50",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "Applying SplitPreview with operationName: blur, percentage: 50.0, imageName: manhattan, "
            + "saveImage: manhattanblur_splitPreview, params: none");
    expected.add("Save image with imageName: manhattanblur_splitPreview");
    expected.add("displaying in preview : manhattanblur_splitPreview");
    assertEquals(expected, log);
  }

  @Test
  public void TestBlurInvalidPercentage1() {
    features.loadImage();
    features.splitPreviewImage("blur", "manhattan", "-50",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Percentage must be between 0 and 100");
    assertEquals(expected, log);
  }

  @Test
  public void TestBlurInvalidPercentage2() {
    features.loadImage();
    features.splitPreviewImage("blur", "manhattan", "150",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Percentage must be between 0 and 100");
    assertEquals(expected, log);
  }

  @Test
  public void TestBlurInvalidPercentage() {
    features.loadImage();
    features.splitPreviewImage("blur", "manhattan", "five",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Parameters must be integers");
    assertEquals(expected, log);
  }

  @Test
  public void TestSharpenTrue() {
    features.loadImage();
    features.sharpenImage("manhattan-small");
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "applySharpen with imageName manhattan-small and saveImage manhattan-small_sharpen");
    expected.add("Save image with imageName: manhattan-small_sharpen");
    expected.add("displaying image in GUI : manhattan-small_sharpen");
    expected.add(
        "applying Histogram on imageName manhattan-small_sharpen and saveImage "
            + "Histogrammanhattan-small_sharpen");
    expected.add("Save image with imageName: Histogrammanhattan-small_sharpen");
    expected.add("displaying histogram in GUI : manhattan-small_sharpen");
    assertEquals(expected, log);
  }

  @Test
  public void TestSharpenFalse() {
    features.sharpenImage(null);
    expected.add("showing error : Image not loaded!");
    assertEquals(expected, log);
  }

  @Test
  public void TestSharpenPreview() {
    features.loadImage();
    features.splitPreviewImage("sharpen", "manhattan", "50",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "Applying SplitPreview with operationName: sharpen, percentage: 50.0, imageName: "
            + "manhattan, saveImage: manhattansharpen_splitPreview, params: none");
    expected.add("Save image with imageName: manhattansharpen_splitPreview");
    expected.add("displaying in preview : manhattansharpen_splitPreview");
    assertEquals(expected, log);
  }

  @Test
  public void TestSharpenInvalidPercentage1() {
    features.loadImage();
    features.splitPreviewImage("sharpen", "manhattan", "-50",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Percentage must be between 0 and 100");
    assertEquals(expected, log);
  }

  @Test
  public void TestSharpenInvalidPercentage2() {
    features.loadImage();
    features.splitPreviewImage("sharpen", "manhattan", "150",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Percentage must be between 0 and 100");
    assertEquals(expected, log);
  }

  @Test
  public void TestSharpenInvalidPercentage() {
    features.loadImage();
    features.splitPreviewImage("sharpen", "manhattan", "five",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Parameters must be integers");
    assertEquals(expected, log);
  }

  @Test
  public void TestSepiaTrue() {
    features.loadImage();
    features.sepiaImage("manhattan-small");
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("applySepia with imageName manhattan-small and saveImage manhattan-small_sepia");
    expected.add("Save image with imageName: manhattan-small_sepia");
    expected.add("displaying image in GUI : manhattan-small_sepia");
    expected.add(
        "applying Histogram on imageName manhattan-small_sepia and saveImage "
            + "Histogrammanhattan-small_sepia");
    expected.add("Save image with imageName: Histogrammanhattan-small_sepia");
    expected.add("displaying histogram in GUI : manhattan-small_sepia");
    assertEquals(expected, log);
  }

  @Test
  public void TestSepiaFalse() {
    features.sepiaImage(null);
    expected.add("showing error : Image not loaded!");
    assertEquals(expected, log);
  }

  @Test
  public void TestSepiaPreview() {
    features.loadImage();
    features.splitPreviewImage("sepia", "manhattan", "50",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "Applying SplitPreview with operationName: sepia, percentage: 50.0, imageName: "
            + "manhattan, saveImage: manhattansepia_splitPreview, params: none");
    expected.add("Save image with imageName: manhattansepia_splitPreview");
    expected.add("displaying in preview : manhattansepia_splitPreview");
    assertEquals(expected, log);
  }

  @Test
  public void TestSepiaInvalidPercentage1() {
    features.loadImage();
    features.splitPreviewImage("sepia", "manhattan", "-50",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Percentage must be between 0 and 100");
    assertEquals(expected, log);
  }

  @Test
  public void TestSepiaInvalidPercentage2() {
    features.loadImage();
    features.splitPreviewImage("sepia", "manhattan", "150",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Percentage must be between 0 and 100");
    assertEquals(expected, log);
  }

  @Test
  public void TestSepiaInvalidPercentage() {
    features.loadImage();
    features.splitPreviewImage("sepia", "manhattan", "five",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Parameters must be integers");
    assertEquals(expected, log);
  }

  @Test
  public void TestHorizontalFlipTrue() {
    features.loadImage();
    features.flipImage("horizontal-flip", "manhattan-small");
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "apply Horizontal Flip for imageName: manhattan-small saving as: "
            + "manhattan-smallhorizontal-flip");
    expected.add("Save image with imageName: manhattan-smallhorizontal-flip");
    expected.add("displaying image in GUI : manhattan-smallhorizontal-flip");
    expected.add(
        "applying Histogram on imageName manhattan-smallhorizontal-flip and saveImage "
            + "Histogrammanhattan-smallhorizontal-flip");
    expected.add("Save image with imageName: Histogrammanhattan-smallhorizontal-flip");
    expected.add("displaying histogram in GUI : manhattan-smallhorizontal-flip");
    assertEquals(expected, log);
  }

  @Test
  public void TestHorizontalFlipFalse() {
    features.flipImage("horizontal-flip", null);
    expected.add("showing error : Image not loaded!");
    assertEquals(expected, log);
  }

  @Test
  public void TestVerticalFlipTrue() {
    features.loadImage();
    features.flipImage("vertical-flip", "manhattan-small");
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "apply Vertical Flip for imageName: manhattan-small saving as: "
            + "manhattan-smallvertical-flip");
    expected.add("Save image with imageName: manhattan-smallvertical-flip");
    expected.add("displaying image in GUI : manhattan-smallvertical-flip");
    expected.add(
        "applying Histogram on imageName manhattan-smallvertical-flip and saveImage "
            + "Histogrammanhattan-smallvertical-flip");
    expected.add("Save image with imageName: Histogrammanhattan-smallvertical-flip");
    expected.add("displaying histogram in GUI : manhattan-smallvertical-flip");
    assertEquals(expected, log);
  }

  @Test
  public void TestVerticalFlipFalse() {
    features.flipImage("vertical-flip", null);
    expected.add("showing error : Image not loaded!");
    assertEquals(expected, log);
  }

  @Test
  public void TestLumaComponentTrue() {
    features.loadImage();
    features.componentImage("luma-component", "manhattan-small");
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "applyComponent with type: luma for imageName: manhattan-small saving as:"
            + " manhattan-smallluma-component");
    expected.add("Save image with imageName: manhattan-smallluma-component");
    expected.add("displaying image in GUI : manhattan-smallluma-component");
    expected.add(
        "applying Histogram on imageName manhattan-smallluma-component and saveImage "
            + "Histogrammanhattan-smallluma-component");
    expected.add("Save image with imageName: Histogrammanhattan-smallluma-component");
    expected.add("displaying histogram in GUI : manhattan-smallluma-component");
    assertEquals(expected, log);
  }

  @Test
  public void TestLumaComponentFalse() {
    features.componentImage("luma-component", null);
    expected.add("showing error : Image not loaded!");
    assertEquals(expected, log);
  }

  @Test
  public void TestLumaPreview() {
    features.loadImage();
    features.splitPreviewImage("luma-component", "manhattan",
        "50", null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "Applying SplitPreview with operationName: luma-component, percentage: 50.0, "
            + "imageName: manhattan, saveImage: "
            + "manhattanluma-component_splitPreview, params: none");
    expected.add("Save image with imageName: manhattanluma-component_splitPreview");
    expected.add("displaying in preview : manhattanluma-component_splitPreview");
    assertEquals(expected, log);
  }

  @Test
  public void TestLumaInvalidPercentage1() {
    features.loadImage();
    features.splitPreviewImage("luma-component", "manhattan", "-50",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Percentage must be between 0 and 100");
    assertEquals(expected, log);
  }

  @Test
  public void TestLumaInvalidPercentage2() {
    features.loadImage();
    features.splitPreviewImage("luma-component", "manhattan", "150",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Percentage must be between 0 and 100");
    assertEquals(expected, log);
  }

  @Test
  public void TestLumaInvalidPercentage3() {
    features.loadImage();
    features.splitPreviewImage("luma-component", "manhattan", "five",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Parameters must be integers");
    assertEquals(expected, log);
  }

  @Test
  public void TestIntensityComponentTrue() {
    features.loadImage();
    features.componentImage("intensity-component", "manhattan-small");
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "applyComponent with type: intensity for imageName: manhattan-small saving as: "
            + "manhattan-smallintensity-component");
    expected.add("Save image with imageName: manhattan-smallintensity-component");
    expected.add("displaying image in GUI : manhattan-smallintensity-component");
    expected.add(
        "applying Histogram on imageName manhattan-smallintensity-component and saveImage "
            + "Histogrammanhattan-smallintensity-component");
    expected.add("Save image with imageName: Histogrammanhattan-smallintensity-component");
    expected.add("displaying histogram in GUI : manhattan-smallintensity-component");
    assertEquals(expected, log);
  }

  @Test
  public void TestIntensityComponentFalse() {
    features.componentImage("intensity-component", null);
    expected.add("showing error : Image not loaded!");
    assertEquals(expected, log);
  }

  @Test
  public void TestIntensityPreview() {
    features.loadImage();
    features.splitPreviewImage("intensity-component", "manhattan",
        "50", null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "Applying SplitPreview with operationName: intensity-component, percentage: 50.0, "
            + "imageName: manhattan, saveImage: "
            + "manhattanintensity-component_splitPreview, params: none");
    expected.add("Save image with imageName: manhattanintensity-component_splitPreview");
    expected.add("displaying in preview : manhattanintensity-component_splitPreview");
    assertEquals(expected, log);
  }

  @Test
  public void TestIntensityInvalidPercentage1() {
    features.loadImage();
    features.splitPreviewImage("intensity-component", "manhattan",
        "-50", null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Percentage must be between 0 and 100");
    assertEquals(expected, log);
  }

  @Test
  public void TestIntensityInvalidPercentage2() {
    features.loadImage();
    features.splitPreviewImage("intensity-component", "manhattan",
        "150", null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Percentage must be between 0 and 100");
    assertEquals(expected, log);
  }

  @Test
  public void TestIntensityInvalidPercentage3() {
    features.loadImage();
    features.splitPreviewImage("intensity-component", "manhattan",
        "five", null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Parameters must be integers");
    assertEquals(expected, log);
  }

  @Test
  public void TestValueComponentTrue() {
    features.loadImage();
    features.componentImage("value-component", "manhattan-small");
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "applyComponent with type: value for imageName: manhattan-small saving as: "
            + "manhattan-smallvalue-component");
    expected.add("Save image with imageName: manhattan-smallvalue-component");
    expected.add("displaying image in GUI : manhattan-smallvalue-component");
    expected.add(
        "applying Histogram on imageName manhattan-smallvalue-component and saveImage"
            + " Histogrammanhattan-smallvalue-component");
    expected.add("Save image with imageName: Histogrammanhattan-smallvalue-component");
    expected.add("displaying histogram in GUI : manhattan-smallvalue-component");
    assertEquals(expected, log);
  }

  @Test
  public void TestValueComponentFalse() {
    features.componentImage("value-component", null);
    expected.add("showing error : Image not loaded!");
    assertEquals(expected, log);
  }

  @Test
  public void TestValuePreview() {
    features.loadImage();
    features.splitPreviewImage("value-component", "manhattan",
        "50", null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "Applying SplitPreview with operationName: value-component, percentage: 50.0,"
            + " imageName: manhattan, saveImage: manhattanvalue-component_splitPreview"
            + ", params: none");
    expected.add("Save image with imageName: manhattanvalue-component_splitPreview");
    expected.add("displaying in preview : manhattanvalue-component_splitPreview");
    assertEquals(expected, log);
  }

  @Test
  public void TestValueInvalidPercentage1() {
    features.loadImage();
    features.splitPreviewImage("value-component", "manhattan",
        "-50", null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Percentage must be between 0 and 100");
    assertEquals(expected, log);
  }

  @Test
  public void TestValueInvalidPercentage2() {
    features.loadImage();
    features.splitPreviewImage("value-component", "manhattan",
        "150", null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Percentage must be between 0 and 100");
    assertEquals(expected, log);
  }

  @Test
  public void TestValueInvalidPercentage3() {
    features.loadImage();
    features.splitPreviewImage("value-component", "manhattan",
        "five", null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Parameters must be integers");
    assertEquals(expected, log);
  }

  @Test
  public void TestRedComponentTrue() {
    features.loadImage();
    features.componentImage("red-component", "manhattan-small");
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "applyComponent with type: red for imageName: manhattan-small saving as: "
            + "manhattan-smallred-component");
    expected.add("Save image with imageName: manhattan-smallred-component");
    expected.add("displaying image in GUI : manhattan-smallred-component");
    expected.add(
        "applying Histogram on imageName manhattan-smallred-component "
            + "and saveImage Histogrammanhattan-smallred-component");
    expected.add("Save image with imageName: Histogrammanhattan-smallred-component");
    expected.add("displaying histogram in GUI : manhattan-smallred-component");
    assertEquals(expected, log);
  }

  @Test
  public void TestRedComponentFalse() {
    features.componentImage("red-component", null);
    expected.add("showing error : Image not loaded!");
    assertEquals(expected, log);
  }

  @Test
  public void TestRedPreview() {
    features.loadImage();
    features.splitPreviewImage("red-component", "manhattan",
        "50", null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "Applying SplitPreview with operationName: red-component, percentage: 50.0, imageName:"
            + " manhattan, saveImage: manhattanred-component_splitPreview, params: none");
    expected.add("Save image with imageName: manhattanred-component_splitPreview");
    expected.add("displaying in preview : manhattanred-component_splitPreview");
    assertEquals(expected, log);
  }

  @Test
  public void TestRedInvalidPercentage1() {
    features.loadImage();
    features.splitPreviewImage("red-component", "manhattan", "-50",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Percentage must be between 0 and 100");
    assertEquals(expected, log);
  }

  @Test
  public void TestRedInvalidPercentage2() {
    features.loadImage();
    features.splitPreviewImage("red-component", "manhattan", "150",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Percentage must be between 0 and 100");
    assertEquals(expected, log);
  }

  @Test
  public void TestRedInvalidPercentage3() {
    features.loadImage();
    features.splitPreviewImage("red-component", "manhattan", "five",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Parameters must be integers");
    assertEquals(expected, log);
  }

  @Test
  public void TestBlueComponentTrue() {
    features.loadImage();
    features.componentImage("blue-component", "manhattan-small");
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "applyComponent with type: blue for imageName: manhattan-small saving as: "
            + "manhattan-smallblue-component");
    expected.add("Save image with imageName: manhattan-smallblue-component");
    expected.add("displaying image in GUI : manhattan-smallblue-component");
    expected.add(
        "applying Histogram on imageName manhattan-smallblue-component "
            + "and saveImage Histogrammanhattan-smallblue-component");
    expected.add("Save image with imageName: Histogrammanhattan-smallblue-component");
    expected.add("displaying histogram in GUI : manhattan-smallblue-component");
    assertEquals(expected, log);
  }

  @Test
  public void TestBlueComponentFalse() {
    features.componentImage("blue-component", null);
    expected.add("showing error : Image not loaded!");
    assertEquals(expected, log);
  }

  @Test
  public void TestBluePreview() {
    features.loadImage();
    features.splitPreviewImage("blue-component", "manhattan", "50",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "Applying SplitPreview with operationName: blue-component, percentage: 50.0, imageName:"
            + " manhattan, saveImage: manhattanblue-component_splitPreview, params: none");
    expected.add("Save image with imageName: manhattanblue-component_splitPreview");
    expected.add("displaying in preview : manhattanblue-component_splitPreview");
    assertEquals(expected, log);
  }

  @Test
  public void TestBlueInvalidPercentage1() {
    features.loadImage();
    features.splitPreviewImage("blue-component", "manhattan", "-50",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Percentage must be between 0 and 100");
    assertEquals(expected, log);
  }

  @Test
  public void TestBlueInvalidPercentage2() {
    features.loadImage();
    features.splitPreviewImage("blue-component", "manhattan", "150",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Percentage must be between 0 and 100");
    assertEquals(expected, log);
  }

  @Test
  public void TestBlueInvalidPercentage3() {
    features.loadImage();
    features.splitPreviewImage("blue-component", "manhattan", "five",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Parameters must be integers");
    assertEquals(expected, log);
  }


  @Test
  public void TestGreenComponentTrue() {
    features.loadImage();
    features.componentImage("green-component", "manhattan-small");
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "applyComponent with type: green for imageName: manhattan-small saving as: "
            + "manhattan-smallgreen-component");
    expected.add("Save image with imageName: manhattan-smallgreen-component");
    expected.add("displaying image in GUI : manhattan-smallgreen-component");
    expected.add(
        "applying Histogram on imageName manhattan-smallgreen-component and saveImage "
            + "Histogrammanhattan-smallgreen-component");
    expected.add("Save image with imageName: Histogrammanhattan-smallgreen-component");
    expected.add("displaying histogram in GUI : manhattan-smallgreen-component");
    assertEquals(expected, log);
  }

  @Test
  public void TestGreenComponentFalse() {
    features.componentImage("green-component", null);
    expected.add("showing error : Image not loaded!");
    assertEquals(expected, log);
  }

  @Test
  public void TestGreenPreview() {
    features.loadImage();
    features.splitPreviewImage("green-component", "manhattan", "50",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "Applying SplitPreview with operationName: green-component, percentage: 50.0, imageName: "
            + "manhattan, saveImage: manhattangreen-component_splitPreview, params: none");
    expected.add("Save image with imageName: manhattangreen-component_splitPreview");
    expected.add("displaying in preview : manhattangreen-component_splitPreview");
    assertEquals(expected, log);
  }

  @Test
  public void TestGreenInvalidPercentage1() {
    features.loadImage();
    features.splitPreviewImage("green-component", "manhattan", "-50",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Percentage must be between 0 and 100");
    assertEquals(expected, log);
  }

  @Test
  public void TestGreenInvalidPercentage2() {
    features.loadImage();
    features.splitPreviewImage("green-component", "manhattan", "150",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Percentage must be between 0 and 100");
    assertEquals(expected, log);
  }

  @Test
  public void TestGreenInvalidPercentage3() {
    features.loadImage();
    features.splitPreviewImage("green-component", "manhattan", "five",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Parameters must be integers");
    assertEquals(expected, log);
  }

  @Test
  public void TestColorCorrectTrue() {
    features.loadImage();
    features.componentImage("color-correct", "manhattan-small");
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "applying ColorCorrection on imageName manhattan-small and saveImage"
            + " manhattan-smallcolor-correct");
    expected.add("Save image with imageName: manhattan-smallcolor-correct");
    expected.add("displaying image in GUI : manhattan-smallcolor-correct");
    expected.add(
        "applying Histogram on imageName manhattan-smallcolor-correct and saveImage"
            + " Histogrammanhattan-smallcolor-correct");
    expected.add("Save image with imageName: Histogrammanhattan-smallcolor-correct");
    expected.add("displaying histogram in GUI : manhattan-smallcolor-correct");
    assertEquals(expected, log);
  }


  @Test
  public void TestColorCorrectFalse() {
    features.componentImage("color-correct", null);
    expected.add("showing error : Image not loaded!");
    assertEquals(expected, log);
  }

  @Test
  public void TestColorCorrectPreview() {
    features.loadImage();
    features.splitPreviewImage("color-correct", "manhattan", "50",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "Applying SplitPreview with operationName: color-correct, percentage: 50.0, imageName:"
            + " manhattan, saveImage: manhattancolor-correct_splitPreview, params: none");
    expected.add("Save image with imageName: manhattancolor-correct_splitPreview");
    expected.add("displaying in preview : manhattancolor-correct_splitPreview");
    assertEquals(expected, log);
  }

  @Test
  public void TestColorCorrectInvalidPercentage1() {
    features.loadImage();
    features.splitPreviewImage("color-correct", "manhattan", "-50",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Percentage must be between 0 and 100");
    assertEquals(expected, log);
  }

  @Test
  public void TestColorCorrectInvalidPercentage2() {
    features.loadImage();
    features.splitPreviewImage("color-correct", "manhattan", "150",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Percentage must be between 0 and 100");
    assertEquals(expected, log);
  }

  @Test
  public void TestColorCorrectInvalidPercentage3() {
    features.loadImage();
    features.splitPreviewImage("color-correct", "manhattan", "five",
        null);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Parameters must be integers");
    assertEquals(expected, log);
  }

  @Test
  public void TestLevelAdjustTrue() {
    features.loadImage();
    features.levelAdjustImage("manhattan", "0", "100", "250");
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "applying LevelAdjustment with black: 0, mid: 100 and white: 250 on imageName manhattan"
            + " and saveImage manhattan_levelAdjusted");
    expected.add("Save image with imageName: manhattan_levelAdjusted");
    expected.add("displaying image in GUI : manhattan_levelAdjusted");
    expected.add(
        "applying Histogram on imageName manhattan_levelAdjusted and "
            + "saveImage Histogrammanhattan_levelAdjusted");
    expected.add("Save image with imageName: Histogrammanhattan_levelAdjusted");
    expected.add("displaying histogram in GUI : manhattan_levelAdjusted");
    assertEquals(expected, log);
  }

  @Test
  public void TestLevelAdjustFalse() {
    features.levelAdjustImage(null, "0", "100", "250");
    expected.add("showing error : Image not loaded!");
    assertEquals(expected, log);
  }

  @Test
  public void TestLevelAdjustValuesAbsent() {
    features.loadImage();
    features.levelAdjustImage("manhattan", " ", " ", " ");
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Parameters must be integers");
    assertEquals(expected, log);
  }

  @Test
  public void TestLevelAdjustValueNotInt() {
    features.loadImage();
    features.levelAdjustImage("manhattan", "wvwev", "ve", "fr");
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Parameters must be integers");
    assertEquals(expected, log);
  }

  @Test
  public void TestLevelAdjustPreview() {
    features.loadImage();
    features.splitPreviewImage("levels-adjust", "manhattan",
        "50", "0", "100", "255");
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "Applying SplitPreview with operationName: levels-adjust, percentage: 50.0, "
            + "imageName: manhattan, saveImage: manhattanlevels-adjust_splitPreview, "
            + "params: [0, 100, 255]");
    expected.add("Save image with imageName: manhattanlevels-adjust_splitPreview");
    expected.add("displaying in preview : manhattanlevels-adjust_splitPreview");
    assertEquals(expected, log);
  }

  @Test
  public void TestLevelAdjustInvalidPercentage1() {
    features.loadImage();
    features.splitPreviewImage("levels-adjust", "manhattan",
        "-50", "0", "100", "255");
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Percentage must be between 0 and 100");
    assertEquals(expected, log);
  }

  @Test
  public void TestLevelAdjustInvalidPercentage2() {
    features.loadImage();
    features.splitPreviewImage("levels-adjust",
        "manhattan", "150", "0", "100", "255");
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Percentage must be between 0 and 100");
    assertEquals(expected, log);
  }

  @Test
  public void TestLevelAdjustInvalidPercentage3() {
    features.loadImage();
    features.splitPreviewImage("levels-adjust", "manhattan",
        "five", "0", "100", "255");
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Parameters must be integers");
    assertEquals(expected, log);
  }

  @Test
  public void TestCompressTrue() {
    features.loadImage();
    features.compressImage("manhattan", "50", true);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "applying Compression with percentage: 50 on imageName "
            + "manhattan and saveImage manhattan50");
    expected.add("Save image with imageName: manhattan50");
    expected.add("Save image with imageName: manhattan50");
    expected.add("displaying image in GUI : manhattan50");
    expected.add("applying Histogram on imageName manhattan50 and saveImage Histogrammanhattan50");
    expected.add("Save image with imageName: Histogrammanhattan50");
    expected.add("displaying histogram in GUI : manhattan50");
    assertEquals(expected, log);
  }

  @Test
  public void TestCompresstruePreview() {
    features.loadImage();
    features.compressImage("manhattan", "50", false);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "applying Compression with percentage: 50 on imageName "
            + "manhattan and saveImage manhattan50");
    expected.add("Save image with imageName: manhattan50");
    expected.add("displaying in preview : manhattan50");
    assertEquals(expected, log);
  }

  @Test
  public void TestCompressFalse() {
    features.compressImage(null, "50", true);
    expected.add("showing error : Image not loaded!");
    assertEquals(expected, log);
  }

  @Test
  public void TestDownscalingTrue() {
    features.loadImage();
    features.downScaling("manhattan", "100", "200", true);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("applying downscaling on image: manhattan with save image name: manhattan100200 "
        + "with height: 100 with width: 200");
    expected.add("Save image with imageName: manhattan100200");
    expected.add("Save image with imageName: manhattan100200");
    expected.add("displaying image in GUI : manhattan100200");
    expected.add(
        "applying Histogram on imageName manhattan100200 and saveImage Histogrammanhattan100200");
    expected.add("Save image with imageName: Histogrammanhattan100200");
    expected.add("displaying histogram in GUI : manhattan100200");
    assertEquals(expected, log);
  }

  @Test
  public void TestDownScalingTruePreview() {
    features.loadImage();
    features.downScaling("manhattan", "100", "200", false);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "applying downscaling on image: manhattan with save image name:"
            + " manhattan100200 with height: 100 with width: 200");
    expected.add("Save image with imageName: manhattan100200");
    expected.add("displaying in preview : manhattan100200");
    assertEquals(expected, log);
  }

  @Test
  public void TestDownscalingFalse() {
    features.downScaling(null, "200", "100", true);
    expected.add("showing error : Image not loaded!");
    assertEquals(expected, log);
  }

  @Test
  public void TestBrightnessTrue() {
    features.loadImage();
    features.brightening("manhattan", "50", true);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("applyBrighten with intensity 50 and imageName manhattan "
        + "and saveImage manhattan50");
    expected.add("Save image with imageName: manhattan50");
    expected.add("Save image with imageName: manhattan50");
    expected.add("displaying image in GUI : manhattan50");
    expected.add("applying Histogram on imageName manhattan50 and saveImage Histogrammanhattan50");
    expected.add("Save image with imageName: Histogrammanhattan50");
    expected.add("displaying histogram in GUI : manhattan50");
    assertEquals(expected, log);
  }

  @Test
  public void TestBrightnessPreviewTrue() {
    features.loadImage();
    features.brightening("manhattan", "50", false);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add(
        "applyBrighten with intensity 50 and imageName manhattan and saveImage manhattan50");
    expected.add("Save image with imageName: manhattan50");
    expected.add("displaying in preview : manhattan50");
    assertEquals(expected, log);
  }

  @Test
  public void TestBrightnessFalse() {
    features.brightening(null, "50", false);
    expected.add("showing error : Image not loaded!");
    assertEquals(expected, log);
  }

  @Test
  public void TestBrightnessInvalidIntensity() {
    features.loadImage();
    features.brightening("manhattan", "fifty", false);
    expected.add("fetching file");
    expected.add("loadImage with imageName: new");
    expected.add("Save image with imageName: new");
    expected.add("displaying image in GUI : new");
    expected.add("applying Histogram on imageName new and saveImage Histogramnew");
    expected.add("Save image with imageName: Histogramnew");
    expected.add("displaying histogram in GUI : new");
    expected.add("showing error : Please provide a valid intensity value!");
    assertEquals(expected, log);
  }


}