package wang.relish.tetris.ui;

import java.awt.Graphics;

public class LayerDisk extends LayerData {
	
	public LayerDisk(int x,int y,int w,int h)
	{
		super(x,y,w,h);
	}
	public void paint(Graphics g)
	{
		this.creatWindow(g);
		this.showData(Img.DISK,this.dto.getDiskRecode(), g);
	}

}
