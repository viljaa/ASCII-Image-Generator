class AsciiAnimation (
    totalFrames: Int,
) {
    var frameArray: Array<Array<Array<String>>> = Array(totalFrames){ arrayOf(arrayOf()) }

    // Function for animating the frame array
    fun animate(){
        while (true) {
            // Loop through animation until program is stopped
            frameArray.forEach { frame ->
                // Print individual frame
                frame.forEach { row ->
                    row.forEach { char ->
                        print(char)
                    }
                    println()  // Change line
                }
                // Clear the terminal to render next frame
                Thread.sleep(32)
                ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor()
            }
        }
    }
}