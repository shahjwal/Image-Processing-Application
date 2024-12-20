package controller;

import java.util.Arrays;
import java.util.List;
import model.ImageModelV2;

/**
 * A mock implementation of the ImageModelV2 interface, extending the functionality of
 * MockImageModelImpl. This class is used primarily for testing the ImageModelV2 interface, allowing
 * for method call logging and validation in tests.
 */
public class MockImageModelImplV2 extends MockImageModelImpl implements ImageModelV2 {

  /**
   * Constructor for MockImageModelImplV2.Initializes this mock model with a provided log list,
   * passing it to the superclass to enable logging of method calls.
   *
   * @param log a List of Strings used to record the sequence of method calls and their details.
   */
  public MockImageModelImplV2(List<String> log) {
    super(log);
  }

  @Override
  public void applyCompress(int percentage, String imageName, String saveImage) {
    log.add("applying Compression with percentage: " + percentage + " on imageName " + imageName
        + " and saveImage " + saveImage);
  }

  @Override
  public void applyHistogram(String imageName, String saveImage) {
    log.add("applying Histogram on imageName " + imageName + " and saveImage " + saveImage);
  }

  @Override
  public void applyLevelAdjustment(String imageName, String saveImage, int black, int mid,
      int white) {
    log.add("applying LevelAdjustment with black: " + black + ", mid: " + mid + " and white: "
        + white + " "
        + "on imageName " + imageName + " and saveImage " + saveImage);
  }

  @Override
  public void applyColorCorrection(String imageName, String saveImage) {
    log.add("applying ColorCorrection on imageName " + imageName + " and saveImage " + saveImage);
  }

  @Override
  public void applySplitPreview(String operationName, String imageName, String saveImage,
      double percentage, int[] params) {
    log.add("Applying SplitPreview with operationName: " + operationName
        + ", percentage: " + percentage
        + ", imageName: " + imageName
        + ", saveImage: " + saveImage
        + ", params: " + (params.length != 0 ? Arrays.toString(params) : "none"));
  }
}
