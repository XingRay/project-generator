package com.xingray.project.generator.core.entity;

import java.io.File;
import java.util.List;

public class FileTreeNode {
    private File file;
    private String content;
    private List<FileTreeNode> children;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<FileTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<FileTreeNode> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "FileTreeNode{" +
                "file=" + file +
                ", content='" + content + '\'' +
                ", children=" + children +
                '}';
    }
}
