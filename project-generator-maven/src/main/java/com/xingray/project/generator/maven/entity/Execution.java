package com.xingray.project.generator.maven.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class Execution {
    private String phase;

    @JacksonXmlElementWrapper(localName = "goals")
    @JacksonXmlProperty(localName = "goal")
    private List<String> goals;

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public List<String> getGoals() {
        return goals;
    }

    public void setGoals(List<String> goals) {
        this.goals = goals;
    }

    @Override
    public String toString() {
        return "Execution{" +
                "phase='" + phase + '\'' +
                ", goals=" + goals +
                '}';
    }
}
