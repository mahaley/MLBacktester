package summary;

//import com.itemize.ml.vo.HtmlSpecialCharacter;

public class Constants {
	public static final String REGEX_TYPE_DATE = "date";
	public static final String COMMON_DATE_FORMAT = "MM/dd/yyyy";
	public static final String RE_DATE_FORMAT = "yyyy-MM-dd";
	public static final String FEATURE_POSITITION_TYPE="POS";
	public static final String FEATURE_HTML_HEADER="FEA_HTMLCOLHEADER_";
	public static final String FEATURE_SPECIALWORD="FEA_SPECIALWORD_";
	public static final String FEATURE_HTML_TOTAL="FEA_TOTAL";
        public static final String FEATURE_HTML_SUBTOTAL = "FEA_SUBTOTAL";
        public static final String FEATURE_HTML_TAX = "FEA_TAX";
        public static final String FEATURE_HTML_SHIPPING = "FEA_SHIPPING";
        public static final String FEATURE_HTML_DISCOUNT = "FEA_DISCOUNT";
	public static final String FEATURE_DELIMITER = "\t";
        public static final String SIMPLE_DATE_TIME_FORMAT = "yy-MM-dd-HHmm";
	

	//"order number"
	public static final String[] USEFUL_WORDS={"order date","order dt", "qty", "item", "cost","payment","subtotal","total"}; //payment added for shop1
	public static final String[] TABLE_HEADER_WORDS={"qty", "quantity", "item", "description", "sku", "cost", "price", "amount","charge date", "product information", "products"};
	public static final String[] LABEL_TABLE_TOKENS = {"shipping"};	
	
	public static final String EMAIL_FROM_LABEL = "from:";
	public static final String EMAIL_TO_LABEL = "to:";
	public static final String EMAIL_SENT_LABEL = "sent:";
	public static final String EMAIL_DATE_LABEL = "date:";
	public static final String EMAIL_SUBJECT_LABEL = "subject:";

	public static final String FEATURE_PREPROCESSOR_POSITION_DESC = "position";
	public static final String FEATURE_PREPROCESSOR_HTML_HEADER_DESC= "html";
	public static final String FEATURE_PREPROCESSOR_HTML_HEADER_SEPARATOR= "_";
	public static final String[] FEATURE_PREPROCESSOR_HTML_HEADER_UNWANTED_CHARCTERS={"*", ".", "#"};
	public static final String[] REQUIRED_TABLE_HEADER_WORDS={"item", "cost", "price","total", "subtotal", "description", "product", "name", "quantity", "qty", "amount", "style", "each", "ups deliverable merchandise", "title", "you purchased", "deal", "rate", "your order","UPS Standard Ground"};
	//"card"  removed
        public static final String[] UNWANTED_NON_ITEM_TABLE_WORDS={"billed to","order summary","merchandise: shipping: tax:","loyalty","tax: grand total:","order time","questions?","-----------", "Walmart.com Total:", "returns policy", "payment method", "paypal", "american express", "billing", "master ending", "surcharge", "off", "tendered", "insurance", "payment info", "credit card:", "customer no", "method of payment", "pickup", "bill To", "ship to","balance due","store credit", "reward", "samples credit", "misc.", "stamp", "buysafe",
								"fob charge", "other", "addtl charge", "adjustments", "coupons", "extra bucks", "miscellaneous charges", "authorized amount", "service charge", "mrkd", "coupon:", "change due:", "estimated winner","net total" ,
								  "credit card","dt bucks", "extra cash", "processing fee", "retail price", "net product", "visa", "deposit", "extended warranty", "calling plan", "monthly access", "you saved", "check order status","upc","Introducing Google Play","PayPal"};
	public static final String[] PRODUCT_ATTRIBUTES={"size:","color:","colour:","width:","quantity:","Sold by:","price:","item #","width:","length:","SKU#","item#:"};

        
        
