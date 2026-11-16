package service;

import dsa.MetroGraph;
import model.MetroLine;
import model.Station;

import java.util.Set;

/**
 * Populates the {@link MetroGraph} with realistic Delhi Metro station and
 * edge data across six lines: Yellow, Blue, Red, Violet, Magenta, and Pink.
 *
 * <p>Includes ~120 stations and all major interchange points.</p>
 */
public class MetroDataLoader {

    private MetroDataLoader() {} // utility class

    /**
     * Builds and returns a fully-populated metro graph.
     *
     * @return a MetroGraph ready for pathfinding
     */
    public static MetroGraph loadGraph() {
        MetroGraph graph = new MetroGraph();
        addStations(graph);
        addYellowLineEdges(graph);
        addBlueLineEdges(graph);
        addRedLineEdges(graph);
        addVioletLineEdges(graph);
        addMagentaLineEdges(graph);
        addPinkLineEdges(graph);
        return graph;
    }

    // ═════════════════════════════════════════════════════════════════════
    //  STATIONS
    // ═════════════════════════════════════════════════════════════════════

    private static void addStations(MetroGraph graph) {
        // ── Yellow Line stations ────────────────────────────────────────
        graph.addStation(new Station("samaypur_badli", "Samaypur Badli", Set.of(MetroLine.YELLOW), true));
        graph.addStation(new Station("rohini_sector_18_19", "Rohini Sector 18-19", Set.of(MetroLine.YELLOW), false));
        graph.addStation(new Station("haiderpur_badli_mor", "Haiderpur Badli Mor", Set.of(MetroLine.YELLOW), false));
        graph.addStation(new Station("azadpur", "Azadpur", Set.of(MetroLine.YELLOW, MetroLine.PINK), false));       // INTERCHANGE
        graph.addStation(new Station("model_town", "Model Town", Set.of(MetroLine.YELLOW), false));
        graph.addStation(new Station("gtb_nagar", "GTB Nagar", Set.of(MetroLine.YELLOW), false));
        graph.addStation(new Station("vishwa_vidyalaya", "Vishwa Vidyalaya", Set.of(MetroLine.YELLOW), false));
        graph.addStation(new Station("vidhan_sabha", "Vidhan Sabha", Set.of(MetroLine.YELLOW), false));
        graph.addStation(new Station("civil_lines", "Civil Lines", Set.of(MetroLine.YELLOW), false));
        graph.addStation(new Station("kashmere_gate", "Kashmere Gate", Set.of(MetroLine.YELLOW, MetroLine.RED, MetroLine.VIOLET), true));  // INTERCHANGE
        graph.addStation(new Station("chandni_chowk", "Chandni Chowk", Set.of(MetroLine.YELLOW), false));
        graph.addStation(new Station("chawri_bazar", "Chawri Bazar", Set.of(MetroLine.YELLOW), false));
        graph.addStation(new Station("new_delhi", "New Delhi", Set.of(MetroLine.YELLOW), false));
        graph.addStation(new Station("rajiv_chowk", "Rajiv Chowk", Set.of(MetroLine.YELLOW, MetroLine.BLUE), false));  // INTERCHANGE
        graph.addStation(new Station("patel_chowk", "Patel Chowk", Set.of(MetroLine.YELLOW), false));
        graph.addStation(new Station("central_secretariat", "Central Secretariat", Set.of(MetroLine.YELLOW, MetroLine.VIOLET), false));  // INTERCHANGE
        graph.addStation(new Station("udyog_bhawan", "Udyog Bhawan", Set.of(MetroLine.YELLOW), false));
        graph.addStation(new Station("ina", "INA", Set.of(MetroLine.YELLOW, MetroLine.PINK), false));  // INTERCHANGE
        graph.addStation(new Station("aiims", "AIIMS", Set.of(MetroLine.YELLOW), false));
        graph.addStation(new Station("green_park", "Green Park", Set.of(MetroLine.YELLOW), false));
        graph.addStation(new Station("hauz_khas", "Hauz Khas", Set.of(MetroLine.YELLOW, MetroLine.MAGENTA), false));  // INTERCHANGE
        graph.addStation(new Station("malviya_nagar", "Malviya Nagar", Set.of(MetroLine.YELLOW), false));
        graph.addStation(new Station("saket", "Saket", Set.of(MetroLine.YELLOW), false));
        graph.addStation(new Station("qutab_minar", "Qutab Minar", Set.of(MetroLine.YELLOW), false));
        graph.addStation(new Station("sultanpur", "Sultanpur", Set.of(MetroLine.YELLOW), false));
        graph.addStation(new Station("ghitorni", "Ghitorni", Set.of(MetroLine.YELLOW), false));
        graph.addStation(new Station("arjan_garh", "Arjan Garh", Set.of(MetroLine.YELLOW), false));
        graph.addStation(new Station("guru_dronacharya", "Guru Dronacharya", Set.of(MetroLine.YELLOW), false));
        graph.addStation(new Station("sikandarpur", "Sikandarpur", Set.of(MetroLine.YELLOW), false));
        graph.addStation(new Station("huda_city_centre", "HUDA City Centre", Set.of(MetroLine.YELLOW), true));

        // ── Blue Line stations ──────────────────────────────────────────
        graph.addStation(new Station("dwarka_sector_21", "Dwarka Sector 21", Set.of(MetroLine.BLUE), true));
        graph.addStation(new Station("dwarka_sector_8", "Dwarka Sector 8", Set.of(MetroLine.BLUE), false));
        graph.addStation(new Station("dwarka", "Dwarka", Set.of(MetroLine.BLUE), false));
        graph.addStation(new Station("uttam_nagar_east", "Uttam Nagar East", Set.of(MetroLine.BLUE), false));
        graph.addStation(new Station("janakpuri_west", "Janakpuri West", Set.of(MetroLine.BLUE, MetroLine.MAGENTA), false));  // INTERCHANGE
        graph.addStation(new Station("tilak_nagar", "Tilak Nagar", Set.of(MetroLine.BLUE), false));
        graph.addStation(new Station("subhash_nagar", "Subhash Nagar", Set.of(MetroLine.BLUE), false));
        graph.addStation(new Station("rajouri_garden", "Rajouri Garden", Set.of(MetroLine.BLUE, MetroLine.PINK), false));  // INTERCHANGE
        graph.addStation(new Station("ramesh_nagar", "Ramesh Nagar", Set.of(MetroLine.BLUE), false));
        graph.addStation(new Station("moti_nagar", "Moti Nagar", Set.of(MetroLine.BLUE), false));
        graph.addStation(new Station("kirti_nagar", "Kirti Nagar", Set.of(MetroLine.BLUE), false));
        graph.addStation(new Station("shadipur", "Shadipur", Set.of(MetroLine.BLUE), false));
        graph.addStation(new Station("patel_nagar", "Patel Nagar", Set.of(MetroLine.BLUE), false));
        graph.addStation(new Station("rajendra_place", "Rajendra Place", Set.of(MetroLine.BLUE), false));
        graph.addStation(new Station("karol_bagh", "Karol Bagh", Set.of(MetroLine.BLUE), false));
        graph.addStation(new Station("jhandewalan", "Jhandewalan", Set.of(MetroLine.BLUE), false));
        graph.addStation(new Station("rk_ashram_marg", "R.K. Ashram Marg", Set.of(MetroLine.BLUE), false));
        graph.addStation(new Station("barakhamba_road", "Barakhamba Road", Set.of(MetroLine.BLUE), false));
        // rajiv_chowk already added above
        graph.addStation(new Station("mandi_house", "Mandi House", Set.of(MetroLine.BLUE, MetroLine.VIOLET), false));  // INTERCHANGE
        graph.addStation(new Station("pragati_maidan", "Pragati Maidan", Set.of(MetroLine.BLUE), false));
        graph.addStation(new Station("indraprastha", "Indraprastha", Set.of(MetroLine.BLUE), false));
        graph.addStation(new Station("yamuna_bank", "Yamuna Bank", Set.of(MetroLine.BLUE), false));
        graph.addStation(new Station("akshardham", "Akshardham", Set.of(MetroLine.BLUE), false));
        graph.addStation(new Station("mayur_vihar_phase_1", "Mayur Vihar Phase 1", Set.of(MetroLine.BLUE, MetroLine.PINK), false));  // INTERCHANGE
        graph.addStation(new Station("noida_sector_15", "Noida Sector 15", Set.of(MetroLine.BLUE), false));
        graph.addStation(new Station("botanical_garden", "Botanical Garden", Set.of(MetroLine.BLUE, MetroLine.MAGENTA), false));  // INTERCHANGE
        graph.addStation(new Station("noida_electronic_city", "Noida Electronic City", Set.of(MetroLine.BLUE), true));

        // ── Red Line stations ───────────────────────────────────────────
        graph.addStation(new Station("rithala", "Rithala", Set.of(MetroLine.RED), true));
        graph.addStation(new Station("netaji_subhash_place", "Netaji Subhash Place", Set.of(MetroLine.RED, MetroLine.PINK), false));  // INTERCHANGE
        graph.addStation(new Station("kohat_enclave", "Kohat Enclave", Set.of(MetroLine.RED), false));
        graph.addStation(new Station("pitampura", "Pitampura", Set.of(MetroLine.RED), false));
        graph.addStation(new Station("inderlok", "Inderlok", Set.of(MetroLine.RED), false));
        graph.addStation(new Station("shastri_nagar", "Shastri Nagar", Set.of(MetroLine.RED), false));
        graph.addStation(new Station("pratap_nagar", "Pratap Nagar", Set.of(MetroLine.RED), false));
        graph.addStation(new Station("pulbangash", "Pulbangash", Set.of(MetroLine.RED), false));
        graph.addStation(new Station("tis_hazari", "Tis Hazari", Set.of(MetroLine.RED), false));
        // kashmere_gate already added above
        graph.addStation(new Station("shastri_park", "Shastri Park", Set.of(MetroLine.RED), false));
        graph.addStation(new Station("seelampur", "Seelampur", Set.of(MetroLine.RED), false));
        graph.addStation(new Station("welcome", "Welcome", Set.of(MetroLine.RED), false));
        graph.addStation(new Station("shahdara", "Shahdara", Set.of(MetroLine.RED), false));
        graph.addStation(new Station("shaheed_sthal", "Shaheed Sthal", Set.of(MetroLine.RED), true));

        // ── Violet Line stations (non-duplicate) ────────────────────────
        // kashmere_gate, mandi_house, central_secretariat already added
        graph.addStation(new Station("lal_quila", "Lal Quila", Set.of(MetroLine.VIOLET), false));
        graph.addStation(new Station("jama_masjid", "Jama Masjid", Set.of(MetroLine.VIOLET), false));
        graph.addStation(new Station("delhi_gate", "Delhi Gate", Set.of(MetroLine.VIOLET), false));
        graph.addStation(new Station("ito", "ITO", Set.of(MetroLine.VIOLET), false));
        graph.addStation(new Station("janpath", "Janpath", Set.of(MetroLine.VIOLET), false));
        graph.addStation(new Station("khan_market", "Khan Market", Set.of(MetroLine.VIOLET), false));
        graph.addStation(new Station("jln_stadium", "JLN Stadium", Set.of(MetroLine.VIOLET), false));
        graph.addStation(new Station("jangpura", "Jangpura", Set.of(MetroLine.VIOLET), false));
        graph.addStation(new Station("lajpat_nagar", "Lajpat Nagar", Set.of(MetroLine.VIOLET, MetroLine.PINK), false));  // INTERCHANGE
        graph.addStation(new Station("moolchand", "Moolchand", Set.of(MetroLine.VIOLET), false));
        graph.addStation(new Station("kailash_colony", "Kailash Colony", Set.of(MetroLine.VIOLET), false));
        graph.addStation(new Station("nehru_place", "Nehru Place", Set.of(MetroLine.VIOLET), false));
        graph.addStation(new Station("kalkaji_mandir", "Kalkaji Mandir", Set.of(MetroLine.VIOLET, MetroLine.MAGENTA), false));  // INTERCHANGE
        graph.addStation(new Station("govind_puri", "Govind Puri", Set.of(MetroLine.VIOLET), false));
        graph.addStation(new Station("harkesh_nagar_okhla", "Harkesh Nagar Okhla", Set.of(MetroLine.VIOLET), false));
        graph.addStation(new Station("jasola_apollo", "Jasola Apollo", Set.of(MetroLine.VIOLET), false));
        graph.addStation(new Station("sarita_vihar", "Sarita Vihar", Set.of(MetroLine.VIOLET), false));
        graph.addStation(new Station("mohan_estate", "Mohan Estate", Set.of(MetroLine.VIOLET), false));
        graph.addStation(new Station("tughlakabad", "Tughlakabad", Set.of(MetroLine.VIOLET), false));
        graph.addStation(new Station("badarpur_border", "Badarpur Border", Set.of(MetroLine.VIOLET), false));
        graph.addStation(new Station("raja_nahar_singh", "Raja Nahar Singh", Set.of(MetroLine.VIOLET), true));

        // ── Magenta Line stations (non-duplicate) ───────────────────────
        // janakpuri_west, hauz_khas, kalkaji_mandir, botanical_garden already added
        graph.addStation(new Station("dabri_mor", "Dabri Mor", Set.of(MetroLine.MAGENTA), false));
        graph.addStation(new Station("dashrathpuri", "Dashrathpuri", Set.of(MetroLine.MAGENTA), false));
        graph.addStation(new Station("palam", "Palam", Set.of(MetroLine.MAGENTA), false));
        graph.addStation(new Station("sadar_bazar_cantonment", "Sadar Bazar Cantonment", Set.of(MetroLine.MAGENTA), false));
        graph.addStation(new Station("terminal_1_igi_airport", "Terminal 1 IGI Airport", Set.of(MetroLine.MAGENTA), false));
        graph.addStation(new Station("shankar_vihar", "Shankar Vihar", Set.of(MetroLine.MAGENTA), false));
        graph.addStation(new Station("vasant_vihar", "Vasant Vihar", Set.of(MetroLine.MAGENTA), false));
        graph.addStation(new Station("munirka", "Munirka", Set.of(MetroLine.MAGENTA), false));
        graph.addStation(new Station("rk_puram", "R.K. Puram", Set.of(MetroLine.MAGENTA), false));
        graph.addStation(new Station("iit_delhi", "IIT Delhi", Set.of(MetroLine.MAGENTA), false));
        graph.addStation(new Station("panchsheel_park", "Panchsheel Park", Set.of(MetroLine.MAGENTA), false));
        graph.addStation(new Station("chirag_delhi", "Chirag Delhi", Set.of(MetroLine.MAGENTA), false));
        graph.addStation(new Station("greater_kailash", "Greater Kailash", Set.of(MetroLine.MAGENTA), false));
        graph.addStation(new Station("nehru_enclave", "Nehru Enclave", Set.of(MetroLine.MAGENTA), false));
        graph.addStation(new Station("okhla_nsic", "Okhla NSIC", Set.of(MetroLine.MAGENTA), false));
        graph.addStation(new Station("sukhdev_vihar", "Sukhdev Vihar", Set.of(MetroLine.MAGENTA), false));
        graph.addStation(new Station("jamia_millia_islamia", "Jamia Millia Islamia", Set.of(MetroLine.MAGENTA), false));
        graph.addStation(new Station("okhla_vihar", "Okhla Vihar", Set.of(MetroLine.MAGENTA), false));
        graph.addStation(new Station("jasola_vihar_shaheen_bagh", "Jasola Vihar Shaheen Bagh", Set.of(MetroLine.MAGENTA), false));

        // ── Pink Line stations (non-duplicate) ──────────────────────────
        // azadpur, netaji_subhash_place, rajouri_garden, ina, lajpat_nagar, mayur_vihar_phase_1 already added
        graph.addStation(new Station("majlis_park", "Majlis Park", Set.of(MetroLine.PINK), true));
        graph.addStation(new Station("shalimar_bagh", "Shalimar Bagh", Set.of(MetroLine.PINK), false));
        graph.addStation(new Station("shakurpur", "Shakurpur", Set.of(MetroLine.PINK), false));
        graph.addStation(new Station("punjabi_bagh_west", "Punjabi Bagh West", Set.of(MetroLine.PINK), false));
        graph.addStation(new Station("esi_hospital", "ESI Hospital", Set.of(MetroLine.PINK), false));
        graph.addStation(new Station("mayapuri", "Mayapuri", Set.of(MetroLine.PINK), false));
        graph.addStation(new Station("naraina_vihar", "Naraina Vihar", Set.of(MetroLine.PINK), false));
        graph.addStation(new Station("delhi_cantt", "Delhi Cantt", Set.of(MetroLine.PINK), false));
        graph.addStation(new Station("durgabai_deshmukh_south_campus", "Durgabai Deshmukh South Campus", Set.of(MetroLine.PINK), false));
        graph.addStation(new Station("sir_vishweshwaraiah_moti_bagh", "Sir Vishweshwaraiah Moti Bagh", Set.of(MetroLine.PINK), false));
        graph.addStation(new Station("bhikaji_cama_place", "Bhikaji Cama Place", Set.of(MetroLine.PINK), false));
        graph.addStation(new Station("sarojini_nagar", "Sarojini Nagar", Set.of(MetroLine.PINK), false));
        graph.addStation(new Station("south_extension", "South Extension", Set.of(MetroLine.PINK), false));
        graph.addStation(new Station("vinobapuri", "Vinobapuri", Set.of(MetroLine.PINK), false));
        graph.addStation(new Station("ashram", "Ashram", Set.of(MetroLine.PINK), false));
        graph.addStation(new Station("hazrat_nizamuddin", "Hazrat Nizamuddin", Set.of(MetroLine.PINK), false));
        graph.addStation(new Station("trilokpuri_sanjay_lake", "Trilokpuri Sanjay Lake", Set.of(MetroLine.PINK), false));
        graph.addStation(new Station("east_vinod_nagar", "East Vinod Nagar", Set.of(MetroLine.PINK), false));
        graph.addStation(new Station("mandawali_west_vinod_nagar", "Mandawali West Vinod Nagar", Set.of(MetroLine.PINK), false));
        graph.addStation(new Station("ip_extension", "IP Extension", Set.of(MetroLine.PINK), false));
        graph.addStation(new Station("anand_vihar", "Anand Vihar", Set.of(MetroLine.PINK), false));
        graph.addStation(new Station("karkarduma", "Karkarduma", Set.of(MetroLine.PINK), false));
        graph.addStation(new Station("shiv_vihar", "Shiv Vihar", Set.of(MetroLine.PINK), true));
    }

