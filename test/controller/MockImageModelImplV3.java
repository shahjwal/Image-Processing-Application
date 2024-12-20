package controller;

import java.util.List;
import model.ImageModelV3;

/**
 * A mock implementation of the ImageModelV3 interface, extending the functionality of
 * MockImageModelImplV2. This class is used primarily for testing the ImageModelV3 interface,
 * allowing for method call logging and validation in tests.
 */
public class MockImageModelImplV3 extends MockImageModelImplV2 implements ImageModelV3 {

  /**
   * Constructor for MockImageModelImplV3.Initializes this mock model with a provided log list,
   * passing it to the superclass to enable logging of method calls.
   *
   * @param log a List of Strings used to record the sequence of method calls and their details.
   */
  public MockImageModelImplV3(List<String> log) {
    super(log);
  }

  @Override
  public void applyDownScaling(String imageName, String saveImage, int height, int width) {
    log.add("applying downscaling on image: " + imageName + " with save image name: " + saveImage
        + " with height: " + height + " with width: " + width);
  }

  @Override
  public void applyMasking(String operationType, String imageName, String maskImage,
      String saveImage) {
    log.add("applying masking on image: " + imageName + " with save image name: "
        + saveImage + " with mask image: " + maskImage + " with operation type: " + operationType);
  }
}
