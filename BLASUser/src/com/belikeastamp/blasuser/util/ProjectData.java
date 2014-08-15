package com.belikeastamp.blasuser.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import android.app.Application;
import android.net.Uri;
import android.util.SparseArray;

import com.belikeastamp.blasuser.R;

public class ProjectData extends Application {

	public static SparseArray<String> colorName;
	public static Map<String,Integer> reverseColorMap;

	private String projectType;
	private String projectName;
	private String numberOfCards;
	private String submitDate;
	private String orderDate;
	private int[] colorPanel;
	private PersoSubject perso;
	private Uri trackFile;
	private String infos;
	private Map<String,String> details;


	public ProjectData() {
		super();
		projectType = "";
		projectName = "";
		numberOfCards = "";
		submitDate = "";
		orderDate = "";
		infos = "";
		setDetails(new HashMap<String, String>());
		colorPanel = new int[]{-1,-1,-1};
		fillMap();
	}
	public int[] getColorPanel() {
		return colorPanel;
	}
	public void setColorPanel(int[] colorPanel) {
		this.colorPanel = colorPanel;
	}
	public void setColor1(int i) {
		this.colorPanel[0] = i;
	}
	public void setColor2(int i) {
		this.colorPanel[1] = i;
	}
	public void setColor3(int i) {
		this.colorPanel[2] = i;
	}
	public int getColor1() {
		return this.colorPanel[0];
	}
	public int getColor2() {
		return this.colorPanel[1];
	}
	public int getColor3() {
		return this.colorPanel[2];
	}

	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getNumberOfCards() {
		return numberOfCards;
	}
	public void setNumberOfCards(String numberOfCards) {
		this.numberOfCards = numberOfCards;
	}
	public String getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public PersoSubject getPerso() {
		return perso;
	}
	public void setPerso(PersoSubject perso) {
		this.perso = perso;
	}
	public String getInfos() {
		return infos;
	}
	public void setInfos(String infos) {
		this.infos = infos;
	}
	public Map<String,String> getDetails() {
		return details;
	}
	public void setDetails(Map<String,String> details) {
		this.details = details;
	}
	public Uri getTrackFile() {
		return trackFile;
	}
	public void setTrackFile(Uri trackFile) {
		this.trackFile = trackFile;
	}

	public void addNewDetails(String key, String value) {
		this.details.put(key, value);
	}

	public boolean isFilled(String detail) {
		return this.details.containsKey(detail);
	}

	public String getPrintableDetails() {
		Map<String, String> map = new TreeMap<String, String>(details);
		StringBuffer sb = new StringBuffer();
		
		for (String s : map.keySet() ) {
			sb.append(s+" => "+map.get(s)+" ; ");
		}

		return sb.toString();
	}

	@Override
	public String toString() {		
		return "ProjectData [projectType=" + projectType + ", projectName="
				+ projectName + ", numberOfCards=" + numberOfCards
				+ ", submitDate=" + submitDate + ", orderDate=" + orderDate
				+ ", colorPanel=" + Arrays.toString(colorPanel) + ", perso="
				+ perso + ", trackFile=" + trackFile + ", infos=" + infos
				+ ", details=" + getPrintableDetails() + "]";
	}


