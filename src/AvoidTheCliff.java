package src;

import javax.swing.*;

class AvoidTheCliff {
    private static void initWindow() {
        JFrame window = new JFrame("Don't fall the cliff!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Board board = new Board();
        window.add(board);
        window.addKeyListener(board);
        
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AvoidTheCliff::initWindow);
    }
}
