import simulator.SequenceGenerator;
import simulator.Simulator;

public class Q1 {

	public void Q1Test() {

		final int GENERATIONS = 5000;
		final int SEQ_SIZE = 100;
		final int POP_SIZE = 100;
		final double MR = 0.01;
		final double RR = 0.01;
		final int RFL = 5;
		String filename = "seq-input";
		
		//generating input fasta file with the above specified properties
		if(new SequenceGenerator().generateSequences(filename, SEQ_SIZE, POP_SIZE)){
			
			//creating simulator
			Simulator simulator = new Simulator(filename, MR, RR, RFL, GENERATIONS);
			//running simulator with statistics - hamming and JC model - outputs a CSV
			simulator.runWithStats();
			//generate output fasta file
			simulator.generateFasta("seq-output");
		
		} else {
			System.out.println("Error while generating sequences!!");
		}
	}

}
