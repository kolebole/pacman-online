import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GreedSnake extends JPanel implements KeyListener, Runnable { //自定义面板类，继承了键盘和线程接口
 private static final long serialVersionUID = 1L;
 private static final int TILE_SIZE = 10;
 
 public static void main(String[] args) {
  SwingUtilities.invokeLater(new Runnable() {
   public void run() {
    JFrame frame = new JFrame("GreedSnake");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    final GreedSnake snake = new GreedSnake();
    JButton btn = new JButton(new AbstractAction("开始") {
     private static final long serialVersionUID = 1L;
     public void actionPerformed(ActionEvent e) {
      snake.reset();
     }
    });
    JPanel panel = new JPanel();
    panel.add(btn);
    btn.setFocusable(false);
    frame.addKeyListener(snake); // 添加键盘监视器
    frame.add(panel, BorderLayout.NORTH);
    frame.add(snake, BorderLayout.CENTER);
    frame.setBounds(100, 100, 610, 500);
    frame.setVisible(true);
    frame.validate();
   }
  });
 }
 
 JButton blackButton;
 JButton whiteButton;
 Point snake[] = new Point[20]; // 定义蛇按钮
 int shu = 1; // 蛇的节数
 boolean win = true; // 判定结果是输 还是赢
 int weix, weiy; // 食物位置
 boolean t = false; // 判定游戏是否结束
 FangXiang fangxiang = FangXiang.YOU; // 蛇移动方向
 int x = 0, y = 0; // 蛇头位置
 Random rand = new Random();
 String message = "按上下左右键控制蛇行动";
 
 enum FangXiang {
  YOU, ZUO, SHANG, XIA
 }

 GreedSnake() {
  new Thread(this).start();

  weix = rand.nextInt(10) * 60; // (0-9)*60为横坐标
  weiy = rand.nextInt(10) * 40; // (0-9)*40为纵坐标
  for (int i = 0; i < 20; i++)
   snake[i] = new Point();
  
  setBackground(Color.cyan);
  blackButton = new JButton();
  blackButton.setBounds(0, 0, TILE_SIZE, TILE_SIZE);
  blackButton.setBackground(Color.black);
  
  whiteButton = new JButton();
  whiteButton.setBounds(0, 0, TILE_SIZE, TILE_SIZE);
 }

 public void reset() { // 按下begin按钮
  t=true;
  shu=1;
  snake[0].move(0, 0);
  x = y = 0;
  fangxiang = FangXiang.YOU;
 }
 
 public void run() { // 接收线程
  while (true) {
   if(t) {
    switch(fangxiang) {
    case YOU: x += 10; break;// 向右
    case ZUO: x -= 10; break;// 向左
    case SHANG: y -= 10; break;// 向上
    case XIA: y += 10; break;// 向下
    }
    
    if (x == weix && y == weiy) { // 吃到食物
     shu++;
     weix = rand.nextInt(10) * 60; // (0-9)*60为横坐标
     weiy = rand.nextInt(10) * 40; // (0-9)*40为纵坐标
     
     if (shu == 15) { // 如果蛇节数等于15则胜利
      t = false;
      win = true;
      message = "YOU WIN!";
     }
    }
    
    for (int i = shu; i > 0; i--) { // 设置蛇节位置
     snake[i].setLocation(snake[i-1]);
    }
 
    snake[0].setLocation(x, y);// 设置蛇头位置
    
    for (int i=4;i<shu;i++) { // 判断是否咬自己的尾巴
     if (snake[i].equals(snake[0])) {
      t = false;
      win = false;
      message = "GAME OVER!";
      break;
     }
    }
    
    if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) { // 判断是否撞墙
     t = false;
     win = false;
     message = "GAME OVER!";
    }
    
    repaint();// 重绘
   }
   try {
    Thread.sleep(100); // 睡眠100ms
   } catch (InterruptedException e) {
    e.printStackTrace();
   }
  }
 }

 public void keyPressed(KeyEvent e) { // 按下键盘方向键
  if (e.getKeyCode() == KeyEvent.VK_RIGHT) {// 右键
   if (fangxiang != FangXiang.ZUO)// 如果先前方向不为左
    fangxiang = FangXiang.YOU;
  } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
   if (fangxiang != FangXiang.YOU)
    fangxiang = FangXiang.ZUO;
  } else if (e.getKeyCode() == KeyEvent.VK_UP) {
   if (fangxiang != FangXiang.XIA)
    fangxiang = FangXiang.SHANG;
  } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
   if (fangxiang != FangXiang.SHANG)
    fangxiang = FangXiang.XIA;
  }
 }

 public void keyTyped(KeyEvent e) {}

 public void keyReleased(KeyEvent e) {}

 public void paint(Graphics g) { // 在面板上绘图
  super.paint(g);
  for(int i=0;i<shu;i++) {
   g.translate(snake[i].x, snake[i].y);
   if(i==0) blackButton.paint(g);
   else whiteButton.paint(g);
   g.translate(-snake[i].x, -snake[i].y);
  }
  g.setColor(Color.red);
  g.fillOval(weix, weiy, TILE_SIZE, TILE_SIZE);// 食物
  g.drawRect(0, 0, getWidth() - 1, getHeight() - 1); // 墙
  g.setColor(Color.black);
  if (!t) {
   Rectangle2D rect = g.getFontMetrics().getStringBounds(message, g);
   g.drawString(message, (getWidth()-(int)rect.getWidth())/2, (getHeight()-(int)rect.getHeight())/2);// 输出游戏成功
  }
 }
} 