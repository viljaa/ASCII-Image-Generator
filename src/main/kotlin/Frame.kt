import java.awt.Color
import java.awt.image.BufferedImage

class Frame(
    private var sourceImg: BufferedImage,
    private val maxHeight: Int,
) {
    private val height: Int = sourceImg.height
    private val width: Int = sourceImg.width
    var asciiArray = Array(height){Array(width){""} }

    fun createFrame(){
        // Check if image resolution exceeds the allowed limit
        if(height > maxHeight){
            // Scale image to match the configured max height
            sourceImg = scaleDown(sourceImg, maxHeight)
            println("Scale complete. Width:${sourceImg.width}px | Height:${sourceImg.height}px")
            // Reinitialize asciiArray to match the new resolution
            asciiArray = Array(sourceImg.height){Array(sourceImg.width){""} }
        }
        // Convert image into an ASCII frame
        toASCII(sourceImg, asciiArray)
    }

    // Function for converting source image to ASCII frame
    private fun toASCII(image:BufferedImage, asciiArray: Array<Array<String>>){
        // Looping through pixels to get color values for each one
        for (y in 0 until image.height){
            for (x in 0 until image.width){
                val color = Color(image.getRGB(x, y))
                // Get luminance for the pixel by using rgb values of the pixel
                val luminance = calculateLuminance(color.red, color.green, color.blue)
                // Compare luminance to greyscale, add ASCII character to represent that pixel in the array
                asciiArray[y][x] = defGrayscale(luminance)
            }
        }
    }

    // Function for scaling down the picture to have max height of pixels
    private fun scaleDown(image: BufferedImage, maxHeight: Int): BufferedImage{
        // Calculate the scale ratio to match scaled width with set height
        val scaleRatio: Float = image.height.toFloat()/maxHeight.toFloat()
        println("Scale ratio:$scaleRatio")

        // Calculate the new width with scale ratio
        val scaledWidth = (image.width/scaleRatio).toInt()

        // Scale the image and return the resized image as buffered image
        val scaledImage = image.getScaledInstance(scaledWidth, maxHeight, BufferedImage.SCALE_SMOOTH)
        val outputImage = BufferedImage(scaledWidth, maxHeight, BufferedImage.TYPE_INT_RGB)
        outputImage.graphics.drawImage(scaledImage,0,0,null)
        return  outputImage
    }

    // Function for calculating luminance of a pixel with formula 0.3*R + 0.59*G + 0.11*B
    private fun calculateLuminance(red: Int, green: Int, blue: Int): Int{
        // Get luminance of an pixel
        return ((0.3*red) + (0.59*green) + (0.11*blue)).toInt()
    }

    // Function for determining ASCII counterpart of the luminance value of a pixel
    private fun defGrayscale(luminance: Int): String{
        return when (luminance) {
            in 0..24 -> " ."
            in 25..49 -> " ,"
            in 50..74 -> " ,"
            in 75..99 -> " ;"
            in 100..124 -> " !"
            in 125..149 -> " v"
            in 150..174 -> " l"
            in 175..199 -> " L"
            in 200..224 -> " F"
            in 225..255 -> " $"
            else -> ""
        }
    }
}