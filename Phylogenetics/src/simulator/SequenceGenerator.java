package simulator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

//Allow to create random sequences, with specified parameters
public class SequenceGenerator {

	private final char[] NUCLEOTIDES = "ACGT".toCharArray();

	public boolean generateSequences(String filename, int seqSize, int popSize) {

		Random random = new Random();
		try (PrintWriter pw = new PrintWriter(new FileWriter(new File(filename + ".fasta")))) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < seqSize; i++) {
				char c = NUCLEOTIDES[random.nextInt(NUCLEOTIDES.length)];
				sb.append(c);
			}
			for (int j = 0; j < popSize; j++) {
				Sequence s = new Sequence(sb.toString());
				pw.println(s);

			}
			pw.flush();
			pw.close();
			return true;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public Sequence[] generateRandomSequences(String filename, int seqSize, int popSize, int randomSeqs) {
		Sequence[] seqs = new Sequence[randomSeqs];
		Random random = new Random();
		try (PrintWriter pw = new PrintWriter(new FileWriter(new File(filename + ".fasta")))) {
			for (int j = 1; j <= randomSeqs; j++) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < seqSize; i++) {
					char c = NUCLEOTIDES[random.nextInt(NUCLEOTIDES.length)];
					sb.append(c);
				}
				Sequence s = new Sequence(sb.toString());
				s.name = ">sequence" + j + "_initial";
				seqs[j-1] = s;
				for (int i = 1; i <= popSize / randomSeqs; i++) {
					s = new Sequence(sb.toString());
					s.name = ">sequence" + j + "_" + i;
					pw.println(s);
				}
			}
			pw.flush();
			pw.close();
			return seqs;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return seqs;
	}

}
