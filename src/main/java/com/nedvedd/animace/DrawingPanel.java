package com.nedvedd.animace;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

/**
 * Upravena Trida JPanel slouzici jako vykreslovaci komponenta.
 * Podle atributu vykresluje postavu nebo prase.
 *
 * @author Dominik Nedved
 * @version 21.03.2024
 */
public class DrawingPanel extends JPanel {

    /** Atribut aktualni souradnice X */
    private int currentX = 0;

    /** Atribut rychlosti animace */
    private int animationSpeed = 3;

    /** Atribut odchylky pridavnych animovanych komponent (koncetin) */
    private int animationOffset = 0;

    /** Pomocny atribut pro animaci pridavnych komponent (koncetin) */
    private boolean legForward = true;

    /** Pomocny atribut pro kontorlu smeru pohybu */
    private boolean goingRight = true;

    /** Pomocny atribut pro aktualni vykreslovany model */
    private DrawingModel currentDrawingModel = DrawingModel.STICK_MAN;

    /** Atribut pro aktualni sirku vykreslovaneho modelu */
    private int currentDrawingModelWidth = 40;

    /**
     * Konstruktor.
     * Upravuje se pouze preferovany rozmer JPanelu
     */
    public DrawingPanel() {
        this.setPreferredSize(new Dimension(720, 480));
    }

    /**
     * Vykreslovaci metoda.
     * Mimo zakladni paint(g) probehne pretypovani grafickeho kontextu na novejsi Graphics2D.
     * Nasledne se vykresli zeleny obdelnik predstavujici zem a probehne presun grafickeho kontextu do pocatecnich souradnic vykreslovaneho modelu.
     * Nakonec probehne volani metody pro vykresleni modelu
     *
     * @param g graficky kontext
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        double rectangleHeight = 40;
        g2.setColor(Color.GREEN);
        g2.fill(new Rectangle2D.Double(0, this.getHeight() - rectangleHeight, this.getWidth(), rectangleHeight));

        g2.translate(0, this.getHeight() - rectangleHeight - 150);	// 150 = Levy horni bod ohranicujiciho obdelnika vykreslovaneho modelu

        drawModel(g2);
    }

    /**
     * Volaci metoda pro vykres aktualniho modelu.
     * Nejprve overi, zda model nema byt otocen doleva - pokud ano, otoci graficky kontext.
     * Nasledne nastavi barvu vykreslovani a rozhodne o vykresleni postavy nebo prasete.
     *
     * @param g2 graficky kontext
     */
    public void drawModel(Graphics2D g2) {
        if (!goingRight) {
            g2.translate(currentX * 2, 0); // Posunuti pro zrcadleni
            g2.scale(-1.0, 1.0); // Zrcadleni
        }

        g2.setColor(Color.BLACK);

        if (currentDrawingModel.equals(DrawingModel.STICK_MAN)) {
            drawStickMan(g2);
        } else {
            drawPig(g2);
        }
    }

    /**
     * Metoda pro vykresleni postavy.
     * Pro vykresleni jsou pouzity komponenty Path2D a Ellipse2D.
     *
     * @param g2 graficky kontext
     */
    private void drawStickMan(Graphics2D g2) {
        Path2D stickManP2D = new Path2D.Double();

        // Nohy
        stickManP2D.moveTo(currentX + animationOffset, 150);
        stickManP2D.lineTo(currentX + 20, 80);
        stickManP2D.lineTo(currentX + 40 - animationOffset, 150);

        // Telo
        stickManP2D.moveTo(currentX + 20, 80);
        stickManP2D.lineTo(currentX + 20, 10);

        // Ruce
        stickManP2D.moveTo(currentX + 20, 20);
        stickManP2D.lineTo(currentX + 12 + animationOffset / 2.0, 50);
        stickManP2D.lineTo(currentX + 20 + animationOffset, 70);

        stickManP2D.moveTo(currentX + 20, 20);
        stickManP2D.lineTo(currentX + 12 - animationOffset / 2.0, 50);
        stickManP2D.lineTo(currentX + 20 - animationOffset, 70);

        // Pusa
        stickManP2D.moveTo(currentX + 35, 0);
        stickManP2D.lineTo(currentX + 15, -5);
        stickManP2D.closePath();

        // Hlava
        double rHead = 20;
        g2.draw(new Ellipse2D.Double(currentX, 10 - 2 * rHead, 2 * rHead, 2 * rHead));

        // Oko
        double rEye = 3;
        g2.draw(new Ellipse2D.Double(currentX + rHead * 1.3, -20, 2 * rEye, 1.5 * rEye));

        g2.draw(stickManP2D);
    }

