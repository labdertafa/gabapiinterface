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
public class GabReport {
    private String id;
    private boolean action_taken;
    private String action_taken_at;
    private String category;
    private String comment;
    private boolean forwarded;
    private String created_at;
    private String[] status_ids;
    private String[] rule_ids;
    private GabAccount target_account;
}