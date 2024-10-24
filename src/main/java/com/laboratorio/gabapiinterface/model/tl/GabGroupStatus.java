package com.laboratorio.gabapiinterface.model.tl;

import java.util.List;
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
public class GabGroupStatus {
    private String i;   // id
    private String ca;  // create_at
    private boolean s;  // sensitive
    private String st;  // spoiler_text
    private String v;   // visibility
    private String l;   // language
    private String u;   // uri
    private String ul;  // url
    private int drc;
    private int rc;     // replies_count
    private int rbc;    // reblogs_count
    private boolean p;  // pinned
    private boolean pbg;
    private String qoi;
    private int fc;     // favourites_count
    private boolean hq;
    private int qc;
    private boolean ir;
    private String ai;      // Author Id
    private List<String> mai;
    private String ci;
    private String gi;      // Group Id
    private String rg;
    private boolean fvd;    // favourited
    private boolean rbgd;   // reblogged
    private boolean cr;
    private String c;       // content
}