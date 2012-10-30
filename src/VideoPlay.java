
import java.nio.ByteBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;
import uk.co.caprica.vlcj.player.direct.RenderCallback;

import com.sun.jna.Memory;
 
public class VideoPlay implements RenderCallback {
	
	private static final int WIDTH = 1280, HEIGHT = 544;
	
	private MediaPlayerFactory mediaPlayerFactory;
    private DirectMediaPlayer mediaPlayer;
    
    private int texture;
    
    private ByteBuffer buffer = null;
    
    public static void main(String[] argv)
	{
		new VideoPlay().updateLoop();
	}
	private static final String VIDEO_FILE = "resources/video/intro/intro.mp4";

	public VideoPlay()
	{
		System.setProperty("jna.library.path", "vlib");

		initGL();
		
    	if (isMac())
    		mediaPlayerFactory = new MediaPlayerFactory(new String[] {"--no-video-title-show", "--vout=macosx"});
	    else
	    	mediaPlayerFactory = new MediaPlayerFactory(new String[] {"--no-video-title-show"});
    	
        mediaPlayer = mediaPlayerFactory.newDirectMediaPlayer("RGBA", WIDTH, HEIGHT, WIDTH * 4, this);
        mediaPlayer.playMedia(VIDEO_FILE);
        Display.setTitle("VLCJ - LWJGL - "+mediaPlayer.getMediaMeta().getTitle());
    }

 
	private void initGL() {
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
			//Display.setVSyncEnabled(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}          
 
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
 
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
 
		glEnable(GL_TEXTURE_2D);     
		
		texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, WIDTH, HEIGHT, 0, GL_RGB, GL_UNSIGNED_BYTE, (ByteBuffer)null);
	}
    
    public static boolean isMac()
    {
	    return (System.getProperty("os.name").toLowerCase().indexOf( "mac" ) >= 0); 
	}
	 
	public void updateLoop()
	{
		while (!Display.isCloseRequested())		{
			
			glClear(GL_COLOR_BUFFER_BIT);
			
			glBindTexture(GL_TEXTURE_2D, texture);
			if(buffer != null){
				glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, WIDTH, HEIGHT, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			}
			
			glBegin(GL_QUADS);
			{
				glTexCoord2f(0, 0);
				glVertex2f(0, 0);
				
				glTexCoord2f(1, 0);
				glVertex2f(WIDTH, 0);
				
				glTexCoord2f(1, 1);
				glVertex2f(WIDTH, HEIGHT);
				
				glTexCoord2f(0, 1);
				glVertex2f(0, HEIGHT);
			}
			glEnd();
 
			Display.update();
		}
		
		mediaPlayer.stop();
  	  	mediaPlayer.release();
  	  	mediaPlayerFactory.release();
  	  	mediaPlayerFactory = null;
  	  	mediaPlayer = null;
 
		Display.destroy();
	}
	
	@Override
	public void display(Memory memory)
	{
		buffer = memory.getByteBuffer(0, WIDTH * HEIGHT * 4);
	}
}