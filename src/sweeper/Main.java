package sweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main extends JFrame {
    private Game game;
    private JPanel panel;
    private final static int IMAGE_OFFSET = 11;
    private final static int IMAGE_LEG = 8;
    private final static int IMAGE_WIDTH = 80 - 2*IMAGE_OFFSET - 2* IMAGE_LEG;
    private final static int IMAGE_HEIGHT = 70 - 2*IMAGE_OFFSET;

    private static InitialDialog dialog;

    public static void main(String[] args) {
        new Main();
    }

    private Main(){
        dialog = new InitialDialog(this);
        setImages();
        restartGame(dialog.getColsN(), dialog.getRowsN(), dialog.getBombsN());
        showRestartDialog("Начальные параметры");
    }

    static void showRestartDialog(String text) {
        dialog.setText(text);
        dialog.setVisible(true);
    }

    void restartGame(int cols, int rows, int bombs) {
        game = new Game(cols, rows, bombs);
        game.start();
        initPanel();
        initFrame();
    }

    private void initPanel(){
        panel = new JPanel(){

            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords()) {
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x * IMAGE_WIDTH - IMAGE_OFFSET,
                            coord.y * IMAGE_HEIGHT - IMAGE_OFFSET + ((coord.x & 1) == 0 ? IMAGE_HEIGHT/2 : 0), this);
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int screenX = e.getX();
                int screenY = e.getY();

                int y = screenY / IMAGE_HEIGHT;
                int x = (screenX - IMAGE_LEG) / IMAGE_WIDTH;

                Coord coord = new Coord(x, ((x & 1) == 0 ? (screenY - IMAGE_HEIGHT / 2) / IMAGE_HEIGHT : y));
                if (!Ranges.inRange(coord)) return;
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton(coord);
                if (e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton(coord);
                if (e.getButton() == MouseEvent.BUTTON2)
                    restartGame(dialog.getColsN(), dialog.getRowsN(), dialog.getBombsN());
                panel.repaint();
            }
        });

        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * IMAGE_WIDTH + 2*IMAGE_OFFSET,
                Ranges.getSize().y * IMAGE_HEIGHT + 2*IMAGE_OFFSET + IMAGE_HEIGHT / 2));
        add (panel);
    }

    private void initFrame(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Сапёр");
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setIconImage(getImage("icon"));

    }

    private void setImages(){
        for (sweeper.Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
    }

    private Image getImage (String name){
        String filename = "/img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
}
