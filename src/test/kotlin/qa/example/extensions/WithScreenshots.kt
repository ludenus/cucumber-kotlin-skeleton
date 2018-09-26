package qa.example.extensions

import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebElement
import io.qameta.allure.Attachment
import io.qameta.allure.Step
import ru.yandex.qatools.ashot.AShot
import ru.yandex.qatools.ashot.Screenshot
import ru.yandex.qatools.ashot.shooting.ShootingStrategies
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO


interface WithScreenshots : WithLog, WithWebDriver {

    fun pageScreenshot(name: String) {
        val tempFile = (driver as TakesScreenshot).getScreenshotAs(OutputType.FILE)
        val dir = "build/screenshots"
        File(dir).apply { mkdirs() }
        val fullpath = "$dir/${System.currentTimeMillis()}_$name.png"
        tempFile.renameTo(File((fullpath)))
    }

    fun allureScreenshot(): ByteArray {
        return (driver as TakesScreenshot).getScreenshotAs(OutputType.BYTES)
    }

    @Attachment(value = "Attachment {0}", type = "image/png")
    fun allureAttach(title: String, bytes: ByteArray): ByteArray {
        return bytes
    }

    @Attachment(value = "Attachment for failed tests {0}", type = "image/png")
    fun allureAttachmentForFailed(title: String, bytes: ByteArray): ByteArray {
        return bytes
    }

    @Step("screenshot with scale factor {1}")
    fun WebElement.screenshot(scalingFactor: Float = config.getDouble("scalingFactor").toFloat()): Screenshot {
        Thread.sleep(2000)
        return AShot()
                .shootingStrategy(ShootingStrategies.scaling(scalingFactor))
                .takeScreenshot(driver, this)
    }

    @Step("Save image to {1}")
    fun BufferedImage.savePng(filename: String) {
        val outputfile = File(filename)
        ImageIO.write(this, "png", outputfile)
    }

    fun BufferedImage.toBytes(): ByteArray {
        val baos = ByteArrayOutputStream()
        ImageIO.write(this, "png", baos)
        return baos.toByteArray()
    }

    fun loadPng(filename: String): BufferedImage {
        return ImageIO.read(File(filename))
    }

    @Step("Save image to {1}")
    fun Screenshot.savePng(filename: String) {
        this.image.savePng(filename)
    }

}