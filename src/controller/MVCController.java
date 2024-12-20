package controller;

import model.ImageModel;
import view.IView;

/**
 * The MVCController class implements the ControllerInterface, serving as the controller component
 * in the Model-View-Controller (MVC) pattern for the image processing application. It mediates
 * communication between the model and the view, initializing the features and passing them to the
 * view.
 */
public class MVCController implements ControllerInterface {

  private final ImageModel model;
  private final IView view;

  /**
   * Constructs an MVCController with the specified model and view.
   *
   * @param model The image model responsible for manipulating image data.
   * @param view  The view component responsible for displaying the user interface.
   */
  public MVCController(ImageModel model, IView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Initializes the features and adds them to the view. This method is called to start the
   * application and link the view with the model.
   */
  public void start() {
    Features features = new FeaturesImpl(model, view);
    view.addFeatures(features);
  }

}
