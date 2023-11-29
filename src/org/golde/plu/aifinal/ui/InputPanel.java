package org.golde.plu.aifinal.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.golde.plu.aifinal.homedepot.HomedepotAPIWrapper;
import org.golde.plu.aifinal.homedepot.Product;
import org.golde.plu.aifinal.homedepot.ProductLookupException;

/**
 * Product input panel.
 */
public class InputPanel extends JPanel {

    private ImagePanel imageLabel;
    private JTextArea descriptionTextArea;
    private JTextField productIdInput;
    private JButton searchButton;

    private boolean foundSomething;

    public InputPanel(String title) throws IOException {
        super();
        setLayout(new BorderLayout());

        productIdInput = new JTextField();
        searchButton = new JButton("Search");

        searchButton.addActionListener(e -> {
            searchForProduct();
            searchButton.setEnabled(false);
        });

        imageLabel = new ImagePanel();

        descriptionTextArea = new JTextArea();
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);
        descriptionTextArea.setEditable(false);

        // Panel for the image and description
        JPanel imageDescriptionPanel = new JPanel(new BorderLayout());
        imageDescriptionPanel.add(new JScrollPane(descriptionTextArea), BorderLayout.CENTER);
        imageDescriptionPanel.add(imageLabel, BorderLayout.EAST);

        // Panel for the label and the input/search button
        JPanel topPanel = new JPanel(new BorderLayout());

        JLabel productLabel = new JLabel(title);
        productLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the label text

        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.add(productLabel, BorderLayout.CENTER);

        topPanel.add(labelPanel, BorderLayout.NORTH);
        topPanel.add(productIdInput, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        // Add components to the custom panel
        this.add(topPanel, BorderLayout.NORTH);
        this.add(imageDescriptionPanel, BorderLayout.CENTER);
    }

    public void prefillForTesting(String productId) {
        productIdInput.setText(productId);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 400); // Set initial preferred size
    }

    private void searchForProduct() {
        foundSomething = false;
        final String productId = productIdInput.getText();

        try {
            Product product = HomedepotAPIWrapper.lookup(productId);
            descriptionTextArea.setText(product.getDescription());
            imageLabel.setURL(product.getImageURL());
            foundSomething = true;
            searchButton.setEnabled(true);
            System.out.println("Found product: " + product);
            repaint();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch(ProductLookupException e) {
            e.printStackTrace();
        }
    }

    public boolean hasFoundProduct() {
        return foundSomething;
    }

    public String getProductDesc() {
        return descriptionTextArea.getText();
    }
}
