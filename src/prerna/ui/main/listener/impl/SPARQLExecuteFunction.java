package prerna.ui.main.listener.impl;

import com.teamdev.jxbrowser.BrowserFunction;

public class SPARQLExecuteFunction implements BrowserFunction {

	@Override
	public Object invoke(Object... arg0) {
		// TODO Auto-generated method stub
		System.out.println("Arguments are " + arg0);
		return "yo baby";
	}
}