    // ═════════════════════════════════════════════════════════════════════
    //  EDGES (bi-directional, with realistic distances and travel times)
    // ═════════════════════════════════════════════════════════════════════

    private static void addYellowLineEdges(MetroGraph graph) {
        MetroLine L = MetroLine.YELLOW;
        graph.addBiDirectionalEdge("samaypur_badli",       "rohini_sector_18_19",  1.5,  2, L);
        graph.addBiDirectionalEdge("rohini_sector_18_19",  "haiderpur_badli_mor",  1.3,  2, L);
        graph.addBiDirectionalEdge("haiderpur_badli_mor",  "azadpur",              2.1,  3, L);
        graph.addBiDirectionalEdge("azadpur",              "model_town",           1.8,  2, L);
        graph.addBiDirectionalEdge("model_town",           "gtb_nagar",            1.4,  2, L);
        graph.addBiDirectionalEdge("gtb_nagar",            "vishwa_vidyalaya",     1.2,  2, L);
        graph.addBiDirectionalEdge("vishwa_vidyalaya",     "vidhan_sabha",         1.5,  2, L);
        graph.addBiDirectionalEdge("vidhan_sabha",         "civil_lines",          1.3,  2, L);
        graph.addBiDirectionalEdge("civil_lines",          "kashmere_gate",        1.6,  2, L);
        graph.addBiDirectionalEdge("kashmere_gate",        "chandni_chowk",        1.4,  2, L);
        graph.addBiDirectionalEdge("chandni_chowk",        "chawri_bazar",         0.9,  2, L);
        graph.addBiDirectionalEdge("chawri_bazar",         "new_delhi",            1.1,  2, L);
        graph.addBiDirectionalEdge("new_delhi",            "rajiv_chowk",          1.0,  2, L);
        graph.addBiDirectionalEdge("rajiv_chowk",          "patel_chowk",          1.3,  2, L);
        graph.addBiDirectionalEdge("patel_chowk",          "central_secretariat",  0.9,  2, L);
        graph.addBiDirectionalEdge("central_secretariat",  "udyog_bhawan",         1.1,  2, L);
        graph.addBiDirectionalEdge("udyog_bhawan",         "ina",                  1.6,  2, L);
        graph.addBiDirectionalEdge("ina",                  "aiims",                1.2,  2, L);
        graph.addBiDirectionalEdge("aiims",                "green_park",           1.8,  2, L);
        graph.addBiDirectionalEdge("green_park",           "hauz_khas",            1.4,  2, L);
        graph.addBiDirectionalEdge("hauz_khas",            "malviya_nagar",        1.6,  2, L);
        graph.addBiDirectionalEdge("malviya_nagar",        "saket",                1.3,  2, L);
        graph.addBiDirectionalEdge("saket",                "qutab_minar",          1.5,  2, L);
        graph.addBiDirectionalEdge("qutab_minar",          "sultanpur",            2.1,  3, L);
        graph.addBiDirectionalEdge("sultanpur",            "ghitorni",             2.3,  3, L);
        graph.addBiDirectionalEdge("ghitorni",             "arjan_garh",           2.0,  3, L);
        graph.addBiDirectionalEdge("arjan_garh",           "guru_dronacharya",     2.5,  3, L);
        graph.addBiDirectionalEdge("guru_dronacharya",     "sikandarpur",          1.8,  2, L);
        graph.addBiDirectionalEdge("sikandarpur",          "huda_city_centre",     1.6,  2, L);
    }

