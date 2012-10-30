import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTexSubImage2D;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.BufferedImageUtil;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;
import uk.co.caprica.vlcj.player.direct.RenderCallback;

import com.sun.jna.Memory;

public class VideoSlick extends BasicGame implements RenderCallback {
	private Image image;
	private float angle;
	private static final String VIDEO_FILE = "resources/video/intro/intro.mp4";
	private static final int WIDTH = 640, HEIGHT = 480;

	private MediaPlayerFactory mediaPlayerFactory;
	private DirectMediaPlayer mediaPlayer;
	private ByteBuffer buffer;

	private int texture;

	public VideoSlick() {
		super("Slick-VideoPlayer");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		AppGameContainer app;
		try {
			app = new AppGameContainer(new VideoSlick());
			app.setDisplayMode(640, 480, false);
			app.setVSync(true);
			app.setShowFPS(false);
			app.start();
			 
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void render(GameContainer containter, Graphics g)
			throws SlickException {
		glBindTexture(GL_TEXTURE_2D, texture);
		if (buffer != null) {
			glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, WIDTH, HEIGHT, GL_RGBA,
					GL_UNSIGNED_BYTE, buffer);

		}
		
		  glBegin(GL_QUADS); { glTexCoord2f(0, 0); glVertex2f(0, 0);
		 
		 glTexCoord2f(1, 0); glVertex2f(WIDTH, 0);
		 
		 glTexCoord2f(1, 1); glVertex2f(WIDTH, HEIGHT);
		 
		  glTexCoord2f(0, 1); glVertex2f(0, HEIGHT); } glEnd();

		 
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		System.setProperty("jna.library.path", "vlib");
		if (isMac())
			mediaPlayerFactory = new MediaPlayerFactory(new String[] {
					"--no-video-title-show", "--vout=macosx" });
		else
			mediaPlayerFactory = new MediaPlayerFactory(
					new String[] { "--no-video-title-show" });

		mediaPlayer = mediaPlayerFactory.newDirectMediaPlayer("RGBA", WIDTH,
				HEIGHT, WIDTH * 4, this);
		mediaPlayer.playMedia(VIDEO_FILE);
	//	texture = glGenTextures();
		/********

		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		glEnable(GL_TEXTURE_2D);

		/*********/

		texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, WIDTH, HEIGHT, 0, GL_RGB,
				GL_UNSIGNED_BYTE, (ByteBuffer) null);
	}

	@Override
	public void update(GameContainer container, int arg1) throws SlickException {

	}

	public static boolean isMac() {
		return (System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0);
	}

	@Override
	public void display(Memory memory) {
		buffer = memory.getByteBuffer(0, WIDTH * HEIGHT * 4);
	}

}