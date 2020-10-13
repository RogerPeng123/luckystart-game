package com.luckystar.panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

/**
 * 编辑窗体内元素
 */
public class Pool extends JPanel {

    //游戏背景图
    private BufferedImage background = null;

    //鱼
    private Fish fish = null;

    // 鱼对象数组
    Fish[] fishs = new Fish[11];

    // 渔网
    Net net = null;

    // 分数
    int score = 0;
    Font font = new Font("楷体", Font.BOLD, 20);

    public Pool() throws IOException {
        //读取资源文件
        background = ImageIO.read(getClass().getResourceAsStream("/images/bg.jpg"));

        /* 产生11条鱼，每1条鱼都是1个线程 */
        for (int i = 0; i < 11; i++) {
            if (i < 9) {
                fish = new Fish("fish0" + (i + 1));
            } else {
                fish = new Fish("fish" + (i + 1));
            }

            fishs[i] = fish;
            new Thread(fish).start();
        }
    }

    /**
     * 重构调用绘制组件
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        // 绘制背景
        g.drawImage(background, 0, 0, null);
        for (int i = 0; i < fishs.length; i++) {
            Fish tempfish = fishs[i];
            // 绘制鱼
            g.drawImage(tempfish.getFishImage(), tempfish.getX(), tempfish.getY(), null);
        }
        if (net.show) {
            // 判断渔网是否显示，绘制渔网
            g.drawImage(net.getNetImage(), net.getX() - net.getWidth() / 2,
                    net.getY() - net.getHeight() / 2, null);
        }
        g.setFont(font);
        g.setColor(Color.YELLOW);
        g.drawString("SCORE:", 10, 20);
        g.setColor(Color.MAGENTA);
        g.drawString("      " + score, 10, 20);
    }

    /**
     * 处理鼠标事件-实现点击鼠标进行捕鱼
     *
     * @throws Exception
     */
    public void action() throws Exception {
        net = new Net();
        /**
         * 实现鼠标适配器避免出现实现鼠标监听器接口或者使用匿名内部类出现的代码冗余
         * 我们可以根据需要重写自己需要的方法
         */
        MouseAdapter adapter = new MouseAdapter() {
            /* 鼠标进入，渔网显示；鼠标移出，渔网不显示 */
            @Override
            public void mouseEntered(MouseEvent e) {
                net.show = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                net.show = false;
            }

            /**
             * 渔网的位置随着鼠标的位置变化
             */
            @Override
            public void mouseMoved(MouseEvent e) {
                net.setX(e.getX());
                net.setY(e.getY());
            }

            /**
             * 当鼠标按下的时候进行捕鱼操作
             */
            @Override
            public void mousePressed(MouseEvent e) {
                catchFish();
            }
        };

        // 添加鼠标监听器
        this.addMouseListener(adapter);
        // 鼠标移动监听器
        this.addMouseMotionListener(adapter);

        while (true) {
            repaint();
            try {
                // 每隔一定时间刷新屏幕，需要符合视觉暂留设置50~100ms
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 渔网是否捉到鱼
     */
    protected void catchFish() {
        // 鱼在不在网的范围内？在的话就让鱼消失
        for (int i = 0; i < fishs.length; i++) {
            fish = fishs[i];
            // 判断在不在网的范围
            if (fish.fishInNet(net.getX(), net.getY())) {
                // 鱼从池子中消失，重新从右边游入
                fish.goInPool();
                // 不同的鱼有不同的分数
                score += fish.getWidth() / 10;
            }
        }
    }
}