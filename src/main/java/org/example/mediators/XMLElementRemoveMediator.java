package org.example.mediators;

import org.apache.synapse.SynapseLog;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.mediators.AbstractMediator;
import org.apache.axiom.om.OMElement;

import javax.xml.namespace.QName;


public class XMLElementRemoveMediator extends AbstractMediator{

    private String removeElementName;

    @Override
    public boolean mediate(MessageContext mc) {
        SynapseLog synLog = getLog(mc);

        if (synLog.isTraceOrDebugEnabled()) {
            synLog.traceOrDebug("Start : XMLElementRemove mediator");
            if (synLog.isTraceTraceEnabled()) {
                synLog.traceTrace("Message : " + mc.getEnvelope());
            }
        }

        try {
            org.apache.axis2.context.MessageContext axis2MsgContext = ((Axis2MessageContext) mc)
                    .getAxis2MessageContext();

            // OMElement payload = axis2MsgContext.getEnvelope().getBody().getFirstElement();
            //  synLog.traceOrDebug("Payload : " + payload.toString());

            // Find the reqMsg element and remove it
            QName elementNS = new QName("", removeElementName);
            OMElement reqMsg = axis2MsgContext.getEnvelope().getBody().getFirstElement().getFirstChildWithName(elementNS);
            // OMElement reqMsg = payload.getFirstChildWithName(new javax.xml.namespace.QName("", "reqMsg"));
            synLog.traceOrDebug("Removing Element : " + reqMsg.toString());

            if (reqMsg != null) {
                reqMsg.detach();
                // synLog.traceOrDebug("Modified Payload : " + payload.toString());
            }

            // Set the modified payload back to the message context
            // axis2MsgContext.getEnvelope().getBody().addChild(payload);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage(), e);
            return false;
        }


        if (synLog.isTraceOrDebugEnabled()) {
            synLog.traceOrDebug("End : XMLElementRemove mediator");
        }
        return true;
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
