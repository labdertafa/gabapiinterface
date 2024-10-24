package com.laboratorio.gabapiinterface.model.tl;

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
public class GabGroupAccount {
    private String i;   // Id
    private String un;  // username
    private String ac;  // acct
    private String dn;  // display_name
    private boolean l;  // locked
    private String ca;  // created_at
    private String nt;  // note
    private String u;   // url
    private String av;  // avatar
    private String avs; // avatar_static
    private String avsml;
    private String avss;
    private String h;   // header
    private String hs;  // header_static
    private boolean is;
    private int foc;    // followers_count
    private int fic;    // following_count
    private int sc;     // statuses_count
    private boolean ip; //  
    private boolean iv;
    private boolean idn;   // indexable
    private boolean li;     // limited
    private boolean spl;
}