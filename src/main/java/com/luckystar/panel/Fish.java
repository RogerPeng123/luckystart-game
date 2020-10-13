package com.luckystar.panel;

import com.luckystar.frame.GameFrame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 鱼
 */
public class Fish implements Runnable {

    // x和y坐标的步进值的参考标准
    private static final int BASE_STEP = 5;

    //控制鱼速度
    private static final int speedOfFish = 20;

    private int x, y, index, width, height, xStep, yStep;

    // 当前鱼的背景图
    private BufferedImage fishImage;

    // 一条鱼的所有帧的图片
    private BufferedImage[] fishImages = new BufferedImage[10];
    // 产生随机数
    Random r;

    public BufferedImage getFishImage() {
        return fishImage;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    /**
     * 初始化鱼的属性
     *
     * @param fishName
     * @throws IOException
     */
    public Fish(String fishName) throws IOException {
        // 加载鱼的图片
        BufferedImage tempFishImage;
        String resourceName;
        for (int i = 0; i < 10; i++) {
            if (i != 9) {
                resourceName = "/images/" + fishName + "_0" + (i + 1) + ".png";
            } else {
                resourceName = "/images/" + fishName + "_" + (i + 1) + ".png";
            }
            tempFishImage = ImageIO.read(getClass().getResourceAsStream(resourceName));
            fishImages[i] = tempFishImage;
        }
        fishImage = fishImages[index];

        // 根据背景图片的宽高设置鱼所占矩形区域的大小
        width = fishImage.getWidth();
        height = fishImage.getHeight();

        goInPool();
    }

    /**
     * 维持鱼的游动---x和y坐标的变化
     */
    @Override
    public void run() {
        while (true) {
            try {
                // 如果不休眠，鱼的速度过快，基本感觉不到鱼的存在。视觉暂留：1/24~1/7秒之间
                Thread.sleep(speedOfFish);

                index++;
                // 轮流切换帧，生成动画
                fishImage = fishImages[index % fishImages.length];

                x = x - xStep;
                int temp = r.nextInt(10) + 1;
                yStep = r.nextBoolean() ? temp : -temp;

                // 判断鱼是否到了边界，因为鱼是从右面进入的，因此只需要判断3个方向
                if (x <= 0 || y <= 0 || y >= 480) {
                    goInPool();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 判断鱼是否在网内
     */
    public boolean fishInNet(int netX, int netY) {
        int dx = netX - x;
        int dy = netY - y;
        return dx >= 0 && dx <= width && dy >= 0 && dy <= height;
    }

    /**
     * 鱼从屏幕上游出或者被网罩住的时候消失
     * 再从屏幕的右侧游动到屏幕中,实际上还是那几条鱼
     */
    void goInPool() {
        r = new Random();
        // 鱼从右侧游动到屏幕左侧
        x = GameFrame.getWIDTH() - 10;
        // 鱼的初始y的坐标是根据窗体的高度随机指定的
        y = r.nextInt(GameFrame.getHEIGHT() - 20 - height);
        // 鱼游动的速度是随机的
        xStep = r.nextInt(BASE_STEP) + 1;
    }
}