    private static void addBlueLineEdges(MetroGraph graph) {
        MetroLine L = MetroLine.BLUE;
        graph.addBiDirectionalEdge("dwarka_sector_21",  "dwarka_sector_8",   2.2,  3, L);
        graph.addBiDirectionalEdge("dwarka_sector_8",   "dwarka",            1.8,  2, L);
        graph.addBiDirectionalEdge("dwarka",            "uttam_nagar_east",  2.5,  3, L);
        graph.addBiDirectionalEdge("uttam_nagar_east",  "janakpuri_west",    1.6,  2, L);
        graph.addBiDirectionalEdge("janakpuri_west",    "tilak_nagar",       1.9,  2, L);
        graph.addBiDirectionalEdge("tilak_nagar",       "subhash_nagar",     1.2,  2, L);
        graph.addBiDirectionalEdge("subhash_nagar",     "rajouri_garden",    1.1,  2, L);
        graph.addBiDirectionalEdge("rajouri_garden",    "ramesh_nagar",      1.0,  2, L);
        graph.addBiDirectionalEdge("ramesh_nagar",      "moti_nagar",        1.3,  2, L);
        graph.addBiDirectionalEdge("moti_nagar",        "kirti_nagar",       1.1,  2, L);
        graph.addBiDirectionalEdge("kirti_nagar",       "shadipur",          1.2,  2, L);
        graph.addBiDirectionalEdge("shadipur",          "patel_nagar",       1.0,  2, L);
        graph.addBiDirectionalEdge("patel_nagar",       "rajendra_place",    1.4,  2, L);
        graph.addBiDirectionalEdge("rajendra_place",    "karol_bagh",        1.1,  2, L);
        graph.addBiDirectionalEdge("karol_bagh",        "jhandewalan",       0.9,  2, L);
        graph.addBiDirectionalEdge("jhandewalan",       "rk_ashram_marg",    1.0,  2, L);
        graph.addBiDirectionalEdge("rk_ashram_marg",    "barakhamba_road",   1.6,  2, L);
        graph.addBiDirectionalEdge("barakhamba_road",   "rajiv_chowk",       0.8,  2, L);
        graph.addBiDirectionalEdge("rajiv_chowk",       "mandi_house",       1.3,  2, L);
        graph.addBiDirectionalEdge("mandi_house",       "pragati_maidan",    1.7,  2, L);
        graph.addBiDirectionalEdge("pragati_maidan",    "indraprastha",      1.2,  2, L);
        graph.addBiDirectionalEdge("indraprastha",      "yamuna_bank",       1.5,  2, L);
        graph.addBiDirectionalEdge("yamuna_bank",       "akshardham",        2.1,  3, L);
        graph.addBiDirectionalEdge("akshardham",        "mayur_vihar_phase_1", 1.8, 2, L);
        graph.addBiDirectionalEdge("mayur_vihar_phase_1", "noida_sector_15", 2.4,  3, L);
        graph.addBiDirectionalEdge("noida_sector_15",   "botanical_garden",  1.9,  2, L);
        graph.addBiDirectionalEdge("botanical_garden",  "noida_electronic_city", 2.2, 3, L);
    }

