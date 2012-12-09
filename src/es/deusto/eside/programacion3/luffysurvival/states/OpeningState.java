package es.deusto.eside.programacion3.luffysurvival.states;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
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
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTexSubImage2D;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jna.Memory;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;
import uk.co.caprica.vlcj.player.direct.RenderCallback;

import es.deusto.eside.programacion3.luffysurvival.LuffySurvival;
import es.deusto.eside.programacion3.luffysurvival.util.PlatformUtils;

/**
 * Video introductorio
 * @author sergio
 *
 */
public class OpeningState extends BasicGameState implements RenderCallback {

	/**
	 * Nos indica si es la primera vez que pulsa una tecla
	 */
	private static boolean firstTime = true;

	/**
	 * VIDEO_FILE: dirección del video
	 */
	private static final String VIDEO_FILE = "resources/Video/intro/intro.mp4";
	
	/**
	 * mediaPlayerFactory: da acceso al VLC
	 */
	private MediaPlayerFactory mediaPlayerFactory;
	/**
	 * mediaPlayer: controla el video
	 */
	private DirectMediaPlayer mediaPlayer;
	/**
	 *  buffer: buffer de los frames del video
	 */
	private ByteBuffer buffer;

	/**
	 * identificador de la textura en la que se renderizará el video
	 */
	private int texture;

	/**
	 * stateID: estado del programa
	 */
	private int stateId;

	/**
	 * mouseListener: listener del ratón
	 */
	private MouseListener mouseListener;
	/**
	 * keyListener: listener del teclado
	 */
	private KeyListener keyListener;

	/**
	 * isFirstTime: indica si es la primera vez que se abre
	 */
	private boolean isFirstTime;
	

	/**
	 * Constructor por defecto
	 * @param id: indica número de estado
	 */
	public OpeningState(int id) {
		this.stateId = id;
	}

	@Override
	public void enter (final GameContainer container, final StateBasedGame sb) {
		if (this.isFirstTime) {
			GL11.glPopAttrib();

		} 
		else {
			texture = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, texture);
		}
		Input i = container.getInput();

