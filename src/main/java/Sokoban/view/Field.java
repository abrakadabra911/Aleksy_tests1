package Sokoban.view;

import Sokoban.controller.EventListener;
import Sokoban.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

public class Field extends JPanel {
    private View view;
    EventListener eventListener;

    public Field(View view) {
        this.view = view;
        KeyHandler keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        setFocusable(true);
    }

    @Override
    public void paint(Graphics g) {
        Set<GameObject> allGameObjects = view.getGameObjects().getAll();
        Set<Home> homes = view.getGameObjects().getHomes();
        Player player = view.getGameObjects().getPlayer();
        allGameObjects.remove(player);
        allGameObjects.removeAll(homes);
   //     g.setColor(new Color(230, 220, 200));

        BufferedImage background = null;
        String RESOURCE_PATH = getClass().getPackage().getName()
                .replaceAll("\\.", "/")
                .replace("Sokoban/view", "pic/Background.png");
        try {
            background = ImageIO.read(getClass().getClassLoader().getResource(RESOURCE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }


     //   g.fillRect(0, 0, 540, 580);
        g.drawImage(background,0,0,null);
        g.setColor(Color.black);
        g.setFont(new Font("MyFont", Font.ITALIC, 12));
        // logo
        g.drawString("The game was created by Aliaksei Zayats", 200, 538);
        g.drawString("using Maven,MVC", 200, 550);
        for (GameObject object : allGameObjects) object.draw(g);
        for (Home home : homes) home.draw(g); // we need to have homes draw above boxes
        player.draw(g);// we need to have player draw above homes
    }


    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case (KeyEvent.VK_LEFT):
                    eventListener.move(Direction.LEFT);
                    break;
                case (KeyEvent.VK_RIGHT):
                    eventListener.move(Direction.RIGHT);
                    break;
                case (KeyEvent.VK_UP):
                    eventListener.move(Direction.UP);
                    break;
                case (KeyEvent.VK_DOWN):
                    eventListener.move(Direction.DOWN);
                    break;
                case (KeyEvent.VK_R):
                    int confirm = JOptionPane.showConfirmDialog(null, "restart level?");
                    if (confirm == 0) eventListener.restart();
                    break;
            }
        }
    }
}

