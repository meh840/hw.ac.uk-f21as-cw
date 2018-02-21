/**
 * 
 */
package uk.ac.hw.F21AS.GROUPms256as294pt45.Junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.hw.F21AS.GROUPms256as294pt45.Core.Flight;
//@author Mehdi Seddiq (ms256)

public class SummaryGUITest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		double totalWeight, totalVolume, totalFeeAccumulated;
		String s1="FLN200", s2="Edinburgh", s3="Lufthansa", s4="",s5="",s6="",s7="",s8="",s9="";
		int i1=150;
		double d1=2000.0, d2=100.0;
		Flight flight1 = new Flight(s1, s2 , s3, i1, d1, d2);
		flight1.AddToWeight(20.0);
		flight1.AddToVolume(10.0);
		flight1.AddToFees(50.0);
		totalWeight=flight1.TotalWeight();
		totalVolume=flight1.TotalVolume();
		totalFeeAccumulated=flight1.TotalFeesPaid();
		s4=Integer.toString(i1);
		s5=Double.toString(d1);
		s6=Double.toString(d2);
		s7=Double.toString(totalWeight);
		s8=Double.toString(totalVolume);
		s9=Double.toString(totalFeeAccumulated);
		String expected1 = s1+s2+s3+s4+s5+s6+s7+s8+s9;
		System.out.println("expected text to be compared visually: \n"+expected1);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
