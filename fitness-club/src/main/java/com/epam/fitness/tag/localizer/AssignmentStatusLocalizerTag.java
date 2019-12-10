package com.epam.fitness.tag.localizer;

import com.epam.fitness.entity.assignment.AssignmentStatus;
import javax.servlet.jsp.JspException;

public class AssignmentStatusLocalizerTag extends AbstractLocalizer {

    private static final String NEW_STATUS = "new_status";
    private static final String ACCEPTED_STATUS = "accepted_status";
    private static final String CHANGED_STATUS = "changed_status";
    private static final String CANCELED_STATUS = "canceled_status";

    @Override
    protected String getAttributeName(String statusValue) throws JspException{
        AssignmentStatus status = AssignmentStatus.valueOf(statusValue);
        String attributeName;
        switch (status){
            case NEW:
                attributeName = NEW_STATUS;
                break;
            case ACCEPTED:
                attributeName = ACCEPTED_STATUS;
                break;
            case CHANGED:
                attributeName = CHANGED_STATUS;
                break;
            case CANCELED:
                attributeName = CANCELED_STATUS;
                break;
            default:
                throw new JspException("Invalid status value: " + statusValue);
        }
        return attributeName;
    }

}