package com.laboratorio.gabapiinterface.model.response;

import com.laboratorio.gabapiinterface.model.GabStatus;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 13/10/2024
 * @updated 13/10/2024
 */
@Getter @Setter @AllArgsConstructor
public class GabStatusListResponse {
    private List<GabStatus> statuses;
    private String nextPage;
}