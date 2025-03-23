package com.nedvedd.animace;

import javax.swing.*;
import java.awt.*;

/**
 * Spousteci trida programu s animaci pohybu.
 * Po spusteni ma uzivatel moznost vyberu mezi postavou a prasetem. Po spusteni animace se da dany model do pohybu.
 *
 * @author Dominik Nedved
 * @version 21.03.2024
 */
public class Main {

    /** Atribut vykreslovaci komponenty DrawingPanel */
    private static DrawingPanel drawingPanel;

    /** Atribut tlacitka pro spusteni/zastaveni animace */
    private static JButton startStopAnimationBTN;

    /** Atribut Timeru pro animaci */
    private static Timer timer;

    /** Atribut pro vyjadreni stavu animace */
    private static boolean isAnimating = false;

    /**
     * Sposteci metoda programu
     *
     * @param args parametry prikazove radky
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frame.setTitle("APR – Animace (nedvedd, IT2)");
        frame.setMinimumSize(new Dimension(480, 480));

        drawingPanel = new DrawingPanel();
        frame.add(drawingPanel, BorderLayout.CENTER);

        JPanel toolbarTop = new JPanel();
        startStopAnimationBTN = new JButton("Spustit animaci");
        toolbarTop.add(startStopAnimationBTN, BorderLayout.CENTER);

        JPanel toolbarBottom = new JPanel();
        JButton stickManBTN = new JButton("Panáček");
        JButton pigBTN = new JButton("Čuník");
        toolbarBottom.add(stickManBTN, BorderLayout.WEST);
        toolbarBottom.add(pigBTN, BorderLayout.CENTER);

        frame.add(toolbarTop, BorderLayout.NORTH);
        frame.add(toolbarBottom, BorderLayout.SOUTH);
        frame.pack(); // Udela resize okna dle komponent
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Vycentrovani okna na obrazovce
        frame.setVisible(true);

        timer = new Timer(16, e -> drawingPanel.animate()); // 16 = min int hodnota delaye pro 60+ FPS
        startStopAnimationBTN.addActionListener(e -> onStartStopAnimationBTNAction());
        stickManBTN.addActionListener(e -> drawingPanel.switchDrawingModelTo(DrawingModel.STICK_MAN));
        pigBTN.addActionListener(e -> drawingPanel.switchDrawingModelTo(DrawingModel.PIG));
    }

    /**
     * Akce pro stisknuti tlacitku start/stop animace
     */
    private static void onStartStopAnimationBTNAction() {
        if (isAnimating) {
            timer.stop();
            startStopAnimationBTN.setText("Spustit animaci");
        } else {
            timer.start();
            startStopAnimationBTN.setText("Zastavit animaci");
        }
        isAnimating = !isAnimating;
    }
}
