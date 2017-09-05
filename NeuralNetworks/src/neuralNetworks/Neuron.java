package neuralNetworks;

public class Neuron {

	protected double[] weights;
	protected double bias;
	protected double output;


	protected double delta;
	protected static double c=5;

	public double getOutput() {
		return output;
	}

	public double getDelta() {
		return delta;
	}
	
	public void setDelta(double delta) {
		this.delta= delta;
	}
	
	public double[] getWeights() {
		return weights;
	}


	public double getBias() {
		return bias;
	}

	public Neuron(){
		this(new double[0],0);
	}
	
	public Neuron(int size) {
		weights=new double[size];
		for(int i=0;i<size;i++) {
			weights[i]=Math.random();
		}
		bias=1;
	}
	
	public Neuron(double[] weights,double bias){
		this.weights=weights;
		this.bias=bias;
	}
	
	public void feed(double[] input){
		double res=0;
		for(int i=0;i<weights.length;i++) {
			res+=weights[i]*input[i];
		}
		output= 1/(1+Math.exp(-res-bias));
	}
	
	
	public void adjustWeights(double[] input) {
		for(int i=0;i<weights.length;i++){
			weights[i]=weights[i]+c*input[i]*delta;
		}
		bias=bias+c*delta;
	}
	
	
	public static void main(String[] args) {
		
	}
}
