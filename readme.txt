-------- README FOR ML BACKTEST --------

The ML Backtest Package is used to summarize the html output files from ML. These files are generated from a set of receipts (eg, 1001 set). 

--- Contents ---

The package contains 3 important classes. 
	* Receipt class is the representation of the output/results of one receipt.
	* The ModelSummary class is the representation of the result from one set of models (it contains collection of Reciepts)
	* The OutputReport class is a class the helps to put multiple ModelSummary's together in a printable summary.
	
--- Use ---

-> CONFIGURATION FILE
There is one config file that is important to use when running (ModelOutputs.txt). Each line of the file represents the outputs from one Model. An example of what a line should look like is: 

v4.0.7,0,1,2013-01-28 Snapshot 1001.txt,htmloutput - 4.0.7+textpipeline(switched order)

It is a comma seperated file. 	
	Column 1. The name of the model (can be anything)
	Column 2. 0 -> Pipeline; 1 -> Isolated Model
	Column 3. 0 -> An old model; 1 -> New or candidate model
	Column 4. The file that contains the list of receipts that the model was run on. It assumes that the file is in the directory declared in OutputReport class under String mainDir
	Column 5. The directory of output files for that model. It assumes that the file is in the directory declared in OutputReport class under String mainDir
	
Any line beginning with "//" will be skipped (ie, it is commented out)

-> ALTERATION AND CHANGING THE CLASSES
The main place to make changes in the main method of the OutputReport class. See that section for details, as it is well-commented.

The second place place to do some changes is in the ModelSummary class, in the compareModels and compareToTruth methods. These two actually do the comparision. See those two methods for comments.


-> RUNNING
The entry point to the package is the main method OutputReport. Running this method will print the results to the console. 
