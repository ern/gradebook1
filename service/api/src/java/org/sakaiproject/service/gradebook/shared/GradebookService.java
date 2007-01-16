/**********************************************************************************
 *
 * $Id$
 *
 ***********************************************************************************
 *
 * Copyright (c) 2005, 2006 The Regents of the University of California, The MIT Corporation
 *
 * Licensed under the Educational Community License Version 1.0 (the "License");
 * By obtaining, using and/or copying this Original Work, you agree that you have read,
 * understand, and will comply with the terms and conditions of the Educational Community License.
 * You may obtain a copy of the License at:
 *
 *      http://www.opensource.org/licenses/ecl1.php
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 **********************************************************************************/
package org.sakaiproject.service.gradebook.shared;

import java.util.*;

/**
 * This is the externally exposed API of the gradebook application.
 * 
 * This interface is principally intended for clients of application services --
 * that is, clients who want to "act like the Gradebook would" to automate what
 * would normally be done in the UI, including any authorization checks.
 * 
 * As a result, these methods may throw security exceptions. Call the service's
 * authorization-check methods if you want to avoid them.
 * 
 * <p>WARNING: For documentation of the deprecated methods, please see the
 * service interfaces which own them.
 */
public interface GradebookService {
	// Application service hooks.

	/**
     * Checks to see whether a gradebook with the given uid exists.
     *
     * @param gradebookUid The gradebook UID to check
     * @return Whether the gradebook exists
     */
    public boolean isGradebookDefined(String gradebookUid);

	/**
	 * Check to see if the current user is allowed to grade the given student in
	 * the given gradebook. This will give clients a chance to avoid a security
	 * exception.
	 */
	public boolean isUserAbleToGradeStudent(String gradebookUid,
			String studentUid);

	/**
	 * @return Returns a list of Assignment objects describing the assignments
	 *         that are currently defined in the given gradebook.
	 */
	public List getAssignments(String gradebookUid)
			throws GradebookNotFoundException;

	/**
	 * Besides the declared exceptions, possible runtime exceptions include:
	 * <ul>
	 * <li> SecurityException - If the current user is not authorized to grade
	 * the student
	 * </ul>
	 * 
	 * @return Returns the current score for the student, or null if no score
	 *         has been assigned yet.
	 */
	public Double getAssignmentScore(String gradebookUid,
			String assignmentName, String studentUid)
			throws GradebookNotFoundException, AssessmentNotFoundException;

	/**
	 * Besides the declared exceptions, possible runtime exceptions include:
	 * <ul>
	 * <li> SecurityException - If the current user is not authorized to grade
	 * the student, or if the assignment is externally maintained.
	 * <li> StaleObjectModificationException - If the student's scores have been
	 * edited by someone else during this transaction.
	 * </ul>
	 * 
	 * @param clientServiceDescription
	 *            What to display as the programmatic source of the score (e.g.,
	 *            "Message Center").
	 */
	public void setAssignmentScore(String gradebookUid, String assignmentName,
			String studentUid, Double score, String clientServiceDescription)
			throws GradebookNotFoundException, AssessmentNotFoundException;

	/**
	 * Check to see if an assignment with the given name already exists in the
	 * given gradebook. This will give clients a chance to avoid the
	 * ConflictingAssignmentNameException.
	 */
	public boolean isAssignmentDefined(String gradebookUid,
			String assignmentTitle) throws GradebookNotFoundException;

	// Site management hooks.

	/**
	 * @deprecated Replaced by
	 *             {@link GradebookFrameworkService#addGradebook(String, String)}
	 */
	public void addGradebook(String uid, String name);

	/**
	 * @deprecated Replaced by
	 *             {@link GradebookFrameworkService#deleteGradebook(String)}
	 */
	public void deleteGradebook(String uid) throws GradebookNotFoundException;

	/**
	 * @deprecated Replaced by
	 *             {@link GradebookFrameworkService#setAvailableGradingScales(Collection)}
	 */
	public void setAvailableGradingScales(Collection gradingScaleDefinitions);

	/**
	 * @deprecated Replaced by
	 *             {@link GradebookFrameworkService#setDefaultGradingScale(String)}
	 */
	public void setDefaultGradingScale(String uid);

	// External assessment management hooks.

	/**
	 * @deprecated Replaced by {@link GradebookExternalAssessmentService#addExternalAssessment(String, String, String, String, double, Date, String)}
	 */
	public void addExternalAssessment(String gradebookUid, String externalId,
			String externalUrl, String title, double points, Date dueDate,
			String externalServiceDescription)
			throws GradebookNotFoundException,
			ConflictingAssignmentNameException, ConflictingExternalIdException,
			AssignmentHasIllegalPointsException;

	/**
	 * @deprecated Replaced by {@link GradebookExternalAssessmentService#updateExternalAssessment(String, String, String, String, double, Date)}
	 */
	public void updateExternalAssessment(String gradebookUid,
			String externalId, String externalUrl, String title, double points,
			Date dueDate) throws GradebookNotFoundException,
			AssessmentNotFoundException, ConflictingAssignmentNameException,
			AssignmentHasIllegalPointsException;

	/**
	 * @deprecated Replaced by {@link GradebookExternalAssessmentService#removeExternalAssessment(String, String)}
	 */
	public void removeExternalAssessment(String gradebookUid, String externalId)
			throws GradebookNotFoundException, AssessmentNotFoundException;

	/**
	 * @deprecated Replaced by {@link GradebookExternalAssessmentService#updateExternalAssessmentScore(String, String, String, Double)}
	 */
	public void updateExternalAssessmentScore(String gradebookUid,
			String externalId, String studentUid, Double points)
			throws GradebookNotFoundException, AssessmentNotFoundException;

	/**
	 * @deprecated Replaced by {@link GradebookExternalAssessmentService#updateExternalAssessmentScores(String, String, Map)}
	 */
	public void updateExternalAssessmentScores(String gradebookUid,
			String externalId, Map studentUidsToScores)
			throws GradebookNotFoundException, AssessmentNotFoundException;

	/**
	 * @deprecated Replaced by {@link GradebookExternalAssessmentService#isExternalAssignmentDefined(String, String)}
	 */
	public boolean isExternalAssignmentDefined(String gradebookUid,
			String externalId) throws GradebookNotFoundException;

}
