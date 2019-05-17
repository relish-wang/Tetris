package wang.relish.tetris.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import wang.relish.tetris.config.FrameConfig;
import wang.relish.tetris.config.GameConfig;
import wang.relish.tetris.dto.GameDto;

public abstract class Layer {
	/**
	 * 内边距
	 */
	protected static final int PADDING;
	/**
	 * 边框宽度
	 */
	protected static final int BORDER;
	static{
		/**
		 * 获得游戏配置
		 */
		FrameConfig fCfg = GameConfig.getFrameConfig();
		PADDING = fCfg.getPadding();
		BORDER = fCfg.getBorder();

	}
	/**
	 * 定义窗体的宽度
	 */
	protected int WINDOW_W = Img.WINDOW.getWidth(null);
	/**
	 * 定义窗体的高度
	 */
	protected int WINDOW_H = Img.WINDOW.getHeight(null);
	/**
	 * 每个数字的宽度
	 */
	protected static final int IMG_NUMBER_W = Img.NUMBER.getWidth(null) / 10;
	/**
	 * 每个数字的高度
	 */
	protected  static final int IMG_NUMBER_H = Img.NUMBER.getHeight(null);
	/**
	 * 字体的颜色
	 */
	private static final Font DEF_FONT = new Font("黑体",Font.BOLD,20);
	/**
	 * 值槽的高度
	 */
	protected  static int IMG_RECT_H = Img.RECT.getHeight(null);
	/**
	 * 值槽图片的宽度
	 */
	private static int IMG_RECT_W = Img.RECT.getWidth(null);
	/**
	 * 值槽宽度
	 */
	private final int rectW;
	/**
	 * 窗体在面板中展示的x坐标
	 */
	protected int x;
	/**
	 * 窗体在面板中显示的y坐标
	 */
	protected int y;
	/**
	 * 自定义需要窗体的的宽度
	 */
	protected int w;
	/**
	 * 自定义需要窗体的高度
	 */
	protected int h;
	/**
	 * 游戏数据
	 */
	protected GameDto dto = null;
	/**
	 * 构造函数
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	protected Layer(int x,int y,int w,int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.rectW = this.w - (PADDING << 1);
	}
	/**
	 * 绘制窗口
	 * @param g
	 */

	protected void creatWindow(Graphics g)
	{
		//左上
		g.drawImage(Img.WINDOW, x, y, x + BORDER, y + BORDER, 0, 0, BORDER, BORDER, null);
		//中上
		g.drawImage(Img.WINDOW, x+BORDER, y, x+w-BORDER, y+BORDER, BORDER, 0,WINDOW_W-BORDER , BORDER, null);
		//右上
		g.drawImage(Img.WINDOW, x+w-BORDER, y, x+w, y+BORDER, WINDOW_W-BORDER, 0,WINDOW_W , BORDER, null);
		//左中
		g.drawImage(Img.WINDOW, x, y+BORDER, x+BORDER, y+h-BORDER, 0 , BORDER,BORDER ,WINDOW_H - BORDER, null);
		//中中
		g.drawImage(Img.WINDOW, x+BORDER, y+BORDER, x+w-BORDER, y+h-BORDER, BORDER, BORDER,WINDOW_W-BORDER , WINDOW_H-BORDER, null);
		//右中
		g.drawImage(Img.WINDOW, x+w-BORDER, y+BORDER, x+w, y+h-BORDER, WINDOW_W-BORDER, BORDER,WINDOW_W , WINDOW_H-BORDER, null);
		//左下
		g.drawImage(Img.WINDOW, x, y+h-BORDER, x+BORDER, y+h, 0, WINDOW_H - BORDER,BORDER , WINDOW_H, null);
		//中下
		g.drawImage(Img.WINDOW, x+BORDER, y+h-BORDER, x+w-BORDER, y+h, BORDER, WINDOW_H - BORDER, WINDOW_W-BORDER , WINDOW_H, null);
		//右下
		g.drawImage(Img.WINDOW, x+w-BORDER, y+h-BORDER, x+w, y+h, WINDOW_W-BORDER, WINDOW_H - BORDER, WINDOW_W , WINDOW_H, null);
	}


	public void setDto(GameDto dto) {
		this.dto = dto;
	}
	/**
	 * 显示数字
	 * @param x 左上角x的坐标
	 * @param y 左上角y的坐标
	 * @param num 要显示的数字
	 * @param maxBit 数字的位数
	 * @param g  画笔工具
	 */
	protected void drawNumberLeftPad(int x,int y,int num,int maxBit,Graphics g){
		//把要打印的数字转化成字符串
		String strNum = Integer.toString(num);
		//绘制循环数字右对齐
		for (int i = 0; i < maxBit; i++) {
			//判断是否满足绘制条件
			if(maxBit - i <= strNum.length())
			{
				//获得数字在字符串的下标
				int idx = i - maxBit + strNum.length();
				//把数字一位一位
				int bit = strNum.charAt(idx) - '0';
				//绘制数字
				g.drawImage(Img.NUMBER,
						this.x + x + IMG_NUMBER_W * i,this.y + y,
						this.x + x + IMG_NUMBER_W *( i + 1),this.y + y + IMG_NUMBER_H,
						bit * IMG_NUMBER_W, 0,
						(bit+1) * IMG_NUMBER_W, IMG_NUMBER_H, null);
			}
		}

	}
	//绘制值槽
	protected void drawRect(int y,String title,String number,double percent,Graphics g){
		//绘制背景
		int rect_x = this.x + PADDING;
		int rect_y = this.y + y;
		g.setColor(Color.BLACK);
		g.fillRect(rect_x, rect_y, this.rectW, IMG_RECT_H + 4);
		g.setColor(Color.WHITE);
		g.fillRect(rect_x + 1, rect_y + 1, this.rectW - 2, IMG_RECT_H + 2);
		g.setColor(Color.BLACK);
		g.fillRect(rect_x + 2, rect_y + 2, this.rectW - 4, IMG_RECT_H);
		g.setColor(Color.GREEN);
		//求出宽度
		int w = (int)(percent * (this.rectW - 4));
		//求出颜色
		int subIdx = (int)(percent * IMG_RECT_W) - 1;
		//绘制值槽
		g.drawImage(Img.RECT,
				rect_x + 2, rect_y + 2,
				rect_x + 2 + w , rect_y + 2 + IMG_RECT_H,
				subIdx, 0,
				subIdx + 1, IMG_RECT_H,
				null);
		g.setColor(Color.WHITE);
		g.setFont(DEF_FONT);
		g.drawString(title, rect_x, rect_y+22);
		if(number != null)
		{
			g.drawString(number, rect_x + 232, rect_y+22);
		}
	}
	/**
	 * 绘制正中心图
	 */
	protected void drawImageAtCenter(Image img,Graphics g){
		int imgW = img.getWidth(null);
		int imgH = img.getHeight(null);
		g.drawImage(img, this.x + (this.w - imgW >> 1), this.y + (this.h - imgH >> 1), null);
	}
	/**
	 * 刷新游戏具体内容
	 *
	 * @param g
	 */
	abstract public void paint(Graphics g);
}
