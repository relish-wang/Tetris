package wang.relish.tetris.ui;
import java.awt.Graphics;


public class LayerAbout extends Layer {
	/**
	 * 姓名图片的宽度
	 */
	private static final int NAME_W = Img.NAME.getWidth(null);
	/**
	 *
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public LayerAbout(int x,int y,int w,int h)
	{
		super(x,y,w,h);
	}
	public void paint(Graphics g)
	{
		this.creatWindow(g);
		g.drawImage(Img.NAME,this.x + (this.w - NAME_W >> 1),this.y + PADDING ,null);
	}

}
