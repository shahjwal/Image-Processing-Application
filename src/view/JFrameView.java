package view;

import controller.Features;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * A graphical user interface (GUI) for an image processing application. This class provides the
 * ability to load, save, and manipulate images through various actions, such as applying filters
 * (e.g., sepia, blur, sharpen), flipping, adjusting brightness, and other image processing
 * operations. It extends {@link JFrame} and implements the {@link IView} interface.
 */
public class JFrameView extends JFrame implements IView {

  private static final Dimension MAX_BUTTON_SIZE = new Dimension(200, Integer.MAX_VALUE);
  private String currentImage;
  private JPanel imagePanel;
  private JPanel histogramPanel;
  private JButton loadButton;
  private JButton saveButton;
  private JButton sepiaButton;
  private JButton blurButton;
  private JButton sharpenButton;
  private JButton hFlipButton;
  private JButton vFlipButton;
  private JButton redComponentButton;
  private JButton blueComponentButton;
  private JButton greenComponentButton;
  private JButton lumaComponentButton;
  private JButton colorCorrectionButton;
  private JButton levelAdjustButton;
  private JButton compressButton;
  private JButton downscaleButton;
  private JButton brightnessButton;
  private JTextField blackField = new JTextField(3);
  private JTextField midField = new JTextField(3);
  private JTextField whiteField = new JTextField(3);
  private JTextField heightField = new JTextField(3);
  private JTextField widthField = new JTextField(3);
  private JTextField intensityField = new JTextField(3);
  private JDialog filterDialog;
  private JButton applyPreview;
  private JButton applyOrg;
  private JButton cancelOrg;
  private JSlider percentageSlider;
  private JLabel percentageDisplay;
  private JPanel previewPanel;

  /**
   * Constructs a new {@link JFrameView}. Initializes the main frame and adds the necessary panels
   * and components. This is the where the application kicks on.
   */
  public JFrameView() {
    setupFrame();

    JPanel leftPanel = createLeftPanel();
    JPanel centerPanel = createCenterPanel();

    add(leftPanel, BorderLayout.WEST);
    add(centerPanel, BorderLayout.CENTER);

    this.setVisible(true);
  }

