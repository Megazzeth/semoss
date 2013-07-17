package prerna.algorithm.impl;

import lpsolve.LpSolve;
import lpsolve.LpSolveException;
import prerna.ui.components.api.IPlaySheet;

public class LPOptimizer extends AbstractOptimizer{
	public LpSolve solver;
	
	@Override
	public void setPlaySheet(IPlaySheet playSheet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setupModel() {
		try {
			setVariables();
		} catch (LpSolveException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setConstraints();
		solver.setAddRowmode(false);
		setObjFunction();
	}
	
	@Override
	public void gatherDataSet() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String[] getVariables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVariables() throws LpSolveException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getAlgoName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConstraints() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setObjFunction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute(){
		

		
		solver.setVerbose(LpSolve.IMPORTANT);
		// solve the problem

		try {
			solver.solve();
		} catch (LpSolveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// print solution
		//System.out.println("Value of objective function: " + solver.getObjective());
		
	}

	@Override
	public void deleteModel() {
		// delete the problem and free memory
		
		solver.deleteLp();
	}





}
