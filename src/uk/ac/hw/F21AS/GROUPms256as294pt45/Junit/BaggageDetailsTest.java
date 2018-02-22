package src.uk.ac.hw.F21AS.GROUPms256as294pt45.Junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.BaggageDetails;

public class BaggageDetailsTest {
	private BaggageDetails baggageInfo1, baggageInfo2, baggageInfo3, baggageInfo4, baggageInfo5;
	
	@Before
	public void setUpBeforeClass() {
		baggageInfo1 = new BaggageDetails(BaggageDetails.FREE_WEIGHT,0,0,0);
		baggageInfo2=new BaggageDetails(0,0,0,BaggageDetails.FREE_HEIGHT);
		baggageInfo3=new BaggageDetails(BaggageDetails.FREE_WEIGHT+1.0,0,0,0);
		baggageInfo4=new BaggageDetails(0,0,0,BaggageDetails.FREE_HEIGHT+1);
		baggageInfo5=new BaggageDetails(0,BaggageDetails.FREE_LENGTH+1,BaggageDetails.FREE_WIDTH+1,BaggageDetails.FREE_HEIGHT+1);
	}

	@Test
	public void testFee() {
		final double DELTA_FEE=0.1; 
		String error1 = "Non-zero Fee for Weight in Free Weight range \n";
		double expected1=0;
		double actual1=baggageInfo1.Fee();
		assertEquals(error1, expected1, actual1,DELTA_FEE);
		
		String error2 = "Non-zero Fee for Dimensions in Free Dimesions range \n";
		double expected2=0;
		double actual2=baggageInfo2.Fee();
		assertEquals(error2, expected2, actual2,DELTA_FEE);
		
		String error3 = "Wrong Fee for Weight \n";
		double expected3=8.0;
		double actual3=baggageInfo3.Fee();
		assertEquals(error3, expected3, actual3,DELTA_FEE);
		
		String error4 = "Wrong Fee for Dimensions \n";
		double expected4=0.2;
		double actual4=baggageInfo4.Fee();
		assertEquals(error4, expected4, actual4,DELTA_FEE);
		
		String error5 = "Wrong Fee for Dimensions \n";
		double expected5=0.6;
		double actual5=baggageInfo4.Fee();
		assertEquals(error5, expected5, actual5,DELTA_FEE);		
	}

	
}
