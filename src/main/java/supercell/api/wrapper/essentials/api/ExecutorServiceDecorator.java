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
 */
package supercell.api.wrapper.essentials.api;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import supercell.api.wrapper.essentials.common.IResponse;
import supercell.api.wrapper.essentials.connector.Connector;
import supercell.api.wrapper.essentials.connector.RequestContext;

class ExecutorServiceDecorator {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(8);

    public <T extends IResponse> Future<T> submit(
            Connector connector, RequestContext requestContext) {
        return EXECUTOR_SERVICE.submit(() -> connector.get(requestContext));
    }
}