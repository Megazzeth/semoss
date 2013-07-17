package prerna.ui.helpers;

import prerna.algorithm.api.IAlgorithm;

public class AlgorithmRunner implements Runnable{

	IAlgorithm algo = null;
	
	public AlgorithmRunner(IAlgorithm algo)
	{
		this.algo = algo;
	}

	@Override
	public void run() {
		algo.execute();
	}
	
}
