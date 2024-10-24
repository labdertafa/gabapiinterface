package com.laboratorio.gabapiinterface.model.tl;

import com.laboratorio.gabapiinterface.model.GabAccount;
import com.laboratorio.gabapiinterface.model.GabStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 24/10/2024
 * @updated 24/10/2024
 */

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class GabGroupsTlResponse {
    private List<String> t;
    private List<GabGroupStatus> s;
    private List<GabGroupAccount> a;
    private List<GabGroup> g;
    private List<GabMessageAttach> ma;
    
    public List<GabStatus> getStatusList() {
        List<GabStatus> statuses = new ArrayList<>();
        
        for (GabGroupStatus ggs : this.s) {
            GabStatus status = new GabStatus(ggs);
            Optional<GabGroupAccount> groupAccount = this.a.stream()
                    .filter(account -> account.getI().equals(ggs.getAi()))
                    .findFirst();
            if (groupAccount.isPresent()) {
                GabAccount account = new GabAccount(groupAccount.get());
                status.setAccount(account);
                statuses.add(status);
            }
        }
        
        return statuses;
    }
}