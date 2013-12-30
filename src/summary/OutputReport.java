package summary;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * @author mlipoff
 *
 */
public class OutputReport {

	private static final String mainDir = "C:/Experiments/Mike/";//"C:\\Users\\Mike\\Dropbox\\Validation Sets\\";
	private static final String modelOutputsPath = "C:/itemize/Copy of MLBackTest/src/ModelOutputs.txt"; 

	public static ArrayList<ModelSummary> os = new ArrayList<ModelSummary>(); //a collection of all the outputs
	
	public static void main(String[] args) throws IOException {
		readInModelOutputs(); //reads in the config file (modelOutputsPath)
		populateModels(); //populats the modells based on the config file	
		print(); //prints basic stats of the models

		//print confusion matrices for the models
		Iterator<ModelSummary> iter = os.iterator();
		while (iter.hasNext()) {
			ModelSummary ms = iter.next();
			System.out.println("\n---------------------------------------------------------------------------------------------");
			ms.compareToTruth();
			printConfusionMatrix(ms);
		}
		
		@SuppressWarnings("unused")
		boolean suppress = true;
		
		
		// ---> MAIN PLACE TO MAKE CHANGES
		// the numbering is based on the ModelOutput.txt file. Excluding the commented lines, each line is numbered sequential.
		int firstModelIndex = 0; //the model to compare from
		int secondModelIndex = 1; //the model to compare to
		System.out.println("\n\n----------------------------------------MODEL COMPARISION ----------------------------------");
                ModelSummary firstModel = os.get(firstModelIndex);
                ModelSummary secondModel = os.get(secondModelIndex);
		//firstModel.compareModels(secondModel); // compares the models
                System.out.println("BASELINE: " + secondModel.name + " WITH: " + firstModel.name + "\n\n");
		printModelDifference(firstModel, secondModel, false); //prints the comparision. */
                System.out.println("\n-------------\n");
                System.out.println("BASELINE:  " + firstModel.name + " WITH: " + secondModel.name + "\n\n");
                printModelDifference(secondModel, firstModel, false); //prints the comparision. 
		
		
		System.out.println("\n\n----------------------------------------GROUND TRUTH COMPARISION ----------------------------------");
		int modelNum = 0; //the model to compare to the ground truth
		os.get(modelNum).compareToTruth(); //does the comparison
		os.get(modelNum).printDiff(os.get(modelNum).trueDiff.get(0), "OVERALL CORRECT", false); //can change the string in this call to compare the model at any parameter.
		//  <----- 
		
	}

