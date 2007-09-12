// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006-2007 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.commons.ui.swt.tableviewer.behavior;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TableItem;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn;
import org.talend.commons.ui.swt.tableviewer.data.AccessorUtils;
import org.talend.commons.ui.swt.tableviewer.data.ModifiedObjectInfo;

/**
 * 
 * Default implementation of <code>ICellModifier</code> used by <code>TableViewerCreator</code>. This
 * implementation retrieve or modify values (using accesors defined in <code>TableViewerCreatorColumn</code>) on data
 * object. <br/>
 * 
 * $Id$
 * 
 * @see ICellModifier
 * 
 */
public class DefaultCellModifier implements ICellModifier {

    private TableViewerCreator tableViewerCreator;

    private ListenerList cellEditorAppliedListeners = new ListenerList();

    public DefaultCellModifier(TableViewerCreator tableViewerCreator) {
        super();
        this.tableViewerCreator = tableViewerCreator;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
     */
    public boolean canModify(Object bean, String idColumn) {
        TableViewerCreatorColumn column = tableViewerCreator.getColumn(idColumn);
        if (column.getColumnCellModifier() != null) {
            return column.getColumnCellModifier().canModify(bean);
        }
        return tableViewerCreator.getColumn(idColumn).isModifiable();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public Object getValue(Object bean, String idColumn) {
        TableViewerCreatorColumn column = tableViewerCreator.getColumn(idColumn);
        ModifiedObjectInfo modifiedObjectInfo = this.tableViewerCreator.getModifiedObjectInfo();
        modifiedObjectInfo.setCurrentModifiedBean(bean);
        modifiedObjectInfo.setCurrentModifiedColumn(column);
        modifiedObjectInfo.setCurrentModifiedIndex(this.tableViewerCreator.getInputList().indexOf(bean));

        Object returnValue = null;
        if (column.getColumnCellModifier() != null) {
            returnValue = column.getColumnCellModifier().getValue(bean);
        }
        if (returnValue == null) {
            Object value = AccessorUtils.get(bean, column);

            if (column.getCellEditorValueAdapter() != null) {
                returnValue = column.getCellEditorValueAdapter().getCellEditorTypedValue(column.getCellEditor(), value);
            } else {
                returnValue = value;
            }
            if (returnValue == null && column.getDefaultInternalValue() != null) {
                returnValue = column.getDefaultInternalValue();
            }
            modifiedObjectInfo.setOriginalPropertyBeanValue(returnValue);
            modifiedObjectInfo.setPreviousPropertyBeanValue(returnValue);
            // System.out.println("getValue : value=" + returnValue);
        }
        return returnValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object, java.lang.String, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public void modify(Object tableItem, String idColumn, Object value) {
        Object bean = ((TableItem) tableItem).getData();
        TableViewerCreatorColumn column = tableViewerCreator.getColumn(idColumn);

        boolean modifiedByColumnCellModifier = false;
        if (column.getColumnCellModifier() != null) {
            modifiedByColumnCellModifier = column.getColumnCellModifier().modify(bean, value);
        }

        if (!modifiedByColumnCellModifier && column.getBeanPropertyAccessors() != null) {
            Object typedValue = null;
            if (column.getCellEditorValueAdapter() != null) {
                typedValue = column.getCellEditorValueAdapter().getOriginalTypedValue(column.getCellEditor(), value);
            } else {
                typedValue = value;
            }
            if (typedValue == null && column.getDefaultInternalValue() != null) {
                typedValue = column.getDefaultInternalValue();
            }
            Object previousValue = AccessorUtils.get(bean, column);
            tableViewerCreator.setBeanValue(column, bean, typedValue, true);
            fireCellEditorApplied((TableItem) tableItem, bean, column, value, previousValue, typedValue);
        }
        ModifiedObjectInfo modifiedObjectInfo = this.tableViewerCreator.getModifiedObjectInfo();
        modifiedObjectInfo.setPreviousModifiedBean(bean);
        modifiedObjectInfo.setPreviousModifiedIndex(modifiedObjectInfo.getCurrentModifiedIndex());
        modifiedObjectInfo.setCurrentModifiedBean(null);
        modifiedObjectInfo.setPreviousModifiedColumn(modifiedObjectInfo.getCurrentModifiedColumn());
        modifiedObjectInfo.setCurrentModifiedColumn(null);
        modifiedObjectInfo.setOriginalPropertyBeanValue(null);
    }

    /**
     * 
     * DOC amaumont Comment method "fireCellEditorApplied".
     * 
     * @param tableItem
     * @param bean
     * @param idColumn
     * @param cellEditorAppliedValue
     * @param newValue
     */
    private void fireCellEditorApplied(TableItem tableItem, Object bean, TableViewerCreatorColumn column,
            Object cellEditorAppliedValue, Object previousValue, Object newValue) {
        TableCellValueModifiedEvent event = new TableCellValueModifiedEvent(tableItem, bean, column, cellEditorAppliedValue,
                newValue);
        final Object[] listenerArray = cellEditorAppliedListeners.getListeners();
        for (int i = 0; i < listenerArray.length; i++) {
            ((ITableCellValueModifiedListener) listenerArray[i]).cellValueModified(event);
        }
        if (column.getCellEditorAppliedListener() != null) {
            column.getCellEditorAppliedListener().cellValueModified(event);
        }
    }

    public void addCellEditorAppliedListener(ITableCellValueModifiedListener lineSelectionListener) {
        this.cellEditorAppliedListeners.add(lineSelectionListener);
    }

    public void removeCellEditorAppliedListener(ITableCellValueModifiedListener cellEditorAppliedListener) {
        this.cellEditorAppliedListeners.remove(cellEditorAppliedListener);
    }

    /**
     * Getter for tableViewerCreator.
     * 
     * @return the tableViewerCreator
     */
    public TableViewerCreator getTableViewerCreator() {
        return this.tableViewerCreator;
    }

}