    /**
     * Metoda pro vykresleni prasete.
     * Pro vykresleni jsou pouzity komponenty Path2D a Ellipse2D.
     *
     * @param g2 graficky kontext
     */
    private void drawPig(Graphics2D g2) {
        Path2D pigP2D = new Path2D.Double();

        //Nohy
        int legsOffset = 10;
        pigP2D.moveTo(currentX + legsOffset + animationOffset / 2.0 , 150);
        pigP2D.lineTo(currentX + legsOffset + 10, 130);
        pigP2D.lineTo(currentX + legsOffset + 20 - animationOffset / 2.0, 150);

        int spaceBetween = 60;
        pigP2D.moveTo(currentX + spaceBetween + legsOffset + animationOffset / 2.0 , 150);
        pigP2D.lineTo(currentX + spaceBetween + legsOffset + 10, 130);
        pigP2D.lineTo(currentX + spaceBetween + legsOffset + 20 - animationOffset / 2.0, 150);

        // Telo
        pigP2D.moveTo(currentX + 10, 130);
        pigP2D.lineTo(currentX + 10, 90);
        pigP2D.lineTo(currentX + 90, 90);
        pigP2D.lineTo(currentX + 90, 130);
        pigP2D.lineTo(currentX + 10, 130);

        // Ocas
        pigP2D.moveTo(currentX + 10, 90);
        pigP2D.lineTo(currentX - animationOffset / 5.0, 80);

        // Hlava
        pigP2D.moveTo(currentX + 90, 90);
        pigP2D.lineTo(currentX + 120, 115);
        pigP2D.lineTo(currentX + 90, 130);

        // Oko
        double rEye = 3;
        g2.draw(new Ellipse2D.Double(currentX + 95, 100, 1.5 * rEye, 2 * rEye));
        g2.draw(pigP2D);
    }

    /**
     * Metoda pro animaci.
     * Upravuje smer pohybu, aktualni souradnici X a offset pohybu koncetin.
     * Na konci vola prekresleni JPanelu
     */
    public void animate() {
        if (currentX < currentDrawingModelWidth && !goingRight) {
            reverseAnimation();
            currentX -= currentDrawingModelWidth;
        } else if (currentX > this.getWidth() - currentDrawingModelWidth && goingRight) {
            reverseAnimation();
            currentX += currentDrawingModelWidth;
        }

        currentX += animationSpeed;

        if (legForward) {
            animationOffset++;
        } else {
            animationOffset--;
        }

        if (animationOffset == 20) {
            legForward = false;
        } else if (animationOffset == 0) {
            legForward = true;
        }

        this.repaint();
    }

    /**
     * Metoda pro upravu pomocnych atributu k urceni smeru animace
     */
    private void reverseAnimation() {
        animationSpeed = -animationSpeed;
        goingRight = !goingRight;
    }

    /**
     * Metoda pro nastaveni aktualniho vykreslovaneho modelu z parametru.
     * Take se zmeni atribut aktualni sirky modelu, diky cemuz se spravne pocita, kdy se ma model otocit a zaroven jeho posun od hranic okna.
     *
     * @param newDrawingModel novy aktualni vykreselovany model
     */
    public void switchDrawingModelTo(DrawingModel newDrawingModel) {
        currentDrawingModel = newDrawingModel;

        if (currentDrawingModel.equals(DrawingModel.STICK_MAN)) {
            currentDrawingModelWidth = 40;
        } else {
            currentDrawingModelWidth = 120;
        }
        this.repaint();
    }
}
