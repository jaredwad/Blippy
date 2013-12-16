package blippy.system;

import java.io.*;

import java.net.*;

/**
 * Gets resources as streams or strings.
 *
 * @author Brother Neff
 */
public class ResourceGetter
{
   /**
    * The path prefix for property-like resources.
    * The leading slash ('/') guarantees that the
    * cResourcesPathPrefix lookup will work regardless
    * of deployment scenario.
    *
    * However, for command-line invocation, the project home
    * directory, or current directory (".") if connected to
    * the project home directory, must be in the CLASSPATH.
    * That is the directory where the "resources" subdirectory
    * is located.
    */
   public static final String cResourcesPathPrefix = "/resources/properties/";

   /**
    * Encapsulates getting a resource given its string basename.
    *
    * @param pName the string basename of the resource.
    *
    * @return a File based on the resource URL.
    */
   public static File getResourceAsFile(String pName)
   {
      return getResourceAsFile(ResourceGetter.class, pName);
   }

   /**
    * Encapsulates getting a resource given its string basename.
    *
    * @param pClass the class of the resource (defaults to this class).
    *
    * @param pName the string basename of the resource.
    *
    * @return a File based on the resource URL.
    */
   public static File getResourceAsFile(Class pClass, String pName)
   {
      File file = null;

      try
      {
         file = new File(new URL(getResource(pClass, pName)).getPath());
      }
      catch (Exception e)
      {
         System.err.println(e);
      }

      return file;
   }

   /**
    * Encapsulates getting a resource given its string basename.
    *
    * @param pName the string basename of the resource.
    *
    * @return an input stream opened on the resource URL.
    */
   public static InputStream getResourceAsStream(String pName)
   {
      return getResourceAsStream(ResourceGetter.class, pName);
   }

   /**
    * Encapsulates getting a resource given its string basename.
    *
    * @param pClass the class of the resource (defaults to this class).
    *
    * @param pName the string basename of the resource.
    *
    * @return an input stream opened on the resource URL.
    */
   public static InputStream getResourceAsStream(Class pClass, String pName)
   {
      InputStream inputStream = null;

      try
      {
         inputStream = new URL(getResource(pClass, pName)).openStream();
      }
      catch (Exception e)
      {
         System.err.println(e);
      }

      return inputStream;
   }

   /**
    * Encapsulates getting a resource given its string basename.
    *
    * @param pName the string basename of the resource.
    *
    * @return a String external form of the resource URL.
    */
   public static String getResource(String pName)
   {
      return getResource(ResourceGetter.class, pName);
   }

   /**
    * Encapsulates getting a resource given its class and its string basename.
    *
    * @param pClass the class of the resource (defaults to this class).
    *
    * @param pName the string basename of the resource.
    *
    * @return a String external form of the resource URL.
    */
   public static String getResource(Class pClass, String pName)
   {
      String externalForm = "Sorry---Resource_Not_Found---" + pName;

      String resourceFilename = (pName.startsWith("/") ? pName :
                                 cResourcesPathPrefix + pName);
      try
      {
         externalForm = 
            pClass.getResource(resourceFilename).toExternalForm();
      }
      catch (Exception e)
      {
      }

      return externalForm;
   }
}
