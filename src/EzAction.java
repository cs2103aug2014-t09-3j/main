import java.util.*;

/**
 * the EzAction Object is only used to store the information of action, which have been done.
 * targets is the list of tasks before doing the action
 * results is the list of task after the action
 * 
 * for ADD action, targets is NULL and results is an 1-element array of task.
 * for UPDATE action, targets and results are the list of task before and after doing the action respectively.
 * for DELETE action, targets is the list of tasks that will be delete while results is NULL.
 * for DONE action, it is the same as UPDATE. 
 * 
 */

/**
 * @author Khanh
 * 
 */


public class EzAction {
	
	private TypeOfAction action;
	private ArrayList<EzTask> targets;
	private ArrayList<EzTask> results;
	private String feedback;
	
	/**
	 *  These attributes are necessary for ADD, UPDATE, DELETE and DONE.
	 *  There will be more attributes for other Actions.
	 */
	

	public TypeOfAction getAction() {
		return action;
	}

	public void setAction(TypeOfAction action) {
		this.action = action;
	}
	
	public ArrayList<EzTask> getTargets() {
		return targets;
	}
	
	public void setTargets(ArrayList<EzTask> targets) {
		this.targets = targets;
	}
	

	public ArrayList<EzTask> getResults() {
		return results;
	}

	public void setResults(ArrayList<EzTask> results) {
		this.results = results;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	
}
