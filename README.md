# Image Processing Application

## Project Description:

This is an image processing application designed to facilitate various image manipulation tasks,
including flipping, filtering, compressing, color-correcting and combining images. The application
allows users to perform
operations through a user-friendly command-line interface as well as in Graphical user interface, 
enabling efficient processing of image
files in formats like JPEG, JPG, PNG, and PPM. With a focus on modular design, the application
supports multiple image manipulation techniques, enhancing user capabilities in image editing and
analysis.
---
Here’s the **Table of Contents** in a proper README format:

# Table of Contents

1. [Project Description](#project-description)
2. [Introduction](#introduction)
3. [Features](#features)
4. [Technology Used](#technology-used)
5. [Installation](#installation)
6. [Command Reference](#command-reference)
7. [Code Structure](#code-structure)
8. [Architecture](#architecture)
9. [Design Changes and Justification](#design-changes-and-justification)
10. [Image Format Support](#image-formats-support)
11. [Class-Diagram](#class-diagram)
12. [References](#references)

---

## Introduction:

Image processing is an essential aspect of digital media, encompassing various techniques and tools
used to manipulate and analyze images. The Image Processing Application is designed to simplify this
process, offering a comprehensive set of features for users to edit and enhance their images
efficiently.

This application caters to a diverse audience, from beginners looking to learn basic image
manipulation techniques to advanced users requiring powerful tools for complex editing tasks. By
providing a command-line interface, users can easily access a variety of functionalities, enabling
them to apply filters, combine images, and manipulate color channels with minimal effort.

#### Objectives:

- Ease of Use: The application aims to provide a user-friendly experience, allowing users to perform
  image processing tasks without the need for extensive technical knowledge.

- Flexibility and Extensibility: Designed with a modular architecture, the application can be easily
  extended to incorporate new features and enhancements, ensuring it remains relevant with evolving
  user needs and technological advancements.

- Support for Multiple Formats: The application supports popular image formats, making it versatile
  and compatible with a wide range of digital images, ensuring users can work with their existing
  files seamlessly.

#### Use Cases:

- Casual Users: Individuals looking to perform basic image editing tasks, such as applying filters
  or flipping images, can benefit from the application's straightforward functionality.

- Content Creators: Photographers, graphic designers, and social media influencers can use the
  application to enhance their visual content quickly and effectively.

- Educational Purposes: Students and learners in the field of digital media can use this application
  as a practical tool to explore and understand fundamental image processing concepts.

In summary, the Image Processing Application serves as a versatile and accessible tool for anyone
interested in manipulating images, regardless of their experience level, and aims to simplify the
image editing process while providing powerful capabilities for those who require them.

## Features:

The Image Processing Application offers a wide array of features designed to enhance and manipulate
images efficiently. Below are the key functionalities available in the application:

#### Image Manipulation:

- Flipping: Easily flip images horizontally or vertically to create mirror effects or change
  orientations.
- Brightening: Adjust the brightness of images to enhance visibility or create artistic effects.
- Blurring: Apply blurring effects to soften details in images, useful for background processing or
  artistic renditions.
- Sharpening: Enhance the clarity and detail of images by applying sharpening techniques to improve
  overall quality.
- Sepia: Apply a sepia-tone for manipulating the image pixels.

#### Partial Image Manipulation:

- Masking: This feature of this application can be used to partially
manipulate the image using the masked image. The mask image has to be provided
by the user to perform this operation. A Mask image basically should be a image
with only black and white intensity pixels. Pixels with black intensity value should
be manipulated and pixels with white should not be manipulated.

#### Color Manipulation:

- RGB Splitting: Separate images into their red, green, and blue components for individual editing
  and analysis.
- RGB Combining: Recombine the separated RGB components to create a final image, allowing for custom
  color adjustments and corrections.
- Type Component Operations: Perform operations like converting an image to grayscale or applying
  thresholds to enhance specific color channels. Components include red, green, blue, luma, value
  and intensity component.
- Level-Adjust: Adjust the black mid and white values of the images to enhance it and make it more
  realistic.
- Color-Correction: Corrects the color intensities of the image in order to make it more realistic
  and enhanced.

#### Histogram:

- Users also have an option to generate the histogram of the image based on their red, green and
  blue pixel intensities.

#### Split Preview:

- Users have an option to preview a manipulated image partially using the split preview option and
  the percentage
  that is provided by the user itself.
- This will only perform the manipulation partially on the x% of the image where x is the percentage
  provided by the user
  through the command line execution.
- Split Preview is available on the following commands:
  Blur, Sharpen, Sepia, Color and Greyscale Components, level adjustment and color correction.

#### Image Resizing:

- Image Downscaling: This feature of this application helps in down scaling the
image by changing its height and width dimensions. Pixels are mapped accordingly
so that the image is not distorted.

#### File Handling:

- Support for Multiple Formats: Open, edit, and save images in various formats, including JPEG, JPG,
  PNG, and PPM, ensuring compatibility with most digital image files.
- Support for multiple image loading and saving: This application supports loading multiple images
  and manipulate them and also saving them based on the user needs at the same time.

#### Command-Line Interface:

- Interactive Commands: Users can execute commands via a straightforward command-line interface,
  providing immediate feedback on their actions.
- Scripting Support: Users can run a series of commands from a script file, enabling automation of
  repetitive tasks and complex workflows.
- Error Handling: The application includes robust error handling, providing informative messages
  when commands fail or when incorrect inputs are provided.

These features collectively make the Image Processing Application a powerful and flexible tool for
anyone looking to edit, enhance, and manipulate digital images efficiently.


#### Graphical User Interface:

- User-Friendly Design: The application includes a modern, intuitive GUI that allows users to 
perform image processing tasks effortlessly, without requiring command-line expertise.

- Interactive Features: Users can preview changes in real time, adjust parameters dynamically, 
and view before-and-after comparisons using split views or side-by-side displays.

- Histogram Display: Visualize image data distribution with histogram charts, enabling better 
analysis and informed decisions during image adjustments.

- Multi-Panel Layout: The GUI organizes tools, preview areas, and controls in separate panels, 
ensuring a clean and accessible workspace.

- Drag-and-Drop Support: Simplifies loading images by allowing users to drag and drop files 
directly into the application.

- Customizable Adjustments: Provides sliders, input fields, and dropdown menus for fine-tuning
effects like brightness, contrast, and filters.

- Error Notifications: Pop-up messages inform users of invalid inputs or actions, ensuring smooth 
operation and preventing confusion.

- Cross-Platform Compatibility: The GUI is designed to run seamlessly on various operating systems, 
including Windows, macOS, and Linux.

This GUI ensures that users of all experience levels can efficiently interact with the application, 
making advanced image processing tools accessible to everyone.


## Technology Used:

The Image Processing Application is built using a combination of modern programming languages,
libraries, and frameworks that contribute to its performance, efficiency, and usability. Below are
the key technologies utilized in the development of this application:

#### Programming Language:

- Java: The core of the application is developed in Java, leveraging its robust object-oriented
  features and extensive standard libraries, making it suitable for building a scalable and
  maintainable image processing solution.

- Swing: The graphical user interface (GUI) is implemented using Java Swing, a powerful and flexible
toolkit for building rich desktop applications. Swing provides customizable components and 
cross-platform support, ensuring a consistent user experience across operating systems. It enables 
interactive features like sliders, dialog boxes, and preview panels, making the application intuitive 
and visually appealing.


#### Design Patterns:

- Model-View-Controller (MVC): The application follows the MVC architectural pattern, separating the
  business logic (Model), user interface (View), and input handling (Controller) to enhance
  modularity
  and maintainability.

- Factory Pattern: The command handling mechanism utilizes the Factory pattern to create command
  objects dynamically, promoting scalability and ease of command management.

- Command Design Pattern: The factory pattern is implemented via command design patten which makes
  it more efficient and readable to generate the appropriate object of the required command provided
  by the user.

- Composition Pattern: The composition pattern is implemented in the model where an object of
  `ImageOperations` class
  is composed in the `ImageModelImpl` class which is the class that is interacting with the
  controller. This is done
  because there is no parent-child relation between both the classes and for future references the
  `ImageOperations` class can be extended
  on its own to introduce new logic implementations of the new filters/operations.

#### Development Tools:

- Integrated Development Environment (IDE): Development was carried out using IDEs like IntelliJ
  IDEA, which provide advanced coding tools, debugging features, and project management
  capabilities.

- Version Control: Git is used for version control, allowing for collaborative development and
  efficient management of code changes.

- Junit4: Unit testing is facilitated through JUnit, ensuring that the application components work
  as intended and maintaining code quality throughout the development process.

- Java Swing: Used to build the graphical user interface (GUI), enabling a visually rich and 
interactive user experience. Swing's flexible components and cross-platform compatibility make it ideal for desktop applications.

These technologies combine to create a powerful, user-friendly image processing application that is
both efficient and scalable, catering to the needs of various users from beginners to advanced image
editors.

## Installation:

To set up the Image Processing Application on your local machine, please follow the steps outlined
below:

#### Prerequisites:

Before installing the application, ensure that you have the following installed on your machine:

- Java Development Kit (JDK): Ensure you have JDK 11 or higher installed. You can download it from
  the Oracle website or use a package manager for your operating system.

- IDE: While not mandatory, an Integrated Development Environment (IDE) like IntelliJ IDEA or
  Eclipse is recommended for development and debugging.

#### Steps to Install:

1. Clone the Repository: Open your terminal and run the following command to clone the repository:

```
git clone https://github.com/jwalshah/image-processing-app.git
```

2. Navigate to the Project Directory: Change your directory to the project folder:

```
cd image-processing-app
```

3. Run the Application :After successfully building the project, you can run the application using
   the following command:\

- Check the java version:

```
java --version
```

- Compile all the java files:

```
javac src/controller/*.java src/model/*.java src/*.java
```

- Run the main java file:

```
java -cp src Main
```

#### How to run with JAR

The user has the option to run the application's JAR file in either an interactive mode or a
non-interactive mode.

The user should navigate to the folder on their local system where the
ImageProcessingApplication.java file is located. (Use cd path/to/directory to change directories.)

```
cd <path_of_application> 
```

Now the user has two options either to work in Graphical user interface (GUI) mode,
interactive mode (via command line interface) or non
interactive mode (via script mode using a script).

For Graphical user interface mode:

```
java -jar res/group.jar
```

After this command you will see the frame of the image processing application. And then you are 
good to use this interface for your image operating tasks.

For interactive / Command line interface mode:

```
java -jar res/group.jar -text
```

After this command you will see a prompt reflecting "Enter commands (enter 'exit' to terminate the
program) :"
and then you can start executing commands one by one.

For non-interactive / Script mode:

In our case the resScript file is within the same folder where we are redirecting the terminal via
`cd` command.

```
java -jar res/group.jar -file res/ResScript.txt
```

#### Troubleshooting:

- If you encounter issues during the installation or running the application, ensure that all
  prerequisites are correctly installed and configured.

- Check the console for any error messages that may provide clues on what went wrong.

## Command Reference:

This section provides a list of available commands and their descriptions for the Image Processing
application.

#### Commands:

Load Command: The command below represents load command which includes path as 2nd parameter and
image name to refer in future manipulations.

```

load <image_path> <reference_name>

```

Save Command: Saves the manipulated image to the specified path using the name of the previously
loaded image reference.

```

save <output_path> <reference_name>

```

Run command: This command is used to run a ".txt" file where the commands are written in it to
execute line by line.

```

run <script_file_path>
```

Exit Command: This command is used to terminate the program. By just entering "exit" and pressing
enter the program will be terminated.

#### Operation Commands:

Brighten Command: Brightens the referenced image by the specified value. A positive value increases
brightness, while a negative value decreases it, the following parameters are the image name that
has to be manipulated and the image name that has to be named after manipulation.

```

brighten <intensity_value> <image_name> <reference_name>

```

Blur Command: Blur command blurs the image with a predefined filter that runs over the pixels of the
image in order to blur it.

Sharpen Command: Sharpen command sharpens the image with a predefined filter that runs over the
pixels of the image in order to sharpen it.

Sepia Command: Sepia command applies a sepia tone to the image that manipulates the pixels by matrix
multiplication method with the sepia equation.

Red-component Command: This converts the image into its red component where the RGB channel gets
converted to RRR channel.

Green-component Command: This converts the image into its green component where the RGB channel gets
converted to GGG channel.

Blue-component: This converts the image into its blue component where the RGB channel gets converted
to BBB channel.

Luma-component : This converts the image into the its red component where the RGB channel gets
multiplied with the luma equation to manipulate the image.

Value-component: This converts the image into the its red component where the RGB channel is changed
to maximum value of pixel and that gets replicated into all the three channel.

Intensity-component: This converts the image into the its red component where the RGB channel is
changed to the average value of pixel and that gets replicated into all the three channel.

Horizontal-flip command: This converts the image into its horizontally flipped form.

Vertical-flip command: This converts the image into its vertically flipped form.

Command syntax for above all filters:

```

<operation_name> <image_name> <reference_name>

```

RGB-split command: This command makes 3 separate images which are the red, green and blue components
of the images.

```

<rgb-split> <image_name> <red_reference_name> <green_reference_name> <blue_reference_name>

```

RGB-combine command: This command combines the 3 RGB components into a single image.

```

<rgb-combine> <reference_name> <red_image_name> <green_image_name> <blue_image_name>

```

Histogram Command: This command creates a histogram image of size 256x256 representing the pixels
of all the three channels which are red, green and blue of the image.

```
<histogram> <image_name> <reference_name>
```

Compression Command: This commands compresses the given image provided by the user via
command line interface based on the percentage which is again provided by the user.

```
<compress> <percentage> <image_name> <reference_name>
```

Level-Adjust Command: This command adjust the black, mid and white levels of the image based on the
black, mid and white values provided by the user through the command line interface.

```
<levels-adjust> <black_value> <mid_value> <white_value> <image_name> <reference_name>
```

Color-Correction Command: This command corrects the color of the image based on the histogram
peaks and then averages out the peaks and returns the image.

```
<color-correct> <image_name> <reference_name>
```

Split Preview Command: This is an advance operation that can be used along with the operations to
only
manipulate and preview the image after performing the operation on some part of the image. This
command
takes in the percentage value and performs the operation only on that much percent of the image.
Operations that can be used with split preview are:

- Blur
- Sharpen
- Sepia
- Red-component
- Green-component
- Blue-component
- Luma-component
- Value-component
- Intensity-component
- Level-adjust
- Color Correct

```
<operation_name> <parameters_(optional)> <image_name> <reference_name> <split> <percentage>
```

Masking Command: This is also an advance operation that can be used to partially
manipulate the image using the mask image. The mask image has to be provided by the user
itself for performing this operation. The mask image basically is a image with only
black and white pixels. The dimensions of the image that has to be manipulated and its mask
should be same in order to execute this operation. Only the black pixels in the mask image 
are referred by the operation to manipulate the base image.
Following are the operations that supports this masking feature:
- Blur command
- Sharpen command
- Sepia command
- Red-component command
- Green-component command
- Blue-component command
- Luma-component command
- Intensity-component command
- Value-component command

```
<operation_name> <image_name> <mask_image_name> <reference_name>
```

Note: Image downscaling is not supported by the command line interface.


## Code Structure:

This code structure for the project is displayed below:

```

ImageProcessingApplication/

│

├── src/

│   ├── controller/     # Handles the execution of commands and interaction with the model.

│   │   ├── AbstractCommand.java

│   │   ├── BrightenCommand.java

│   │   ├── SepiaCommand.java

│   │   ├── SharpenCommand.java

│   │   ├── BlurCommand.java

│   │   ├── SplitCommand.java

│   │   ├── CombineCommand.java

│   │   ├── FlipCommand.java

│   │   ├── LoadCommand.java

│   │   ├── SaveCommand.java

│   │   ├── ComponentCommand.java

│   │   ├── ColorCorrectCommand.java

│   │   ├── CompressCommand.java

│   │   ├── HistogramCommand.java

│   │   ├── LevelsAdjustCommand.java

│   │   ├── SplitPreviewCommand.java

│   │   └── DownScaleCommand.java

│   │   └── MaskImageCommand.java

│   │   ├── CommandFactory.java

│   │   └── ImageController.java

│   │   └── MVCController.java

│   │   └── FeaturesImpl.java

│   │   └── Features.java

│   │   ├── ControllerInterface.java

│   │

│   ├── model/    # Contains the core logic for image manipulation and operations.

│   │   ├── ImageOperations.java

│   │   ├── ImageOperationsV2.java

│   │   ├── ImageOperationsV3.java

│   │   ├── ImageModel.java

│   │   ├── ImageModelV2.java

│   │   ├── ImageModelV3.java

│   │   ├── ImageModelImpl.java

│   │   ├── ImageModelImplV2.java

│   │   ├── ImageModelImplV3.java

│   │   └── ImageData.java

│   │   └── AbstractSpecificOps.java

│   │   └── NormalizeedHistogram.java

│   │   └── ImageCompression.java

│   │

│   ├── view/    # Contains the logic for the success and failure messages that are displayed to the user.

│   │   └── Message.java

│   │   └── MessageView.java

│   │   └── IView.java

│   │   └── JFrameView.java

│   │

│   ├── utils/    # Contains the logic for common functionalities that can used by this application.

│   │   └── MyUtils.java

│   │

│   └── Main.java               # Entry point for the application.

│

├── test/

│   ├── controller/             # Unit tests for the controller components.

│   │   └── tempTestImages/         # Contains images for testing purposes

│   │   └── ImageControllerTest.java

│   │   └── MVCControllertest.java

│   │   └── MockGUIView.java

│   │   └── MockImageModelImpl.java

│   │   └── MockImageModelImplV2.java

│   │   └── MockImageModelImplV3.java

│   │   └── testFalseScript.txt

│   │   └── testTrueScript.txt

│   │

│   └── model/                  # Unit tests for the model components.

│       └── testImages/  # Images for testing in model.

│       └── ImageModelImplTest.java

│       └── ImageModelImplV2Test.java

│       └── ImageModelImplV3Test.java

│

└── res/                        # Resources directory for storing image files used in testing.

    └── (Images for loading, saving, and processing)

```

This structure ensures a clean separation of concerns, allowing easy modifications, testing, and
future extension of functionalities.

## Architecture:

The Image Processing Application is built using a clean architecture that separates concerns across
three key packages: model, controller, and main. This section explains the role of each package and
provides a detailed explanation of the core classes within them.

#### Model Package:

The model package is the core of the application. It handles all the image data and the manipulation
logic, separating image operations from user interaction. This package encapsulates the logic that
processes and transforms images based on various filters and effects.

- `ImageModel`: This is the interface that defines the methods for interacting with the image
  model. It provides abstraction for operations like loading, saving, and applying filters on
  images.


- `ImageModelImpl`: This class implements the ImageModel interface and provides the functionality
  for manipulating images. It includes methods to load and save images, apply various filters (e.g.,
  blur, sharpen, brighten), and perform operations such as flipping and component extraction.


- `ImageOperations`: This helper class contains the logic to perform specific image filtering
  operations. It supports image transformations like brightening, flipping, applying sepia,
  blurring,
  and sharpening. Each filter manipulates pixel-level data, altering RGB values to achieve the
  desired
  effect.


- `ImageModelV2`: This is the interface that extends `ImageModel` interface and introduces new
  operation
  abstraction for histogram, compression, level-adjustment and color-correction.


- `ImageModelImplV2`: This is the new implementation class which extends `ImageModelImpl` class and
  implements
  the new `ImageModelV2` interface to implement the new functionality introcuded in the version 2 of
  this application.


- `ImageOperationV2`: This is the new helper class which extends `ImageOperations` class which
  contains the
  logic of the new functionalities such as level adjustment, color correction, image compression and image histogram
  which are introduced in the new version 2.


- `ImageModelV3`: This is the interface that extends `ImageModelV2` interface and introduces new
  operation
  abstraction for Image downscaling and manipulation of images with mask images.


- `ImageModelImplV3`: This is the new implementation class which extends `ImageModelImplV2` class and
  implements
  the new `ImageModelV3` interface to implement the new functionality introcuded in the version 3 of
  this application.


- `ImageOperationV3`: This is the new helper class which extends `ImageOperationsV2` class which
  contains the
  logic of the new functionalities such as manipulating image with a mask image, image downscaling 
  which are introduced in the new version 3.


- `AbstractSpecificOps`: This class works as an umbrella for specific logic implementation of
  classes
  which requires more than 2/3 helper method in themselves to make it more modular and readable.


- `NormalizedHistogram`: This class extends `AbstractSpecificOps` and contains the logic for
  generating
  the histogram on the user specified image.


- `ImageCompression`: This class extends `AbstractSpecificOps` and contains the logic for
  compressing
  the image upto the percentage that is again provided by the user via command line interface.


- `ImageData`: This class represents the structure of the image, holding its height, width, and
  pixel data in a 3D RGB array format. It acts as a data transfer object for images.

#### View Package:

- `Message`: This is the interface for the view package which integrates with the command line interface 
   and which works as a contract for this package for that mode.


- `MessageView`: This is the class of the view which is interacting with the controller when
   the command line interface mode is triggered and which performs the task of displaying either
   the success or the failure messages to the user.


- `IView` : This is the interface for the view package which integrates with the graphical user
   interface and that works as a contract for this package for that mode.


- `JFrameView` : This is also the class of the view which interacts with the controller when the
  graphical user interface is triggered and which performs the task of displaying the framework and
  the GUI that has been implemented within this class.


#### Controller Package:

The controller package manages the user input and command execution. It interacts with the Model to
apply the appropriate operations based on user commands, serving as the connection between user
inputs and model logic.

- `ControllerInterface`: This is the controller interface that serves as a contract between the controller, model, and view. It defines the essential methods required to facilitate interaction between the user interface and the underlying application logic.

- `Features`: This interface defines the various operations (or features) available in the image processing application. It allows the view to trigger specific functionality in the model, such as loading images, applying filters, adjusting brightness, and generating histograms.

- `ImageController`: The primary controller class responsible for managing user input from either the console or script files. It validates commands and coordinates between the model and view to execute user requests. Supported operations include loading and saving images, applying filters (e.g., blur, sepia), and performing image component manipulations (e.g., splitting into RGB components).

- `MVCController`: A GUI-based implementation of the controller interface, designed specifically for applications with a graphical user interface (GUI). It listens to user interactions from the GUI, processes them, and updates the view with the corresponding results, ensuring a smooth user experience.

- `FeaturesImpl`: This class provides the concrete implementation of the Features interface. It encapsulates the logic required to perform various operations on images, including file handling, filter application, brightness adjustments, and histogram generation. It acts as a bridge between the GUI and the model.

- `AbstractCommand`: This is an abstract class that defines the structure for all command
  classes. Each specific command (e.g., brighten, blur, flip) extends this class and implements the
  execute method to define its behavior.


- `BrightenCommand`: Extends the `AbstractCommand` class. It extracts the required parameters such
  as
  intensity, save image name and source image name from the command and delegates it to the model
  for further manipulation.


- `SepiaCommand`: Extends the `AbstractCommand` class. It extracts the required parameters such as
  save image name and source image name from the command and delegates it to the model for further
  manipulation.


- `SharpenCommand`: Extends the `AbstractCommand` class. It extracts the required parameters such as
  save image name and source image name from the command and delegates it to the model for further
  manipulation.


- `BlurCommand`: Extends the `AbstractCommand` class. It extracts the required parameters such as
  save image name and source image name from the command and delegates it to the model for further
  manipulation.


- `SplitCommand`: Extends the `AbstractCommand` class. It extracts the required parameters such as
  red image name, green image name, blue image name and save image name from the command and
  delegates it to the model for further manipulation.


- `CombineCommand`: Extends the `AbstractCommand` class. It extracts the required parameters such as
  source image name and component image names from the command and delegates it to the model for
  further manipulation.


- `FlipCommand`: Extends the `AbstractCommand` class.It extracts the required parameters such as
  flip-type, save image name and source image name from the command and delegates it to the model
  for further manipulation.


- `ComponentCommand`: Extends the `AbstractCommand` class. It extracts the required parameters such
  as
  Component type, save image name and source image name from the command and delegates it to the
  model for further manipulation.


- `LoadCommand`: Extends the `AbstractCommand` class. Loads an image from a specified file path into
  the model. Also responsible for
  extension conversions based on user's need.


- `SaveCommand`: Extends the `AbstractCommand` class. Saves the processed image to a specified file
  path. Also responsible for
  extension conversions based on user's need.


- `HistogramCommand`: Extends the `AbstractCommand` class. It extracts the required parameters such
  as
  save image name and source image name from the command and delegates it to the model for further
  manipulation.


- `CompressCommand`: Extends the `AbstractCommand` class. It extracts the required parameters such
  as
  compression percentage, save image name and source image name from the command and delegates it to
  the model for further manipulation.


- `LevelsAdjustCommand`: Extends the `AbstractCommand` class. It extracts the required parameters
  such as
  black, mid and white values, save image name and source image name from the command and delegates
  it to the model for further manipulation.


- `ColorCorrectCommand`: Extends the `AbstractCommand` class. It extracts the required parameters
  such as
  save image name and source image name from the command and delegates it to the model for further
  manipulation.


- `SplitPreviewCommand`: Extends the `AbstractCommand` class. It extracts the required parameters
  such as
  operation-type, parameters if required, split percentage, save image name and source image name
  from the command and delegates it to the model for further manipulation.


- `MaskImageCommand` : Extends the `AbstractCommand` class. It extracts the required parameters
  such as
  operation-type, image name, mask image name and save image name
  from the command and delegates it to the model for further manipulation.


- `DownScaleCommand` : Extends the `AbstractCommand` class. It extracts the required parameters
  such as
  image name, height, width and save image name
  from the command and delegates it to the model for further manipulation. This feature is not
  supported by the command line interface.


- `CommandFactory`: This class is responsible for creating instances of commands based on the
  input command string. It maps user commands (like brighten, blur, save) to their respective
  classes.

#### Utils Package:

- `MyUtils`: This is the class of the utils package which contains static method which can be used
  by any of the packages inside this application. The methods in this class are the common helper
  methods.

#### Main Class:

- `Main`: This is the entry point of the application. It initializes the model, view and the
  controller
  and starts the application. The controller takes user input (either from the console or a script
  file) and passes it to the model for processing.

## Design Changes and Justification

In the third development phase of our Java-based image processing application, we enhanced its
capabilities by introducing advanced image operations, including image downscaling,
and partial image manipulation using mask image. These additions were carefully integrated to 
expand the app's functionality while retaining its original design integrity.

To seamlessly incorporate these new features, we embraced the Model-View-Controller (MVC)
architecture and adhered to core principles of extensibility. This architectural approach allows for
flexible scaling of features, enabling smooth integration of future enhancements without impacting
existing functionality. The updated MVC structure supports clean separation of concerns,
with each component handling distinct responsibilities: the Model manages data and business logic,
the View presents processed image data to users, and the Controller serves as an intermediary,
directing commands and orchestrating interactions between the Model and View.

#### Changes in model:

In the second phase of development, we extended the functionality of the model package to support
new image processing operations, while keeping the existing design intact. Below is a summary of the
changes made in the model package, along with the rationale for each update.

New Classes and Updates in the Model Package

- `V3-Suffixed Classes`:

Purpose: The new classes ending with V3 represent the updated versions of existing classes. These
classes were introduced to accommodate the additional functionality without altering the behavior of
the original classes, ensuring backward compatibility.

Justification: By creating V3 versions, we enabled the application to support both legacy
functionality and new features. This approach follows the open/closed principle, where we add new
capabilities without modifying existing, stable code. This follows the principle of code reuse.

- No Changes to Existing Classes

To maintain stability and avoid introducing unexpected bugs, no changes were made to the original
classes in the model package. Existing functionality remains unaffected, allowing users who rely on
the original classes to continue using them as expected.

#### Changes in controller:

Here are the changes made to the controller package in the second development phase of the
application. These modifications aim to enhance functionality, maintain flexibility, and align with
the principles of the command design pattern and SOLID principles:

- Introduction to New Interface Features

The `Features` interface was introduced to decouple the controller logic from the view. 
It defines high-level operations like loading, saving, and manipulating images, which the view can trigger.
This abstraction allows the view to communicate with the controller without directly invoking command classes, 
promoting flexibility and adhering to the dependency inversion principle.

- New MVCController

The `MVCController` is a GUI-focused controller that implements the `ControllerInterface`. It serves as 
the bridge between the `JFrameView` (GUI) and the `ImageModel`, 
ensuring seamless integration of graphical user inputs. The `MVCController` handles user actions such
as selecting files, applying filters, and previewing changes, 
and it updates the GUI dynamically with processed results.

- New FeaturesImpl Class 

The `FeaturesImpl` class provides the concrete implementation of the `Features` interface. 
It encapsulates all image processing operations, enabling the controller to handle commands in a 
modular and extensible manner. This class integrates the new commands 
(e.g., `DownScaleCommand` and `MaskImageCommand`) into its functionality, 
ensuring that the GUI has access to all the advanced features available 
in the latest model implementations.

- Introduction of New Command Classes

To integrate new image processing functionalities, two command classes were added:
`DownScaleCommand` and `MaskImageCommand`. Each of these new classes aligns with the command design pattern and
encapsulates specific image transformation logic, providing a clean structure for future extension.

- Utilization of abstractCommand for Consistent Abstraction

Each of the new command classes extends the existing `AbstractCommand` class, which promotes
higher-order abstraction. This setup allows the controller to handle new commands through the same
overarching command interface, preserving the uniformity of the design.

- Typecasting for Advanced Features

Since the controller receives an `ImageModel` interface object, typecasting to the `ImageModelV2` or `ImageModelV3`
interface (the enhanced model interface with new functionalities) is used in relevant classes. This
casting is required for accessing advanced methods in the `ImageModelImplV2` or `ImageModelImplV3`  implementation and
ensures that the controller supports newer image manipulation features while keeping compatibility
with the original structure. Typecasting based on interfaces maintains best practices and does not
compromise the system’s integrity.

- Command Factory Enhancement for New Commands

In the `CommandFactory`, additional key-value pair of image mask operation was added to map 
new commands to their respective command classes. This update allows the command factory to 
instantiate and execute these new commands seamlessly, expanding the controller's capabilities without requiring significant
structural changes.


- Preservation of SOLID Principles

The controller changes adhere to SOLID principles by focusing on single responsibility, open-closed,
and dependency inversion. By separating concerns between commands, maintaining consistent
abstraction with abstractCommand, and ensuring each new feature is modular, these principles are
thoroughly upheld, resulting in a robust and extensible controller design.

Unchanged Components:

All previously implemented classes and interfaces remain intact, ensuring that the original
structure is preserved while accommodating new features. This approach allows the new additions to
fit seamlessly into the existing design, avoiding disruption and maintaining stability across the
application.

#### Changes in View:

The View package was updated with two key files to enhance user interaction with the application:

- `IView` :
   The IView interface defines the contract for the View component in the MVC architecture. 
   It includes methods for:

    Displaying images and previews.
    Managing file loading and saving.
    Displaying histograms.
    Showing error messages.
    Implementing this interface ensures consistent interaction between the View and the Controller.

- `JFrameView` :
   The JFrameView class implements the IView interface, providing a Swing-based graphical user interface (GUI). 
   It handles user interactions and displays images, previews, and histograms in the GUI. This class enables users to interact with the application visually, making it more accessible and user-friendly.

These additions provide both command-line and GUI interfaces for better usability in image processing tasks.


## Image formats Support

This application supports these following extension for loading, saving and manipulating the images:

- `PPM` : Portable PixelMap Format
- `JPG`/`JPEG` : Joint Photographic Experts Group Format.
- `PNG` : Portable Network Graphics.

Note: PPM images are saved in the plain text format (P3).



## Class Diagram:

![Class-Diagram](res/ClassDiagram.png)

## References:

Images used for script testing and application testing:

- [bird.png](https://as2.ftcdn.net/v2/jpg/09/37/12/73/1000_F_937127370_RzigoTq55hhV6TOcnRXbZ2kBjSOgWUMJ.jpg)
- [lion.jpeg](https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FLion&psig=AOvVaw1aE5J6dN0eygZmnhUbNext&ust=1729795790593000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCJD15dCVpYkDFQAAAAAdAAAAABAE)