	private void fillMap() {

		colorName = new SparseArray<String>();
		// tendances
		colorName.put(R.color.rasberry_ripple,"rasberry_ripple");
		colorName.put(R.color.summer_starfruit,"summer_starfruit");
		colorName.put(R.color.midnight_muse,"midnight_muse");
		colorName.put(R.color.primrose_petals,"primrose_petals");
		colorName.put(R.color.gumball_green,"gumball_green");
		colorName.put(R.color.baked_brown_sugar,"baked_brown_sugar");
		colorName.put(R.color.coastal_cabana,"coastal_cabana");
		colorName.put(R.color.crisp_cantaloupe,"crisp_cantaloupe");
		colorName.put(R.color.strawberry_slush,"strawberry_slush");
		colorName.put(R.color.pistachio_pudding,"pistachio_pudding");
		// eclatantes
		colorName.put(R.color.bermuda_bay,"bermuda_bay");
		colorName.put(R.color.daffodil_delight,"daffodil_delight");
		colorName.put(R.color.melon_mambo,"melon_mambo");
		colorName.put(R.color.old_olive,"old_olive");
		colorName.put(R.color.pacific_point,"pacific_point");
		colorName.put(R.color.pumpkin_pie,"pumpkin_pie");
		colorName.put(R.color.real_red,"real_red");
		colorName.put(R.color.rich_razzleberry,"rich_razzleberry");
		colorName.put(R.color.tangerine_tango,"tangerine_tango");
		colorName.put(R.color.tempting_turquoise,"tempting_turquoise");
		// subtiles
		colorName.put(R.color.blushing_bride,"blushing_bride");
		colorName.put(R.color.calypso_coral,"calypso_coral");
		colorName.put(R.color.marina_mist,"marina_mist");
		colorName.put(R.color.pear_pizzazz,"pear_pizzazz");
		colorName.put(R.color.pink_pirouette,"pink_pirouette");
		colorName.put(R.color.pool_party,"pool_party");
		colorName.put(R.color.so_saffron,"so_saffron");
		colorName.put(R.color.soft_sky,"soft_sky");
		colorName.put(R.color.wild_wasabi,"wild_wasabi");
		colorName.put(R.color.wisteria_wonder,"wisteria_wonder");
		// gourmandes
		colorName.put(R.color.always_artichoke,"always_artichoke");
		colorName.put(R.color.cajun_craze,"cajun_craze");
		colorName.put(R.color.cherry_cobbler,"cherry_cobbler");
		colorName.put(R.color.crushed_curry,"crushed_curry");
		colorName.put(R.color.elegant_eggplant,"elegant_eggplant");
		colorName.put(R.color.garden_green,"garden_green");
		colorName.put(R.color.island_indigo,"island_indigo");
		colorName.put(R.color.night_of_navy,"night_of_navy");
		colorName.put(R.color.rose_red,"rose_red");
		colorName.put(R.color.perfect_plum,"perfect_plum");
		// neutres
		colorName.put(R.color.basic_black,"basic_black");
		colorName.put(R.color.basic_gray,"basic_gray");
		colorName.put(R.color.chocolate_chip,"chocolate_chip");
		colorName.put(R.color.crumb_cake,"crumb_cake");
		colorName.put(R.color.early_espresso,"early_espresso");
		colorName.put(R.color.sahara_sand,"sahara_sand");
		colorName.put(R.color.whisper_white,"whisper_white");
		colorName.put(R.color.smoky_slate,"smoky_slate");
		colorName.put(R.color.soft_suede,"soft_suede");
		colorName.put(R.color.very_vanilla,"very_vanilla");

		reverseColorMap = new HashMap<String, Integer>();

		reverseColorMap.put("rasberry_ripple",R.color.rasberry_ripple);
		reverseColorMap.put("summer_starfruit",R.color.summer_starfruit);
		reverseColorMap.put("midnight_muse",R.color.midnight_muse);
		reverseColorMap.put("primrose_petals",R.color.primrose_petals);
		reverseColorMap.put("gumball_green",R.color.gumball_green);
		reverseColorMap.put("baked_brown_sugar",R.color.baked_brown_sugar);
		reverseColorMap.put("coastal_cabana",R.color.coastal_cabana);
		reverseColorMap.put("crisp_cantaloupe",R.color.crisp_cantaloupe);
		reverseColorMap.put("strawberry_slush",R.color.strawberry_slush);
		reverseColorMap.put("pistachio_pudding",R.color.pistachio_pudding);
		// eclatantes
		reverseColorMap.put("bermuda_bay",R.color.bermuda_bay);
		reverseColorMap.put("daffodil_delight",R.color.daffodil_delight);
		reverseColorMap.put("melon_mambo",R.color.melon_mambo);
		reverseColorMap.put("old_olive",R.color.old_olive);
		reverseColorMap.put("pacific_point",R.color.pacific_point);
		reverseColorMap.put("pumpkin_pie",R.color.pumpkin_pie);
		reverseColorMap.put("real_red",R.color.real_red);
		reverseColorMap.put("rich_razzleberry",R.color.rich_razzleberry);
		reverseColorMap.put("tangerine_tango",R.color.tangerine_tango);
		reverseColorMap.put("tempting_turquoise",R.color.tempting_turquoise);
		// subtiles
		reverseColorMap.put("blushing_bride",R.color.blushing_bride);
		reverseColorMap.put("calypso_coral",R.color.calypso_coral);
		reverseColorMap.put("marina_mist",R.color.marina_mist);
		reverseColorMap.put("pear_pizzazz",R.color.pear_pizzazz);
		reverseColorMap.put("pink_pirouette",R.color.pink_pirouette);
		reverseColorMap.put("pool_party",R.color.pool_party);
		reverseColorMap.put("so_saffron",R.color.so_saffron);
		reverseColorMap.put("soft_sky",R.color.soft_sky);
		reverseColorMap.put("wild_wasabi",R.color.wild_wasabi);
		reverseColorMap.put("wisteria_wonder",R.color.wisteria_wonder);
		// gourmandes
		reverseColorMap.put("always_artichoke",R.color.always_artichoke);
		reverseColorMap.put("cajun_craze",R.color.cajun_craze);
		reverseColorMap.put("cherry_cobbler",R.color.cherry_cobbler);
		reverseColorMap.put("crushed_curry",R.color.crushed_curry);
		reverseColorMap.put("elegant_eggplant",R.color.elegant_eggplant);
		reverseColorMap.put("garden_green",R.color.garden_green);
		reverseColorMap.put("island_indigo",R.color.island_indigo);
		reverseColorMap.put("night_of_navy",R.color.night_of_navy);
		reverseColorMap.put("rose_red",R.color.rose_red);
		reverseColorMap.put("perfect_plum",R.color.perfect_plum);
		// neutres
		reverseColorMap.put("basic_black",R.color.basic_black);
		reverseColorMap.put("basic_gray",R.color.basic_gray);
		reverseColorMap.put("chocolate_chip",R.color.chocolate_chip);
		reverseColorMap.put("crumb_cake",R.color.crumb_cake);
		reverseColorMap.put("early_espresso",R.color.early_espresso);
		reverseColorMap.put("sahara_sand",R.color.sahara_sand);
		reverseColorMap.put("whisper_white",R.color.whisper_white);
		reverseColorMap.put("smoky_slate",R.color.smoky_slate);
		reverseColorMap.put("soft_suede",R.color.soft_suede);
		reverseColorMap.put("very_vanilla",R.color.very_vanilla);

	}




}
