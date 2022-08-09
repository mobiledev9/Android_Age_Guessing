package technologies.akkas.ageguess.ui;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import technologies.akkas.ageguess.GlobalClass;

import static com.facebook.FacebookSdk.getApplicationContext;
import static technologies.akkas.ageguess.ui.BabyBirthdayActivity.isLeapYear;

/**
 * 
 * @author Thomas Peralta
 * @category Date and Time
 * @problem How to calculate age?
 * 
 */

public class AgeGuruu {
	
	public static int guessingAge = -1;   //added a global variable to be used later
    public static int birthYear = 0;
	public static  GlobalClass globalVariable;


	public static  ArrayList<Integer> dataSet=new ArrayList<Integer>();
	
	@RequiresApi(api = Build.VERSION_CODES.O)
	public  void main (String args []){

		System.out.println("Please guess person's age:");
		String s1 = null;
		String monthString = null;
		String dayString = null;
		int day = -1;
		int month = -1;
		
		try{
		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    String s = bufferRead.readLine();
		    s1 = s;
		    guessingAge = Integer.parseInt(s1);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		
		int value1 = -1;
		int value = -1;
		
		try {
			value = Integer.parseInt(s1);
		}
		catch (NumberFormatException e)
		{
			System.out.println("Please try a valid input. You entered: " + s1);
		}
		
		value1 = value - 4;
		
		int value2 = value + 4;
		
		System.out.println("Starting Range is: " + value1 + "   Ending Range is: " + value2);
		
		System.out.println();
		
		System.out.println("Enter Month:");
		
		try{
		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    String s = bufferRead.readLine();
		    monthString = s;
	 
		    System.out.println("You Entered: " + s);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		try {
			month = Integer.parseInt(monthString) - 1;
		}
		catch (NumberFormatException e)
		{
			System.out.println("Please try a valid input. You entered: " +  monthString);
		}
		
		System.out.println();
		
		System.out.println("Enter Day:");
		
		try{
		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    String s = bufferRead.readLine();
		    dayString = s;
	 
		    System.out.println("You Entered: " + s);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		try {
			day = Integer.parseInt(dayString);
		}
		catch (NumberFormatException e)
		{
			System.out.println("Please try a valid input. You entered: " + dayString);
		}
		
	    System.out.println();
		
		System.out.println("The birthdate you entered was: " + (month+1) + "/" + day + "/" + getYear(month + 1, day, guessingAge)); 
        System.out.println();
		calculate(month, day,value1, value2);	
	}
	
		
   public static void calculate(int month, int day, int value1, int value2)
		{

			globalVariable = (GlobalClass) getApplicationContext();
			guessingAge = globalVariable.getGuessingAge();

			int GoodAnswer = 0;
			
			int whattoSub = 7;
			 
			int answer = calculateAge(month + 1, day, value1, value2);
			
			GoodAnswer = answer - whattoSub;
			
			int[] testAge = expectedBirth(month + 1, day, getYear(month + 1, day, guessingAge));
			
			Calendar birthdayNoYear = Calendar.getInstance();
		    birthdayNoYear.set(testAge[2], testAge[0], testAge[1]);

			LocalDate todayNoYear = null;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				todayNoYear = LocalDate.now();
			}
			LocalDate birthdayFormat2 = LocalDate.of(testAge[2], testAge[0], testAge[1]);

			Period pNoYear = null;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				pNoYear = Period.between(birthdayFormat2, todayNoYear);
			}

			Calendar birthdayNoYear3 = Calendar.getInstance();

			LocalDate todayNoYear3 = LocalDate.now();

            int[] testAge3 = expectedBirth(month + 1, day, getYear(month + 1, day, answer));

			birthdayNoYear3.set(testAge3[2], testAge3[0], testAge3[1]);

			LocalDate birthdayFormat3 = LocalDate.of(testAge3[2], testAge3[0], testAge3[1]);
		    
		    Period pNoYear3 = Period.between(birthdayFormat3, todayNoYear3);
		    
		    Calendar birthdayNoYear4 = Calendar.getInstance();

			int[] testAge4 = expectedBirth(month + 1, day, getYear(month + 1, day, GoodAnswer));

		    birthdayNoYear4.set(testAge4[2], testAge4[0], testAge4[1]);

			LocalDate todayNoYear4 = LocalDate.now();
	        
		    LocalDate birthdayFormat4 = LocalDate.of(testAge4[2], testAge4[0], testAge4[1]);

		   Period pNoYear4 = Period.between(birthdayFormat4, todayNoYear4);
		    
	       if (guessingAge <= 7)
			{
		      globalVariable.setYearYouAre(pNoYear.getYears()+"");
		      globalVariable.setMonthYouAre(pNoYear.getMonths()+"");
		      globalVariable.setDaysYouAre(pNoYear.getDays()+"");
		      System.out.println("The age should be: " + pNoYear.getYears() + " years, " + pNoYear.getMonths() + " months, and " + pNoYear.getDays() + " days old.");
		      System.out.println();

		      System.out.println("The birthdate should be: " + testAge[0] + "/" + testAge[1] + "/" + testAge[2]);
		      globalVariable.setBirthGuessDate(testAge[0] + "/" + testAge[1] + "/" + testAge[2]);
			}
			
			// a = answer b = GoodAnswer and c = guessingAge
				
	        else if (Math.abs(guessingAge - answer) < Math.abs(guessingAge - GoodAnswer)) 
			{
				globalVariable.setYearYouAre(pNoYear3.getYears()+"");
				globalVariable.setMonthYouAre(pNoYear3.getMonths()+"");
				globalVariable.setDaysYouAre(pNoYear3.getDays()+"");

	      	   System.out.println("The age should be: " + pNoYear3.getYears() + " years, " + pNoYear3.getMonths() + " months, and " + pNoYear3.getDays() + " days old."); 	
	           System.out.println();

	           System.out.println("The birthdate should be: " + testAge3[0] + "/" + testAge3[1] + "/" + testAge3[2]);
	           globalVariable.setBirthGuessDate(testAge3[0] + "/" + testAge3[1] + "/" + testAge3[2]);

			}
				
			else
				   {

					   globalVariable.setYearYouAre(pNoYear4.getYears()+"");
					   globalVariable.setMonthYouAre(pNoYear4.getMonths()+"");
					   globalVariable.setDaysYouAre(pNoYear4.getDays()+"");

				   System.out.println("The age should be: " + pNoYear4.getYears() + " years, " + pNoYear4.getMonths() + " months, and " + pNoYear4.getDays() + " days old.");	
			       System.out.println();

			       System.out.println("The birthdate should be: " + testAge4[0] + "/" + testAge4[1] + "/" + testAge4[2]);
			       globalVariable.setBirthGuessDate(testAge4[0] + "/" + testAge4[1] + "/" + testAge4[2]);
				   }  
			
      		//ask

	}

   
   public static int calculateAge(int month, int day, int value1,int value2)
			{
				int counter = day % 7;
				
				while (counter < value1 && counter < value2)
				{
					
					counter += 7;
					
				}
								
				int thisYear = new Date().getYear() + 1900;
				int lastyearUsed = 0;
				int yearCount = 0;
				
				for (int x = 2004; x <= thisYear; x += 7) {
					
					lastyearUsed = 7 * yearCount + 2004;
					yearCount++;
					
				}
				
			    int whattoAdd = thisYear - lastyearUsed;   
				
			    int answer = counter + whattoAdd;
				
			    Calendar today = Calendar.getInstance();
				
				int todayMonth = today.get(Calendar.MONTH) + 1;  
				//Java months go from 0 to 11
				
				int todayDayOfMonth = today.get(Calendar.DAY_OF_MONTH);
				
				if ((month > todayMonth)){
				        answer--;
				
				}
				
				else if ((month == todayMonth) && (day > todayDayOfMonth)) 
				{
			        answer--;
			    }
				
				return answer;
			
		}
   
   
    public static int getYear(int month, int day, int age) 
   {
	   int answer = -1;
	   int thisYear = new Date().getYear() + 1900;
	   
	   answer = thisYear - age;
	   
	   Calendar today = Calendar.getInstance();
		
	   int todayMonth = today.get(Calendar.MONTH) +1;  
	   //Java months go from 0 to 11
	   
	   int todayDayOfMonth = today.get(Calendar.DAY_OF_MONTH);
	   
	   if (age == 0) {
		   
		   return thisYear;
		   
	   }
		
	   else if ((month > todayMonth))
	   {
		        answer --;
		
	   }
		
	   else if ((month == todayMonth) && (day > todayDayOfMonth))
		{
	        answer --;
	    }
		
		return answer;       
	   
   }
   
   
public static int[] expectedBirth(int month, int day, int year)
   {
	  int answer[] = new int[3];
	  int answer2[] = new int[3];
      int ageYear = -1;
      int ageNoYear = -1;
	  int value = -1;
      int day2 = day;
      int yearUp = year;
	  int fixMonthUp = month;
	  int expectedDayUp = day;
      int testYear = (year % 7) + 7;
     
         if (month == 2 && day == 29 && isLeapYear(year) && isLeapYear(testYear) == false) {
		  
		 while (isLeapYear(testYear) == false) {
			 
			 testYear = testYear + 7;
			 
		 }
		 
       }
         
       if (month == 2 && day2 == 29 && isLeapYear(year) == false) {
    	   
    	   day2 = 1;
    	   month++;
    	   
    	   fixMonthUp++;
    	   expectedDayUp = 1;
    	   
       }
	  
	  LocalDate today = LocalDate.now();
  
	  LocalDate birthdayFormat = LocalDate.of(testYear, month, day2);  
	  Period p = Period.between(birthdayFormat, today);
	  value = p.getYears();
	  ageYear = p.getYears();
  
	  int value1 = value - 4;
	  int value2 = value + 4;
	
	  ageNoYear = calculateAge(month, day2, value1, value2);
	  
	  int a = 0;
	  int b = 0;
	  
	  if (ageNoYear == ageYear || ageNoYear - 7 == ageYear) 
	   {
		   answer[0] = month; 
		   answer[1] = day;
		   answer[2] = year;
	   }
	   
	  else while (ageNoYear != ageYear) 
	     {
		   if (day2 == 0 ) 
		   {
			   
			   if (month == 11 || month == 9 || month == 8 ||month == 6 || month == 4 || month == 2 || month == 1) 
			   { 
	    			  day2 = 31;
	    			  
	    			  if (month == 1) 
	    			  {
	    				  month = 13;
	    				  year--;
	    		      }
	    			  
	    			 month--;
	    			 
			   }
	    		       // 30 days months
	    		  
	    		  else if (month == 12 || month == 10 || month == 7 || month == 5) 
	    		  {
	    			  day2 = 30;
	    			  month--;
	    			  
	    		  }
	    		  
	    		       // February
			 
			else if (month == 3 && isLeapYear(year) == false) 
			{
			       
				day2 = 28;
				month--;
				
		 
	    	    }
		   
			else if (month == 3 && isLeapYear(year)) {
				
				day2 = 29;
				month--;
				
			}
			  
		}
			   
	    ageNoYear = calculateAge (month, day2, value1, value2);
			      
		if (ageNoYear == ageYear || ageNoYear - 7 == ageYear)
		 {
			answer[0] = month;
			answer[1] = day2;
			answer[2] = year; 
			break;
		  }
				
		  day2--;
		  a++;
			 	   
	    } // ends while
     
   // check upward direction
	  
	  LocalDate birthdayFormatUp = LocalDate.of(testYear, fixMonthUp, expectedDayUp);  
	  Period pUp = Period.between(birthdayFormatUp, today);
	  int valueUp = pUp.getYears();
	  int ageYearUp = pUp.getYears();
	  
	  int valueUp1 = valueUp - 4;
	  int valueUp2 = valueUp + 4;
   
       ageNoYear = calculateAge (fixMonthUp, expectedDayUp, valueUp1, valueUp2); // reset ageNoYear
   
       if (ageNoYear == ageYearUp || ageNoYear - 7 == ageYearUp)
	    {
		
		 answer2[0] = fixMonthUp;
		 answer2[1] = expectedDayUp;
		 answer2[2] = yearUp;
	    }
   
        else while (ageNoYear != ageYearUp)
	       {    
		   
        	   ageNoYear = calculateAge (fixMonthUp, expectedDayUp, valueUp1, valueUp2);
		
		   if (ageNoYear == ageYearUp || ageNoYear - 7 == ageYearUp)
		    {
			   
			 answer2[0] = fixMonthUp;
			 answer2[1] = expectedDayUp;
			 answer2[2] = yearUp;
		     break;
		    }
	
		if (fixMonthUp == 2 && expectedDayUp == 28 && isLeapYear(year) == false)
		    {
		    	 
			    expectedDayUp = 0;
			    fixMonthUp++;
			
		     }
		
        if (fixMonthUp == 2 && expectedDayUp == 29) {
			
			expectedDayUp = 0;
		    fixMonthUp++;
			
		} 
		
	     else if (((fixMonthUp == 11 || fixMonthUp == 9 || fixMonthUp == 6 || fixMonthUp == 4)) && expectedDayUp == 30)
	        {
	    	 
		expectedDayUp = 0;
	    fixMonthUp++;
	    
	    }
	
	    else if (((fixMonthUp == 12 || fixMonthUp == 10 || fixMonthUp == 8 || fixMonthUp == 7 || fixMonthUp == 5 || fixMonthUp == 3 || fixMonthUp == 1)) && expectedDayUp == 31)
	       {
		    
	    	    expectedDayUp = 0;
		
		    if (month == 12) 
		     {
			 
		    	fixMonthUp = 1;
			yearUp++;
			
		    }
	
		    else 
		       {
			
		    	fixMonthUp++;
		    	
		    }
		
	}
	
	   expectedDayUp++;
	
	   b++;	
	
   }  // ends while
	
	   if (b <= a)
	    {
		   
		return answer2;
		
	    }
	
		return answer;
		
}
 

public static ArrayList<Integer> possibleAge(int month, int day, int year) 
   {
	   ArrayList<Integer> arrli = new ArrayList<Integer>();
	   int start = 0;
	   int end = 0;
	   int startYear = year - 4;
	   int endYear = year + 4;
	   int end2 = 0;
	   int end3 = 0;
	   int pMonth = month;
	   int pDay = day;
	   int pYear = year;
	   int[] possibleAgeList;
	   
	   if (month == 2 && day == 29 && isLeapYear(year) == false) {
		   
		   day = 1;
		   month++;
		   end = day;
		   
	   }
	   
	   if (day <= 29) 
		{
	      end = day;
		}
	   
	   if (day >= 5) 
	   {
		   start = day - 4;
	   }
	   
	   else if (day == 4)
	   {
		   start = day - 3;
		   
		   end3 = 1;
	   }
	   
	   else if (day == 3)
	   {
		   start = day - 2;
		   
		   end3 = 2;
	   }
	   
	   else if (day == 2)
	   {
		   start = day - 1;
		   
		   end3 = 3;
	   }
	   
	   else if (day == 1)
	   {
		   start = day;
		   
		   end3 = 4;
	   }
	   
		if (((pMonth == 10 || pMonth == 8 || pMonth == 5 || pMonth == 3)) && pDay > 24)
		{
			
		    if (pDay == 25 || pDay == 26) 
			 {
				 end = pDay + 4;
			 }
		    
		    else if (pDay == 27) 
			 {
				 end = pDay + 3;
				 
				 end2 = 1;
			 }
		    
		    else if (pDay == 28) 
			 {
			     end = pDay + 2;
						 
				 end2 = 2;
			 }
		    
		    else if (pDay == 29) 
			   {
				 end = pDay + 1;
						 
				 end2 = 3;
			   }
		    
		    else if (pDay == 30) 
			   {
				 end = pDay;
						 
				 end2 = 4;
				}
		}
		
		if (((pMonth == 11 || pMonth == 9 || pMonth == 7 || pMonth == 6 || pMonth == 4 || pMonth == 2 || pMonth == 0)) && pDay > 24)
		{
		
			 if (pDay == 25 || pDay == 26 || pDay == 27) 
			 {
				 end = pDay + 4;
			 }
			
		 
		else if (pDay == 28) 
		{
			end = pDay + 3;
			
			end2 = 1;
		}
		 
		else if (pDay == 29) 
		{
			end = pDay + 2;
			
			end2 = 2;
		}
		 
		else if (pDay == 30) 
		{
			end = pDay + 1;
			
			end2 = 3;
		}
		 
		else if (pDay == 31) 
		{
			end = pDay;
			
			end2 = 4;
		}
		
    }
	
	   pYear = startYear;
		
	   for (int k = startYear; k <= endYear; k++) 
	   {
	    
	   for (int x = start; x <= end; x++) 
	   { 
		possibleAgeList = expectedBirth(pMonth + 1, x, pYear);
			
			Calendar birthday = Calendar.getInstance();
		    birthday.set(possibleAgeList[2], possibleAgeList[0], possibleAgeList[1]);
	        
	        LocalDate today = LocalDate.now();
		    LocalDate birthdayFormat = LocalDate.of(possibleAgeList[2], possibleAgeList[0], possibleAgeList[1]);

		    Period p = Period.between(birthdayFormat, today);
		    
		   int ageYear = p.getYears();
		 
		if (guessingAge >= ageYear - 4 && guessingAge <= ageYear + 4) {
			 
         arrli.add(new Integer (possibleAgeList[0]));
         arrli.add(new Integer (possibleAgeList[1]));
         arrli.add(new Integer (possibleAgeList[2]));
         
		 }
   
	   }
	   
	   pYear++;
	   
	   }
	   
	   if (end2 > 0) 
	   {
		   
	   for (int k = startYear; k <= endYear; k++) 
	   {
		    
	   for (int x = start; x <= end2; x++) 
	   {
		   possibleAgeList = expectedBirth(pMonth + 1, x, pYear);
		   
		   Calendar birthday = Calendar.getInstance();
		   birthday.set(possibleAgeList[2], possibleAgeList[0], possibleAgeList[1]);
	        
	        LocalDate today = LocalDate.now();
		    LocalDate birthdayFormat = LocalDate.of(possibleAgeList[2], possibleAgeList[0], possibleAgeList[1]);

		    Period p = Period.between(birthdayFormat, today);
		    
		   int ageYear = p.getYears();
			 
		  if (guessingAge >= ageYear - 4 && guessingAge <= ageYear + 4) {
				 
	       arrli.add(new Integer (possibleAgeList[0]));
	       arrli.add(new Integer (possibleAgeList[1]));
	       arrli.add(new Integer (possibleAgeList[2]));
	       
		   }
   
	   }
	   
	   pYear++;
	   
	  } 
	}
	   
	   else if (end3 > 0) 
	   {
		   
	   for (int k = startYear; k <= endYear; k++) 
	   {

	   for (int x = start; x <= end3; x++) 
	   {
		  possibleAgeList = expectedBirth(pMonth + 1, x, pYear);
			
			Calendar birthday = Calendar.getInstance();
		    birthday.set(possibleAgeList[2], possibleAgeList[0], possibleAgeList[1]);
	        
	        LocalDate today = LocalDate.now();
		    LocalDate birthdayFormat = LocalDate.of(possibleAgeList[2], possibleAgeList[0], possibleAgeList[1]); 

		    Period p = Period.between(birthdayFormat, today);
		    
		   int ageYear = p.getYears();
			 
		   if (guessingAge >= ageYear - 4 && guessingAge <= ageYear + 4) {
			   
	       arrli.add(new Integer (possibleAgeList[0]));
	       arrli.add(new Integer (possibleAgeList[1]));
	       arrli.add(new Integer (possibleAgeList[2]));
	       
		 }
           
	   }
	   
	   pYear++;
	   
	 } 
	   
   }
	   
	  return removeDuplicates(arrli);  
	   
  }
   
   
   public static ArrayList<Integer> removeDuplicates(ArrayList<Integer> list) 
   { 
		
		ArrayList<String> oldlist = new ArrayList<>(); 
		int count=0;
		
		for(int i=0;i<list.size()/3;i++)
		{
			int val1=(int) list.get(count);
			int val2=(int) list.get(++count);
			int val3=(int) list.get(++count);
			count++;
			String dateString=(String) (val1+"/"+val2+"/"+val3);
			
			oldlist.add(dateString);
		}
 
       // Create a new ArrayList 
       ArrayList<String> newList = new ArrayList<>(); 
       ArrayList<Integer> arl = new ArrayList<>();
 
       // Traverse through the first list 
       for (String element : oldlist) { 
 
           // If this element is not present in newList 
           // then add it 
           if (!newList.contains(element)) { 
 
               newList.add(element); 
           } 
       } 
       
       for (int i=0;i<newList.size();i++) 
		 {
			 String firstDate=newList.get(i);
		
			 String [] token=firstDate.split("/");
		
	
		      arl.add(Integer.valueOf(token[0]));
		      arl.add(Integer.valueOf(token[1])); 
		      arl.add(Integer.valueOf(token[2]));
		      
		 }
       
       // return the new list 
       return arl; 
   } 
   
    public static int[] selectAge(ArrayList<Integer> list,int month,int day,int year)
	{
		ArrayList<String> newlist=new ArrayList<String>();
		
		ArrayList<String> freshList=new ArrayList<String>();
		
		for (int i=0;i<list.size();i+=3 )
		{
			String str=list.get(i)+" "+list.get(i+1)+" "+list.get(i+2);
			
			newlist.add(str);			
		}
	
		for(String element : newlist) {
			
			String [] token=element.split(" ");
			if(token[2].equals(String.valueOf(birthYear)))
			{
				
				freshList.add(element);
				
			}
		
		}
		
		 DateTimeFormatterBuilder dateTimeFormatterBuilder = new DateTimeFormatterBuilder()
		            .append(DateTimeFormatter.ofPattern( "[MM dd yyyy]" 
		 + "[M dd yyyy]"+"[M d yyyy]"+"[MM d yyyy]"+"[MM d yyyy]" 
		 + "[MM dd yyy]" +"[M dd yyy]"+"[M d yyy]"+"[MM d yyy]" 
		 + "[MM dd yy]" + "[M dd yy]"+"[M d yy]"+"[MM d yy]" + "[MM dd y]" 
		 + "[M dd y]"+"[M d y]"+"[MM d y]"));
		 
		 DateTimeFormatter dtf = dateTimeFormatterBuilder.toFormatter();
		 String inputString1 ="";
		 long  negative_days=0;
		 boolean flag=true;
		 boolean flag2=false;
		
		if (year<0)
		{
			inputString1= month+" "+day+" "+1;
			
			LocalDate date3 = LocalDate.parse(1+" "+1+" "+1, dtf);
			int temp=-year;
			LocalDate date4 = LocalDate.parse( month+" "+day+" "+temp, dtf);
			negative_days=ChronoUnit.DAYS.between(date3,date4);

			if (negative_days<0)
			{
				negative_days=-(negative_days);
			}
			
		}
		else if(year>0){
			
			inputString1 = month+" "+day+" "+year;
		}
		else {
			inputString1 = month+" "+day+" "+1;
			
			flag=false;
		}
		
		LocalDate date1 = LocalDate.parse(inputString1, dtf);
		
		int count=1;
		long mininum = 0;
		String UpdatedDate="";
		long  daysBetween=0;
		
		for(String element:freshList)
		{
			String[] splitdate=element.split(" ");
			
			if (Integer.valueOf(splitdate[2])<0)
			{
				long daysupdate=0;
				if (year==0)
				{
					LocalDate date5 = LocalDate.parse(1+" "+1+" "+1, dtf);
					LocalDate date4 = LocalDate.parse( month+" "+day+" "+1, dtf);
					long day1 = ChronoUnit.DAYS.between(date5, date4);
					
					date5 = LocalDate.parse(1+" "+1+" "+1, dtf);
					date4 = LocalDate.parse( splitdate[0]+" "+splitdate[1]+" "+1, dtf);
					long day2 = ChronoUnit.DAYS.between(date5, date4);
					
					daysBetween=day1-day2;
					
				}
				else if (year>0) {		
					
					LocalDate date5 = LocalDate.parse(month+" "+day+" "+1, dtf);
					LocalDate date4 = LocalDate.parse( splitdate[0]+" "+splitdate[1]+" "+1, dtf);
					long day2 = ChronoUnit.DAYS.between(date5, date4);
				
					if (day2<0)
					{day2=-day2;}
                   daysBetween=day2;
              
				}
				else {
					LocalDate date5 = LocalDate.parse(1+" "+1+" "+1, dtf);
					LocalDate date4 = LocalDate.parse( month+" "+day+" "+1, dtf);
					long day1 = ChronoUnit.DAYS.between(date5, date4);
					
					date5 = LocalDate.parse(1+" "+1+" "+1, dtf);
					date4 = LocalDate.parse( splitdate[0]+" "+splitdate[1]+" "+1, dtf);
					long day2 = ChronoUnit.DAYS.between(date5, date4);
					
					daysBetween=day1-day2;
				}
		
				if (daysBetween<0)
				{
					daysBetween=-(daysBetween);
				}	
			}
			else if  (Integer.valueOf(splitdate[2])>0){
							
				if (year<0  )
				{
					
					LocalDate date5 = LocalDate.parse(month+" "+day+" "+1, dtf);
					LocalDate date4 = LocalDate.parse( splitdate[0]+" "+splitdate[1]+" "+1, dtf);
					long day2 = ChronoUnit.DAYS.between(date5, date4);
		
					{day2=-day2;}
                   daysBetween=day2;
               
				}
				else if (year==0) {
					
					inputString1 = month+" "+day+" "+splitdate[2];
				    date1 = LocalDate.parse(inputString1, dtf);
				
					LocalDate date2 = LocalDate.parse(splitdate[0]+" "+splitdate[1]+" "+splitdate[2], dtf);
					
					daysBetween = ChronoUnit.DAYS.between(date1, date2);
				
				}
				else {
					LocalDate date5 = LocalDate.parse(month+" "+day+" "+year, dtf);
					LocalDate date4 = LocalDate.parse( splitdate[0]+" "+splitdate[1]+" "+splitdate[2], dtf);
					long day2 = ChronoUnit.DAYS.between(date5, date4);
					
		
					if (day2<0)
					{day2=-day2;}
                   daysBetween=day2;
				}
				
			}
			
			else if (Integer.valueOf(splitdate[2])==0){
					
				if (year==0)
				{			
		
						element=splitdate[0]+" "+splitdate[1]+" "+1;
						LocalDate date2 = LocalDate.parse(element, dtf);
						daysBetween = ChronoUnit.DAYS.between(date1, date2);
						flag=true;
						flag2=true;
				
				}
				
				else {
					LocalDate date5 = LocalDate.parse(1+" "+1+" "+1, dtf);
					LocalDate date4 = LocalDate.parse( month+" "+day+" "+1, dtf);
					long day1 = ChronoUnit.DAYS.between(date5, date4);
					
					date5 = LocalDate.parse(1+" "+1+" "+1, dtf);
					date4 = LocalDate.parse( splitdate[0]+" "+splitdate[1]+" "+1, dtf);
					long day2 = ChronoUnit.DAYS.between(date5, date4);
					
					daysBetween=day1-day2;
					flag2=true;
				}
							
			}
			if (flag2==true)
			{
				element=splitdate[0]+" "+splitdate[1]+" "+0;
				flag2=false;
			}
				
			//daysBetween=negative_days+daysBetween;
			
			 //To Check if difference is negative then make it positive
			 if (daysBetween<0)
			 {
				 daysBetween=-daysBetween;
		
			 }
			 
			if(count==1)
			{
				mininum=daysBetween;
				UpdatedDate=element;
				count++;
				
			}
			else {
				
				if(daysBetween<mininum)
				{
					mininum=daysBetween;
					 UpdatedDate=element;
				}
			}
		
		}
		
		String[] token=UpdatedDate.split(" ");
		int [] ClosestDate=new int [3];		
		ClosestDate[0]=Integer.valueOf(token[0]);
		ClosestDate[1]=Integer.valueOf(token[1]);
		ClosestDate[2]=Integer.valueOf(token[2]);
	
	return ClosestDate;		
		
	}
   
}
 


// What's Needed:

// Please don't allow invalid inputs i.e dates, months, days.
// Please make sure user choose a year from the list of possible birthdates.
//similar entries from dataSet have been removed.
			

// @Author: Thomas Peralta Finalized 1/25/2021