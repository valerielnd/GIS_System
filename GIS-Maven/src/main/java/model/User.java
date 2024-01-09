/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
* <h1>User</h1>
*This class store data of user, getter and setter method can add and retrieve data 
*most variables will be final so it can not be change after construction
*there are 5 variables in user class, -email, -password, -security question, -security answer, -user type
*there are 2 data structure to hold user's external data (POI favorite, POI custom)
*<br><br>
* Grades are added with the {@link addGrade} method and an average can
* be calculated with the {@link calcAvg} method.<br><br>
*
* <b>Example Use:</b>
* <pre>
* {@code
* 
* User user1 = new User("someone@uwo.ca", "mypassword", "What is this", "a dog", false);
* System.out.println(user1.getEmail());
* System.out.println(user1.getPassWord());
* System.out.println(user1.getSecQuestion());
* System.out.println(user1.getSecAnswer());
* System.out.println(user1.isDeveloper());
* 
*
* </pre>
*
* <b>Example Output:</b> <code>someone@uwo.ca</code><br>
* <b>Example Output:</b> <code>mypassword</code><br>
* <b>Example Output:</b> <code>What is this</code><br>
* <b>Example Output:</b> <code></code>a dog<br>
* <b>Example Output:</b> <code></code>false<br>
*
*
*
*
*
* @author Cynthia Du
 * @author Irmene-Valerie Leonard 
 * @author John Curran Krupa 
 * @author Thai An Luong 
 * @author Tyler James Johnson
* @version 3
* @since   2022-11-11
*/
import java.util.ArrayList;

public class User {
	
        /**
	 *the email that will serve as part of the user credentials to log into
	 *the application. Once set. this cannot be changed 
	 */
	private String email;
	
        /**The password set by the user to access the application*/
	private String password;
	
        /**
	*The security question chosen by the user to reset a forgotten
        *password. Once set, this cannot be changed.
	*/
	private String secQuestion;
	
        /**
	*The security answer provided by the user to reset a forgotten
        *password. Once set, this cannot be changed
        */
	private String secAnswer;
	
        /**The user type, regular or developer 0 for user, 1 for developer*/
	private int userType;
	
        /**The list of the user's POIs*/
	private ArrayList<POI> POIList;
	
	
        /**
	* Constructor for the class User
	*@param password the user password
	*@param	email the email of the user when sign up
	*@param question the question that user pick when sign up
	*@param answer the answer for the security question
	*@param	type identify if user is developer of not, true for dev, false for regular user
	*/
	public User(String email, String password, String question, String answer, int type) {
		
		this.email = email;
		
		this.password = password;
		
		this.secQuestion = question;
		
		this.secAnswer = answer;
		
		this.userType = type;
		
		this.POIList = new ArrayList<POI>();
	}

        /**
	* Getter method to retrieve the email
	* 
	* @return return the email of the user
	*/
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

        /**
	* Getter method to retrieve the user's password
	* 
	* @return return the string of password
	*/
	public String getPassword() {
		return password;
	}

        /**
	 * setter method for pass
	 * @param password the pass word to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

        /**
	* Getter method to retrieve the security question
	* 
	* @return return String of security question
	*/
	public String getSecQuestion() {
		return secQuestion;
	}

        
	public void setSecQuestion(String secQuestion) {
		this.secQuestion = secQuestion;
	}


        /**
	* Getter method to retrieve security answer
	* 
	* @return return String of the answer
	*/
	public String getSecAnswer() {
		return secAnswer;
	}


        /**
         * Sets a user security answer
         * @param secAnswer the user security answer
         */
	public void setSecAnswer(String secAnswer) {
		this.secAnswer = secAnswer;
	}

        /**
         * Method to get a user type
         * @return 0 if the user is normal, 1 if the user is developer
         */
	public int getUserType() {
		return userType;
	}

        /**
	* Getter method to retrieve the list POI
	* 
	* @return return array list type POI
	*/
	public ArrayList<POI> getPOIs(){
		return POIList;
	}

        /**
	* method to add a new favorite POI into the list
	* @param poi the item need to add in the FAV list with type POI class 
        * @return true if the POI is added, false otherwise
	*/
	public Boolean addToList(POI poi) {
		if(poi != null){
                    POIList.add(poi);
                    return true;
                }
                return false;
	}
        
        /**
         * Sets the user type
         * @param userType the integer type 0f the user, 0 for normal, 1 for developer
         */
	public void setUserType(int userType) {
		this.userType = userType;
	}
        
        /**
	 * setter method to set the user list of POIs
	 * @param list the list of POIs
	 */
        public void setPOIList(ArrayList<POI> list){
            this.POIList = list;
        }
	
        /**
         * To test is two users are equal
         * @param otherUser the user to compare this user to
         * @return true if they are equal, false otherwise
         */
	public Boolean isEqual(User otherUser) {
		
		return this.email.equals(otherUser.getEmail());
	}
	
        /**
         * To print the information about a user
         * @return the string containing the user information
         */
        @Override
	public String toString() {
		
		String type;
		
		if(this.userType == 0) {
			
			type = "Normal";
			
		}
		else {
			
			type = "Dev";
		}
		String userInfo = "email:" + this.email + "\n" + "user type:" + type;
		
		return userInfo;
	}
        
        /**
	* true false method to check if the user is dev or user
	* true (1) for dev
	* false (0) for user
	* 
	* @return return true false
	*/
        public Boolean isDeveloper(){
        
            return userType != 0;
        }
        
        /**
	* method to remove a favorite POI out of the FAV list
	* @param poiToRemove an item with POI type class
        * @throws EmpytListException if the POI list is empty
	*/
	public void removePOI(POI poiToRemove) throws EmpytListException{
            
            if(!this.POIList.isEmpty()){
                this.POIList.remove(poiToRemove);
            }
            else{
                throw new EmpytListException("List of POIs is empty");
            
            }
		
        }

}

