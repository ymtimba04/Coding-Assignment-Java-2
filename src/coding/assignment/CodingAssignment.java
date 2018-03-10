/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coding.assignment;

import java.util.*;
import java.io.*;

/**
 *
 * @author Yandisa Mtimba
 * @version 1.0
 * @since 2018-03-05
 */
public class CodingAssignment {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // check if the number of arguments meet the criteria
        if(args.length == 2){
            try{
                // process and display the tweets
                new CodingAssignment().go(args[0], args[1]);
            }
            catch(FileNotFoundException fe){
                fe.printStackTrace();
            }
            catch(IOException ie){
                ie.printStackTrace();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            System.out.println("The program is expecting 2 file paths arguments and you have supplied " + args.length);
        }
    }
    /**
    * This method is the program entry point
    * @param userFilePath  - the user file path
    * @param tweetFilePath - the user file path
    *
    * @throws FileNotFoundException, IOException
    *
    * @return void
    */
    public void go(String userFilePath, String tweetFilePath) throws FileNotFoundException, IOException{
        // instantiate a user file object from the passed argument
        File userFile = new File(userFilePath);

        // instantiate a tweet file object from the passed argument
        File tweetFile = new File(tweetFilePath);

        // check if user file paths
        if(!userFile.exists()){
            System.out.println("Invalid user file path: " + userFilePath);
        }

        // validate  file name to be "user.txt"
        if(!userFile.getName().toLowerCase().equals("user.txt")){
            System.out.println("Invalid file name, the expected first argument file name is user.txt");
        }

        // check if tweet file path exists
        if(!tweetFile.exists()){
            System.out.println("Invalid tweet file path: " + tweetFilePath);
        }

        // validate file name to be "tweet.txt"
        if(!tweetFile.getName().toLowerCase().equals("tweet.txt")){
            System.out.println("Invalid file name, the expected second argument file name is tweet.txt");
        }

        // process and display the tweets
        TwitterFeed.display(userFile, tweetFile);
    }
    
}
