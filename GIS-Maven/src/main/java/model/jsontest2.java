/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.*;

public class jsontest2 {

	public static void main(String[] args) {
            
                try{
                    
                    
                
                Path currentRelativePath = Paths.get("");
                 Path path2 = currentRelativePath.toAbsolutePath();
                System.out.println(path2);
		
		FileHandler wrkJSON = new FileHandler("C:/Users/valer/OneDrive/Documents/NetBeansProjects/GISapp/build/user3.json");
		
		User currentUser = wrkJSON.getUserData("user1.com");
		
		System.out.println(currentUser.toString());
		
		User oldUser = new User("user1.com","user1","What is your favorite country?","Australia",0);
                
                User newUser = new User("user1.com","user11","What is your favorite country?","Australia",0);
		//User newUser = new User("user4.com","user4","Where were you born","Italy",1);
		
		//POI newPOI = new POI("TD Stadium",2,"TD second washroom",2,"Washroom in second floor",true,new Vec2i(3,4));
		
		//oldUser.addToList(newPOI);
		
		//newUser.addToList(newPOI);
		
		wrkJSON.UpdateUser(oldUser, newUser);
                }
                catch(MetadataFileException e){
        
            System.out.println(e.getMessage());
        }
	}

}
