package neuralNetworks;

public class NeuronLayer {
	
	Neuron[] neurons;
	
	public NeuronLayer(int layerSize,int inputSize) {
		Neuron[] neurons=new Neuron[layerSize];
		for(int i=0;i<layerSize;i++) {
			neurons[i]=new Neuron(inputSize);
		}
		this.neurons=neurons;
	}
	
	public NeuronLayer(Neuron[] neurons) {
		this.neurons =neurons;
	}
	
	public void feed(double[] input) {
		for(Neuron n:neurons) {
			n.feed(input);
		}
	}
	
	public int getSize() {
		return neurons.length;
	}
	
	public double[] getOutput() {
		double[] output=new double[getSize()];
		for(int i=0;i<getSize();i++) {
			output[i]=neurons[i].getOutput();
		}
		return output;
	}
	
	public void propagate(double[] error) {
		for(int i=0;i<neurons.length;i++) {
			Neuron n=neurons[i];
			n.setDelta((1-n.getOutput())*n.getOutput()*error[i]);
		}
	}
	
	public double[] error() {
		double[] errors=new double[neurons[0].getWeights().length];
		for(Neuron n:neurons) {
			double[] weights=n.getWeights();
			for(int i=0;i<errors.length;i++) {
				errors[i]+=n.getDelta()*weights[i];
			}
		}
		return errors;
	}
	
	public void adjustWeights(double[] input) {
		for(Neuron n:neurons) {
			n.adjustWeights(input);
		}
	}
	
	
	
}
