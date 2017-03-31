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

@Syntax("(add|inject) css %string% (for|of) [player[s]] %players%")
public class EffAddCSS extends Effect {
	
	private Expression<Player> players;
	private Expression<String> CSS;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		players = (Expression<Player>) e[0];
		CSS = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(add|inject) css %string% (for|of) [player[s]] %players%";
	}
	@Override
	protected void execute(Event e) {
		if (players != null && CSS != null) {
			for (Player player : players.getAll(e)) {
				command.addJs(player.getName(), CSS.getSingle(e));
			}
		}
	}
}