/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author valer
 */
public class jsontest4 {
    
    public static void main(String[] args) {
            
           
                    
                    
                
                Path currentRelativePath = Paths.get("");
                 Path path2 = currentRelativePath.toAbsolutePath();
                System.out.println(path2);
    
}
}