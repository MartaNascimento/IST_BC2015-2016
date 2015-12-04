package distance;

import simulator.Sequence;

public class HammingDistance{

	public int getDistance(Sequence s1, Sequence s2) {
		int diffs = 0;
		for (int i = 0; i < s1.size(); i++) {
			if (s1.sequence[i] != s2.sequence[i]) {
				diffs++;
			}
		}
		return diffs;
	}

}
