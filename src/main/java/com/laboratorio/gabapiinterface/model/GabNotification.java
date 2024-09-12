package com.laboratorio.gabapiinterface.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 25/07/2024
 * @updated 25/07/2024
 */

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class GabNotification {
    private String id;
    private String type;
    private String created_at;
    private GabAccount account;
    private GabStatus status;
    private GabReport report;
    private GabRelationshipSeveranceEvent relationship_severance_event;
    private GabAccountWarning moderation_warning;
}