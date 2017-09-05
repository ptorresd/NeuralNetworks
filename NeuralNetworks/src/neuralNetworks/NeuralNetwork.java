package neuralNetworks;

public class NeuralNetwork {
	NeuronLayer[] layers;
	double[] input;
	
	public NeuralNetwork(int[] layersSize,int inputSize) {
		layers=new NeuronLayer[layersSize.length];
		layers[0]=new NeuronLayer(layersSize[0],inputSize);
		for(int i=1;i<layersSize.length;i++) {
			layers[i]=new NeuronLayer(layersSize[i],layersSize[i-1]);
		}
	}
	
	public void feed(double[] input) {
		layers[0].feed(input);
		for(int i=1;i<layers.length;i++) {
			double[] output=layers[i-1].getOutput();
			layers[i].feed(output);
		}
	}
	
	public void backPropagation(double[] expectedOutput) {
		double[] output=last().getOutput();
		double[] error=new double[output.length];
		for(int i=0;i<output.length;i++) {
			error[i]=expectedOutput[i]-output[i];
		}
		last().propagate(error);;
		for(int i=layers.length-2;i>-1;i--) {
			error=layers[i+1].error();
			layers[i].propagate(error);
		}
	}
	
	public void adjustWeights(double[] input) {
		first().adjustWeights(input);
		for(int i=1;i<layers.length;i++) {
			double[] output=layers[i-1].getOutput();
			layers[i].adjustWeights(output);
		}
	}
	
	public void train(double[] input, double[] expectedOutput) {
		feed(input);
		backPropagation(expectedOutput);
		adjustWeights(input);
	}
	
	public NeuronLayer last() {
		return layers[layers.length-1];
	}
	
	public NeuronLayer first() {
		return layers[0];
	}
	
	public double[] getOutput(){
		return last().getOutput();
	}
	
	public static void main(String[] args) {
		double[] input1= {1,1,0,0};
		double[] input2= {1,0,1,0};
		double[] output1= {1,0,0,1};
		double[] output2= {0,1,1,0};
		
		NeuralNetwork red=new NeuralNetwork(new int[]{4,5,2},2);
		
		int epochs=10000;
		for(int i=0;i<epochs;i++) {
			for(int j=0;j<4;j++) {
				red.train(new double[]{input1[j], input2[j]},new double[]{output1[j], output2[j]});
			}
		}
		for(int i=0;i<4;i++) {
			red.feed(new double[]{input1[i], input2[i]});
			double[] output=red.getOutput();
			for(double d:output) {
				System.out.print(d+" ");
			}
			System.out.println("");
		}
	}
}
