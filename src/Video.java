//
//import java.awt.Graphics2D;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
//import org.newdawn.slick.AppGameContainer;
//import org.newdawn.slick.BasicGame;
//import org.newdawn.slick.GameContainer;
//import org.newdawn.slick.Graphics;
//import org.newdawn.slick.Image;
//import org.newdawn.slick.Music;
//import org.newdawn.slick.SlickException;
//import org.newdawn.slick.util.BufferedImageUtil;
//
//import com.xuggle.mediatool.IMediaReader;
//import com.xuggle.mediatool.IMediaTool;
//import com.xuggle.mediatool.MediaToolAdapter;
//import com.xuggle.mediatool.ToolFactory;
//import com.xuggle.mediatool.event.IAudioSamplesEvent;
//import com.xuggle.mediatool.event.IVideoPictureEvent;
///*
//public class Video extends BasicGame
//{
//   private Image image;
//   private float angle;
//   static private IMediaReader reader;
//	private static final String VIDEO_FILE = "resources/video/intro/intro.mp4";
//
//   public Video ()
//   {
//      super("Slick-VideoPlayer");
//   }
//
//   /**
//    * @param args
//    */
//   public static void main(String[] args)
//   {
//      /* if (args.length < 1)
//       {
//         System.out.println(
//           "Usage: ModifyAudioAndVideo <inputFileName>");
//         System.exit(-1);
//       }*/
//
//	   
//       File inputFile = new File(VIDEO_FILE);
//       if (!inputFile.exists())
//       {
//         System.out.println("Input file does not exist: " + inputFile);
//         System.exit(-1);
//       }
//       reader = ToolFactory.makeReader(inputFile.toString());
//       reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
//
//       AppGameContainer app;
//      try
//      {
//         app = new AppGameContainer(new Video());
//         app.setDisplayMode(800, 600, false);
//         app.start();
//      }
//      catch (SlickException e)
//      {
//         e.printStackTrace();
//      }
//
//   }
//
//   @Override
//   public void init(GameContainer arg0) throws SlickException
//   {
//       IMediaTool addTimeStamp = new TimeStampTool();
//       reader.addListener(addTimeStamp);
//       image = new Image (600,500);
//      
//   }
//
//   @Override
//   public void update(GameContainer arg0, int arg1) throws SlickException
//   {
//      reader.readPacket();
//      angle = (float) (angle + 0.2);
//      if (angle > 360)
//         angle = 0;
//   }
//
//   @Override
//   public void render(GameContainer arg0, Graphics arg1) throws SlickException
//   {
//      if (image != null)
//      {
//         image.draw (0,0);
//       //  image.setRotation(angle);
//      }
//   }
//
//   public class TimeStampTool extends MediaToolAdapter
//   {
//	   @Override
//	   public void onAudioSamples(IAudioSamplesEvent e) {
//		//   e.getAudioSamples().
//	   }
//	   
//      @Override
//      public void onVideoPicture(IVideoPictureEvent event)
//       {
//         Graphics2D g = event.getImage().createGraphics();
//         try
//         {
//          if (image != null)
//            image.setTexture (BufferedImageUtil.getTexture("bla", event.getImage()));
//      }
//       catch (IOException e)
//       {
//         e.printStackTrace();
//      }
//       }
//   }
//}