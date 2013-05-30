package prerna.algorithm.api;

import prerna.ui.components.api.IPlaySheet;

// primary interface that can be utilized to apply algorithms
// an algorithm requires a number of things
// it might suggest a type of algorithm - that may be applied to 
// i.e. is it a graphical algorithm etc. 
// it might suggest what are the variables that are needed
// and it might suggest how the output is going to be
// I think this will become part of the IPlaySheet interface in terms of 
// what types of algorithm we apply
// the information itself is derived from the graph
// and given to this algorithm
// which can process it and then display what it needs to

/**
 * This interface is used to standardize the functionality of the algorithms applied to various play sheets.  Algorithms are, 
 * in general terms, a way of using the data and attributes of a play sheet to gather insight that is not immediately apparent 
 * from the play sheet by itself.
 *
 */
public interface IAlgorithm {
	
	// sets the reference to playsheet
	/**
	 * Sets the play sheet that the algorithm is to be associated with and run on.
	 * @param playSheet the play sheet that the algorithm is to be run on
	 */
	public void setPlaySheet(IPlaySheet playSheet);
	
	// asks for any variables required
	/**
	 * Gets a String Array of variables that need to be fed into the algorithm in order for the algorithm to be sucessfully run.
	 * @return names of variables that the algorithm requires
	 */
	public String[] getVariables();
	
	// execute the algorithm for now
	/**
	 * Runs the algorithm.
	 */
	public void execute();
	
	// Give a name for the algorithm
	/**
	 * Gets the name of the algorithm in String form.
	 * @return the name of the algorithm
	 */
	public String getAlgoName();
	
}
