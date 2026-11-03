import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class FormatPhoneScreenshots {

    public static void main(String[] args) throws Exception {
        File graphicsDir = new File("C:/Users/vinod/AndroidStudioProjects/ExpenseBudgetTracker/playstore_graphics");
        File screenshotsDir = new File(graphicsDir, "screenshots");

        formatScreenshot(
            new File(screenshotsDir, "screenshot_1_add_expense.png"),
            new File(screenshotsDir, "phone_screenshot_1_add_expense.png"),
            "Easy Expense Entry",
            "Log daily expenses with custom categories & decimal precision"
        );

        formatScreenshot(
            new File(screenshotsDir, "screenshot_2_analytics.png"),
            new File(screenshotsDir, "phone_screenshot_2_analytics.png"),
            "Interactive Bar Chart Analytics",
            "Filter spending by Weekly, Monthly, Yearly, or Custom Date Ranges"
        );

        formatScreenshot(
            new File(screenshotsDir, "screenshot_3_about_app.png"),
            new File(screenshotsDir, "phone_screenshot_3_about_app.png"),
            "100% Offline & Private Data",
            "Stored securely on your device with Jetpack Compose & Room DB"
        );
    }

    private static void formatScreenshot(File inputFile, File outputFile, String title, String subtitle) throws Exception {
        int canvasWidth = 1080;
        int canvasHeight = 2400;

        BufferedImage canvas = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = canvas.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // Background Gradient
        GradientPaint bgGradient = new GradientPaint(
            0f, 0f, new Color(0x0F, 0x17, 0x2A),
            canvasWidth, canvasHeight, new Color(0x1E, 0x3A, 0x8A)
        );
        g.setPaint(bgGradient);
        g.fillRect(0, 0, canvasWidth, canvasHeight);

        // Header Title
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 52));
        FontMetrics fmTitle = g.getFontMetrics();
        int titleX = (canvasWidth - fmTitle.stringWidth(title)) / 2;
        g.drawString(title, titleX, 150);

        // Header Subtitle
        g.setColor(new Color(0xFF, 0xC1, 0x07)); // Gold Accent
        g.setFont(new Font("SansSerif", Font.BOLD, 28));
        FontMetrics fmSub = g.getFontMetrics();
        int subX = (canvasWidth - fmSub.stringWidth(subtitle)) / 2;
        g.drawString(subtitle, subX, 220);

        // Read source screenshot image
        if (inputFile.exists()) {
            BufferedImage srcImg = ImageIO.read(inputFile);

            int targetPhoneWidth = 920;
            int targetPhoneHeight = 2000;
            int phoneX = (canvasWidth - targetPhoneWidth) / 2;
            int phoneY = 300;

            // Draw Outer Phone Shell
            g.setColor(new Color(0x33, 0x41, 0x55));
            g.fill(new RoundRectangle2D.Float(phoneX - 12, phoneY - 12, targetPhoneWidth + 24, targetPhoneHeight + 24, 48, 48));

            // Clip & Draw Screenshot
            Shape clip = new RoundRectangle2D.Float(phoneX, phoneY, targetPhoneWidth, targetPhoneHeight, 36, 36);
            Graphics2D gClip = (Graphics2D) g.create();
            gClip.setClip(clip);
            gClip.drawImage(srcImg, phoneX, phoneY, targetPhoneWidth, targetPhoneHeight, null);
            gClip.dispose();
        }

        g.dispose();

        ImageIO.write(canvas, "PNG", outputFile);
        System.out.println("SUCCESS: Formatted phone screenshot -> " + outputFile.getAbsolutePath());
    }
}
