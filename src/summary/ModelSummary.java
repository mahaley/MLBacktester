package summary;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ModelSummary {


	private HashMap<String, Receipt> outputSet; //all the receipts, key'ed by the GUID
	public HashMap<String,ArrayList<Receipt>> diff;// the different parameters measured for the difference. key'ed by the name of the difference type
	public ArrayList<HashMap<String,ArrayList<Receipt>>> trueDiff;
	private Pattern TOTAL_REGEX = Pattern.compile("[^\\d]*([\\d]{1,}[\\.]{0,1}[\\d]{0,2}).*[\\t]{1}.*");
	private Pattern CONFID_REGEX = Pattern.compile("Labeling confidence is: (.*)");

	//properties
	public String backTestSet;
	public boolean isIsolated;
	public boolean isCandidate;
	public String name;
	public String receiptList;
	public String outputDirectory;


	//parameters based on the files
	private static final int guidCol = 0;
	private static final int merNmCol = 3;
	//private static final int merCatCol = 4;
	//private static final int currCol = 5;
	private static final int recTypeCol = 6;
	//private static final int formerSource = 7;
	private static final int eTrueTotalCol = 8;
        private static final int ocrTrueTotalCol = 7;
	private static final int eTrueNumItemsCol = 10;
        private static final int ocrTrueNumItemsCol = 9;
	private static final int subjectLineCol = 12;


	public ModelSummary() {
		outputSet = new HashMap<String, Receipt>();
	}

	public ModelSummary(String name, boolean isIsolated, boolean isCandidate, String receiptList, String outputDirectory) {
		outputSet = new HashMap<String, Receipt>();
		this.name = name;
		this.isIsolated = isIsolated;
		this.isCandidate = isCandidate;
		this.receiptList = receiptList;
		this.outputDirectory = outputDirectory;
	}
	
	public ModelSummary(String name, String receiptList, String outputDirectory) {
		outputSet = new HashMap<String, Receipt>();
		this.name = name;
		this.isIsolated = false;
		this.isCandidate = false;
		this.receiptList = receiptList;
		this.outputDirectory = outputDirectory;
	}

	public HashMap<String, Receipt> getOutputSet() {
		return outputSet;
	}

    @Override
	public String toString() {
		//gets a string representation of hte OutputSummary
		StringBuilder sb = new StringBuilder();
		sb.append(this.name);
		sb.append(" ");
		/*sb.append(" (");
		if (isCandidate) {sb.append("New");} else {sb.append("Old");}
		sb.append(" ");
		if (isIsolated) {sb.append("Isolated");} else {sb.append("Pipeline");}
		sb.append(")");*/
		return sb.toString();	
	}

	/** Add a receipt to the ModelSummary
	 * @param r 
	 */
	public void addReceipt(Receipt r) {
		//add a reciept
		outputSet.put(r.guid, r);
	}

	/** Gets the number of receipts in the model
	 * @return
	 */
	public int getNumReceipts() {
		return outputSet.size();
	}

	/**
	 * @return the number of receipts that the model successfully verified
	 */
	public int getNumVerif() {
		int result = 0;
		Iterator<Receipt> iter = outputSet.values().iterator();
		while (iter.hasNext()) {
			if (iter.next().verif) {
				result++;
			}
		}
		return result;
	}

	/**
	 * @return number of receipt where verification=false and total is found
	 */
	public int getNumFallback() {
		
		int result = 0;
		Iterator<Receipt> iter = outputSet.values().iterator();
		while (iter.hasNext()) {
			if (iter.next().fallback) {
				result++;
			}
		}
		return result;
	}

	/**
	 * @return number that model got a total
	 */
	public int getNumGotTotal() {
		//
		int result = 0;
		Iterator<Receipt> iter = outputSet.values().iterator();
		while (iter.hasNext()) {
			if (iter.next().total > -999999) {
				result++;
			}
		}
		return result;
	}



	/**populates the ModelSummary with all its associated receipts. Reads in from directory where all the receipts are
	 * @throws IOException
	 */
	public void populate() throws IOException {	
		
		File directory = new File(outputDirectory);
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader( new FileReader(receiptList));	
		String line = null;
		String guid;

		while((line = reader.readLine() ) != null ) {
                        File resultFile;
			guid = line.split(",{0,1}\\t")[guidCol].trim();
                        try{
                           resultFile = OutputReport.findFile(guid, directory);
                        }catch(Exception e){
                           System.out.println(e);
                           resultFile = null;
                        }
			
			if (resultFile != null) {
				BufferedReader resultReader = new BufferedReader(new FileReader(resultFile));
				String resultLine;
				float grandTotal = -999999;
				boolean isTrueResult = false;
				boolean lowConfidence = false;
				float confidence = -1;
				int numItems = 0;
				while ((resultLine = resultReader.readLine()) != null) { //add more lines here to extract more info from the result files
					if (resultLine.indexOf("label=total_price") > -1) {
						grandTotal = getTotalFromLine(resultLine);
						//if (grandTotal == -999999) {System.out.println(resultLine + "\n" + guid);}
					}
					if (resultLine.indexOf("Labeling confidence is") > -1) {
						confidence = getConfidence(resultLine);
					}
					if (!isTrueResult) {
						isTrueResult = resultLine.indexOf("Verification Result ~ true") > -1;
					}
					if (!lowConfidence) {
						lowConfidence = resultLine.indexOf("Confidence is below") > -1;
					}
					if (resultLine.indexOf("label=item_description") > -1) {
						numItems++;
					}
				}
				resultReader.close();
				String[] lineSplit = line.split(",{0,1}\\t");
                                if (lineSplit.length <= 1) lineSplit = line.split(",");                                   
				try {
                                Receipt r;
                                if (lineSplit.length <= 11){
                                    r = new Receipt(guid, 
						lineSplit[merNmCol],
						(Boolean)isTrueResult,
						grandTotal,
						lineSplit[recTypeCol],
						lowConfidence,
						numItems,
						(String[]) null,
						Float.parseFloat(lineSplit[ocrTrueTotalCol]),
						Integer.parseInt(lineSplit[ocrTrueNumItemsCol]),
						(String[])null,
						null,
						confidence);
                                }else{
                                    r = new Receipt(guid, 
						lineSplit[merNmCol],
						(Boolean)isTrueResult,
						grandTotal,
						lineSplit[recTypeCol],
						lowConfidence,
						numItems,
						(String[]) null,
						Float.parseFloat(lineSplit[eTrueTotalCol]),
						Integer.parseInt(lineSplit[eTrueNumItemsCol]),
						(String[])null,
						lineSplit[subjectLineCol],
						confidence);
                                }    
				
					addReceipt(r);
				} catch (Exception e) {
					System.out.println("Failed to add: " + guid + "  " + "\n\t" + line);
				}
				
			}

		}

	}
        public static boolean regExMatchesSubsequenceOfString(String regex, String input){
		return Pattern.compile(regex).matcher(input).find();
	} 
        public static String fetchRegExMatch(String regEx, String input) {
            String result = "";          
            Matcher matcher = Pattern.compile(regEx).matcher(input);
            if (matcher.find()) 
            result = matcher.group();
            return result;
        }
        
            public static String fetchRegExMatch(List<String> regExs, String input) {
        String result = "";
        for (String reg : regExs)
        {
            String str = fetchRegExMatch(reg, input);
            if (str != null && !str.isEmpty()){
                result = str;
                break;
            }
        }
        return result;
    }
    
     public static String fetchRegExMatch(String[] regExs, String input) {
       return fetchRegExMatch(Arrays.asList(regExs), input);
    }
       public static String fetchFirstCapturingGroup(String regEx, String input) {
        String result = "";
        Matcher matcher = Pattern.compile(regEx).matcher(input);
        if (matcher.find()) {
            result = matcher.group(1); 
        }
        return result;
    }
           public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst(regex+"(?!.*?"+regex+")", replacement);
    }
        
       public static String fetchNormalizedPrice(String priceToken) { //TODO this should really return a float or decimal (or null)
        //if (priceToken==null /*|| priceToken.matches(".*201[0-9]$")*/) return (""); 
        /*
        String normalizedPriceLargeFont=fetchRegExMatch(RegExConstants.ALL_PRICE_REGEX, priceToken).replace(" ","");
        if (normalizedPriceLargeFont.isEmpty()) normalizedPriceLargeFont=replaceLast(fetchFirstCapturingGroup(RegExConstants.NORMALIZED_PRICE_REGEX_PAPER, priceToken).trim().replace("-",".").replace("_","."), " ", ".");
 
        String firstPriceWithCurrency=fetchNormalizedFirstPriceWithCurrency(priceToken);
   
        if (!firstPriceWithCurrency.isEmpty() && !firstPriceWithCurrency.equals("-") && !firstPriceWithCurrency.matches("\\$8") && !firstPriceWithCurrency.contains("8.88") && !firstPriceWithCurrency.contains("$88") && !priceToken.contains("$8.$") && !priceToken.contains("_$.88")) 
            priceToken=firstPriceWithCurrency;
        String normalizedPrice = fetchRegExMatch(RegExConstants.NORMALIZED_PRICE_REGEX_1+"(?!\\d)", priceToken);
        if (normalizedPrice.isEmpty()) normalizedPrice = fetchRegExMatch(RegExConstants.NORMALIZED_PRICE_REGEX_2+"(?!\\d)", priceToken);
        if (normalizedPrice.isEmpty() && regExMatchesSubsequenceOfString(RegExConstants.GRATIS, priceToken)) return("0");
        if (!normalizedPrice.isEmpty() && priceToken.startsWith("-")) normalizedPrice="-"+normalizedPrice;
               if (normalizedPrice.length() < normalizedPriceLargeFont.length()) normalizedPrice = fetchRegExMatch(RegExConstants.NORMALIZED_PRICE_REGEX_1+"(?!\\d)", normalizedPriceLargeFont);
        return normalizeLargePrice(normalizedPrice); */
        if (priceToken.contains("29.341"))
                System.out.println("here");
        String priceNormalized=priceToken.substring(0, priceToken.indexOf("\t"));
        Float priceNormalizedFloat=Float.valueOf(priceNormalized);
        return String.format("%.2f", priceNormalizedFloat); 
     }
    
     public static String normalizeLargePrice(String price) {
           if (price.indexOf(",") < price.indexOf("."))
             return price.replace(",", "");
           else 
             return price.replace(".", "").replace(",",".");
     }
    
     public static String fetchNormalizedFirstPriceWithCurrency(String priceToken) {
        if (priceToken==null) return ("");      
        String normalizedPrice = fetchRegExMatch(RegExConstants.NORMALIZED_PRICE_REGEX_2+"\\s*"+RegExConstants.CURRENCY_CODE_REGEX, priceToken);
        if (normalizedPrice.isEmpty()) normalizedPrice = fetchRegExMatch(RegExConstants.NORMALIZED_PRICE_REGEX_1+"\\s*"+RegExConstants.CURRENCY_CODE_REGEX, priceToken);       
        if (normalizedPrice.isEmpty()) normalizedPrice = fetchRegExMatch(RegExConstants.CURRENCY_CODE_REGEX+"\\s*"+RegExConstants.NORMALIZED_PRICE_REGEX_2, priceToken);           
        if (normalizedPrice.isEmpty()) normalizedPrice = fetchRegExMatch(RegExConstants.CURRENCY_CODE_REGEX+"\\s*"+RegExConstants.NORMALIZED_PRICE_REGEX_1, priceToken);           
        
        if (normalizedPrice.isEmpty()) normalizedPrice = fetchRegExMatch(RegExConstants.CURRENCY_SYMBOL_REGEX+"\\s*"+RegExConstants.NORMALIZED_PRICE_REGEX_2, priceToken);
        if (normalizedPrice.isEmpty()) normalizedPrice = fetchRegExMatch(RegExConstants.CURRENCY_SYMBOL_REGEX+"\\s*"+RegExConstants.NORMALIZED_PRICE_REGEX_1, priceToken); 
 
        if (normalizedPrice.isEmpty() && regExMatchesSubsequenceOfString(RegExConstants.GRATIS, priceToken)) return("0");
        
        //if (regExMatchesSubsequenceOfString(RegExConstants.NEGATIVE_CURRENCY_PRICE_REGEX, priceToken)) normalizedPrice="-"+normalizedPrice;
        return normalizeLargePrice(normalizedPrice); 
    }
	/** Extracts the total from the line that contains the total. The line should be the line that contains label=total. Returns -999999 if no total found
	 * @param line
	 * @return total
	 */
	private static float getTotalFromLine(String line) {

		try {
			String totalString = fetchNormalizedPrice(line);//Matcher m = TOTAL_REGEX.matcher(line.replace(",",""));
			if (!totalString.isEmpty()) {
				return Float.parseFloat(totalString);
			} else { 
				System.out.println("Failed to extract the price from " + line);
				return -999999;
			}
		} catch (Exception e) {
			return -999999;
		}
	}
	
	private float getConfidence(String line) {
		
		try {
			Matcher m = CONFID_REGEX.matcher(line);
			if (m.matches()) {
				return Float.parseFloat(m.group(1));
			} else { 
				System.out.println("Failed to extract confidence from " + line);
				return -1;
			}
		} catch (Exception e) {

			return -1;
		}
		

	}
	

	/**Compares this model to another ModelSummary. stores the results in this.diff 
	 * @param compareTo
	 */
	public void compareModels(ModelSummary compareTo) {

		// create ArrayLists of Receipts for each for the metrics that need to be measured.
		ArrayList<Receipt> trueToFalse = new ArrayList<Receipt>();
		ArrayList<Receipt> trueToFalseAndCorrect = new ArrayList<Receipt>();
		ArrayList<Receipt> falseToTrue = new ArrayList<Receipt>();
                ArrayList<Receipt> lostTotal = new ArrayList<Receipt>();
		ArrayList<Receipt> lowToHighConfid = new ArrayList<Receipt>();
		ArrayList<Receipt> highToLowConfid = new ArrayList<Receipt>();
		ArrayList<Receipt> newFalsePos = new ArrayList<Receipt>();
		ArrayList<Receipt> newFalsePosTotal = new ArrayList<Receipt>();
                ArrayList<Receipt> newNegatives = new ArrayList<Receipt>();
                ArrayList<Receipt> newPositives = new ArrayList<Receipt>();
                ArrayList<Receipt> newL1Negatives = new ArrayList<Receipt>();
                ArrayList<Receipt> newL1Positives = new ArrayList<Receipt>();


		Iterator<Entry<String, Receipt>> oldIter = compareTo.getOutputSet().entrySet().iterator();

		// for each receipt, compare it to another receipt and see what the difference is. 
		// --> can make big changes here

		while (oldIter.hasNext()) {
			Entry<String, Receipt> e = oldIter.next();
			String guidOld = e.getKey();
			Receipt rOld = e.getValue();
                        /*if (rOld==null) 
                            System.out.println("here");
                        
                        if (guidOld.contains("2a5"))
                                System.out.println("here");*/

			Receipt rNew = this.getOutputSet().get(guidOld);
                        if (rNew==null) 
                            System.out.println("here"); 
                      try {
			if (!rNew.verif && rOld.verif) {
				trueToFalse.add(rNew);
				if ((rOld.total == rOld.trueTotal && (rOld.trueNumItems==0 || rOld.numItems == rOld.trueNumItems)) &&
                                    (rNew.total == rNew.trueTotal && (rNew.trueNumItems==0 || rNew.numItems == rNew.trueNumItems)))
                                {
					trueToFalseAndCorrect.add(rNew);
				}
			} else if (rNew.verif && !rOld.verif) {
				falseToTrue.add(rNew);
			}
			if (rNew.lowConfidence && !rOld.lowConfidence) {
				highToLowConfid.add(rNew);
			} else if (!rNew.lowConfidence && rOld.lowConfidence) {
				lowToHighConfid.add(rNew);
			}
			
			if (!(rNew.total==rNew.trueTotal && (rNew.trueNumItems==0 || rNew.numItems == rNew.trueNumItems)) && rNew.verif==true && (rOld.verif==false || !(rOld.verif==true && (rOld.total!=rOld.trueTotal | (rOld.trueNumItems>0 && rOld.numItems!=rOld.trueNumItems))))) {
				newFalsePos.add(rNew);
			}
                        if (((Float)rNew.total==null || rNew.total == -999999)  && (Float)rOld.total != null && rOld.total != -999999 )
                            lostTotal.add(rNew);
                        
			
			if (rNew.verif==true && !(rNew.total==rNew.trueTotal) && !(rOld.verif==true && !(rOld.total==rOld.trueTotal))) {
				newFalsePosTotal.add(rNew);
			}
                        
                        if (!(rNew.total==rNew.trueTotal && (rNew.trueNumItems==0 || rNew.numItems == rNew.trueNumItems)) && 
                            (rOld.total==rOld.trueTotal && (rOld.trueNumItems==0 || rOld.numItems == rOld.trueNumItems))) {
				newNegatives.add(rNew);
			}
                        if ((rNew.total==rNew.trueTotal && (rNew.trueNumItems==0 || rNew.numItems == rNew.trueNumItems)) && 
                            !(rOld.total==rOld.trueTotal && (rOld.trueNumItems==0 || rOld.numItems == rOld.trueNumItems))) {
				newPositives.add(rNew);
			}
                        if (!(rNew.total==rNew.trueTotal) && 
                            (rOld.total==rOld.trueTotal)) {
				newL1Negatives.add(rNew);
			}
                        if ((rNew.total==rNew.trueTotal) && 
                            !(rOld.total==rOld.trueTotal)) {
				newL1Positives.add(rNew);
			}
		       } catch (Exception ex) {
                            ex.printStackTrace();
                       }	
			
		}


		//take each of those ArrayLists, and put them into a hashmap, keyed by a descriptive string. Will access the arraylists in the main method by the string, so make it compact and descriptive.
		HashMap<String,ArrayList<Receipt>> result = new HashMap<String,ArrayList<Receipt>>();
		result.put("TRUE-TO-FALSE", trueToFalse);
                result.put("LOST THE TOTAL", lostTotal);
		result.put("TRUE-POSITIVE-TO-FALSE-NEGATIVE", trueToFalseAndCorrect);
		result.put("FALSE-TO-TRUE", falseToTrue);
		
		result.put("NEW-OVERALL-FALSE-POSITIVE", newFalsePos);
		result.put("NEW-TOTAL-PRICE-FALSE-POSITIVE", newFalsePosTotal);
                result.put("NEW-NEGATIVES", newNegatives);
                result.put("NEW-POSITIVES", newPositives);
                result.put("NEW-L1-NEGATIVES", newL1Negatives);
                result.put("NEW-L1-POSITIVES", newL1Positives);

		result.put("CONFID-LESS", highToLowConfid);
		result.put("CONFID-MORE", lowToHighConfid);
		this.diff = result;
	}

	/**Compares this model to the approx groundtruth. stores the results in this.trueDiff
	 * 
	 */
	public void compareToTruth() {
		// create two hashmaps. one for hte true results, and one for the false results.
		HashMap<String,ArrayList<Receipt>> trueResult = new HashMap<String,ArrayList<Receipt>>();
		HashMap<String,ArrayList<Receipt>> falseResult = new HashMap<String,ArrayList<Receipt>>();

		//add arraylists to the hashmap. similar to what is done in the compareModels method
		trueResult.put("GOT CORRECT TOTAL", new ArrayList<Receipt>());
		trueResult.put("GOT INCORRECT TOTAL", new ArrayList<Receipt>());
		trueResult.put("GOT NO TOTAL", new ArrayList<Receipt>());
		trueResult.put("GOT CORRECT NUM ITEMS", new ArrayList<Receipt>());
		trueResult.put("GOT TOO FEW ITEMS", new ArrayList<Receipt>());
		trueResult.put("GOT TOO MANY ITEMS", new ArrayList<Receipt>());
		trueResult.put("OVERALL CORRECT", new ArrayList<Receipt>());
		trueResult.put("OVERALL INCORRECT", new ArrayList<Receipt>());

		falseResult.put("GOT CORRECT TOTAL", new ArrayList<Receipt>());
		falseResult.put("GOT INCORRECT TOTAL", new ArrayList<Receipt>());
		falseResult.put("GOT NO TOTAL", new ArrayList<Receipt>());
		falseResult.put("GOT CORRECT NUM ITEMS", new ArrayList<Receipt>());
		falseResult.put("GOT TOO FEW ITEMS", new ArrayList<Receipt>());
		falseResult.put("GOT TOO MANY ITEMS", new ArrayList<Receipt>());
		falseResult.put("OVERALL CORRECT", new ArrayList<Receipt>());
		falseResult.put("OVERALL INCORRECT", new ArrayList<Receipt>());

		Iterator<Entry<String, Receipt>> iter = this.getOutputSet().entrySet().iterator();
		HashMap<String,ArrayList<Receipt>> saveTo;
		while (iter.hasNext()) {
			Entry<String, Receipt> e = iter.next();
			Receipt rec = e.getValue();

			if (rec.verif) {
				saveTo = trueResult;
			} else {
				saveTo = falseResult;
			}

			if ((Float)rec.total == null | rec.total == -999999) {
				saveTo.get("GOT NO TOTAL").add(rec);
			} else if (rec.total == Math.abs(rec.trueTotal)) {
				saveTo.get("GOT CORRECT TOTAL").add(rec);
			} else if (rec.total != rec.trueTotal) {
				saveTo.get("GOT INCORRECT TOTAL").add(rec);
			}
			if (rec.trueNumItems==0 || rec.numItems == rec.trueNumItems) {
				saveTo.get("GOT CORRECT NUM ITEMS").add(rec);
			} else if (rec.trueNumItems>0 && rec.numItems > rec.trueNumItems) {
				saveTo.get("GOT TOO MANY ITEMS").add(rec);
			} else if (rec.numItems < rec.trueNumItems) {
				saveTo.get("GOT TOO FEW ITEMS").add(rec);
			} 
			if ((rec.trueNumItems==0 || rec.numItems == rec.trueNumItems) && rec.total == rec.trueTotal) {
				saveTo.get("OVERALL CORRECT").add(rec);
			} else {
				saveTo.get("OVERALL INCORRECT").add(rec);
			}
			
			
		}

		ArrayList<HashMap<String,ArrayList<Receipt>>> result = new ArrayList<HashMap<String,ArrayList<Receipt>>>();
		result.add(falseResult);
		result.add(trueResult);
		this.trueDiff = result;
	}

	/** Prints the difference to the screen
	 * @param diff (Eg, can be either this.diff.get(0), or this.trueDiff.get(1)). 0 are the results with FALSE, and 1 are the results with TRUE
	 * @param diffType (Eg, "TRUE-TO-FALSE")
	 * @param suppressReceiptList (true or false)
	 */
	public void printDiff(HashMap<String,ArrayList<Receipt>> diff, String diffType, boolean suppressReceiptList) {
		
		System.out.println("---- " + diffType + " ----");
		System.out.println("Number: " + diff.get(diffType.toUpperCase()).size());
		System.out.println();

		if (suppressReceiptList) {
			// do nothing
		} else {
			Iterator<Receipt> iter = diff.get(diffType.toUpperCase()).iterator();

			ArrayList<String> d = new ArrayList<String>(); //storage of all the receipts. will sort them later
			while(iter.hasNext()) {
				Receipt r = iter.next();
				d.add(r.toString()); //add them, but will not store any duplicates
			}
			Collections.sort(d); //sort them
			Iterator<String> iterD = d.iterator();
			while(iterD.hasNext()) {
				System.out.println(iterD.next()); //prints the receipts
			}
		}
	}
        
        public static void main(String[] args) throws IOException {
               float totalFromLine = getTotalFromLine("29.341	FEA_ALPHABETS	FEA_DECIMAL	FEA_CURRENCYSIGN	FEA_WEBADDRESS_NOT	FEA_KEYWORD_total	FEA_TOTAL_WORDCOUNT_2	FEA_$88.888	FEA_total	FEA_TXTBLK_CURRENCYPRICE	FEA_TXTBLK_LASTPRICE	FEA_TXTBLK_ROOT	FEA_TOTAL_$29.341	DIRECTLY_FOLLOWING_FEA_DECIMAL	DIRECTLY_FOLLOWING_FEA_CURRENCYSIGN	DIRECTLY_FOLLOWING_FEA_WEBADDRESS_NOT	DIRECTLY_FOLLOWING_FEA_TOTAL_WORDCOUNT_1	DIRECTLY_FOLLOWING_FEA_$88.88	DIRECTLY_FOLLOWING_FEA_TXTBLK_CURRENCYPRICE	DIRECTLY_FOLLOWING_FEA_TXTBLK_ROOT	DIRECTLY_FOLLOWING_FEA_$29.34	INDIRECTLY_FOLLOWING_FEA_ALPHABETS	INDIRECTLY_FOLLOWING_FEA_HYPHENATED	INDIRECTLY_FOLLOWING_FEA_WEBADDRESS_NOT	INDIRECTLY_FOLLOWING_FEA_KEYWORD_Subtotal	INDIRECTLY_FOLLOWING_FEA_TOTAL_WORDCOUNT_2	INDIRECTLY_FOLLOWING_FEA_--------	INDIRECTLY_FOLLOWING_FEA_subtotal	INDIRECTLY_FOLLOWING_FEA_COMMON_WORD_subtotal	INDIRECTLY_FOLLOWING_FEA_TXTBLK_ROOT	INDIRECTLY_FOLLOWING_FEA_BEFORE_PRICE	INDIRECTLY_FOLLOWING_FEA_Subtotal_--------	ININDIRECTLY_FOLLOWING_FEA_CURRENCYSIGN	ININDIRECTLY_FOLLOWING_FEA_WEBADDRESS_NOT	ININDIRECTLY_FOLLOWING_FEA_TOTAL_WORDCOUNT_1	ININDIRECTLY_FOLLOWING_FEA_$88,88	ININDIRECTLY_FOLLOWING_FEA_TXTBLK_ROOT	ININDIRECTLY_FOLLOWING_FEA_AFTER_ITEM_LABEL	ININDIRECTLY_FOLLOWING_FEA_$29,34	DIRECTLY_PRECEDING_FEA_CAPITALIZED	DIRECTLY_PRECEDING_FEA_ALPHABETS	DIRECTLY_PRECEDING_FEA_WEBADDRESS_NOT	DIRECTLY_PRECEDING_FEA_TOTAL_WORDCOUNT_1	DIRECTLY_PRECEDING_FEA_master	DIRECTLY_PRECEDING_FEA_TXTBLK_ROOT	DIRECTLY_PRECEDING_FEA_Master	INDIRECTLY_PRECEDING_FEA_NUMERIC	INDIRECTLY_PRECEDING_FEA_WEBADDRESS_NOT	INDIRECTLY_PRECEDING_FEA_TOTAL_WORDCOUNT_1	INDIRECTLY_PRECEDING_FEA_888	INDIRECTLY_PRECEDING_FEA_TXTBLK_ROOT	INDIRECTLY_PRECEDING_FEA_338	label=total_price");              
               System.out.println(totalFromLine);
        }
}
