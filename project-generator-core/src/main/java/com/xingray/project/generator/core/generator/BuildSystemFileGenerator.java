package com.xingray.project.generator.core.generator;

import com.xingray.project.generator.core.entity.FileTreeNode;
import com.xingray.project.generator.core.entity.Project;

public interface BuildSystemFileGenerator {
    FileTreeNode generatorBuildSystemFile(Project project);
}
