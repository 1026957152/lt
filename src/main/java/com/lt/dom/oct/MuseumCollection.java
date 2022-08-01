package com.lt.dom.oct;

public class MuseumCollection {

    private String q;//	FIELD:VALUE (see Elasticsearch Query String syntax for more options)
    private String size;//	0-9+
    private String page;//	0-9+
    private String sort;//	FIELD NAME or "random" or "random:[SEED NUMBER]"
    private String sortorder;//	asc or desc
    private String fields;//	comma separated list of data fields you want in the output
    private String aggregation;//	see Elasticsearch aggregations
    private String hasimage;//	0 or 1
    private String century;//	CENTURY ID or pipe separated list of CENTURY IDs or CENTURY NAME or pipe separated list of CENTURY NAMES or "any"
    private String classification;//	CLASSIFICATION ID or pipe separated list of CLASSIFICATION IDs or CLASSIFICATION NAME or pipe separated list of CLASSIFICATION NAMES or "any"
    private String color;//	URL encoded COLOR or pipe separated list of URL encoded colors or "any"
    private String culture;//	CULTURE ID or pipe separated list of CULTURE IDs or CULTURE NAME or pipe separated list of CULTURE NAMES or "any"
    private String exhibition;//	EXHIBITION ID or "any" or "none"
    private String gallery;//	GALLERY NUMBER or pipe separated list of gallery numbers or "any" or "none"
    private String group;//	GROUP ID or pipe separated list of GROUP IDs or GROUP NAME or "any"
    private String keyword;//	a keyword search string; this parameter searches object titles, artists, description, classification, culture, and worktype
    private String medium;//	MEDIUM ID or pipe separated list of MEDIUM IDs or MEDIUM NAME or pipe separated list of MEDIUM NAMES or "any"
    private String objectnumber;//	1 or more terms
    private String period;//	PERIOD or pipe separated list of periods or "any"
    private String  person;//	PERSON ID or pipe separated list of PERSON IDs or PERSON NAME or "any"
    private String place;//	PLACE ID or pipe separated list of PLACE IDs or PLACE NAME or "any"
    private String publication;//	PUBLICATION ID or "any" or "none"
    private String relatedto;//	OBJECT ID
    private String technique;//	TECHNIQUE ID or pipe separated list of TECHNIQUE IDs or TECHNIQUE NAME or pipe separated list of TECHNIQUE NAMES or "any"
    private String title;//	1 or more terms
    private String exact_title;//	EXACT URL ENCODED TITLE
    private String worktype;//	WORKTYPE ID or pipe separated list of WORKTYPE IDs or WORKTYPE NAME or pipe separated list of WORKTYPE NAMES or "any"
    private String yearmade	;//four digit year

}
