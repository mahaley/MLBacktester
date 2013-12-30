package summary;

import summary.Constants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class RegExConstants {

    public static final String   DATE_COMMON_FORMAT_REGEX              = "(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.]\\d{4}";
    public static final String   DATE_EXTRACTION_REGEX                 = "(\\d{1,2})/(\\d{1,2})/(\\d{2,4})";
    public static final String   DATE_RE_FORMAT_REGEX                  = "(\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]))";
    public static final String   HOTEL_DATE_FORMAT_REGEX               = "(\\d{2})[-/](\\d{2})([-/](\\d{2}|\\d{4})){0,1}";
    // public static public static String REGEX_FOR_PRICE_DECIMAL =
    // "(\\d{1,15})*\\.(\\d{1,2})+"; //happens for peapod
    public static final String   FREE                                  = "(^FREE$)|(^Free$)";
    public static final String   INCLUDED                              = "(^INCLUDED$)|(^Included$)";
    public static final String   GRATIS                                = "("+FREE+")|("+INCLUDED+")";
    public static final String   CURRENCY_SYMBOL_REGEX                 = "([\\$£€]|US\\$)";
    public static final String   CURRENCY_CODE_REGEX                   = "(USD|GBP|EUR)";
    public static final String   CONTAINS_NUMERIC_PRICE_REGEX          = "(\\d+,)?(\\d{0,15})[\\.,](\\d{2})\\s*"+CURRENCY_CODE_REGEX+"?\\s*";
    public static final String   PRICE_REGEX                           = "(\\d+,)?(\\d{0,15})[\\.,](\\d{2})\\s*"+CURRENCY_CODE_REGEX+"?\\s*\\w?$|"+GRATIS;
    public static final String   NORMALIZED_PRICE_REGEX_2               = "(\\d+,)?\\d{1,15}([\\.,]\\d{2})?";
    public static final String   NORMALIZED_PRICE_REGEX_1             = "(\\d+,)?(\\d{0,15})?[\\.,]\\d{2}";
    public static final String   CURRENCY_PRICE_REGEX                  = CURRENCY_SYMBOL_REGEX + "[\\s]*".concat(PRICE_REGEX);//"\\$[\\s]*(\\d{1,15})\\.(\\d{1,2})";
    public static final String   NEGATIVE_CURRENCY_PRICE_REGEX         = "(-[\\s]*"+CURRENCY_SYMBOL_REGEX +"[\\s]*".concat(CONTAINS_NUMERIC_PRICE_REGEX)+")|("+CURRENCY_SYMBOL_REGEX+"-"+CONTAINS_NUMERIC_PRICE_REGEX+")|(\\("+CURRENCY_SYMBOL_REGEX +"[\\s]*".concat(CONTAINS_NUMERIC_PRICE_REGEX)+"\\))";
    // (-[\$£€](\d+,)?(\d{0,15})\.(\d{2})\s*)|([\$£€]-(\d+,)?(\d{0,15})\.(\d{2})\s*)|(\([\$£€](\d+,)?(\d{0,15})\.(\d{2})\s*)\)
    public static final String   PRICE_NO_DECIMAL_REGEX                = "\\d{0,15}";
    
    public static final String   EMAIL_ID_REGEX                        = "\\b[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\\b";
    public static final String   EMAIL_DOMAIN_REGEX                    = "[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";


    // Regex to identify Add td tag
    // public static String REGEX_HTML_AD_FILTER1=
    // "<td.*>.*<a.*>.*</a>.*(\\$(\\d{1,15})+\\.(\\d{1,2})+).*</td>";
    public static final String   HTML_AD_FILTER1_REGEX                 = "<td[^>]*>.*<a[^>]*>.*</a>.*([\\$£€](\\d{1,15})\\.(\\d{1,2})).*</td>";
    // public static String REGEX_HTML_AD_FILTER2=
    // "<TD.*>.*<A.*>.*</A>.*(\\$(\\d{1,15})+\\.(\\d{1,2})+).*</TD>";
    public static final String   HTML_AD_FILTER2_REGEX                 = "<TD[^>]*>.*<A[^>]*>.*</A>.*([\\$£€](\\d{1,15})\\.(\\d{1,2})).*</TD>";
    public static final String[] HTML_ANCHOR_END_TAG_REGEX             = { "</a>", "</A>" };
    public static final String[] HTML_ANCHOR_START_TAG_REGEX           = { "(?s)<a[^>]*>", "(?s)<A[^>]*>" };
    //
    public static final String[] HTML_EMAIL_LINK_REGEX                 = { "(?s)<a.*mailto:.*/a>", "(?s)<A.*mailto:.*/A>" };
    // RegEx to identify Header for item tables when there are no headers or
    // headers are Images
    public static final String[] HTML_ITEM_ROW_MATCHES_REGEX           = {
            "[A-Za-z0-9|\\s]* [\\s]*(\\d{2})/(\\d{2})/(\\d{4})[\\s]*\\$(\\d{1,15})\\.(\\d{1,2})[\\s]*(\\d{1,3})[\\s]*[\\$£€](\\d{1,15})\\.(\\d{1,2})",
            // "[A-Za-z0-9|\\s]*\\$(\\d{1,15})+\\.(\\d{1,2})+[\\s]*(\\d{1,3})++[\\s]*\\$(\\d{1,15})+\\.(\\d{1,2})+",
            "(([a-z]|[A-Z]){2,4})[\\s]*(\\d{4,8}).*([a-z]|[A-Z]){2,4}[\\s]*(\\d{1,3})[\\s]*[\\$£€](\\d{1,15})\\.(\\d{1,2}).*",
            "[a-zA-z0-9]+.*(\\d{1,3})[\\s]+Each.*\\$(\\d{1,15})\\.(\\d{2})[\\s]+\\d{1,3}[\\s]+\\$(\\d{1,15})\\.(\\d{2})[\\s]+[\\$£€](\\d{1,15})\\.(\\d{2})",
            "[A-Za-z0-9|\\s]*[\\$£€](\\d{1,15})\\.(\\d{1,2})[\\s]*[^$](\\d{1,3})[\\s]*\\$(\\d{1,15})\\.(\\d{1,2})",
            "^[a-zA-Z0-9,/\\s]*\\s($?\\d{1,15}\\.\\d{1,2})\\s\\d{1,3}\\s($?\\d{1,15}\\.\\d{1,2})$"
            };
    public static final String   HTML_TAGS_REGEX                       = "</?[^>]*>";
    // unwanted rows in Item table like Shipment date...
    public static final String[] ITEM_TABLE_UNWANTED_ROWS_REGEX        = {
                                                                       // shipment
                                                                       // ....
                                                                       // 01/25/2010
                                                                       ".*shipment.*(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d" };
    public static final String   ITEM_TABLE_WAS_PRICE_REGEX            = "was " + CURRENCY_PRICE_REGEX;
    public static final String   LABEL_TABLE_REGEX                     = "[a-zA-z]{3,15}:";
    public static final String   ORDER_NUMBER_DIGITS_REGEX             = "\\d{1,10}";
    // OrderNumber Extraction
    public static final String[] ORDER_NUMBER_PATTERN_REGEX            = { "(?s)[Oo]rder.*?([0-9]){4,10}" };
    public static final String   ORDER_NUMBER_REGEX                    = "([0-9]){4,10}";
    public static final String   P3_TOTAL_AND_PRICE_REGEX              = "total.*\\s*(\\d{1,15})\\.(\\d{1,2})";
    public static final String   P3_TOTAL_PRICE_REGEX                  = "total:\\s*(\\d{1,15})\\.(\\d{1,2})";
    // public static public static String REGEX_FOR_PHONE_NUMBER =
    // "(\\d{1,4})+\\.(\\d{1,4})+\\.(\\d{1,4})+\\.(\\d{1,4})|(\\d{1,4})+\\.(\\d{1,4})+\\.([a-z]|[A-Z])+";

    public static String   DOT_PHONE_NUMBER_REGEX                = "\\d{3}[\\s - .]\\d{3}[\\s - .]\\d{4}";
    public static String   PHONE_NUMBER_REGEX                    = "(\\d{2,4})[- .](\\d{2,4})[- .](\\d{2,4})|(\\d{2,4})[- .](\\d{2,4})[- .](\\d{2,4})[- .](\\d{2,4})";
    public static String   PHONE_NUMBER_WITHOUT_HIPHEN_REGEX     = "(\\d{1,4})[ .](\\d{1,4})[ .](\\d{1,4})[ .](\\d{1,4})|(\\d{1,4})[ .](\\d{1,4})[ .]([a-z]|[A-Z])+";
    public static String   PRICE_CURRENCY_WITH_SPACE_REGEX       = CURRENCY_SYMBOL_REGEX + "[\\s*](\\d{1,15})\\.(\\d{1,2})";
    public static String   PRICE_TABLE_REGEX                     = "[\\$]*(\\d{1,15})\\.(\\d{1,2})";
    public static String   PRICE_VERIFICATION_REGEX              = "([0-9]{1,3}(,*[0-9]{3})*(\\.[0-9]{2})?)|\\.[0-9]{2}";

    // Regex to check grand total price item
    public static final String[] PRICE_TOTAL_POSSIBLE_REGEX            = { ".*(\\s)total:\\s*(\\$\\d{1,15})\\.(\\d{1,2})", ".*(\\s)card to charge:\\s*(\\$\\d{1,15})\\.(\\d{1,2})",
            ".*(\\s)order total:\\s*(\\$\\d{1,15})\\.(\\d{1,2})", ".*(\\s)total for this order:\\s*(\\$\\d{1,15})\\.(\\d{1,2})" };
    public static final String   PUNCTUATION_INCLUDE_AT_THE_RATE_REGEX = "[^\\w\\.\\s\\-\\@]";
    public static final String   PUNCTUATION_REGEX_REGEX               = "[^\\w\\.\\s\\-]";
    
    
    public static String   QTY_REGEX                             = "\\d{1,3}";
    public static String   QUANTITY_NAME_REGEX                   = "([Qq]uantity|[Qq]ty)";
    public static String   QTY_VERIFY_REGEX                      = "^"+QTY_REGEX;
    public static String   QUANTITY_TOKEN_REGEX                  = "^"+QUANTITY_NAME_REGEX+"[\\s]*:[\\s]*"+QTY_REGEX;

    // REGEXs for [C]apitalized, [l]owercase, [u]ppercase - CLU
    public static final String[] REGEX_FOR_CLU                         = { "^[A-Z]([A-Z])+$", "^[A-Z]([a-z])+$", "^[a-z]([a-z])+$" };
    public static final String   REGEX_FOR_PRICE_REGEX                 = "(\\d)*(,)*(\\d)*\\.(\\d{1,2})";
    public static final String   SPL_CHAR_REGEX                        = "&[a-zA-Z]{3,8};";
    public static final String   GENERIC_SPL_CHAR_REGEX                = "&#?\\w{1,6};";
    
    public static final String   TIME_REGEX                            = "(([0-1]?[0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])[\\s]*(AM|PM|am|pm)?[\\s]*([P|M|C|E|AK|HA|H][S|D]T)?)|" +
    		                                         "(([0-1]?[0-9]|[2][0-3]):([0-5][0-9])[\\s]*(AM|PM|am|pm)?[\\s]*([P|M|C|E|AK|HA|H][S|D][T])?)";

    public static String   REGEX_FOR_NUMERIC					   = "[0-9]";
    public static String[] ALL_PRICE_REGEX                       = { "(\\d+,)?((\\d{0,15})\\.(\\d{2})$)", PRICE_REGEX};
    public static String[] CURRENCYPRICE_REGEX                   = { CURRENCY_SYMBOL_REGEX+"[\\s]*(\\d{1,15})\\.(\\d{1,2})"};
    public static String containsDecimal 						   = ".*\\d{1,}.*";
    public static String containsPunctuation			 = ".*\\p{P}.*";  
   
    public static String US_POSTAL_CODE                          = "([\\s]+\\d{5}(-\\d{4})?\\s*$)|(^\\d{5}(-\\d{4})?\\s*$)";
    public static String UK_POSTAL_CODE                          = "^([A-PR-UWYZ0-9][A-HK-Y0-9][AEHMNPRTVXY0-9]?[ABEHMNPRVWXY0-9]? {1,2}[0-9][ABD-HJLN-UW-Z]{2}|GIR 0AA)\\s*$"; //[^\w]{0,1}[\d\s\w]{5,8}$ && (?:([\d\w]+\s{1}[\d\w]+)$)
    public static String[] POSTAL_CODES                          = {US_POSTAL_CODE,
                                                                    UK_POSTAL_CODE };
    
    
    
        //new regex by Jing
     public static final String   PERCENTAGE_REGEX                      ="(\\d+,)?\\d{1,2}(\\.\\d{1,2})?%";
     public static final String   VAT_PERCENTAGE_TOKEN_REGEX             ="\\s*:{0,1}\\s*\\({0,1}\\s*"+PERCENTAGE_REGEX+"\\s*\\){0,1}$";

     public static final String   PRICE_TABLE_VERIFY_REGEX              = "(USD|GBP){0,1}\\s*(\\d{1,15})\\.(\\d{1,2})\\s*(USD|GBP){0,1}";
     public static final String   PRICE_CURRENCY_WITH_SPACE_VERIFY_REGEX = "-{0,1}\\s*"+CURRENCY_SYMBOL_REGEX + "\\s*(\\d{1,15})(\\.(\\d{1,2}))?\\s*(USD|GBP){0,1}";
     public static final String   GRAND_TOTAL_TOKEN_POSSIBLE_REGEX      =  "\\s*(total|card to charge|order total|total for this order|invoice total|payment amount|total amt|paid amount|total donation|your price)";
     public static final String   PRICE_TOTAL_VERIFY_REGEX              =  GRAND_TOTAL_TOKEN_POSSIBLE_REGEX+ ":{0,1}\\s*[\\$£€]?\\s*(\\d{1,15})(\\.(\\d{1,2}))?\\s*(usd|gbp|eur){0,1}";
     public static final String   PRICE_TOTAL_VERIFY_REGEX_2              =  GRAND_TOTAL_TOKEN_POSSIBLE_REGEX+ ":{0,1}\\s*(usd|gbp|eur)\\s*(\\d{1,15})(\\.(\\d{1,2}))";
     
    public static final List<String> specificLabels    = new ArrayList<>();

    static {
        //itemDescriptionInvalidators.addAll(Arrays.asList(com.itemize.ml.process.extractor.Constants.UNWANTED_NON_ITEM_TABLE_WORDS));
        specificLabels.addAll(makeCaseAgnostic(Arrays.asList(Constants.FEATURE_SHIPPING)));
        specificLabels.addAll(makeCaseAgnosticAndEntireSpan(Arrays.asList(Constants.FEATURE_TAX)));
        specificLabels.addAll(makeCaseAgnosticAndEntireSpan(Arrays.asList(Constants.FEATURE_DISCOUNT)));
        specificLabels.addAll(makeCaseAgnosticAndEntireSpan(Arrays.asList(Constants.FEATURE_TOTAL_LABEL)));
        specificLabels.addAll(makeCaseAgnosticAndEntireSpan(Arrays.asList(Constants.FEATURE_TOTAL)));
    }
    public static final List<String> totalLabels    = new ArrayList<>();

    static {
        totalLabels.addAll(makeCaseAgnosticAndEntireSpan(Arrays.asList(Constants.FEATURE_TOTAL_LABEL)));
        totalLabels.addAll(makeCaseAgnosticAndEntireSpan(Arrays.asList(Constants.FEATURE_TOTAL)));
    }
    
    public static final List<String> otherLabels    = new ArrayList<>();
    static {
        otherLabels.addAll(makeCaseAgnosticAndEntireSpan(Arrays.asList(Constants.REQUIRED_TABLE_HEADER_WORDS)));
        otherLabels.addAll(makeCaseAgnosticAndEntireSpan(Arrays.asList(Constants.UNWANTED_NON_ITEM_TABLE_WORDS)));
        otherLabels.addAll(makeCaseAgnosticAndEntireSpan(Arrays.asList(Constants.OTHER_LABELS)));
        otherLabels.addAll(makeCaseAgnosticAndStartOfSpan(Arrays.asList(Constants.PRODUCT_ATTRIBUTES)));
        otherLabels.addAll(makeCaseAgnostic(Arrays.asList(Constants.NON_ITEM_TABLE_WORDS_FOR_TOTAL_SUPPRESS)));
        //otherLabels.add(":\\s*$");
        otherLabels.add(":[\\W\\d]*$");
        otherLabels.addAll(Arrays.asList(POSTAL_CODES));
        //otherLabels.add(PHONE_NUMBER_REGEX);
    }
    
    public static final List<String> specificAndOtherLabels    = new ArrayList<>();
    static {
        specificAndOtherLabels.addAll(specificLabels);
        specificAndOtherLabels.addAll(otherLabels);
    }
    
    public static final List<String> itemDescriptionInvalidators    = new ArrayList<>();
    static {
        itemDescriptionInvalidators.addAll(specificAndOtherLabels);
        itemDescriptionInvalidators.add("^[^A-Za-z]{0,4}$"); //non-alphabetic shorter than 5 characters
        itemDescriptionInvalidators.add("^" + CURRENCY_SYMBOL_REGEX + "*[\\s]*".concat(PRICE_REGEX)); //a price only is not an item description!
    }

 
    private static ArrayList<String> makeCaseAgnosticWithSpanOption(List<String> constants, boolean beginSpan, boolean endSpan) {
        ArrayList<String> agnosticRegExps    = new ArrayList<>();
        for (String constant : constants) {
            StringBuilder agnosticRegExp = makeCaseAgnostic(constant);         
            if (beginSpan) agnosticRegExp.insert(0, '^');
            if (endSpan)   agnosticRegExp.append("[\\s]*$");
            agnosticRegExps.add(agnosticRegExp.toString());
        }
        return agnosticRegExps;       
    }

    public static ArrayList<String> makeCaseAgnostic(List<String> constants) {
        return makeCaseAgnosticWithSpanOption(constants, false, false);
    }
    
    public static StringBuilder makeCaseAgnostic(String constant) {
            StringBuilder agnosticRegExp = new StringBuilder();
            String testChar=null;
            for (char c : constant.toCharArray()) {
                testChar=""+c;
                if (testChar.matches("\\w")) agnosticRegExp.append("[").append(Character.toUpperCase(c)).append(Character.toLowerCase(c)).append("]");
                else agnosticRegExp.append(c);
            }
            return agnosticRegExp;
    }
    
    public static ArrayList<String> makeCaseAgnosticAndEntireSpan(List<String> constants) {
        return makeCaseAgnosticWithSpanOption(constants, true, true);
    }

    public static Collection<? extends String> makeCaseAgnosticAndStartOfSpan(List<String> constants) {
        return makeCaseAgnosticWithSpanOption(constants, true, false);
    }

    
 
 
        
}
