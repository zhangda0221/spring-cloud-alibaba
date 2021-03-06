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
package org.springframework.cloud.alibaba.dubbo.metadata.resolver;

import org.springframework.cloud.alibaba.dubbo.annotation.DubboTransported;
import org.springframework.core.env.PropertyResolver;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.core.annotation.AnnotationUtils.getAnnotationAttributes;

/**
 * {@link DubboTransported} annotation attributes resolver
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 */
public class DubboTransportedAttributesResolver {

    private final PropertyResolver propertyResolver;

    public DubboTransportedAttributesResolver(PropertyResolver propertyResolver) {
        this.propertyResolver = propertyResolver;
    }

    public Map<String, Object> resolve(DubboTransported dubboTransported) {
        Map<String, Object> attributes = getAnnotationAttributes(dubboTransported);
        return resolve(attributes);
    }

    public Map<String, Object> resolve(Map<String, Object> attributes) {
        Map<String, Object> resolvedAttributes = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof String) {
                value = propertyResolver.resolvePlaceholders(value.toString());
            }
            resolvedAttributes.put(entry.getKey(), value);
        }
        return resolvedAttributes;
    }
}
