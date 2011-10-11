/*
 * Copyright 2008-2011 Red Hat, Inc, and individual contributors.
 * 
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 * 
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.fnbox.web.ring.processors;

import org.fnbox.core.ClojureRuntime;
import org.fnbox.web.ring.RingMetaData;
import org.fnbox.web.servlet.RingFilter;
import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.as.server.deployment.DeploymentUnitProcessor;
import org.jboss.as.web.deployment.ServletContextAttribute;

public class RingFilterClojureRuntimeInstaller implements DeploymentUnitProcessor {

    
    @Override
    public void deploy(DeploymentPhaseContext phaseContext) throws DeploymentUnitProcessingException {
        DeploymentUnit unit = phaseContext.getDeploymentUnit();
        
        if (!unit.hasAttachment( RingMetaData.ATTACHMENT_KEY )) {
            return;
        }
        
        ClojureRuntime runtime = unit.getAttachment( ClojureRuntime.ATTACHMENT_KEY );
        ServletContextAttribute runtimeAttr = new ServletContextAttribute( RingFilter.CLOJURE_RUNTIME, runtime );
        unit.addToAttachmentList( ServletContextAttribute.ATTACHMENT_KEY, runtimeAttr );
    }
    
    @Override
    public void undeploy(DeploymentUnit context) {

    }
}
