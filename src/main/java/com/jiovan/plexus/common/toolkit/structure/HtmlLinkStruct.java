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

package com.jiovan.plexus.common.toolkit.structure;


import javax.swing.text.html.HTML;
import jakarta.annotation.Nullable;
import lombok.Getter;


/**
 * Structure for HTML "&lt;link&gt;" node
 *
 * @author David Hsing
 */
@Getter
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public class HtmlLinkStruct extends HtmlTagStruct {
    public static final String DEFAULT_REL = "stylesheet";    // $NON-NLS-1$
    public static final String DEFAULT_TYPE = "text/css";    // $NON-NLS-1$
    private String href;
    private String rel;
    private String type;

    /**
     * Creates a link structure with the default relationship and type.
     */
    public HtmlLinkStruct() {
        this(null, DEFAULT_REL, DEFAULT_TYPE);
    }

    /**
     * Creates a link structure with the given target URL and the default relationship and type.
     *
     * @param href the target URL, or null when absent
     */
    public HtmlLinkStruct(@Nullable String href) {
        this(href, DEFAULT_REL, DEFAULT_TYPE);
    }

    /**
     * Creates a link structure with the given target URL, relationship, and type.
     *
     * @param href the target URL, or null when absent
     * @param rel the relationship type, or null when absent
     * @param type the content type, or null when absent
     */
    public HtmlLinkStruct(@Nullable String href, @Nullable String rel, @Nullable String type) {
        super.setTag(HTML.Tag.LINK);
        setHref(href);
        setRel(rel);
        setType(type);
    }

    /**
     * Sets the href attribute.
     *
     * @param href the target URL, or null when absent
     */
    public void setHref(@Nullable String href) {
        this.href = href;
        super.addAttribute(HTML.Attribute.HREF, href);
    }

    /**
     * Sets the rel attribute.
     *
     * @param rel the relationship type, or null when absent
     */
    public void setRel(@Nullable String rel) {
        this.rel = rel;
        super.addAttribute(HTML.Attribute.REL, rel);
    }

    /**
     * Sets the type attribute.
     *
     * @param type the content type, or null when absent
     */
    public void setType(@Nullable String type) {
        this.type = type;
        super.addAttribute(HTML.Attribute.TYPE, type);
    }
}
