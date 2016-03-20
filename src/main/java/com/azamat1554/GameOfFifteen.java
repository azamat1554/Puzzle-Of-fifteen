package com.azamat1554;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Game of fifteen
 */
public class GameOfFifteen extends JFrame implements ActionListener {
    private static final int n = 4; // размерность поля: n*n
    private JButton[] buttons; //массив ячеек
    private int indexEmpty; //индекс пустой яцейки
    private int numbers[]; //хранит числа в ячейках
    private int countPressBtn = 0; //хранит кол-во ходов
    private JLabel jlab; //выводит кол-во ходов

    //Инициализация графического интерфейса игры
    public GameOfFifteen(String title) throws HeadlessException {
        super(title);
        setLayout(new BorderLayout());

        //создание меню
        JMenuBar jmb = new JMenuBar();
        JMenuItem jNewGame = new JMenuItem("New game");
        jNewGame.addActionListener(e -> startNewGame());
        add(jNewGame);
        jmb.add(jNewGame);
        setJMenuBar(jmb);

        jlab = new JLabel("Number of moves: 0", JLabel.CENTER);
        add(new JPanel().add(jlab), BorderLayout.SOUTH);

        JPanel jpnl = new JPanel(new GridLayout(n, n));
        Font font = new Font("SanfSerif", Font.BOLD, 40); //шрифт
        buttons = new JButton[n * n];
        numbers = RandomOrder.getShuffleArray(0, n * n, n); //массив с числами в случайном порядке

        JButton jbtn;
        for (int i = 0; i < n * n; i++) {
            if (numbers[i] == 0) {
                jbtn = new JButton();
                jbtn.setVisible(false);
                indexEmpty = i;
            } else jbtn = new JButton(String.valueOf(numbers[i]));
            buttons[i] = jbtn;
            jpnl.add(jbtn); //добавлнеие на панель
            jbtn.setFont(font);
            jbtn.addActionListener(this);
            jbtn.setActionCommand(i + "");
        }
        add(jpnl, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setMinimumSize(new Dimension(350, 350));
        setVisible(true);
    }

    private void startNewGame() {
        numbers = RandomOrder.getShuffleArray(0, n * n, n);
        buttons[indexEmpty].setVisible(true);
        for (int i = 0; i < n * n; i++) {
            if (numbers[i] != 0)
                buttons[i].setText(String.valueOf(numbers[i]));
            else {
                buttons[i].setVisible(false);
                indexEmpty = i;
            }
        }
        jlab.setText("Number of moves: " + (countPressBtn = 0)); //обнулить счетчик
    }

    //обработчик события нажания на кнопку
    public void actionPerformed(ActionEvent e) {
        int idxPressBtn = Integer.parseInt(e.getActionCommand());

        if (((idxPressBtn == indexEmpty - 1) & ((idxPressBtn + 1) % n != 0)) ||
                ((idxPressBtn == indexEmpty + 1) & (idxPressBtn % (n) != 0)) ||
                (idxPressBtn == indexEmpty - n) || (idxPressBtn == indexEmpty + n)) {

            swapButton(buttons[idxPressBtn], buttons[indexEmpty]);
            //переставляем местами значения
            numbers[indexEmpty] = numbers[idxPressBtn];
            numbers[idxPressBtn] = 0;
            indexEmpty = idxPressBtn;

            if (isSolve()) {
                JOptionPane.showMessageDialog(this, "You solved it!");
            } else
                jlab.setText("Number of moves: " + (++countPressBtn));
        }
    }

    private void swapButton(JButton pressButton, JButton emptyButton) {
        emptyButton.setText(pressButton.getText());
        emptyButton.setVisible(true);

        pressButton.setVisible(false);

        jlab.requestFocusInWindow();
    }

    private boolean isSolve() {
        for (int i = 0; i < numbers.length - 1; i++) {
            if (numbers[i] != i + 1) return false;
        }
        return true;
    }

    //точка входа
    public static void main(String... args) throws Exception {
        //if (args[0] != null) n = Integer.parseInt(args[0]);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameOfFifteen("Game of fifteen");
            }
        });
    }


}