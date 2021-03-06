/*
 * Copyright 2013 Proofpoint, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.proofpoint.reporting;

import javax.management.AttributeNotFoundException;
import javax.management.MBeanException;
import javax.management.ReflectionException;

import static com.google.common.base.MoreObjects.firstNonNull;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.proofpoint.reporting.ReflectionUtils.invoke;
import static com.proofpoint.reporting.ReportedBean.GET_PREVIOUS_BUCKET;

class BucketedReportedBeanAttribute implements ReportedBeanAttribute
{
    private final ReportedBeanAttribute delegate;
    private final String name;
    private final Object holder;

    BucketedReportedBeanAttribute(Object holder, ReportedBeanAttribute delegate)
    {
        this.holder = checkNotNull(holder, "holder is null");
        this.delegate = delegate;
        name = delegate.getName();
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public Object getValue(Object target)
            throws AttributeNotFoundException, MBeanException, ReflectionException
    {
        return delegate.getValue(invoke(firstNonNull(target, holder), GET_PREVIOUS_BUCKET));
    }
}