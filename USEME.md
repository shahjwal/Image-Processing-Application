**USEME**
----------------------------------------------------------------------------------------------------------------------
USEME for Image Processing Application

----------------------------------------------------------------------------------------------------------------------

[//]: # (This USEME file provides instructions on how to operate the Image Processing Application. You can)

[//]: # (utilize this application through **Command Line Interface &#40;CLI&#41;**.)

[//]: # ()

[//]: # (This application offers a command-based interface to perform a range of image processing operations.)

[//]: # (Below is a summary of the available commands, their functions, usage examples, and any relevant)

[//]: # (conditions or requirements.)

[//]: # ()

[//]: # (**IMPORTANT:** Users must follow the precise format for each command as outlined below.)

[//]: # (Any deviations or formatting errors could cause execution issues, and altering the argument order)

[//]: # (may lead to unexpected results.)





This USEME file provides instructions on how to operate the Image Processing Application. You can
utilize this application through both a **Command Line Interface (CLI)** and a **Graphical User
Interface (GUI)**.

This application offers users two convenient ways to perform a wide range of image processing
operations:

1. **Command Line Interface (CLI):** A command-based interface for executing operations by typing
   precise commands.
2. **Graphical User Interface (GUI):** An intuitive and accessible interface that enhances
   functionality, allowing users to perform tasks visually without needing to remember specific
   commands.

The GUI supports all the features available in the CLI, such as loading and saving images, applying
filters, and adjusting image properties. Additionally, it offers a unique **Image Downscaling**
feature, allowing users to resize images while preserving their aspect ratio. This is particularly
useful for optimizing images for the web or adjusting resolution. Since downscaling is not available
through the CLI, the GUI provides an intuitive and efficient way to resize images without
distortion.

**NOTE:** For users who prefer the CLI, it is essential to follow the exact command format outlined
in this document. Deviating from the specified format or altering the argument order may result in
execution errors or unexpected behavior.

----------------------------------------------------------------------------------------------------------------------

## Table of Contents

- [Command Line Interface USAGE](#command-line-based-interaction)
- [Graphical User Interface](#graphical-user-interface)

### Command Line Interface USAGE

**Commands Available**:

1\. **Load:** `load <filepath> <dest-image-name>`

- Load an image (formats: jpg/png/jpeg/ppm).

- Example: `load res/bird.png myimage`

2\. **Save:** `save <filepath> <existing-image-name>`

- Save the current image to a specified location.
- Saving can be done in 4 different extension based on user's choice which are PNG, JPG, JPEG or
  PPM.

- Example: `save res/bird.ppm myimage`

3\. **Image Blur**: `blur <source-image-name> <dest-image-name> [split <percentage>]`

- Apply blur effect to the image.
- Option to preview with a split percentage which should be within 0 to 100 (floating point
  allowed).
- Example: `blur myimage myimage-blur` or `blur myimage myimage-blur split 50`

4\. **Image Sharpen**: `sharpen <source-image-name> <dest-image-name> [split <percentage>]`

- Apply sharpen effect to the image.
- Option to preview with a split percentage which should be within 0 to 100 (floating point
  allowed).
- Example: `sharpen myimage myimage-sharpen` or `sharpen myimage myimage-sharpen split 50`

5\. **Sepia Tone**: `sepia <source-image-name> <dest-image-name> [split <percentage>]`

- Apply sepia effect on the image.
- Option to preview with a split percentage which should be within 0 to 100 (floating point
  allowed).
- Example: `sepia myimage myimage-sepia` or `sepia myimage myimage-sepia split 50`

6\. **Brighten/Darken**: `brighten <intensity> <source-image-name> <dest-image-name>`

- Adjust brightness by a specified increment (positive or negative).
- Intensity value should be an integer (not in words or a floating number).
- Example: `brighten 50 myimage myimage-bright` or `brighten -150 myimage myimage-bright`

7\. **Fliping Image:**

- Flips the image either vertically or horizontally based on the user provided command.
- **Horizontal:** `horizontal-flip <source-image-name> <dest-image-name>`
- **Vertical:** `vertical-flip <source-image-name> <dest-image-name>`
- Example: `horizontal-flip myimage myimage-H` or `vertical-flip myimage myimage-V`

8\. **Color and Greyscale Components:**

- **Red:** `red-component <source-image-name> <dest-image-name> [split <percentage>]`
- Produces red-component of the image with chanel (R,R,R).
- Option to preview with a split percentage which should be within 0 to 100 (floating point
  allowed).
- Example: `red-component myimage myimagered split 50` or `red-component myimage myimagered`


- **Green:** `green-component <source-image-name> <dest-image-name> [split <percentage>]`
- Produces green-component of the image with chanel (G,G,G).
- Option to preview with a split percentage which should be within 0 to 100 (floating point
  allowed).
- Example: `green-component myimage myimagegreen split 50` or `green-component myimage myimagegreen`


- **Blue:** `blue-component <source-image-name> <dest-image-name> [split <percentage>]`
- Produces blue-component of the image with chanel (B,B,B).
- Option to preview with a split percentage which should be within 0 to 100 (floating point
  allowed).
- Example: `blue-component myimage myimageblue split 50` or `green-component myimage myimageblue`


- **luma:** `luma-component <source-image-name> <dest-image-name> [split <percentage>]`
- Produces luma-component of the image.
- Option to preview with a split percentage which should be within 0 to 100 (floating point
  allowed).
- Example: `luma-component myimage myimageluma split 60.3` or `luma-component myimage myimageluma`


- **Value:** `value-component <source-image-name> <dest-image-name> [split <percentage>]`
- Produces value-component of the image.
- Option to preview with a split percentage which should be within 0 to 100 (floating point
  allowed).
- Example: `value-component myimage myimagevalue split 50` or
  `value-component myimage myimagevalue`


- **Intensity:** `intensity-component <source-image-name> <dest-image-name> [split <percentage>]`
- Produces intensity-component of the image.
- Option to preview with a split percentage which should be within 0 to 100 (floating point
  allowed).
- Example: `intensity-component myimage myimageintensity split 50.4` or
  `intensity-component myimage myimageintensity`

9\. **RGB Operations**:

- **Split:**
  `rgb-split <source-image-name> <red-dest-image-name> <green-dest-image-name> <blue-dest-image-name>`
- Splits the image into its red, green and blue components respectively.
- Example: `rgb-split myimage myimage-red myimage-green myimage-blue`


- **Combine:** `rgb-combine <dest-image-name> <red-image> <green-image> <blue-image>`
- Combines the image from its red, green and blue components to make the final image.
- Example: `rgb-combine myimage-red myimage-green myimage-blue myimage`

10\. **Color Correction:**
`color-correct <source-image-name> <dest-image-name> [split <percentage>]`

- Performs color correction on the image.
- Option to preview with a split percentage which should be within 0 to 100 (floating point
  allowed).
- Example: `color-correct myimage myimagecorrected` or `color-correct myimage myimagecorrected`

11\. **Levels Adjust:**
`levels-adjust <black> <mid> <white> <source-image-name> <dest-image-name> [split <percentage>]`

- Performs level adjustment on the image based on the black, mid and white value provided through
  the command.
- Black, mid and white values should be within 0 to 155 and should be in ascending order.
- Option to preview with a split percentage which should be within 0 to 100 (floating point
  allowed).
- Example: `levels-adjust 0 100 255 myimage myimageadjusted` or
  `levels-adjust 0 100 255 myimage myimageadjusted split 50`

12\. **Image Compression**: `compress <percentage> <source-image-name> <dest-image-name>`

- Compresses the image based on the percentage (within 0-100 and floating value not allowed)
  provided by the user.
- Example: `compress 40 myimage compressedmyimage`

13\. **Image Histogram**: `histogram <source-image-name> <dest-image-name>`

- Generates Histogram of the image based on the pixel values of the image.
- Example: `histogram myimage myimagehistogram`

14\. **Run Script**: `run <filepath>`

- runs the script file present on the filepath which contains the commands to execute.
- Example: `run res/ResScript.txt`

15\. **Mask**: `<operation_name> <image_name> <mask_image_name> <reference_name>`

- This feature allows the operation to be applied only on the selected pixels, using the mask image
  as a reference.
- The mask image is an image with only black and white pixels, where black pixels are the only ones
  used for manipulation.
- Operations with available masking functionality:
    - **blur**
    - **sharpen**
    - **sepia**
    - **red-component**
    - **blue-component**
    - **green-component**
    - **luma-component**
    - **value-component**
    - **intensity-component**
    -
- Example:

`blur myimage maskimage maskedmyimageBlur`or
`luma-component myimage maskimage maskedmyimageLuma`

15\. **Exit:** `exit`

- Ends the application when user enters exit.

----------------------------------------------------------------------------------------------------------------------

Important Instructions:

- Commands should be implemented in the same order as they are provided in this USEME file.
- Only one command should be executed at one time.
- Image operations can only be done on the images which are loaded previously.

For more details on each command, refer to the README file. Enjoy using the Image Processing
Application!

----------------------------------------------------------------------------------------------------------------------

### Graphical User Interface

#### How to use GUI:

![load2.png](screenshots/load2.png)

- In the top panel there is two button Load image and save image.
- On the left panel there is a operation's list which contains buttons for each operation and split
  preview.
- Below the top panel and to the right of the operations panel, there are two sections: one displays
  the current image, and the other shows the histogram of the current image.

#### Load an Image

- On clicking the `load` button:
  ![load1.png](screenshots/load1.png)

- A popup menu will appear, allowing you to select a file type (e.g., PNG, PPM, JPEG,jpg).
- Once you select an image and click the `Open` button, the image will be loaded into the
  application and displayed in the GUI.

  ![load2.png](screenshots/load2.png)

- As shown in the image above, the selected image will be displayed at the center of the GUI, and
  the histogram of the same image will appear below it.
- If you try to load a new image while an image is already displayed in the GUI, a popup will appear
  asking, `Do you want to overwrite the loaded image?`.

  ![load3.png](screenshots/load3.png)

- If you press yes then image will be overwritten and new image will be displayed in the GUI. If you
  press no then current image will be shown.

  ![load4.png](screenshots/load4.png)

- There is another option for loading an image, which is the drag and drop option.
- The left panel where the message “Load an image here by clicking the load button or drag the
  image here” is displayed, is the place where the image should be dropped to load that image.

  ![load5.png](screenshots/load5.png)

#### Save Image

- After doing some image operations, the user will be able to save the image by clicking the `save`
  button.

  ![save1.png](screenshots/save1.png)

- If the user gave invalid format of image, on clicking the `save` button the user will be shown
  error popup saying `Invalid extension`.

  ![save2.png](screenshots/save2.png)

- If the user tries to save the image without loading, then error message will be displayed.
  ![error.png](screenshots/error.png)

#### Change Brightness

- When the user clicks the `Change Brightness` button, the user will be prompted to enter the value
  to which amount to brighten the image.
  ![bright1.png](screenshots/bright1.png)
- On clicking `Apply` button, the image operation will be applied on the image and resultant image
  will be shown in the center panel in GUI.
  ![bright2.png](screenshots/bright2.png)
- If the user directly clicks the `Change Brightness` button without loading the image, the error
  message will be
  shown.
  ![error.png](screenshots/error.png)

#### Blur Image

- After loading the image, when the user clicks on the `Blur Image` button, the GUI looks like:

  ![blur1.png](screenshots/blur1.png)
- In the Split preview popup we have placed a slider for the percentage. By default, user can see
  the 50% preview.
- When the user clicks on the `cancel` button the popup menu will be closed.
- If user clicks on apply button the image will be 100% blurred and histogram will also be updated
  and shown in GUI.
  ![blur2.png](screenshots/blur2.png)
- If the user directly clicks the `blur` button without loading the image, the error message will be
  shown.
  ![error.png](screenshots/error.png)

#### Sharpen Image

- After loading the image, when the user clicks on the `Sharpen Image` button, the GUI looks like:
  ![sharpen1.png](screenshots/sharpen1.png)
- In the Split preview popup we have placed a slider for the percentage. By default, user can see
  the 50% preview.
- When the user clicks on the `cancel` button the popup menu will be closed.
- If user clicks on apply button the image will be 100% sharpened and histogram will also be updated
  and shown in GUI.
  ![sharpen2.png](screenshots/sharpen2.png)
- If the user directly clicks the `sharpen` button without loading the image, the error message will
  be shown.
  ![error.png](screenshots/error.png)

#### Apply Sepia

- After loading the image, when the user clicks on the `Apply Sepia` button, the GUI looks like:
  ![sepia1.png](screenshots/sepia1.png)
- In the Split preview popup we have placed a slider for the percentage. By default, user can see
  the 50% preview.
- When the user clicks on the `cancel` button the popup menu will be closed.
- If user clicks on apply button, 100% sepia operation will be done and histogram will also be
  updated and shown in GUI.
  ![sepia2.png](screenshots/sepia2.png)

- If the user directly clicks the `Apply Sepia` button without loading the image, the error message
  will be shown.
  ![error.png](screenshots/error.png)

#### Horizontal Flip

- When the user clicks on the `Horizontal Flip` button, the GUI looks like:
  ![horizontal1.png](screenshots/horizontal1.png)
- The loaded image will be horizontally flipped, and it's histogram will be shown in GUI.
  ![horizontal2.png](screenshots/horizontal2.png)

#### Vertical Flip

- When the user clicks on the `Vertical Flip` button, the GUI looks like:
  ![horizontal1.png](screenshots/horizontal1.png)
- The loaded image will be vertically flipped, and it's histogram will be shown in GUI.
  ![vertical1.png](screenshots/vertical1.png)

#### Color Correction

- After loading the image, when the user clicks on the `Color Correction` button, the GUI looks
  like:
  ![color1.png](screenshots/color1.png)
- In the Split preview popup we have placed a slider for the percentage. By default, user can see
  the 50% preview.
- When the user clicks on the `cancel` button the popup menu will be closed.
- If user clicks on apply button, 100% color correction operation will be done and histogram will
  also be
  updated and shown in GUI.
  ![color2.png](screenshots/color2.png)

- If the user directly clicks the `Color Correction` button without loading the image, the error
  message
  will be shown.
  ![error.png](screenshots/error.png)

#### Red Component

- After loading the image, when the user clicks on the `Red Component` button, the GUI looks like:
  ![red1.png](screenshots/red1.png)
- In the Split preview popup we have placed a slider for the percentage. By default, user can see
  the 50% preview.
- When the user clicks on the `cancel` button the popup menu will be closed.
- If user clicks on apply button, 100% red component operation will be done and histogram will also
  be
  updated and shown in GUI.
  ![red2.png](screenshots/red2.png)

- If the user directly clicks the `Red Component` button without loading the image, the error
  message
  will be shown.
  ![error.png](screenshots/error.png)

#### Green Component

- After loading the image, when the user clicks on the `Green Component` button, the GUI looks like:
  ![green1.png](screenshots/green1.png)
- In the Split preview popup we have placed a slider for the percentage. By default, user can see
  the 50% preview.
- When the user clicks on the `cancel` button the popup menu will be closed.
- If user clicks on apply button, 100% green component operation will be done and histogram will
  also be
  updated and shown in GUI.
  ![green2.png](screenshots/green2.png)

- If the user directly clicks the `Green Component` button without loading the image, the error
  message
  will be shown.
  ![error.png](screenshots/error.png)

#### Blue Component

- After loading the image, when the user clicks on the `Blue Component` button, the GUI looks like:
  ![blue1.png](screenshots/blue1.png)
- In the Split preview popup we have placed a slider for the percentage. By default, user can see
  the 50% preview.
- When the user clicks on the `cancel` button the popup menu will be closed.
- If user clicks on apply button, 100% blue component operation will be done and histogram will also
  be
  updated and shown in GUI.
  ![blue2.png](screenshots/blue2.png)

- If the user directly clicks the `Blue Component` button without loading the image, the error
  message
  will be shown.
  ![error.png](screenshots/error.png)

#### Greyscale

- After loading the image, when the user clicks on the `Greyscale` button, the GUI looks like:
  ![grey1.png](screenshots/grey1.png)
- In the Split preview popup we have placed a slider for the percentage. By default, user can see
  the 50% preview.
- When the user clicks on the `cancel` button the popup menu will be closed.
- If user clicks on apply button, 100% greyscale image and histogram will also be
  updated and shown in GUI.
  ![grey2.png](screenshots/grey2.png)

- If the user directly clicks the `Greyscale` button without loading the image, the error message
  will be shown.
  ![error.png](screenshots/error.png)

#### Level Adjust

- When the user clicks on `Level Adjust` button, GUI will look like:
  ![level1.png](screenshots/level1.png)
- The user will be prompted to enter black, mid and white values for level adjustment operation.
- If the user enters invalid values for black, mid and white, the user will be prompted error
  message if value is greater than 255 or less than 0.
  ![level2.png](screenshots/level2.png)
- If the user enters invalid values for black, mid and white, the user will be prompted error
  message if the condition is not satisfied.
  ![level3.png](screenshots/level3.png)
- After adding the valid inputs, the user will have to click `Apply Level Adjustment` button which
  will preview the level adjustment image and user can use the slider to input split percentage.
- When the user clicks on the `cancel` button the popup menu will be closed.
  ![level4.png](screenshots/level4.png)
- If user clicks on apply button, 100% level adjust image and histogram will also be
  updated and shown in GUI.
  ![level5.png](screenshots/level5.png)
-
- If the user directly clicks the `Greyscale` button without loading the image, the error message
  will be shown.
  ![error.png](screenshots/error.png)

#### Downscale Image

- When the user clicks on `Downscale Image` button, the user will be prompted height value and
  weight value.
  ![downscale1.png](screenshots/downscale1.png)
- After entering the values, then the user will click on `Apply Downscale` button which will preview
  the image operation.
  ![downscale2.png](screenshots/downscale2.png)
- On clicking `Apply` the image operation will be applied and resultant image will be shown on
  center panel in GUI.
  ![downscale3.png](screenshots/downscale3.png)
- If the user directly clicks the `Apply Downscale` button without loading the image, the error
  message will be shown.
- If user press on the cancel button the popup menu will be closed.
  ![error.png](screenshots/error.png)
- If the height and width provided are not within the range of 0 to the respective height and width
  of the image that we are working on, then this error will be displayed as shown below.
  ![d1.png](screenshots/d1.png)
  ![d2.png](screenshots/d2.png)
  ![d3.png](screenshots/d3.png)
  ![d4.png](screenshots/d4.png)

#### Compress Image

- When the user clicks `Compress Image` button, the user will be shown a prompt for compress
  percentage with a slide.
  ![compress.png](screenshots/compress.png)
- After the user selects the compress percentage, the image will be compressed on clicking the
  `apply` button.
- The compressed image will be displayed on the panel along with its histogram.
  ![compress2.png](screenshots/compress2.png)
- If the user directly clicks the `Compress Image` button without loading the image, the error
  message
  will be shown.
  ![error.png](screenshots/error.png)


