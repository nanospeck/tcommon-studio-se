// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package io;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Test;
import org.talend.utils.io.FilesUtils;

/**
 * DOC Administrator class global comment. Detailled comment
 */
public class FilesUtilsTest {

    /**
     * Test method for {@link org.talend.utils.io.FilesUtils#isSVNFolder(java.io.File)}.
     */
    @Test
    public void testIsSVNFolderFile() {
        File file = new File("META-INF");
        assertTrue(file.exists());
        assertFalse(FilesUtils.isSVNFolder(file));
    }

    /**
     * Test method for {@link org.talend.utils.io.FilesUtils#isEmptyFolder(java.lang.String)}.
     */
    @Test
    public void testIsEmptyFolder() {
        File file = new File("temp");
        assertTrue(file.exists());
        assertTrue(FilesUtils.isEmptyFolder("temp"));
    }

    /**
     * Test method for
     * {@link org.talend.utils.io.FilesUtils#copyFolder(java.io.File, java.io.File, boolean, java.io.FileFilter, java.io.FileFilter, boolean)}
     * .
     * 
     * @throws IOException
     */
    @Test
    public void testCopyFolder() throws IOException {
        File source = new File("data");
        File target = new File("temp");
        assertTrue(source.exists());
        assertTrue(target.exists());
        FilesUtils.copyFolder(source, target, false, null, null, false);
        assertTrue(new File("temp//testfile").exists());
    }

    private void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    this.deleteFile(files[i]);
                }
            }
            file.delete();
        }
    }

    /**
     * 
     * DOC Administrator Comment method "clearFolder".
     * 
     * @throws IOException
     */
    @After
    public void clearFolder() throws IOException {
        File file = new File("temp");
        if (file.isDirectory()) {
            File files[] = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFile(files[i]);
            }
        }
    }

    /**
     * Test method for {@link org.talend.utils.io.FilesUtils#copyDirectory(java.io.File, java.io.File)}.
     */
    @Test
    public void testCopyDirectory() {
        File source = new File("data");
        File target = new File("temp");
        assertTrue(source.isDirectory() && target.isDirectory());
        FilesUtils.copyDirectory(source, target);
        assertTrue(new File("temp//data").exists());
    }

    /**
     * Test method for {@link org.talend.utils.io.FilesUtils#createFolder(java.io.File)}.
     */
    @Test
    public void testCreateFolderFile() {
        File file = new File("temp//testfolder");
        assertFalse(file.exists());
        FilesUtils.createFolder(file);
        assertTrue(file.exists());
    }

    /**
     * Test method for {@link org.talend.utils.io.FilesUtils#createFoldersIfNotExists(java.lang.String)}.
     */
    @Test
    public void testCreateFoldersIfNotExistsString() {
        File file = new File("temp//testfolder");
        assertFalse(file.exists());
        FilesUtils.createFolder(file);
        assertTrue(file.exists());
    }

    /**
     * Test method for {@link org.talend.utils.io.FilesUtils#deleteFile(java.io.File, boolean)}.
     * 
     * @throws IOException
     */
    @Test
    public void testDeleteFile() throws IOException {
        File file = new File("temp//testFiles");
        assertFalse(file.exists());
        file.createNewFile();
        assertTrue(file.exists());
        FilesUtils.deleteFile(file, true);
        assertFalse(file.exists());
    }
}
