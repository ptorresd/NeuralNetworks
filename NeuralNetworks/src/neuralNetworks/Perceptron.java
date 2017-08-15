package neuralNetworks;


public class Perceptron {
	protected double[] weights;
	protected double bias;
	protected static double c=0.01;
	
	public double[] getWeights() {
		return weights;
	}

	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	public double getBias() {
		return bias;
	}

	public void setBias(double bias) {
		this.bias = bias;
	}
	
	public Perceptron(){
		this(new double[0],0);
	}
	
	public Perceptron(int size) {
		this(new double[size],1);
	}
	
	public Perceptron(double[] weights,double bias){
		this.weights=weights;
		this.bias=bias;
	}
	
	public int calcular(double[] input){
		double res=0;
		for(int i=0;i<weights.length;i++) {
			res+=weights[i]*input[i];
		}
		if(res+bias>0)return 1;
		else return 0;
	}
	
	public void train(double[] input,int resEsperada) {
		int res=calcular(input);
		double lrate=0;
		if(res<resEsperada)lrate=c;
		if(res>resEsperada)lrate=-c;
		for(int i=0;i<weights.length;i++) {
			weights[i]=weights[i]+lrate*input[i];
		}
	}
	
	public static void main(String[] args) {
		int trainCases=10000;
		double a=Math.random()*100-50;
		double b=Math.random()*100-50;
		Perceptron myLittlePerceptron=new Perceptron(2);
		for(int i=0;i<trainCases;i++) {
			double x=Math.random()*100-50;
			double y=Math.random()*100-50;
			int res=0;
			if(y>a*x+b)res=1;
			myLittlePerceptron.train(new double[]{x,y},res);
		}
		int correctas=0;
		for(int i=0;i<trainCases;i++) {
			double x=Math.random()*100-50;
			double y=Math.random()*100-50;
			int res=0;
			if(y>a*x+b)res=1;
			if(myLittlePerceptron.calcular(new double[]{x,y})==res)correctas++;
		}
		System.out.print(correctas);
		
	}
}