package ikor.util;

// Title:       Resource Loader
// Version:     1.2
// Copyright:   1999
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

import java.io.*;

import java.awt.Image;
import java.awt.Toolkit;

import java.util.PropertyResourceBundle;

import javax.swing.ImageIcon;

// Version 1.0: Apr'99 byte[] & Image
// Version 1.1: Jun'99 ImageIcon
// Version 1.2: Dic'99 ResourceBundle

public class Loader
{
  /**
   *  Image Loader:
   *  loadImage (getClass(), file);
   */

  public static Image loadImage (final Class baseClass, String file)
  	throws IOException
  {
    // img = Toolkit.getDefaultToolkit().getImage ( file );

    byte[] imageData = loadResource(baseClass,file);
    Image  img = Toolkit.getDefaultToolkit().createImage(imageData);

    return img;
  }

  /**
   *  ImageIcon Loader:
   *  loadImageIcon (getClass(), file);
   */

  public static ImageIcon loadImageIcon (final Class baseClass, String file)
  	throws IOException
  {
    byte[] iconData = loadResource(baseClass,file);

    return new ImageIcon(iconData);
  }

  /**
   * Copy resource into a byte array.
   *
   * This is necessary because several browsers consider Class.getResource
   * a security risk because it can be used to load additional classes.
   * Class.getResourceAsStream just returns raw bytes, which we can convert to anything.
   */

  public static byte[] loadResource(final Class baseClass, String file)
  	throws IOException
  {
    byte[] buffer = null;

    try {

      InputStream resource = baseClass.getResourceAsStream(file);

      if (resource != null) {
          
          BufferedInputStream   in  = new BufferedInputStream(resource);
          ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
          
          buffer = new byte[1024];
          
          int n;
          
          while ((n = in.read(buffer)) > 0) {
              out.write(buffer, 0, n);
          }
          
          in.close();
          out.flush();
          
          buffer = out.toByteArray();
          
          if (buffer.length == 0) {
             System.err.println("Warning: " + file + " is zero-length");
          }

      } else {
         System.err.println(baseClass.getName() + "/" + file + " not found.");        
      }


    } catch (IOException ioe) {

       System.err.println(ioe.toString());
       throw (ioe);
    }

    return buffer;
  }

  /**
   * Load resource
   */

  public static PropertyResourceBundle getResourceBundle (final Class baseClass, String file)
  	throws IOException
  {
    PropertyResourceBundle rb;
    BufferedInputStream    in;

    try {

      InputStream resource = baseClass.getResourceAsStream(file);

      if (resource == null) {

         System.err.println(baseClass.getName() + "/" + file + " not found.");
         return null;
      }

      in = new BufferedInputStream(resource);

      rb = new PropertyResourceBundle( in );

      in.close();

    } catch (IOException ioe) {

      System.err.println(ioe.toString());
      
      throw (ioe);
    }

    return rb;
  }
}