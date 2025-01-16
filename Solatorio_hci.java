/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author GIGABYTE
 */


package solatorio_hci;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Solatorio_hci extends JFrame {

    private JSlider valveSlider;
    private JLabel valveLabel;
    private JButton turnOffButton;
    private JButton turnOnButton;
    private JButton customValueButton;
    private JButton addValveButton;
    private JButton saveButton; 
    private JButton deleteValveButton; 
    private JButton resetAllButton; 
    private JButton deleteAllValvesButton; // New button
    private JPanel valvePanel;
    private ArrayList<JPanel> valveBoxes;
    private ArrayList<Integer> valveValues; 
    private JPopupMenu popupMenu;

    public Solatorio_hci() {
        setTitle("Valve Control System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(9, 1, 10, 10));
        controlPanel.setBackground(Color.DARK_GRAY);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        valveLabel = new JLabel("Valve Position: 0", JLabel.CENTER);
        valveLabel.setFont(new Font("Arial", Font.BOLD, 18));
        valveLabel.setForeground(Color.WHITE);
        valveLabel.setBorder(BorderFactory.createTitledBorder("Current Status"));

        valveSlider = new JSlider(0, 1000, 0);
        valveSlider.setMajorTickSpacing(200);
        valveSlider.setMinorTickSpacing(50);
        valveSlider.setPaintTicks(true);
        valveSlider.setPaintLabels(true);
        valveSlider.setBackground(Color.DARK_GRAY);
        valveSlider.setForeground(Color.WHITE);
        valveSlider.addChangeListener(e -> valveLabel.setText("Valve Position: " + valveSlider.getValue()));

        addValveButton = new JButton("Add Valve");
        styleButton(addValveButton);
        addValveButton.addActionListener(e -> addNewValve());

        turnOffButton = new JButton("Turn Off");
        styleButton(turnOffButton);
        turnOnButton = new JButton("Turn Fully On");
        styleButton(turnOnButton);
        customValueButton = new JButton("Custom Value");
        styleButton(customValueButton);
        saveButton = new JButton("Save Value");
        styleButton(saveButton);
        deleteValveButton = new JButton("Delete Valve");
        styleButton(deleteValveButton);
        resetAllButton = new JButton("Reset All Valves");
        styleButton(resetAllButton);
        deleteAllValvesButton = new JButton("Delete All Valves"); // New button
        styleButton(deleteAllValvesButton);

        turnOffButton.addActionListener(e -> {
            valveSlider.setValue(0);
            valveLabel.setText("Valve Position: 0");
        });

        turnOnButton.addActionListener(e -> {
            valveSlider.setValue(1000);
            valveLabel.setText("Valve Position: 1000");
        });

        customValueButton.addActionListener(e -> openNumericInput());
        saveButton.addActionListener(e -> saveCurrentValveValue());
        deleteValveButton.addActionListener(e -> deleteLastValve());
        resetAllButton.addActionListener(e -> resetAllValves());
        deleteAllValvesButton.addActionListener(e -> deleteAllValves()); // New action

        controlPanel.add(addValveButton);
        controlPanel.add(turnOffButton);
        controlPanel.add(turnOnButton);
        controlPanel.add(customValueButton);
        controlPanel.add(saveButton);
        controlPanel.add(deleteValveButton);
        controlPanel.add(resetAllButton);
        controlPanel.add(deleteAllValvesButton); // Add new button to panel
        controlPanel.add(valveLabel);
        controlPanel.add(valveSlider);

        valvePanel = new JPanel();
        valvePanel.setLayout(new FlowLayout());
        valvePanel.setBackground(Color.BLACK);
        valveBoxes = new ArrayList<>();
        valveValues = new ArrayList<>(); 

        popupMenu = new JPopupMenu();
        JMenuItem offItem = new JMenuItem("Off"); // New menu item
        JMenuItem slightlyOpenItem = new JMenuItem("Slightly Open");
        JMenuItem mostlyOpenItem = new JMenuItem("Mostly Open");
        JMenuItem allTheWayOnItem = new JMenuItem("All the Way On"); // New menu item
        JMenuItem numericSettingItem = new JMenuItem("Numeric Setting");

        popupMenu.add(offItem);
        popupMenu.add(slightlyOpenItem);
        popupMenu.add(mostlyOpenItem);
        popupMenu.add(allTheWayOnItem);
        popupMenu.add(numericSettingItem);

        offItem.addActionListener(e -> setValvePosition(0));
        slightlyOpenItem.addActionListener(e -> setValvePosition(200));
        mostlyOpenItem.addActionListener(e -> setValvePosition(800));
        allTheWayOnItem.addActionListener(e -> setValvePosition(1000));
        numericSettingItem.addActionListener(e -> openNumericInput());

        add(controlPanel, BorderLayout.WEST);
        add(valvePanel, BorderLayout.CENTER);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(70, 130, 180));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEtchedBorder());
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(100, 149, 237));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
    }

    private void addNewValve() {
        JPanel valveBox = new JPanel();
        valveBox.setBorder(BorderFactory.createTitledBorder("Valve " + (valveBoxes.size() + 1)));
        valveBox.setPreferredSize(new Dimension(100, 100));
        valveBox.setBackground(Color.LIGHT_GRAY);
        valveBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        valveBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleValveMouseAction(e, valveBox);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                valveBox.setBackground(new Color(220, 220, 220)); // Lighter on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                valveBox.setBackground(Color.LIGHT_GRAY); // Reset color
            }
        });
        valveBoxes.add(valveBox);
        valveValues.add(0); 
        valvePanel.add(valveBox);
        valvePanel.revalidate();
        valvePanel.repaint();
    }

    private void handleValveMouseAction(MouseEvent e, JPanel valveBox) {
        int index = valveBoxes.indexOf(valveBox);
        if (index != -1) {
            if (e.getButton() == MouseEvent.BUTTON1) { // Left button
                valveSlider.setValue(valveValues.get(index)); // Set slider to valve's value
                valveLabel.setText("Valve Position: " + valveValues.get(index));
            } else if (e.getButton() == MouseEvent.BUTTON3) { // Right button
                popupMenu.show(valveBox, e.getX(), e.getY()); // Show popup menu
            }
        }
    }

    private void setValvePosition(int value) {
        int index = valveBoxes.size() - 1;
        if (index >= 0) {
            valveValues.set(index, value); 
            valveSlider.setValue(value);
            valveLabel.setText("Valve Position: " + value);
        }
    }

    private void saveCurrentValveValue() {
        int index = valveBoxes.size() - 1; // Get the last added valve
        if (index >= 0) {
            valveValues.set(index, valveSlider.getValue()); 
            JOptionPane.showMessageDialog(this, "Valve " + (index + 1) + " saved at position: " + valveSlider.getValue());
        }
    }

    private void deleteLastValve() {
        if (!valveBoxes.isEmpty()) {
            int index = valveBoxes.size() - 1; 
            valvePanel.remove(valveBoxes.remove(index)); 
            valveValues.remove(index); 
            valveSlider.setValue(0); 
            valveLabel.setText("Valve Position: 0");
            valvePanel.revalidate();
            valvePanel.repaint();
            JOptionPane.showMessageDialog(this, "Valve " + (index + 1) + " deleted.");
        } else {
            JOptionPane.showMessageDialog(this, "No valves to delete.");
        }
    }

    private void deleteAllValves() {
        valveBoxes.clear();
        valveValues.clear();
        valvePanel.removeAll();
        valveSlider.setValue(0);
        valveLabel.setText("Valve Position: 0");
        valvePanel.revalidate();
        valvePanel.repaint();
        JOptionPane.showMessageDialog(this, "All valves deleted.");
    }

    private void resetAllValves() {
        for (int i = 0; i < valveValues.size(); i++) {
            valveValues.set(i, 0); 
        }
        valveSlider.setValue(0);
        valveLabel.setText("Valve Position: 0");
        JOptionPane.showMessageDialog(this, "All valves have been reset.");
    }

    private void openNumericInput() {
        String inputValue = JOptionPane.showInputDialog(null, "Enter a value between 0 and 1000:");
        try {
            int customValue = Integer.parseInt(inputValue);
            if (customValue >= 0 && customValue <= 1000) {
                int index = valveBoxes.size() - 1; 
                if (index >= 0) {
                    valveValues.set(index, customValue); 
                    valveSlider.setValue(customValue);
                    valveLabel.setText("Valve Position: " + customValue);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a value between 0 and 1000.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Solatorio_hci frame = new Solatorio_hci();
            frame.setVisible(true);
        });
    }
}












