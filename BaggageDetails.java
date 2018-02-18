package uk.ac.hw.F21AS.GROUPms256as294pt45.Core;

public class BaggageDetails {
	public double weight, length, width, height;
	public double fee;
	public static final double FREE_DIM=0.90;
	public static final double FREE_WEIGHT=32.0;
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
		if (maxDim>FREE_DIM){
			fee+=40.0*(weight-FREE_WEIGHT);
		}
		if (weight>FREE_WEIGHT){
			fee+=8.0*(weight-FREE_WEIGHT);
		}
		return fee;
	}
}
