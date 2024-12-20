import controller.ControllerInterface;
import controller.ImageController;
import controller.MVCController;
import java.io.InputStreamReader;
import java.io.StringReader;
import model.ImageModel;
import model.ImageModelImplV3;
import view.IView;
import view.JFrameView;
import view.Message;
import view.MessageView;

/**
 * This is the main class that serves as the entry point for the Image Processing application.
 * Depending on the command-line arguments, the application can run in text-based or GUI mode.
 */
public class Main {

  /**
   * The main function that triggers the application. It supports both text-based and GUI-based
   * modes, determined by the command-line arguments provided.
   *
   * @param args command-line arguments for selecting the mode of the application.
   */
  public static void main(String[] args) {

    ImageModel model = new ImageModelImplV3();
    Appendable output;
    Message view;
    IView viewGUI;
    ControllerInterface controllerGUI;
    ControllerInterface controller;
    Readable in = null;

    if (args.length > 0) {
      if (args[0].equals("-file") && args.length == 2) {
        in = new StringReader("run " + args[1] + "\nexit");
      } else if (args[0].equals("-text") && args.length == 1) {
        in = new InputStreamReader(System.in);
      }
      output = System.out;
      view = new MessageView(output);
      controller = new ImageController(model, view, in);
      controller.start();
    } else {
      viewGUI = new JFrameView();
      controllerGUI = new MVCController(model, viewGUI);
      controllerGUI.start();
    }

  }
}