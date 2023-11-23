package org.golde.plu.aifinal.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import lombok.Getter;

public class TestUI {

    @Getter
    private InputPanel product1Panel;
    @Getter
    private InputPanel product2Panel;

    private SettingsPanel settingsPanel;
    @Getter
    private OutputPanel outputPanel;

    public void create() throws IOException {
        JFrame frame = new JFrame("AI Final Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        // Create the main panel using GridLayout (2 rows, 2 columns)
        JPanel mainPanel = new JPanel(new GridLayout(2, 2));

        // Create sub-panels with different background colors
        product1Panel = new InputPanel("Product #1");
        //product1Panel.setBackground(Color.RED);
        product1Panel.prefillForTesting("317061072");

        product2Panel = new InputPanel("Product #2");
        //product2Panel.setBackground(Color.YELLOW);
        product2Panel.prefillForTesting("314600837");

        settingsPanel = new SettingsPanel(this);

        outputPanel = new OutputPanel();

        // Adding sub-panels to the main panel
        mainPanel.add(product1Panel);
        mainPanel.add(product2Panel);
        mainPanel.add(new JScrollPane(settingsPanel));

//        JScrollPane outputScrollPane = new JScrollPane(outputPanel);
//        outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        outputScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//
//        //outputScrollPane.getVerticalScrollBar().addAdjustmentListener(e -> e.getAdjustable().setValue(e.getAdjustable().getMaximum()));
//        mainPanel.add(outputScrollPane);
        mainPanel.add(outputPanel);

        // Add the main panel to the frame
        frame.add(mainPanel);

        frame.setVisible(true);

        outputPanel.println("Welcome to my AI Final Project!");
        outputPanel.println("Created by: Eric Golde", Color.BLUE);
        outputPanel.println("--------------------------------------------------", Color.BLACK);
    }

}
