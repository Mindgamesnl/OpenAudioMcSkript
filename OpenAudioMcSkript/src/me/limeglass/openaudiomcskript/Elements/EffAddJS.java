package me.limeglass.openaudiomcskript.Elements;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.limeglass.openaudiomcskript.Utils.Syntax;
import net.openaudiomc.actions.command;

@Syntax("(add|inject) (java[ ]script|js) %string% (for|of) [player[s]] %players%")
public class EffAddJS extends Effect {
	
	private Expression<Player> players;
	private Expression<String> JS;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		players = (Expression<Player>) e[0];
		JS = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(add|inject) (java[ ]script|js) %string% (for|of) [player[s]] %players%";
	}
	@Override
	protected void execute(Event e) {
		if (players != null && JS != null) {
			for (Player player : players.getAll(e)) {
				command.addJs(player.getName(), JS.getSingle(e));
			}
		}
	}
}