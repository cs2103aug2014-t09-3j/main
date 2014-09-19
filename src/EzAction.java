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
 */

/**
 * @author Khanh
 *
 */


public class EzAction {
	
	private TypeOfAction action;
	private ArrayList<EzTask> targets;
	private ArrayList<EzTask> results;
	
	/**
	 *  These attributes are necessary for ADD, UPDATE, DELETE and DONE.
	 *  There will be more attributes for other Actions.
	 */
	
	
	/**
	 * @return the action
	 */
	public TypeOfAction getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(TypeOfAction action) {
		this.action = action;
	}
	/**
	 * @return the targets
	 */
	
	public ArrayList<EzTask> getTargets() {
		return targets;
	}
	/**
	 * @param targets the targets to set
	 */
	public void setTargets(ArrayList<EzTask> targets) {
		this.targets = targets;
	}
	
	/**
	 * @return the results
	 */
	public ArrayList<EzTask> getResults() {
		return results;
	}
	/**
	 * @param results the results to set
	 */
	public void setResults(ArrayList<EzTask> results) {
		this.results = results;
	}
	
	
}
