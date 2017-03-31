package me.limeglass.openaudiomcskript.Elements;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.openaudiomc.actions.command;

public class EffPlayAudio extends Effect {
	
	static {
		Skript.registerEffect(EffPlayAudio.class, "play audio [from] URL %string% (to|for) [player[s]] %players% [and] [[with] [ID] %-string%]");
	}
	
	private Expression<Player> players;
	private Expression<String> URL, ID;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		URL = (Expression<String>) e[0];
		players = (Expression<Player>) e[1];
		ID = (Expression<String>) e[2];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "play audio [from] URL %string% (to|for) %players% [and] [[with] [ID] %string%]";
	}
	@Override
	protected void execute(Event e) {
		if (players != null) {
			for (Player player : players.getAll(e)) {
				if (ID != null) {
					command.playNormalSoundID(player.getName(), URL.getSingle(e), ID.getSingle(e));
				} else {
					command.playNormalSound(player.getName(), URL.getSingle(e));
				}
			}
		}
	}
}