package simulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import distance.HammingDistance;
import distance.JukesCantorDistance;

//this class represents the simulator
public class Simulator {

	private final char[] NUCLEOTIDES = "ACGT".toCharArray();
	private final double MUTATION_RATE;
	private final double RECOMBINATION_RATE;
	private final int RECOMBINATION_FRAGMENT_LENGTH;
	private final int MAX_DIFFS;
	private List<Sequence> sequences;
	private int POP_SIZE;
	private int SEQ_SIZE;
	private int GENERATIONS;
	private StringBuffer statistics = new StringBuffer();

	public Simulator(String fasta, double mr, double rr, int rfl, int generations) {

		this.sequences = readFastaFile(fasta);
		this.MUTATION_RATE = mr;
		this.RECOMBINATION_RATE = rr;
		this.RECOMBINATION_FRAGMENT_LENGTH = rfl;
		this.MAX_DIFFS = ((POP_SIZE - 1) * POP_SIZE / 2) * SEQ_SIZE;
		this.GENERATIONS = generations;

	}

	//reads a fasta file and map each sequence in the file to a Sequence
	private List<Sequence> readFastaFile(String fasta) {
		try (BufferedReader br = new BufferedReader(new FileReader(new File(fasta + ".fasta")))) {

			List<Sequence> seqs = new ArrayList<>();
			while (br.ready()) {
				String name = br.readLine();
				String seq = br.readLine();
				Sequence sequence = new Sequence(seq);
				sequence.name = name.substring(1);
				seqs.add(sequence);
				this.POP_SIZE++;
				this.SEQ_SIZE = seq.length();
			}
			return seqs;

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	public void runWithStats() {
		Random random = new Random();
		for (int i = 1; i <= GENERATIONS; i++) {
			//mutate with random value
			sequences.forEach(seq -> {	mutate(seq, random.nextDouble());});
			//recombine with a new random value
			sequences.forEach(seq -> {	recombine(seq, random.nextDouble());});

			//Hamming and Jukes-Cantor Model Statistics
			computeStatistics(i);
		}
		
		//Printing statistics into file CSV
		String filename = "seq" + SEQ_SIZE + "-" + "pop" + POP_SIZE + "-" + "mr" + MUTATION_RATE + "-" + "rr"
				+ RECOMBINATION_RATE + "-" + "rl" + RECOMBINATION_FRAGMENT_LENGTH + "-" + "gen" + GENERATIONS;
		try (PrintWriter pw = new PrintWriter(new File(filename + "-statistics.csv"))) {
			pw.println(statistics);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

	}

	public void run() {
		Random random = new Random();
		for (int i = 1; i <= GENERATIONS; i++) {
			//mutate with random number
			sequences.forEach(seq -> { mutate(seq, random.nextDouble());});
			//recombine with new random number
			sequences.forEach(seq -> { recombine(seq, random.nextDouble());});
		}
	}

	public void computeStatistics(int generation) {

		StringBuffer sb = new StringBuffer();

		HammingDistance hd = new HammingDistance();
		JukesCantorDistance jcd = new JukesCantorDistance();

		//calculating % of different sites
		int diffs = 0;
		for (int j = 0; j < sequences.size() - 1; j++) {
			Sequence a = sequences.get(j);
			for (int k = j + 1; k < sequences.size(); k++) {
				Sequence b = sequences.get(k);
				diffs += hd.getDistance(a, b);
			}
		}
		double p = ((double) diffs) / MAX_DIFFS;
		//only calculate JC if the % is less than 0.75
		if (p < 0.75){
			double jukes = jcd.getDistance(p);
			sb.append(generation + "," + p + "," + jukes + "\n");
		}
		else
			sb.append(generation + "," + p + "\n");

		statistics.append(sb);
	}

	//generate fasta file
	public void generateFasta(String name, Sequence... initialSeqs) {
		if (name == null) {
			name = "seq" + SEQ_SIZE + "-" + "pop" + POP_SIZE + "-" + "mr" + MUTATION_RATE + "-" + "rr"
					+ RECOMBINATION_RATE + "-" + "rl" + RECOMBINATION_FRAGMENT_LENGTH + "-" + "gen" + GENERATIONS;
		} 
		try (PrintWriter pw = new PrintWriter(new File(name + ".fasta"))) {
			for(Sequence s:initialSeqs){
				pw.println(s);
			}
			sequences.forEach(seq -> pw.println(seq));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public Sequence mutate(Sequence seq, double mr) {
		if (mr <= MUTATION_RATE) {
			char[] sequence = seq.sequence;
			Random random = new Random();
			//choose random position in sequence
			int position = random.nextInt(seq.size());
			char oldNucleotide = sequence[position];
			char newNucleotide = NUCLEOTIDES[random.nextInt(NUCLEOTIDES.length)];
			//while mutation results in the same nucleotide, calculate again
			while (oldNucleotide == newNucleotide)
				newNucleotide = NUCLEOTIDES[random.nextInt(NUCLEOTIDES.length)];
			sequence[position] = newNucleotide;

		}
		return seq;
	}

	public Sequence recombine(Sequence seq, double rr) {
		if (rr <= RECOMBINATION_RATE) {
			char[] sequence1 = seq.sequence;
			Random random = new Random();
			//random position
			int position = random.nextInt(seq.size() - RECOMBINATION_FRAGMENT_LENGTH);
			//get random sequence
			Sequence seq2 = sequences.get(random.nextInt(sequences.size()));
			//while the random sequence is equal to the sequence that is being recombined, get new Sequence
			while (seq.equals(seq2))
				seq2 = sequences.get(random.nextInt(sequences.size()));
			char[] sequence2 = seq2.sequence;

			//perform recombination of length rfl
			for (int i = position; i < position + RECOMBINATION_FRAGMENT_LENGTH; i++)
				sequence1[i] = sequence2[i];

		}
		return seq;
	}

	
	public List<Sequence> getSequences() {
		return sequences;
	}
}