  /**
   * Sets up the main frame for the image processing application with title, size, close operation,
   * and layout. Adds a window listener to confirm exit action.
   */
  private void setupFrame() {
    setTitle("Image Processing App");
    setSize(1000, 1000);
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    setLayout(new BorderLayout());

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        int option = JOptionPane.showConfirmDialog(JFrameView.this,
            "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
          JFrameView.this.dispose();
        }
      }
    });
  }

  /**
   * Creates the left panel of the user interface, containing buttons for image processing
   * operations.
   *
   * @return JPanel representing the left panel of the UI.
   */
  private JPanel createLeftPanel() {
    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
    leftPanel.setBorder(new LineBorder(Color.BLACK, 2));
    leftPanel.setPreferredSize(new Dimension(200, getHeight()));

    leftPanel.add(Box.createVerticalGlue());
    leftPanel.add(createLoadSavePanel());
    leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));

    brightnessButton = new JButton("Change Brightness");
    brightnessButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    brightnessButton.setMaximumSize(MAX_BUTTON_SIZE);
    leftPanel.add(brightnessButton);
    leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    blurButton = new JButton("Blur Image");
    blurButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    blurButton.setMaximumSize(MAX_BUTTON_SIZE);
    leftPanel.add(blurButton);
    leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    sharpenButton = new JButton("Sharpen Image");
    sharpenButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    sharpenButton.setMaximumSize(MAX_BUTTON_SIZE);
    leftPanel.add(sharpenButton);
    leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    sepiaButton = new JButton("Apply Sepia");
    sepiaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    sepiaButton.setMaximumSize(MAX_BUTTON_SIZE);
    leftPanel.add(sepiaButton);
    leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    hFlipButton = new JButton("Horizontal Flip");
    hFlipButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    hFlipButton.setMaximumSize(MAX_BUTTON_SIZE);
    leftPanel.add(hFlipButton);
    leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    vFlipButton = new JButton("Vertical Flip");
    vFlipButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    vFlipButton.setMaximumSize(MAX_BUTTON_SIZE);
    leftPanel.add(vFlipButton);
    leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    colorCorrectionButton = new JButton("Color Correction");
    colorCorrectionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    colorCorrectionButton.setMaximumSize(MAX_BUTTON_SIZE);
    leftPanel.add(colorCorrectionButton);
    leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    redComponentButton = new JButton("Red Component");
    redComponentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    redComponentButton.setMaximumSize(MAX_BUTTON_SIZE);
    leftPanel.add(redComponentButton);
    leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    greenComponentButton = new JButton("Green Component");
    greenComponentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    greenComponentButton.setMaximumSize(MAX_BUTTON_SIZE);
    leftPanel.add(greenComponentButton);
    leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    blueComponentButton = new JButton("Blue Component");
    blueComponentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    blueComponentButton.setMaximumSize(MAX_BUTTON_SIZE);
    leftPanel.add(blueComponentButton);
    leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    lumaComponentButton = new JButton("Greyscale");
    lumaComponentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    lumaComponentButton.setMaximumSize(MAX_BUTTON_SIZE);
    leftPanel.add(lumaComponentButton);
    leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    levelAdjustButton = new JButton("Level Adjustment");
    levelAdjustButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    levelAdjustButton.setMaximumSize(MAX_BUTTON_SIZE);
    leftPanel.add(levelAdjustButton);
    leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    downscaleButton = new JButton("Downscale Image");
    downscaleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    downscaleButton.setMaximumSize(MAX_BUTTON_SIZE);
    leftPanel.add(downscaleButton);
    leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    compressButton = new JButton("Compress Image");
    compressButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    compressButton.setMaximumSize(MAX_BUTTON_SIZE);
    leftPanel.add(compressButton);
    leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    leftPanel.add(Box.createVerticalGlue());
    return leftPanel;
  }

  /**
   * Creates the Small panel in north area of the left panel with the load and save button in it.
   *
   * @return the JPanel with load and save button in it.
   */
  private JPanel createLoadSavePanel() {
    JPanel loadSavePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
    loadSavePanel.setMaximumSize(new Dimension(200, 40));

    loadButton = new JButton("Load");
    loadButton.setPreferredSize(new Dimension(90, 25));
    saveButton = new JButton("Save");
    saveButton.setPreferredSize(new Dimension(90, 25));
    loadSavePanel.add(loadButton);
    loadSavePanel.add(saveButton);

    return loadSavePanel;
  }

  /**
   * This created the centre or the main panel where the image and the histogram is displayed.
   *
   * @return the centre or the main panel.
   */
  private JPanel createCenterPanel() {
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BorderLayout());

    imagePanel = new JPanel();
    imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
    imagePanel.setBorder(new LineBorder(Color.BLACK, 1));  // Add border
    JLabel imageLabel = createLabel(
        "Load an image here by clicking the " + "load button or drag the image here.");
    imagePanel.add(imageLabel);

    histogramPanel = new JPanel();
    histogramPanel.setLayout(new BoxLayout(histogramPanel, BoxLayout.Y_AXIS));
    histogramPanel.setBorder(new LineBorder(Color.BLACK, 1));  // Add border
    JLabel histogramLabel = createLabel("Histogram of the loaded image will be displayed here");
    histogramPanel.add(histogramLabel);

    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, imagePanel, histogramPanel);
    splitPane.setResizeWeight(0.5);
    splitPane.setDividerLocation(0.5);
    splitPane.setOneTouchExpandable(true);
    splitPane.setContinuousLayout(true);

    centerPanel.add(splitPane, BorderLayout.CENTER);
    return centerPanel;
  }

  /**
   * Creates a label with the custom text in it.
   *
   * @param text the text that is to be displayed.
   * @return the JLabel with desired text in it.
   */
  private JLabel createLabel(String text) {
    JLabel label = new JLabel(text, SwingConstants.CENTER);
    label.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
    label.setBorder(new LineBorder(Color.BLACK, 2));
    return label;
  }

  /**
   * Refreshes the dialog panel after it has been used.
   */
  private void dialogPanelRefresh() {
    filterDialog.revalidate();
    filterDialog.repaint();
    filterDialog.setLocationRelativeTo(this);
    filterDialog.setVisible(true);
  }

  /**
   * This method is responsible for creating a panel with a slider with initial percentage value.
   * This method also adds a display of the percentage where the slider is pointed.
   *
   * @param title   the title of the slider.
   * @param initial the initial percentage value that has to be set by the slider.
   * @return the JPanel with the slider and the display of the percentage in it.
   */
  private JPanel setupSlider(String title, int initial) {

    JPanel sliderPanel = new JPanel();
    sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));

    JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel sliderLabel = new JLabel(title + ": ");
    percentageDisplay = new JLabel((initial) + "%");
    labelPanel.add(sliderLabel);
    labelPanel.add(percentageDisplay);

    percentageSlider = new JSlider(0, 100, initial);
    percentageSlider.setMajorTickSpacing(25);
    percentageSlider.setMinorTickSpacing(5);
    percentageSlider.setPaintTicks(true);
    percentageSlider.setPaintLabels(true);

    sliderPanel.add(labelPanel);
    sliderPanel.add(percentageSlider);

    return sliderPanel;
  }

  /**
   * This method helps to set up the Apply and the cancel button for the dialog box.
   *
   * @return the JPanel with the apply and cancel button inside it.
   */
  private JPanel setupButtons() {
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    applyOrg = new JButton("Apply");
    cancelOrg = new JButton("Cancel");
    buttonPanel.add(applyOrg);
    buttonPanel.add(cancelOrg);
    return buttonPanel;
  }

  /**
   * This method is triggered when the compress button is clicked. It has the required
   * functionalities that is to be needed for the compression of an image.
   *
   * @param features features to use for the compression of the image.
   */
  private void openCompressDialog(Features features) {
    if (!features.checkImage(this.currentImage)) {
      return;
    }

    setupFilterDialog("Compress Image");

    previewPanel = new JPanel(new BorderLayout());
    filterDialog.add(previewPanel, BorderLayout.CENTER);
    features.compressImage(currentImage, "0", false);

    JPanel sliderPanel = setupSlider("Compress Percentage", 0);
    JPanel buttonPanel = setupButtons();

    JPanel containerPanel = new JPanel();
    containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
    containerPanel.add(sliderPanel);
    containerPanel.add(buttonPanel);

    percentageSlider.addChangeListener(e -> {
      int value = percentageSlider.getValue();
      percentageDisplay.setText(value + "%");
      features.compressImage(currentImage, String.valueOf(value), false);
    });

    applyOrg.addActionListener(e -> {
      features.compressImage(currentImage, String.valueOf(percentageSlider.getValue()), true);
      filterDialog.dispose();
    });

    cancelOrg.addActionListener(e -> filterDialog.dispose());

    filterDialog.add(containerPanel, BorderLayout.SOUTH);
    dialogPanelRefresh();
  }

  /**
   * This method sets up the upper field panel for the downscaling panel which contains height and
   * width field as well as the apply method to display the manipulated in the preview dialog.
   */
  private void setupDownscalePanel() {
    JPanel fieldPanel = new JPanel();
    JLabel heightLabel = new JLabel("Height value:");
    JLabel widthLabel = new JLabel("Weight value:");
    heightField = createPlaceholderTextField("height");
    widthField = createPlaceholderTextField("width");
    fieldPanel.add(heightLabel);
    fieldPanel.add(heightField);
    fieldPanel.add(widthLabel);
    fieldPanel.add(widthField);

    applyPreview = new JButton("Apply Downscale");
    applyPreview.setAlignmentX(Component.CENTER_ALIGNMENT);
    fieldPanel.add(applyPreview);
    filterDialog.add(fieldPanel, BorderLayout.NORTH);
  }

  /**
   * This method is triggered when the downscale image button is clicked. It has the required
   * functionalities that is to be needed for the downscaling of an image.
   *
   * @param features features to use for the downscaling of the image.
   */
  private void openDownscaleDialog(Features features) {
    if (features.checkImage(this.currentImage)) {

      setupFilterDialog("Downscale Image");
      setupDownscalePanel();
      previewPanel = new JPanel(new BorderLayout());
      filterDialog.add(previewPanel, BorderLayout.CENTER);

      applyPreview.addActionListener(e -> {
        String height = heightField.getText();
        String width = widthField.getText();
        features.downScaling(currentImage, height, width, false);
      });

      JPanel buttonPanel = setupButtons();

      cancelOrg.addActionListener(e -> filterDialog.dispose());

      applyOrg.addActionListener(e -> {
        features.downScaling(currentImage, heightField.getText(), widthField.getText(), true);
        filterDialog.dispose();
      });

      filterDialog.add(buttonPanel, BorderLayout.SOUTH);
      dialogPanelRefresh();
    }
  }

  /**
   * This method sets up the upper field panel for the brightness panel which contains the intensity
   * field and a apply button to display the manipulated image in the preview dialog.
   */
  private void setupBrightenPanel() {
    JPanel fieldPanel = new JPanel();
    JLabel intensityLabel = new JLabel("Intensity:");
    intensityField = createPlaceholderTextField("intensity");
    fieldPanel.add(intensityLabel);
    fieldPanel.add(intensityField);

    applyPreview = new JButton("Apply effect");
    applyPreview.setAlignmentX(Component.CENTER_ALIGNMENT);
    fieldPanel.add(applyPreview);

    filterDialog.add(fieldPanel, BorderLayout.NORTH);
  }

  /**
   * This method is triggered when the change brightness is clicked. It has the required
   * functionalities that is to be needed for brightening of the image.
   *
   * @param features features to use for changing the brightness of the image.
   */
  private void openBrightnessDialog(Features features) {
    if (features.checkImage(currentImage)) {
      setupFilterDialog("Brighten/Darken Image");
      setupBrightenPanel();
      previewPanel = new JPanel(new BorderLayout());
      filterDialog.add(previewPanel, BorderLayout.CENTER);
      JPanel buttonPanel = setupButtons();

      applyPreview.addActionListener(
          e -> features.brightening(currentImage, intensityField.getText(), false));

      cancelOrg.addActionListener(e -> filterDialog.dispose());

      applyOrg.addActionListener(e -> {
        features.brightening(currentImage, intensityField.getText(), true);
        filterDialog.dispose();
      });

      filterDialog.add(buttonPanel, BorderLayout.SOUTH);
      dialogPanelRefresh();
    }
  }

  /**
   * Creates a text field with the custom text placeholder in it.
   *
   * @param placeholder that is to be displayed inside the field text box.
   * @return the JTextField with the desired placeholder in it.
   */
  private JTextField createPlaceholderTextField(String placeholder) {
    JTextField textField = new JTextField(5);
    textField.setText(placeholder);
    textField.setForeground(Color.GRAY);

    textField.addFocusListener(new FocusAdapter() {
      @Override
      public void focusGained(FocusEvent e) {
        if (textField.getText().equals(placeholder)) {
          textField.setText("");
          textField.setForeground(Color.BLACK);
        }
      }

      @Override
      public void focusLost(FocusEvent e) {
        if (textField.getText().isEmpty()) {
          textField.setText(placeholder);
          textField.setForeground(Color.GRAY);
        }
      }
    });
    return textField;
  }

  /**
   * Set up the main dialog by deciding its width and height. This dialog box is use for previewing
   * the image before applying.
   *
   * @param title of the filter dialog box.
   */
  private void setupFilterDialog(String title) {
    filterDialog = new JDialog(this, title, true);
    filterDialog.setSize(500, 500);
    filterDialog.setLayout(new BorderLayout());
  }

  /**
   * This sets up the level adjust panel when the dialog is triggered and the button pressed is
   * level adjustment. This panel has black, mid and white fields with instructions. This panel also
   * has applied filter button for previewing the image.
   */
  private void setupLevelAdjustPanel() {
    JPanel valuePanel = new JPanel();
    JLabel note1 = new JLabel("All values should be within 0-255.");
    JLabel note2 = new JLabel("Black, mid and white values should be in ascending order.");

    note1.setAlignmentX(Component.CENTER_ALIGNMENT);
    note2.setAlignmentX(Component.CENTER_ALIGNMENT);
    valuePanel.setLayout(new BoxLayout(valuePanel, BoxLayout.Y_AXIS));  // Set vertical layout
    JPanel buttonPanel = new JPanel();
    JLabel blackLabel = new JLabel("Black value:");
    JLabel midLabel = new JLabel("Mid value:");
    JLabel whiteLabel = new JLabel("White value:");
    blackField = createPlaceholderTextField("black");
    midField = createPlaceholderTextField("mid");
    whiteField = createPlaceholderTextField("white");
    buttonPanel.add(blackLabel);
    buttonPanel.add(blackField);
    buttonPanel.add(midLabel);
    buttonPanel.add(midField);
    buttonPanel.add(whiteLabel);
    buttonPanel.add(whiteField);

    valuePanel.add(buttonPanel);
    valuePanel.add(note1);
    valuePanel.add(note2);

    applyPreview = new JButton("Apply Level Adjustment");
    applyPreview.setAlignmentX(Component.CENTER_ALIGNMENT);

    valuePanel.add(applyPreview);
    filterDialog.add(valuePanel, BorderLayout.NORTH);
  }

  /**
   * Retrieves a map of filter actions associated with filter types. The map keys represent filter
   * types, and the values are actions that execute the corresponding image filter on the input
   * image.
   *
   * @param features the Features object that provides image processing methods.
   * @param params   the parameters to be passed for specific filter actions (e.g., for levels
   *                 adjustment).
   * @return a map of filter actions, where the key is the filter type and the value is the action.
   */
  private Map<String, Consumer<String>> getFilterActions(Features features, String[] params) {
    Map<String, Consumer<String>> filterActions = new HashMap<>();
    filterActions.put("blur", features::blurImage);
    filterActions.put("sharpen", features::sharpenImage);
    filterActions.put("sepia", features::sepiaImage);
    filterActions.put("red-component", img -> features.componentImage("red-component", img));
    filterActions.put("green-component", img -> features.componentImage("green-component", img));
    filterActions.put("blue-component", img -> features.componentImage("blue-component", img));
    filterActions.put("luma-component", img -> features.componentImage("luma-component", img));
    filterActions.put("levels-adjust",
        img -> features.levelAdjustImage(img, params[0], params[1], params[2]));
    filterActions.put("color-correct", features::colorCorrectImage);
    return filterActions;
  }

  /**
   * Opens a dialog to apply a specific filter to an image. Depending on the filter type, a
   * corresponding user interface is set up, allowing the user to adjust parameters such as
   * brightness, contrast, etc.
   *
   * @param features   the Features object that contains the image processing methods.
   * @param filterType the type of filter to apply (e.g., "blur", "sharpen", "levels-adjust").
   */
  private void openDialog(Features features, String filterType) {
    if (!features.checkImage(this.currentImage)) {
      return;
    }

    setupFilterDialog("Split Preview");
    String[] params = new String[3];

    previewPanel = new JPanel(new BorderLayout());
    filterDialog.add(previewPanel, BorderLayout.CENTER);

    if (Objects.equals(filterType, "levels-adjust")) {
      setupLevelAdjustPanel();
      applyPreview.addActionListener(e -> {
        params[0] = blackField.getText();
        params[1] = midField.getText();
        params[2] = whiteField.getText();
        features.splitPreviewImage("levels-adjust", currentImage, "50", params);
      });
    } else {
      features.splitPreviewImage(filterType, currentImage, "50", (String[]) null);
    }

    JPanel sliderPanel = setupSlider("Split Percentage", 50);
    JPanel buttonPanel = setupButtons();

    JPanel containerPanel = new JPanel();
    containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
    containerPanel.add(sliderPanel);
    containerPanel.add(buttonPanel);

    percentageSlider.addChangeListener(e -> {
      int value = percentageSlider.getValue();
      percentageDisplay.setText(value + "%");
      if (Objects.equals(filterType, "levels-adjust")) {
        features.splitPreviewImage(filterType, currentImage, String.valueOf(value), params);
      } else {
        features.splitPreviewImage(filterType, currentImage, String.valueOf(value),
            (String[]) null);
      }
    });

    Map<String, Consumer<String>> filterActions = getFilterActions(features, params);
    applyOrg.addActionListener(e -> {
      Consumer<String> filterAction = filterActions.get(filterType);
      if (filterAction != null) {
        filterAction.accept(currentImage);
      }
      filterDialog.dispose();
    });
    cancelOrg.addActionListener(e -> filterDialog.dispose());

    filterDialog.add(containerPanel, BorderLayout.SOUTH);
    dialogPanelRefresh();
  }

  /**
   * Displays a scaled version of the given image within the specified panel. The image is resized
   * to fit within the bounds of the panel and is displayed inside a scrollable area for better
   * viewing.
   *
   * @param image the image to display.
   * @param panel the panel where the image will be displayed.
   */
  private void displayScaledImage(BufferedImage image, JPanel panel) {
    panel.removeAll();
    panel.setLayout(new BorderLayout());

    Dimension panelSize = panel.getSize();
    int panelWidth = panelSize.width;
    int panelHeight = panelSize.height;

    ImageIcon imageIcon = new ImageIcon(image);
    JLabel imageLabel = new JLabel(imageIcon);

    JScrollPane scrollPane = new JScrollPane(imageLabel);
    scrollPane.setPreferredSize(new Dimension(panelWidth, panelHeight));
    scrollPane.setBorder(BorderFactory.createEmptyBorder());

    panel.add(scrollPane, BorderLayout.CENTER);
    panel.revalidate();
    panel.repaint();
  }

  /**
   * Checks if the given file is a valid image file based on its extension. Supported image formats
   * include JPG, JPEG, PNG, and PPM.
   *
   * @param file the file to check.
   * @return true if the file is an image, false otherwise.
   */
  private boolean isImageFile(File file) {
    String fileName = file.getName().toLowerCase();
    return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")
        || fileName.endsWith(".ppm");
  }

  /**
   * Adds action listeners for various buttons and sets up features for image manipulation. This
   * includes loading, saving, flipping, and applying filters or adjustments to the image.
   *
   * @param features the {@link Features} object that provides image processing functionality.
   */
  @Override
  public void addFeatures(Features features) {
    imagePanel.setTransferHandler(new TransferHandler() {
      @Override
      public boolean canImport(TransferHandler.TransferSupport support) {
        return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
      }

      @Override
      public boolean importData(TransferHandler.TransferSupport support) {
        if (!canImport(support)) {
          return false;
        }
        try {
          List<File> files = (List<File>) support.getTransferable()
              .getTransferData(DataFlavor.javaFileListFlavor);
          if (!files.isEmpty()) {
            File imageFile = files.get(0);
            if (isImageFile(imageFile)) {
              String imagePath = imageFile.getAbsolutePath();
              features.loadImageFromPath(imagePath);
              return true;
            } else {
              JOptionPane.showMessageDialog(imagePanel,
                  "Invalid image file. Please drop a valid image.", "Invalid File",
                  JOptionPane.ERROR_MESSAGE);
            }
          }
        } catch (Exception e) {
          //silent
        }
        return false;
      }
    });
    loadButton.addActionListener(evt -> {
      if (this.currentImage == null) {
        features.loadImage();
      } else {
        int option = JOptionPane.showConfirmDialog(this,
            "Do you want to overwrite the loaded image?", "Warning", JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
          features.loadImage();
        }
      }
    });
    saveButton.addActionListener(evt -> {
      if (features.checkImage(this.currentImage)) {
        features.saveImage(this.currentImage);
      }
    });
    hFlipButton.addActionListener(evt -> {
      if (features.checkImage(this.currentImage)) {
        features.flipImage("horizontal-flip", currentImage);
      }
    });
    vFlipButton.addActionListener(evt -> {
      if (features.checkImage(this.currentImage)) {
        features.flipImage("vertical-flip", currentImage);
      }
    });
    sepiaButton.addActionListener(evt -> openDialog(features, "sepia"));
    blurButton.addActionListener(evt -> openDialog(features, "blur"));
    sharpenButton.addActionListener(evt -> openDialog(features, "sharpen"));
    redComponentButton.addActionListener(evt -> openDialog(features, "red-component"));
    greenComponentButton.addActionListener(evt -> openDialog(features, "green-component"));
    blueComponentButton.addActionListener(evt -> openDialog(features, "blue-component"));
    lumaComponentButton.addActionListener(evt -> openDialog(features, "luma-component"));
    colorCorrectionButton.addActionListener(evt -> openDialog(features, "color-correct"));
    levelAdjustButton.addActionListener(evt -> openDialog(features, "levels-adjust"));
    brightnessButton.addActionListener(evt -> openBrightnessDialog(features));
    compressButton.addActionListener(evt -> openCompressDialog(features));
    downscaleButton.addActionListener(evt -> openDownscaleDialog(features));
  }

  /**
   * Opens a file chooser dialog to load an image file.
   *
   * @return the selected image file, or {@code null} if no file was selected.
   */
  @Override
  public File fetchFile() {
    File imagePath = null;
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Load Image File");
    FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter(
        "Image Files" + "(JPG, JPEG, PPM, PNG)", "jpg", "jpeg", "png", "ppm");
    fileChooser.setFileFilter(extensionFilter);
    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      String path = file.getAbsolutePath();
      imagePath = new File(path);
    }
    return imagePath;
  }

  /**
   * Opens a file chooser dialog to save an image file.
   *
   * @return the selected file to save the image, or {@code null} if no file was selected.
   */
  @Override
  public File saveFile() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save Image File");
    FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter(
        "Image Files" + "(JPG, JPEG, PPM, PNG)", "jpg", "jpeg", "png", "ppm");
    fileChooser.setFileFilter(extensionFilter);
    int result = fileChooser.showSaveDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile();
    }
    return null;
  }

  /**
   * Displays a preview of the image on the preview panel.
   *
   * @param imageName the name of the image.
   * @param image     the image to be displayed.
   */
  @Override
  public void displayInPreview(String imageName, BufferedImage image) {
    previewPanel.removeAll();
    this.displayScaledImage(image, previewPanel);
    previewPanel.revalidate();
    previewPanel.repaint();
  }

  /**
   * Displays the image on the image panel and updates the {@link #currentImage} field.
   *
   * @param imageName the name of the image.
   * @param image     the image to be displayed.
   */
  @Override
  public void displayImage(String imageName, BufferedImage image) {
    this.currentImage = imageName;
    this.displayScaledImage(image, this.imagePanel);
  }

  /**
   * Displays a histogram of the image on the histogram panel.
   *
   * @param imageName the name of the image.
   * @param image     the image whose histogram is to be displayed.
   */
  @Override
  public void displayHistogram(String imageName, BufferedImage image) {
    this.displayScaledImage(image, this.histogramPanel);
  }

  /**
   * Displays an error message in a dialog box.
   *
   * @param message the error message to be displayed.
   */
  @Override
  public void showError(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

}
