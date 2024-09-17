package com.laboratorio.gabapiinterface.model.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 17/09/2024
 * @updated 17/09/2024
 */

@Getter @Setter @AllArgsConstructor
public class GabRelationshipRequest {
    private List<String> accountIds;
}