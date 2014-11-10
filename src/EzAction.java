import java.util.*;

//@author A0112129U
/**
 * the EzAction Object is only used to store the information of action, which
 * have been done. targets is the list of tasks before doing the action results
 * is the list of task after the action
 * 
 * for ADD action, targets is NULL and results is an 1-element array of task.
 * for UPDATE action, targets and results are the list of task before and after
 * doing the action respectively. for DELETE action, targets is the list of
 * tasks that will be delete while results is NULL. for DONE, UNDONE and REMOVE
 * action, they are the same as UPDATE.
 * 
 */

public class EzAction {

	private TypeOfAction action;
	private ArrayList<EzTask> targets;
	private ArrayList<EzTask> results;
	private String feedback;
	private TypeOfSort typeSort;
	private int pageNumber;

	/**
	 * @return the type of the action
	 */
	public TypeOfAction getAction() {
		return action;
	}

	/**
	 * set the type of the action
	 * 
	 * @param action
	 *            is the type of the action
	 */
	public void setAction(TypeOfAction action) {
		this.action = action;
	}

	/**
	 * @return the target of the action
	 */
	public ArrayList<EzTask> getTargets() {
		return targets;
	}

	/**
	 * set the target tasks of the action
	 * 
	 * @param targets
	 *            is the list of the target tasks
	 */
	public void setTargets(ArrayList<EzTask> targets) {
		this.targets = targets;
	}

	/**
	 * @return the result tasks of the action
	 */
	public ArrayList<EzTask> getResults() {
		return results;
	}

	/**
	 * set the result tasks of the action
	 * 
	 * @param results
	 *            is the list of the result tasks
	 */
	public void setResults(ArrayList<EzTask> results) {
		this.results = results;
	}

	/**
	 * @return the feedback of the action
	 */
	public String getFeedback() {
		return feedback;
	}

	/**
	 * set feedback of the action
	 * 
	 * @param feedback
	 */
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	/**
	 * @return the type of sorting for SORT action
	 */
	public TypeOfSort getTypeSort() {
		return typeSort;
	}

	/**
	 * set the type of sorting for SORT action
	 * 
	 * @param typeSort
	 */
	public void setTypeSort(TypeOfSort typeSort) {
		this.typeSort = typeSort;
	}

	/**
	 * @return the page number for PAGE action
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * set the page number for PAGE action
	 * 
	 * @param pageNumber
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

}
