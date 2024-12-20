package controller;

import java.util.List;
import model.ImageModel;

/**
 * A mock implementation of the ImageModel interface, designed for testing purposes. This class
 * allows for logging interactions with the model to verify expected behaviors in a controlled
 * environment.
 */
public class MockImageModelImpl implements ImageModel {

  protected final List<String> log;

  /**
   * Constructor for MockImageModelImpl. Initializes the mock model with a provided log list to
   * store method call logs.
   *
   * @param log a List of Strings used to record the sequence of method calls and their details.
   */
  public MockImageModelImpl(List<String> log) {
    this.log = log;
  }

  @Override
  public void loadImage(String imageName, int[][][] rgb) {
    log.add("loadImage with imageName: " + imageName);
  }

  @Override
  public int[][][] saveImage(String imageName) {
    log.add("Save image with imageName: " + imageName);
    return new int[4][4][3];
  }

  @Override
  public void applyBrighten(int intensity, String imageName, String saveImage) {
    log.add("applyBrighten with intensity " + intensity + " and imageName "
        + imageName + " and saveImage " + saveImage);
  }

  @Override
  public void applyBlur(String imageName, String saveImage) {
    log.add("applyBlur with imageName " + imageName + " and saveImage " + saveImage);
  }

  @Override
  public void applySharpen(String imageName, String saveImage) {
    log.add("applySharpen with imageName " + imageName + " and saveImage " + saveImage);
  }

  @Override
  public void applySepia(String imageName, String saveImage) {
    log.add("applySepia with imageName " + imageName + " and saveImage " + saveImage);
  }

  @Override
  public void applyFlip(String imageName, String saveImage, boolean flip) {
    String flipType = flip ? "Vertical" : "Horizontal";
    log.add("apply " + flipType + " Flip for imageName: " + imageName
        + " saving as: " + saveImage);
  }

  @Override
  public void applyComponent(String imageName, String saveImage, String type) {
    log.add("applyComponent with type: " + type + " for imageName: "
        + imageName + " saving as: " + saveImage);
  }

  @Override
  public void applySplit(String imageName, String redImage, String greenImage, String blueImage) {
    log.add("applySplit for imageName: " + imageName +
        " into redImage: " + redImage + ", greenImage: " + greenImage +
        ", blueImage: " + blueImage);
  }

  @Override
  public void applyCombine(String saveImage, String redImage, String greenImage, String blueImage) {
    log.add("applyCombine for redImage: " + redImage + ", greenImage: " + greenImage +
        ", blueImage: " + blueImage + " saving as: " + saveImage);
  }
}
