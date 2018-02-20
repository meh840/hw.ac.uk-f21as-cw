package uk.ac.hw.F21AS.GROUPms256as294pt45.JUnit;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BaggageDetailsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BaggageDetails baggageinfo1=new BaggageDetails(FREE_WEIGHT,0,0,0);
		BaggageDetails baggageinfo2=new BaggageDetails(0,0,0,FREE_DIM);
		BaggageDetails baggageinfo3=new BaggageDetails(FREE_WEIGHT+1.0,0,0,0);
		BaggageDetails baggageinfo4=new BaggageDetails(0,0,0,FREE_DIM+1);
	}


	@Test
	public void test() {
	}

	@Test
	public void testFee() {
		static final double DELTA_FEE=0.1; 
		String error1 = "Non-zero Fee for Weight in Free Weight range \n";
		double expected1=0;
		double actual1=baggageinfo1.Fee();
		assertEquals(error1, expected1, actual1,DELTA_FEE);
		
		String error2 = "Non-zero Fee for Dimensions in Free Dimesions range \n";
		double expected2=0;
		double actual2=baggageinfo2.Fee();
		assertEquals(error2, expected2, actual2,DELTA_FEE);
		
		String error3 = "Wrong Fee for Weight \n";
		double expected3=8.0;
		double actual3=baggageinfo3.Fee();
		assertEquals(error3, expected3, actual3,DELTA_FEE);
		
		String error4 = "Wrong Fee for Dimensions \n";
		double expected4=40.0;
		double actual4=baggageinfo4.Fee();
		assertEquals(error4, expected4, actual4,DELTA_FEE);		
	}

	
}