    private static void addRedLineEdges(MetroGraph graph) {
        MetroLine L = MetroLine.RED;
        graph.addBiDirectionalEdge("rithala",               "netaji_subhash_place", 2.0, 3, L);
        graph.addBiDirectionalEdge("netaji_subhash_place",  "kohat_enclave",        1.4, 2, L);
        graph.addBiDirectionalEdge("kohat_enclave",         "pitampura",            1.3, 2, L);
        graph.addBiDirectionalEdge("pitampura",             "inderlok",             3.2, 4, L);
        graph.addBiDirectionalEdge("inderlok",              "shastri_nagar",        1.1, 2, L);
        graph.addBiDirectionalEdge("shastri_nagar",         "pratap_nagar",         1.0, 2, L);
        graph.addBiDirectionalEdge("pratap_nagar",          "pulbangash",           1.2, 2, L);
        graph.addBiDirectionalEdge("pulbangash",            "tis_hazari",           1.0, 2, L);
        graph.addBiDirectionalEdge("tis_hazari",            "kashmere_gate",        1.5, 2, L);
        graph.addBiDirectionalEdge("kashmere_gate",         "shastri_park",         1.8, 2, L);
        graph.addBiDirectionalEdge("shastri_park",          "seelampur",            1.6, 2, L);
        graph.addBiDirectionalEdge("seelampur",             "welcome",              1.3, 2, L);
        graph.addBiDirectionalEdge("welcome",               "shahdara",             1.1, 2, L);
        graph.addBiDirectionalEdge("shahdara",              "shaheed_sthal",        1.9, 2, L);
    }