		keyListener = initKeyListener(sb);
		i.addKeyListener(keyListener);
		mouseListener = initMouserListener(sb);
		i.addMouseListener(mouseListener);
		mediaPlayer.playMedia(VIDEO_FILE);

	}
	
	@Override
	public void init(final GameContainer container, final StateBasedGame sb)
			throws SlickException {
		initVideoRequirements();
		mediaPlayer.addMediaPlayerEventListener(initVideoListener(sb));

		this.isFirstTime = true;
	}

	@Override
	public void leave(final GameContainer container, final StateBasedGame sb) {
		Input i = container.getInput();

		i.removeKeyListener(keyListener);
		i.removeMouseListener(mouseListener);
		
		stopVideo();
		if (this.isFirstTime) {
			this.isFirstTime = false;
		}
		
	}
	/**
	 * Inicializa el video
	 */
	private void initVideoRequirements() {
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);

		System.setProperty("jna.library.path", "vlib");
		if (PlatformUtils.isMac())
			mediaPlayerFactory = new MediaPlayerFactory(new String[] {
					"--no-video-title-show", "--vout=macosx" });
		else
			mediaPlayerFactory = new MediaPlayerFactory(
					new String[] { "--no-video-title-show" });

		mediaPlayer = mediaPlayerFactory.newDirectMediaPlayer("RGBA",
				LuffySurvival.WIDTH, LuffySurvival.HEIGHT,
				LuffySurvival.WIDTH * 4, this);
		texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, LuffySurvival.WIDTH,
				LuffySurvival.HEIGHT, 0, GL_RGB, GL_UNSIGNED_BYTE,
				(ByteBuffer) null);

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		
		glBindTexture(GL_TEXTURE_2D, texture);
		if (buffer != null) {
			glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, LuffySurvival.WIDTH,
					LuffySurvival.HEIGHT, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		}
			glBegin(GL_QUADS);
			{
				glTexCoord2f(0, 0);
				glVertex2f(0, 0);

				glTexCoord2f(1, 0);
				glVertex2f(LuffySurvival.WIDTH, 0);

				glTexCoord2f(1, 1);
				glVertex2f(LuffySurvival.WIDTH, LuffySurvival.HEIGHT);

				glTexCoord2f(0, 1);
				glVertex2f(0, LuffySurvival.HEIGHT);
			}
			glEnd();
		

	}

	@Override
	public void update(GameContainer container, StateBasedGame arg1, int arg2)
			throws SlickException {
	}

	@Override
	public int getID() {
		return this.stateId;
	}

	@Override
	public void display(Memory memory) {
		buffer = memory.getByteBuffer(0, LuffySurvival.WIDTH
				* LuffySurvival.HEIGHT * 4);
	}

	private MouseListener initMouserListener(final StateBasedGame sb) {
		return new MouseListener() {

			@Override
			public void setInput(Input arg0) {
			}

			@Override
			public boolean isAcceptingInput() {
				return true;
			}

			@Override
			public void inputStarted() {

			}

			@Override
			public void inputEnded() {

			}

			@Override
			public void mouseWheelMoved(int arg0) {

			}

			@Override
			public void mouseReleased(int arg0, int arg1, int arg2) {
				if(firstTime){
					sb.enterState(GameState.PREMAIN_MENU_STATE.ordinal(),  new FadeOutTransition(), new FadeInTransition());
					firstTime=false;
				}
			}

			@Override
			public void mousePressed(int arg0, int arg1, int arg2) {
			}

			@Override
			public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {

			}
		};
	}

	/**
	 * Para el video
	 */
	private void stopVideo() {

		mediaPlayer.stop();
		//mediaPlayer.release();
		//mediaPlayerFactory.release();
		//mediaPlayerFactory = null;
		//mediaPlayer = null;
	}

	private KeyListener initKeyListener(final StateBasedGame sb) {
		KeyListener kl = new KeyListener() {
			@Override
			public void inputEnded() {
			}

			@Override
			public boolean isAcceptingInput() {
				return true;
			}

			@Override
			public void setInput(Input arg0) {
			}

			@Override
			public void keyPressed(int arg0, char arg1) {
			}

			@Override
			public void keyReleased(int arg0, char arg1) {
				if(firstTime){
					sb.enterState(GameState.PREMAIN_MENU_STATE.ordinal(),  new FadeOutTransition(), new FadeInTransition());
					firstTime=false;
				}
			}

			@Override
			public void inputStarted() {

			}
		};

		return kl;
	}

	private MediaPlayerEventListener initVideoListener(final StateBasedGame sb) {
		return new MediaPlayerEventListener() {

			@Override
			public void videoOutput(MediaPlayer arg0, int arg1) {

			}

			@Override
			public void titleChanged(MediaPlayer arg0, int arg1) {
			}

			@Override
			public void timeChanged(MediaPlayer arg0, long arg1) {
			}

			@Override
			public void subItemPlayed(MediaPlayer arg0, int arg1) {

			}

			@Override
			public void subItemFinished(MediaPlayer arg0, int arg1) {

			}

			@Override
			public void stopped(MediaPlayer arg0) {
			}

			@Override
			public void snapshotTaken(MediaPlayer arg0, String arg1) {

			}

			@Override
			public void seekableChanged(MediaPlayer arg0, int arg1) {
			}

			@Override
			public void positionChanged(MediaPlayer arg0, float arg1) {
			}

			@Override
			public void playing(MediaPlayer arg0) {
			}

			@Override
			public void paused(MediaPlayer arg0) {
			}

			@Override
			public void pausableChanged(MediaPlayer arg0, int arg1) {
			}

			@Override
			public void opening(MediaPlayer arg0) {
			}

			@Override
			public void newMedia(MediaPlayer arg0) {
			}

			@Override
			public void mediaSubItemAdded(MediaPlayer arg0, libvlc_media_t arg1) {
			}

			@Override
			public void mediaStateChanged(MediaPlayer arg0, int arg1) {
			}

			@Override
			public void mediaParsedChanged(MediaPlayer arg0, int arg1) {

			}

			@Override
			public void mediaMetaChanged(MediaPlayer arg0, int arg1) {

			}

			@Override
			public void mediaFreed(MediaPlayer arg0) {

			}

			@Override
			public void mediaDurationChanged(MediaPlayer arg0, long arg1) {

			}

			@Override
			public void mediaChanged(MediaPlayer arg0, libvlc_media_t arg1,
					String arg2) {

			}

			@Override
			public void lengthChanged(MediaPlayer arg0, long arg1) {

			}

			@Override
			public void forward(MediaPlayer arg0) {
			}

			@Override
			public void finished(MediaPlayer arg0) {
				sb.enterState(GameState.PREMAIN_MENU_STATE.ordinal(),  new FadeOutTransition(), new FadeInTransition());
				
			}

			@Override
			public void error(MediaPlayer arg0) {
			}

			@Override
			public void endOfSubItems(MediaPlayer arg0) {
			}

			@Override
			public void buffering(MediaPlayer arg0, float arg1) {
			}

			@Override
			public void backward(MediaPlayer arg0) {

			}
		};
	}

}
