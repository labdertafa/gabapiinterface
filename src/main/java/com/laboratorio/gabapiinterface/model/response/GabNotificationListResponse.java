package com.laboratorio.gabapiinterface.model.response;

import com.laboratorio.gabapiinterface.model.GabNotification;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 25/07/2024
 * @updated 05/09/2024
 */
@Getter @Setter @AllArgsConstructor
public class GabNotificationListResponse {
    private String minId;
    private List<GabNotification> notifications;
}