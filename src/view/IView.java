package view;

import controller.Features;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * This interface defines the contract for the View component in an image processing application.
 * The View is responsible for presenting information to the user, displaying images, and handling
 * user interactions by delegating actions to the controller through the provided features. It
 * includes methods for displaying images, managing files (opening and saving images), showing image
 * histograms, and displaying error messages. Implementing this interface allows a class to act as
 * the View in the Model-View-Controller (MVC) architecture, ensuring that the interface between the
 * View and Controller remains consistent, while the actual UI implementation can be customized. The
 * key responsibilities of a class implementing this interface include:
 * - Displaying the current image and its preview.
 * - Allowing users to load and save image files.
 * - Presenting image histograms.
 * - Providing feedback to the user in case of errors.
 * The View should update dynamically based on the user's interaction, which is managed by
 * the controller through the `Features` interface. It may involve operations such as applying
 * filters to images or saving the processed result.
 */
public interface IView {

  /**
   * Adds the features (controller methods) to the view for handling user actions. This method
   * allows the view to delegate user input (e.g., button clicks) to the controller.
   *
   * @param features The features (controller) that provide the operations to be performed based on
   *                 user interaction.
   * @throws IllegalArgumentException if the provided features are invalid or null.
   */
  void addFeatures(Features features) throws IllegalArgumentException;

  /**
   * Fetches a file from the system using a file chooser dialog. This method is used to load an
   * image or other files into the application.
   *
   * @return A {@link File} object representing the chosen file.
   */
  File fetchFile();

  /**
   * Prompts the user to save a file to the system. This method is typically used to save the
   * processed image or other data.
   *
   * @return A {@link File} object representing the file path where the image is to be saved.
   */
  File saveFile();

  /**
   * Displays an image in the main display area of the view. This method shows the provided image
   * with the specified name in the view.
   *
   * @param imageName The name or identifier of the image being displayed.
   * @param image     The image (as a {@link BufferedImage}) to be displayed.
   */
  void displayImage(String imageName, BufferedImage image);

  /**
   * Displays an image in a preview window or section of the view. This method shows a preview of
   * the image, usually in a smaller or less detailed format.
   *
   * @param imageName The name or identifier of the image being previewed.
   * @param image     The image (as a {@link BufferedImage}) to be displayed in the preview.
   */
  void displayInPreview(String imageName, BufferedImage image);

  /**
   * Displays the histogram of an image in the view. This method typically generates a graphical
   * representation of the image's color distribution.
   *
   * @param imageName The name or identifier of the image whose histogram is being displayed.
   * @param image     The image (as a {@link BufferedImage}) for which the histogram is to be
   *                  shown.
   */
  void displayHistogram(String imageName, BufferedImage image);

  /**
   * Displays an error message in the view. This method is used to inform the user about errors or
   * issues that occur in the application.
   *
   * @param message The error message to be displayed.
   */
  void showError(String message);

}
