package com.xingray.project.generator.core.entity;

import com.xingray.java.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileTreeNode {
    private File file;
    private String content;
    private List<FileTreeNode> children;

    private FileTreeNode parent;

    private boolean isNode;

    public File getFile() {
        return file;
    }

    public void setNode(File node) {
        this.file = node;
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

    public boolean isNode() {
        return isNode;
    }

    public void setNode(boolean isNode) {
        this.isNode = isNode;
    }

    public FileTreeNode getParent() {
        return parent;
    }

    public void setParent(FileTreeNode parent) {
        this.parent = parent;
    }

    public FileTreeNode(File file, String content, List<FileTreeNode> children, FileTreeNode parent, boolean isNode) {
        this.file = file;
        this.content = content;
        this.children = children;
        this.parent = parent;
        this.isNode = isNode;
    }

    @Override
    public String toString() {
        return "FileTreeNode{" +
                "file=" + file +
                ", content='" + (content == null ? "" : content.substring(0, 10)) + '\'' +
                ", children=" + children +
                ", isFile=" + isNode +
                '}';
    }

    public static FileTreeNode createTree(String path) {
        return createTree(new File(path));
    }

    public static FileTreeNode createTree(File file) {
        return new FileTreeNode(file, null, new ArrayList<>(), null, false);
    }

    public static FileTreeNode createTree(FileTreeNode parent, String name) {
        if (parent.isNode) {
            throw new IllegalArgumentException("parent is node, only tree can add child");
        }
        File file = new File(parent.getFile(), name);
        FileTreeNode node = new FileTreeNode(file, null, new ArrayList<>(), parent, false);
        parent.getChildren().add(node);
        return node;
    }

    public static FileTreeNode createNode(String path) {
        return createNode(new File(path), null);
    }

    public static FileTreeNode createNode(File file) {
        return createNode(file, null);
    }

    public static FileTreeNode createNode(File file, String content) {
        return new FileTreeNode(file, content, null, null, true);
    }

    public static FileTreeNode createNode(FileTreeNode parent, String name, String content) {
        if (parent.isNode) {
            throw new IllegalArgumentException("parent is node, only tree can add child");
        }
        File file = new File(parent.getFile(), name);
        FileTreeNode node = new FileTreeNode(file, content, null, parent, true);
        parent.getChildren().add(node);
        return node;
    }

    public void write() throws IOException {
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        FileUtil.deleteFileRecursive(file);

        if (isNode()) {
            file.createNewFile();
            Files.writeString(file.toPath(), content == null ? "" : content);
        } else {
            file.mkdir();
            for (FileTreeNode node : children) {
                node.write();
            }
        }
    }

    public FileTreeNode addAndGetChildTree(String name) {
        return createTree(this, name);
    }

    public FileTreeNode addChildTree(String name) {
        addAndGetChildTree(name);
        return this;
    }

    public FileTreeNode addAndGetChildNode(String name) {
        return addAndGetChildNode(name, null);
    }

    public FileTreeNode addAndGetChildNode(String name, String content) {
        return createNode(this, name, content);
    }

    public FileTreeNode addChildNode(String name) {
        return addChildNode(name, null);
    }

    public FileTreeNode addChildNode(String name, String content) {
        addAndGetChildNode(name, content);
        return this;
    }

    public FileTreeNode parent() {
        return parent;
    }

    public FileTreeNode child(String name) {
        for (FileTreeNode node : children) {
            if (node.getFile().getName().equals(name)) {
                return node;
            }
        }
        return null;
    }

    public FileTreeNode descendant(String... names) {
        FileTreeNode node = this;
        for (String name : names) {
            node = node.child(name);
        }
        return node;
    }

    public FileTreeNode addAndGetDescendantTree(String... names) {
        FileTreeNode node = this;
        for (String name : names) {
            node = node.addAndGetChildTree(name);
        }
        return node;
    }

    public FileTreeNode addDescendantTree(String... names) {
        addAndGetDescendantTree(names);
        return this;
    }

    public FileTreeNode addChild(FileTreeNode child) {
        child.setParent(this);
        children.add(child);
        return this;
    }
}
