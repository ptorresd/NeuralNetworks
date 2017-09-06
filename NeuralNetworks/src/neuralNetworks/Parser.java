package neuralNetworks;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
	ArrayList<double[]> expOutput;
	ArrayList<double[]> input;
	
	public Parser(String dir){
		BufferedReader reader = null;
		input=new ArrayList<>();
		expOutput=new ArrayList<>();
		String line;
		try {
		    reader = new BufferedReader(new FileReader(dir));
		    line=reader.readLine();
		    
		    while(line!=null) {
		    	String[] splLine=line.split(",");
		    	double[] lineInput =new double[113];
		    	for(int i=0;i<113;i++){
		    		lineInput[i]=Double.parseDouble(splLine[4+i]);
		    	}
		    	input.add(lineInput);
		    	expOutput.add(new double[]{Double.parseDouble(splLine[0])}); //tanSigmoid
		    	//if(splLine[0].equals("1"))expOutput.add(new double[]{1,0});  //sigmoid
		    	//else expOutput.add(new double[]{0,1});
			    line=reader.readLine();
		    }
			;
		    reader.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	
	public double[][] getInput(){
		double[][] inputt=new double[input.size()][];
		for(int i=0;i<input.size();i++)inputt[i]=input.get(i);
		
		return  inputt;
		
	}
	
	public double[][] getOutput(){
		double[][] output=new double[expOutput.size()][];
		for(int i=0;i<expOutput.size();i++)output[i]=expOutput.get(i);
		
		return  output;
	}
	
	public static void main(String[] args){
		Parser p=new Parser("C:/Users/Pablo/git/RN/src/redesNeuronales/dota2Train.csv");
		p.getOutput();
	}
}
