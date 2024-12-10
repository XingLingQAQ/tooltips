package fi.septicuss.tooltips.managers.condition.impl.world;

import fi.septicuss.tooltips.managers.condition.Context;
import org.bukkit.entity.Player;

import fi.septicuss.tooltips.managers.condition.Condition;
import fi.septicuss.tooltips.managers.condition.argument.Arguments;
import fi.septicuss.tooltips.managers.condition.type.MultiString;
import fi.septicuss.tooltips.utils.cache.area.CurrentAreaCache;
import fi.septicuss.tooltips.utils.validation.Validity;

public class Region implements Condition {

	private static final String[] REGION = { "r", "reg", "region", "name", "id" };

	@Override
	public boolean check(Player player, Arguments args) {
		MultiString region = null;

		if (args.has(REGION))
			region = MultiString.of(args.get(REGION).getAsString());

		if (region == null) {
			return CurrentAreaCache.has(player);
		}

		if (!CurrentAreaCache.has(player))
			return false;

		for (var applicableRegion : CurrentAreaCache.get(player))
			if (region.contains(applicableRegion))
				return true;

		return false;
	}

	@Override
	public void writeContext(Player player, Arguments args, Context context) {
		MultiString region = null;

		if (args.has(REGION))
			region = MultiString.of(args.get(REGION).getAsString());

		if (region == null) {
			return;
		}

		if (!CurrentAreaCache.has(player))
			return;

		for (var applicableRegion : CurrentAreaCache.get(player))
			if (region.contains(applicableRegion)) {
				context.put("region", applicableRegion);
				return;
			}

	}

	@Override
	public Validity valid(Arguments args) {
		return Validity.TRUE;
	}

	@Override
	public String id() {
		return "region";
	}
}