package Tetris;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	/**
	 * Load an image from a path, divide this image into subImages, 
	 * and return an array filled by these subImages.
	 * 
	 * @param path
	 * 		The path of the Image.
	 * @param width
	 * 		The width of a subImage.
	 * @return
	 * 		An array of the subImages.
	 * @throws IOException
	 */
	public static Image[] loadImage(String path, int width) throws IOException{
		BufferedImage load = ImageIO.read(ImageLoader.class.getResource(path));
		Image[] images = new Image[load.getWidth() / width];
		for(int i = 0; i < images.length; i++){
			images[i] = load.getSubimage(i * width, 0, width, width);
		}
		return images;
	}
	
	/**
	 * Return an image loaded from a path.
	 * 
	 * @param path
	 * 		The path of the Image.
	 * @return
	 * 		The Image.
	 * @throws IOException
	 */
	public static Image loadImageFond(String path) throws IOException{
		Image load = ImageIO.read(ImageLoader.class.getResource(path));	
		return load;
	}
}
