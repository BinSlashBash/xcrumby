/*
 * Decompiled with CFR 0_110.
 */
package org.junit.rules;

import java.io.File;
import java.io.IOException;
import org.junit.rules.ExternalResource;

public class TemporaryFolder
extends ExternalResource {
    private File folder;
    private final File parentFolder;

    public TemporaryFolder() {
        this(null);
    }

    public TemporaryFolder(File file) {
        this.parentFolder = file;
    }

    private File createTemporaryFolderIn(File file) throws IOException {
        file = File.createTempFile("junit", "", file);
        file.delete();
        file.mkdir();
        return file;
    }

    private boolean isLastElementInArray(int n2, String[] arrstring) {
        if (n2 == arrstring.length - 1) {
            return true;
        }
        return false;
    }

    private void recursiveDelete(File file) {
        File[] arrfile = file.listFiles();
        if (arrfile != null) {
            int n2 = arrfile.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                this.recursiveDelete(arrfile[i2]);
            }
        }
        file.delete();
    }

    @Override
    protected void after() {
        this.delete();
    }

    @Override
    protected void before() throws Throwable {
        this.create();
    }

    public void create() throws IOException {
        this.folder = this.createTemporaryFolderIn(this.parentFolder);
    }

    public void delete() {
        if (this.folder != null) {
            this.recursiveDelete(this.folder);
        }
    }

    public File getRoot() {
        if (this.folder == null) {
            throw new IllegalStateException("the temporary folder has not yet been created");
        }
        return this.folder;
    }

    public File newFile() throws IOException {
        return File.createTempFile("junit", null, this.getRoot());
    }

    public File newFile(String string2) throws IOException {
        File file = new File(this.getRoot(), string2);
        if (!file.createNewFile()) {
            throw new IOException("a file with the name '" + string2 + "' already exists in the test folder");
        }
        return file;
    }

    public File newFolder() throws IOException {
        return this.createTemporaryFolderIn(this.getRoot());
    }

    public File newFolder(String string2) throws IOException {
        return this.newFolder(new String[]{string2});
    }

    public /* varargs */ File newFolder(String ... arrstring) throws IOException {
        File file = this.getRoot();
        for (int i2 = 0; i2 < arrstring.length; ++i2) {
            String string2 = arrstring[i2];
            if ((file = new File(file, string2)).mkdir() || !this.isLastElementInArray(i2, arrstring)) continue;
            throw new IOException("a folder with the name '" + string2 + "' already exists");
        }
        return file;
    }
}

