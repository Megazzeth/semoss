package prerna.algorithm.impl;

import lpsolve.LpSolveException;
import prerna.algorithm.api.IAlgorithm;

public abstract class AbstractOptimizer implements IAlgorithm{

	public abstract void setupModel();
	
	public abstract void gatherDataSet();
	
	public abstract void setConstraints();
	
	public abstract void setObjFunction();
	
	public abstract void deleteModel();
	
	public abstract void setVariables() throws LpSolveException;
	
	@Override
	public abstract void execute();
	
	
}
