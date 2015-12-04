package distance;

public class JukesCantorDistance{

	public double getDistance(double p) {
		return (-3.0/4.0) * Math.log(1.0-((4.0/3.0) * p)); 
	}

}
