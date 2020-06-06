import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.sql.Timestamp;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.Date;


public class firstA {

  //read from a file C:\Users\bekad\Documents\Java 3500\firstA\conf
  // file name is firstA.conf

  //read file name from firstA conf
  // display the file name
  public static void main(String[] args) throws IOException {

    String dataFile;
    Scanner user = new Scanner(System.in);
    String inputFileName = "";
    String outputFileName = "";
    String logFileName = "";
    int aLine = 0;


    System.out.println("Working Directory = " + System.getProperty("user.dir"));

    //System.out.print("firstA.conf");
//    fname = user.nextLine().trim();
    // File relativeFile = new File("/sample-documents/pdf-sample.pdf");
    File configFile = new File(System.getProperty("user.dir") + "/conf/firstA.conf");
    Scanner scan = new Scanner(configFile);


    //prepare the output file
    System.out.print(configFile + "\n");
// validating input and output file names 
    try {
      FileReader fReader = new FileReader(configFile);
      BufferedReader bReader = new BufferedReader(fReader);

      int lineNum = 0;
      while ((dataFile = bReader.readLine()) != null) {
        String[] parts = dataFile.split("\\=");
        lineNum++;

        if (parts[0].trim().equals("inputFileName")) {
          inputFileName = parts[1].trim();
          System.out.println("input file: " + inputFileName + " lineNum: " + lineNum);
        } else if (parts[0].trim().equals("outputFileName")) {
          outputFileName = parts[1].trim();
          System.out.println("output file: " + outputFileName + " lineNum: " + lineNum);
        } else if (parts[0].trim().equals("logFileName")) {
          logFileName = parts[1].trim();
          System.out.println("log file name: " + logFileName + " lineNum: " + lineNum);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("error reading file named ' " + configFile + " ' ");
    }
    // another try and catch to read the file and move the records with A to the 
    // output file

    // initializing the logger
    SimpleDateFormat formatter = new SimpleDateFormat("_YYYYMMdd_HHmmss");
    Logger logger = Logger.getLogger(firstA.class.getName());
    FileHandler fileHandler = new FileHandler(System.getProperty("user.dir") + "/log/" + logFileName +
            formatter.format(Calendar.getInstance().getTime()) + ".log");
    fileHandler.setFormatter(new SimpleFormatter());

    logger.addHandler(fileHandler);
    //logger init complete

    logger.info(new Timestamp(new Date().getTime()).toString() + " Starting to process " + inputFileName);

    try {


      //finds the file and prepares the path so the program can find it
      File inputFilePath = new File(System.getProperty("user.dir") + "/data/" + inputFileName);
      Scanner scan1 = new Scanner(inputFilePath);
      File outputFilePath = new File(System.getProperty("user.dir") + "/data/" + outputFileName);

      FileReader fReader = new FileReader(inputFilePath);
      BufferedReader bReader = new BufferedReader(fReader);
      BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
      int lineNum = 0;
      logger.info(new Timestamp(new Date().getTime()).toString() + " Start to process loop ");

      while ((dataFile = bReader.readLine()) != null) {
        String[] parts = dataFile.split("\\,");
        lineNum++;

        if (parts[1].substring(0, 1).equals("A")) {
          // System.out.println("second field: " + parts[1] + " lineNum: " + lineNum);
          writer.write(dataFile + "\n");
          aLine++;
        }

      }
      writer.close();
      System.out.println("lines processed: " + lineNum);
      System.out.println(("lines written: " + aLine));
      logger.info(new Timestamp(new Date().getTime()).toString() + " Finished writing into " + outputFileName);
      logger.info(new Timestamp(new Date().getTime()).toString() + " lines processed: " + lineNum);
      logger.info(new Timestamp(new Date().getTime()).toString() + " lines written:  " + aLine);

    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("error reading file named ' " + inputFileName + " ' ");
      logger.severe(new Timestamp(new Date().getTime()).toString() + " error processing file named " + inputFileName);

    }

    logger.info(new Timestamp(new Date().getTime()).toString() + " application complete");

  }

}