    private static void addVioletLineEdges(MetroGraph graph) {
        MetroLine L = MetroLine.VIOLET;
        graph.addBiDirectionalEdge("kashmere_gate",         "lal_quila",            1.3, 2, L);
        graph.addBiDirectionalEdge("lal_quila",             "jama_masjid",          0.9, 2, L);
        graph.addBiDirectionalEdge("jama_masjid",           "delhi_gate",           1.1, 2, L);
        graph.addBiDirectionalEdge("delhi_gate",            "ito",                  1.2, 2, L);
        graph.addBiDirectionalEdge("ito",                   "mandi_house",          1.0, 2, L);
        graph.addBiDirectionalEdge("mandi_house",           "janpath",              1.1, 2, L);
        graph.addBiDirectionalEdge("janpath",               "central_secretariat",  0.8, 2, L);
        graph.addBiDirectionalEdge("central_secretariat",   "khan_market",          1.5, 2, L);
        graph.addBiDirectionalEdge("khan_market",           "jln_stadium",          1.3, 2, L);
        graph.addBiDirectionalEdge("jln_stadium",           "jangpura",             1.1, 2, L);
        graph.addBiDirectionalEdge("jangpura",              "lajpat_nagar",         1.0, 2, L);
        graph.addBiDirectionalEdge("lajpat_nagar",          "moolchand",            1.2, 2, L);
        graph.addBiDirectionalEdge("moolchand",             "kailash_colony",       1.4, 2, L);
        graph.addBiDirectionalEdge("kailash_colony",        "nehru_place",          1.1, 2, L);
        graph.addBiDirectionalEdge("nehru_place",           "kalkaji_mandir",       1.0, 2, L);
        graph.addBiDirectionalEdge("kalkaji_mandir",        "govind_puri",          1.2, 2, L);
        graph.addBiDirectionalEdge("govind_puri",           "harkesh_nagar_okhla",  1.3, 2, L);
        graph.addBiDirectionalEdge("harkesh_nagar_okhla",   "jasola_apollo",        1.5, 2, L);
        graph.addBiDirectionalEdge("jasola_apollo",         "sarita_vihar",         1.7, 2, L);
        graph.addBiDirectionalEdge("sarita_vihar",          "mohan_estate",         1.4, 2, L);
        graph.addBiDirectionalEdge("mohan_estate",          "tughlakabad",          1.6, 2, L);
        graph.addBiDirectionalEdge("tughlakabad",           "badarpur_border",      2.0, 3, L);
        graph.addBiDirectionalEdge("badarpur_border",       "raja_nahar_singh",     2.3, 3, L);
    }

