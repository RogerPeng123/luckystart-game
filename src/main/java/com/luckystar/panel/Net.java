package com.luckystar.panel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 渔网类：网的位置随着鼠标指针的移动而移动
 */
public class Net {

    private BufferedImage netImage = null;

    private int x = 0, y = 0, width, height;

    // 是否显示当前网对象，鼠标移出游戏界面时，渔网消失
    boolean show;

    public BufferedImage getNetImage() {
        return netImage;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * 构造器中加载渔网的背景图片，刚刚显示出游戏界面时是看不见渔网的
     */
    public Net() {

        try {
            netImage = ImageIO.read(getClass().getResourceAsStream("/images/net.png"));
        } catch (IOException e) {
            System.out.println("渔网资源加载失败！");
            e.printStackTrace();
        }
        show = false;
        width = netImage.getWidth();
        height = netImage.getHeight();
    }
}
