package org.example.mediators;

import org.apache.synapse.SynapseLog;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.mediators.AbstractMediator;
import org.apache.axiom.om.OMElement;

import javax.xml.namespace.QName;


public class SampleMediator extends AbstractMediator{
    private String removeElementName;

    @Override
    public boolean mediate(MessageContext mc) {

        boolean result = false;

        SynapseLog synLog = getLog(mc);

        if (synLog.isTraceOrDebugEnabled()) {
            synLog.traceOrDebug("Start : Sample Mediator");
            if (synLog.isTraceTraceEnabled()) {
                synLog.traceTrace("Message : " + mc.getEnvelope());
            }
        }

        try {
            org.apache.axis2.context.MessageContext axis2MsgContext = ((Axis2MessageContext) mc)
                    .getAxis2MessageContext();

            // Find the reqMsg element
            QName elementNS = new QName("", removeElementName);
            OMElement reqMsg = axis2MsgContext.getEnvelope().getBody().getFirstElement().getFirstChildWithName(elementNS);

            if (synLog.isTraceOrDebugEnabled()) {
                synLog.traceOrDebug("Removing Element : " + reqMsg.toString());
            }

            // Remove reqMsg element from the context
            if (reqMsg != null) {
                reqMsg.detach();
            }

            // Set the modified payload back to the message context
            // axis2MsgContext.getEnvelope().getBody().addChild(payload);
            result = true;
        } catch (Exception e) {
            log.error("Error: " + e.getMessage(), e);
            result = false;
        }

        if (synLog.isTraceOrDebugEnabled()) {
            synLog.traceOrDebug("End : Sample Mediator");
        }

        return result;
    }

    public String getRemoveElementName() {
        return removeElementName;
    }

    public void setRemoveElementName(String removeElementName) {
        this.removeElementName = removeElementName;
    }

    public String getType() {
        return null;
    }
}
