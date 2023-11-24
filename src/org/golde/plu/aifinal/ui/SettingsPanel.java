package org.golde.plu.aifinal.ui;

import com.google.gson.JsonObject;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import org.golde.plu.aifinal.ai.AISettings.Setting;
import org.golde.plu.aifinal.ai.EnumResponseType;
import org.golde.plu.aifinal.ai.ResponseGenerator;
import org.golde.plu.aifinal.ai.ResponseGenerator.AsyncCallback;

public class SettingsPanel extends JPanel {

    private EnumResponseType RESPONSE_GENERATOR = EnumResponseType.GPT3_5;
    private final TestUI parent;

    public SettingsPanel(TestUI parent) {
        this.parent = parent;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        resetPanel();
    }



    private void resetPanel() {
        this.removeAll();

        this.add(Box.createVerticalGlue());

        //add title
        JLabel title = new JLabel("Settings");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        this.add(title);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        //add picker for AI
        this.add(getAISettingsRow());

        //populate all the settings based on the AI
        generateRowsBasedOnAI();

        //Add reset and start button
        this.add(getResetStartRow());

        this.add(Box.createVerticalGlue());

        repaint();
        revalidate();
    }

    private JPanel getResetStartRow() {
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            System.out.println("Reset button pressed");
            resetSettingsToDefault();
        });

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            System.out.println("Start button pressed");
            startTheAIAndDoSomeMagic();
        });

        JPanel panel = new JPanel();
        panel.add(resetButton);
        panel.add(startButton);

        return panel;
    }

    private void startTheAIAndDoSomeMagic() {

        if(!parent.getProduct1Panel().hasFoundProduct()) {
            parent.getOutputPanel().println("Please enter a valid product ID for product 1", Color.RED);
            return;
        }

        if(!parent.getProduct2Panel().hasFoundProduct()) {
            parent.getOutputPanel().println("Please enter a valid product ID for product 2", Color.RED);
            return;
        }


        System.out.println("Started AI");
        parent.getOutputPanel().println("Generating response using the following settings", Color.BLACK.darker());
        parent.getOutputPanel().println(RESPONSE_GENERATOR.getInstance().getSettings().toFormattedString(), Color.MAGENTA.darker());
        parent.getOutputPanel().println("--------------------------------------------------", Color.BLACK);

        final String product1Desc = parent.getProduct1Panel().getProductDesc();
        final String product2Desc = parent.getProduct2Panel().getProductDesc();

        RESPONSE_GENERATOR.getInstance().generateResponse(product1Desc, product2Desc, new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //System.out.println(result);
                parent.getOutputPanel().println(result, Color.GREEN.darker());
            }

            @Override
            public void onFailure(String error) {
                //System.out.println(error);
                parent.getOutputPanel().println(error, Color.RED);
            }
        });

    }

    private void resetSettingsToDefault() {
        RESPONSE_GENERATOR.getInstance().getSettings().resetToDefault();
        resetPanel();
    }

    private JPanel getAISettingsRow() {
        JComboBox<String> comboBox = new JComboBox<String>();

        for(EnumResponseType ert : EnumResponseType.values()) {
            comboBox.addItem(ert.getNiceName());
        }

        comboBox.setSelectedItem(RESPONSE_GENERATOR.getNiceName());
        comboBox.addActionListener(e -> {
            EnumResponseType type = EnumResponseType.fromNiceName((String)comboBox.getSelectedItem());
            setAIResponseGenerator(type);
            System.out.println("Set selected type: " + type.getNiceName());
            parent.getOutputPanel().println("Set AI to: " + type.getNiceName(), Color.YELLOW.darker());
        });


        return createRow("AI Settings: ", comboBox);
    }

    Map<String, JLabel> LABELS = new HashMap<>();

    private void generateRowsBasedOnAI() {
        for(Setting setting : RESPONSE_GENERATOR.getInstance().getSettings().getAllSettings()) {

            String name = setting.getName();
            JComponent component = new JLabel("Not implemented: " + setting.getType().getName());;

            if(setting.getType() == String.class) {
                component = new JTextField();
                ((JTextField)component).setText((String)setting.getValue());

                JComponent finalComponent = component;
                ((JTextField)component).addFocusListener(new java.awt.event.FocusAdapter() {
                    public void focusLost(java.awt.event.FocusEvent evt) {
                        setting.setValue(((JTextField) finalComponent).getText());
                        System.out.println("Set value to: " + setting.getValue());
                        updateSettingLabel(setting);
                    }
                });
            }
            else if(setting.getType() == Integer.class) {

                final int min = (int)setting.getMin();
                final int max = (int)setting.getMax();
                final int value = (int)setting.getValue();

                System.out.println("Min: " + min + " Max: " + max + " Value: " + value);

                if(min != Integer.MIN_VALUE && max != Integer.MAX_VALUE) {

                    component = new JSlider(min, max, value);

                    ((JSlider) component).setPaintTicks(true);
                    ((JSlider) component).setPaintLabels(true);
                    ((JSlider) component).setSnapToTicks(true);
                    ((JSlider) component).setMajorTickSpacing((max - min) / 10);
                    ((JSlider) component).setMinorTickSpacing((max - min) / 100);

                    JComponent finalComponent = component;
                    ((JSlider) component).addChangeListener(e -> {
                        int sliderValue = ((JSlider) finalComponent).getValue();
                        setting.setValue(sliderValue);
                        System.out.println("Set value to: " + sliderValue);
                        updateSettingLabel(setting);
                    });

                } else {
                    component = new JTextField();
                    ((JTextField)component).setText(String.valueOf(value));

                    JComponent finalComponent = component;
                    ((JTextField)component).addFocusListener(new java.awt.event.FocusAdapter() {
                        public void focusLost(java.awt.event.FocusEvent evt) {
                            setting.setValue(Integer.parseInt(((JTextField) finalComponent).getText()));
                            System.out.println("Set value to: " + setting.getValue());
                            updateSettingLabel(setting);
                        }
                    });
                }


            }
            else if(setting.getType() == Double.class) {

                final double min = (double)setting.getMin();
                final double max = (double)setting.getMax();
                final double value = (double)setting.getValue();
                final double increment = (double)setting.getIncrementAmount();

                final int amountOfSteps = (int)((max - min) / increment);
                System.out.println("Min: " + min + " Max: " + max + " Value: " + value + " Increment: " + increment + " Amount of steps: " + amountOfSteps);

                Format f = new DecimalFormat("0.0");
                Hashtable<Integer, JComponent> labels = new Hashtable<Integer, JComponent>();

                final int defaultValueInSteps = (int)((value - min) / increment);

                final int step = amountOfSteps / 10;

                for(int i = 0; i <= amountOfSteps; i+=step) {
                    JLabel label = new JLabel(f.format(i*increment));
                    label.setFont(label.getFont().deriveFont(Font.PLAIN));
                    labels.put(i,label);
                }

                component = new JSlider(0, amountOfSteps, defaultValueInSteps);

                ((JSlider)component).setPaintTicks(true);
                ((JSlider)component).setPaintLabels(true);
                //((JSlider)component).setSnapToTicks(true);
                ((JSlider)component).setMajorTickSpacing(amountOfSteps / 10);
                ((JSlider)component).setMinorTickSpacing(amountOfSteps / 100);
                ((JSlider)component).setLabelTable(labels);

                JComponent finalComponent = component;
                ((JSlider)component).addChangeListener(e -> {
                    int sliderValue = ((JSlider) finalComponent).getValue();
                    double newValue = min + (sliderValue * increment);
                    setting.setValue(newValue);
                    System.out.println("Set value to: " + newValue);
                    updateSettingLabel(setting);
                });

            }
            else if(setting.getType() == Boolean.class) {
                component = new JCheckBox();
                ((JCheckBox)component).setSelected((Boolean)setting.getValue());

                JComponent finalComponent = component;
                ((JCheckBox)component).addActionListener(e -> {
                    setting.setValue(((JCheckBox) finalComponent).isSelected());
                    updateSettingLabel(setting);
                });
            }

            this.add(createRow(name, component));

            updateSettingLabels();
        }
    }

    private void updateSettingLabels() {
        for(Setting setting : RESPONSE_GENERATOR.getInstance().getSettings().getAllSettings()) {
            updateSettingLabel(setting);
        }
    }

    private void updateSettingLabel(Setting setting) {
        JLabel label = LABELS.get(setting.getName());
        if(label != null) {

            if(setting.getType() == Double.class) {
                label.setText(setting.getName() + " (" + new DecimalFormat("0.0").format(setting.getValue()) + "): ");
            }
            else if(setting.getType() == Integer.class) {
                label.setText(setting.getName() + " (" + setting.getValue() + "): ");
            }
            else {
                label.setText(setting.getName() + ": ");
            }
        }
    }

    private void setAIResponseGenerator(EnumResponseType type) {
        RESPONSE_GENERATOR.getInstance().shutdown(new AsyncCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                System.out.println("Shut down old AI");
            }

            @Override
            public void onFailure(String error) {
                System.out.println("Failed to shut down old AI: " + error);
            }
        });
        RESPONSE_GENERATOR = type;
        resetPanel();
    }

    public EnumResponseType getAIResponseGenerator() {
        return RESPONSE_GENERATOR;
    }

    private JPanel createRow(String name, JComponent component) {
        JLabel label = new JLabel(name);

        LABELS.put(name, label);

        // Create a sub-panel for each row
        JPanel rowPanel = new JPanel(new BorderLayout());
        rowPanel.add(label, BorderLayout.WEST);
        rowPanel.add(component, BorderLayout.CENTER);

        return rowPanel;
    }

}