    private static void addMagentaLineEdges(MetroGraph graph) {
        MetroLine L = MetroLine.MAGENTA;
        graph.addBiDirectionalEdge("janakpuri_west",           "dabri_mor",                  1.4, 2, L);
        graph.addBiDirectionalEdge("dabri_mor",                "dashrathpuri",               1.2, 2, L);
        graph.addBiDirectionalEdge("dashrathpuri",             "palam",                      1.5, 2, L);
        graph.addBiDirectionalEdge("palam",                    "sadar_bazar_cantonment",     1.8, 2, L);
        graph.addBiDirectionalEdge("sadar_bazar_cantonment",   "terminal_1_igi_airport",     2.1, 3, L);
        graph.addBiDirectionalEdge("terminal_1_igi_airport",   "shankar_vihar",              1.9, 2, L);
        graph.addBiDirectionalEdge("shankar_vihar",            "vasant_vihar",               1.6, 2, L);
        graph.addBiDirectionalEdge("vasant_vihar",             "munirka",                    1.3, 2, L);
        graph.addBiDirectionalEdge("munirka",                  "rk_puram",                   1.1, 2, L);
        graph.addBiDirectionalEdge("rk_puram",                 "iit_delhi",                  1.4, 2, L);
        graph.addBiDirectionalEdge("iit_delhi",                "hauz_khas",                  1.2, 2, L);
        graph.addBiDirectionalEdge("hauz_khas",                "panchsheel_park",            1.0, 2, L);
        graph.addBiDirectionalEdge("panchsheel_park",          "chirag_delhi",               1.3, 2, L);
        graph.addBiDirectionalEdge("chirag_delhi",             "greater_kailash",            1.1, 2, L);
        graph.addBiDirectionalEdge("greater_kailash",          "nehru_enclave",              1.2, 2, L);
        graph.addBiDirectionalEdge("nehru_enclave",            "kalkaji_mandir",             1.0, 2, L);
        graph.addBiDirectionalEdge("kalkaji_mandir",           "okhla_nsic",                 1.3, 2, L);
        graph.addBiDirectionalEdge("okhla_nsic",               "sukhdev_vihar",              1.1, 2, L);
        graph.addBiDirectionalEdge("sukhdev_vihar",            "jamia_millia_islamia",       1.5, 2, L);
        graph.addBiDirectionalEdge("jamia_millia_islamia",     "okhla_vihar",                1.2, 2, L);
        graph.addBiDirectionalEdge("okhla_vihar",              "jasola_vihar_shaheen_bagh",  1.4, 2, L);
        graph.addBiDirectionalEdge("jasola_vihar_shaheen_bagh","botanical_garden",           1.8, 3, L);
    }

