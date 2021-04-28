package com.github.PrzeBarCore.springlibraryworkshop.utils;

public class EntitiesStringsPatterns {
    public static final String forAuthorName="(?i)[a-ż]+([. ]?[a-ż])*$";
    public static final String forAuthorLastName="(?i)[a-ż]*([- ]?[a-ż])*$";
    public static final String forBookTitle="(?i)[a-ż0-9]+.*";
    public static final String forSectionName="(?i)[a-ż0-9]+([- ]?[a-ż0-9])*$";
    public static final String forPublisherName="(?i)[a-ż0-9]+.*";
}
