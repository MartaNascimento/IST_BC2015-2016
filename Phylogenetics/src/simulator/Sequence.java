package simulator;

//this class represents a Sequence with a name and the sequence itself
public class Sequence {
	
	private static int id = 1;
	public String name = "sequence_";
	public char[] sequence;

	public Sequence(String seq) {
		this.sequence = seq.toCharArray();
		this.name += id++;
	}

	public int size() {
		return sequence.length;
	}
	@Override
	public boolean equals(Object seq2){
		Sequence s2 = (Sequence)seq2;
		return this.name.equals(s2.name);
	}
	@Override
	public String toString() {
		return ">" + name + "\n" + String.valueOf(sequence);
	}
}