        //JL - Below is the original, before attempting to extract subtotal, tax, shipping, etc.
        /*public static final String[] UNWANTED_NON_ITEM_TABLE_WORDS={"shipping", "freight","delivery","discount", "subtotal","sub total", "tax", "promotions", "-----------", "Walmart.com Total:", "gift", "merchandise", "returns policy", "color:", "size:", "handling", "payment method", "paypal", "american express", "billing", "master ending", "savings", "order #", "surcharge", "off", "tendered", "insurance", "payment info", "credit card:", "order no", "customer no", "method of payment", "pickup", "bill To", "ship to","balance due","item total:","store credit", "reward", "samples credit", "misc.", "stamp", "standard", "buysafe",
								"fob charge", "other", "credits", "addtl charge", "adjustments", "coupons", "extra bucks", "miscellaneous charges", "authorized amount", "service charge", "mrkd", "items", "coupon:", "change due:", "estimated winner","net total",
								  "credit card","dt bucks", "sub-total", "extra cash", "processing fee", "retail price", "total product", "net product", "settlement", "tip", "fee", "item(s) total", "visa", "deposit", "extended warranty", "calling plan", "monthly access","s&amp;h", "you saved", "mc **********", "save", "check order status","upc"};
        */
        //public static final String[] UNWANTED_NON_ITEM_TABLE_WORDS={"shipping", "discounts", "subtotal", "tax", "promotions","card", "-----------", "Walmart.com Total:", "gift", "merchandise", "returns policy"};
	public static final String[] NON_ITEM_TABLE_WORDS_FOR_TOTAL_SUPPRESS={"(inc shipping)", "Write a Review",  "Report a Problem", "View Privacy Policy","Movie Rental","Paid on"};
	public static final String[] FEATURE_TOTAL = {"total"};
        public static final String[] FEATURE_SHIPPING = {"shipping","freight", "FREE Super Saver Delivery","Shipping/Handling","shipping and handling","shipping \\& handling","Shipping \\& Processing","Free Shipping"};
        public static final String[] FEATURE_TOTAL_LABEL = {"what's this?","subtotal","sub total","item total","product total","merchandise","merchandise subtotal","sub-total","total purchases","total product","total before tax","total items","order total","order grand total","grand total","item subtotal"};
        public static final String[] FEATURE_TAX = {"tax","vat","v.a.t.","%","estimated tax","sales tax"};
        public static final String[] FEATURE_DISCOUNT = {"discount","save","promotion","savings","coupon","Promotional Code","Promo Savings:","Gift Card"};
        public static final String FEATURE_TOTAL_WORD = "Total";
	public static final String FEATURE_PURCHASE_TOTAL = "purchase total";
	public static final String FEATURE_ORDER_DATE = "Order Date";
	public static final String[] REQUIRED_DATE_IN_NON_TABLE_DATA_WORDS={"order", "placed on"};
	
	//File naming conventions
	public static final String FILE_NAME_PREPROCESSOR_PREFIX = "preprocessed-receipt-";
	public static final String FILE_NAME_FEATUE_EXTRACTOR_PREFIX = "feature-";
	public static final String FILE_NAME_MERGER_RULE_PREFIX = "rule-";
	public static final String FILE_NAME_MERGER_OUTPUT_PREFIX = "merge-";
	public static final String FEATURE_PREFIX="FEA_";
	
	//Dictionary search
	public static final String[] DICTIONARY_NAME={"merchants","products"};
	
	//headers for item tables
	public static final String[] REGEX_HTML_ITEM_ROW_HEADERS ={"item,shipping date,item price,qty,subtotal", 
		"ctlg,item,description,size,qty,price,status",
		"item,size,price,qty,subtotal,savings",
		"item,price,qty,subtotal", "description,price,qty,subtotal"
		//,"blank,item,size,price,qty,price,blank"
		};
	public static final String BLANK_LINE = "\n";
	
