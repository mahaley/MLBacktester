package summary;

public class Receipt {

	
	public String guid;
	public boolean verif;
	public boolean lowConfidence;
	public String type; //html v text
	public String merchant;
	public String subjectLine;
	public boolean fallback;
	public float confidence;
	
	//public boolean gotTotal;
	public float total;
	public int numItems;
	public String[] itemNames;
	
	public float trueTotal;
	public int trueNumItems;
	public String[] trueItemNames;
	
	
	
	
	public Receipt (String guid, String merchant, boolean verif, float total, String type) {
		this.guid = guid;
		this.merchant = merchant;
		this.verif = verif;
		this.total = total;
		this.fallback = !verif & (((Float)total) != null);
		this.type = type;

	}

	public Receipt (String guid, String merchant, boolean verif, float total, String type, boolean lowConfidence, int numItems, String[] itemNames, float trueTotal, int trueNumItems, String[] trueItemNames) {
		this.guid = guid;
		this.merchant = merchant;
		this.verif = verif;
		this.total = total;
		this.fallback = !verif & (((Float)total) != null);
		this.type = type;
		this.lowConfidence = lowConfidence;
		this.numItems = numItems;
		this.itemNames=itemNames;
		this.trueTotal=trueTotal;
		this.trueNumItems=trueNumItems;
		this.trueItemNames=trueItemNames;
	}
	
	public Receipt (String guid, String merchant, boolean verif, float total, String type, boolean lowConfidence, int numItems, String[] itemNames, float trueTotal, int trueNumItems, String[] trueItemNames, String subjectLine, float confidence) {
		this.guid = guid;
		this.merchant = merchant;
		this.verif = verif;
		this.total = total;
		this.fallback = !verif & (((Float)total) != null);
		this.type = type;
		this.lowConfidence = lowConfidence;
		this.numItems = numItems;
		this.itemNames=itemNames;
		this.trueTotal=trueTotal;
		this.trueNumItems=trueNumItems;
		this.trueItemNames=trueItemNames;
		this.subjectLine = subjectLine;
		this.confidence = confidence;
	}
	
	public String toString() {
		//a string representation of Receipt
		StringBuilder sb = new StringBuilder();
		String m = "                  ";
		if (merchant.length() < m.length()-1) {
			m = merchant + m.substring(merchant.length(), m.length()-1) + "\t";
		} else {
			m = merchant.substring(0, m.length()-4) + "...\t"; 
		}
		
		
		sb.append(m);
		sb.append(type);
		sb.append("\t");
		sb.append(guid);
		//sb.append("\t");
		//sb.append("confid=");
		//sb.append(String.format("%.4f", confidence));
		sb.append("\t(");
		sb.append(total);
		sb.append(" vs. ");
		sb.append(trueTotal);
		sb.append(")  \t");
		sb.append("[");
		sb.append(numItems);
		sb.append(" vs. ");
		sb.append(trueNumItems);
		sb.append("]  \t");
		sb.append(subjectLine);
		return sb.toString();
	}
	
}