    private static void addPinkLineEdges(MetroGraph graph) {
        MetroLine L = MetroLine.PINK;
        graph.addBiDirectionalEdge("majlis_park",                        "azadpur",                              1.7, 2, L);
        graph.addBiDirectionalEdge("azadpur",                            "shalimar_bagh",                        1.9, 2, L);
        graph.addBiDirectionalEdge("shalimar_bagh",                      "netaji_subhash_place",                 1.5, 2, L);
        graph.addBiDirectionalEdge("netaji_subhash_place",               "shakurpur",                            1.8, 2, L);
        graph.addBiDirectionalEdge("shakurpur",                          "punjabi_bagh_west",                    1.3, 2, L);
        graph.addBiDirectionalEdge("punjabi_bagh_west",                  "esi_hospital",                         1.1, 2, L);
        graph.addBiDirectionalEdge("esi_hospital",                       "rajouri_garden",                       1.2, 2, L);
        graph.addBiDirectionalEdge("rajouri_garden",                     "mayapuri",                             1.4, 2, L);
        graph.addBiDirectionalEdge("mayapuri",                           "naraina_vihar",                        1.1, 2, L);
        graph.addBiDirectionalEdge("naraina_vihar",                      "delhi_cantt",                          1.6, 2, L);
        graph.addBiDirectionalEdge("delhi_cantt",                        "durgabai_deshmukh_south_campus",       1.3, 2, L);
        graph.addBiDirectionalEdge("durgabai_deshmukh_south_campus",     "sir_vishweshwaraiah_moti_bagh",        1.0, 2, L);
        graph.addBiDirectionalEdge("sir_vishweshwaraiah_moti_bagh",      "bhikaji_cama_place",                   1.2, 2, L);
        graph.addBiDirectionalEdge("bhikaji_cama_place",                 "sarojini_nagar",                       1.4, 2, L);
        graph.addBiDirectionalEdge("sarojini_nagar",                     "ina",                                  1.3, 2, L);
        graph.addBiDirectionalEdge("ina",                                "south_extension",                      1.1, 2, L);
        graph.addBiDirectionalEdge("south_extension",                    "lajpat_nagar",                         1.5, 2, L);
        graph.addBiDirectionalEdge("lajpat_nagar",                       "vinobapuri",                           1.0, 2, L);
        graph.addBiDirectionalEdge("vinobapuri",                         "ashram",                               1.2, 2, L);
        graph.addBiDirectionalEdge("ashram",                             "hazrat_nizamuddin",                    1.4, 2, L);
        graph.addBiDirectionalEdge("hazrat_nizamuddin",                  "mayur_vihar_phase_1",                  2.8, 3, L);
        graph.addBiDirectionalEdge("mayur_vihar_phase_1",                "trilokpuri_sanjay_lake",               1.3, 2, L);
        graph.addBiDirectionalEdge("trilokpuri_sanjay_lake",             "east_vinod_nagar",                     1.1, 2, L);
        graph.addBiDirectionalEdge("east_vinod_nagar",                   "mandawali_west_vinod_nagar",           1.0, 2, L);
        graph.addBiDirectionalEdge("mandawali_west_vinod_nagar",         "ip_extension",                        1.2, 2, L);
        graph.addBiDirectionalEdge("ip_extension",                       "anand_vihar",                          1.5, 2, L);
        graph.addBiDirectionalEdge("anand_vihar",                        "karkarduma",                           1.3, 2, L);
        graph.addBiDirectionalEdge("karkarduma",                         "shiv_vihar",                           2.8, 3, L);
    }
}
