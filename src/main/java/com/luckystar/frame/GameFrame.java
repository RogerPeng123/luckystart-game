package com.luckystar.frame;

import com.luckystar.panel.Pool;

import javax.swing.*;
import java.io.IOException;

/**
 * 游戏窗体
 *
 * @time 2020-10-13 12:00
 */
public class GameFrame extends JFrame {

    //设置窗体宽、高
    private static final int WIDTH = 800;
    private static final int HEIGHT = 480;

    private Pool pool;

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public GameFrame() {

        this.setTitle("游戏大战");
        this.setSize(WIDTH, HEIGHT);
        // 设置窗口居中，必须放在setSize之后
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            pool = new Pool();
            this.getContentPane().add(pool);
            this.setVisible(true);
            pool.action();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载资源失败！",
                    "应用程序错误", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "初始化游戏失败！",
                    "应用程序错误", JOptionPane.ERROR_MESSAGE);
        }
    }


}
