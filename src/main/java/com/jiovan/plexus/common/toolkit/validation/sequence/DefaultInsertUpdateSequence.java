/*
 * Copyright (c) 2016 Jiovan Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jiovan.plexus.common.toolkit.validation.sequence;


import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;
import com.jiovan.plexus.common.toolkit.validation.group.InsertGroup;
import com.jiovan.plexus.common.toolkit.validation.group.UpdateGroup;


/**
 * Validation sequence for default and persistence insert and update
 *
 * @author David Hsing
 *
 * @see jakarta.validation.Validator
 * @see jakarta.validation.Valid
 * @see "org.springframework.validation.Validator"
 * @see "org.springframework.validation.annotation.Validated"
 */
@GroupSequence(value = {Default.class, InsertGroup.class, UpdateGroup.class})
@SuppressWarnings("unused")
public interface DefaultInsertUpdateSequence {
}