	//for total price filter
	public static final String[] POSSIBLE_TOTAL_STRINGS={"card to charge", "order total", "total for this order","total"};
	public static final String[] POSSIBLE_TABLE_DATA_IN_SINGLE_TD = {"card to charge", "order total", "total for this order","total","subtotal","discount", "tax","S&amp;H", "shipping", "you saved"};
	public static final String[] UNWANTED_TOTAL_STRINGS={"subtotal"};
	public static final String P3_PATTERN_TOTAL_LABEL="L";
	public static final String P3_PATTERN_TOTAL_PRICE="P";
	public static final String P3_PATTERN_UNKNOWN="X";
	public static final String P3_PATTERN_LABEL_PRICE="LP";
	//constants for ReExManager
	public static final String REGEX_MANAGER_LABEL_VALUE = "value";	
	public static final String REGEX_MANAGER_LABEL_DESCRIPTION = "description";
	public static final String REGEX_MANAGER_LABEL_FEATURE="feature";
	public static final String REGEX_MANAGER_LABEL_JAVA_DATE_FORMAT="javaformat";
	public static final String REGEX_MANAGER_ROOT_TAG_NAME="regex";

	//Receipts Tye
	public static final String EMAIL_CRAWLER="EmailCrawler";
	public static final String RECEIPT_TYPE_PHYSICAL="preceipt";
	public static final String COLON_DELIMITER = ":";
	public static final String COMMA_DELIMITER = ","; 
	public static final String SEMICOLON_DELIMITER = ";";
	public static final String DICT_DIR = "/normalizationdictionary"; 
	public static final String ABBRV_DICT = "/abbreviation.txt";
	public static final String ALTERNATIVES_DICT = "/canonicalmerchants.txt";
	public static final String MERCHANT_LABEL = "merchant_name";
	public static final String BRAND_LABLE = "label=item_description"; // we are gonna extract brand information from the item description
	public static final String ML_FILE_FORMAT_DELIMITER = "\t";
	public static final String NORMALIZED_FILE_FORMAT_DELIMITER = "\t";
	public static final String BUSINESS_TYPE_ENTITY_DELIMITER = " ";
	
	public static final String EMPTY_STRING = "";
	public static final String EMAIL_AT_THE_RATE = "@";
	//Feature Factory
	public static final String FEATURE_PREPROCESSOR_DESC = "Preprocessor generated feature";
	//NORMALIZATION 
	public static final String LABEL_MERCHANT_NAME = "merchant_name";
	public static final String LABEL_RECEIPT_DATE = "receipt_date";
	
	public static final String[] ITEM_SPECIFICATIONS = {"options:"};

	public static final String BLANK_STRING= " ";
	public static final String NEWLINE_CHARCTER= "\n";

	public static final String experiment_process = "Experiment"  ;
        
	public static final String parser1_process = "Parser1";
        public static final String parser1_AlternateProcess = "Parser1Alternate";
	//public static final String parser1_1_process = "Parser1_1"  ;
        public static final String simple_parser_process = "SimpleParser";
        public static final String summary_simple_parser_process = "SummaryReceiptSimpleParser";
        public static final String text_simple_parser_process = "Level1SimpleParser-Text";
        public static final String text_nontabular_simple_parser_process = "Level1SimpleParser-Text-NonTabular";
        public static final String hotel_simple_parser_process = "Level1SimpleParser-Hotel";
        public static final String one_off_text_process1 = "Level3SimpleParser-Text-OneOff-1";
        public static final String tabular_simple_parser_process  = "TabularReceiptSimpleParser";
        public static final String tabular_simple_parser_process2 = "TabularReceiptSimpleParser2";
        public static final String one_off_parser_process =  "SummarySimpleParserOneOff-Ticketmaster";

        public static final String one_off_parser_process2 = "SimpleParserOneOff-Seamless";
        public static final String one_off_parser_process3 = "Level1SimpleParserOneOff-Paypal";
        public static final String one_off_parser_process4 = "SimpleParser-AmazonShipping";
        public static final String one_off_parser_process5 = "SimpleParser-Ebay";
        public static final String one_off_parser_process6 = "SimpleParser-Etsy";
        public static final String one_off_parser_process7 = "SimpleParser-Nordstrom";
        public static final String one_off_parser_process8 = "SimpleParser-VictoriasSecret";
        public static final String simple_parser_feedback = "SimpleParserFeedback";
        

