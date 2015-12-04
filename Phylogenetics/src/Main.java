public class Main {

	public static void main(String[] args) {
			
			if(args.length > 0){
				String question = args[0];
				//execute question1
				if(question.equalsIgnoreCase("q1")){
					new Q1().Q1Test();
					System.out.println("Files successfully generated in current directory");
				}
				//execute question2
				else if (question.equalsIgnoreCase("q2")){
					new Q2().Q2Test();
				System.out.println("Files successfully generated in current directory");
			}
				else
					System.out.println("Invalid question!");
			} else {
				System.out.println("Invalid parameters. Should receive question q1 or q2");
			}

	}

}
