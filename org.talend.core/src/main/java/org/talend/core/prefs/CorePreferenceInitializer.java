// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
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
package org.talend.core.prefs;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.update.core.SiteManager;
import org.eclipse.update.internal.scheduler.SchedulerStartup;
import org.talend.commons.ui.swt.colorstyledtext.ColorManager;
import org.talend.core.CorePlugin;

/**
 * Intializer of core preferences. <br/>
 * 
 * $Id$
 * 
 */
public class CorePreferenceInitializer extends AbstractPreferenceInitializer {

    /**
     * Construct a new CorePreferenceInitializer.
     */
    public CorePreferenceInitializer() {
        super();
    }

    /**
     * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
     */
    @Override
    public void initializeDefaultPreferences() {
        IEclipsePreferences node = new DefaultScope().getNode(CorePlugin.getDefault().getBundle().getSymbolicName());

        // Building temporary files directory path
        IPath tempPath = new Path(System.getProperty("user.dir")).append("temp");
        File tempFile = tempPath.toFile();
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        node.put(ITalendCorePrefConstants.FILE_PATH_TEMP, tempPath.toOSString());

        // TODO CCA : Change this default value
        String os = Platform.getOS();
        if (os.equals(Platform.OS_WIN32)) {
            node.put(ITalendCorePrefConstants.PERL_INTERPRETER, "C:\\Perl\\bin\\perl.exe");
        } else if (os.equals(Platform.OS_LINUX)) {
            node.put(ITalendCorePrefConstants.PERL_INTERPRETER, "/usr/bin/perl");
        }

        node.put(ITalendCorePrefConstants.PREVIEW_LIMIT, "50");
        
        initializeUpdatePreference();

        // Initialize editors properties : line number shown
        final String perlEditorBundleName = "org.epic.perleditor";
        final String editorsBundleName = "org.eclipse.ui.editors";
        // AbstractDecoratedTextEditorPreferenceConstants.EDITOR_LINE_NUMBER_RULER = "lineNumberRuler"
        final String editorLineNumberRuler = "lineNumberRuler";
        IPreferenceStore store = new ScopedPreferenceStore(new InstanceScope(), perlEditorBundleName);
        store.setValue(editorLineNumberRuler, true);
        store = new ScopedPreferenceStore(new InstanceScope(), editorsBundleName);
        store.setValue(editorLineNumberRuler, true);

        // default colors for the ColorStyledText.
        ColorManager.initDefaultColors(CorePlugin.getDefault().getPreferenceStore());
    }

    public void initializeUpdatePreference() {
        IEclipsePreferences nodeScheduler = new DefaultScope().getNode("org.eclipse.update.scheduler"); // NON-NLS-1$
        nodeScheduler.putBoolean(SchedulerStartup.P_ENABLED, true);
        nodeScheduler.put(SchedulerStartup.P_SCHEDULE, SchedulerStartup.VALUE_ON_STARTUP);
        nodeScheduler.putBoolean(SchedulerStartup.P_DOWNLOAD, true);
    }
    
    public static void setProxy(String proxyHost, String proxyPort) {
        SiteManager.setHttpProxyInfo(true, proxyHost, proxyPort);
    }

}
