package org.golde.plu.aifinal.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import lombok.Setter;

public class ImagePanel extends JPanel {

    @Setter private BufferedImage img;

    public ImagePanel() {
        this((BufferedImage) null);
    }

    public ImagePanel(BufferedImage img) {
        this.img = img;
        this.setBackground(Color.WHITE);
    }

    public void setURL(String url)  throws IOException {
        setImg(ImageIO.read(new URL(url)));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(256,256);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null) {
            // Calculate scaling factors for width and height
            double widthScale = (double) getWidth() / img.getWidth();
            double heightScale = (double) getHeight() / img.getHeight();
            double scale = Math.min(widthScale, heightScale);

            // Calculate scaled dimensions
            int scaledWidth = (int) (img.getWidth() * scale);
            int scaledHeight = (int) (img.getHeight() * scale);

            // Calculate position to center the image
            int x = (getWidth() - scaledWidth) / 2;
            int y = (getHeight() - scaledHeight) / 2;

            // Draw the image centered and scaled
            g.drawImage(img, x, y, scaledWidth, scaledHeight, this);
        }
    }
}
