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

@Syntax("skip [audio] to [time[span]] %string% (for|of|in) [player[s]] %players% [(in|for|with)] [id] %string%")
public class EffSkipSongLength extends Effect {
	
	private Expression<Player> players;
	private Expression<String> time, ID;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		time = (Expression<String>) e[0];
		players = (Expression<Player>) e[1];
		ID = (Expression<String>) e[2];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "skip [audio] to [time[span]] %string% (for|of|in) [player[s]] %players% [(in|for|with)] [id] %string%";
	}
	@Override
	protected void execute(Event e) {
		if (players != null && time != null && ID != null) {
			for (Player player : players.getAll(e)) {
				command.skipTo(player.getName(), ID.getSingle(e), time.getSingle(e));
			}
		}
	}
}