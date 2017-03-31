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
import me.mindgamesnl.openaudiomc.publicApi.OpenAudioApi;


@Syntax("set audio [web[ ]page] back[ ]ground (for|from|of) [player[s]] %players% to [URL] %string%")
public class EffSetBackground extends Effect {
	
	private Expression<Player> players;
	private Expression<String> URL;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		players = (Expression<Player>) e[0];
		URL = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "set audio [web[ ]page] back[ ]ground (for|from|of) [player[s]] %players% to [URL] %string%";
	}
	@Override
	protected void execute(Event e) {
		if (players != null) {
			for (Player player : players.getAll(e)) {
				OpenAudioApi.setBg(player, URL.getSingle(e));
			}
		}
	}
}
