package com.example.freightviewer.Model;

import java.util.HashMap;
import java.util.Map;

public enum USAStates {
    AL("Alabama", "AL"), AK("Alaska", "AK"), AS("American Samoa", "AS"),
    AZ("Arizona", "AZ"), AR("Arkansas", "AR"), CA("California", "CA"),
    CO("Colorado", "CO"), CT("Connecticut", "CT"), DE("Delaware", "DE"),
    DC("District of Columbia", "DC"), FM("Federated States of Micronesia", "FM"),
    FL("Florida", "FL"), GA("Georgia", "GA"), GU("Guam", "GU"), HI("Hawaii", "HI"),
    ID("Idaho", "ID"), IL("Illinois", "IL"), IN("Indiana", "IN"), IA("Iowa", "IA"),
    KS("Kansas", "KS"), KY("Kentucky", "KY"), LA("Louisiana", "LA"), ME("Maine", "ME"),
    MD("Maryland", "MD"), MH("Marshall Islands", "MH"), MA("Massachusetts", "MA"),
    MI("Michigan", "MI"), MN("Minnesota", "MN"), MS("Mississippi", "MS"),
    MO("Missouri", "MO"), MT("Montana", "MT"), NE("Nebraska", "NE"), NV("Nevada", "NV"),
    NH("New Hampshire", "NH"), NJ("New Jersey", "NJ"), NM("New Mexico", "NM"),
    NY("New York", "NY"), NC("North Carolina", "NC"),
    ND("North Dakota", "ND"), MP("Northern Mariana Islands", "MP"), OH("Ohio", "OH"),
    OK("Oklahoma", "OK"), OR("Oregon", "OR"), PW("Palau", "PW"),
    PA("Pennsylvania", "PA"), PR("Puerto Rico", "PR"), RI("Rhode Island", "RI"),
    SC("South Carolina", "SC"), SD("South Dakota", "SD"), TN("Tennessee", "TN"),
    TX("Texas", "TX"), UT("Utah", "UT"), VT("Vermont", "VT"),
    VI("Virgin Islands", "VI"), VA("Virginia", "VA"), WA("Washington", "WA"),
    WV("West Virginia", "WV"), WI("Wisconsin", "WI"), WY("Wyoming", "WY"), UNKNOWN(
            "Unknown", "-");

    /**
     * The state's name.
     */
    private String name;

    /**
     * The state's abbreviation.
     */
    private String abbreviation;

    /**
     * The set of states addressed by abbreviations.
     */
    private static final Map<String, USAStates> STATES_BY_ABBR = new HashMap<String, USAStates>();

    /* static initializer */
    static {
        for (USAStates state : values()) {
            STATES_BY_ABBR.put(state.getAbbreviation(), state);
        }
    }

    /**
     * Constructs a new state.
     *
     * @param name the state's name.
     * @param abbreviation the state's abbreviation.
     */
    USAStates(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    /**
     * Returns the state's abbreviation.
     *
     * @return the state's abbreviation.
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * Gets the enum constant with the specified abbreviation.
     *
     * @param abbr the state's abbreviation.
     * @return the enum constant with the specified abbreviation.
     */
    public static USAStates valueOfAbbreviation(final String abbr) {
        final USAStates state = STATES_BY_ABBR.get(abbr);
        if (state != null) {
            return state;
        } else {
            return UNKNOWN;
        }
    }

    public static USAStates valueOfName(final String name) {
        final String enumName = name.toUpperCase().replaceAll(" ", "_");
        try {
            return valueOf(enumName);
        } catch (final IllegalArgumentException e) {
            return USAStates.UNKNOWN;
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