        public static final String[] all_models = { parser1_process,
                                                    parser1_AlternateProcess,
                                                    simple_parser_process,
                                                    simple_parser_feedback,
                                                    summary_simple_parser_process,
                                                    tabular_simple_parser_process,
                                                    tabular_simple_parser_process2,
                                                    one_off_parser_process,
                                                    one_off_parser_process2,
                                                    one_off_parser_process3,
                                                    one_off_parser_process4,
                                                    one_off_parser_process5,
                                                    one_off_parser_process6,
                                                    one_off_parser_process7,
                                                    one_off_parser_process8,

                                                };
        
        public static final String[] hotel_models = {
                                                        simple_parser_feedback,
                                                        hotel_simple_parser_process
                                                    };

        public static final String[] live_execution_tool_models = { parser1_process,
                                                                    parser1_AlternateProcess,
                                                                    //one_off_parser_process2, //make sure Seamless gets the merchant (restaurant)
                                                                    simple_parser_feedback,  
                                                                    tabular_simple_parser_process,
                                                                    tabular_simple_parser_process2, 
                                                                    summary_simple_parser_process,
                                                                    simple_parser_process,
                                                                   } ;
        public static final String[] text_models = {
                                                    one_off_text_process1,
                                                    text_simple_parser_process,
                                                    text_nontabular_simple_parser_process,
                                                    };
       
        public static final String[] feed_back_loop_html_models = {
                                                                tabular_simple_parser_process,
                                                                simple_parser_process
                                                            };
        public static final String[] feed_back_loop_text_models = { 
                                                                    text_simple_parser_process,
                                                                    text_nontabular_simple_parser_process
                                                                };
                                                                   

        public static final String[] SIMPLE_PARSERS = { simple_parser_process,
                                                        summary_simple_parser_process,
                                                        tabular_simple_parser_process,
                                                        tabular_simple_parser_process2,
                                                        one_off_parser_process,
                                                        one_off_parser_process2,
                                                        one_off_parser_process3,
                                                        one_off_parser_process4,
                                                        one_off_parser_process5,
                                                        one_off_parser_process6,
                                                        one_off_parser_process7,
                                                        one_off_parser_process8,
                                                        simple_parser_feedback

                                                       };
         public static final String[] PARSER1S={ parser1_process,
                                                parser1_AlternateProcess,
                                               };
        public static final String[] TABULAR_SIMPLE_PARSERS={
                                                            tabular_simple_parser_process,
                                                            tabular_simple_parser_process2,
                                                            };
        public static final String[] ONE_OFF_PARSERS={ one_off_parser_process,
                                                       one_off_parser_process2,
                                                       one_off_parser_process3,
                                                        one_off_parser_process4,
                                                        one_off_parser_process5,
                                                        one_off_parser_process6,
                                                        one_off_parser_process7,
                                                        one_off_parser_process8,
                                                     };
        


	
	public static final String AD_REPLACEMENT_STRING = "<td></td>";
    // public static final String[] FEATURES_FOR_CLU =
    // {"uppercase","capitalized","lowercase"};
       public static final String[] FEATURES_FOR_CLU                      = { "FEA_UPPERCASE", "FEA_CAPITALIZED", "FEA_LOWERCASE" };
        public static final String[] ORDER_NUMBER_UNWANTED_CHARCTES        = { "\n", ":", "#" };
 	public static final String[] NOT_REQUIRED_TABLE_HEADER_WORDS={"item discount"};
        public static final String[] OTHER_LABELS={"Thank you", "Thanks again", "Special Savings", "Legal Agreement", "Group my items", "Last 4 digits", "Order#", "Order #", "Order Date", "Invoice", "Receipt", "Order Date", "Billed To", "Sold By", "Card Number", "DOCUMENT_BEGIN", "tip:", "Ready For Pickup", "Item Description","Card to Charge:","------"};

        
}
