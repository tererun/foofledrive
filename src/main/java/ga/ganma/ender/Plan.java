package ga.ganma.ender;

import java.io.Serializable;

public enum Plan implements Serializable {
	FREE,LIGHT,MIDDLE,LARGE;

	/**
	 * プラン名として有効な文字列か判定する
	 * @param arg プラン名（かもしれない）文字列
	 * @return 大文字小文字を意識せず、プラン名として有効な場合<code>true</code>。それ以外の場合<code>false</code>
	 */
	public static boolean isPlanName(String arg){
		if(arg == null || arg.isEmpty()){
			return false;
		}
		for(Plan p: Plan.values()){
			if(arg.equalsIgnoreCase(p.name())){
				return true;
			}
		}
		return false;
	}

	/**
	 * 引数に対応するPlanを返す
	 * @param arg 取得したいPlan名
	 * @return 有効なPlan名の場合、対応するPlan。対応しない場合は<code>null</code>
	 */
	public static Plan getPlan(String arg){
		if(isPlanName(arg)){
			for(Plan p : Plan.values()){
				if(p.name().equalsIgnoreCase(arg)){
					return p;
				}
			}
		}
		return null;
	}

	/**
	 * Plan名の一覧を取得する
	 * @return カンマ区切りで列挙したPlan名の一覧
	 */
	public static String getPlanNameList(){
		StringBuilder sb = new StringBuilder();
		for(Plan p : Plan.values()){
			if(sb.length() != 0){
				sb.append(',');
			}
			sb.append(p.name());
		}
		return sb.toString();
	}
}
