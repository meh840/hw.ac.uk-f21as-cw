package uk.ac.hw.F21AS.GROUPms256as294pt45.Core;
//@author Mehdi Seddiq (ms256)
public class BaggageDetails {
	public double weight, length, width, height;//dimensions in cm
	public double fee;
	public static final double FREE_DIM=190.0; //unit: cm, inspired from: British Airways
	public static final double FREE_WEIGHT=32.0; //unit: kg, inspired from: British Airways
	/*
	Constructor of BaggageDetails class.
	*/
	public BaggageDetails(double weight, double length, double width, double height) {
		this.weight=weight;
		this.length=length;
		this.width=width;
		this.height=height;
}

	public double Weight(){
		return weight;
	}
	
	public double Volume(){
		return (length*width*height);
	}
	
	public double Fee(){
		double maxDim, fee=0;
		maxDim=Math.max(length,width);
		maxDim=Math.max(maxDim,height);
		if (length>FREE_DIM){
			fee+=0.20*(length-FREE_DIM);
		}
		if (width>FREE_DIM){
			fee+=0.20*(width-FREE_DIM);
		}		
		if (height>FREE_DIM){
			fee+=0.20*(height-FREE_DIM);
		}		
		if (weight>FREE_WEIGHT){
			fee+=8.0*(weight-FREE_WEIGHT);
		}
		return fee;
	}
}
