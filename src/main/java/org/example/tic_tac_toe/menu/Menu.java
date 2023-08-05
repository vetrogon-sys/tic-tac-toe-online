package org.example.tic_tac_toe.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Menu extends JPanel implements ActionListener {

    public static final int FONT_SIZE = 36;
    private JFrame frame;
    private final List<MenuItem> menuItems;

    public Menu(JFrame frame, List<MenuItem>menuItems) {
        this.frame = frame;
        this.menuItems = menuItems;
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, StartMenu.WIDTH, StartMenu.HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Consola", Font.PLAIN, FONT_SIZE));
        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem menuItem = menuItems.get(i);
            g.drawString(menuItem.name(), 100, 150 + i * (FONT_SIZE + 15));
        }

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        repaint();
    }

}