	/**Reads in the models from the ModelOutput.txt file, and makes ModelSummary based on it
	 * @throws IOException
	 */
	public static void readInModelOutputs() throws IOException {
		//reads in all the output files, and creates ModelSummary's
		File f = new File(modelOutputsPath);
		@SuppressWarnings("resource")
		BufferedReader r = new BufferedReader( new FileReader(f));	
		String line = null;
		while((line = r.readLine() ) != null ) {
			if (!line.startsWith("//")) {
				//ORDER --> BackTestSet,Isolated,Candidate,ReceiptList,DirectoryOfOutputs
				try {
					String[] params = line.split(",");
					String name = params[0];
					boolean isIsolated = params[1].equals("1");
					boolean isCandidate = params[2].equals("1");
					String receiptList = mainDir + params[3];
					String outputDirectory1 = mainDir + params[4];
					os.add(new ModelSummary(name,isIsolated,isCandidate,receiptList,outputDirectory1));
                                        String outputDirectory2=null;
                                        String name2=null;
                                        if (params.length>5) {    
                                           name2=params[5];
                                           outputDirectory2 = mainDir + params[6];
                                        }
                                        if (outputDirectory2!=null && ! outputDirectory2.isEmpty()) os.add(new ModelSummary(name2,isIsolated,isCandidate,receiptList,outputDirectory2));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**Populates the ModelSummary based on receipts, etc. Iterates thru all the ModelSummary's to do so
	 * @throws IOException
	 */
	public static void populateModels() throws IOException {
		//populated each of hte ModelSummary's with the receipts
		Iterator<ModelSummary> iter = os.iterator();
		System.out.print("Populating.");
		while (iter.hasNext()) {
			iter.next().populate();
			System.out.print(".");
		}
		System.out.println();
		System.out.println();
	}

	
	/**Prints a simple confusion matrix, with the column headers as the verification results (true v false)
	 * @param ModelSummary m
	 */
	public static void printConfusionMatrix(ModelSummary m) {
		System.out.println(m);
		System.out.println("                   FALSE     TRUE");
		System.out.println("Correct Total     "+ m.trueDiff.get(0).get("GOT CORRECT TOTAL").size() + "     " + m.trueDiff.get(1).get("GOT CORRECT TOTAL").size());
		System.out.println("Incorrect Total   "+ m.trueDiff.get(0).get("GOT INCORRECT TOTAL").size() + "     " + m.trueDiff.get(1).get("GOT INCORRECT TOTAL").size());
		System.out.println("No Total          "+ m.trueDiff.get(0).get("GOT NO TOTAL").size() + "     " + m.trueDiff.get(1).get("GOT NO TOTAL").size());
		System.out.println();
		System.out.println("Correct No. Items "+ m.trueDiff.get(0).get("GOT CORRECT NUM ITEMS").size() + "     " + m.trueDiff.get(1).get("GOT CORRECT NUM ITEMS").size());
		System.out.println("Too Many Items    "+ m.trueDiff.get(0).get("GOT TOO MANY ITEMS").size() + "     " + m.trueDiff.get(1).get("GOT TOO MANY ITEMS").size());
		System.out.println("Too Few Items     "+ m.trueDiff.get(0).get("GOT TOO FEW ITEMS").size() + "     " + m.trueDiff.get(1).get("GOT TOO FEW ITEMS").size());
		System.out.println();
		System.out.println("Overall Correct   "+ m.trueDiff.get(0).get("OVERALL CORRECT").size() + "     " + m.trueDiff.get(1).get("OVERALL CORRECT").size());
		System.out.println("Overall Incorrect "+ m.trueDiff.get(0).get("OVERALL INCORRECT").size() + "     " + m.trueDiff.get(1).get("OVERALL INCORRECT").size());
		
		
	}
	
	/** Prints the differences in the models, based on some metrics that are defined within the method.
	 * Eg, TRUE-TO-FALSE are the receipts that were TRUE VERIFICATION in model a, but are FALSE VERIFCATION in the model b. 
	 * @param ModelSummary a
	 * @param ModelSummary b
	 * @param suppressRecieptList (true or false)
	 */
	public static void printModelDifference(ModelSummary a, ModelSummary b, boolean suppressRecieptList) {
		a.compareModels(b);
		Iterator<String> iter = a.diff.keySet().iterator();
		while (iter.hasNext()) {
			a.printDiff(a.diff,iter.next(),suppressRecieptList);
			System.out.println("\n\n");
		}
		
	}
	
	/**stores the results in this.diff
	 * @return
	 */
	public static Set<String> getBackTest() {
		Iterator<ModelSummary> iter = os.iterator();
		Set<String> backTest = new HashSet<String>();
		while(iter.hasNext()) {
			backTest.add(iter.next().backTestSet);
		}
		return backTest;
	}
	
 	/**DEPRECATED. given some parameters, finds the ModelSummary that corresponds.
 	 * @param backTestSet
 	 * @param isIsolated
 	 * @param isNew
 	 * @return ModelSummary
 	 */
 	public static ModelSummary find(String backTestSet, boolean isIsolated, boolean isNew) {

		Iterator<ModelSummary> iter = os.iterator();
		while(iter.hasNext()) {
			ModelSummary o = iter.next();
			if (o.backTestSet.equals(backTestSet) && o.isIsolated==isIsolated && o.isCandidate==isNew) {
				return o;
			}
		}
		return null;
	}
	
	/**Prints basic stats on the models.
	 * 
	 */
	public static void print() {
		
		String tab = "    ";
		
		Iterator<ModelSummary> iter = os.iterator();
		System.out.println("-----VERIFICATION RESULT IS TRUE-----");
		System.out.println();
		ModelSummary m;
		while (iter.hasNext()) {
			m = iter.next();
			System.out.print(m + tab);
			System.out.println(m.getNumVerif() +"/" +m.getNumReceipts());
		}
		System.out.println("\n\n\n");
	}


	public static File findFile (final String guid, File directory) {
		//a helper function for the ModelSummary
		//finds the ML output file for a given GUID
		File[] f = directory.listFiles(new FilenameFilter() { 
			public boolean accept(File dir, String filename)
			{ return filename.endsWith(guid + ".txt"); }
		} );
		if (f!=null && f.length > 0) {return f[0];}
		else {return null;}
	}

}
