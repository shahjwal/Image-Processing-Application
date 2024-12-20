package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import view.IView;

/**
 * A mock implementation of the IView interface for testing purposes.
 * This class simulates the behavior of the GUI view and logs method calls
 * instead of interacting with a real graphical interface.
 */
public class MockGUIView implements IView {

  protected final List<String> log;

  /**
   * Constructs a MockGUIView with a specified log list to capture interactions.
   *
   * @param log the list that will hold log messages for the interactions.
   */
  public MockGUIView(List<String> log) {
    this.log = log;
  }

  @Override
  public void addFeatures(Features features) throws IllegalArgumentException {
    this.log.add("features object has been passed to the view successfully");
  }

  @Override
  public File fetchFile() {
    this.log.add("fetching file");
    return new File("test/controller/tempTestImages/new.png");
  }

  @Override
  public File saveFile() {
    this.log.add("Saving file");
    return new File("test/controller/tempTestImages/manhattan-small.png");
  }

  @Override
  public void displayImage(String imageName, BufferedImage image) {
    this.log.add("displaying image in GUI : " + imageName);
  }

  @Override
  public void displayInPreview(String imageName, BufferedImage image) {
    this.log.add("displaying in preview : " + imageName);
  }

  @Override
  public void displayHistogram(String imageName, BufferedImage image) {
    this.log.add("displaying histogram in GUI : " + imageName);
  }

  @Override
  public void showError(String message) {
    this.log.add("showing error : " + message);
  }

}
