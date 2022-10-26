package com.switchfully.duckbusters.digibooky.domain.person;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.switchfully.duckbusters.digibooky.domain.person.Feature.*;

public enum Role {
    ADMIN(newArrayList(ADD_LIBRARIAN, VIEW_MEMBERS)), MEMBER(newArrayList(LOAN_BOOK, SEE_BORROWERS)), LIBRARIAN(newArrayList(CRUD_BOOK, VIEW_LOANS));

    private List<Feature> featureList;

    Role(List<Feature> featureList) {
        this.featureList = featureList;
    }

    public boolean containsFeature(Feature feature) {
        return featureList.contains(feature);
    }
}
