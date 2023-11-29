package org.golde.plu.aifinal.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import lombok.Getter;

/**
 * This class is the main UI for the project. It creates the JFrame and adds all the sub-panels to it.
 */
public class ProjectUI {

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
        mainPanel.add(outputPanel);

        // Add the main panel to the frame
        frame.add(mainPanel);

        frame.setVisible(true);

        outputPanel.println("Welcome to my AI Final Project!");
        outputPanel.println("Created by: Eric Golde", Color.BLUE);
        outputPanel.println("--------------------------------------------------", Color.BLACK);
    }

}
