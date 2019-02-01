/*
 * Main.java
 *
 * Created on 23 de Outubro de 2007, 11:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

//iniciar a execução com o número da rede digitado e o foco no botão confirmar
package consignatortabajara;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author danieloliveira
 */
public class Main extends Thread {

    //posição do botão confirmar
    private static int CONFIRM_X = 160;
    private static int CONFIRM_Y = 466;
    //Cor quando consignou a rede
    private static Color netColor = new Color(255, 255, 24);
    //posição da cor quando consignou a rede
    private static int NET_X = 248;
    private static int NET_Y = 360;
    //Cor do erro 1
    private static Color error1Color = new Color(255, 253, 23);
    //posição do erro 1
    private static int ERROR1_X = 297;
    private static int ERROR1_Y = 354;
    //Cor do Erro 2
    private static Color error2Color = new Color(255, 0, 0);
    //posição do erro 2
    private static int ERROR2_X = 267;
    private static int ERROR2_Y = 308;
    //Cor quando acaba
    private static Color endColor = new Color(15, 85, 169);
    //posição quando acaba
    private static int END_X = 686;
    private static int END_Y = 306;
    //controla o mouse e o teclado
    private Robot robot;
    private Timer timer = new Timer();

    /** Creates a new instance of Main */
    public Main() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Main().start();
    }

    @Override
    public void run() {
        try {
            robot = new Robot();

            //mover mouse para o botão confirmar
            robot.mouseMove(CONFIRM_X, CONFIRM_Y);
            //clicar botão do mouse
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            //TODO: esperar consignar rede
            timer.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {

                    Color currentNetColor = robot.getPixelColor(NET_X, NET_Y);
                    Color currentError1Color = robot.getPixelColor(ERROR1_X, ERROR1_Y);
                    Color currentError2Color = robot.getPixelColor(ERROR2_X, ERROR2_Y);
                    Color currentEndColor = robot.getPixelColor(END_X, END_Y);

                    //System.out.println("R: " + currentNetColor.getRed() + "G: " + currentNetColor.getGreen() + "B:" + currentNetColor.getBlue());

                    if (currentError2Color.getRed() == error2Color.getRed() &&
                            currentError2Color.getGreen() == error2Color.getGreen() &&
                            currentError2Color.getBlue() == error2Color.getBlue()) {
                        timer.cancel();
                    } else if (currentError1Color.getRed() == error1Color.getRed() &&
                            currentError1Color.getGreen() == error1Color.getGreen() &&
                            currentError1Color.getBlue() == error1Color.getBlue()) {
                        timer.cancel();
                    } else if (currentNetColor.getRed() == netColor.getRed() &&
                            currentNetColor.getGreen() == netColor.getGreen() &&
                            currentNetColor.getBlue() == netColor.getBlue()) {
                        //apertar enter
                        robot.keyPress(KeyEvent.VK_ENTER);
                        robot.keyRelease(KeyEvent.VK_ENTER);
                        //mover mouse para o botão confirmar
                        robot.mouseMove(CONFIRM_X, CONFIRM_Y);
                        //clicar botão do mouse
                        new Timer().schedule(new TimerTask() {

                            @Override
                            public void run() {
                                robot.mousePress(InputEvent.BUTTON1_MASK);
                                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                            }
                        }, 500);
                    } /*else if (currentEndColor.getRed() == endColor.getRed() &&
                            currentEndColor.getGreen() == endColor.getGreen() &&
                            currentEndColor.getBlue() == endColor.getBlue()) {
                        System.err.println("fim");
                        timer.cancel();
                    }*/
                }
            }, 0, 300);
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
    }
}
