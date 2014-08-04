package com.belikeastamp.blasuser.util;

import java.util.Arrays;

import android.app.Application;
import android.util.SparseArray;

import com.belikeastamp.blasuser.R;

public class ProjectData extends Application {

	public static SparseArray<String> colorName;

	private String projectTheme;
	private String projectType;
	private String projectStyle;
	private String projectName;
	private String numberOfCards;
	private String submitDate;
	private String orderDate;
	private int[] colorPanel;
	private PersoSubject perso;

	public ProjectData() {
		super();
		projectTheme = "";
		projectType = "";
		projectStyle = "";
		projectName = "";
		numberOfCards = "";
		submitDate = "";
		orderDate = "";
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
	public String getProjectTheme() {
		return projectTheme;
	}
	public void setProjectTheme(String projectTheme) {
		this.projectTheme = projectTheme;
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
	public String getProjectStyle() {
		return projectStyle;
	}
	public void setProjectStyle(String projectStyle) {
		this.projectStyle = projectStyle;
	}
	@Override
	public String toString() {
		return "ProjectData [projectTheme=" + projectTheme + ", projectType="
				+ projectType + ", projectStyle=" + projectStyle
				+ ", projectName=" + projectName + ", numberOfCards="
				+ numberOfCards + ", submitDate=" + submitDate + ", orderDate="
				+ orderDate + ", colorPanel=" + Arrays.toString(colorPanel)
				+ ", perso=" + perso + "]";
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
	}
}
