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

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
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

public class OpeningState extends BasicGameState implements RenderCallback {

	private static final String VIDEO_FILE = "resources/video/intro/chV6DSBeI7k.mp4";

	private MediaPlayerFactory mediaPlayerFactory;
	private DirectMediaPlayer mediaPlayer;
	private ByteBuffer buffer;

	private int texture;
	private int stateId;

	private final Logger logger = LoggerFactory.getLogger(OpeningState.class);

	public OpeningState(int id) {
		this.stateId = id;
	}

	@Override
	public void init(final GameContainer container, final StateBasedGame sb)
			throws SlickException {
		initVideoRequirements();

		Input i = container.getInput();

		i.addKeyListener(initKeyListener(sb));
		i.addMouseListener(initMouserListener(sb));
		mediaPlayer.addMediaPlayerEventListener(initVideoListener(sb));

	}

	@Override
	public void leave(final GameContainer container, final StateBasedGame sb) {
		Input i = container.getInput();

		i.removeAllKeyListeners();
		i.removeAllMouseListeners();
		stopVideo();
	}

	private void initVideoRequirements() {
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
		mediaPlayer.playMedia(VIDEO_FILE);
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

			}

			@Override
			public void mousePressed(int arg0, int arg1, int arg2) {
				sb.enterState(GameState.MAIN_MENU_STATE.ordinal());
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

	private void stopVideo() {

		mediaPlayer.stop();
		mediaPlayer.release();
		mediaPlayerFactory.release();
		mediaPlayerFactory = null;
		mediaPlayer = null;
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
				sb.enterState(GameState.MAIN_MENU_STATE.ordinal());
			}

			@Override
			public void keyReleased(int arg0, char arg1) {
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
				sb.enterState(GameState.MAIN_MENU_STATE.ordinal());
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