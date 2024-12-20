package model;

/**
 * The ImageModelImplV2 class provides an extended implementation for the ImageModel interface,
 * offering additional image processing operations such as compression, histogram adjustments, level
 * adjustments, color correction, and split preview.
 */
public class ImageModelImplV2 extends ImageModelImpl implements ImageModelV2 {

  private final ImageOperationsV2 imageOpsV2;

  /**
   * Constructs an ImageModelImplV2 instance, initializing the ImageOperationsV2 object used for
   * extended image manipulations and inherits the image collection and operations from the parent
   * class.
   */
  public ImageModelImplV2() {
    super();
    imageOpsV2 = new ImageOperationsV2();
  }

  /**
   * Applies compression to the specified image and saves the result. Delegates the compression
   * operation to the ImageOperationsV2 class.
   *
   * @param percentage the percentage of image compression (0-100).
   * @param imageName  the name of the image to compress.
   * @param saveImage  the name under which the compressed image is saved.
   */
  @Override
  public void applyCompress(int percentage, String imageName, String saveImage) {
    ImageData inputImage = this.getImage(imageName);
    int[][][] newRGB = imageOpsV2.imageCompression(inputImage, percentage);
    this.addImage(saveImage, newRGB);
  }

  /**
   * Applies histogram adjustment to the specified image and saves the result. Delegates the
   * histogram operation to the ImageOperationsV2 class.
   *
   * @param imageName the name of the image to adjust.
   * @param saveImage the name under which the adjusted image is saved.
   */
  @Override
  public void applyHistogram(String imageName, String saveImage) {
    ImageData inputImage = this.getImage(imageName);
    int[][][] newRGB = imageOpsV2.imageHistogram(inputImage);
    this.addImage(saveImage, newRGB);
  }

  /**
   * Applies level adjustment to the specified image and saves the result. Delegates the level
   * adjustment operation to the ImageOperationsV2 class.
   *
   * @param imageName the name of the image to adjust.
   * @param saveImage the name under which the adjusted image is saved.
   * @param black     the black level adjustment.
   * @param mid       the mid level adjustment.
   * @param white     the white level adjustment.
   */
  @Override
  public void applyLevelAdjustment(String imageName, String saveImage, int black, int mid,
      int white) {
    ImageData inputImage = this.getImage(imageName);
    int[][][] newRGB = imageOpsV2.levelAdjustment(inputImage, black, mid, white);
    this.addImage(saveImage, newRGB);
  }

  /**
   * Applies color correction to the specified image and saves the result. Delegates the color
   * correction operation to the ImageOperationsV2 class.
   *
   * @param imageName the name of the image to correct.
   * @param saveImage the name under which the color-corrected image is saved.
   */
  @Override
  public void applyColorCorrection(String imageName, String saveImage) {
    ImageData inputImage = this.getImage(imageName);
    int[][][] newRGB = imageOpsV2.colorCorrection(inputImage);
    this.addImage(saveImage, newRGB);
  }

  /**
   * Applies a preview split of an image for a specified operation (e.g., compression or color
   * correction) and saves the result. Delegates the preview split operation to the
   * ImageOperationsV2 class.
   *
   * @param operationName the name of the operation to preview.
   * @param imageName     the name of the image to split preview.
   * @param saveImage     the name under which the previewed image is saved.
   * @param percentage    the percentage to apply for the split preview.
   * @param params        additional parameters required for the operation.
   */
  @Override
  public void applySplitPreview(String operationName, String imageName, String saveImage,
      double percentage, int[] params) {
    ImageData inputImage = this.getImage(imageName);
    int[][][] newRGB = imageOpsV2.splitPreview(operationName, inputImage, percentage, params);
    this.addImage(saveImage, newRGB);
  }

}
