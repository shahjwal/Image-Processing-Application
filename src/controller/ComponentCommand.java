package controller;

import model.ImageModel;

/**
 * ComponentCommand is an implementation of the command pattern that extracts a specific component
 * (such as red, green, blue, or intensity) from an image and saves the result. The combined image
 * will be saved with the specified name. This class will delegate the requirements to the specific
 * model's method.
 */
class ComponentCommand extends AbstractCommand {

  private final String imageName;
  private final String saveImage;
  private final String type;

  /**
   * Constructs a ComponentCommand with the specified image name, save image name, and component
   * type.
   *
   * @param imageName The name of the image to extract the component from.
   * @param saveImage The name of the image where the result will be saved.
   * @param type      The type of component to extract (e.g., "red", "green", "blue", "intensity").
   */
  ComponentCommand(String imageName, String saveImage, String type) {
    this.imageName = imageName;
    this.saveImage = saveImage;
    this.type = type;
  }

  /**
   * Executes the command by invoking the model's method to extract the specified component from the
   * image. The result is saved under the specified save name.
   *
   * @param model The ImageModel instance that performs the component extraction operation.
   */
  @Override
  void execute(ImageModel model) {
    model.applyComponent(this.imageName, this.saveImage, this.type);
  }

}
