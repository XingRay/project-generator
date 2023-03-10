package com.xingray.project.generator.core.generator.impl;

import com.xingray.project.generator.core.entity.FileTreeNode;
import com.xingray.project.generator.core.entity.Project;
import com.xingray.project.generator.core.generator.BuildSystemFileGenerator;

public class NoneBuildSystemFileGenerator implements BuildSystemFileGenerator {
    @Override
    public FileTreeNode generatorBuildSystemFile(Project project) {
        return null;
    }
}
