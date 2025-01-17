package fi.septicuss.tooltips.managers.condition.impl.world;

import fi.septicuss.tooltips.managers.condition.Condition;
import fi.septicuss.tooltips.managers.condition.Context;
import fi.septicuss.tooltips.managers.condition.argument.Arguments;
import fi.septicuss.tooltips.managers.condition.type.Operation;
import fi.septicuss.tooltips.utils.validation.Validity;
import org.bukkit.entity.Player;

public class Time implements Condition {

	private static final String[] TIME = { "time", "t" };
	private static final String[] OPERATION = { "o", "oper", "operation" };

	@Override
	public boolean check(Player player, Arguments args) {

		Operation operation = Operation.EQUAL;
		int time = args.get(TIME).getAsInt();
		long worldTime = player.getWorld().getTime();
		
		if (args.has(OPERATION))
			operation = Operation.parseOperation(args.get(OPERATION).getAsString());

		switch (operation) {
		case EQUAL:
			return time == worldTime;
		case GREATER_THAN:
			return time > worldTime;
		case GREATER_THAN_OR_EQUAL:
			return time >= worldTime;
		case LESS_THAN:
			return time < worldTime;
		case LESS_THAN_OR_EQUAL:
			return time <= worldTime;
		default:
			return false;
		}

	}

	@Override
	public void writeContext(Player player, Arguments args, Context context) {
		context.put("time", player.getWorld().getTime());
	}

	@Override
	public Validity valid(Arguments args) {

		if (!args.has(TIME)) {
			return Validity.of(false, "Time argument is missing");
		}

		if (!args.isNumber(TIME)) {
			return Validity.of(false, "Time must be an integer");
		}

		if (args.has(OPERATION)) {
			String operation = args.get(OPERATION).getAsString();
			if (Operation.parseOperation(operation) == null) {
				return Validity.of(false, "Unknown operation " + quote(operation));
			}
		}

		return Validity.TRUE;
	}

	@Override
	public String id() {
		return "time";
	}
}
