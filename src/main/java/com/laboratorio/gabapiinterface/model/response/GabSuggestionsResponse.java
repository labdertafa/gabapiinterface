package com.laboratorio.gabapiinterface.model.response;

import com.laboratorio.gabapiinterface.model.GabAccount;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 17/09/2024
 * @updated 17/09/2024
 */

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class GabSuggestionsResponse {
    private List<GabAccount> accounts;
}