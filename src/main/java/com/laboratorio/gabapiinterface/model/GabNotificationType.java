package com.laboratorio.gabapiinterface.model;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 25/07/2024
 * @updated 25/07/2024
 */
public enum GabNotificationType {
    MENTION, STATUS, REBLOG, FOLLOW, FOLLOW_REQUEST, FAVOURITE, POLL, UPDATE, ADMIN_SIGN_UP, ADMIN_REPORT, SEVERED_RELATIONSHIPS, MODERATION_WARNING;
    
    public static GabNotificationType fromString(String value) {
        for (GabNotificationType type : GabNotificationType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant " + GabNotificationType.class.getCanonicalName() + "." + value);
    }
}