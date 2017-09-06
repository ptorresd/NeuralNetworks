package neuralNetworks;



import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Plotter extends Application{

    @Override public void start(Stage stage) {
        stage.setTitle("JPlot");

        BorderPane pane = new BorderPane();

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_LEFT);

        
        NeuralNetwork red=new NeuralNetwork(new int[]{25,50,50,1},113);  // aqui se puede configurar la distribucion de la NN, modificando el arreglo

		Parser p=new Parser("dota2Train.csv");
		double[][] trainInput=p.getInput();
		double[][] trainOutput=p.getOutput();
		
		Parser t=new Parser("dota2Test.csv");
		
		//double[][] testInput=t.getInput();          //para testear con casos distintos a los del entrenamiento
		//double[][] testOutput=t.getOutput();		  //descomentar estas lineas y comentar las de abajo 
		double[][] testOutput=trainOutput;
		double[][] testInput=trainInput;
		
		
		int epochs=1000;                       
		int paso=10;				//con que paso se miden los indicadores, en epochs
		
		int casosPrueba=1000;       //aqui se puede modificar con cuantos casos se quiere correr la prueba
									//para correr todos los casos cambiar el 1000 por testInput.length
		
		double[] x=new double[epochs/paso];
		double[] error=new double[epochs/paso];
		double[] accuracy=new double[epochs/paso];
		
		for(int i=1;i<=epochs;i++) {
			for(int j=0;j<1000;j++) {     //aqui se puede modificar con cuantos casos se quiere entrenar la red
										  //para entrenar con todos los casos cambiar el 1000 por trainInput.length
				red.train(trainInput[j], trainOutput[j]);
			}
			if(i%paso==0){
				System.out.println("epoch:"+i);
				int correctas=0;
				for(int j=0;j<casosPrueba;j++) { 
					red.feed(testInput[j]);
					double[] output=red.getOutput();
					double err=NeuralNetwork.error(output,testOutput[j]);
					if(err<1)correctas++;
					error[i/paso-1]+=err;
				}
				accuracy[i/paso-1]=correctas*100/casosPrueba;
				error[i/paso-1]=error[i/paso-1]/casosPrueba;
				x[i/paso-1]=i;
				System.out.println("correctas:"+correctas);
				
			}
		}
        
        pane.setRight(buildPlot(x,error,"Error vs Epochs", "Epochs", "Error"));
        pane.setLeft(buildPlot(x,accuracy,"Accuracy vs Epochs","Epochs","Accuracy"));
        

        Scene scene  = new Scene(pane,800,600);

        stage.setScene(scene);
        stage.show();
    }

    protected Node buildPlot(double[] xx,double[] yy,String title,String xLabel,String yLabel) {
    	LinePlot<Number, Number> lineplot = new LinePlot<>(new NumberAxis(), new NumberAxis());
    	Double[] x2=new Double[xx.length];
    	Double[] y2=new Double[yy.length];
    	for(int i=0;i<xx.length;i++){
    		x2[i]=xx[i];
    		y2[i]=yy[i];
    	}
        List<Number> x = Arrays.asList(x2);
        List<Number> y = Arrays.asList(y2);

        lineplot.setTitle(title);
        lineplot.setXLabel(xLabel);
        lineplot.setYLabel(yLabel);
        lineplot.addSeries(x, y);
		return lineplot.getPlot();
	}

	public static void main(String[] args) {
        launch(args);
    }
}
