package neuralNetworks;

public class NeuralNetwork {
	NeuronLayer[] layers;
	
	public NeuralNetwork(int[] layersSize,int inputSize) {  		//el constructor recibe un arreglo indicando la cantidad de neuronas 
		layers=new NeuronLayer[layersSize.length];					//en cada hidden layer y output layer
		layers[0]=new NeuronLayer(layersSize[0],inputSize);			//y un entero indicando la cantidad de inputs
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
	
	public static double error(double[] output,double[] expOutput){
		double err=0;
		for(int i=0;i<output.length;i++){
			err+=Math.pow(output[i]-expOutput[i],2);
		}
		return err;
	}
	
	
	public static void main(String[] args) {
		
		NeuralNetwork red=new NeuralNetwork(new int[]{25,50,50,1},113); //esto nos entrega una red con la configuracion
																		// 113,25,50,50,1
		Parser p=new Parser("dota2Train.csv");
		double[][] trainInput=p.getInput();
		double[][] trainOutput=p.getOutput();
		
		Parser t=new Parser("dota2Test.csv");
		double[][] testInput=t.getInput();
		double[][] testOutput=t.getOutput();
		
		int epochs=1000;
		System.out.println(trainInput.length);
		for(int i=0;i<epochs;i++) {
			if(i%10==0){
				System.out.println("epoch:"+i);
				int correctas=0;
				int mCorrectas=0;
				for(int j=0;j<testInput.length;j++) {
					red.feed(testInput[j]);
					double[] output=red.getOutput();
					double err=error(output,testOutput[j]);
					if(err<0.1)correctas++;
					if(err<1)mCorrectas++;
				}
				System.out.println("correctas:"+correctas);
				System.out.println("mas o menos correctas:"+mCorrectas);
				
			}
			for(int j=0;j<90000;j++) {
				red.train(trainInput[j], trainOutput[j]);
			}
		}
	}
}
