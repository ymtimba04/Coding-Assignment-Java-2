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
public class TwitterFeed {
    // declare variables within the class scope
    private List<String> userLines;
    private List<String> tweetLines;
    private static List<String> userList;
    private static ArrayList<HashMap<String, String>> followers = new ArrayList<HashMap<String,String>>();
    private static Iterator userIterator;
    private static Iterator tweetIterator;
    
    /*
    * This method reads the twitter user file using buffered reader mechanism
    * @param input - the user file path
    * 
    * @throws FileNotFoundException, IOException
    *
    * @return userLines - returns tweet list
    */
    protected List<String> userReader(File input) throws FileNotFoundException, IOException {
        // initiate tweet list
        userLines = new ArrayList<String>();
        
        // initiate buffered reader for file data reading
        BufferedReader bReader = new BufferedReader(new FileReader(input));
        String line;
        // read the lines from the user file
        while ((line = bReader.readLine()) != null){
            userLines.add(line);
        }
        bReader.close();
            
        // return user list for processing and display
        return userLines;
    }
    
    /**
    * This method reads the tweet file using buffered reader mechanism
    * @param input - the tweet file path
    *
    * @throws FileNotFoundException, IOException
    *
    * @return tweetLines - returns tweet list
    */
    protected List<String> tweetReader(File input) throws FileNotFoundException, IOException {
        // initiate tweet list
        tweetLines = new ArrayList<String>();
        
        // initiate buffered reader for file data reading
        BufferedReader bReader = new BufferedReader(new FileReader(input));
        String line;
        // read the lines from the tweet file
        while ((line = bReader.readLine()) != null){
            tweetLines.add(line);
        }
        
        // close buffered reader stream
        bReader.close();
        
        // return tweet list for processing and display
        return tweetLines;
    }
    
    /**
    * This method processes all the tweets based on who follows who and display tweet feed accordingly
    * @param userInput  - the user file path
    * @param tweetInput - the user file path
    *
    * @throws FileNotFoundException, IOException
    *
    * @returns void
    */
    public static void display(File userInput, File tweetInput)throws FileNotFoundException, IOException{
        // instantiate the TwitterFeed class
        TwitterFeed objTwitterFeed = new TwitterFeed();
        
        // read the file list from "user.txt"
        List<String> userLinesData = objTwitterFeed.userReader(userInput);
        
        // imitiate iterator through user line data from the user file
        userIterator = userLinesData.iterator();
        
        // declare a user list variable to store cleaned user list
        userList = new ArrayList<String>();
        
        // iterate through the data from the user file to process it
        while(userIterator.hasNext()){
            // read the list from the user file
            String user = (String) userIterator.next();
            
            // split the user data read from the file by the word "follows"
            String[] userSplited = user.trim().split("follows");
            
            // check if user is not on the list and if not, add the user to the list
           if(!(userList.contains(userSplited[0].trim()))){
                userList.add(userSplited[0].trim());
           }
           
           // split the user followers by comma
            String[] followersList = userSplited[1].trim().split(",");
            
            // loop through splited user followers list
            for(int i = 0; i < followersList.length; i++){
                // check if a follower is also on the twitter user list, if not add the follower as a user
                if(!(userList.contains(followersList[i].trim()))){
                    userList.add(followersList[i].trim()); 
                }
                
                // declare a hash map variable to hold user followers
                HashMap<String, String> map  = new HashMap<String, String>();
                
                // add follower to hash map
                map.put(userSplited[0].trim() + "_", followersList[i].trim());
                
                // fill in the followers list
                followers.add(map);
            }
           
        }
        
        // sort the user list alphabetically
        Collections.sort(userList);
        
        // loop through the users and print each user's tweets plus the user's followers tweets
        for(int i = 0; i < userList.size(); i++){
            // open the tweet file for reading
            List<String> tweetLinesData = objTwitterFeed.tweetReader(tweetInput);
            
            // populate iterator with tweet data
            tweetIterator = tweetLinesData.iterator();
            
            // print user name
            System.out.println(userList.get(i));
            
            // loop through all tweets and display the ones relevant to the user
            while(tweetIterator.hasNext()){
                // a bollean variable to check whether a tweet has been printed
                boolean printed = false;
                
                // read the tweet
                String tweet = (String) tweetIterator.next();
                
                // seperate the tweet by greater than sign to get the user and the tweet
                String[] tweetSplited = tweet.trim().split(">");
                
                // check if current user has a tweet
                if(tweetSplited[0].trim().equals(userList.get(i))){
                    System.out.println("\t@" + userList.get(i) + ": " + tweetSplited[1].trim());
                    printed = true;
                }
                // check if the user's followers have tweets to print
                else{
                    // current read tweet has not been printed
                    if(!printed){
                       // if you need to find the string associated with the user in followers list
                        for (HashMap<String, String> items : followers) {
                            // check if the current user has a follower with the tweet to print and if yes, print it
                            if(items.containsKey(userList.get(i).trim() + "_") && items.containsValue(tweetSplited[0].trim())){
                                System.out.println("\t@" + tweetSplited[0].trim() + ": " + tweetSplited[1].trim());
                                break;
                            }
                        } 
                    }
                }
            }
        }
    }
}
