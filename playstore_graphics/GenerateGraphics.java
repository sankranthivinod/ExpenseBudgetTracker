import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class GenerateGraphics {

    public static void main(String[] args) throws Exception {
        File dir = new File("C:/Users/vinod/AndroidStudioProjects/ExpenseBudgetTracker/playstore_graphics");
        if (!dir.exists()) dir.mkdirs();

        generate512Icon(dir);
        generate1024Banner(dir);
    }

    private static void generate512Icon(File dir) throws Exception {
        int size = 512;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Background Gradient
        GradientPaint bgGradient = new GradientPaint(0f, 0f, new Color(0x0F, 0x4C, 0x81), size, size, new Color(0x1E, 0x88, 0xE5));
        g.setPaint(bgGradient);
        g.fill(new RoundRectangle2D.Float(0f, 0f, size, size, 100f, 100f));

        // White Wallet Container
        g.setColor(Color.WHITE);
        g.fill(new RoundRectangle2D.Float(106f, 136f, 300f, 240f, 32f, 32f));

        // Wallet Flap Accent
        g.setColor(new Color(0xE3, 0xF2, 0xFD));
        g.fill(new RoundRectangle2D.Float(106f, 136f, 300f, 60f, 32f, 32f));

        // Wallet Lock / Clasp
        g.setColor(new Color(0xFF, 0xC1, 0x07));
        g.fill(new RoundRectangle2D.Float(336f, 226f, 70f, 60f, 16f, 16f));
        g.setColor(new Color(0xFF, 0xA0, 0x00));
        g.fillOval(361, 246, 20, 20);

        // Multi-color Bar Chart Columns
        // Bar 1 (Cyan)
        g.setColor(new Color(0x00, 0xAC, 0xC1));
        g.fill(new RoundRectangle2D.Float(146f, 286f, 36f, 60f, 12f, 12f));

        // Bar 2 (Green)
        g.setColor(new Color(0x4C, 0xAF, 0x50));
        g.fill(new RoundRectangle2D.Float(206f, 236f, 36f, 110f, 12f, 12f));

        // Bar 3 (Gold)
        g.setColor(new Color(0xFF, 0xB3, 0x00));
        g.fill(new RoundRectangle2D.Float(266f, 196f, 36f, 150f, 12f, 12f));

        // Trendline Arrow
        g.setColor(new Color(0xFF, 0xC1, 0x07));
        g.setStroke(new BasicStroke(12f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(140, 270, 200, 220);
        g.drawLine(200, 220, 270, 180);
        g.drawLine(270, 180, 330, 140);

        g.dispose();

        File outputFile = new File(dir, "playstore_icon_512x512.png");
        ImageIO.write(image, "PNG", outputFile);
        System.out.println("SUCCESS: Generated " + outputFile.getAbsolutePath());
    }

    private static void generate1024Banner(File dir) throws Exception {
        int width = 1024;
        int height = 500;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Deep Navy/Blue Background
        GradientPaint bgGradient = new GradientPaint(0f, 0f, new Color(0x0B, 0x25, 0x45), width, height, new Color(0x1D, 0x4E, 0xD8));
        g.setPaint(bgGradient);
        g.fillRect(0, 0, width, height);

        // Accent Wave / Circle
        g.setColor(new Color(255, 255, 255, 15));
        g.fillOval(550, -100, 600, 600);
        g.setColor(new Color(255, 255, 255, 10));
        g.fillOval(650, 100, 500, 500);

        // Left Side: Typography
        // App Title
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 46));
        g.drawString("Expense & Budget Tracker", 60, 140);

        // Subtitle
        g.setColor(new Color(0xFF, 0xC1, 0x07)); // Gold Accent
        g.setFont(new Font("SansSerif", Font.BOLD, 26));
        g.drawString("Smart, Simple & Offline Financial Manager", 60, 195);

        // Feature Badges
        String[] badges = new String[]{
            "Interactive Bar Charts",
            "11 Custom Categories",
            "100% Private & Offline"
        };
        g.setFont(new Font("SansSerif", Font.PLAIN, 20));
        int badgeY = 260;

        for (String badge : badges) {
            g.setColor(new Color(0x1E, 0x3A, 0x8A, 180));
            g.fill(new RoundRectangle2D.Float(60f, badgeY - 24f, 380f, 40f, 16f, 16f));

            g.setColor(new Color(0xE0, 0xF2, 0xFE));
            g.drawString(badge, 80, badgeY);
            badgeY += 56;
        }

        // Right Side: Graphic Card Illustration
        g.setColor(new Color(0x1E, 0x29, 0x3B, 220));
        g.fill(new RoundRectangle2D.Float(580f, 80f, 380f, 340f, 24f, 24f));

        // Card Header
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("Monthly Overview", 610, 125);

        g.setColor(new Color(0x4A, 0xDE, 0x80));
        g.setFont(new Font("SansSerif", Font.BOLD, 32));
        g.drawString("$1,850.00", 610, 170);

        // Bar Chart Columns on Card
        int[] barX = new int[]{620, 670, 720, 770, 820, 870, 920};
        int[] barHeights = new int[]{90, 140, 110, 180, 130, 200, 160};
        Color[] barColors = new Color[]{
            new Color(0x9C, 0x27, 0xB0), // Entertainment
            new Color(0xE5, 0x39, 0x35), // Insurance
            new Color(0x1E, 0x88, 0xE5), // Rent
            new Color(0x4C, 0xAF, 0x50), // Groceries
            new Color(0xFF, 0x98, 0x00), // Outside Food
            new Color(0x00, 0xAC, 0xC1), // Utilities
            new Color(0xFF, 0xB3, 0x00)  // Subscriptions
        };

        for (int i = 0; i < barX.length; i++) {
            g.setColor(barColors[i]);
            int h = barHeights[i];
            int y = 380 - h;
            g.fill(new RoundRectangle2D.Float(barX[i], y, 30f, h, 10f, 10f));
        }

        g.dispose();

        File outputFile = new File(dir, "feature_graphic_1024x500.png");
        ImageIO.write(image, "PNG", outputFile);
        System.out.println("SUCCESS: Generated " + outputFile.getAbsolutePath());
    }
}
