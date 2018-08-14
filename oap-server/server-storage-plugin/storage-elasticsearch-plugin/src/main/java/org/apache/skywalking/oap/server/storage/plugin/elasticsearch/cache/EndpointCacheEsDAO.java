/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.skywalking.oap.server.storage.plugin.elasticsearch.cache;

import org.apache.skywalking.oap.server.core.register.RegisterSource;
import org.apache.skywalking.oap.server.core.register.endpoint.Endpoint;
import org.apache.skywalking.oap.server.core.storage.cache.IEndpointCacheDAO;
import org.apache.skywalking.oap.server.library.client.elasticsearch.ElasticSearchClient;
import org.apache.skywalking.oap.server.storage.plugin.elasticsearch.base.EsDAO;
import org.elasticsearch.action.get.GetResponse;

/**
 * @author peng-yongsheng
 */
public class EndpointCacheEsDAO extends EsDAO implements IEndpointCacheDAO {

    public EndpointCacheEsDAO(ElasticSearchClient client) {
        super(client);
    }

    @Override public int get(String id) {
        try {
            GetResponse response = getClient().get("", id);
            if (response.isExists()) {
                return response.getField(RegisterSource.SEQUENCE).getValue();
            } else {
                return 0;
            }
        } catch (Throwable e) {
            return 0;
        }
    }

    @Override public Endpoint get(int sequence) {
        return null;
    }
}