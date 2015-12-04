import simulator.Sequence;
import simulator.SequenceGenerator;
import simulator.Simulator;

public class Q2 {

	public void Q2Test() {
		final int SEQ_SIZE = 100;
		final int POP_SIZE = 6;
		final int RANDOM_SEQS = 2;
		final String filename = "seq-input";

		//Generate de 2 random sequences in a population of 6. Getting 2 different populations 
		SequenceGenerator sg = new SequenceGenerator();
		Sequence[] initialSeqs = sg.generateRandomSequences(filename, SEQ_SIZE, POP_SIZE, RANDOM_SEQS);
		
		//simulation1
		double mr = 0.1;
		double rr = 0.0;
		int rfl = 0;
		int generations = 1000;
		test(filename, mr, rr, rfl, generations, initialSeqs);

		//simulation2
		mr = 0.1;
		rr = 0.1;
		rfl = 5;
		generations = 1000;
		test(filename, mr, rr, rfl, generations, initialSeqs);

		//simulation3
		mr = 0.1;
		rr = 0.001;
		rfl = 5;
		generations = 1000;
		test(filename, mr, rr, rfl, generations, initialSeqs);

		//simulation4
		mr = 0.001;
		rr = 0.1;
		rfl = 5;
		generations = 1000;
		test(filename, mr, rr, rfl, generations, initialSeqs);

		//simulation5
		mr = 0.1;
		rr = 0.01;
		rfl = 5;
		generations = 2500;
		test(filename, mr, rr, rfl, generations, initialSeqs);

	}

	public void test(String filename, double mr, double rr, int rfl, int generations, Sequence... seqs) {

		//creating and running simulator
		Simulator simulator = new Simulator(filename, mr, rr, rfl, generations);
		simulator.run();
		simulator.generateFasta(null, seqs);

	}

}
