/*
 * Copyright 2024 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.linecorp.armeria.xds.client.endpoint;

import com.linecorp.armeria.client.ClientRequestContext;
import com.linecorp.armeria.client.Endpoint;
import com.linecorp.armeria.client.endpoint.AbstractEndpointSelector;
import com.linecorp.armeria.client.endpoint.EndpointGroup;
import com.linecorp.armeria.common.annotation.Nullable;

final class XdsEndpointSelector extends AbstractEndpointSelector {

    private final ClusterManager clusterManager;

    XdsEndpointSelector(ClusterManager clusterManager, EndpointGroup endpointGroup) {
        super(endpointGroup);
        this.clusterManager = clusterManager;
        initialize();
    }

    @Override
    @Nullable
    public Endpoint selectNow(ClientRequestContext ctx) {
        return clusterManager.selectNow(ctx);
    }
}