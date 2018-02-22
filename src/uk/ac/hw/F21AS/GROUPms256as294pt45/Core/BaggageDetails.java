package src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core;
/*
 * Contains the baggage information: Weight, Length, Width and Height
 * Weight in kg
 * Dimensions in cm
 * @author Mehdi Seddiq (ms256)
 */

public class BaggageDetails {
	public double weight, length, width, height;//dimensions in cm
	public double fee;
	//All dimensions are in cm, free of charge values are inspired from British Airways
	public static final double FREE_LENGTH=190, FREE_WIDTH=75, FREE_HEIGHT=65;
	public static final double FREE_WEIGHT=32.0; //Weight is in kg, free of charge weight inspired from British Airways
	
	/*
	Constructor of BaggageDetails class.
	*/
	public BaggageDetails(double weight, double length, double width, double height) {
		this.weight=weight;
		this.length=length;
		this.width=width;
		this.height=height;
}
	//gives the weight
	public double Weight(){
		return weight;
	}
	
	//Calculates and return the Volume
	public double Volume(){
		return (length*width*height);
	}
	
	//Calculates the fee due to extra weight or longer dimensions
	//length, width and height have different values for the free of charge limits 
	public double Fee(){
		double L, W, H, fee=0, temp;  //L: the greatest dimension, H: the smallest dimension, W: the dimension in the middle  
		L=length; W=width; H=height;
		if (W>L){
			temp=L; L=W; W=temp;
		}
		if (H>L){
			temp=L; L=W; W=temp;
		}
		if (H>W){
			temp=H; H=W; W=temp;
		}
		//now L>=W>=H
		if (length>FREE_LENGTH){
			fee+=0.20*(length-FREE_LENGTH);
		}
		if (width>FREE_WIDTH){
			fee+=0.20*(width-FREE_WIDTH);
		}		
		if (height>FREE_HEIGHT){
			fee+=0.20*(height-FREE_HEIGHT);
		}		
		if (weight>FREE_WEIGHT){
			fee+=8.0*(weight-FREE_WEIGHT);
		}
		return fee;
	}
	
	//returns a String containing baggage information
	@Override
	public String toString() {
		return "" + weight + length + width + height;
	}
}
