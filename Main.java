package com.MyApplication;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.Random;
import java.util.Vector;


class Menu extends JFrame
{
    JButton Play;
    JButton Help;
    JButton Exit;
    JButton Res;
    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/javabase";
    private static final String user = "root";
    private static final String password = "qwerty";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    Menu()
    {
        JFrame jFrame = new JFrame("Menu");
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jFrame.setSize(300,380);
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
        Play = new JButton("Играть");
        Play.setBounds(40,50,200,40);
        jFrame.getContentPane().add(Play);
        Res = new JButton("Результаты");
        Res.setBounds(40,110,200,40);
        jFrame.getContentPane().add(Res);
        Help = new JButton("Правила");
        Help.setBounds(40,170,200,40);
        jFrame.getContentPane().add(Help);
        Exit = new JButton("Выход");
        Exit.setBounds(40,230,200,40);
        jFrame.getContentPane().add(Exit);
        jFrame.getContentPane().setLayout(null);
        Play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String count = JOptionPane.showInputDialog("Введите количество игроков:");
                UIManager.put("OptionPane.yesButtonText"   , "Легко"    );
                UIManager.put("OptionPane.noButtonText"    , "Нормально"   );
                UIManager.put("OptionPane.cancelButtonText", "Сложно");
                int difficult = JOptionPane.showConfirmDialog(null,"Выберите уровень сложности","Сложность",JOptionPane.YES_NO_CANCEL_OPTION);
                jFrame.setVisible(false);
                new Game(Integer.valueOf(count),difficult,jFrame);
            }
        });
        Res.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<Player> recordList = new Vector<>();
                String query = "select * from players";
                try {
                    //Открываем соединение с сервером базы данных MySQL
                    con = DriverManager.getConnection(url, user, password);
                    //получение объекта Statement для выполнения запроса
                    stmt = con.createStatement();
                    // выполняем запрос SELECT
                    rs = stmt.executeQuery(query);
                    while (rs.next())
                    {
                        recordList.add(new Player(rs.getString(1),rs.getInt(2)));
                    }
                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                } finally {
                    //Закрываем соединение, stmt, и rs здесь
                    try { con.close(); } catch(SQLException se) { }
                    try { stmt.close(); } catch(SQLException se) { }
                    try { rs.close(); } catch (SQLException se) {}
                }
                if(recordList != null) {
                    Vector<String> columnsHeader = new Vector<String>(2);
                    columnsHeader.add("Игрок");
                    columnsHeader.add("Очки");
                    Vector<Vector<String>> data = new Vector<Vector<String>>();
                    for (int i = 0; i < recordList.size(); i++) {
                        Vector<String> row = new Vector<String>();
                        row.add(recordList.get(i).nickname);
                        row.add(String.valueOf(recordList.get(i).scores));
                        data.add(row);
                    }
                    JTable jTable = new JTable(data, columnsHeader) {
                        private static final long serialVersionUID = 1L;
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    jFrame.setVisible(false);
                    jTable.setBounds(20, 35, 145, 16*recordList.size());
                    new Results(jTable,jFrame);
                }
                else
                    JOptionPane.showMessageDialog(null,"Пусто","Результаты",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        Help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"По порядку участники единожды бросают кости, что повторяется раунд за раундом.\n" +
                        "Необходимо чтобы, сумма очков первого раунда равнялась двум, второго раунда – трём и так до 12.\n" +
                        "За каждым успешным броском полученная сумма очков прибавляется к собственному счету каждого игрока.\n" +
                        "Побеждает игрок, набравший наибольшее количество очков.","Правила",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
class Game extends JFrame
{
    BufferedImage image_first;
    BufferedImage image_sec;
    BufferedImage image_third;
    BufferedImage image_fourth;
    BufferedImage image_fifth;
    BufferedImage image_sixth;
    BufferedImage round_first;
    BufferedImage round_sec;
    BufferedImage round_third;
    BufferedImage round_fourth;
    BufferedImage round_fifth;
    BufferedImage round_sixth;
    BufferedImage round_sev;
    BufferedImage round_eig;
    BufferedImage round_nine;
    BufferedImage round_ten;
    BufferedImage round_elev;
    BufferedImage phone;
    boolean proc = false;
    int count = 2;
    int j = 0;
    int temp,temp2;
    {
        try {
            image_first = ImageIO.read(new File("src/1.png"));
            image_sec = ImageIO.read(new File("src/2.png"));
            image_third = ImageIO.read(new File("src/3.png"));
            image_fourth = ImageIO.read(new File("src/4.png"));
            image_fifth = ImageIO.read(new File("src/5.png"));
            image_sixth = ImageIO.read(new File("src/6.png"));
            round_first = ImageIO.read(new File("src/r1.png"));
            round_sec = ImageIO.read(new File("src/r2.png"));
            round_third = ImageIO.read(new File("src/r3.png"));
            round_fourth = ImageIO.read(new File("src/r4.png"));
            round_fifth = ImageIO.read(new File("src/r5.png"));
            round_sixth = ImageIO.read(new File("src/r6.png"));
            round_sev = ImageIO.read(new File("src/r7.png"));
            round_eig = ImageIO.read(new File("src/r8.png"));
            round_nine = ImageIO.read(new File("src/r9.png"));
            round_ten = ImageIO.read(new File("src/r10.png"));
            round_elev = ImageIO.read(new File("src/r11.png"));
            phone = ImageIO.read(new File("src/33.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    BufferedImage[] roundList = new BufferedImage[]{round_first,round_sec,round_third,round_fourth,round_fifth,round_sixth,round_sev,round_eig,round_nine,round_ten,round_elev};
    BufferedImage[] imageList = new BufferedImage[]{image_first, image_sec, image_third, image_fourth, image_fifth, image_sixth};
    JButton Roll = new JButton("Кинуть (R)");
    JButton Stop = new JButton("Остановить (S)");
    JFrame jFrame;
    int difficult = 0;
    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/javabase";
    private static final String user = "root";
    private static final String password = "qwerty”;

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    Game giveGame() {return this;}
    Game(int players,int dif,Jframe menu)
    {
        UIManager.put(“OptionPane.cancelButtonText”, “Отмена”);
        if(dif == 0)
            difficult = 1000;
        else
            if(dif == 1)
                difficult = 500;
            else
                difficult = 200;
        Roll.setBounds(200, 500, 200, 40);
        Stop.setBounds(500,500,200,40);
        Stop.setEnabled(false);
        Vector<Player> playerList = new Vector<>(players);
        for (int I = 0; I < players; i++) {
            String nickname = JoptionPane.showInputDialog(“Введите имя “ + (I + 1) + “ игрока:”);
            playerList.add(new Player(nickname, 0));
        }
        Vector<String> columnsHeader = new Vector<String>(2);
        columnsHeader.add(“Игрок”);
        columnsHeader.add(“Очки”);
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        for (int I = 0; I < playerList.size(); i++) {
            Vector<String> row = new Vector<String>();
            row.add(playerList.get(i).nickname);
            row.add(String.valueOf(playerList.get(i).scores));
            data.add(row);
        }
        Jtable jTable = new Jtable(data,columnsHeader) {
            private static final long serialVersionUID = 1L;
            public  oolean isCellEditable(int row, int column) {
                return false;
            }
            };
        jTable.setAutoCreateRowSorter(true);
        jTable.setEnabled(false);
        jTable.setBounds(670, 30, 200, 16*players);
        jTable.setBackground(Color.LIGHT_GRAY);
        Jlabel Players = new Jlabel(“Игрок”);
        Players.setBounds(670,10,40,12);
        Players.setForeground(Color.WHITE);
        Jlabel Scores = new Jlabel(“Очки”);
        Scores.setBounds(770,10,40,12);
        Scores.setForeground(Color.WHITE);
        Jlabel curPlayer = new Jlabel();
        curPlayer.setForeground(Color.WHITE);
        curPlayer.setText(“Игрок: “ + playerList.get(0).nickname);
        curPlayer.setBounds(0,0,200,30);
        curPlayer.setFont(new Font(“Serif”,Font.BOLD,25));
        Jlabel curScore = new Jlabel();
        curScore.setForeground(Color.WHITE);
        curScore.setText(“Количество очков: “ + playerList.get(0).scores);
        curScore.setBounds(0,40,300,30);
        curScore.setFont(new Font(“Serif”,Font.BOLD,25));
        jFrame = new Jframe(“Application”);
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jFrame.setSize(900, 600);
        jFrame.setVisible(true);
        JpanelWithBackground jPanel = null;
        try {
            jPanel = new JpanelWithBackground(“src/333.png”);
        } catch (IOException e) {
            e.printStackTrace();
        }
        jPanel.setBounds(0,0,900,600);
        jPanel.setLayout(null);
        jPanel.add(Roll);
        jPanel.add(Stop);
        jPanel.add(jTable);
        jPanel.add(Players);
        jPanel.add(Scores);
        jPanel.add(curPlayer);
        jPanel.add(curScore);
        jFrame.getContentPane().add(jPanel);

            AbstractAction button_R = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    proc = true;
                    Roll.setEnabled(false);
                    Stop.setEnabled(true);
                    MyThread myThread = new MyThread(giveGame());
                    myThread.start();
                }
            };
            Roll.addActionListener(button_R);
            Roll.getInputMap(Jcomponent.WHEN_IN_FOCUSED_WINDOW).
                    put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), “1”);
            Roll.getActionMap().put(“1”, button_R);
            AbstractAction button_S = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    proc = false;
                    Stop.setEnabled(false);
                    Roll.setEnabled(true);
                    if(temp + 1 + temp2 + 1 == count)
                    {
                        if(count < 5)
                            JoptionPane.showMessageDialog(null,”Игроку под именем “ + playerList.get(j).nickname + “ сегодня везёт!\n” +
                                    “Он записывает в свою копилку “ + count + “ очка сверху!”);
                        else
                            JoptionPane.showMessageDialog(null,”Игроку под именем “ + playerList.get(j).nickname + “ сегодня везёт!\n” +
                                    “Он записывает в свою копилку “ + count + “ очков сверху!”);
                        playerList.get(j).scores += count;
                        jTable.setValueAt(String.valueOf(playerList.get(j).scores),j,1);
                        count++;
                    }
                    Graphics graphics = jFrame.getGraphics();
                    if(count == 13) // поменяй на 13
                    {
                        String query = “truncate table players”;
                        try {
                            con = DriverManager.getConnection(url, user, password);
                            // getting Statement object to execute query
                            stmt = con.createStatement();
                            // executing SELECT query
                            stmt.execute(query);
                            for (int I = 0; I < playerList.size(); i++)
                            {
                                PreparedStatement pstmt = con.prepareStatement(“insert INTO `players`(Nickname,Scores) VALUES (?, ?)”);
                                pstmt.setString(1,playerList.get(i).nickname);
                                pstmt.setInt(2, playerList.get(i).scores);
                                pstmt.executeUpdate();
                            }
                        } catch (SQLException sqlEx) {
                            sqlEx.printStackTrace();
                        } finally {
                            //close connection ,stmt and resultset here
                            try { con.close(); } catch(SQLException se) { /*can’t do anything */ }
                            try { stmt.close(); } catch(SQLException se) { /*can’t do anything */ }
                        }
                        Player winner = playerList.get(0);
                        for (int I = 0;I < playerList.size();i++)
                            if(playerList.get(i).scores > winner.scores) winner = playerList.get(i);
                        int result = JoptionPane.showOptionDialog(null,
                                “Игра окончена, победителем в этой игре становится игрок под именем “ + winner.nickname + “!!!\n” +
                                        “Он набрал “ + winner.scores + “ очков.\n” +
                                        “Остальным остаётся пожелать удачи в следующей игре!\n”
                                        + “Что делаем дальше?”,
                                “Конец игры”,
                                JoptionPane.OK_CANCEL_OPTION,
                                JoptionPane.INFORMATION_MESSAGE,
                                null,
                                new String[]{“Играть заново”, “Выход в главное меню”}, // this is the array
                                “default”);
                        if(result == 0)
                        {
                            count = 2;
                            for (int I = 0; I < playerList.size();i++)
                            {
                                playerList.get(i).scores = 0;
                                jTable.setValueAt(String.valueOf(playerList.get(i).scores),I,1);
                            }
                        }
                        if(result == 1) {
                            jFrame.setVisible(false);
                            menu.setVisible(true);
                        }
                    }
                    else
                    {
                        graphics.drawImage(phone,385,60,null);
                        graphics.drawImage(roundList[count-2],385,60,null);
                        if(j < playerList.size() – 1)
                            j++;
                        else
                            j = 0;
                        if(count < 5)
                            JoptionPane.showMessageDialog(null,”Ход игрока “ + playerList.get(j).nickname + “\nЕго цель выбить “ + count + “ очка”);
                        else
                            JoptionPane.showMessageDialog(null,”Ход игрока “ + playerList.get(j).nickname + “\nЕго цель выбить “ + count + “ очков”);
                        curPlayer.setText(“Игрок: “ + playerList.get(j).nickname);
                        curScore.setText(“Количество очков: “ + playerList.get(j).scores);
                    }
                }
            };
            Stop.addActionListener(button_S);
            Stop.getInputMap(Jcomponent.WHEN_IN_FOCUSED_WINDOW).
                put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), “1”);
            Stop.getActionMap().put(“1”, button_S);
            jFrame.setLayout(null);
            jFrame.setLocationRelativeTo(null);
            JoptionPane.showMessageDialog(null,”Ход игрока “ + playerList.get(j).nickname + “\nЕго цель выбить “ + count + “ очка”);
            Graphics graphics = jFrame.getGraphics();
            graphics.drawImage(phone,385,60,null);
            graphics.drawImage(round_first,385,60,null);
    }
    void gameDraw()
    {
        Random random = new Random();
        temp = random.nextInt(6);
        temp2 = random.nextInt(6);
        Graphics graphics = jFrame.getGraphics();
        graphics.drawImage(imageList[temp], 240, 200, null);
        graphics.drawImage(imageList[temp2], 470, 200, null);
    }
}
class Results extends Jframe
{
    Jbutton Back;
    Results(Jtable jTable,Jframe jFrame)
    {
        Jframe Res = new Jframe(“Результаты прошлой игры”);
        Res.setSize(200,400);
        Res.setVisible(true);
        Res.setLocationRelativeTo(null);
        Res.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Res.setLayout(null);
        Jlabel Players = new Jlabel(“Игрок”);
        Players.setBounds(20,10,40,14);
        Players.setForeground(Color.BLACK);
        Jlabel Scores = new Jlabel(“Очки”);
        Scores.setBounds(90,10,40,14);
        Scores.setForeground(Color.BLACK);
        jTable.setEnabled(false);
        jTable.setBackground(Color.LIGHT_GRAY);
        Back = new Jbutton(“Назад”);
        Back.setBounds(50,320,100,30);
        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Res.setVisible(false);
                jFrame.setVisible(true);
            }
        });
        Res.getContentPane().add(Players);
        Res.getContentPane().add(Scores);
        Res.getContentPane().add(Back);
        Res.getContentPane().add(jTable);
    }
}

public class Main {
    public static void main(String args[]) {
        new Menu();
    }
}
