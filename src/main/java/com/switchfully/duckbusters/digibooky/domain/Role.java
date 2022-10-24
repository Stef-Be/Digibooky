package com.switchfully.duckbusters.digibooky.domain;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.switchfully.duckbusters.digibooky.domain.Feature.*;

public enum Role {
    ADMIN(newArrayList(ADD_LIBRARIAN)), MEMBER(newArrayList()), LIBRARIAN(newArrayList(REGISTER_BOOK));

    private List<Feature> featureList;

    Role(List<Feature> featureList) {
        this.featureList = featureList;
    }

    public boolean containsFeature(Feature feature) {
        return featureList.contains(feature);
    }
}
