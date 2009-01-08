// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.commons.ui.image;

import org.talend.commons.ui.CommonUIPlugin;

/**
 * 
 * DOC smallet ImageProvider class global comment. Detailled comment <br/>
 * 
 * $Id: ImageProvider.java 418 2006-11-13 16:01:26 +0000 (lun., 13 nov. 2006) cantoine $
 * 
 */
public enum EImage implements IImage {

    DEFAULT_IMAGE,
    EMPTY("/icons/empty.gif"),

    SAVE_ICON("/icons/save.png"),
    COPY_ICON("/icons/copy.gif"),
    PASTE_ICON("/icons/paste.gif"),
    CUT_ICON("/icons/cut.png"),
    DELETE_ICON("/icons/delete.gif"),
    RESTORE_ICON("/icons/add.gif"),
    ADD_ICON("/icons/add.gif"),
    ADD_ALL_ICON("/icons/addall.gif"),
    MINUS_ICON("/icons/delete.gif"),
    REFRESH_ICON("/icons/refresh.gif"),
    EDIT_ICON("/icons/write_obj.gif"),
    READ_ICON("/icons/read_obj.gif"),

    RESET_DBTYPES_ICON("/icons/reset_dbtypes.jpg"),

    IMPORT_ICON("/icons/import.gif"),
    EXPORT_ICON("/icons/export.gif"),

    CHECKED_ICON("/icons/checked.gif"),
    UNCHECKED_ICON("/icons/unchecked.gif"),

    ERROR_ICON("/icons/error.gif"),
    WARNING_ICON("/icons/warning.gif"),
    ERROR_SMALL("/icons/error_small.gif"),
    WARNING_SMALL("/icons/warning_small.gif"),
    OK("/icons/ok.png"),

    PARALLEL_EXECUTION("/icons/parallelize.png"),

    UP_ICON("/icons/up.gif"),
    DOWN_ICON("/icons/down.gif"),
    LEFT_ICON("/icons/left.gif"),
    LEFTX_ICON("/icons/leftx.png"),
    RIGHT_ICON("/icons/right.gif"),
    RIGHTX_ICON("/icons/rightx.png"),

    KEY_ICON("/icons/key.gif"),
    HIERARCHY_ICON("/icons/hierarchicalLayout.gif"),

    THREE_DOTS_ICON("/icons/dots_button.gif"),

    PROPERTIES_WIZ("/icons/editpref_wiz.gif"),

    TRACES_EXPAND("/icons/traces_expand.png"),
    TRACES_COLLAPSE("/icons/traces_collapse.png"),

    SUBJOB_EXPAND("/icons/subjob_expand.png"),
    SUBJOB_COLLAPSE("/icons/subjob_collapse.png"),

    COMPACT_VIEW("/icons/compact.png"),
    NO_COMPACT_VIEW("/icons/noCompact.png"),
    TABLE_VIEW("/icons/array_hot.png"),
    NO_TABLE_VIEW("/icons/array.png"),
    COMPOSITE_BACKGROUND("/icons/compositeBackground.jpg"),

    ;

    private String path;

    EImage() {
        this.path = "/icons/unknown.gif";
    }

    EImage(String path) {
        this.path = path;
    }

    /**
     * Getter for path.
     * 
     * @return the path
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Getter for clazz.
     * 
     * @return the clazz
     */
    public Class getLocation() {
        return CommonUIPlugin.class;
    }

}
