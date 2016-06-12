package tinker_io.handler;

import java.util.List;

public class OreCrusherRecipe {
	public static boolean isOreDicAccepted(String oreDic){
		if(oreDic != null && oreDic.length() >= 3){
			String title = oreDic.substring(0, 3);
			List<String> banList = OreCrusherBanList.getBanList();
			
			if(title.equals("ore") && !banList.contains(oreDic)){
				return true;
			}
		}
		return false;
	}

}
