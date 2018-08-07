package com.gofar.goplayer;

import java.util.List;

/**
 * @author lcf
 * @date 7/8/2018 上午 11:35
 * @since 1.0
 */
public class SimpleGroup {
    private String name;
    private List<Simple> simples;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Simple> getSimples() {
        return simples;
    }

    public void setSimples(List<Simple> simples) {
        this.simples = simples;
    }
}
