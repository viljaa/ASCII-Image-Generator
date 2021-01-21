import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO




fun main() {
    // Variables
    val path = getPath()
    val maxHeight = setMaxHeight() // Max height of the source image as px

    // Check for filetype, gif activates animation feature and picture formats will be printed out as static images
    if( File(path).extension == "gif"){
        val reader = ImageIO.getImageReadersByFormatName("gif").next()
        val stream = ImageIO.createImageInputStream(File(path))
        reader.setInput(stream, false)

        // Get number of individual frames in the gif
        val totalFrames: Int = reader.getNumImages(true)
        println("Total frames: $totalFrames")

        // Create instance of AsciiAnimation to store individual frames in
        val asciiAnimation = AsciiAnimation(totalFrames)

        // Loop through gif frames and create matching ASCII frames
        for (i in 0 until totalFrames) {
            val image: BufferedImage = reader.read(i)
            // Create instance of Frame to create ASCII frame
            val frame = Frame(image, maxHeight)
            frame.createFrame()
            // Store created frame
            asciiAnimation.frameArray[i] = frame.asciiArray
        }
        // Play the animation once ready
        asciiAnimation.animate()

    } else {
        val image: BufferedImage = initBufferedImage(path)  // Source image
        println("Width:${image.width}px | Height:${image.height}px")

        // Create instance of Frame
        val frame = Frame(image, maxHeight)

        // Create ASCII frame from the image
        frame.createFrame()
        // Print created ASCII frame
        asciiPrint(frame.asciiArray)
    }
}

// Function for initializing image variable
@Throws(IOException::class)
fun initBufferedImage(path: String): BufferedImage{
        // Get image file from the local directory
        val file = File(path)
        // Create buffered image to be able to process the image. Throws exception if filepath is invalid.
        return ImageIO.read(file) ?: throw IOException("File path is invalid")
}

// Function for taking path from user input
fun getPath(): String{
    print("Enter path:")
    return readLine().toString()
}

// Setter for max height
fun setMaxHeight(): Int{
    print("Set max height (px) of the rendered image/animation:")
    return readLine()!!.toInt()
}

// Function for printing ascii version of the source picture
fun asciiPrint(matrix: Array<Array<String>>){
    // Loop through matrix to render the image
    matrix.forEach {
        it.forEach { char ->
            print(char)
        }
        println() // Change line to start next row of the matrix
    }
}